package com.sqlwriter
package AppActivityGenerator

//https://doc.akka.io/docs/akka/current/stream/stream-quickstart.html

import akka.stream._
import akka.stream.scaladsl._
import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.alpakka.slick.javadsl.SlickSession
import akka.stream.alpakka.slick.scaladsl.Slick
import akka.util.{ByteString, Timeout}
import com.sqlwriter.Models.{Courier, Order}
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import slick.model.Table

import scala.concurrent._
import scala.concurrent.duration._
import java.nio.file.Paths
import java.sql.Timestamp


object RandomSource {

  //material
  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()

  //db session
  implicit val session: SlickSession = SlickSession.forConfig("slick-mysql")
  import session.profile.api._
  system.registerOnTermination(session.close())


  //  def basicFlow: Unit = {
//    //Basics and working with Flows
//    // https://doc.akka.io/docs/akka/current/stream/stream-flows-and-basics.html
//    val source = Source(1 to 10)
//    val sink = Sink.fold[Int, Int](0)(_ + _)
//    // connect the Source to the Sink, obtaining a RunnableGraph
//    val runnable = source.toMat(sink)(Keep.right)
//    // materialize the flow and get the value of the FoldSink
//    // futures https://doc.akka.io/docs/akka/2.5/futures.html
//    val sum = runnable.run()
//    // about await timeout
//    val result = Await.result(sum, 5 seconds)
//    print(result)
//  }

  /**
   * need materialization
   * https://stackoverflow.com/questions/30964824/how-to-create-a-source-that-can-receive-elements-later-via-a-method-call
   *
   * @return
   */
  def generateCourier: Unit = {

    val cour = Seq(new Courier)
    val done =
      Source(cour)
        .log("cour")
        .runWith(
          Slick.sink(cour => sqlu"INSERT INTO DeliveryDB.CourierTest VALUES(${cour.courier_id}, ${cour.courier_score}, ${cour.courier_created}, ${cour.lat}, ${cour.lon})")
        )
        println(cour)
  }

  def generateOrder = new Order


  def main(args: Array[String]): Unit = {
    val num = 5
    for (i <- 1 to num) {
      generateCourier
      generateOrder
      Thread.sleep(1000)
    }

    session.close()

  }

}
