package scalanizer

trait Num[T] {
  def zero: T
  def one: T
  def plus(a: T, b: T): T
  def times(a: T, b: T): T
}

class DoubleNum() extends Num[Double] {
  def zero: Double = 0.0
  def one: Double = 1.0
  def plus(a: Double, b: Double) = a + b
  def times(a: Double, b: Double) = a * b
}

