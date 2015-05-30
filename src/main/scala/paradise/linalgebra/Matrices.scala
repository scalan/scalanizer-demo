package paradise.linalgebra

import scala.reflect.ClassTag

trait Matrices { self: LinearAlgebra =>
  type Matrix[T] = AbstractMatrix[T]
  trait AbstractMatrix[T] {
    implicit def ctT: ClassTag[T]
    def numColumns: Int
    def numRows: Int
    def rows: Collection[AbstractVector[T]]
    def columns(implicit n: Numeric[T]): Collection[AbstractVector[T]]
    def *(vector: AbstractVector[T])(implicit n: Numeric[T], m: NumMonoid[T]): AbstractVector[T] = {
      DenseVector(rows.map { r: AbstractVector[T] => r.dot(vector) })
    }
  }

  case class CompoundMatrix[T](val rows: Collection[AbstractVector[T]], val numColumns: Int)
                              (implicit val ctT: ClassTag[T])
    extends AbstractMatrix[T] {

    def numRows = rows.length
    def columns(implicit n: Numeric[T]): Collection[AbstractVector[T]] = {
      Collection((0 to numColumns map { (j: Int) =>
        DenseVector(rows.map((vec: AbstractVector[T]) => vec(j))): AbstractVector[T]
      }).toArray)
    }
  }
}
