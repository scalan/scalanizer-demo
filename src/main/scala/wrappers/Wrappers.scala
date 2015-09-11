package wrappers {
  import scalan._

  trait WrappersDsl extends ScalanCommunityDsl with WDenseMatrsDsl with WNumsDsl with WBaseMatrOpsDsl with WMatrsDsl with WColsDsl with WNumMonoidsDsl with WVecsDsl with WDenseVecsDsl with WDoubleNumsDsl with WPlusMonoidsDsl

  trait WrappersDslSeq extends WrappersDsl with ScalanCommunityDslSeq

  trait WrappersDslExp extends WrappersDsl with ScalanCommunityDslExp with WDenseMatrsDslExp with WNumsDslExp with WBaseMatrOpsDslExp with WMatrsDslExp with WColsDslExp with WNumMonoidsDslExp with WVecsDslExp with WDenseVecsDslExp with WDoubleNumsDslExp with WPlusMonoidsDslExp
}