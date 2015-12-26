package rxgpio.examples

import rx.lang.scala.Observable
import rxgpio._
import rxgpio.pigpio.PigpioLibrary

import scala.concurrent.duration.DurationInt
import scala.util.Success


/**
 *
 */
object Flasher extends App {
    import rxgpio.Gpio.Implicits._

    DefaultInitializer.gpioInitialise() match {
        case Success(InitOK(ver)) =>
            println(s"initialized pigpio:$ver")
            RxGpio.install(1, PigpioLibrary.Instance.gpioSetAlertFunc)
        case _ =>
            println("failed")
            System.exit(1)
    }
    implicit val pigpio = PigpioLibrary.Instance

    import DefaultDigitalIO._

    gpioSetMode(1, OutputPin)
    Observable.timer(0 seconds, 1 second).map(i => if (i % 2 == 0) High else Low).subscribe(gpioWrite(1, _))

    gpioWrite(1, Low)
}
