package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scala.collection.GenIterable

  trait WGenIterables extends Base with TypeWrappers { self: WrappersDsl =>
    trait WGenIterable[A] extends TypeWrapper[GenIterable[A], WGenIterable[A]] { self =>
      implicit def eeA: Elem[A];
      def wrappedValueOfBaseType: Rep[GenIterable[A]]
    };
    trait WGenIterableCompanion extends ExCompanion1[WGenIterable];
    def DefaultOfGenIterable[A]: Default[GenIterable[A]] = Default.defaultVal(null)
  }

  trait WGenIterablesDsl extends WGenIterablesAbs { self: WrappersDsl =>
    
  }

  trait WGenIterablesDslExp extends WGenIterablesExp { self: WrappersDslExp =>
    
  }
}