package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scalanizer.DoubleNum

  trait WDoubleNums extends Base with TypeWrappers { self: WrappersDsl =>
    trait WDoubleNum extends TypeWrapper[DoubleNum, WDoubleNum] with WNum[Double] { self =>
      def wrappedValueOfBaseType: Rep[DoubleNum];
      @External def zero: Rep[Double]
    };
    trait WDoubleNumCompanion extends ExCompanion0[WDoubleNum] {
      @Constructor def apply: Rep[WDoubleNum]
    };
    def DefaultOfDoubleNum: Default[DoubleNum] = Default.defaultVal(null)
  }

  trait WDoubleNumsDsl extends WDoubleNumsAbs { self: WrappersDsl =>
    
  }

  trait WDoubleNumsDslExp extends WDoubleNumsExp { self: WrappersDslExp =>
    
  }
}