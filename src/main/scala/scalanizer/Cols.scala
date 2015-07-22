package scalanizer

trait Cols {

  trait Col[A] {
    def arr: Arr[A]
    def length: Int
    def apply(i: Int): A
  }

  class ColOverArray(val arr: Arr[Int]) extends Col[Int] {
    val length = arr.length
    def apply(i: Int) = arr(i)
  }
}