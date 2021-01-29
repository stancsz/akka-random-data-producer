package com.sqlwriter
package Models

import com.sqlwriter.AppActivityGenerator.SyntacticGenerator

import java.sql.Timestamp

class Courier() {
  val gen : SyntacticGenerator = new SyntacticGenerator()
  val coor : Seq[Double] = gen.generateRandomCoordinate()

  var courier_id : String = gen.generateUUID()
  var courier_score : Double = gen.generateScore()
  var courier_created : Timestamp = gen.getTimestamp()
  var lat : Double = coor(0)
  var lon : Double = coor(1)

  override def toString = s"Courier($courier_id, $courier_score, $courier_created, $lat, $lon)"
}
