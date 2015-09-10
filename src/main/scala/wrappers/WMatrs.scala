package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scalanizer.linalgebra.Matr

  trait WMatrs extends Base with TypeWrappers { self: WrappersDsl =>
    trait WMatr[T] extends TypeWrapper[Matr[T], WMatr[T]] { self =>
      implicit def eeT: Elem[T];
      def wrappedValueOfBaseType: Rep[Matr[T]];
      @External def numRows: Rep[Int]
    };
    trait WMatrCompanion extends ExCompanion1[WMatr];
    def DefaultOfMatr[T]: Default[Matr[T]] = Default.defaultVal(null)
  }

  trait WMatrsDsl extends WMatrsAbs { self: WrappersDsl =>
    
  }

  trait WMatrsDslExp extends WMatrsExp { self: WrappersDslExp =>
    
  }
}