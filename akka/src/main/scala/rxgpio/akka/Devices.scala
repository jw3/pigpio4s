package rxgpio.akka

import akka.actor.{Props, ActorRef, Actor}
import com.typesafe.config.Config


/**
 * Tagging interface that identifies a Device Actor
 */
trait Device extends Actor

object Device {
    sealed trait DeviceMessage
    case class InstallDevice(info: DeviceInfo) extends DeviceMessage
    case class DeviceInstalled(id: String, actorRef: ActorRef) extends DeviceMessage
    case class DeviceInstallFailed(id: String, e: Throwable) extends DeviceMessage
}

/**
 * Information about a device
 */
trait DeviceInfo {
    def id: String
    def conf: Config
    def impl: Class[_ <: Device]

    final def props(pins: PinMap): Props = Props(impl, id, conf, pins)
}
