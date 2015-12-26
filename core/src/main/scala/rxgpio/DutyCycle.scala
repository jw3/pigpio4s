package rxgpio

import rxgpio.pigpio.{PigpioLibrary => lib}

sealed trait DutyCycle {
    def value: Int
}

object DutyCycle {
    def apply(dc: Int): DutyCycle = {
        // revisit;; the max value here needs validated against a global property which is set with gpioSetPWMrange
        // revisit;; there is no great way to access that setting from this point so this only be partial valiation
        if (dc != 0 && dc < lib.PI_MIN_DUTYCYCLE_RANGE || dc > lib.PI_MAX_DUTYCYCLE_RANGE) throw new BadDutyCycle()
        new DutyCycle {val value = dc}
    }
}


sealed trait DutyCycleRange {
    def value: Range
}

object DutyCycleRange {
    val bounds = Range(lib.PI_MIN_DUTYCYCLE_RANGE, lib.PI_MAX_DUTYCYCLE_RANGE)
    val default = DutyCycleRange(lib.PI_DEFAULT_DUTYCYCLE_RANGE)

    def apply(): DutyCycleRange = default
    def apply(upper: Int) = {
        if (!bounds.contains(upper)) throw BadDutyCycleRange()
        new DutyCycleRange {val value = Range(bounds.min, upper + 1)}
    }
}