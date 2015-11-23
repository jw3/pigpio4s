import pigpio4s.PigpioLibrary.gpioAlertFunc_t
import pigpio4s.{PigpioLibrary => lpigpio}

import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

package object pigpio4s {
    type GPIO_t = Int
    type DutyCycle_t = Int
    type PinMode_t = Int
    type Pull_t = Int
    type ServoPulseWidth_t = Int

    val userGpios = Range(0, lpigpio.PI_MAX_USER_GPIO)
    val servoPulseWidths = Range(500, 2500)

    trait Gpio {
        def value: GPIO_t
    }

    case class UserGpio private[pigpio4s](value: Int) extends Gpio
    case class ExGpio private[pigpio4s](value: Int) extends Gpio
    object Gpio {
        // default behavior of Gpio is user-gpios
        def apply(num: Int) = {
            require(userGpios.contains(num), "out of range")
            UserGpio(num)
        }
    }

    sealed trait ServoPulse {
        def value: ServoPulseWidth_t
    }
    case object ServoOff extends ServoPulse {val value = 0}
    case class ServoOn private[pigpio4s](value: ServoPulseWidth_t) extends ServoPulse

    object ServoPulse {
        def apply(pw: ServoPulseWidth_t) = {
            require(servoPulseWidths.contains(pw), "unsupported pulse width")
            ServoOn(pw)
        }
    }

    sealed trait GpioPull {
        def value: Pull_t
    }
    case object PullUp extends GpioPull {val value = lpigpio.PI_PUD_UP}
    case object PullDown extends GpioPull {val value = lpigpio.PI_PUD_DOWN}
    case object DontPull extends GpioPull {val value = lpigpio.PI_PUD_OFF}

    object GpioPull {
        def apply(value: Pull_t) = value match {
            case lpigpio.PI_PUD_UP => PullUp
            case lpigpio.PI_PUD_DOWN => PullDown
            case lpigpio.PI_PUD_OFF => DontPull
            case _ => throw new IllegalArgumentException(s"unsupported pull, $value")
        }
    }

    sealed trait PinMode {
        def value: PinMode_t
    }
    case object InputPin extends PinMode {val value = lpigpio.PI_INPUT}
    case object OutputPin extends PinMode {val value = lpigpio.PI_OUTPUT}

    object PinMode {
        def apply(value: PinMode_t) = value match {
            case lpigpio.PI_INPUT => InputPin
            case lpigpio.PI_OUTPUT => OutputPin
            case _ => throw new IllegalArgumentException(s"unsupported mode, $value")
        }
    }

    // aka Analog, could rename?
    trait DutyCycle {
        def value: DutyCycle_t
    }

    object DutyCycle {
        val default = lpigpio.PI_DEFAULT_DUTYCYCLE_RANGE
        val min = lpigpio.PI_MIN_DUTYCYCLE_RANGE
        val max = lpigpio.PI_MAX_DUTYCYCLE_RANGE

        def apply() = new DutyCycle {val value = default}
        def apply(dc: DutyCycle_t) = {
            require(dc <= max && dc >= min, "out of range")
            new DutyCycle {val value = dc}
        }
    }

    case class GpioAlert(gpio: Int, level: Int, tick: Int)
    trait GpioWatcher extends gpioAlertFunc_t {
        def apply(alert: GpioAlert): Unit
        def apply(gpio: Int, level: Int, tick: Int): Unit = apply(GpioAlert(gpio, level, tick))
    }

    trait DigitalValue {
        def value: Int
    }
    case object High extends DigitalValue {val value = 1}
    case object Low extends DigitalValue {val value = 0}

    object DigitalValue {
        def apply(bool: Boolean) = if (bool) High else Low
        def apply(int: Int) = int match {
            case 0 => Low
            case 1 => High
            case _ => throw BadLevel()
        }
    }

    trait GpioResult
    trait GpioFailure extends RuntimeException with GpioResult
    trait BadGpio extends GpioFailure

    case object OK extends GpioResult
    case class BadUserGpio() extends BadGpio
    case class BadExGpio() extends BadGpio
    case class UnknownFailure() extends GpioFailure
    case class NotServoGpio() extends GpioFailure
    case class BadMode() extends GpioFailure
    case class BadLevel() extends GpioFailure

    object GpioResult {
        def apply(code: Int) = code match {
            case 0 => OK
            case lpigpio.PI_BAD_USER_GPIO => throw BadUserGpio()
            case lpigpio.PI_BAD_GPIO => throw BadExGpio()
            case lpigpio.PI_NOT_SERVO_GPIO => throw NotServoGpio()
            case lpigpio.PI_BAD_MODE => throw BadMode()
            case _ => throw UnknownFailure()
        }
    }

    def gpioResultFunction(f: => Int): Try[GpioResult] = {
        try Success(GpioResult(f))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }
}
