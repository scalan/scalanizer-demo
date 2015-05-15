package patterns

case class ZeroPat()
case class OnePat(x: Int)
//case class MultiPat(x: Int, y: Long)

trait Patterns {
  trait Pattern {
    def get(a: Any, b: Int): String = {
      a match {
        //case MultiPat(0, 1) => "Multi"
//        case ZeroPat() => "Zero"
//        case OnePat(y) => "one" + y.toString
//        case c: Int => "Int"
//        case 0 | 1 => "0 or 1"
        case `b` => "b"
        case scala.math.Pi => "3.14"
        case 42 => "42"
        case unknown => "Unknown"
      }
    }
  }
}
