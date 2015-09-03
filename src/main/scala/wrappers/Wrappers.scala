package wrappers {
  import scalan._

trait WrappersDsl extends ScalanCommunityDsl with WMyArrsDsl

trait WrappersDslSeq extends WrappersDsl with ScalanCommunityDslSeq with WMyArrsDslSeq

trait WrappersDslExp extends WrappersDsl with ScalanCommunityDslExp with WMyArrsDslExp
}