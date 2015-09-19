package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scala.collection.mutable.WrappedArray

  trait WWrappedArrays extends Base with TypeWrappers { self: WrappersDsl =>
    trait WWrappedArray[T] extends TypeWrapper[WrappedArray[T], WWrappedArray[T]] { self =>
      implicit def eeT: Elem[T];
      def wrappedValueOfBaseType: Rep[WrappedArray[T]]
    };
    trait WWrappedArrayCompanion extends ExCompanion1[WWrappedArray];
    def DefaultOfWrappedArray[T]: Default[WrappedArray[T]] = Default.defaultVal(null)
  }

  trait WWrappedArraysDsl extends WWrappedArraysAbs { self: WrappersDsl =>
    
  }

  trait WWrappedArraysDslExp extends WWrappedArraysExp { self: WrappersDslExp =>
    
  }
}