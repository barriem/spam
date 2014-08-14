package org.bcm.spam.actor

import scala.collection.mutable
import java.util.Date

import akka.actor._

import org.bcm.spam.payload.action.{GetPresence, GetUsers, RegisterUser, Prune}
import org.bcm.spam.payload.model.Presence

class PrescenceActor extends Actor {
  val userIdsWithPresence = mutable.Map[String, Presence]()

  def receive = {
    case ru: RegisterUser => {
      val presence = Presence(ru.id, new Date(), "Online", ru.ref)
      broadcast(presence)
      userIdsWithPresence += ru.id -> presence
    }
    case gp: GetPresence => sender ! userIdsWithPresence(gp.id)
    case GetUsers => sender ! userIdsWithPresence.keys
    case Prune => userIdsWithPresence --= userIdsWithPresence collect { case (u, p) if p.lastUpdate.before(tenMinutesAgo) => u }
  }

  private def broadcast(presence: Presence) {
    userIdsWithPresence foreach { case (uid, p) => p.ref ! presence }
  }

  private def tenMinutesAgo: Date = {
  	val d = new Date
  	d.setMinutes(d.getMinutes - 10)
  	d
  }
}
