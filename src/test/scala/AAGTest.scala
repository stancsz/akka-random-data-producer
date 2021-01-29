package com.sqlwriter

import AppActivityGenerator.SyntacticGenerator

class AAGTest {}

object GeneratorRunner{
  val aag : SyntacticGenerator = new SyntacticGenerator()
  def main(args: Array[String]): Unit = {
    print(aag.generateRandomCoordinate(1,2,3,4))
    print("\n")
    print(aag.generateUUID())
    print("\n")
    print(aag.getTimestamp())
  }
}

