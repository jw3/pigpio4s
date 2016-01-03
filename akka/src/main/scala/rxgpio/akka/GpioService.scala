package rxgpio.akka

import akka.actor._
import com.typesafe.config.Config
import gpio4s.gpiocfg.CfgIO._
import rx.lang.scala.subjects.PublishSubject
import rxgpio.akka.Device.{DeviceInstallFailed, DeviceInstalled, InstallDevice}


/**
 * Service as an interface to GPIO
 */
class GpioService(m: GpioInfo, pp: PinProducer = DefaultPinProducer) extends Actor {
    import GpioService._

    val gpios: PinAllocation = produceGpios(m, pp)
    val subscribers = PublishSubject[DigitalEvent]


    //!\\ do not forward events - outsiders cannot touch PinRefs //!\\
    def receive: Actor.Receive = {
        case Configure(c) => configure(gpios, c)
        case Subscribe(p) =>
            val s = sender()
            s ! subscribers.filter(_.pin == p).subscribe(s ! _)

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

object GpioService {
    def apply(m: GpioInfo, f: PinProducer)(implicit sys: ActorSystem): ActorRef = sys.actorOf(Props(new GpioService(m, f)))

    def produceGpios(model: GpioInfo, pp: PinProducer)(implicit ctx: ActorContext): PinAllocation = model.pins.map(p => p -> pp.get(p)).toMap
    def configure(gpios: PinAllocation, conf: Config, reset: Boolean = false) = conf.pins().foreach(p => gpios(p.num) ! Setup(p))
}
