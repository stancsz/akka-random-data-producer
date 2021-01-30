package com.sqlwriter
package AppActivityGenerator

//https://doc.akka.io/docs/akka/current/stream/stream-quickstart.html

import akka.stream._
import akka.stream.scaladsl._
import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.util.{ByteString, Timeout}

import scala.concurrent._
import scala.concurrent.duration._
import java.nio.file.Paths


object RandomSource{
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val mat = ActorMaterializer()

    //Basics and working with Flows
    // https://doc.akka.io/docs/akka/current/stream/stream-flows-and-basics.html
    val source = Source(1 to 10)
    val sink = Sink.fold[Int, Int](0)(_ + _)

    // connect the Source to the Sink, obtaining a RunnableGraph
    val runnable: RunnableGraph[Future[Int]] = source.toMat(sink)(Keep.right)

    // materialize the flow and get the value of the FoldSink
    // futures https://doc.akka.io/docs/akka/2.5/futures.html
    val sum: Future[Int] = runnable.run()

    // about await timeout
    val result = Await.result(sum, 5 seconds)
    print(result)
  }

}
