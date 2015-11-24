package pigpio4s

import pigpio4s.{PigpioLibrary => lib}

sealed trait ServoPulseWidth {
    def value: Int
}

case object ServoPulseOff extends ServoPulseWidth {val value = lib.PI_SERVO_OFF}
case class ServoPulseOn private[pigpio4s](value: Int) extends ServoPulseWidth

object ServoPulseWidth {
    val range = Range(500, 2500)

    def apply(pw: Int) = pw match {
        case lib.PI_SERVO_OFF => ServoPulseOff
        case v if range.contains(v) => ServoPulseOn(pw)
        case _ => throw BadPulseWidth()
    }
}
