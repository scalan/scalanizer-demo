package paradise.linalgebra

import scala.reflect.ClassTag

trait Matrices { self: LinearAlgebra =>
  type Matrix[T] = AbstractMatrix[T]
  trait AbstractMatrix[T] {
    implicit def ctT: ClassTag[T]
    def numColumns: Int
    def numRows: Int
    def rows: Collection[Vector[T]]
    def columns(implicit n: Numeric[T]): Collection[Vector[T]]
    def *(vector: Vector[T])(implicit n: Numeric[T], m: NumMonoid[T]): Vector[T] = {
      DenseVector(rows.map { r: Vector[T] => r.dot(vector) })
    }
  }

  case class CompoundMatrix[T](val rows: Collection[Vector[T]], val numColumns: Int)
                              (implicit val ctT: ClassTag[T])
    extends AbstractMatrix[T] {

    def numRows = rows.length
    def columns(implicit n: Numeric[T]): Collection[Vector[T]] = {
      Collection((0 to numColumns map { (j: Int) =>
        DenseVector(rows.map((vec: Vector[T]) => vec(j))): Vector[T]
      }).toArray)
    }
  }
}
