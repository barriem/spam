package org.bcm.spam.actor

import scala.collection.mutable
import java.util.Date

import akka.actor._

import org.bcm.spam.payload.action.{GetPresence, GetUsers, Prune}
import org.bcm.spam.payload.model.{Presence, User}

class PrescenceActor extends Actor {
  val usersWithPresence = mutable.Map[String, Presence]()

  def receive = {
    case u: User => {
    	println(s"${u.id} is now online")
    	usersWithPresence += u.id -> Presence(new Date(), u.ref)
    }
    case gp: GetPresence => sender ! Option(usersWithPresence(gp.id))
    case GetUsers => sender ! usersWithPresence.keys
    case Prune => usersWithPresence --= usersWithPresence collect { case (u, p) if p.lastUpdate.before(tenMinutesAgo) => u }
  }

  private def tenMinutesAgo: Date = {
  	val d = new Date
  	d.setMinutes(d.getMinutes - 10)
  	d
  }
}
