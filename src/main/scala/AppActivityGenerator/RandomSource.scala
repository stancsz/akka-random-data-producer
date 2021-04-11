package com.sqlwriter
package AppActivityGenerator

//https://doc.akka.io/docs/akka/current/stream/stream-quickstart.html

import Models.{Courier, Order}

import akka.Done
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.alpakka.slick.javadsl.SlickSession
import akka.stream.alpakka.slick.scaladsl.Slick
import akka.stream.scaladsl._

import scala.concurrent.Future
import scala.language.postfixOps



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
  def generateCourier = {
    val cour = Seq(new Courier)
    val done: Future[Done] =
      Source(cour)
        .log("cour")
        .runWith(
          Slick.sink(cour => sqlu"INSERT INTO courier_order_db.CourierTest (courier_id,courier_score,app_created_timestamp,lat,lon) VALUES(${cour.courier_id}, ${cour.courier_score}, ${cour.courier_created}, ${cour.lat}, ${cour.lon})")
        )
  }

  def generateOrder = {
    val order = Seq(new Order)
    val done: Future[Done] =
      Source(order)
        .log("order")
        .runWith(
          Slick.sink(order => sqlu"INSERT INTO courier_order_db.OrderTest (order_id,order_score,app_created_timestamp,lat,lon) VALUES(${order.order_id}, ${order.order_score}, ${order.order_created}, ${order.lat}, ${order.lon})")
        )
  }


  def main(args: Array[String]): Unit = {
    val num = 50000
    for (i <- 1 to num) {
      generateCourier
      Thread.sleep(scala.util.Random.nextInt(5000)+500)
      generateOrder
      Thread.sleep(scala.util.Random.nextInt(5000)+500)

    }
    session.close()
    System.exit(0)
  }

}
