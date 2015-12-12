package pigpio4s

import com.ochafik.lang.jnaerator.runtime.NativeSize
import com.sun.jna.Pointer
import pigpio4s.{PigpioLibrary => lib}

import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

/**
 *
 */
trait SerialIO {
    def gpioSerialReadOpen(user_gpio: UserGpio, baud: BaudRate, data_bits: DataBits): Try[GpioResult]
    def gpioSerialReadInvert(user_gpio: UserGpio, invert: InvertSerial): Try[GpioResult]
    def gpioSerialReadClose(user_gpio: UserGpio): Try[GpioResult]

    // buf and bufSize should be hidden, and a data stream is made available
    def gpioSerialRead(user_gpio: UserGpio, buf: Pointer, bufSize: NativeSize): Try[SerialReadResult]
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

    // revisit;; buf and bufSize should be hidden, and a stream be made available
    def gpioSerialRead(user_gpio: UserGpio, buf: Pointer, bufSize: NativeSize): Try[SerialReadResult] = {
        try {
            pigpio.gpioSerialRead(user_gpio.value, buf, bufSize) match {
                case sz if sz >= 0 => Success(ReadOK(sz))
                case lib.PI_BAD_USER_GPIO => Failure(BadUserGpio())
                case lib.PI_NOT_SERIAL_GPIO => Failure(NotSerialGpio())
                case ec => Failure(UnknownFailure())
            }
        }
        catch {
            case NonFatal(e) => Failure(e)
        }
    }
}
