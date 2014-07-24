package org.bcm.spam.actor

import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.TimeUnit

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout

import org.bcm.spam.payload.action.{GetPresence, RoutingRequest}
import org.bcm.spam.payload.model.{Message, Presence, User}

class RouterActor extends Actor {

  implicit val timeout = Timeout(10, TimeUnit.SECONDS)

  def receive = {
  	case rr: RoutingRequest => {
  		rr.presence ? GetPresence(rr.toId) onSuccess {
  			case userPresence: Option[Presence] => {
  				userPresence map { _.actorRef ! Message(rr.fromId, rr.content) }
  			}
  		}
  	}
  }
}