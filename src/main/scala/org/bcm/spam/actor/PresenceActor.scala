package org.bcm.spam.actor

import scala.collection.mutable
import java.util.Date

import akka.actor._

import org.bcm.spam.payload.action.{GetPresence, GetUsers, Register, Prune}
import org.bcm.spam.payload.model.{Presence, User}

class PrescenceActor extends Actor {
  val userIdsWithPresence = mutable.Map[String, Presence]()

  def receive = {
    case r: Register => {
      val userActor = context.actorOf(Props(classOf[UserActor], r.userId, r.outputActor))
      val user = User(r.userId, userActor)
      val presence = Presence(user, new Date(), "Online")
      broadcast(presence)
      userIdsWithPresence += user.id -> presence
      sender ! presence
    }
    case gp: GetPresence => sender ! userIdsWithPresence(gp.id)
    case GetUsers => {
      println(s"Returning Users: ${userIdsWithPresence.keys}")
      sender ! userIdsWithPresence.keys
    }
    case Prune => userIdsWithPresence --= userIdsWithPresence collect { case (u, p) if p.lastUpdate.before(tenMinutesAgo) => u }
    case _ => println("Presence actor received an unknown command.")
  }

  private def broadcast(presence: Presence) {
    userIdsWithPresence foreach { case (uid, p) => p.user.ref ! presence }
  }

  private def tenMinutesAgo: Date = {
  	val d = new Date
  	d.setMinutes(d.getMinutes - 10)
  	d
  }
}
