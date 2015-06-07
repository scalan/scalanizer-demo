package paradise.linalgebra

import scala.reflect.ClassTag

trait Matrs { self: LinearAlgebra =>
  trait Matr[T] {
    implicit def ctT: ClassTag[T]
    def numColumns: Int
    def numRows: Int
    def rows: Col[Vec[T]]
    def columns(implicit n: Numer[T]): Col[Vec[T]]
  }

  case class CompoundMatr[T](val rows: Col[Vec[T]], val numColumns: Int)
                              (implicit val ctT: ClassTag[T])
    extends Matr[T] {

    def numRows = rows.length
    def columns(implicit n: Numer[T]): Col[Vec[T]] = {
      Col((Array.range(0, numColumns, 1): Array[Int]).map { (j: Int) =>
        DenseVec(rows.map((vec: Vec[T]) => vec(j))): Vec[T]
      })
    }
  }
}
