package patterns

trait Patterns {
  trait Pattern {
    def get(a: Any, b: Int): String = a match {
      case 0 => "0"
      case `b` => "b"
      case scala.math.Pi => "3.14"
      case _ => "Unknown"
    }
  }
}
