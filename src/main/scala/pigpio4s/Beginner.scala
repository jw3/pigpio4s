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

    def gpioSetPullUpDown(gpio: Gpio, pud: GpioPull): Try[GpioResult]
    def gpioRead(gpio: Gpio): Try[DigitalValue]
    def gpioWrite(gpio: Gpio, level: DigitalValue): Try[GpioResult]
    def gpioPWM(user_gpio: UserGpio, dutycycle: DutyCycle): Try[GpioResult]
    def gpioGetPWMdutycycle(user_gpio: UserGpio): Try[DutyCycle]

    def gpioServo(user_gpio: UserGpio, pulsewidth: ServoPulse): Try[GpioResult]

    /**
    Returns the servo pulsewidth setting for the gpio.

        . .
        user_gpio: 0-31
        . .

        Returns , 0 (off), 500 (most anti-clockwise) to 2500 (most clockwise)
        if OK, otherwise PI_BAD_USER_GPIO or PI_NOT_SERVO_GPIO.
     */
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
    def gpioSetMode(gpio: Gpio, mode: PinMode): Try[GpioResult] = {
        try Success(GpioResult(pigpio.gpioSetMode(gpio.value, mode.value)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }

    def gpioSetPullUpDown(gpio: Gpio, pud: GpioPull): Try[GpioResult] = {
        try Success(GpioResult(pigpio.gpioSetPullUpDown(gpio.value, pud.value)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }

    def gpioRead(gpio: Gpio): Try[DigitalValue] = {
        try Success(DigitalValue(pigpio.gpioRead(gpio.value)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }
    def gpioWrite(gpio: Gpio, level: DigitalValue): Try[GpioResult] = {
        try Success(GpioResult(pigpio.gpioWrite(gpio.value, level.value)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }

    def gpioPWM(user_gpio: UserGpio, dutycycle: DutyCycle): Try[GpioResult] = {
        try Success(GpioResult(pigpio.gpioPWM(user_gpio.value, dutycycle.value)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }
    def gpioGetPWMdutycycle(user_gpio: UserGpio): Try[DutyCycle] = {
        try Success(DutyCycle(pigpio.gpioGetPWMdutycycle(user_gpio.value)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }

    def gpioServo(user_gpio: UserGpio, pulsewidth: ServoPulse): Try[GpioResult] = {
        try Success(GpioResult(pigpio.gpioServo(user_gpio.value, pulsewidth.value)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }
    def gpioGetServoPulsewidth(user_gpio: UserGpio): Try[ServoPulse] = {
        try Success(ServoPulse(pigpio.gpioGetServoPulsewidth(user_gpio.value)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }

    def gpioSetAlertFunc(user_gpio: UserGpio, f: GpioWatcher): Try[GpioResult] = {
        try Success(GpioResult(pigpio.gpioSetAlertFunc(user_gpio.value, f)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }
}