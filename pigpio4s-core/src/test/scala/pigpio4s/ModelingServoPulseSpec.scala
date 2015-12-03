package pigpio4s

import org.scalatest.{Matchers, WordSpecLike}
import pigpio4s.{PigpioLibrary => lib}

/**
 *
 */
class ModelingServoPulseSpec extends WordSpecLike with Matchers {
    "servo pulse" should {
        "match lib value when off" in {ServoPulseOff.value shouldBe lib.PI_SERVO_OFF}
        "be off" in {ServoPulseWidth(lib.PI_SERVO_OFF) shouldBe ServoPulseOff}
        "be on when in range" in {ServoPulseWidth.range.forall(v => isOn(ServoPulseWidth(v)))}
        "throw when invalid" in {intercept[BadPulseWidth] {ServoPulseWidth(badValue)}}
    }

    def isOn(pw: ServoPulseWidth) = pw.isInstanceOf[ServoPulseOn]
    def badValue = ServoPulseWidth.range.min - 1
}
