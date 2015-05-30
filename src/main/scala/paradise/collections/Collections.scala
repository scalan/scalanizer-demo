package paradise.collections

import paradise.NumMonoids
import scala.collection.generic.CanBuildFrom
import scala.reflect.ClassTag

trait Collections extends NumMonoids {

  trait Collection[A] {
    def arr: Array[A]
    def length: Int
    def apply(i: Int): A
    def map[B: ClassTag](f: A => B): Collection[B] =
      Collection(arr.map(f)(implicitly[CanBuildFrom[Array[A], B, Array[B]]]))
    def reduce(implicit m: NumMonoid[A]): A = arr.reduce(m.append)
    def zip[B](ys: Collection[B]): PairCollection[A, B] = PairCollection(this, ys)
  }

  object Collection {
    def apply[T: ClassTag](arr: Array[T]): Collection[T] = CollectionOverArray(arr)
  }

  case class CollectionOverArray[A: ClassTag](val arr: Array[A]) extends Collection[A] {
    def length = arr.length
    def apply(i: Int) = arr(i)
  }

  case class PairCollection[A, B](val as: Collection[A], val bs: Collection[B]) extends Collection[(A, B)] {
    def arr: Array[(A, B)] = (as.arr zip bs.arr)
    def length = as.length
    def apply(i: Int) = (as(i), bs(i))
  }
}