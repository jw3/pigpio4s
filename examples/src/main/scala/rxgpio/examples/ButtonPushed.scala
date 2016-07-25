package rxgpio.examples

import rxgpio.pigpio.PigpioLibrary
import rxgpio.{GpioAlert, _}

import scala.io.StdIn
import scala.util.Success

/**
 *
 */
object ButtonPushed extends App {
  import rxgpio.Gpio.Implicits._

  DefaultInitializer.gpioInitialise() match {
    case Success(InitOK(ver)) =>
      println(s"initialized pigpio:$ver")
    case _ =>
      println("failed")
      System.exit(1)
  }
  implicit val pigpio = PigpioLibrary.Instance

  val l = new Listener

  RxGpio.installAll()
  DefaultDigitalIO.gpioSetMode(1, InputPin)
  RxGpio(1).map(_.tick).subscribe(l.tick(_))

  println("Press Enter to exit")
  StdIn.readLine()
}


class Listener {
  var last = 0L
  def tick(a: Long) = {
    println(s"received GpioAlert(${a}) -> diff ${a - last}")
    last = a
  }
}
