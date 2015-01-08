package org.bcm.spam.payload.model

import java.util.Date

import akka.actor.ActorRef

case class User(id: String, ref: ActorRef)
case class Input(line: String)
case class Output(content: String)
case class Message(fromId: String, content: String)
case class Presence(user: User, lastUpdate: Date, status: String)
