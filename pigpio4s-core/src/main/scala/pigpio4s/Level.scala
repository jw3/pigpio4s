package pigpio4s

/**
 *
 */
trait Level {
    def value: Int
}

case object High extends Level {val value = 1}
case object On extends Level {val value = 1}
case object Set extends Level {val value = 1}

case object Low extends Level {val value = 0}
case object Off extends Level {val value = 0}
case object Clear extends Level {val value = 0}

object Level {
    def apply(bool: Boolean) = if (bool) High else Low
    def apply(int: Int) = int match {
        case 0 => Low
        case 1 => High
        case _ => throw BadLevel()
    }
}