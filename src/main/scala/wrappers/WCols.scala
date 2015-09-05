package wrappers {

import scalan._

import scalan.common.Default

import impl._

import scalanizer.collections.Col

trait WCols extends Base with TypeWrappers {
  self: WrappersDsl =>

  trait WCol[A] extends TypeWrapper[Col[A], WCol[A]] {
    self =>
    implicit def eeA: Elem[A];

    def wrappedValueOfBaseType: Rep[Col[A]]
  };

  trait WColCompanion extends ExCompanion1[WCol] {
    @External def apply[T](arr: Rep[Array[T]])(implicit emT: Elem[T]): Rep[WCol[T]]
  };

  def DefaultOfCol[A]: Default[Col[A]] = Default.defaultVal(null)
}

trait WColsDsl extends WColsAbs {
  self: WrappersDsl =>

}

trait WColsDslSeq extends WColsSeq {
  self: WrappersDslSeq =>

}

trait WColsDslExp extends WColsExp {
  self: WrappersDslExp =>

}

}