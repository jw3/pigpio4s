package rxgpio

import org.scalatest.{Matchers, WordSpecLike}
import rxgpio.{PigpioLibrary => lib}

/**
 *
 */
class ModelingPinModeSpec extends WordSpecLike with Matchers {
    "pin modes" should {
        "input pin should be PI_INPUT" in {InputPin.value shouldBe lib.PI_INPUT}
        "output pin should be PI_OUTPUT" in {OutputPin.value shouldBe lib.PI_OUTPUT}
        "throw BadMode on invalid mode" in {intercept[BadMode] {PinMode(badValue)}}
    }

    def badValue = 999
}
