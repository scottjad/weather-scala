package com.jaderholm.weather

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class UtilSuite extends AnyFunSuite with Matchers {
  import Util._

  test("partitionBy") {
    partitionBy(Seq(1,1,2,3))(_ % 2 == 0) shouldBe Seq(Seq(1,1), Seq(2), Seq(3))
  }

}
