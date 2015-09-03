package wrappers {
  import scalan._

  import scalan.common.Default

  import impl._

  import scalanizer.MyArr

trait WMyArrs extends Base with TypeWrappers {
  self: WrappersDsl =>

  trait WMyArr[T] extends TypeWrapper[MyArr[T], WMyArr[T]] {
    self =>
      implicit def eeT: Elem[T];
      def wrappedValueOfBaseType: Rep[MyArr[T]];
      @External def apply(j: Rep[Int]): Rep[T];
      @External def length: Rep[Int]
    };

  trait WMyArrCompanion;
    def DefaultOfMyArr[T]: Default[MyArr[T]] = Default.defaultVal(null)
  }

trait WMyArrsDsl extends WMyArrsAbs {
  self: WrappersDsl =>
    
  }

trait WMyArrsDslSeq extends WMyArrsSeq {
  self: WrappersDslSeq =>
    
  }

trait WMyArrsDslExp extends WMyArrsExp {
  self: WrappersDslExp =>
    
  }
}