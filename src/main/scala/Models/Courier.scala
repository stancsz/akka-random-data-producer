package com.sqlwriter
package Models

import com.sqlwriter.AppActivityGenerator.SyntacticGenerator

import java.sql.Timestamp

class Courier() {
  var courier_id : String = new SyntacticGenerator().generateUUID()
  var courier_score : Double = new SyntacticGenerator().generateScore()
  var courier_created : Timestamp = new SyntacticGenerator().getTimestamp()
  var (lon, lat)  = new SyntacticGenerator().generateRandomCoordinate()

  override def toString = s"Courier($courier_id, $courier_score, $courier_created, $lat, $lon)"
  (courier_id,courier_score,courier_created,lat,lon)
}
