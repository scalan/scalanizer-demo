//package wrappers {
//  import scalan._
//
//  import scalan.common.Default
//
//  import impl._
//
//  import scalanizer.linalgebra.LA
//
//  trait WLAs extends Base with TypeWrappers { self: WrappersDsl =>
//    trait WLA extends TypeWrapper[LA, WLA] { self =>
//      def wrappedValueOfBaseType: Rep[LA];
//      @External def mvm[T](matrix: Rep[WMatr[T]], vector: Rep[WVec[T]])(n: Rep[WNum[T]], m: Rep[WNumMonoid[T]])(implicit emT: Elem[T]): Rep[WVec[T]]
//    };
//    trait WLACompanion extends ExCompanion0[WLA] {
//      @Constructor def apply: Rep[WLA]
//    };
//    def DefaultOfLA: Default[LA] = Default.defaultVal(null)
//  }
//
//  trait WLAsDsl extends WLAsAbs { self: WrappersDsl =>
//
//  }
//
//  trait WLAsDslExp extends WLAsExp { self: WrappersDslExp =>
//
//  }
//}