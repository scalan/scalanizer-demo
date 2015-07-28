package scalanizer.wrappers

import scalan._

trait WrappersDsl extends ScalanCommunityDsl
with MyArrWrappersDsl

trait WrappersDslSeq extends WrappersDsl with ScalanCommunityDslSeq
with MyArrWrappersDslSeq

trait WrappersDslExp extends WrappersDsl with ScalanCommunityDslExp
with MyArrWrappersDslExp
