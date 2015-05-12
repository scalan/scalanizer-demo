package patterns

trait Patterns {
  trait Pattern {
    def get(a: AnyVal): String = a match {
      case 0 => "0"
      case i: Int => i.toString
      case _ => "Unknown"
    }
  }
}
