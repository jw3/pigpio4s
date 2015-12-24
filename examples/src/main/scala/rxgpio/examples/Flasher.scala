package rxgpio.examples

import rxgpio._

import scala.util.Success


/**
 *
 */
object Flasher extends App {
    import rxgpio.Gpio.Implicits._

    DefaultInitializer.gpioInitialise() match {
        case Success(InitOK(ver)) =>
            println(s"initialized pigpio:$ver")
        case _ =>
            println("failed")
            System.exit(1)
    }
    implicit val pigpio = PigpioLibrary.Instance

    DefaultDigitalIO.gpioSetMode(1, OutputPin)

    for (i <- 1 to 100) {
        DefaultDigitalIO.gpioWrite(1, High)
        Thread.sleep(250)
        DefaultDigitalIO.gpioWrite(1, Low)
        Thread.sleep(250)
    }
    DefaultDigitalIO.gpioWrite(1, Low)
}
