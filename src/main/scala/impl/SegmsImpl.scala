package segms {
  object StagedEvaluation {
    import scalan._;
    import scala.reflect.runtime.universe._;
    import scala.reflect._;
    import scalan.common.Default;
    trait SegmsAbs extends Segms with ScalanDsl { self: SegmsDsl =>
      implicit def proxySegm(p: Rep[Segm]): Segm = proxyOps[Segm](p)(classTag[Segm]);
      class SegmElem[To <: Segm] extends EntityElem[To] {
        override def isEntityType = true;
        override lazy val tag = weakTypeTag[Segm].asInstanceOf[WeakTypeTag[To]];
        override def convert(x: Rep[(Reifiable[_$1] forSome { 
          type _$1
        })]) = {
          val conv = fun(((x: Rep[Segm]) => convertSegm(x)));
          tryConvert(element[Segm], this, x, conv)
        };
        def convertSegm(x: Rep[Segm]): Rep[To] = {
          assert(x.selfType1 match {
            case ((_): SegmElem[(_)]) => true
            case _ => false
          });
          x.asRep[To]
        };
        override def getDefaultRep: Rep[To] = ???
      };
      implicit def segmElement: Elem[Segm] = new SegmElem[Segm]();
      implicit case object SegmCompanionElem extends CompanionElem[SegmCompanionAbs] with scala.Product with scala.Serializable {
        lazy val tag = weakTypeTag[SegmCompanionAbs];
        protected def getDefaultRep = Segm
      };
      abstract class SegmCompanionAbs extends CompanionBase[SegmCompanionAbs] with SegmCompanion {
        override def toString = "Segm"
      };
      def Segm: Rep[SegmCompanionAbs];
      implicit def proxySegmCompanion(p: Rep[SegmCompanion]): SegmCompanion = proxyOps[SegmCompanion](p);
      class IntervalElem(val iso: Iso[IntervalData, Interval]) extends SegmElem[Interval] with ConcreteElem[IntervalData, Interval] {
        override def convertSegm(x: Rep[Segm]) = Interval(x.start, x.end);
        override def getDefaultRep = super[ConcreteElem].getDefaultRep;
        override lazy val tag = weakTypeTag[Interval]
      };
      type IntervalData = scala.Tuple2[Int, Int];
      class IntervalIso extends Iso[IntervalData, Interval]()(pairElement(implicitly[Elem[Int]], implicitly[Elem[Int]])) {
        override def from(p: Rep[Interval]) = scala.Tuple2(p.start, p.end);
        override def to(p: Rep[scala.Tuple2[Int, Int]]) = {
          val x$1 = (p: @scala.unchecked) match {
            case Pair((start @ _), (end @ _)) => scala.Tuple2(start, end)
          };
          val start = x$1._1;
          val end = x$1._2;
          Interval(start, end)
        };
        lazy val defaultRepTo = Default.defaultVal[Rep[Interval]](Interval(0, 0));
        lazy val eTo = new IntervalElem(this)
      };
      abstract class IntervalCompanionAbs extends CompanionBase[IntervalCompanionAbs] with IntervalCompanion {
        override def toString = "Interval";
        def apply(p: Rep[IntervalData]): Rep[Interval] = isoInterval.to(p);
        def apply(start: Rep[Int], end: Rep[Int]): Rep[Interval] = mkInterval(start, end)
      };
      object IntervalMatcher {
        def unapply(p: Rep[Segm]) = unmkInterval(p)
      };
      def Interval: Rep[IntervalCompanionAbs];
      implicit def proxyIntervalCompanion(p: Rep[IntervalCompanionAbs]): IntervalCompanionAbs = proxyOps[IntervalCompanionAbs](p);
      implicit case object IntervalCompanionElem extends CompanionElem[IntervalCompanionAbs] with scala.Product with scala.Serializable {
        lazy val tag = weakTypeTag[IntervalCompanionAbs];
        protected def getDefaultRep = Interval
      };
      implicit def proxyInterval(p: Rep[Interval]): Interval = proxyOps[Interval](p);
      implicit class ExtendedInterval(p: Rep[Interval]) {
        def toData: Rep[IntervalData] = isoInterval.from(p)
      };
      implicit def isoInterval: Iso[IntervalData, Interval] = new IntervalIso();
      def mkInterval(start: Rep[Int], end: Rep[Int]): Rep[Interval];
      def unmkInterval(p: Rep[Segm]): Option[scala.Tuple2[Rep[Int], Rep[Int]]];
      class SliceElem(val iso: Iso[SliceData, Slice]) extends SegmElem[Slice] with ConcreteElem[SliceData, Slice] {
        override def convertSegm(x: Rep[Segm]) = Slice(x.start, x.length);
        override def getDefaultRep = super[ConcreteElem].getDefaultRep;
        override lazy val tag = weakTypeTag[Slice]
      };
      type SliceData = scala.Tuple2[Int, Int];
      class SliceIso extends Iso[SliceData, Slice]()(pairElement(implicitly[Elem[Int]], implicitly[Elem[Int]])) {
        override def from(p: Rep[Slice]) = scala.Tuple2(p.start, p.length);
        override def to(p: Rep[scala.Tuple2[Int, Int]]) = {
          val x$2 = (p: @scala.unchecked) match {
            case Pair((start @ _), (length @ _)) => scala.Tuple2(start, length)
          };
          val start = x$2._1;
          val length = x$2._2;
          Slice(start, length)
        };
        lazy val defaultRepTo = Default.defaultVal[Rep[Slice]](Slice(0, 0));
        lazy val eTo = new SliceElem(this)
      };
      abstract class SliceCompanionAbs extends CompanionBase[SliceCompanionAbs] with SliceCompanion {
        override def toString = "Slice";
        def apply(p: Rep[SliceData]): Rep[Slice] = isoSlice.to(p);
        def apply(start: Rep[Int], length: Rep[Int]): Rep[Slice] = mkSlice(start, length)
      };
      object SliceMatcher {
        def unapply(p: Rep[Segm]) = unmkSlice(p)
      };
      def Slice: Rep[SliceCompanionAbs];
      implicit def proxySliceCompanion(p: Rep[SliceCompanionAbs]): SliceCompanionAbs = proxyOps[SliceCompanionAbs](p);
      implicit case object SliceCompanionElem extends CompanionElem[SliceCompanionAbs] with scala.Product with scala.Serializable {
        lazy val tag = weakTypeTag[SliceCompanionAbs];
        protected def getDefaultRep = Slice
      };
      implicit def proxySlice(p: Rep[Slice]): Slice = proxyOps[Slice](p);
      implicit class ExtendedSlice(p: Rep[Slice]) {
        def toData: Rep[SliceData] = isoSlice.from(p)
      };
      implicit def isoSlice: Iso[SliceData, Slice] = new SliceIso();
      def mkSlice(start: Rep[Int], length: Rep[Int]): Rep[Slice];
      def unmkSlice(p: Rep[Segm]): Option[scala.Tuple2[Rep[Int], Rep[Int]]];
      class CenteredElem(val iso: Iso[CenteredData, Centered]) extends SegmElem[Centered] with ConcreteElem[CenteredData, Centered] {
        override def convertSegm(x: Rep[Segm]) = !!!("Cannot convert from Segm to Centered: missing fields List(center, radius)");
        override def getDefaultRep = super[ConcreteElem].getDefaultRep;
        override lazy val tag = weakTypeTag[Centered]
      };
      type CenteredData = scala.Tuple2[Int, Int];
      class CenteredIso extends Iso[CenteredData, Centered]()(pairElement(implicitly[Elem[Int]], implicitly[Elem[Int]])) {
        override def from(p: Rep[Centered]) = scala.Tuple2(p.center, p.radius);
        override def to(p: Rep[scala.Tuple2[Int, Int]]) = {
          val x$3 = (p: @scala.unchecked) match {
            case Pair((center @ _), (radius @ _)) => scala.Tuple2(center, radius)
          };
          val center = x$3._1;
          val radius = x$3._2;
          Centered(center, radius)
        };
        lazy val defaultRepTo = Default.defaultVal[Rep[Centered]](Centered(0, 0));
        lazy val eTo = new CenteredElem(this)
      };
      abstract class CenteredCompanionAbs extends CompanionBase[CenteredCompanionAbs] with CenteredCompanion {
        override def toString = "Centered";
        def apply(p: Rep[CenteredData]): Rep[Centered] = isoCentered.to(p);
        def apply(center: Rep[Int], radius: Rep[Int]): Rep[Centered] = mkCentered(center, radius)
      };
      object CenteredMatcher {
        def unapply(p: Rep[Segm]) = unmkCentered(p)
      };
      def Centered: Rep[CenteredCompanionAbs];
      implicit def proxyCenteredCompanion(p: Rep[CenteredCompanionAbs]): CenteredCompanionAbs = proxyOps[CenteredCompanionAbs](p);
      implicit case object CenteredCompanionElem extends CompanionElem[CenteredCompanionAbs] with scala.Product with scala.Serializable {
        lazy val tag = weakTypeTag[CenteredCompanionAbs];
        protected def getDefaultRep = Centered
      };
      implicit def proxyCentered(p: Rep[Centered]): Centered = proxyOps[Centered](p);
      implicit class ExtendedCentered(p: Rep[Centered]) {
        def toData: Rep[CenteredData] = isoCentered.from(p)
      };
      implicit def isoCentered: Iso[CenteredData, Centered] = new CenteredIso();
      def mkCentered(center: Rep[Int], radius: Rep[Int]): Rep[Centered];
      def unmkCentered(p: Rep[Segm]): Option[scala.Tuple2[Rep[Int], Rep[Int]]]
    };
    trait SegmsSeq extends SegmsDsl with ScalanSeq { self: SegmsDslSeq =>
      lazy val Segm: Rep[SegmCompanionAbs] = {
        final class $anon extends SegmCompanionAbs with UserTypeSeq[SegmCompanionAbs] {
          lazy val selfType = element[SegmCompanionAbs]
        };
        new $anon()
      };
      case class SeqInterval(override val start: Rep[Int], override val end: Rep[Int]) extends Interval(start, end) with UserTypeSeq[Interval] {
        lazy val selfType = element[Interval]
      };
      lazy val Interval = {
        final class $anon extends IntervalCompanionAbs with UserTypeSeq[IntervalCompanionAbs] {
          lazy val selfType = element[IntervalCompanionAbs]
        };
        new $anon()
      };
      def mkInterval(start: Rep[Int], end: Rep[Int]): Rep[Interval] = new SeqInterval(start, end);
      def unmkInterval(p: Rep[Segm]) = p match {
        case (p @ ((_): Interval @unchecked)) => Some(scala.Tuple2(p.start, p.end))
        case _ => None
      };
      case class SeqSlice(override val start: Rep[Int], override val length: Rep[Int]) extends Slice(start, length) with UserTypeSeq[Slice] {
        lazy val selfType = element[Slice]
      };
      lazy val Slice = {
        final class $anon extends SliceCompanionAbs with UserTypeSeq[SliceCompanionAbs] {
          lazy val selfType = element[SliceCompanionAbs]
        };
        new $anon()
      };
      def mkSlice(start: Rep[Int], length: Rep[Int]): Rep[Slice] = new SeqSlice(start, length);
      def unmkSlice(p: Rep[Segm]) = p match {
        case (p @ ((_): Slice @unchecked)) => Some(scala.Tuple2(p.start, p.length))
        case _ => None
      };
      case class SeqCentered(override val center: Rep[Int], override val radius: Rep[Int]) extends Centered(center, radius) with UserTypeSeq[Centered] {
        lazy val selfType = element[Centered]
      };
      lazy val Centered = {
        final class $anon extends CenteredCompanionAbs with UserTypeSeq[CenteredCompanionAbs] {
          lazy val selfType = element[CenteredCompanionAbs]
        };
        new $anon()
      };
      def mkCentered(center: Rep[Int], radius: Rep[Int]): Rep[Centered] = new SeqCentered(center, radius);
      def unmkCentered(p: Rep[Segm]) = p match {
        case (p @ ((_): Centered @unchecked)) => Some(scala.Tuple2(p.center, p.radius))
        case _ => None
      }
    };
    trait SegmsExp extends SegmsDsl with ScalanExp { self: SegmsDslExp =>
      lazy val Segm: Rep[SegmCompanionAbs] = {
        final class $anon extends SegmCompanionAbs with UserTypeDef[SegmCompanionAbs] {
          lazy val selfType = element[SegmCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      case class ExpInterval(override val start: Rep[Int], override val end: Rep[Int]) extends Interval(start, end) with UserTypeDef[Interval] {
        lazy val selfType = element[Interval];
        override def mirror(t: Transformer) = ExpInterval(t(start), t(end))
      };
      lazy val Interval: Rep[IntervalCompanionAbs] = {
        final class $anon extends IntervalCompanionAbs with UserTypeDef[IntervalCompanionAbs] {
          lazy val selfType = element[IntervalCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      object IntervalMethods {
        object length {
          def unapply(d: (Def[_$2] forSome { 
            type _$2
          })): Option[Rep[Interval]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[IntervalElem].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[Rep[Interval]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$3] forSome { 
            type _$3
          })): Option[Rep[Interval]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object shift {
          def unapply(d: (Def[_$4] forSome { 
            type _$4
          })): Option[scala.Tuple2[Rep[Interval], Rep[Int]]] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((ofs @ _), _*), _) if receiver.elem.isInstanceOf[IntervalElem].&&(method.getName.==("shift")) => Some(scala.Tuple2(receiver, ofs)).asInstanceOf[Option[scala.Tuple2[Rep[Interval], Rep[Int]]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$5] forSome { 
            type _$5
          })): Option[scala.Tuple2[Rep[Interval], Rep[Int]]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object IntervalCompanionMethods;
      def mkInterval(start: Rep[Int], end: Rep[Int]): Rep[Interval] = new ExpInterval(start, end);
      def unmkInterval(p: Rep[Segm]) = p.elem.asInstanceOf[(Elem[_$6] forSome { 
        type _$6
      })] match {
        case ((_): IntervalElem @unchecked) => Some(scala.Tuple2(p.asRep[Interval].start, p.asRep[Interval].end))
        case _ => None
      };
      case class ExpSlice(override val start: Rep[Int], override val length: Rep[Int]) extends Slice(start, length) with UserTypeDef[Slice] {
        lazy val selfType = element[Slice];
        override def mirror(t: Transformer) = ExpSlice(t(start), t(length))
      };
      lazy val Slice: Rep[SliceCompanionAbs] = {
        final class $anon extends SliceCompanionAbs with UserTypeDef[SliceCompanionAbs] {
          lazy val selfType = element[SliceCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      object SliceMethods {
        object end {
          def unapply(d: (Def[_$7] forSome { 
            type _$7
          })): Option[Rep[Slice]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[SliceElem].&&(method.getName.==("end")) => Some(receiver).asInstanceOf[Option[Rep[Slice]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$8] forSome { 
            type _$8
          })): Option[Rep[Slice]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object shift {
          def unapply(d: (Def[_$9] forSome { 
            type _$9
          })): Option[scala.Tuple2[Rep[Slice], Rep[Int]]] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((ofs @ _), _*), _) if receiver.elem.isInstanceOf[SliceElem].&&(method.getName.==("shift")) => Some(scala.Tuple2(receiver, ofs)).asInstanceOf[Option[scala.Tuple2[Rep[Slice], Rep[Int]]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$10] forSome { 
            type _$10
          })): Option[scala.Tuple2[Rep[Slice], Rep[Int]]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object SliceCompanionMethods;
      def mkSlice(start: Rep[Int], length: Rep[Int]): Rep[Slice] = new ExpSlice(start, length);
      def unmkSlice(p: Rep[Segm]) = p.elem.asInstanceOf[(Elem[_$11] forSome { 
        type _$11
      })] match {
        case ((_): SliceElem @unchecked) => Some(scala.Tuple2(p.asRep[Slice].start, p.asRep[Slice].length))
        case _ => None
      };
      case class ExpCentered(override val center: Rep[Int], override val radius: Rep[Int]) extends Centered(center, radius) with UserTypeDef[Centered] {
        lazy val selfType = element[Centered];
        override def mirror(t: Transformer) = ExpCentered(t(center), t(radius))
      };
      lazy val Centered: Rep[CenteredCompanionAbs] = {
        final class $anon extends CenteredCompanionAbs with UserTypeDef[CenteredCompanionAbs] {
          lazy val selfType = element[CenteredCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      object CenteredMethods {
        object start {
          def unapply(d: (Def[_$12] forSome { 
            type _$12
          })): Option[Rep[Centered]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[CenteredElem].&&(method.getName.==("start")) => Some(receiver).asInstanceOf[Option[Rep[Centered]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$13] forSome { 
            type _$13
          })): Option[Rep[Centered]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object end {
          def unapply(d: (Def[_$14] forSome { 
            type _$14
          })): Option[Rep[Centered]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[CenteredElem].&&(method.getName.==("end")) => Some(receiver).asInstanceOf[Option[Rep[Centered]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$15] forSome { 
            type _$15
          })): Option[Rep[Centered]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object length {
          def unapply(d: (Def[_$16] forSome { 
            type _$16
          })): Option[Rep[Centered]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[CenteredElem].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[Rep[Centered]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$17] forSome { 
            type _$17
          })): Option[Rep[Centered]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object shift {
          def unapply(d: (Def[_$18] forSome { 
            type _$18
          })): Option[scala.Tuple2[Rep[Centered], Rep[Int]]] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((ofs @ _), _*), _) if receiver.elem.isInstanceOf[CenteredElem].&&(method.getName.==("shift")) => Some(scala.Tuple2(receiver, ofs)).asInstanceOf[Option[scala.Tuple2[Rep[Centered], Rep[Int]]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$19] forSome { 
            type _$19
          })): Option[scala.Tuple2[Rep[Centered], Rep[Int]]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object CenteredCompanionMethods;
      def mkCentered(center: Rep[Int], radius: Rep[Int]): Rep[Centered] = new ExpCentered(center, radius);
      def unmkCentered(p: Rep[Segm]) = p.elem.asInstanceOf[(Elem[_$20] forSome { 
        type _$20
      })] match {
        case ((_): CenteredElem @unchecked) => Some(scala.Tuple2(p.asRep[Centered].center, p.asRep[Centered].radius))
        case _ => None
      };
      object SegmMethods {
        object start {
          def unapply(d: (Def[_$21] forSome { 
            type _$21
          })): Option[Rep[Segm]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(SegmElem[_$22] forSome { 
  type _$22
})].&&(method.getName.==("start")) => Some(receiver).asInstanceOf[Option[Rep[Segm]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$23] forSome { 
            type _$23
          })): Option[Rep[Segm]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object length {
          def unapply(d: (Def[_$24] forSome { 
            type _$24
          })): Option[Rep[Segm]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(SegmElem[_$25] forSome { 
  type _$25
})].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[Rep[Segm]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$26] forSome { 
            type _$26
          })): Option[Rep[Segm]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object end {
          def unapply(d: (Def[_$27] forSome { 
            type _$27
          })): Option[Rep[Segm]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(SegmElem[_$28] forSome { 
  type _$28
})].&&(method.getName.==("end")) => Some(receiver).asInstanceOf[Option[Rep[Segm]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$29] forSome { 
            type _$29
          })): Option[Rep[Segm]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object shift {
          def unapply(d: (Def[_$30] forSome { 
            type _$30
          })): Option[scala.Tuple2[Rep[Segm], Rep[Int]]] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((ofs @ _), _*), _) if receiver.elem.isInstanceOf[(SegmElem[_$31] forSome { 
  type _$31
})].&&(method.getName.==("shift")) => Some(scala.Tuple2(receiver, ofs)).asInstanceOf[Option[scala.Tuple2[Rep[Segm], Rep[Int]]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$32] forSome { 
            type _$32
          })): Option[scala.Tuple2[Rep[Segm], Rep[Int]]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object SegmCompanionMethods
    };
    trait Segms extends Base { self: SegmsDsl =>
      trait Segm extends Reifiable[Segm] {
        def start: Rep[Int];
        def length: Rep[Int];
        def end: Rep[Int];
        def shift(ofs: Rep[Int]): Rep[Segm]
      };
      abstract class Interval(val start: Rep[Int], val end: Rep[Int]) extends Segm with Product with Serializable {
        def length: Rep[Int] = end.-(start);
        def shift(ofs: Rep[Int]) = Interval(start.+(ofs), end.+(ofs))
      };
      abstract class Slice(val start: Rep[Int], val length: Rep[Int]) extends Segm with Product with Serializable {
        def end: Rep[Int] = start.+(length);
        def shift(ofs: Rep[Int]) = Slice(start.+(ofs), length)
      };
      abstract class Centered(val center: Rep[Int], val radius: Rep[Int]) extends Segm with Product with Serializable {
        def start: Rep[Int] = center.-(radius);
        def end: Rep[Int] = center.+(radius);
        def length: Rep[Int] = radius.*(toRep(2));
        def shift(ofs: Rep[Int]) = Centered(center.+(ofs), radius)
      };
      trait SegmCompanion;
      trait IntervalCompanion;
      trait SliceCompanion;
      trait CenteredCompanion
    };
    trait SegmsDsl extends SegmsAbs;
    trait SegmsDslSeq extends SegmsSeq;
    trait SegmsDslExp extends SegmsExp;
    val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZrSr+/co886dAgALTAAEYm9keXQAIUxzY2FsYS9jb2xsZWN0aW9uL2ltbXV0YWJsZS9MaXN0O0wAEGNvbmNyZXRlU0NsYXNzZXNxAH4AAUwACGVudGl0aWVzcQB+AAFMAAllbnRpdHlPcHN0ACFMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcmFpdERlZjtMABBlbnRpdHlSZXBTeW5vbnltdAAOTHNjYWxhL09wdGlvbjtMAAdpbXBvcnRzcQB+AAFMAAdtZXRob2RzcQB+AAFMAARuYW1ldAASTGphdmEvbGFuZy9TdHJpbmc7TAALcGFja2FnZU5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAApzZXFEc2xJbXBscQB+AAN4cHNyADJzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0JFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABAwAAeHBzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzRGVmB9B9aPGocDMCAApaAAppc0Fic3RyYWN0TAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYXJnc3QAIkxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU0NsYXNzQXJncztMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAMTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0Q2FsbFDrGS0l7EVYAgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQABFNlZ21xAH4AB3NxAH4AD3QAB1Byb2R1Y3RxAH4AB3NxAH4AD3QADFNlcmlhbGl6YWJsZXEAfgAHcQB+AAl4cQB+AAdzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZ3Mn6+5kxTAAXAIAAUwABGFyZ3NxAH4AAXhwc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NBcmcFc9jWC9QpPwIACFoAB2ltcEZsYWdaAAxpc0VsZW1PckNvbnRaAAhvdmVyRmxhZ1oAB3ZhbEZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBldAAgTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTVHBlRXhwcjt4cAAAAAFxAH4AB3NyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHIADHNjYWxhLk9wdGlvbv5pN/3bDmZ0AgAAeHB0AAVzdGFydHNyACNzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZVByaW1pdGl2ZTXDu1Xwvk4FAgACTAASZGVmYXVsdFZhbHVlU3RyaW5ncQB+AARMAARuYW1lcQB+AAR4cHQAATB0AANJbnRzcQB+ABkAAAABcQB+AAdxAH4AHnQAA2VuZHEAfgAhcQB+AAl4c3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kRGVm3VlxcbcwKw4CAApaAAxpc0VsZW1PckNvbnRaAAppc0ltcGxpY2l0WgAKaXNPdmVycmlkZUwAC2Fubm90YXRpb25zcQB+AAFMAAthcmdTZWN0aW9uc3EAfgABTAAEYm9keXEAfgADTAAEbmFtZXEAfgAETAAKb3ZlcmxvYWRJZHEAfgADTAAHdHBlQXJnc3EAfgABTAAGdHBlUmVzcQB+AAN4cAAAAHEAfgAHcQB+AAdzcgAKc2NhbGEuU29tZREi8mleoYt0AgABTAABeHQAEkxqYXZhL2xhbmcvT2JqZWN0O3hxAH4AHXNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0FwcGx5hGYpn9Ka/H8CAANMAAVhcmdzc3EAfgABTAADZnVudAAdTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTRXhwcjtMAAJ0c3EAfgABeHBzcQB+AAZzcQB+AAZzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJZGVudCcSpfvwCddPAgABTAAEbmFtZXEAfgAEeHB0AAVzdGFydHEAfgAJeHEAfgAJeHNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1NlbGVjdAvdaWVlQPmFAgACTAAEZXhwcnEAfgAtTAAFdG5hbWVxAH4ABHhwc3EAfgAxdAADZW5kdAAGJG1pbnVzcQB+AAd0AAZsZW5ndGhxAH4AHnEAfgAHc3EAfgApcQB+ACFzcQB+ACcAAABxAH4AB3NxAH4ABnNyACFzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZ3MpwieWXnW6iwIAAUwABGFyZ3NxAH4AAXhwc3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kQXJnA9z/y5NycwMCAAdaAAdpbXBGbGFnWgAMaXNFbGVtT3JDb250WgAIb3ZlckZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBlcQB+ABp4cAAAAHEAfgAHcQB+AB50AANvZnNxAH4AIXEAfgAJeHEAfgAJeHNxAH4AKXNxAH4ALHNxAH4ABnNxAH4ABnNxAH4ALHNxAH4ABnNxAH4ABnNxAH4AMXQAA29mc3EAfgAJeHEAfgAJeHNxAH4ANHNxAH4AMXQABXN0YXJ0dAAFJHBsdXNxAH4AB3NxAH4ALHNxAH4ABnNxAH4ABnNxAH4AMXQAA29mc3EAfgAJeHEAfgAJeHNxAH4ANHNxAH4AMXQAA2VuZHEAfgBPcQB+AAdxAH4ACXhxAH4ACXhzcQB+ADF0AAhJbnRlcnZhbHEAfgAHdAAFc2hpZnRxAH4AHnEAfgAHcQB+AB5xAH4ACXhxAH4AHnNxAH4AFnEAfgAHdAAISW50ZXJ2YWxxAH4AHnEAfgAHc3EAfgALAHNxAH4ABnNxAH4AD3QABFNlZ21xAH4AB3NxAH4AD3EAfgATcQB+AAdzcQB+AA9xAH4AFXEAfgAHcQB+AAl4cQB+AAdzcQB+ABZzcQB+AAZzcQB+ABkAAAABcQB+AAdxAH4AHnQABXN0YXJ0cQB+ACFzcQB+ABkAAAABcQB+AAdxAH4AHnEAfgA5cQB+ACFxAH4ACXhzcQB+AAZzcQB+ACcAAABxAH4AB3EAfgAHc3EAfgApc3EAfgAsc3EAfgAGc3EAfgAGc3EAfgAxcQB+ADlxAH4ACXhxAH4ACXhzcQB+ADRzcQB+ADF0AAVzdGFydHEAfgBP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"
  }
}