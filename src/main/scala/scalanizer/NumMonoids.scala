package scalanizer

trait NumMonoids extends Nums {
  trait NumMonoid[A] {
    def n: Num[A]
    def opName: String
    def zero: A
    def append: (A, A) => A
    def isCommutative: Boolean
  }

  class PlusMonoid[A](val n: Num[A]) extends NumMonoid[A] {
    def opName = "+"
    def zero = n.zero
    def append = (a0: A, a1: A) => n.plus(a0, a1)
    def isCommutative: Boolean = true
  }
}
