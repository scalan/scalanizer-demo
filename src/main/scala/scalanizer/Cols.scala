package scalanizer

import scalan.HotSpot
import scalan.compilation.KernelTypes._

trait Cols {self: Demo =>

  trait Col[A] {
    def arr: MyArr[A]
    def apply(i: Int): A
  }

  class ColOverArray(val arr: MyArr[Int]) extends Col[Int] {
    @HotSpot(ScalaKernel)
    def apply(i: Int) = {
      if (arr.length <= i) arr(arr.length - 1)
      else if (i < 0) arr(0)
      else arr(i)
    }
  }
}