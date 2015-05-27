package paradise

import linalgebra._
import paradise.collections.Collections

trait LinearAlgebraExamples extends LinearAlgebra with Collections {
  lazy val ddmvm = (m: Array[Array[Double]], v: Array[Double]) => {
    val width = m(0).length
    val matrix: Matrix[Double] = CompoundMatrix(Collection(m.map { r: Array[Double] => DenseVector(Collection(r)) }), width)
    val vector: Vector[Double] = DenseVector(Collection(v))

    (matrix * vector).items.arr
  }
}
