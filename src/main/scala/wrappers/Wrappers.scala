package wrappers {
  import scalan._

  trait WrappersDsl extends ScalanCommunityDsl with WNumsDsl with WColsDsl with WVecsDsl with WDenseVecsDsl with WDoubleNumsDsl with WPlusMonoidsDsl

  trait WrappersDslSeq extends WrappersDsl with ScalanCommunityDslSeq with WNumsDslSeq with WColsDslSeq with WVecsDslSeq with WDenseVecsDslSeq with WDoubleNumsDslSeq with WPlusMonoidsDslSeq

  trait WrappersDslExp extends WrappersDsl with ScalanCommunityDslExp with WNumsDslExp with WColsDslExp with WVecsDslExp with WDenseVecsDslExp with WDoubleNumsDslExp with WPlusMonoidsDslExp
}