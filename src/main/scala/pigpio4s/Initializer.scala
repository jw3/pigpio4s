package pigpio4s

/**
 *
 */
trait Initializer {
    def gpioInitialise(): Int
    def gpioTerminate(): Unit
}

trait DefaultInitializer extends Initializer {
    private val pigpio = PigpioLibrary.Instance

    def gpioInitialise(): Int = pigpio.gpioInitialise()
    def gpioTerminate(): Unit = pigpio.gpioTerminate()
}