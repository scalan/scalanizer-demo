package wrappers {
  import scalan._

  trait WrappersDsl extends ScalanCommunityDsl with WCanBuildFromsDsl with WArrayOpssDsl with WWrappedArraysDsl with WGenIterablesDsl with WArraysDsl

  trait WrappersDslSeq extends WrappersDsl with ScalanCommunityDslSeq

  trait WrappersDslExp extends WrappersDsl with ScalanCommunityDslExp with WCanBuildFromsDslExp with WArrayOpssDslExp with WWrappedArraysDslExp with WGenIterablesDslExp with WArraysDslExp
}