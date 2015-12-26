package rxgpio.akka

import akka.actor._
import com.typesafe.config.Config
import rxgpio.akka.Device.{InstallDevice, DeviceInstallFailed, DeviceInstalled}
import gpio4s.gpiocfg.CfgIO._

import scala.collection.mutable


/**
 * Service as an interface to GPIO
 */
class GpioService(m: GpioInfo, pp: PinProducer) extends Actor {
    import GpioService._

    val gpios: PinAllocation = produceGpios(m, pp)
    val subscribers: SubscriberList = subscriberList()


    //!\\ do not forward events - outsiders cannot touch PinRefs //!\\
    def receive: Actor.Receive = {
        case Configure(c) => configure(gpios, c)

        case Subscribe(p) => subscribers.addBinding(p, sender())
        case Unsubscribe(p) => subscribers.removeBinding(p, sender())

        case m @ DigitalRead(p) => gpios(p) ! m
        case m @ DigitalWrite(p, _) => gpios(p) ! m
        case e @ DigitalEvent(p, v) => subscribers(p).foreach(_ ! e)

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

object GpioService {
    def apply(m: GpioInfo, f: PinProducer)(implicit sys: ActorSystem): ActorRef = sys.actorOf(Props(new GpioService(m, f)))

    def produceGpios(model: GpioInfo, pp: PinProducer)(implicit ctx: ActorContext): PinAllocation = model.pins.map(p => p -> pp.get(p)).toMap
    def configure(gpios: PinAllocation, conf: Config, reset: Boolean = false) = conf.pins().foreach(p => gpios(p.num) ! Setup(p))

    private type SubscriberList = mutable.HashMap[Int, mutable.Set[PinRef]] with mutable.MultiMap[Int, PinRef]
    private def subscriberList() = new mutable.HashMap[Int, mutable.Set[PinRef]] with mutable.MultiMap[Int, PinRef]
}
