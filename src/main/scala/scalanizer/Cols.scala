package scalanizer

trait Cols {

  trait Col[A] {
    def arr: MyArr[A]
    def length: Int
    def apply(i: Int): A
  }

  class ColOverArray(val arr: MyArr[Int]) extends Col[Int] {
    val length = arr.length
    def apply(i: Int) = arr(i)
  }
}