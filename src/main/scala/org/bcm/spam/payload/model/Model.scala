package org.bcm.spam.payload.model

import java.util.Date

import akka.actor.ActorRef

case class Message(fromId: String, content: String)
case class Presence(lastUpdate: Date, status: String, ref: ActorRef)
case class User(id: String)