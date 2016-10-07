package wrappers {
  import scalan._

  import impl._

  import scala.Array

  trait WArrays extends Base with TypeWrappers { self: WrappersDsl =>
    trait WArray[T] extends TypeWrapper[Array[T], WArray[T]] { self =>
      implicit def eeT: Elem[T];
      def wrappedValue: Rep[Array[T]];
      @External def apply(i: Rep[Int]): Rep[T];
      @External def length: Rep[Int]
    };
    trait WArrayCompanion extends ExCompanion1[WArray]
  }

  trait WArraysDsl extends WArraysAbs { self: WrappersDsl =>
    
  }

  trait WArraysDslExp extends WArraysExp { self: WrappersDslExp =>
    
  }
}