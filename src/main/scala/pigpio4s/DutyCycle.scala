package pigpio4s

import pigpio4s.{PigpioLibrary => lib}

sealed trait DutyCycle {
    def value: Int
}

object DutyCycle {
    val range = Range(lib.PI_MIN_DUTYCYCLE_RANGE, lib.PI_MAX_DUTYCYCLE_RANGE)
    val default = DutyCycle(lib.PI_DEFAULT_DUTYCYCLE_RANGE)

    def apply(): DutyCycle = default
    def apply(dc: Int): DutyCycle = {
        require(range.contains(dc), "out of range")
        new DutyCycle {val value = dc}
    }
}
