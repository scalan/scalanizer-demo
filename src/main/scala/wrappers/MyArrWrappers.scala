package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scalanizer.MyArr

  trait MyArrWrappers extends Base with TypeWrappers { self: WrappersDsl =>
    trait MyArrWrapper[T] extends TypeWrapper[MyArr[T], MyArrWrapper[T]] { self =>
      implicit def eeT: Elem[T];
      def wrappedValueOfBaseType: Rep[MyArr[T]];
      @External def apply(j: Rep[Int]): Rep[T];
      @External def length: Rep[Int]
    };
    trait MyArrWrapperCompanion;
    def DefaultOfMyArr[T]: Default[MyArr[T]] = ???
  }

  trait MyArrWrappersDsl extends MyArrWrappersAbs { self: WrappersDsl =>
    
  }

  trait MyArrWrappersDslSeq extends MyArrWrappersSeq { self: WrappersDslSeq =>
    
  }

  trait MyArrWrappersDslExp extends MyArrWrappersExp { self: WrappersDslExp =>
    
  }
}