package scalanizer.linalgebra

import scalanizer._
import scala.reflect.ClassTag
import scalan.HotSpot
import scalan.compilation.KernelTypes._

trait LinearAlgebraOps { self: LinearAlgebra =>
  trait LinearAlgebraOp {
    def mvm[T: ClassTag](matrix: Matr[T], vector: Vec[T])
                        (n: Num[T], m: NumMonoid[T]): Vec[T]
  }

  case class LA() extends LinearAlgebraOp {
    def mvm[T: ClassTag](matrix: Matr[T], vector: Vec[T])
                        (n: Num[T], m: NumMonoid[T]): Vec[T] = {
      DenseVec(matrix.rows.map{ r: Vec[T] => r.dot(vector)(n, m) })
    }
  }

  object LA {
    @HotSpot(CppKernel)
    def ddmvm(m: Array[Array[Double]], v: Array[Double]): Array[Double] = {
      val doubleNumer = new DoubleNum()
      //      val plusMonoid = new PlusMonoid[Double](doubleNumer)
      //
      //      val width = m(0).length
      //      val matrix = DenseMatr[Double](Col(m.map(r => DenseVec(Col(r)))), width)
      //      val vector = DenseVec(Col(v))
      //
      //      LA().mvm(matrix, vector)(doubleNumer, plusMonoid).items.arr
      v
    }
  }
}
