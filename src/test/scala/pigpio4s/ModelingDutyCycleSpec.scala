package pigpio4s

import org.scalatest.{Matchers, WordSpecLike}

/**
 *
 */
class ModelingDutyCycleSpec extends WordSpecLike with Matchers {
    "duty cycle" should {
        "throw when lt 0" in {intercept[BadDutyCycle](DutyCycle(-1))}
        "throw when gt default range" in {intercept[BadDutyCycle](DutyCycle(DutyCycleRange.default.value.max + 1))}
        "throw when gt range specified" in {intercept[BadDutyCycle](DutyCycle(DutyCycleRange.bounds.min + 1, DutyCycleRange(DutyCycleRange.bounds.min)))}
    }
    "duty cycle range" should {
        "have default upper bounds" in {DutyCycleRange().value.max shouldBe PigpioLibrary.PI_DEFAULT_DUTYCYCLE_RANGE}
        "be equal when same in" in {DutyCycleRange.default shouldEqual DutyCycleRange.default}
        "throw when lt min" in {intercept[BadDutyCycleRange](DutyCycleRange(DutyCycleRange.bounds.min - 1))}
        "throw when gt max" in {intercept[BadDutyCycleRange](DutyCycleRange(DutyCycleRange.bounds.max + 1))}
    }
}
