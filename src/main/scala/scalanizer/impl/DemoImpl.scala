package scalanizer {
  package implOfDemo {

    object StagedEvaluation {

      import scalan._
      import wrappers._
      import scalanizer.implOfCols.StagedEvaluation._

      trait DemoDsl extends Scalan with WrappersDsl
      with ColsDsl

      trait DemoDslSeq extends ScalanSeq with DemoDsl
      with WrappersDslSeq
      with ColsDslSeq

      trait DemoDslExp extends ScalanExp with DemoDsl
      with WrappersDslExp
      with ColsDslExp
    }
  }
}
