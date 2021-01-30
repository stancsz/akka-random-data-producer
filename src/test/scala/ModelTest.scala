package com.sqlwriter

import com.sqlwriter.Models.{Courier, Order}

class ModelTest {


}


object ModelRunner {

  def main(args: Array[String]): Unit = {
    print(new Courier()+ "\n")
    print(new Order()+ "\n")
    print(new Courier()+ "\n")
    print(new Order()+ "\n")
  }

}