package scalanizer.wrappers

import scalan._
import scalanizer.MyArr

trait MyArrWrappers extends Base with TypeWrappers {self: WrappersDsl =>
  trait MyArrWrapper[T] extends TypeWrapper[MyArr[T], MyArrWrapper[T]] {self =>
    def wrappedValueOfBaseType: Rep[MyArr[T]];
    implicit def eeT: Elem[T];
    @External def apply(j: Rep[Int]): Rep[T];
    @External def length: Rep[Int]
  }
}

trait MyArrWrappersDsl extends MyArrWrappersAbs {self: WrappersDsl =>

}

trait MyArrWrappersDslSeq extends MyArrWrappersSeq {self: WrappersDslSeq =>

}

trait MyArrWrappersDslExp extends MyArrWrappersExp {self: WrappersDslExp =>

}
