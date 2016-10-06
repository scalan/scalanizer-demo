package wrappers {
  import scalan._

  trait WrappersDsl extends ScalanDsl with WCanBuildFromsDsl with WArrayOpssDsl with WWrappedArraysDsl with WGenIterablesDsl with WPredefsDsl with WArraysDsl

  trait WrappersDslStd extends ScalanDslStd with WrappersDsl

  trait WrappersDslExp extends ScalanDslExp with WrappersDsl with WCanBuildFromsDslExp with WArrayOpssDslExp with WWrappedArraysDslExp with WGenIterablesDslExp with WPredefsDslExp with WArraysDslExp
}