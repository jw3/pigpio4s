package pigpio4s

import org.scalatest.{FlatSpecLike, Matchers}

/**
 *
 */
class DefaultBeginnerImplSpec extends FlatSpecLike with Matchers {
    "gpios of same pin" should "be equal" in {Gpio(0) shouldBe Gpio(0)}

}
