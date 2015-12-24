package pi4s.examples

import pigpio4s._

import scala.io.StdIn
import scala.util.Success

/**
 *
 */
object ButtonPushed extends App {
    import pigpio4s.Gpio.Implicits._

    DefaultInitializer.gpioInitialise() match {
        case Success(InitOK(ver)) =>
            println(s"initialized pigpio:$ver")
            RxGpio.install(1, PigpioLibrary.Instance.gpioSetAlertFunc)
        case _ =>
            println("failed")
            System.exit(1)
    }
    implicit val pigpio = PigpioLibrary.Instance

    DefaultDigitalIO.gpioSetMode(1, InputPin)
    RxGpio(1).map(_.tick).subscribe(tick => println(s"alert @ tick(${tick})"))

    println("Press Enter to exit")
    StdIn.readLine()
}
