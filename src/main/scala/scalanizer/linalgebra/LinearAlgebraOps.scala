package scalanizer.linalgebra

import scalanizer._
import scalanizer.collections._
import scala.reflect.ClassTag
import scalan.HotSpot
import scalan.compilation.KernelTypes._


trait LinearAlgebraOp {
  def mvm[T: ClassTag](matrix: Matr[T], vector: Vec[T])
                      (n: Num[T], m: NumMonoid[T]): Vec[T]
}

class LA() extends LinearAlgebraOp {
  def mvm[T: ClassTag](matrix: Matr[T], vector: Vec[T])
                      (n: Num[T], m: NumMonoid[T]): Vec[T] = {
    new DenseVec(matrix.rows.map { r: Vec[T] => r.dot(vector)(n, m) })
  }
}

object LA {
  @HotSpot(CppKernel)
  def ddmvm(m: Array[Array[Double]], v: Array[Double]): Array[Double] = {
    val doubleNumer: Num[Double] = new DoubleNum()
    val zero = doubleNumer.zero
    val plusMonoid = new PlusMonoid(doubleNumer)
    val width = m(0).length

    val vCol = Col(v)
    val vector: Vec[Double] = new DenseVec(vCol)
    val vLen = vector.length

    val matrix = new DenseMatr[Double](Col(m.map(r => new DenseVec(Col(r)))), width)

    val la = new LA()
    //LA().mvm(matrix, vector)(doubleNumer, plusMonoid).items.arr
    v
  }
}

