package wrappers {
  import scalan._

  trait WrappersDsl extends ScalanDsl with WArraysDsl

  trait WrappersDslStd extends ScalanDslStd with WrappersDsl

  trait WrappersDslExp extends ScalanDslExp with WrappersDsl with WArraysDslExp
}