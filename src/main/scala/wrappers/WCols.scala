package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scalanizer.collections.Col

  trait WCols extends Base with TypeWrappers { self: WrappersDsl =>
    trait WCol[T] extends TypeWrapper[Col[T], WCol[T]] { self =>
      implicit def eeT: Elem[T];
      def wrappedValueOfBaseType: Rep[Col[T]];
      @External def arr: Rep[Array[T]]
    };
    trait WColCompanion extends ExCompanion1[WCol] {
      @External def apply[T](arr: Rep[Array[T]])(implicit emT: Elem[T]): Rep[WCol[T]]
    };
    def DefaultOfCol[T]: Default[Col[T]] = Default.defaultVal(null)
  }

  trait WColsDsl extends WColsAbs { self: WrappersDsl =>
    
  }

  trait WColsDslExp extends WColsExp { self: WrappersDslExp =>
    
  }
}