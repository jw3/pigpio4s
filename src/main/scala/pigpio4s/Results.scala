package pigpio4s

import pigpio4s.{PigpioLibrary => lib}


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

object GpioResult {
    def apply(code: Int) = code match {
        case 0 => OK
        case lib.PI_BAD_USER_GPIO => throw BadUserGpio()
        case lib.PI_BAD_GPIO => throw BadExGpio()
        case lib.PI_NOT_SERVO_GPIO => throw NotServoGpio()
        case lib.PI_BAD_MODE => throw BadMode()
        case _ => throw UnknownFailure()
    }
}