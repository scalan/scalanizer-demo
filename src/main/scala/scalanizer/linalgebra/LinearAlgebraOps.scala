package scalanizer.linalgebra

import scala.collection.mutable
import scalanizer._
import scalanizer.collections._
import scala.reflect.ClassTag
import scalan.HotSpot
import scalan.compilation.KernelTypes._

trait LinearAlgebraOps {self: LinearAlgebra =>

  trait LinearAlgebraOp
  class LA extends LinearAlgebraOp

  object LA {
    @HotSpot(CppKernel)
    def ddmvm(m: Array[Array[Double]], v: Array[Double]): Array[Double] = {
      val doubleNumer = new DoubleNum()
      val plusMonoid = new PlusMonoid(doubleNumer)
      val width = m(0).length
      val vector = new DenseVec(Col(v))
      val matrix = new DenseMatr[Double](Col(m.map(r => new DenseVec(Col(r)))), width)

      new BaseMatrOp().mvm(matrix, vector)(doubleNumer, plusMonoid).items.arr
      v
    }
  }

}

