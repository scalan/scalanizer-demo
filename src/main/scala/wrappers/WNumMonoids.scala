package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scalanizer.NumMonoid

  trait WNumMonoids extends Base with TypeWrappers { self: WrappersDsl =>
    trait WNumMonoid[T] extends TypeWrapper[NumMonoid[T], WNumMonoid[T]] { self =>
      implicit def eeT: Elem[T];
      def wrappedValueOfBaseType: Rep[NumMonoid[T]];
      @External def opName: Rep[String]
    };
    trait WNumMonoidCompanion extends ExCompanion1[WNumMonoid];
    def DefaultOfNumMonoid[T]: Default[NumMonoid[T]] = Default.defaultVal(null)
  }

  trait WNumMonoidsDsl extends WNumMonoidsAbs { self: WrappersDsl =>
    
  }

  trait WNumMonoidsDslExp extends WNumMonoidsExp { self: WrappersDslExp =>
    
  }
}