package pigpio4s

sealed trait DigitalValue {
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