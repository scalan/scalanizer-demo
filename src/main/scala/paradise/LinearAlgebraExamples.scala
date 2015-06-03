package paradise

import paradise.linalgebra._

trait LinearAlgebraExamples extends LinearAlgebra {
  implicit val doubleNumer: Numer[Double] = DoubleNumer()
  implicit val plusMonoid: NumMonoid[Double] = PlusMonoid[Double]

  //@hotspot
  def ddmvm0(m: Array[Array[Double]], v: Array[Double]) = {
    val width = m(0).length
    val matrix: AbstractMatrix[Double] = CompoundMatrix(Collection(m.map { r: Array[Double] => DenseVector(Collection(r)) }), width)
    val vector: AbstractVector[Double] = DenseVector(Collection(v))

    BaseOp().mvm(matrix, vector).items.arr
  }
}
