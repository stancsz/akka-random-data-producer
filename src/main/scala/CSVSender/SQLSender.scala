package com.sqlwriter
package CSVSender

import akka.Done
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.alpakka.slick.scaladsl._
import akka.stream.scaladsl.Source
import slick.jdbc.GetResult

import scala.concurrent.Future

import java.sql.Timestamp

object SQLSender {

  //Iris domain
  case class Order(id: String, score: Double, created: Timestamp, lon: Double, lat: Double)
  //Transform the results of the query into Iris class instances
  implicit val getOrderResult: AnyRef with GetResult[Order] = GetResult(r => Order(r.nextString,
    r.nextDouble, r.nextTimestamp, r.nextDouble, r.nextDouble))
  //Read each line into an Iris class instance
  def lineRead(line: String, delim: String): Order = {
    val fields = line.split(delim)
    Order(fields(0), fields(1).toDouble, Timestamp.valueOf(fields(2)), fields(3).toDouble, fields(4).toDouble)
  }


  def streamCSV(csvPath: String, header: Boolean, delim: String,databaseName: String, tableName: String): Unit = {
    //Set up SlickSession that reads the "slick-mysql" configuration from application.conf
    implicit val system: ActorSystem = ActorSystem()
    implicit val mat: ActorMaterializer = ActorMaterializer()
    implicit val session: SlickSession = SlickSession.forConfig("slick-mysql")
    system.registerOnTermination(session.close())
    val csvSource = scala.io.Source.fromFile(csvPath)
    //If csv contains headers at the 1st line
    val lines = csvSource.getLines.drop(if (header) 1 else 0).toList
    val orders = lines.map(line => lineRead(line, delim))
    //This import enables the use of Slick sql"...", sqlu"...", sqlt"..." String interpolators.
    import session.profile.api._
    //This import enables executions of scala.concurrent.Future, similar to Java's Thread.
    import scala.concurrent.ExecutionContext.Implicits.global
    //Stream ires into the database as insert statements

    val done: Future[Done] =
      Source(orders).runWith(
        Slick.sink(order => sqlu"INSERT INTO $databaseName.$tableName VALUES(${order.id},${order.score},${order.created.toString},${order.lon},${order.lat}")
      )
    //Execute the Future[Done]
    done foreach {
      msg => println(msg)
    }
    //End the Slick Session, terminate the Actor System
    session.close()
  }


  def main(args: Array[String]): Unit ={
    val orderCSVPath = ""
    val hasHeader = true
    val delim = ","
    val databaseName = ""
    val tableName = ""
    streamCSV(orderCSVPath, hasHeader, delim, databaseName, tableName)
  }

}
