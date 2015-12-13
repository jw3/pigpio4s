package pigpio4s

import pigpio4s.{PigpioLibrary => lib}

sealed trait DutyCycle {
    def value: Int
}

object DutyCycle {
    def apply(dc: Int, range: DutyCycleRange = DutyCycleRange.default): DutyCycle = {
        if (dc != 0 && !range.value.contains(dc)) throw new BadDutyCycle()
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