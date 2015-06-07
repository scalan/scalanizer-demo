package paradise.linalgebra

import scala.reflect.ClassTag
import scalan.HotSpot
import scalan.Kernels._

trait LinearAlgebraOps { self: LinearAlgebra =>
  trait LinearAlgebraOp {
    def mvm[T: ClassTag](matrix: Matr[T], vector: Vec[T])
                        (n: Numer[T], m: NumMonoid[T]): Vec[T]
  }

  case class LA() extends LinearAlgebraOp {
    def mvm[T: ClassTag](matrix: Matr[T], vector: Vec[T])
                        (n: Numer[T], m: NumMonoid[T]): Vec[T] = {
      DenseVec(matrix.rows.map{ r: Vec[T] => r.dot(vector)(n, m) })
    }
  }

  object LA {
    @HotSpot(CppKernel)
    def ddmvm(m: Array[Array[Double]], v: Array[Double]): Array[Double] = {
      val doubleNumer: Numer[Double] = DoubleNumer()
      val plusMonoid: NumMonoid[Double] = PlusMonoid[Double](doubleNumer)

      val width = m(0).length
      val matrix: Matr[Double] = {
        CompoundMatr(Col((m: Array[Array[Double]]).map { r: Array[Double] => DenseVec(Col(r)) }), width)
      }
      val vector: Vec[Double] = DenseVec(Col(v))

      LA().mvm(matrix, vector)(doubleNumer, plusMonoid).items.arr
    }
  }
}
