package com.sqlwriter

import com.sqlwriter.Models.{Courier, Order}

class ModelTest {


}


object ModelRunner {

  def main(args: Array[String]): Unit = {
    val c1 = new Courier()
    print(c1.toString)
    val c2 = new Order()
    print(c2.toString)
  }

}