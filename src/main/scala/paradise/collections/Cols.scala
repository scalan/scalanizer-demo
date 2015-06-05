package paradise.collections

import paradise.NumMonoids
import scala.collection.generic.CanBuildFrom
import scala.reflect.ClassTag

trait Cols extends NumMonoids {

  trait Col[A] {
    def arr: Array[A]
    def length: Int
    def apply(i: Int): A
    def map[B: ClassTag](f: A => B): Col[B] =
      Col(arr.map(f)(implicitly[CanBuildFrom[Array[A], B, Array[B]]]))
    def reduce(implicit m: NumMonoid[A]): A = (arr: Array[A]).reduce(m.append)
    def zip[B](ys: Col[B]): PairCol[A, B] = PairCol(this, ys)
  }

  object Col {
    def apply[T: ClassTag](arr: Array[T]): Col[T] = fromArray(arr)
    def fromArray[T: ClassTag](arr: Array[T]): Col[T] = ColOverArray(arr)
  }

  case class ColOverArray[A: ClassTag](val arr: Array[A]) extends Col[A] {
    def length = (arr: Array[A]).length
    def apply(i: Int) = (arr: Array[A]).apply(i)
  }

  case class PairCol[A, B](val as: Col[A], val bs: Col[B]) extends Col[(A, B)] {
    def arr: Array[(A, B)] = (as.arr zip bs.arr)
    def length = as.length
    def apply(i: Int) = (as(i), bs(i))
  }
}