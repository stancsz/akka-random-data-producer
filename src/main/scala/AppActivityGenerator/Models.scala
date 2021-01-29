package com.sqlwriter
package AppActivityGenerator

import java.sql.Timestamp


class Models {
   class Courier(courier_id:String, courier_score: Double, order_created: Timestamp, lat: Double, lon: Double)
   class Order(order_id:String,  courier_score: Double, order_created: Timestamp, lat: Double, lon: Double)
}
