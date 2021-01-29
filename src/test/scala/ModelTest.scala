package com.sqlwriter

import AppActivityGenerator.Models

import com.sqlwriter.AppActivityGenerator.Models.Courier

class ModelTest {


}


object ModelRunner {

  def main(args: Array[String]): Unit = {
    val c1 = new Courier()
    print(c1.courier_id)
  }

}