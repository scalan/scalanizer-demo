package patterns

trait Patterns {
  trait Pattern {
    def get(a: Any, b: Int): String = a match {
      case _: Int => "Int"
      case 0 | 1 => "0 or 1"
      case `b` => "b"
      case scala.math.Pi => "3.14"
      case _ => "Unknown"
    }
  }
}
