package wrappers {
  import scalan._

  import impl._

  import scala.collection.mutable.ArrayOps

  trait WArrayOpss extends Base with TypeWrappers { self: WrappersDsl =>
    trait WArrayOps[T] extends TypeWrapper[ArrayOps[T], WArrayOps[T]] { self =>
      implicit def eeT: Elem[T];
      def wrappedValue: Rep[ArrayOps[T]];
      @External def zip[A1, B, That](that: Rep[WGenIterable[B]])(bf: Rep[WCanBuildFrom[WArray[T], scala.Tuple2[A1, B], That]])(implicit emA1: Elem[A1], emB: Elem[B], emThat: Elem[That]): Rep[That];
      @External def reduce[A1](op: Rep[scala.Function1[scala.Tuple2[A1, A1], A1]])(implicit emA1: Elem[A1]): Rep[A1];
      @External def map[B, That](f: Rep[scala.Function1[T, B]])(bf: Rep[WCanBuildFrom[WArray[T], B, That]])(implicit emB: Elem[B], emThat: Elem[That]): Rep[That]
    };
    trait WArrayOpsCompanion extends ExCompanion1[WArrayOps]
  }

  trait WArrayOpssDsl extends WArrayOpssAbs { self: WrappersDsl =>
    
  }

  trait WArrayOpssDslExp extends WArrayOpssExp { self: WrappersDslExp =>
    
  }
}