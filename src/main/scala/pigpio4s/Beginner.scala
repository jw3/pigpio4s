package pigpio4s

import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

/**
 * members of the BEGINNER section as described in pigpio.h
 * https://github.com/joan2937/pigpio/blob/master/pigpio.h
 */
trait Beginner {
    def gpioSetMode(gpio: Gpio, mode: PinMode): Try[GpioResult]
    def gpioGetMode(gpio: Gpio): Try[PinMode]

    def gpioRead(gpio: Gpio): Try[DigitalValue]
    def gpioWrite(gpio: Gpio, level: DigitalValue): Try[GpioResult]
    def gpioSetPullUpDown(gpio: Gpio, pud: GpioPull): Try[GpioResult]

    def gpioPWM(user_gpio: UserGpio, dutycycle: DutyCycle): Try[GpioResult]
    def gpioGetPWMdutycycle(user_gpio: UserGpio): Try[DutyCycle]

    def gpioServo(user_gpio: UserGpio, pulsewidth: ServoPulse): Try[GpioResult]
    def gpioGetServoPulsewidth(user_gpio: UserGpio): Try[ServoPulse]

    def gpioSetAlertFunc(user_gpio: UserGpio, f: GpioWatcher): Try[GpioResult]
}


trait DefaultBeginner extends Beginner {
    private val pigpio = PigpioLibrary.INSTANCE

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

    def gpioPWM(user_gpio: UserGpio, dutycycle: DutyCycle): Try[GpioResult] =
        gpioResultFunction(pigpio.gpioPWM(user_gpio.value, dutycycle.value))


    def gpioGetPWMdutycycle(user_gpio: UserGpio): Try[DutyCycle] = {
        try Success(DutyCycle(pigpio.gpioGetPWMdutycycle(user_gpio.value)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }

    def gpioServo(user_gpio: UserGpio, pulsewidth: ServoPulse): Try[GpioResult] =
        gpioResultFunction(pigpio.gpioServo(user_gpio.value, pulsewidth.value))

    def gpioGetServoPulsewidth(user_gpio: UserGpio): Try[ServoPulse] = {
        try Success(ServoPulse(pigpio.gpioGetServoPulsewidth(user_gpio.value)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }

    def gpioSetAlertFunc(user_gpio: UserGpio, f: GpioWatcher): Try[GpioResult] =
        gpioResultFunction(pigpio.gpioSetAlertFunc(user_gpio.value, f))
}
