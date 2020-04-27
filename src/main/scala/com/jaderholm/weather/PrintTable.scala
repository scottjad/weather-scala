package com.jaderholm.weather

object PrintTable {
  def formatTable(xs: Seq[Seq[String]]): String = {
    val lines = for (row <- xs) yield row.mkString("\t")
    lines.mkString("\n")
  }
}

object PTest extends App {
  "foo".length()
    (0 to 10).foreach { x => println(x) }
  (0 to 10) foreach { x => println(x) }
}
