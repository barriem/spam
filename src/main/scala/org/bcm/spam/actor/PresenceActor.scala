package org.bcm.spam.actor

import scala.collection.mutable

import akka.actor._

import org.bcm.spam.payload.action.{GetPresence, GetUsers, Register, Unregister}
import org.bcm.spam.payload.model.{Presence, User}

class PrescenceActor extends Actor {
  val userIdsWithPresence = mutable.Map[String, Presence]()

  def receive = {
    case r: Register => {
      val userActor = context.actorOf(Props(classOf[UserActor], r.userId, r.outputActor))
      val user = User(r.userId, userActor)
      val presence = Presence(user, "Online")
      broadcast(presence)
      userIdsWithPresence += user.id -> presence
      sender ! presence
    }
    case u: Unregister => {
      userIdsWithPresence.get(u.userId) foreach { presence =>
        presence.user.ref ! PoisonPill
        userIdsWithPresence -= presence.user.id
        broadcast(presence.copy(status = "Offline"))
      }
    }
    case gp: GetPresence => sender ! userIdsWithPresence.get(gp.id)
    case GetUsers => {
      println(s"Returning Users: ${userIdsWithPresence.keys}")
      sender ! userIdsWithPresence.keys
    }
    case _ => println("Presence actor received an unknown command.")
  }

  private def broadcast(presence: Presence) {
    userIdsWithPresence foreach { case (uid, p) => p.user.ref ! presence }
  }
}
