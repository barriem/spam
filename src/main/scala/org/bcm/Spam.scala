package org.bcm.spam

import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.util.Timeout

import org.bcm.spam.actor.{PrescenceActor, RouterActor}

object Spam extends App {
  implicit val timeout = Timeout(5, TimeUnit.SECONDS)

  val system = ActorSystem("Spam")
  system.actorOf(Props[PrescenceActor], "presence")
  system.actorOf(Props[RouterActor], "router")
}