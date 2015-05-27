package linalgebra

import scala.annotation.unchecked.uncheckedVariance

trait Vectors { self: LinearAlgebra =>
  type Vector[T] = AbstractVector[T]
  trait AbstractVector[T] {
    def length: Int

    def apply(i: Int): T
    def mapBy[R](f: T => R @uncheckedVariance): Vector[R]
  }

  case class DenseVector[T](val items: Collection[T]) extends AbstractVector[T] {
    def length = items.length

    def apply(i: Int): T = items(i)
    def mapBy[R](f: T => R @uncheckedVariance): Vector[R] = DenseVector(items.mapBy(f))
  }

  case class SparseVector[T](val length: Int) extends AbstractVector[T] {
    def apply(i: Int): T = ???
    def mapBy[R](f: T => R @uncheckedVariance): Vector[R] = ???
  }
}
