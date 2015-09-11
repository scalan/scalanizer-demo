package scalanizer.linalgebra

import scala.reflect.ClassTag
import scalanizer._

trait MatrOp {
  def mvm[T: ClassTag](matrix: Matr[T], vector: Vec[T])
                      (n: Num[T], m: NumMonoid[T]): Vec[T]
}

class BaseMatrOp extends MatrOp {
  def mvm[T: ClassTag](matrix: Matr[T], vector: Vec[T])
                      (n: Num[T], m: NumMonoid[T]): Vec[T] = {
    new DenseVec(matrix.rows.map { r: Vec[T] => r.dot(vector)(n, m) })
  }
}
