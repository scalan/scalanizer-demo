package paradise.collections

import scala.annotation.unchecked.uncheckedVariance

trait Collections {

  trait Collection[@uncheckedVariance +A] {
    def arr: Array[A @uncheckedVariance]
    def length: Int
    def apply(i: Int): A
    def zip[B](ys: Collection[B]): PairCollection[A@uncheckedVariance, B]
    def mapBy[B](f: A => B@uncheckedVariance): Collection[B]
    def map[B](f: A => B@uncheckedVariance): Collection[B]
    def reduce: A
  }

  object Collection {
    def apply[T](arr: Array[T]): Collection[T] = ??? //fromArray(arr)
  }

  case class PairCollection[A, B](val as: Collection[A], val bs: Collection[B]) extends Collection[(A, B)] {
    def arr = (as.arr zip bs.arr)
    def length = as.length
    def apply(i: Int) = (as(i), bs(i))
    def zip[C](ys: Collection[C]): PairCollection[(A, B),C] = PairCollection(self, ys)
    def mapBy[C](f: (A,B) @uncheckedVariance => C): Collection[C] = Collection(arr.mapBy(f))
    def map[C](f: (A,B) @uncheckedVariance => C): Collection[C] = Collection(arr.mapBy(f))
    def reduce: (A,B) = arr.reduce
  }
}