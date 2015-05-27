package linalgebra

trait LinearAlgebraExamples extends LinearAlgebra {
  lazy val ddmvm = (m: Array[Array[Double]], v: Array[Double]) => {
    val width = m(0).length
    val matrix: Matrix[Double] = CompoundMatrix(Collection(m.map { r: Array[Double] => DenseVector(Collection(r)) }), width)
    val vector: Vector[Double] = DenseVector(Collection(v))

    (matrix * vector).items.arr
  }

  lazy val dsmvm = (m: Array[Array[Double]], vIs: Array[Int], vVs: Array[Double], vL: Int) => {
    val width = m(0).length
    val matrix: Matrix[Double] = CompoundMatrix(Collection(m.map { r: Array[Double] => DenseVector(Collection(r)) }), width)
    val vector: Vector[Double] = SparseVector(Collection(vIs), Collection(vVs), vL)

    (matrix * vector).items.arr
  }

  lazy val sdmvm = (m: Array[(Array[Int], Array[Double], Int)], v: Array[Double]) => {
    val width = m(0)._3
    val matrix: Matrix[Double] = CompoundMatrix(Collection(m.map {
      r: (Array[Int], Array[Double], Int) => SparseVector(Collection(r._1), Collection(r._2), r._3)
    }), width)
    val vector: Vector[Double] = DenseVector(Collection(v))

    (matrix * vector).items.arr
  }

  lazy val ssmvm = (m: Array[(Array[Int], Array[Double], Int)], vIs: Array[Int], vVs: Array[Double], vL: Int) => {
    val width = m(0)._3
    val matrix: Matrix[Double] = CompoundMatrix(Collection(m.map {
      r: (Array[Int], Array[Double], Int) => SparseVector(Collection(r._1), Collection(r._2), r._3)
    }), width)
    val vector: Vector[Double] = SparseVector(Collection(vIs), Collection(vVs), vL)

    (matrix * vector).items.arr
  }
}
