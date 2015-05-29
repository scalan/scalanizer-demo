package paradise

import paradise.linalgebra._

trait LinearAlgebraExamples extends LinearAlgebra {

  trait LinearAlgebraExample {
    implicit val plusMonoid = PlusMonoid[Double]

    def ddmvm(m: Array[Array[Double]], v: Array[Double]) = {
      val width = m(0).length
      val matrix: Matrix[Double] = CompoundMatrix(Collection(m.map { r: Array[Double] => DenseVector(Collection(r)) }), width)
      val vector: Vector[Double] = DenseVector(Collection(v))

      (matrix * vector).items.arr
    }
  }

}
