package wrappers

import scalanizer.MyArr
import scalan._
import scalan.common.Default

trait MyArrWrappers extends Base with TypeWrappers {self: WrappersDsl =>
  trait MyArrWrapper[T] extends TypeWrapper[MyArr[T], MyArrWrapper[T]] {self =>
    def wrappedValueOfBaseType: Rep[MyArr[T]];
    implicit def eeT: Elem[T];
    @External def apply(j: Rep[Int]): Rep[T];
    @External def length: Rep[Int]
  }

  trait MyArrWrapperCompanion

  def DefaultOfMyArr[T:Elem]: Default[MyArr[T]] = null
}

trait MyArrWrappersDsl extends impl.MyArrWrappersAbs {self: WrappersDsl =>

}

trait MyArrWrappersDslSeq extends impl.MyArrWrappersSeq {self: WrappersDslSeq =>

}

trait MyArrWrappersDslExp extends impl.MyArrWrappersExp {self: WrappersDslExp =>

}
