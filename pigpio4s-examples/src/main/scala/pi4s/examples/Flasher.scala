package pi4s.examples

import pigpio4s._

import scala.util.Success


/**
 *
 */
object Flasher extends App {
    import pigpio4s.Gpio.Implicits._

    DefaultInitializer.gpioInitialise() match {
        case Success(InitOK(ver)) =>
            println(s"initialized pigpio:$ver")
        case _ =>
            println("failed")
            System.exit(1)
    }

    IO.gpioSetMode(1, OutputPin)

    for (i <- 1 to 100) {
        IO.gpioWrite(1, High)
        Thread.sleep(250)
        IO.gpioWrite(1, Low)
        Thread.sleep(250)
    }
    IO.gpioWrite(1, Low)
}

// revisit;; huh?
object IO extends DefaultBeginner {
    def pigpio: PigpioLibrary = PigpioLibrary.Instance
}

