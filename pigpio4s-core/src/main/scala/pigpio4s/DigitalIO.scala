package pigpio4s

import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

/**
 *
 */
trait DigitalIO {
    def gpioGetMode(gpio: Gpio): Try[PinMode]
    def gpioSetMode(gpio: Gpio, mode: PinMode): Try[GpioResult]

    def gpioRead(gpio: Gpio): Try[DigitalValue]
    def gpioWrite(gpio: Gpio, level: DigitalValue): Try[GpioResult]

    def gpioSetPullUpDown(gpio: Gpio, pud: GpioPull): Try[GpioResult]
    def gpioSetAlertFunc(user_gpio: UserGpio, f: GpioWatcher): Try[GpioResult]
}

object DefaultDigitalIO extends DefaultDigitalIO

trait DefaultDigitalIO {
    val pigpio: PigpioLibrary = PigpioLibrary.Instance

    def gpioGetMode(gpio: Gpio): Try[PinMode] = {
        try Success(PinMode(pigpio.gpioGetMode(gpio.value)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }
    def gpioSetMode(gpio: Gpio, mode: PinMode): Try[GpioResult] =
        gpioResultFunction(pigpio.gpioSetMode(gpio.value, mode.value))

    def gpioSetPullUpDown(gpio: Gpio, pud: GpioPull): Try[GpioResult] =
        gpioResultFunction(pigpio.gpioSetPullUpDown(gpio.value, pud.value))

    def gpioRead(gpio: Gpio): Try[DigitalValue] = {
        try Success(DigitalValue(pigpio.gpioRead(gpio.value)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }
    def gpioWrite(gpio: Gpio, level: DigitalValue): Try[GpioResult] =
        gpioResultFunction(pigpio.gpioWrite(gpio.value, level.value))

    def gpioSetAlertFunc(user_gpio: UserGpio, f: GpioWatcher): Try[GpioResult] =
        gpioResultFunction(pigpio.gpioSetAlertFunc(user_gpio.value, f))
}