package paradise.collections

trait Collections {

  trait Collection[A] {
    def arr: Array[A]
    def length: Int
    def apply(i: Int): A
    def map[B](f: A => B): Collection[B]
    def reduce: A
    def zip[B](ys: Collection[B]): PairCollection[A, B]
  }

  object Collection {
    def apply[T](arr: Array[T]): Collection[T] = CollectionOverArray(arr)
  }

  case class CollectionOverArray[A](val arr: Array[A]) extends Collection[A] {
    def length = arr.length
    def apply(i: Int) = arr(i)
    def map[B](f: A => B): Collection[B] = ??? //Collection(arr.map(f))
    def reduce: A = ??? //arr.reduce
    def zip[B](ys: Collection[B]): PairCollection[A, B] = PairCollection(this, ys)
  }

  case class PairCollection[A, B](val as: Collection[A], val bs: Collection[B]) extends Collection[(A, B)] {
    def arr: Array[(A, B)] = (as.arr zip bs.arr)
    def length = as.length
    def apply(i: Int) = (as(i), bs(i))
    def map[C](f: ((A, B)) => C): Collection[C] = ??? //Collection(arr.map(f))
    def reduce: (A,B) = ??? //arr.reduce
    def zip[C](ys: Collection[C]): PairCollection[(A, B),C] = PairCollection(this, ys)
  }
}