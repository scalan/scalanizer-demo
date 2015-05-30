package paradise

trait Numers {
  trait Numer[T] {
    def zero: T
    def one: T
    def plus(a: T, b: T): T
    def times(a: T, b: T): T
  }

  case class DoubleNumer() extends Numer[Double] {
    def zero = 0.0
    def one = 1.0
    def plus(a: Double, b: Double) = a + b
    def times(a: Double, b: Double) = a * b
  }
}
