package wrappers {
  import scalan._

  import impl._

  import scala.collection.mutable.WrappedArray

  trait WWrappedArrays extends Base with TypeWrappers { self: WrappersDsl =>
    trait WWrappedArray[T] extends TypeWrapper[WrappedArray[T], WWrappedArray[T]] { self =>
      implicit def eeT: Elem[T];
      def wrappedValue: Rep[WrappedArray[T]]
    };
    trait WWrappedArrayCompanion extends ExCompanion1[WWrappedArray]
  }

  trait WWrappedArraysDsl extends WWrappedArraysAbs { self: WrappersDsl =>
    
  }

  trait WWrappedArraysDslExp extends WWrappedArraysExp { self: WrappersDslExp =>
    
  }
}