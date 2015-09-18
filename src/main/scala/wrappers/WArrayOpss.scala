package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scala.collection.mutable.ArrayOps

  trait WArrayOpss extends Base with TypeWrappers { self: WrappersDsl =>
    trait WArrayOps[T] extends TypeWrapper[ArrayOps[T], WArrayOps[T]] { self =>
      implicit def eeT: Elem[T];
      def wrappedValueOfBaseType: Rep[ArrayOps[T]];
      @External def map[B, That](f: Rep[scala.Function1[T, B]])(bf: Rep[WCanBuildFrom[WArray[T], B, That]])(implicit emB: Elem[B], emThat: Elem[That]): Rep[That]
    };
    trait WArrayOpsCompanion extends ExCompanion1[WArrayOps];
    def DefaultOfArrayOps[T]: Default[ArrayOps[T]] = Default.defaultVal(null)
  }

  trait WArrayOpssDsl extends WArrayOpssAbs { self: WrappersDsl =>
    
  }

  trait WArrayOpssDslExp extends WArrayOpssExp { self: WrappersDslExp =>
    
  }
}