package patterns

trait Patterns {
  trait Pattern {
    def get(a: AnyVal): String = a match {
      case 0 => "0"
      case 1 => "1"
      case _ => "Unknown"
    }
  }
}
