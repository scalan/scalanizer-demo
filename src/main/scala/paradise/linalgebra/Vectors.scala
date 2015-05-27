package paradise.linalgebra

import scala.annotation.unchecked.uncheckedVariance

trait Vectors { self: LinearAlgebra =>
  type Vector[T] = AbstractVector[T]
  trait AbstractVector[T] {
    def length: Int
    def items: Collection[T]
    def apply(i: Int): T
    def map[R](f: T => R): Vector[R]
    def dot(other: Vector[T])(implicit n: Numeric[T]): T
  }

  case class DenseVector[T](val items: Collection[T]) extends AbstractVector[T] {
    def length = items.length

    def apply(i: Int): T = items(i)
    def map[R](f: T => R): Vector[R] = DenseVector(items.map(f))
    def dot(other: Vector[T])(implicit n: Numeric[T]): T = {
      //(other.items zip items).map { case Pair(v1, v2) => v1 * v2 }.reduce
      ???
    }
  }
}
