package rxgpio.examples

import rxgpio.Gpio.Implicits._
import rxgpio._
import rxgpio.pigpio.PigpioLibrary

import scala.collection.immutable.Range
import scala.concurrent.duration.DurationInt
import scala.util.Success


/**
 *
 * bb_serial.py
 * 2014-12-23
 * Public Domain
 *
 * bit bang transmit and receive of serial data
 *
 * tested by connecting the arbitrary RX/TX gpios to a USB
 * serial dongle plugged in to a Linux box.
 *
 * on the Linux box set the baud and data bits (cs5-cs8)
 *
 * stty -F /dev/ttyUSB0 19200 cs8
 * cat </dev/ttyUSB0 >/dev/ttyUSB0
 *
 * so the Linux box echoes back data received from the Pi.
 *
 * laptop timings deviations
 *
 * baud  exp us   act us
 *  50   20000    13310 * 75
 *  75   13333    13310
 * 110    9091    13310 * 75
 * 134    7462     6792 * 150
 * 150    6667     6792
 * 200    5000     6792 * 150
 * 300    3333     3362
 *
 */
object BitBangSerial {
    DefaultInitializer.gpioInitialise() match {
        case Success(InitOK(ver)) =>
            println(s"initialized pigpio:$ver")
        case _ =>
            println("failed")
            System.exit(1)
    }
    implicit val pigpio = PigpioLibrary.Instance
    val digitalIO: DigitalIO = DefaultDigitalIO
    val serialIO: SerialIO = DefaultSerialIO
    val waveIO: WaveIO = DefaultWaveIO

    val RX: UserGpio = 19
    val TX: UserGpio = 26
    val MSGLEN = 256

    val baud = BaudRates.`115200`
    val bits = DataBits.`8`
    val runtime = 300 seconds
    val tenCharTime = 100.0 / baud.value

    val MASK = (1 << bits.value) - 1

    var msg = Array(MSGLEN + 256)
    for (i <- Range(0, MSGLEN)) msg(i) = i & MASK

    var first: Int = 0
    val pi = pigpio
    digitalIO.gpioSetMode(TX, PinModes.output)

    // fatal exceptions off (so that closing an unopened gpio doesn't error)
    //pigpio.exceptions = False

    serialIO.gpioSerialReadClose(RX)

    // fatal exceptions on
    //pigpio.exceptions = True

    // create a waveform representing the serial data
    //pi.wave_clear()

    var TEXT = msg.slice(first, first + MSGLEN)
    waveIO.gpioWaveAddSerial(TX, baud, DataBits.`8`, StopBits.`2`, TEXT.mkString)
    //    pi.wave_add_serial(TX, baud, TEXT)

    var wid = waveIO.gpioWaveCreate().map(r => WaveId(r.value)).get
    //pi.wave_create()

    // open a gpio to bit bang read the echoed data

    serialIO.gpioSerialReadOpen(RX, baud, bits)
    //pi.bb_serial_read_open(RX, baud, bits)

    var bad_char = 0
    var total_char = 0

    while (true) {
        waveIO.gpioWaveTxSend(wid, WaveModes.once) // transmit serial data

        //pi.wave_delete(wid)
        waveIO.gpioWaveDelete(wid)

        val TXTEXT = TEXT

        first += 1
        if (first >= MSGLEN) first = 0

        TEXT = msg.slice(first, first + MSGLEN) // msg[first: first + MSGLEN]
        //pi.wave_add_serial(TX, baud, TEXT, bb_bits = 7)
        waveIO.gpioWaveAddSerial(TX, baud, DataBits.`7`, StopBits.`2`, TEXT.mkString)

        while (waveIO.gpioWaveTxBusy().get) Thread.sleep(1) // pi.wave_tx_busy(): // wait until all data sent

        //wid = pi.wave_create()
        wid = waveIO.gpioWaveCreate().map(r => WaveId(r.value)).get

        var count = 1
        var text = ""
        var lt = 0
        total_char += MSGLEN

        while (count > 0) // read echoed serial data
            serialIO.gpioSerialRead(RX) { data =>
                count = data.length

                if (count > 0) text += data
                lt += count
                Thread.sleep(tenCharTime.toInt)
                //time.sleep(ten_char_time) // #enough time to ensure more data
            } // pi.bb_serial_read(RX)

        // #Do sent and received match ?
        if (text != TXTEXT) {
            // #No, is message correct length ?
            if (lt == MSGLEN) {
                // #If so compare byte by byte.
                for (i <- Range(0, MSGLEN)) {
                    if (text(i) != TXTEXT(i)) // #print("{:2X} {:2X}".format(text[i], TXTEXT[i]))
                        bad_char += 1
                }
            }
        }
        else {
            // #Wrong message length, find matching blocks.
            var ok = 0
            /*var s = difflib.SequenceMatcher(None, TXTEXT, text)
            for frag in s.get_matching_blocks():
                ok += frag[ 2] // #More matching bytes found.

            //#print(frag)#print(text, MSGLEN, ok)

            // Sanity check
            if (ok < MSGLEN)
                bad_char += (MSGLEN - ok)
            else*/
            println(s"*** ERRONEOUS good=$ok LEN=$MSGLEN ***")
        }
    }


    println(s"secs=$runtime baud=$baud bits=$bits bad=${bad_char * 100.0 / total_char}%")
    println(s"total=$total_char badchar=$bad_char")

    // free resources

    waveIO.gpioWaveDelete(wid)

    DefaultSerialIO.gpioSerialReadClose(RX)

    DefaultInitializer.gpioTerminate()
}
