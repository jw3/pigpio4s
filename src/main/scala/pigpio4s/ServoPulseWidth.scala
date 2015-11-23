package pigpio4s

sealed trait ServoPulseWidth {
    def value: Int
}

case object ServoPulseOff extends ServoPulseWidth {val value = 0}
case class ServoPulseOn private[pigpio4s](value: Int) extends ServoPulseWidth

object ServoPulseWidth {
    val range = Range(500, 2500)

    def apply(pw: Int) = {
        require(range.contains(pw), "unsupported pulse width")
        ServoPulseOn(pw)
    }
}
