package paradise

import paradise.linalgebra._

import scala.reflect.ClassTag
import scala.util.Random

trait LinearAlgebraExamples extends LinearAlgebra {

  trait LinearAlgebraExample

  object CommonData {
    val rnd = new Random(1)

    def genArray(len: Int): Array[Double] = {
      { for (i <- 1 to len) yield { rnd.nextDouble() } }.toArray
    }

    def genMatr(rows: Int, cols: Int): Array[Array[Double]] = {
      { for (i <- 1 to rows) yield { genArray(cols) } }.toArray
    }
  }

  case class MvMExample() extends LinearAlgebraExample {
    implicit val doubleNumer: Numer[Double] = DoubleNumer()
    implicit val plusMonoid: NumMonoid[Double] = PlusMonoid[Double]

    def mvm[T: ClassTag](matrix: AbstractMatrix[T], vector: AbstractVector[T])
              (implicit n: Numer[T], m: NumMonoid[T]): AbstractVector[T] = {
      DenseVector(matrix.rows.map{ r: AbstractVector[T] => r dot vector })
    }

    def ddmvm(m: Array[Array[Double]], v: Array[Double]): Array[Double] = {
      val width = m(0).length
      val matrix: AbstractMatrix[Double] = CompoundMatrix(Collection(m.map { r: Array[Double] => DenseVector(Collection(r)) }), width)
      val vector: AbstractVector[Double] = DenseVector(Collection(v))
      mvm(matrix, vector).items.arr
    }

    lazy val ddmvm0 = ddmvm(_,_)
  }
}
