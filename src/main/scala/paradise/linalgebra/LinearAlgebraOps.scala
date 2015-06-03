package paradise.linalgebra

import scala.reflect.ClassTag

trait LinearAlgebraOps { self: LinearAlgebra =>
  trait LinearAlgebraOp {
    def mvm[T: ClassTag](matrix: AbstractMatrix[T], vector: AbstractVector[T])
                        (implicit n: Numer[T], m: NumMonoid[T]): AbstractVector[T]
  }

  case class BaseOp() extends LinearAlgebraOp {
    def mvm[T: ClassTag](matrix: AbstractMatrix[T], vector: AbstractVector[T])
                        (implicit n: Numer[T], m: NumMonoid[T]): AbstractVector[T] = {
      DenseVector(matrix.rows.map{ r: AbstractVector[T] => r dot vector })
    }
  }
}
