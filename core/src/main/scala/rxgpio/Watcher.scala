package rxgpio

import rx.lang.scala.Observable
import rx.lang.scala.subjects.PublishSubject
import rxgpio.PigpioLibrary.gpioAlertFunc_t

import scala.collection.mutable
import scala.util.control.NonFatal


case class GpioAlert(gpio: Gpio, level: Level, tick: Long)

object RxGpio {
    private val rxpins = mutable.Map[Int, RxGpio]()

    def install(num: Int, fn: (Int, gpioAlertFunc_t) => Int) = {
        fn(num, rxpins.getOrElseUpdate(num, new RxGpio))
    }

    def apply(num: Int): Observable[GpioAlert] = {
        require(rxpins.contains(num), s"invalid pin, $num")
        Observable(o => rxpins(num).subject.subscribe(o))
    }
}

private class RxGpio extends gpioAlertFunc_t {
    private[rxgpio] val subject = PublishSubject[GpioAlert]

    final def callback(gpio: Int, level: Int, tick: Int /*UINT32*/): Unit = {
        try subject.onNext(GpioAlert(Gpio(gpio), Level(level), Ticks.asUint(tick)))
        catch {
            case NonFatal(e) => subject.onError(e)
        }
    }
}