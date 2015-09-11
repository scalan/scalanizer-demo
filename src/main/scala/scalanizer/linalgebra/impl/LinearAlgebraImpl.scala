package scalanizer.linalgebra {
  package implOfLinearAlgebra {

  import wrappers.{WrappersDslExp, WrappersDslSeq, WrappersDsl}

  object StagedEvaluation {

      import scalan._
      import scalanizer.linalgebra.implOfLinearAlgebraOps.StagedEvaluation._

      trait LinearAlgebraDsl extends Scalan with WrappersDsl
      with LinearAlgebraOpsDsl

      trait LinearAlgebraDslSeq extends ScalanSeq with WrappersDslSeq
      with LinearAlgebraDsl

      trait LinearAlgebraDslExp extends ScalanExp with WrappersDslExp
      with LinearAlgebraDsl
      with LinearAlgebraOpsDslExp

    }
  }
}
