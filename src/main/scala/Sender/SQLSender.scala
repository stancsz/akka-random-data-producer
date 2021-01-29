package com.sqlwriter
package Sender

import akka.Done
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.alpakka.slick.scaladsl._
import akka.stream.scaladsl.Source
import slick.jdbc.GetResult

import scala.concurrent.Future

object SQLSender {

  //Iris domain
  case class Iris(id: Int, sepalLengthCm: Float, sepalWidthCm: Float, petalLengthCm: Float,
                  petalWidthCm: Float, species: String)
  //Transform the results of the query into Iris class instances
  implicit val getIrisResult: AnyRef with GetResult[Iris] = GetResult(r => Iris(r.nextInt,
    r.nextFloat, r.nextFloat, r.nextFloat, r.nextFloat, r.nextString))
  //Read each line into an Iris class instance
  def lineRead(line: String): Iris = {
    val fields = line.split(",")
    Iris(fields(0).toInt, fields(1).toFloat, fields(2).toFloat, fields(3).toFloat, fields(4).toFloat, fields(5))
  }


  def streamCSV(csvPath: String): Unit = {
    //Set up SlickSession that reads the "slick-mysql" configuration from application.conf
    implicit val system: ActorSystem = ActorSystem()
    implicit val mat: ActorMaterializer = ActorMaterializer()
    implicit val session: SlickSession = SlickSession.forConfig("slick-mysql")
    system.registerOnTermination(session.close())
    val csvSource = scala.io.Source.fromFile(csvPath)
    val lines = csvSource.getLines.drop(1).toList
    val ires = lines.map(lineRead)
    //This import enables the use of Slick sql"...", sqlu"...", sqlt"..." String interpolators.
    import session.profile.api._
    //This import enables executions of scala.concurrent.Future, similar to Java's Thread.
    import scala.concurrent.ExecutionContext.Implicits.global
    //Stream ires into the database as insert statements
    val done: Future[Done] =
      Source(ires).runWith(
        Slick.sink(iris => sqlu"INSERT INTO IrisTest.iris VALUES(${iris.id},${iris.sepalLengthCm},${iris.sepalWidthCm},${iris.petalLengthCm},${iris.petalWidthCm},${iris.species}")
      )
    //Execute the Future[Done]
    done foreach {
      msg => println(msg)
    }
    //End the Slick Session, terminate the Actor System
    session.close()
  }


  def main(args: Array[String]): Unit ={
    streamCSV("./csv_data_source/Iris.csv")
  }

}
