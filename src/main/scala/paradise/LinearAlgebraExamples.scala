package paradise

import paradise.linalgebra._

import scala.reflect.ClassTag

trait LinearAlgebraExamples extends LinearAlgebra {

  trait LinearAlgebraExample

  case class MvMExample() extends LinearAlgebraExample {
    implicit val doubleNumer: Numer[Double] = DoubleNumer()
    implicit val plusMonoid: NumMonoid[Double] = PlusMonoid[Double]

    def mvm[T: ClassTag](matrix: AbstractMatrix[T], vector: AbstractVector[T])
              (implicit n: Numer[T], m: NumMonoid[T]): AbstractVector[T] = {
      DenseVector(matrix.rows.map(r => r dot vector))
    }

    lazy val ddmvm0 = (m: Array[Array[Double]], v: Array[Double]) => {
      val width = m(0).length
      val matrix: AbstractMatrix[Double] = CompoundMatrix(Collection(m.map { r: Array[Double] => DenseVector(Collection(r)) }), width)
      val vector: AbstractVector[Double] = DenseVector(Collection(v))
      mvm(matrix, vector).items.arr
    }
  }
}
