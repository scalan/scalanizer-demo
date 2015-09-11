package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scalanizer.linalgebra.BaseMatrOp

  trait WBaseMatrOps extends Base with TypeWrappers { self: WrappersDsl =>
    trait WBaseMatrOp extends TypeWrapper[BaseMatrOp, WBaseMatrOp] { self =>
      def wrappedValueOfBaseType: Rep[BaseMatrOp];
      @External def mvm[T](matrix: Rep[WMatr[T]], vector: Rep[WVec[T]])(n: Rep[WNum[T]], m: Rep[WNumMonoid[T]])(implicit emT: Elem[T]): Rep[WVec[T]]
    };
    trait WBaseMatrOpCompanion extends ExCompanion0[WBaseMatrOp] {
      @Constructor def apply: Rep[WBaseMatrOp]
    };
    def DefaultOfBaseMatrOp: Default[BaseMatrOp] = Default.defaultVal(null)
  }

  trait WBaseMatrOpsDsl extends WBaseMatrOpsAbs { self: WrappersDsl =>
    
  }

  trait WBaseMatrOpsDslExp extends WBaseMatrOpsExp { self: WrappersDslExp =>
    
  }
}