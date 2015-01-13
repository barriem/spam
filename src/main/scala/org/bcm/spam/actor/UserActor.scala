package org.bcm.spam.actor

import scala.collection.mutable
import scala.io.Source

import akka.actor._

import org.bcm.spam.payload.action.RoutingRequest
import org.bcm.spam.payload.model.{Input, Message, Output, Presence}

class UserActor(userId: String, outputActor: ActorRef) extends Actor {

  def receive = {
    case i: Input => {
      val router = context.actorSelection("/user/router")
      router ! RoutingRequest(userId, i.toId, i.line)
    }
    case m: Message => outputActor ! Output(s"${m.fromId}: ${m.content}")
    case p: Presence => outputActor ! Output(s"${p.user.id} is now ${p.status}")
  }
}
