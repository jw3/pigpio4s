package pigpio4s

import pigpio4s.PigpioLibrary.gpioAlertFunc_t

import scala.util.control.NonFatal


case class GpioAlert(gpio: Gpio, level: DigitalValue, tick: Int)

trait GpioWatcher extends gpioAlertFunc_t {
    def onSuccess(alert: GpioAlert): Unit
    def onFailure(t: Throwable): Unit = ()

    final def apply(gpio: Int, level: Int, tick: Int): Unit = {
        try onSuccess(GpioAlert(Gpio(gpio), DigitalValue(level), tick))
        catch {
            case NonFatal(e) => onFailure(e)
        }
    }
}
