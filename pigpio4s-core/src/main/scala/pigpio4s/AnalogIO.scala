package pigpio4s

import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

/**
 *
 */
trait AnalogIO {
    def gpioPWM(user_gpio: UserGpio, dutycycle: DutyCycle): Try[GpioResult]
    def gpioGetPWMdutycycle(user_gpio: UserGpio): Try[DutyCycle]

    def gpioServo(user_gpio: UserGpio, pulsewidth: ServoPulseWidth): Try[GpioResult]
    def gpioGetServoPulsewidth(user_gpio: UserGpio): Try[ServoPulseWidth]
}

object DefaultAnalogIO extends DefaultAnalogIO

trait DefaultAnalogIO extends AnalogIO {
    val pigpio: PigpioLibrary = PigpioLibrary.Instance

    def gpioPWM(user_gpio: UserGpio, dutycycle: DutyCycle): Try[GpioResult] =
        gpioResultFunction(pigpio.gpioPWM(user_gpio.value, dutycycle.value))


    def gpioGetPWMdutycycle(user_gpio: UserGpio): Try[DutyCycle] = {
        try Success(DutyCycle(pigpio.gpioGetPWMdutycycle(user_gpio.value)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }

    def gpioServo(user_gpio: UserGpio, pulsewidth: ServoPulseWidth): Try[GpioResult] =
        gpioResultFunction(pigpio.gpioServo(user_gpio.value, pulsewidth.value))

    def gpioGetServoPulsewidth(user_gpio: UserGpio): Try[ServoPulseWidth] = {
        try Success(ServoPulseWidth(pigpio.gpioGetServoPulsewidth(user_gpio.value)))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }
}
