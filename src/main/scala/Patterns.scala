package patterns

trait Patterns {
  trait Pattern {
    def get(a: Any, b: Int): String = a match {
      case 0 => "0"
      case `b` => b.toString
//      case i: Int => i.toString
      case _ => "Unknown"
    }
  }
}
