package wrappers {
  import scalan._

  import impl._

  import scala.Predef

  trait WPredefs extends Base with TypeWrappers { self: WrappersDsl =>
    trait WPredef extends TypeWrapper[Predef.type, WPredef] { self =>
      def wrappedValue: Rep[Predef.type]
    };
    trait WPredefCompanion extends ExCompanion0[WPredef] {
      @External def intArrayOps(xs: Rep[WArray[Int]]): Rep[WArrayOps[Int]];
      @External def refArrayOps[T](xs: Rep[WArray[T]])(implicit emT: Elem[T]): Rep[WArrayOps[T]];
      @External def genericWrapArray[T](xs: Rep[WArray[T]])(implicit emT: Elem[T]): Rep[WWrappedArray[T]];
      @External def genericArrayOps[T](xs: Rep[WArray[T]])(implicit emT: Elem[T]): Rep[WArrayOps[T]]
    }
  }

  trait WPredefsDsl extends WPredefsAbs { self: WrappersDsl =>
    
  }

  trait WPredefsDslExp extends WPredefsExp { self: WrappersDslExp =>
    
  }
}