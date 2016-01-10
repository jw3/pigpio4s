package rxgpio

import org.scalatest.{Matchers, WordSpecLike}
import rxgpio.pigpio.PigpioLibrary

/**
 *
 */
class ModelingSerialSpec extends WordSpecLike with Matchers {
    "Baud Rates" should {
        "throw bad baud if out of bounds" in {
            intercept[BadBaudRate] {BaudRate(BaudRate.range.min - 1)}
            intercept[BadBaudRate] {BaudRate(BaudRate.range.max + 1)}
        }
        "have accurate constants" in {
            BaudRates.`9600` shouldBe BaudRate(9600)
            BaudRates.`115200` shouldBe BaudRate(115200)
        }
        "create correctly" in {
            BaudRate(BaudRate.range.min + 1).value shouldBe BaudRate.range.min + 1
        }
    }
    "Data Bits" should {
        "have accurate constants" in {
            DataBits.`8`.value shouldBe 8
            DataBits.`7`.value shouldBe 7
        }
    }
    "Stop Bits" should {
        "have accurate constants" in {
            StopBits.`2`.value shouldBe 2
        }
    }
    "inverted serial" should {
        "be inverted" in {InvertedSerial.value shouldBe PigpioLibrary.PI_BB_SER_INVERT}
        "be normal" in {NormalSerial.value shouldBe PigpioLibrary.PI_BB_SER_NORMAL}
    }


}
