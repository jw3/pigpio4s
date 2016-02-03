package rxgpio.akka

import java.util.UUID

import akka.actor._
import com.typesafe.config.Config
import gpio4s.gpiocfg.CfgIO._
import rx.lang.scala.Subscription
import rx.lang.scala.subjects.PublishSubject
import rxgpio.akka.Device.{DeviceInstallFailed, DeviceInstalled, InstallDevice}

import scala.collection.mutable


/**
 * GPIO as a Service
 */
object GpioService {
    def apply(m: GpioInfo, f: PinProducer)(implicit sys: ActorSystem): ActorRef = sys.actorOf(Props(new GpioService(m, f)))

    def produceGpios(model: GpioInfo, pp: PinProducer)(implicit ctx: ActorContext): PinMap = model.pins.map(p => p -> pp.get(p)).toMap
    def configure(gpios: PinMap, conf: Config, reset: Boolean = false) = conf.pins().foreach(p => gpios(p.num) ! Setup(p))
    def random() = UUID.randomUUID.toString
}

class GpioService(m: GpioInfo, pp: PinProducer = DefaultPinProducer) extends Actor {
    import GpioService._

    val gpios: PinMap = produceGpios(m, pp)
    val subscribers = PublishSubject[DigitalEvent]
    val subscriptions = mutable.Map[String, Subscription]()


    //!\\ do not forward events - outsiders cannot touch PinRefs //!\\
    def receive: Actor.Receive = {
        case Configure(c) => configure(gpios, c)
        case Subscribe(p) =>
            val s = sender()
            val sub = subscribers.filter(_.pin == p).subscribe(s ! _)
            val id = random()
            subscriptions(id) = sub
            s ! id


        case m @ DigitalRead(p) => gpios(p) ! m
        case m @ DigitalWrite(p, _) => gpios(p) ! m
        case e @ DigitalEvent(p, v) => subscribers.onNext(e)

        case m @ InstallDevice(info) => sender() ! installDevice(info)

        case _ =>
    }

    // todo validate the requested pin exists
    // todo validate the pins are not locked
    // todo   - a locked pin has restricted config
    // todo   - an unlocked pin can have its config changed
    def installDevice(info: DeviceInfo) = {
        try {
            configure(gpios, info.conf)
            val dev = context.actorOf(info.props(gpios))
            DeviceInstalled(info.id, dev)
        }
        catch {
            case t: Throwable =>
                DeviceInstallFailed(info.id, t)
        }
    }
}
