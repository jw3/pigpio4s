package rxgpio

import org.scalatest.{Matchers, WordSpecLike}
import rxgpio.{PigpioLibrary => lib}

/**
 *
 */
class ModelingPullUpDownSpec extends WordSpecLike with Matchers {
    "pull up/down" should {
        "match lib value for PullUp" in {PullUp.value shouldBe lib.PI_PUD_UP}
        "match lib value for PullDown" in {PullDown.value shouldBe lib.PI_PUD_DOWN}
        "match lib value for DontPull" in {DontPull.value shouldBe lib.PI_PUD_OFF}
        "pull down" in {GpioPull(lib.PI_PUD_UP) shouldBe PullUp}
        "pull up" in {GpioPull(lib.PI_PUD_DOWN) shouldBe PullDown}
        "not pull" in {GpioPull(lib.PI_PUD_OFF) shouldBe DontPull}
        "throw when invalid" in {intercept[BadPull] {GpioPull(badValue)}}
    }

    def badValue = lib.PI_PUD_DOWN + lib.PI_PUD_UP + lib.PI_PUD_OFF
}
