package scalanizer.linalgebra {
  package implOfLinearAlgebra {
    import wrappers._

    import scalanizer.collections.implOfCols.StagedEvaluation._

    object StagedEvaluation {
      import scalan._;
      trait LinearAlgebraDsl extends Scalan with WrappersDsl with ColsDsl;
      trait LinearAlgebraDslStd extends WrappersDslStd with LinearAlgebraDsl;
      trait LinearAlgebraDslExp extends WrappersDslExp with LinearAlgebraDsl with ColsDslExp
    }
  }
}