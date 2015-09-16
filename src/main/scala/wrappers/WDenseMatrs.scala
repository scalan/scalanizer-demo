package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scalanizer.linalgebra.DenseMatr

  trait WDenseMatrs extends Base with TypeWrappers { self: WrappersDsl =>
    trait WDenseMatr[T] extends TypeWrapper[DenseMatr[T], WDenseMatr[T]] with WMatr[T] { self =>
      implicit def eeT: Elem[T];
      def wrappedValueOfBaseType: Rep[DenseMatr[T]]
    };
    trait WDenseMatrCompanion extends ExCompanion1[WDenseMatr] {
      @Constructor def apply[T](rows: Rep[WCol[WVec[T]]], numColumns: Rep[Int])(implicit emT: Elem[T]): Rep[WDenseMatr[T]]
    };
    def DefaultOfDenseMatr[T]: Default[DenseMatr[T]] = Default.defaultVal(null)
  }

  trait WDenseMatrsDsl extends WDenseMatrsAbs { self: WrappersDsl =>
    
  }

  trait WDenseMatrsDslExp extends WDenseMatrsExp { self: WrappersDslExp =>
    
  }
}