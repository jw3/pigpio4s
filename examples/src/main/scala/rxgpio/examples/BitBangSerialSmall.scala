package rxgpio.examples

import rxgpio.Gpio.Implicits._
import rxgpio._
import rxgpio.pigpio.PigpioLibrary

import scala.concurrent.duration.DurationInt
import scala.util.Success


/**
 * simplified version of the [[BitBangSerial]] example
 * this one sends only one string, and then shuts down
 */
object BitBangSerialSmall {
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

    val baud = BaudRates.`115200`
    val bits = DataBits.`8`
    val runtime = 300 seconds
    val tenCharTime = 100.0 / baud.value

    digitalIO.gpioSetMode(TX, PinModes.output)
    serialIO.gpioSerialReadClose(RX)
    waveIO.gpioWaveClear()

    var msg = "hello, serial!"
    waveIO.gpioWaveAddSerial(TX, baud, DataBits.`8`, StopBits.`2`, msg)

    var wid = waveIO.gpioWaveCreate().map(r => WaveId(r.value)).get
    serialIO.gpioSerialReadOpen(RX, baud, bits)
    waveIO.gpioWaveTxSend(wid, WaveModes.once) // transmit serial data

    while (waveIO.gpioWaveTxBusy().get) Thread.sleep(1)

    waveIO.gpioWaveDelete(wid)
    DefaultSerialIO.gpioSerialReadClose(RX)
    DefaultInitializer.gpioTerminate()

    println("exiting")
}
