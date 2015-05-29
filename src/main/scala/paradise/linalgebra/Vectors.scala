package paradise.linalgebra

import scala.reflect.ClassTag

trait Vectors { self: LinearAlgebra =>
  type Vector[T] = AbstractVector[T]
  trait AbstractVector[T] {
    def length: Int
    def items: Collection[T]
    def apply(i: Int): T
    def map[R: ClassTag](f: T => R): Vector[R]
    def dot(other: Vector[T])(implicit n: Numeric[T], m: Monoid[T]): T
  }

  case class DenseVector[T](val items: Collection[T])(implicit val ctT: ClassTag[T]) extends AbstractVector[T] {
    def length = items.length
    def apply(i: Int): T = items(i)
    def map[R: ClassTag](f: T => R): Vector[R] = DenseVector(items.map(f))
    def dot(other: Vector[T])(implicit n: Numeric[T], m: Monoid[T]): T = {
      (other.items zip items).map((v: (T, T)) => n.times(v._1, v._2)).reduce
    }
  }
}