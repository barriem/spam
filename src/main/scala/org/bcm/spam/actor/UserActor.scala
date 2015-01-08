package org.bcm.spam.actor

import scala.io.Source

import akka.actor._

import org.bcm.spam.payload.action.RoutingRequest
import org.bcm.spam.payload.model.{Input, Message, Output, Presence}

class UserActor(userId: String, outputActor: ActorRef) extends Actor {

  val router = context.actorSelection("/user/router")

  var chattingTo: String = _

  def receive = {
    case i: Input => { router ! RoutingRequest(userId, chattingTo, i.line) }
    case m: Message => {
      nowChattingTo(m.fromId)
      outputActor ! Output(s"${m.fromId}: ${m.content}")
    }
    case p: Presence => outputActor ! Output(s"${p.user.id} is now ${p.status}")
  }

  def nowChattingTo(toUserId: String) {
    if (chattingTo != toUserId) {
      outputActor ! Output(s"Now chatting to $toUserId")
      chattingTo = toUserId
    }
  }
}
