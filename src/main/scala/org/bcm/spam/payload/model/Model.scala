package org.bcm.spam.payload.model

import java.util.Date

import akka.actor.ActorRef

case class Message(fromId: String, content: String)
case class Presence(lastUpdate: Date, actorRef: ActorRef)
case class User(id: String, ref: ActorRef)