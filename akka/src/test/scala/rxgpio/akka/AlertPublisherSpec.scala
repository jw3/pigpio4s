package rxgpio.akka

import akka.actor.{Props, ActorSystem}
import akka.stream.testkit.scaladsl.TestSink
import akka.stream.{ThrottleMode, ActorMaterializer}
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.testkit.{TestActorRef, TestKit, ImplicitSender}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{WordSpecLike, Matchers}
import rx.lang.scala.Observable
import rxgpio.{Levels, GpioAlert}
import scala.concurrent.Await
import scala.concurrent.duration.{Duration, DurationInt}

/**
 *
 */
class AlertPublisherSpec extends TestKit(ActorSystem(getClass.getSimpleName.dropRight(1)))
                                 with ImplicitSender with WordSpecLike with Matchers with MockFactory {

    implicit val mat = ActorMaterializer()

    "publisher" should {
        "stream from observer" in {
            val alerts = Observable.from(1 to 10).map(v => GpioAlert(v, v % 2, System.currentTimeMillis().toInt))
            val source = Source.actorPublisher(Props(classOf[AlertPublisher], alerts))
            //val flow = Flow[GpioAlert].map(a => s"${a.gpio.value} ${a.level.getClass.getSimpleName}").throttle(1, 1 second, 1, ThrottleMode.Shaping)

            val flow = Flow[GpioAlert].map(_.gpio.value)

            source.via(flow).runWith(TestSink.probe[Int])
            .request(5)
            .expectNext(1, 2, 3, 4, 5)
            .request(5)
            .expectNext(6, 7, 8, 9, 10)
        }
    }
}
