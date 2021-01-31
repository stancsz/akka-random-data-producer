package com.sqlwriter
package Models

import AppActivityGenerator.SyntacticGenerator

import java.sql.Timestamp

class Order {
  var order_id : String = new SyntacticGenerator().generateUUID()
  var order_score : Double = new SyntacticGenerator().generateScore()
  var order_created : Timestamp = new SyntacticGenerator().getTimestamp()
  var (lon, lat)  = new SyntacticGenerator().generateRandomCoordinate()

  override def toString = s"Order($order_id, $order_score, $order_created, $lat, $lon)"

  def * = (order_id, order_score, order_created, lat, lon)
}
