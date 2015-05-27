package linalgebra

trait Matrices { self: LinearAlgebra =>
  type Matrix[T] = AbstractMatrix[T]
  trait AbstractMatrix[T] {
    def numColumns: Int
    def numRows: Int
    def rows: Collection[AbstractVector[T]]
    def *(vector: Vector[T])(implicit n: Numeric[T]): Vector[T] = {
      DenseVector(rows.map { r => r.dot(vector) })
    }
  }

  case class CompoundMatrix[T](val rows: Collection[AbstractVector[T]], val numColumns: Int)
    extends AbstractMatrix[T] {

    def numRows = rows.length

    def *(matrix: Matrix[T])(implicit n: Numeric[T]): Matrix[T] = {
      val mT = matrix.companion.fromRows(matrix.columns, matrix.numRows)
      companion(self.rows.map(row => mT * row), matrix.numColumns)
    }
  }
}
