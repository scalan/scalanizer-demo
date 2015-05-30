package paradise

trait NumMonoids {
  trait NumMonoid[A] {
    def opName: String
    def zero: A
    def append: (A, A) => A
    def isCommutative: Boolean
  }

  case class PlusMonoid[A](implicit n: Numeric[A]) extends NumMonoid[A] {
    def opName = "+"
    def zero = n.zero
    def append = (a0: A, a1: A) => n.plus(a0, a1)
    def isCommutative: Boolean = true
  }

  case class MultMonoid[A](implicit n: Numeric[A]) extends NumMonoid[A] {
    def opName = "*"
    def zero = n.one
    def append = (a0: A, a1: A) => n.times(a0, a1)
    def isCommutative: Boolean = true
  }
}
