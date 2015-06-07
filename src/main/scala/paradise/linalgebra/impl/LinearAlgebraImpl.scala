package paradise.linalgebra {
  package implOfLinearAlgebra {
    object StagedEvaluation {

      import scalan._
      import paradise.implOfNums.StagedEvaluation._
      import paradise.implOfNumMonoids.StagedEvaluation._
      import paradise.collections.implOfCols.StagedEvaluation._
      import paradise.linalgebra.implOfVecs.StagedEvaluation._
      import paradise.linalgebra.implOfMatrs.StagedEvaluation._
      import paradise.linalgebra.implOfLinearAlgebraOps.StagedEvaluation._

      trait LinearAlgebraDsl extends Scalan
      with NumsDsl
      with NumMonoidsDsl
      with ColsDsl
      with VecsDsl
      with MatrsDsl
      with LinearAlgebraOpsDsl

      trait LinearAlgebraDslSeq extends ScalanSeq with LinearAlgebraDsl
      with NumsDslSeq
      with NumMonoidsDslSeq
      with ColsDslSeq
      with VecsDslSeq
      with MatrsDslSeq
      with LinearAlgebraOpsDslSeq

      trait LinearAlgebraDslExp extends ScalanExp with LinearAlgebraDsl
      with NumsDslExp
      with NumMonoidsDslExp
      with ColsDslExp
      with VecsDslExp
      with MatrsDslExp
      with LinearAlgebraOpsDslExp

    }
  }
}
