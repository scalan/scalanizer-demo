package scalanizer

import scalan.HotSpot
import scalan.compilation.KernelTypes._

trait Cols {self: Demo =>

  trait Col[A] {
    def arr: MyArr[A]
    def apply(i: Int): A
  }

  class ColOverArray(val arr: MyArr[Int]) extends Col[Int] {
    def apply(i: Int) = arr(i)
  }

  object ColOverArray {
    @HotSpot(ScalaKernel)
    def safeApply(arr: MyArr[Int], index: Int) = {
//      if (arr.length <= index) arr(arr.length - 1)
//      else if (index < 0) arr(0)
//      else arr(index)
      implOfCols.HotSpotKernels.safeApplyKernel(arr, index)
    }
  }
}