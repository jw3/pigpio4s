package rxgpio

import rxgpio.pigpio.PigpioLibrary

import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

/**
 *
 */
trait Initializer {
    /**
     * Initialises the library.  Call before using the other library functions.
     * Returns the pigpio version number if OK, otherwise PI_INIT_FAILED.
     * The only exception is the optional [[GpioCfg]] functions.
     */
    def gpioInitialise(): Try[InitResult]

    /**
     * Terminates the library.  Call before program exit.
     * Resets the used DMA channels, releases memory, and terminates any running threads.
     */
    def gpioTerminate(): Unit
}

object DefaultInitializer extends Initializer {
    private val pigpio = PigpioLibrary.Instance

    def gpioInitialise(): Try[InitResult] = {
        try Success(InitResult(pigpio.gpioInitialise()))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }
    def gpioTerminate(): Unit = pigpio.gpioTerminate()
}
