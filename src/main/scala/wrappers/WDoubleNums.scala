package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scalanizer.DoubleNum

  trait WDoubleNums extends Base with TypeWrappers { self: WrappersDsl =>
    trait WDoubleNum extends TypeWrapper[DoubleNum, WDoubleNum] { self =>
      def wrappedValueOfBaseType: Rep[DoubleNum]
    };
    trait WDoubleNumCompanion extends ExCompanion0[WDoubleNum] {
      @External def apply: Rep[WDoubleNum]
    };
    def DefaultOfDoubleNum: Default[DoubleNum] = Default.defaultVal(null)
  }

  trait WDoubleNumsDsl extends WDoubleNumsAbs { self: WrappersDsl =>
    
  }

  trait WDoubleNumsDslSeq extends WDoubleNumsSeq { self: WrappersDslSeq =>
    
  }

  trait WDoubleNumsDslExp extends WDoubleNumsExp { self: WrappersDslExp =>
    
  }
}