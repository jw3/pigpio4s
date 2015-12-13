package pigpio4s

import com.ochafik.lang.jnaerator.runtime.NativeSize
import com.sun.jna.Memory
import pigpio4s.{PigpioLibrary => lib}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

/**
 *
 */
trait SerialIO {
    def gpioSerialReadOpen(user_gpio: UserGpio, baud: BaudRate, data_bits: DataBits): Try[GpioResult]
    def gpioSerialReadInvert(user_gpio: UserGpio, invert: InvertSerial): Try[GpioResult]
    def gpioSerialReadClose(user_gpio: UserGpio): Try[GpioResult]

    // buf and bufSize should be hidden, and a data stream is made available
    def gpioSerialRead(user_gpio: UserGpio, sz: Int = 1024)(fn: String => Unit): Future[SerialReadResult]
}

object DefaultSerialIO extends DefaultSerialIO

trait DefaultSerialIO {
    def pigpio: PigpioLibrary = PigpioLibrary.Instance

    def gpioSerialReadOpen(user_gpio: UserGpio, baud: BaudRate, data_bits: DataBits): Try[GpioResult] =
        gpioResultFunction(pigpio.gpioSerialReadOpen(user_gpio.value, baud.value, data_bits.value))

    def gpioSerialReadInvert(user_gpio: UserGpio, invert: InvertSerial): Try[GpioResult] =
        gpioResultFunction(pigpio.gpioSerialReadInvert(user_gpio.value, invert.value))

    def gpioSerialReadClose(user_gpio: UserGpio): Try[GpioResult] =
        gpioResultFunction(pigpio.gpioSerialReadClose(user_gpio.value))

    def gpioSerialRead(user_gpio: UserGpio, bufsz: Int = 1024)(fn: String => Unit): Future[SerialReadResult] = {
        require(bufsz > 0, "read size must be gt zero")
        val size = new NativeSize(bufsz)
        val buffer = new Memory(size.intValue)
        Future {
            // revisit;; not sure if this native call is blocking
            pigpio.gpioSerialRead(user_gpio.value, buffer, size) match {
                case sz if sz >= 0 =>
                    fn(buffer.getString(0))
                    ReadOK(sz)
                case lib.PI_BAD_USER_GPIO => throw BadUserGpio()
                case lib.PI_NOT_SERIAL_GPIO => throw NotSerialGpio()
                case ec => throw UnknownFailure()
            }
        }
    }
}
