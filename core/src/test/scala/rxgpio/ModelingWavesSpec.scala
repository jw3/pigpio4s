package rxgpio

import org.scalatest.{Matchers, WordSpecLike}
import rxgpio.pigpio.PigpioLibrary

/**
 *
 */
class ModelingWavesSpec extends WordSpecLike with Matchers {
    "WaveModes" should {
        "be accurate to constants" in {
            WaveModes.once.value shouldBe PigpioLibrary.PI_WAVE_MODE_ONE_SHOT
            WaveModes.repeat.value shouldBe PigpioLibrary.PI_WAVE_MODE_REPEAT
        }
    }

}
