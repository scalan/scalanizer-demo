package paradise.linalgebra

trait Matrices { self: LinearAlgebra =>
  type Matrix[T] = AbstractMatrix[T]
  trait AbstractMatrix[T] {
    def numColumns: Int
    def numRows: Int
    def rows: Collection[Vector[T]]
    def columns(implicit n: Numeric[T]): Collection[Vector[T]]
    def *(vector: Vector[T])(implicit n: Numeric[T]): Vector[T] = {
      DenseVector(rows.map { r: Vector[T] => r.dot(vector) })
    }
  }

  case class CompoundMatrix[T](val rows: Collection[Vector[T]], val numColumns: Int)
    extends AbstractMatrix[T] {

    def numRows = rows.length
    def columns(implicit n: Numeric[T]): Collection[Vector[T]] = {
      ??? //Collection(SArray.tabulate(numColumns) { j => DenseVector(rows.map(_(j)))})
    }

    def *(matrix: Matrix[T])(implicit n: Numeric[T]): Matrix[T] = {
      val mT = CompoundMatrix(matrix.columns, matrix.numRows)
      CompoundMatrix(this.rows.map((row: Vector[T]) => mT * row), matrix.numColumns)
    }
  }
}
