package scalanizer

import scalanizer.linalgebra.LinearAlgebra

trait NumMonoids {self: LinearAlgebra =>
  trait NumMonoid[T] {
    def n: Num[T]
    def opName: String
    def zero: T
    def append: (T, T) => T
    def isCommutative: Boolean
  }

  class PlusMonoid[T](val n: Num[T]) extends NumMonoid[T] {
    def opName = "+"
    def zero = n.zero
    def append = (a0: T, a1: T) => n.plus(a0, a1)
    def isCommutative: Boolean = true
  }
}
