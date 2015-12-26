package rxgpio

import rxgpio.pigpio.{PigpioLibrary => lib}

sealed trait GpioPull {
    def value: Int
}

case object PullUp extends GpioPull {val value = lib.PI_PUD_UP}
case object PullDown extends GpioPull {val value = lib.PI_PUD_DOWN}
case object DontPull extends GpioPull {val value = lib.PI_PUD_OFF}

object GpioPull {
    def apply(value: Int) = value match {
        case lib.PI_PUD_UP => PullUp
        case lib.PI_PUD_DOWN => PullDown
        case lib.PI_PUD_OFF => DontPull
        case _ => throw BadPull()
    }
}
