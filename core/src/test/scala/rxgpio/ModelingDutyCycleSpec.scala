package rxgpio

import org.scalatest.{Matchers, WordSpecLike}
import rxgpio.pigpio.PigpioLibrary

/**
 *
 */
class ModelingDutyCycleSpec extends WordSpecLike with Matchers {
    "duty cycle" should {
        "throw when lt 0" in {intercept[BadDutyCycle](DutyCycle(-1))}
    }
    "duty cycle range" should {
        "have default upper bounds" in {DutyCycleRange().value.max shouldBe PigpioLibrary.PI_DEFAULT_DUTYCYCLE_RANGE}
        "be equal when same in" in {DutyCycleRange.default shouldEqual DutyCycleRange.default}
        "throw when lt min" in {intercept[BadDutyCycleRange](DutyCycleRange(DutyCycleRange.bounds.min - 1))}
        "throw when gt max" in {intercept[BadDutyCycleRange](DutyCycleRange(DutyCycleRange.bounds.max + 1))}
    }
}
