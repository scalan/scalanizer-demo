package paradise.linalgebra {
  package implOfLinearAlgebra {
    object StagedEvaluation {

      import scalan._
      import paradise.implOfNumers.StagedEvaluation._
      import paradise.implOfNumMonoids.StagedEvaluation._
      import paradise.collections.implOfCollections.StagedEvaluation._
      import paradise.linalgebra.implOfVectors.StagedEvaluation._
      import paradise.linalgebra.implOfMatrices.StagedEvaluation._
      import paradise.linalgebra.implOfLinearAlgebraOps.StagedEvaluation._

      trait LinearAlgebraDsl extends Scalan
      with NumersDsl
      with NumMonoidsDsl
      with CollectionsDsl
      with VectorsDsl
      with MatricesDsl
      with LinearAlgebraOpsDsl

      trait LinearAlgebraDslSeq extends ScalanSeq with LinearAlgebraDsl
      with NumersDslSeq
      with NumMonoidsDslSeq
      with CollectionsDslSeq
      with VectorsDslSeq
      with MatricesDslSeq
      with LinearAlgebraOpsDslSeq

      trait LinearAlgebraDslExp extends ScalanExp with LinearAlgebraDsl
      with NumersDslExp
      with NumMonoidsDslExp
      with CollectionsDslExp
      with VectorsDslExp
      with MatricesDslExp
      with LinearAlgebraOpsDslExp

    }
  }
}
