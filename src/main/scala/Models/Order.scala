package com.sqlwriter
package Models

import AppActivityGenerator.SyntacticGenerator

import java.sql.Timestamp

class Order {
  val gen : SyntacticGenerator = new SyntacticGenerator()
  val coor : Seq[Double] = gen.generateRandomCoordinate()

  var order_id : String = gen.generateUUID()
  var order_score : Double = gen.generateScore()
  var order_created : Timestamp = gen.getTimestamp()
  var lat : Double = coor(0)
  var lon : Double = coor(1)


  override def toString = s"Order($order_id, $order_score, $order_created, $lat, $lon)"
}
