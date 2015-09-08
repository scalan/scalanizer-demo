package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scalanizer.PlusMonoid

  trait WPlusMonoids extends Base with TypeWrappers { self: WrappersDsl =>
    trait WPlusMonoid[A] extends TypeWrapper[PlusMonoid[A], WPlusMonoid[A]] { self =>
      implicit def eeA: Elem[A];
      def wrappedValueOfBaseType: Rep[PlusMonoid[A]]
    };
    trait WPlusMonoidCompanion extends ExCompanion1[WPlusMonoid] {
      @Constructor def apply[A](n: Rep[WNum[A]])(implicit emA: Elem[A]): Rep[WPlusMonoid[A]]
    };
    def DefaultOfPlusMonoid[A]: Default[PlusMonoid[A]] = Default.defaultVal(null)
  }

  trait WPlusMonoidsDsl extends WPlusMonoidsAbs { self: WrappersDsl =>
    
  }

  trait WPlusMonoidsDslSeq extends WPlusMonoidsSeq { self: WrappersDslSeq =>
    
  }

  trait WPlusMonoidsDslExp extends WPlusMonoidsExp { self: WrappersDslExp =>
    
  }
}