package com.sqlwriter
package AppActivityGenerator

import java.sql.Timestamp


object Models {
   val gen : SyntacticGenerator = new SyntacticGenerator()
   class Courier(courier_id:String=gen.generateUUID(),
                 courier_score: Double=gen.generateScore(),
                 order_created: Timestamp=gen.getTimestamp(),
                 coordinates: Seq[Double] = gen.generateRandomCoordinate())

   class Order(order_id:String=gen.generateUUID(),
               courier_score: Double=gen.generateScore(),
               order_created: Timestamp=gen.getTimestamp(),
               coordinates: Seq[Double] = gen.generateRandomCoordinate())
}
