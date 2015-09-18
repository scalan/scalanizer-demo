package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scala.collection.generic.CanBuildFrom

  trait WCanBuildFroms extends Base with TypeWrappers { self: WrappersDsl =>
    trait WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo] extends TypeWrapper[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo], WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] { self =>
      implicit def eeFrom: Elem[From];
      implicit def eeWCanBuildFromsElem: Elem[WCanBuildFromsElem];
      implicit def eeWCanBuildFromsTo: Elem[WCanBuildFromsTo];
      def wrappedValueOfBaseType: Rep[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]
    };
    trait WCanBuildFromCompanion extends ExCompanion3[WCanBuildFrom];
    def DefaultOfCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]: Default[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] = Default.defaultVal(null)
  }

  trait WCanBuildFromsDsl extends WCanBuildFromsAbs { self: WrappersDsl =>
    
  }

  trait WCanBuildFromsDslExp extends WCanBuildFromsExp { self: WrappersDslExp =>
    
  }
}