package pigpio4s

import pigpio4s.{PigpioLibrary => lib}

sealed trait InitResult
case class InitOK private[pigpio4s](ver: Int) extends InitResult
case object InitFailed extends InitResult
case object UnknownInitFailure extends InitResult

object InitResult {
    def apply(code: Int) = code match {
        case lib.PI_INIT_FAILED => InitFailed
        case ver: Int => InitOK(ver)
    }
}

sealed trait GpioResult
case object OK extends GpioResult
sealed trait GpioFailure extends RuntimeException with GpioResult
case class UnknownFailure() extends GpioFailure

sealed trait BadGpio extends GpioFailure
case class BadUserGpio() extends BadGpio
case class BadExGpio() extends BadGpio

case class NotServoGpio() extends GpioFailure
case class BadMode() extends GpioFailure
case class BadLevel() extends GpioFailure
case class BadDutyCycle() extends GpioFailure
case class BadDutyCycleRange() extends GpioFailure
case class BadPull() extends GpioFailure
case class BadPulseWidth() extends GpioFailure

object GpioResult {
    def apply(code: Int) = code match {
        case 0 => OK
        case lib.PI_BAD_USER_GPIO => throw BadUserGpio()
        case lib.PI_BAD_GPIO => throw BadExGpio()
        case lib.PI_NOT_SERVO_GPIO => throw NotServoGpio()
        case lib.PI_BAD_MODE => throw BadMode()
        case lib.PI_BAD_DUTYCYCLE => BadDutyCycle()
        case lib.PI_BAD_PUD => BadPull()
        case lib.PI_BAD_PULSEWIDTH => BadPulseWidth()
        case _ => throw UnknownFailure()
    }
}