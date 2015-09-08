package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scalanizer.linalgebra.DenseVec

  trait WDenseVecs extends Base with TypeWrappers { self: WrappersDsl =>
    trait WDenseVec[T] extends TypeWrapper[DenseVec[T], WDenseVec[T]] { self =>
      implicit def eeT: Elem[T];
      def wrappedValueOfBaseType: Rep[DenseVec[T]]
    };
    trait WDenseVecCompanion extends ExCompanion1[WDenseVec] {
      @Constructor def apply[T](items: Rep[WCol[T]])(implicit emT: Elem[T]): Rep[WDenseVec[T]]
    };
    def DefaultOfDenseVec[T]: Default[DenseVec[T]] = Default.defaultVal(null)
  }

  trait WDenseVecsDsl extends WDenseVecsAbs { self: WrappersDsl =>
    
  }

  trait WDenseVecsDslSeq extends WDenseVecsSeq { self: WrappersDslSeq =>
    
  }

  trait WDenseVecsDslExp extends WDenseVecsExp { self: WrappersDslExp =>
    
  }
}