package scalanizer

trait Cols {

  trait Col[A] {
    def arr: Arr[A]
    def length: Int
    def apply(i: Int): A
  }

  class ColOverArray[A](val arr: Arr[A]) extends Col[A] {
    def length = arr.length
    def apply(i: Int) = arr(i)
  }
}