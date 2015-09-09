package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scalanizer.linalgebra.Vec

  trait WVecs extends Base with TypeWrappers { self: WrappersDsl =>
    trait WVec[T] extends TypeWrapper[Vec[T], WVec[T]] { self =>
      implicit def eeT: Elem[T];
      def wrappedValueOfBaseType: Rep[Vec[T]];
      @External def length: Rep[Int]
    };
    trait WVecCompanion extends ExCompanion1[WVec];
    def DefaultOfVec[T]: Default[Vec[T]] = Default.defaultVal(null)
  }

  trait WVecsDsl extends WVecsAbs { self: WrappersDsl =>
    
  }

  trait WVecsDslSeq extends WVecsSeq { self: WrappersDslSeq =>
    
  }

  trait WVecsDslExp extends WVecsExp { self: WrappersDslExp =>
    
  }
}