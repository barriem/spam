package org.bcm.spam.actor

import akka.actor._

import org.bcm.spam.payload.action.BecomeUser
import org.bcm.spam.payload.model.{Message, User}

class UserActor extends Actor {
  var user: User = _

  def receive = {
  	case bu: BecomeUser => {
  		val u = User(bu.id, self)
  		this.user = u
  		sender ! u
  	}
    case m: Message => println(s"${m.fromId}: ${m.content}")
  }
}