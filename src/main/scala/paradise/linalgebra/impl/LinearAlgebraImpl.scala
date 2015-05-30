package paradise.linalgebra {
  package implOfLinearAlgebra {
    object StagedEvaluation {

      import scalan._
      import paradise.implOfNumMonoids.StagedEvaluation._
      import paradise.collections.implOfCollections.StagedEvaluation._
      import paradise.linalgebra.implOfVectors.StagedEvaluation._
      import paradise.linalgebra.implOfMatrices.StagedEvaluation._

      trait LinearAlgebraDsl extends Scalan
      with NumMonoidsDsl
      with CollectionsDsl
      with VectorsDsl
      with MatricesDsl

      trait LinearAlgebraDslSeq extends ScalanSeq with LinearAlgebraDsl
      with NumMonoidsDslSeq
      with CollectionsDslSeq
      with VectorsDslSeq
      with MatricesDslSeq

      trait LinearAlgebraDslExp extends ScalanExp with LinearAlgebraDsl
      with NumMonoidsDslExp
      with CollectionsDslExp
      with VectorsDslExp
      with MatricesDslExp

    }
  }
}
