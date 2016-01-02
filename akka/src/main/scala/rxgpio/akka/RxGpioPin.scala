package rxgpio.akka

import akka.actor.Actor
import gpio4s.gpiocfg.CfgIO.PinCfg
import rx.lang.scala.Subscription
import rxgpio.DefaultDigitalIO._
import rxgpio.Gpio.Implicits._
import rxgpio.pigpio.PigpioLibrary
import rxgpio.{Level, PinModes, RxGpio}
import wiii.inject._

class RxGpioPin(num: Int) extends Actor {
    implicit val pigpio: PigpioLibrary = Inject[PigpioLibrary]
    val in = RxGpio(num)
    var subs: Subscription = _

    def digitalIn: Receive = {
        case DigitalRead(_) => sender() ! gpioRead(num)
        case Reset(_) => reset()
    }

    def digitalOut: Receive = {
        case DigitalWrite(_, s) => gpioWrite(num, Level(s))
        case Reset(_) => reset()
    }

    def receive: Receive = {
        case Setup(p) => setup(p)
    }

    def setup(pin: PinCfg) = {
        pin.mode match {
            case digital if pin.dir.isInput => {
                subs = in.map(e => DigitalEvent(num, e.level.toBoolean)).subscribe(context.parent ! _)
                gpioSetMode(num, PinModes.input)
                context.become(digitalIn)
            }
            case digital if pin.dir.isOutput => {
                gpioSetMode(num, PinModes.output)
                context.become(digitalOut)
            }
        }
    }

    def reset(): Unit = {
        subs.unsubscribe()
        context.become(receive)
    }
}
