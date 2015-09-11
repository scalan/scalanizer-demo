package scalanizer.linalgebra

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
      val doubleNumer: Num[Double] = new DoubleNum()
      val zero = doubleNumer.zero
      val plusMonoid: NumMonoid[Double] = new PlusMonoid(doubleNumer)
      val width = m(0).length

      val vCol = Col(v)
      val vector: Vec[Double] = new DenseVec(vCol)
      val vLen = vector.length

      val matrix: Matr[Double] = new DenseMatr[Double](Col(m.map(r => new DenseVec(Col(r)))), width)
      val matrixNumRows = matrix.numRows

      val la = new BaseMatrOp()
      val monoidName = plusMonoid.opName
      val vres = la.mvm(matrix, vector)(doubleNumer, plusMonoid)
      val items = vres.items

      items.arr
    }
  }

}

