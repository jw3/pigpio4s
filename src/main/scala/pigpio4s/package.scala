import pigpio4s.PigpioLibrary.{gpioAlertFunc_t, gpioTimerFunc_t}
import pigpio4s.{PigpioLibrary => lpigpio}

package object pigpio4s {
    type DutyCycle_t = Int
    type PinMode_t = Int
    type Pull_t = Int
    type ServoPulseWidth_t = Int


    sealed trait ServoPulse {
        def value: ServoPulseWidth_t
    }
    case object ServoOff extends ServoPulse {val value = 0}
    case class ServoOn private[pigpio4s](value: ServoPulseWidth_t) extends ServoPulse

    object ServoPulse {
        def apply(pw: ServoPulseWidth_t) = {
            require(pw >= 500 && pw <= 2500, "unsupported pulse width")
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

    case class TimerAlert(/*userdata?*/)
    trait TimerWatcher extends gpioTimerFunc_t {
        def apply(alert: TimerAlert): Unit
        def apply(): Unit = apply(TimerAlert())
    }
}
