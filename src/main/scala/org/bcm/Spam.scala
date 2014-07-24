package org.bcm.spam

import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import org.bcm.spam.actor.{PrescenceActor, RouterActor, UserActor}
import org.bcm.spam.payload.action.{BecomeUser, RoutingRequest}

object Spam extends App {
  implicit val timeout = Timeout(5, TimeUnit.SECONDS)

  val system = ActorSystem("Spam")
  val presence = system.actorOf(Props[PrescenceActor], "presence")
  val router = system.actorOf(Props[RouterActor], "router")

  spawnUser("Dave", presence)
  spawnUser("Bob", presence)

  router ! RoutingRequest("Dave", "Bob", "Hey Bob, how's it going?", presence)
  router ! RoutingRequest("Dave", "Bob", "Are you there?", presence)
  router ! RoutingRequest("Bob", "Dave", "Sure!!", presence)

//  system.shutdown()

  def spawnUser(id: String, presence: ActorRef) {
    val actor = system.actorOf(Props[UserActor])
    actor ? BecomeUser(id) onSuccess {
      case user => presence ! user
    }
  }
}

