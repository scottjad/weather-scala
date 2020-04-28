package com.jaderholm.weather

object IconConversion {

  def convert(icon: String): String = {
    icon.replace("-day", "").split("-").map(_.capitalize).mkString(" ")
  }
}
