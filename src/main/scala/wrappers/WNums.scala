package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scalanizer.Num

  trait WNums extends Base with TypeWrappers { self: WrappersDsl =>
    trait WNum[T] extends TypeWrapper[Num[T], WNum[T]] { self =>
      implicit def eeT: Elem[T];
      def wrappedValueOfBaseType: Rep[Num[T]];
      @External def zero: Rep[T]
    };
    trait WNumCompanion extends ExCompanion1[WNum];
    def DefaultOfNum[T]: Default[Num[T]] = Default.defaultVal(null)
  }

  trait WNumsDsl extends WNumsAbs { self: WrappersDsl =>
    
  }

  trait WNumsDslExp extends WNumsExp { self: WrappersDslExp =>
    
  }
}