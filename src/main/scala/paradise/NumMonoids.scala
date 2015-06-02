package paradise

trait NumMonoids extends Numers {
  trait NumMonoid[A] {
    implicit def n: Numer[A]
    def opName: String
    def zero: A
    def append: (A, A) => A
    def isCommutative: Boolean
  }

  case class PlusMonoid[A](implicit val n: Numer[A]) extends NumMonoid[A] {
    def opName = "+"
    def zero = n.zero
    def append = (a0: A, a1: A) => n.plus(a0, a1)
    def isCommutative: Boolean = true
  }
}
