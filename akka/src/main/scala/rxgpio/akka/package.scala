package rxgpio

import _root_.akka.actor.ActorRef

package object akka {
    type PinRef = ActorRef
    type PinAllocation = Map[Int, PinRef]
}
