package com.jaderholm.weather

import org.scalatest.FunSuite
import org.scalatest.matchers.should.Matchers

class IconConversionSuite extends FunSuite with Matchers {
  import IconConversion.convert

  test("partly-cloudy-day") {
    convert("partly-cloudy-day") shouldBe "Partly Cloudy"
  }
}
