package paradise.linalgebra

import scala.reflect.ClassTag
import scalan.HotSpot

trait LinearAlgebraOps { self: LinearAlgebra =>
  trait LinearAlgebraOp {
    def mvm[T: ClassTag](matrix: Matr[T], vector: Vec[T])
                        (implicit n: Numer[T], m: NumMonoid[T]): Vec[T]
  }

  case class LA() extends LinearAlgebraOp {
    def mvm[T: ClassTag](matrix: Matr[T], vector: Vec[T])
                        (implicit n: Numer[T], m: NumMonoid[T]): Vec[T] = {
      DenseVec(matrix.rows.map{ r: Vec[T] => r dot vector })
    }
  }

  object LA {
    @HotSpot
    def ddmvm(m: Array[Array[Double]], v: Array[Double]): Array[Double] = {
      implicit val doubleNumer: Numer[Double] = DoubleNumer()
      implicit val plusMonoid: NumMonoid[Double] = PlusMonoid[Double]

      val width = m(0).length
      val matrix: Matr[Double] = {
        CompoundMatr(Col((m: Array[Array[Double]]).map { r: Array[Double] => DenseVec(Col(r)) }), width)
      }
      val vector: Vec[Double] = DenseVec(Col(v))

      LA().mvm(matrix, vector).items.arr
    }
  }
}
