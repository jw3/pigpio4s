package pi4s.examples

import pigpio4s._

import scala.io.StdIn
import scala.util.{Failure, Success}

/**
 *
 */
object ButtonPushed extends App {
    import pigpio4s.Gpio.Implicits._

    DefaultInitializer.gpioInitialise() match {
        case Success(InitOK(ver)) =>
            println(s"initialized pigpio:$ver")
        case _ =>
            println("failed")
            System.exit(1)
    }
    implicit val pigpio = PigpioLibrary.Instance

    DefaultDigitalIO.gpioSetMode(1, InputPin)
    DefaultDigitalIO.gpioSetAlertFunc(1, new Watcher) match {
        case Success(x) => println("added watch func")
        case Failure(x) => println(s"watch add failure. $x")
    }

    println("Press Enter to exit")
    StdIn.readLine()
}

class Watcher extends GpioWatcher {
    def onSuccess(alert: GpioAlert): Unit = println(s"alert @ tick(${alert.tick})")
}