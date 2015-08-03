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
      if (i < arr.length) arr(i)
      else throw new IllegalArgumentException(s"arr.length = ${arr.length} i = $i")
    }
  }
}