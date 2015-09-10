package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scalanizer.NumMonoid

  trait WNumMonoids extends Base with TypeWrappers { self: WrappersDsl =>
    trait WNumMonoid[A] extends TypeWrapper[NumMonoid[A], WNumMonoid[A]] { self =>
      implicit def eeA: Elem[A];
      def wrappedValueOfBaseType: Rep[NumMonoid[A]];
      @External def opName: Rep[String]
    };
    trait WNumMonoidCompanion extends ExCompanion1[WNumMonoid];
    def DefaultOfNumMonoid[A]: Default[NumMonoid[A]] = Default.defaultVal(null)
  }

  trait WNumMonoidsDsl extends WNumMonoidsAbs { self: WrappersDsl =>
    
  }

  trait WNumMonoidsDslSeq extends WNumMonoidsSeq { self: WrappersDslSeq =>
    
  }

  trait WNumMonoidsDslExp extends WNumMonoidsExp { self: WrappersDslExp =>
    
  }
}