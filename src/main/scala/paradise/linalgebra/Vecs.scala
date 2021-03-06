package scalanizer.linalgebra

import scala.reflect.ClassTag

trait Vecs { self: LinearAlgebra =>
  trait Vec[T] {
    def length: Int
    def items: Col[T]
    def apply(i: Int): T
    def map[R: ClassTag](f: T => R): Vec[R]
    def dot(other: Vec[T])(implicit n: Num[T], m: NumMonoid[T]): T
  }

  case class DenseVec[T](val items: Col[T])(implicit val ctT: ClassTag[T]) extends Vec[T] {
    def length = items.length
    def apply(i: Int): T = items(i)
    def map[R: ClassTag](f: T => R): Vec[R] = DenseVec(items.map(f))
    def dot(other: Vec[T])(implicit n: Num[T], m: NumMonoid[T]): T = {
      (other.items zip items).map((v: (T, T)) => n.times(v._1, v._2)).reduce(m)
    }
  }
}
