package rxgpio

import rxgpio.pigpio.PigpioLibrary


sealed trait BaudRate {
    def value: Int
}

object BaudRate {
    val range = Range(50, 250000)

    def apply(rate: Int): Unit = {
        if (!range.contains(rate)) throw BadBaudRate()
        new BaudRate() {val value = rate}
    }
}

object BaudRates {
    val `9600` = BaudRate(9600)
    val `115200` = BaudRate(115200)
}

sealed trait DataBits {
    def value: Int
}

object DataBits {
    val `8` = new DataBits {val value = 8}
}

sealed trait InvertSerial {
    def value: Int
}
case object InvertedSerial extends InvertSerial {val value = PigpioLibrary.PI_BB_SER_INVERT}
case object NormalSerial extends InvertSerial {val value = PigpioLibrary.PI_BB_SER_NORMAL}