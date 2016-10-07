package scalanizer.linalgebra

import scalanizer._
import scalanizer.collections._
import scala.reflect.ClassTag
import scalan.HotSpot
import scalan.compilation.KernelType._

trait LinearAlgebraOps {self: LinearAlgebra =>

  // This is declared in order to satisfy SEntityModuleDef requirements
  trait LinearAlgebraOp
  class LA extends LinearAlgebraOp
  object LA {
    @HotSpot(kernel = Scala)
    def ddmvm(v: Array[Double]): Int = {
      val c = Col.fromArray(v)
      c.length
    }
    @HotSpot(Cpp)
    def ddmvm(m: Array[Array[Double]], v: Array[Double]): Array[Double] = {
      val doubleNumer = new DoubleNum()
      val plusMonoid = new PlusMonoid(doubleNumer)
      val width = m(0).length
      val vector = new DenseVec(Col(v))
      val matrix = new DenseMatr[Double](Col(m.map(r => new DenseVec(Col(r)): Vec[Double])), width)

      new BaseMatrOp().mvm(matrix, vector)(doubleNumer, plusMonoid).items.arr
    }
  }

}

