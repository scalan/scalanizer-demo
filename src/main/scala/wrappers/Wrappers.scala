package wrappers {
  import scalan._

  trait WrappersDsl extends ScalanCommunityDsl with WCanBuildFromsDsl with WArrayOpssDsl with WGenIterablesDsl with WArraysDsl

  trait WrappersDslSeq extends WrappersDsl with ScalanCommunityDslSeq

  trait WrappersDslExp extends WrappersDsl with ScalanCommunityDslExp with WCanBuildFromsDslExp with WArrayOpssDslExp with WGenIterablesDslExp with WArraysDslExp
}