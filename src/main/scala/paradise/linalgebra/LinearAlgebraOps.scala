package paradise.linalgebra

import scala.reflect.ClassTag
import scalan.HotSpot

trait LinearAlgebraOps { self: LinearAlgebra =>
  trait LinearAlgebraOp {
    def mvm[T: ClassTag](matrix: Matr[T], vector: Vec[T])
                        (implicit n: Numer[T], m: NumMonoid[T]): Vec[T]
  }

  case class LA() extends LinearAlgebraOp {
    def mvm[T: ClassTag](matrix: Matr[T], vector: Vec[T])
                        (implicit n: Numer[T], m: NumMonoid[T]): Vec[T] = {
      DenseVec(matrix.rows.map{ r: Vec[T] => r dot vector })
    }
  }

  object LA {
    @HotSpot
    def ddmvm0(m: Array[Array[Double]], v: Array[Double]): Array[Double] = {
      implicit val doubleNumer: Numer[Double] = DoubleNumer()
      implicit val plusMonoid: NumMonoid[Double] = PlusMonoid[Double]

      val width = m(0).length
      val matrix: Matr[Double] = {
        CompoundMatr(Col(m.map { r: Array[Double] => DenseVec(Col(r)) }), width)
      }
      val vector: Vec[Double] = DenseVec(Col(v))

      LA().mvm(matrix, vector).items.arr
    }
  }
}

/*
  def ddmvm0(m: Array[Array[Double]], v: Array[Double]) = {
    import scalan._
    import scalan.compilation.lms._
    import scalan.compilation.lms.scalac.CommunityLmsCompilerScala
    import paradise.linalgebra.implOfLinearAlgebra.StagedEvaluation._

    trait TVirt { self: LinearAlgebraDsl =>
      lazy val ddmvm0Virt = fun(((in: Rep[scala.Tuple2[Array[Array[Double]], Array[Double]]]) => {
        implicit val doubleNumer: Numer[Double] = DoubleNumer();
        implicit val plusMonoid: NumMonoid[Double] = PlusMonoid[Double];
        val m: Rep[Array[Array[Double]]] = in._1;
        val v: Rep[Array[Double]] = in._2;
        {
          val width = m(toRep(0)).length;
          val matrix: Rep[AbstractMatrix[Double]] = CompoundMatrix(Collection(array_map(m,fun(((r: Rep[Array[Double]]) => DenseVector(Collection(r)))))), width);
          val vector: Rep[AbstractVector[Double]] = DenseVector(Collection(v));
          BaseOp().mvm(matrix, vector).items.arr
        }
      }))
    }
    class ProgExp extends TVirt with LinearAlgebraDslExp
                  with ScalanCommunityDslExp
                  with ScalanCtxExp
                  with CommunityLmsCompilerScala
                  with CommunityBridge {
      val lms = new CommunityLmsBackend
    }
    val progStaged = new ProgExp
    progStaged.buildExecutable()
  }
*/