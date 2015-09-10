package scalanizer.collections

import scalanizer._
import scala.collection.generic.CanBuildFrom
import scala.reflect.ClassTag

trait Col[T] {
  def arr: Array[T]
  def length: Int
  def apply(i: Int): T
  def map[B: ClassTag](f: T => B): Col[B] =
    Col((arr: Array[T]).map(f)(implicitly[CanBuildFrom[Array[T], B, Array[B]]]))
  def reduce(implicit m: NumMonoid[T]): T = (arr: Array[T]).reduce(m.append)
  def zip[B](ys: Col[B]): PairCol[T, B] = new PairCol(this, ys)
}

object Col {
  def apply[T: ClassTag](arr: Array[T]): Col[T] = fromArray(arr)
  def fromArray[T: ClassTag](arr: Array[T]): Col[T] = new ColOverArray(arr)
}

class ColOverArray[A: ClassTag](val arr: Array[A]) extends Col[A] {
  def length = (arr: Array[A]).length
  def apply(i: Int) = (arr: Array[A]).apply(i)
}

class PairCol[A, B](val as: Col[A], val bs: Col[B]) extends Col[(A, B)] {
  def arr: Array[(A, B)] = (as.arr zip bs.arr)
  def length = as.length
  def apply(i: Int) = (as(i), bs(i))
}
