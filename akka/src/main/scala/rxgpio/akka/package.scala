package rxgpio

import _root_.akka.actor.ActorRef

package object akka {
    type PinRef = ActorRef
    type PinMap = Map[Int, PinRef]
}
