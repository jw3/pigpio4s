package pigpio4s

import org.scalatest.{Matchers, WordSpecLike}

/**
 *
 */
class DutyCycleModelingSpec extends WordSpecLike with Matchers {
    "duty cycle" should {
        "be equal when same in" in {DutyCycle.default shouldEqual DutyCycle.default}
        "throw IAE when lt min" in {intercept[IllegalArgumentException](DutyCycle(DutyCycle.range.min - 1))}
        "throw IAE when gt max" in {intercept[IllegalArgumentException](DutyCycle(DutyCycle.range.max + 1))}
    }

}
