package rxgpio

import rx.lang.scala.Observable
import rx.lang.scala.subjects.PublishSubject
import rxgpio.pigpio.PigpioLibrary
import rxgpio.pigpio.PigpioLibrary.gpioAlertFunc_t

import scala.util.control.NonFatal


sealed trait GpioAlert {
    def gpio: UserGpio
    def level: Level
    def tick: Long
}

object GpioAlert {
    def apply(user_gpio: Int, gpio_level: Int, microtick: Int /*UINT32*/) = {
        new GpioAlert {
            lazy val gpio = UserGpio(user_gpio)
            lazy val level = Level(gpio_level)
            lazy val tick = Ticks.asUint(microtick)
        }
    }
}

object RxGpio {
    private val rxpins = (0 to PigpioLibrary.PI_MAX_USER_GPIO).map(_ -> new RxGpio).toMap

    def apply(num: Int): Observable[GpioAlert] = {
        require(rxpins.contains(num), s"invalid pin, $num")
        Observable(o => rxpins(num).subject.subscribe(o))
    }
}

private class RxGpio extends gpioAlertFunc_t {
    private[rxgpio] val subject = PublishSubject[GpioAlert]

    final def callback(gpio: Int, level: Int, tick: Int /*UINT32*/): Unit = {
        try subject.onNext(GpioAlert(gpio, level, tick))
        catch {
            case NonFatal(e) => subject.onError(e)
        }
    }
}