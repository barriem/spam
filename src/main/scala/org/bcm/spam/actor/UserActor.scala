package org.bcm.spam.actor

import akka.actor._

import org.bcm.spam.payload.model.{Message, Presence}

class UserActor extends Actor {

  def receive = {
    case m: Message => println(s"${m.fromId}: ${m.content}")
    case p: Presence => println(s"${p.userId} is now ${p.status}")
  }
}