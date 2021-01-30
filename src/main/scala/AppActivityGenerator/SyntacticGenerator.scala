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
  * Calgary is used in example:
  * selected range:
  * 50.93693487404149, -114.13936397558079
  * 51.15709214704997, -113.96014950430495
  *
  * */
  val (x1, y1, x2, y2) = (50.93693487404149, -114.13936397558079, 51.15709214704997, -113.96014950430495)

  def generateRandomCoordinate(lat1: Double = x1, lon1: Double =y1, lat2: Double=x2, long2: Double=y2): Tuple2[Double, Double] = {
    val lat = Random.between(lat1, lat2)
    val lon = Random.between(lon1, long2)
    (lat, lon)
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

  def generateScore(): Double={Random.between(0.0, 10.0)}

}
