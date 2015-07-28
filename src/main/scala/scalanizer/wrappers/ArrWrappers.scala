package scalanizer.wrappers

import scalan._
import scalan.common.Default
import scalanizer.MyArr
import impl._

trait MyArrWrappers extends Base with TypeWrappers {self: WrappersDsl =>
  type RepMyArr[T] = Rep[MyArrWrapper[T]]

  trait MyArrWrapper[T] extends TypeWrapper[MyArr[T], MyArrWrapper[T]] {self =>
    def wrappedValueOfBaseType: Rep[MyArr[T]];
    implicit def eeT: Elem[T];
    @External def apply(j: Rep[Int]): Rep[T];
    @External def length: Rep[Int]
  }

  trait MyArrWrapperCompanion

  def DefaultOfMyArr[T:Elem]: Default[MyArr[T]] = {
    null
  }
}

trait MyArrWrappersDsl extends MyArrWrappersAbs {self: WrappersDsl =>

}

trait MyArrWrappersDslSeq extends MyArrWrappersSeq {self: WrappersDslSeq =>

}

trait MyArrWrappersDslExp extends MyArrWrappersExp {self: WrappersDslExp =>

}
