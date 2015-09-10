package wrappers {
  import scalan._

  trait WrappersDsl extends ScalanCommunityDsl with WDenseMatrsDsl with WNumsDsl with WLAsDsl with WMatrsDsl with WColsDsl with WNumMonoidsDsl with WVecsDsl with WDenseVecsDsl with WDoubleNumsDsl with WPlusMonoidsDsl

  trait WrappersDslSeq extends WrappersDsl with ScalanCommunityDslSeq with WDenseMatrsDslSeq with WNumsDslSeq with WLAsDslSeq with WMatrsDslSeq with WColsDslSeq with WNumMonoidsDslSeq with WVecsDslSeq with WDenseVecsDslSeq with WDoubleNumsDslSeq with WPlusMonoidsDslSeq

  trait WrappersDslExp extends WrappersDsl with ScalanCommunityDslExp with WDenseMatrsDslExp with WNumsDslExp with WLAsDslExp with WMatrsDslExp with WColsDslExp with WNumMonoidsDslExp with WVecsDslExp with WDenseVecsDslExp with WDoubleNumsDslExp with WPlusMonoidsDslExp
}