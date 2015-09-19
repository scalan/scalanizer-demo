package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scala.Array

  trait WArrays extends Base with TypeWrappers { self: WrappersDsl =>
    trait WArray[T] extends TypeWrapper[Array[T], WArray[T]] { self =>
      implicit def eeT: Elem[T];
      def wrappedValueOfBaseType: Rep[Array[T]];
      @External def apply(i: Rep[Int]): Rep[T];
      @External def length: Rep[Int]
    };
    trait WArrayCompanion extends ExCompanion1[WArray] {
      @External def range(start: Rep[Int], end: Rep[Int], step: Rep[Int]): Rep[WArray[Int]];
      @External def canBuildFrom[T](implicit emT: Elem[T]): Rep[WCanBuildFrom[(WArray[_$4] forSome { 
        type _$4
      }), T, WArray[T]]]
    };
    def DefaultOfArray[T]: Default[Array[T]] = Default.defaultVal(null)
  }

  trait WArraysDsl extends WArraysAbs { self: WrappersDsl =>
    
  }

  trait WArraysDslExp extends WArraysExp { self: WrappersDslExp =>
    
  }
}