package org.bcm.spam.actor

import scala.collection.mutable
import java.util.Date

import akka.actor._

import org.bcm.spam.payload.action.{GetPresence, GetUsers, RegisterUser, Prune}
import org.bcm.spam.payload.model.{Presence, User}

class PrescenceActor extends Actor {
  val usersWithPresence = mutable.Map[String, Presence]()

  def receive = {
    case ru: RegisterUser => {
      usersWithPresence += ru.id -> Presence(new Date(), "Online", ru.ref)
      println(s"${ru.id} is now online!")
    }
    case gp: GetPresence => sender ! usersWithPresence(gp.id)
    case GetUsers => sender ! usersWithPresence.keys
    case Prune => usersWithPresence --= usersWithPresence collect { case (u, p) if p.lastUpdate.before(tenMinutesAgo) => u }
  }

  private def tenMinutesAgo: Date = {
  	val d = new Date
  	d.setMinutes(d.getMinutes - 10)
  	d
  }
}
