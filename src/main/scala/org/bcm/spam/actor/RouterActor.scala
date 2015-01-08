package org.bcm.spam.actor

import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.TimeUnit

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout

import org.bcm.spam.payload.action.{GetPresence, RoutingRequest}
import org.bcm.spam.payload.model.{Message, Presence}

class RouterActor extends Actor {

  implicit val timeout = Timeout(10, TimeUnit.SECONDS)

  def receive = {
  	case rr: RoutingRequest => {
      val presence = context.actorSelection("/user/presence")
  		presence ? GetPresence(rr.toId) onSuccess {
  			case userPresence: Presence => {
          // Only send message if user has presence
          userPresence.status match {
            case "Online" => userPresence.user.ref ! Message(rr.fromId, rr.content)
            case _ => println(s"Presence is unknown for user ${rr.toId}")
          }
        }
  		}
  	}
  }
}
