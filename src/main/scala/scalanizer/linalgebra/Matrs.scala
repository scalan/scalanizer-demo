package scalanizer.linalgebra

import scalanizer._
import scalanizer.collections._
import scala.reflect.ClassTag

trait Matrs {self: LinearAlgebra =>

  trait Matr[T] {
    def numColumns: Int
    def numRows: Int
    def rows: Col[Vec[T]]
    def columns(implicit n: Num[T]): Col[Vec[T]]
  }

  class DenseMatr[T: ClassTag](val rows: Col[Vec[T]], val numColumns: Int) extends Matr[T] {
    def numRows = rows.length
    def columns(implicit n: Num[T]): Col[Vec[T]] = {
      Col((Array.range(0, numColumns, 1): Array[Int]).map { (j: Int) =>
        new DenseVec(rows.map((vec: Vec[T]) => vec(j))): Vec[T]
      })
    }
  }
}

