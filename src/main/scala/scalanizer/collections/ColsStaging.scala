package scalanizer.collections

import wrappers.WrappersDslExp

import scalan.ScalanExp

trait ColsStaging extends ScalanExp { self: WrappersDslExp =>
  override def rewriteDef[T](d: Def[T]): Exp[_] = d match {
//    case WArrayMethods.length(arr) =>
//      array_length(arr.wrappedValue)
    case _ =>
      super.rewriteDef(d)
  }
}
