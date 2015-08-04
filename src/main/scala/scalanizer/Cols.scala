package scalanizer

import scalan.HotSpot
import scalan.compilation.KernelTypes._

trait Cols {

  trait Col[A] {
    def arr: MyArr[A]
    def length: Int
    def apply(i: Int): A
  }

  class ColOverArray(val arr: MyArr[Int]) extends Col[Int] {
    val length = arr.length
    @HotSpot(ScalaKernel)
    def apply(i: Int) = {
      if (arr.length <= i) arr(arr.length - 1)
      else if (i < 0) arr(0)
      else arr(i)
    }
  }
}