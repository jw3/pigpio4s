package pigpio4s

import org.scalatest.{Matchers, WordSpecLike}

/**
 *
 */
class ModelingDigitalValueSpec extends WordSpecLike with Matchers {
    "digital value" should {
        "be high when true" in {DigitalValue(true) shouldBe High}
        "be low when false" in {DigitalValue(false) shouldBe Low}
        "be high when 1" in {DigitalValue(1) shouldBe High}
        "be low when 0" in {DigitalValue(0) shouldBe Low}
        "throw when not a 1 or 0" in {
            intercept[BadLevel] {DigitalValue(2)}
            intercept[BadLevel] {DigitalValue(-1)}
            intercept[BadLevel] {DigitalValue(999)}
            intercept[BadLevel] {DigitalValue(-999)}
        }
    }
}