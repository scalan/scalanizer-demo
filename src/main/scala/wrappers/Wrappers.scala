package wrappers {
  import scalan._

  trait WrappersDsl extends ScalanCommunityDsl with WColsDsl with WDenseVecsDsl with WDoubleNumsDsl with WPlusMonoidsDsl

  trait WrappersDslSeq extends WrappersDsl with ScalanCommunityDslSeq with WColsDslSeq with WDenseVecsDslSeq with WDoubleNumsDslSeq with WPlusMonoidsDslSeq

  trait WrappersDslExp extends WrappersDsl with ScalanCommunityDslExp with WColsDslExp with WDenseVecsDslExp with WDoubleNumsDslExp with WPlusMonoidsDslExp
}