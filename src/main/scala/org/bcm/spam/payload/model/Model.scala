package org.bcm.spam.payload.model

import akka.actor.ActorRef

case class User(id: String, ref: ActorRef)
case class Input(toId: String, line: String)
case class Output(content: String)
case class Message(fromId: String, content: String)
case class Presence(user: User, status: String)
