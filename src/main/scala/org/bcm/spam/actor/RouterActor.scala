package org.bcm.spam.actor

import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.TimeUnit

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout

import org.bcm.spam.payload.action.{GetPresence, RoutingRequest}
import org.bcm.spam.payload.model.{Message, Presence}

class RouterActor extends Actor {
  implicit val timeout = Timeout(30, TimeUnit.SECONDS)

  def receive = {
  	case rr: RoutingRequest => {
      val presence = context.actorSelection("/user/presence")
  		presence ? GetPresence(rr.toId) onSuccess {
  			case p: Option[Presence] => p foreach { _.user.ref ! Message(rr.fromId, rr.content) }
  		}
  	}
  }
}
