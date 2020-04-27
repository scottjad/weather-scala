package com.jaderholm.weather

object Util {
  def partitionBy[T, Y](coll: Seq[T])(selector: T => Y): Seq[Seq[T]] = {
    def inner(lastDiff: T, rest: Seq[T], result: Seq[Seq[T]]): Seq[Seq[T]] = {
      if (rest.isEmpty)
        return result
      val current = rest.head
      if (selector(current) != selector(lastDiff)) {
        inner(current, rest.tail, result ++ Seq(Seq(current)))
      } else {
        inner(lastDiff, rest.tail, result.init :+ (result.last :+ current))
      }
    }
    inner(coll.head, coll.tail, Seq(Seq(coll.head)))
  }

  val clearLine = "\u001b[2K\n"
}
