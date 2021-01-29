package com.sqlwriter
package AppActivityGenerator

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.UUID
import scala.util.Random
import scala.collection.mutable

/*
* Usage Reference
* https://doc.akka.io/docs/alpakka/current/slick.html
*
* */
class SyntacticGenerator {
  /*
  * util code for syntactically generating two models:
  * 1. courier(courier_id, courier_score, order_created, courier_coordinate)
  * 2. order(order_id, order_value, order_created, order_coordinate)
  * */

  def generateRandomCoordinate(x1: Double, y1: Double, x2: Double, y2: Double): Seq[Double] = {
    val xr = x1 + Random.between(x1, x2)
    val yr = y1 + Random.between(y1, y2)
    Seq(xr, yr)
  }

  def generateUUID(): String = {
    def uuid = UUID.randomUUID.toString
    uuid
  }

  def getTimestamp(): Timestamp = {
    // get sql timestamp https://stackoverflow.com/questions/15534775/how-to-insert-current-time-in-mysql-using-java
    // usage of current time https://gist.github.com/WesleyBatista/dcd0fd076c0ea1d013e25c8ed954bca2
    val timestamp: Timestamp = new Timestamp(System.currentTimeMillis())
    timestamp
  }

}
