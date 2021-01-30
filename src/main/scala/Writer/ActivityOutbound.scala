package com.sqlwriter
package Writer

import akka.Done
import akka.stream.alpakka.slick.scaladsl._
import akka.stream.scaladsl._
import slick.jdbc.{GetResult, JdbcProfile}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.sqlwriter.Models._
import slick.backend.DatabaseConfig

import scala.concurrent.Future

/**
 * https://doc.akka.io/docs/alpakka/current/slick.html
 * https://scala-slick.org/doc/3.2.1/sql.html
 */

class ActivityOutbound {
  //Initialization
  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()
  //Starting a Database Session
  implicit val session: SlickSession = SlickSession.forConfig("slick-mysql")
  system.registerOnTermination(session.close())

  //Domain

  // This import enables the use of the Slick sql"...",
  // See "http://slick.lightbend.com/doc/3.2.1/sql.html#string-interpolation"
  import session.profile.api._


  // Stream the results of a query
  val done: Future[Done] =
    Slick
      .source(TableQuery[Courier].result)
      .log("courier")
      .runWith(Sink.ignore)


}