package wrappers {
  import scalan._

  trait WrappersDsl extends ScalanCommunityDsl with WColsDsl with WDoubleNumsDsl with WPlusMonoidsDsl

  trait WrappersDslSeq extends WrappersDsl with ScalanCommunityDslSeq with WColsDslSeq with WDoubleNumsDslSeq with WPlusMonoidsDslSeq

  trait WrappersDslExp extends WrappersDsl with ScalanCommunityDslExp with WColsDslExp with WDoubleNumsDslExp with WPlusMonoidsDslExp
}