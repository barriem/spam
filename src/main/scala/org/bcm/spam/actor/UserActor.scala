package org.bcm.spam.actor

import akka.actor._

import org.bcm.spam.payload.model.Message

class UserActor extends Actor {

  def receive = {
    case m: Message => println(s"${m.fromId}: ${m.content}")
  }
}