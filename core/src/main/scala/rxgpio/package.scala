import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

package object rxgpio {
    def gpioResultFunction(f: => Int): Try[GpioResult] = {
        try Success(GpioResult(f))
        catch {
            case NonFatal(e) => Failure(e)
        }
    }
}
