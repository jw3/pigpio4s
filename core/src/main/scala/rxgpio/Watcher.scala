package rxgpio

import rxgpio.PigpioLibrary.gpioAlertFunc_t
import rx.lang.scala.Observable
import rx.lang.scala.subjects.PublishSubject

import scala.util.control.NonFatal


case class GpioAlert(gpio: Gpio, level: Level, tick: Long)

sealed trait RxGpio extends gpioAlertFunc_t {
    private[rxgpio] val subject = PublishSubject[GpioAlert]

    final def callback(gpio: Int, level: Int, tick: Int /*UINT32*/): Unit = {
        try subject.onNext(GpioAlert(Gpio(gpio), Level(level), Ticks.asUint(tick)))
        catch {
            case NonFatal(e) => subject.onError(e)
        }
    }
}

object RxGpio {
    private val map: Map[Int, RxGpio] = (for (i <- 0 to 10) yield {i -> new RxGpio {}}).toMap

    def install(num: Int, fn: (Int, gpioAlertFunc_t) => Int) = {
        require(map.contains(num), s"unsupported pin, $num")
        fn(num, map(num))
    }

    def apply(num: Int): Observable[GpioAlert] = {
        require(map.contains(num), s"unsupported pin, $num")
        Observable(o => map(num).subject.subscribe(o))
    }
}

