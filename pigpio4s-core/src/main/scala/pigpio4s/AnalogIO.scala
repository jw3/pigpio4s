package pigpio4s

import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

/**
 *
 */
trait AnalogIO {
    def gpioPWM(user_gpio: UserGpio, dutycycle: DutyCycle)(implicit pigpio: PigpioLibrary): Try[GpioResult]
    def gpioGetPWMdutycycle(user_gpio: UserGpio)(implicit pigpio: PigpioLibrary): Try[DutyCycle]

    def gpioServo(user_gpio: UserGpio, pulsewidth: ServoPulseWidth)(implicit pigpio: PigpioLibrary): Try[GpioResult]
    def gpioGetServoPulsewidth(user_gpio: UserGpio)(implicit pigpio: PigpioLibrary): Try[ServoPulseWidth]
}

object DefaultAnalogIO extends DefaultAnalogIO

trait DefaultAnalogIO extends AnalogIO {
    def gpioPWM(user_gpio: UserGpio, dutycycle: DutyCycle)(implicit pigpio: PigpioLibrary): Try[GpioResult] =
        gpioResultFunction(pigpio.gpioPWM(user_gpio.value, dutycycle.value))


    def gpioGetPWMdutycycle(user_gpio: UserGpio)(implicit pigpio: PigpioLibrary): Try[DutyCycle] = {
        try Success(DutyCycle(pigpio.gpioGetPWMdutycycle(user_gpio.value)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }

    def gpioServo(user_gpio: UserGpio, pulsewidth: ServoPulseWidth)(implicit pigpio: PigpioLibrary): Try[GpioResult] =
        gpioResultFunction(pigpio.gpioServo(user_gpio.value, pulsewidth.value))

    def gpioGetServoPulsewidth(user_gpio: UserGpio)(implicit pigpio: PigpioLibrary): Try[ServoPulseWidth] = {
        try Success(ServoPulseWidth(pigpio.gpioGetServoPulsewidth(user_gpio.value)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }
}
