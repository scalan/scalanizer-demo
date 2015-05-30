package paradise.linalgebra

import scala.reflect.ClassTag

trait Vectors { self: LinearAlgebra =>
  trait AbstractVector[T] {
    def length: Int
    def items: Collection[T]
    def apply(i: Int): T
    def map[R: ClassTag](f: T => R): AbstractVector[R]
    def dot(other: AbstractVector[T])(implicit n: Numer[T], m: NumMonoid[T]): T
  }

  case class DenseVector[T](val items: Collection[T])(implicit val ctT: ClassTag[T]) extends AbstractVector[T] {
    def length = items.length
    def apply(i: Int): T = items(i)
    def map[R: ClassTag](f: T => R): AbstractVector[R] = DenseVector(items.map(f))
    def dot(other: AbstractVector[T])(implicit n: Numer[T], m: NumMonoid[T]): T = {
      (other.items zip items).map((v: (T, T)) => n.times(v._1, v._2)).reduce
    }
  }
}
