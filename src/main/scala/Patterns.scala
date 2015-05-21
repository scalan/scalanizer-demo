package patterns

//case class ZeroPat()
//case class OnePat[T](x: T)
//case class MultiPat(x: Int, y: Long)

trait Patterns {
  trait Pattern {
    def get(a: Any, b: Int): String = {
      a match {
//        case MultiPat(0, c:Long) => "Multi"
//        case OnePat(y: OnePat[Int]) => "OnePat"
//        case ZeroPat() => "Zero"
        case 0 | 1 => "0 or 1"
        case c: Int => c.toString
        case `b` => "b"
        case scala.math.Pi => "3.14"
        case 42 if b != 42 => "42"
        case _ => "Unknown"
      }
    }
  }
}
