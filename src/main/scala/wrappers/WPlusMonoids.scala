package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scalanizer.PlusMonoid

  trait WPlusMonoids extends Base with TypeWrappers { self: WrappersDsl =>
    trait WPlusMonoid[T] extends TypeWrapper[PlusMonoid[T], WPlusMonoid[T]] { self =>
      implicit def eeT: Elem[T];
      def wrappedValueOfBaseType: Rep[PlusMonoid[T]]
    };
    trait WPlusMonoidCompanion extends ExCompanion1[WPlusMonoid] {
      @Constructor def apply[T](n: Rep[WNum[T]])(implicit emT: Elem[T]): Rep[WPlusMonoid[T]]
    };
    def DefaultOfPlusMonoid[T]: Default[PlusMonoid[T]] = Default.defaultVal(null)
  }

  trait WPlusMonoidsDsl extends WPlusMonoidsAbs { self: WrappersDsl =>
    
  }

  trait WPlusMonoidsDslSeq extends WPlusMonoidsSeq { self: WrappersDslSeq =>
    
  }

  trait WPlusMonoidsDslExp extends WPlusMonoidsExp { self: WrappersDslExp =>
    
  }
}