package rxgpio.akka

import com.typesafe.config.Config
import gpio4s.gpiocfg.CfgDSL.{PinNumberBuilder, _}
import gpio4s.gpiocfg.CfgIO._

// service events
case class Configure(conf: Config)
object Configure {
    def apply(fn: PinNumberBuilder => Unit): Configure = Configure(gpio(fn))
}
case class Subscribe(pin: Int)
case class Unsubscribe(pin: Int)

// pin events
case class DigitalWrite(pin: Int, state: Boolean)
case class DigitalRead(pin: Int)

trait ModeEvent
private[akka] case class Reset(pin: Int) extends ModeEvent
private[akka] case class Setup(pin: PinCfg) extends ModeEvent

// responses
case class DigitalEvent(pin: Int, state: Boolean)
