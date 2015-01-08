package org.bcm.spam.payload.action

import akka.actor.ActorRef

import org.bcm.spam.payload.model.User

case class Register(userId: String, outputActor: ActorRef)
case object GetUsers
case object Prune
case object Listen
case class GetPresence(id: String)
case class RoutingRequest(fromId: String, toId: String, content: String)
