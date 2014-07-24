package org.bcm.spam.payload.action

import akka.actor.ActorRef

case class RegisterUser(id: String, ref: ActorRef)
case object GetUsers
case object Prune
case class GetPresence(id: String)
case class RoutingRequest(fromId: String, toId: String, content: String)