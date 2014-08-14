package org.bcm.spam.payload.model

import java.util.Date

import akka.actor.ActorRef

case class Message(fromId: String, content: String)
case class Presence(userId: String, lastUpdate: Date, status: String, ref: ActorRef)