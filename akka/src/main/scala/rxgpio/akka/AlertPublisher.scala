package rxgpio.akka

import akka.actor.{ActorLogging, Props}
import akka.stream.actor.ActorPublisher
import rx.lang.scala.{Observable, Subscription}
import rxgpio.{GpioAlert, RxGpio}

import scala.annotation.tailrec

/**
 *
 */
object AlertPublisher {
    def props(pin: Int) = Props(new AlertPublisher(RxGpio(pin)))
}

class AlertPublisher(gpio: Observable[GpioAlert]) extends ActorPublisher[GpioAlert] with ActorLogging {
    import akka.stream.actor.ActorPublisherMessage._

    var buffer = Vector.empty[GpioAlert]
    var subscription: Option[Subscription] = None

    def receive: Receive = {
        case Request(_) =>
            deliver()

        case Cancel =>
            subscription.foreach(_.unsubscribe())
            context.stop(self)
    }

    private def route(a: GpioAlert) = {
        if (buffer.isEmpty && totalDemand > 0) {
            log.debug("direct routing alert")
            onNext(a)
        }
        else {
            log.debug("buffering alert, buf@[{}]", buffer.size)
            buffer :+= a
            deliver()
        }
    }

    @tailrec
    private def deliver(): Unit = {
        if (totalDemand > 0) {
            if (totalDemand <= Int.MaxValue) {
                val (use, keep) = buffer.splitAt(totalDemand.toInt)
                buffer = keep
                use foreach onNext
            } else {
                val (use, keep) = buffer.splitAt(Int.MaxValue)
                buffer = keep
                use foreach onNext
                deliver()
            }
        }
    }

    override def preStart(): Unit = {
        subscription = Option(gpio.subscribe(route(_)))
    }
}
