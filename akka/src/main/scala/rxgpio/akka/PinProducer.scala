package rxgpio.akka

import akka.actor.{Props, ActorContext}

/**
 * Provider of [[PinRef]] for a given pin number
 */
trait PinProducer {
    def get(num: Int)(implicit ctx: ActorContext): PinRef
}


object DefaultPinProducer extends PinProducer {
    def get(num: Int)(implicit ctx: ActorContext): PinRef = {
        ctx.actorOf(Props(classOf[RxGpioPin], num))
    }
}
