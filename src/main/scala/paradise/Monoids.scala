package paradise

trait Monoids {
  trait Monoid[A] {
    def opName: String
    def zero: A
    def append: (A, A) => A
    def isCommutative: Boolean
  }

  case class PlusMonoid[A](implicit n: Numeric[A]) extends Monoid[A] {
    def opName = "+"
    def zero = n.zero
    def append = (a0: A, a1: A) => n.plus(a0, a1)
    def isCommutative: Boolean = true
  }

  case class MultMonoid[A](implicit n: Numeric[A]) extends Monoid[A] {
    def opName = "*"
    def zero = n.one
    def append = (a0: A, a1: A) => n.times(a0, a1)
    def isCommutative: Boolean = true
  }

  case class OrMonoid() extends Monoid[Boolean] {
    def opName = "||"
    def zero = false
    def append = (a0: Boolean, a1: Boolean) => a0 || a1
    def isCommutative: Boolean = true
  }

  case class AndMonoid() extends Monoid[Boolean] {
    def opName = "&&"
    def zero = true
    def append = (a0: Boolean, a1: Boolean) => a0 && a1
    def isCommutative: Boolean = true
  }
}
