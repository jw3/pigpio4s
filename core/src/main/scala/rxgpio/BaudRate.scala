package rxgpio

import rxgpio.pigpio.PigpioLibrary


sealed trait BaudRate {
    def value: Int
}
private case class DefaultBaudRate(value: Int) extends BaudRate

object BaudRate {
    val range = Range(50, 250000)

    def apply(rate: Int): BaudRate = {
        if (!range.contains(rate)) throw BadBaudRate()
        DefaultBaudRate(rate)
    }
}

object BaudRates {
    val `9600` = BaudRate(9600)
    val `115200` = BaudRate(115200)
}

sealed trait DataBits {
    def value: Int
}
private case class DefaultDataBits(value: Int) extends DataBits

object DataBits {
    val `8`: DataBits = DefaultDataBits(8)
    val `7`: DataBits = DefaultDataBits(7)
}

sealed trait StopBits {
    def value: Int
}
private case class DefaultStopBits(value: Int) extends StopBits

object StopBits {
    val `2`: StopBits = DefaultStopBits(2)
}

sealed trait InvertSerial {
    def value: Int
}
case object InvertedSerial extends InvertSerial {val value = PigpioLibrary.PI_BB_SER_INVERT}
case object NormalSerial extends InvertSerial {val value = PigpioLibrary.PI_BB_SER_NORMAL}
