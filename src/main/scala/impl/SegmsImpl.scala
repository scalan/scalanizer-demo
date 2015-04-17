package segms {
  object StagedEvaluation {
    import scalan._;
    import scala.reflect.runtime.universe._;
    import scalan.common.Default;
    trait SegmsAbs extends ScalanDsl with Segms { self: SegmsDsl =>
      implicit def proxySegm(p: Rep[Segm]): Segm = {
        implicit val tag = weakTypeTag[Segm];
        proxyOps[Segm](p)(TagImplicits.typeTagToClassTag[Segm])
      };
      abstract class SegmElem[From, To <: Segm](iso: Iso[From, To]) extends ViewElem[From, To](iso) {
        override def convert(x: Rep[(Reifiable[_$1] forSome { 
          type _$1
        })]) = convertSegm(x.asRep[Segm]);
        def convertSegm(x: Rep[Segm]): Rep[To]
      };
      trait SegmCompanionElem extends CompanionElem[SegmCompanionAbs];
      implicit lazy val SegmCompanionElem: SegmCompanionElem = {
        final class $anon extends SegmCompanionElem {
          lazy val tag = weakTypeTag[SegmCompanionAbs];
          protected def getDefaultRep = Segm
        };
        new $anon()
      };
      abstract class SegmCompanionAbs extends CompanionBase[SegmCompanionAbs] with SegmCompanion {
        override def toString = "Segm"
      };
      def Segm: Rep[SegmCompanionAbs];
      implicit def proxySegmCompanion(p: Rep[SegmCompanion]): SegmCompanion = proxyOps[SegmCompanion](p);
      class IntervalElem(iso: Iso[IntervalData, Interval]) extends SegmElem[IntervalData, Interval](iso) {
        def convertSegm(x: Rep[Segm]) = Interval(x.start, x.end)
      };
      type IntervalData = scala.Tuple2[Int, Int];
      class IntervalIso extends Iso[IntervalData, Interval] {
        override def from(p: Rep[Interval]) = unmkInterval(p) match {
          case Some(scala.Tuple2((start @ _), (end @ _))) => Pair(start, end)
          case None => !!!
        };
        override def to(p: Rep[scala.Tuple2[Int, Int]]) = {
          val x$1 = (p: @scala.unchecked) match {
            case Pair((start @ _), (end @ _)) => scala.Tuple2(start, end)
          };
          val start = x$1._1;
          val end = x$1._2;
          Interval(start, end)
        };
        lazy val tag = weakTypeTag[Interval];
        lazy val defaultRepTo = Default.defaultVal[Rep[Interval]](Interval(0, 0));
        lazy val eTo = new IntervalElem(this)
      };
      abstract class IntervalCompanionAbs extends CompanionBase[IntervalCompanionAbs] with IntervalCompanion {
        override def toString = "Interval";
        def apply(p: Rep[IntervalData]): Rep[Interval] = isoInterval.to(p);
        def apply(start: Rep[Int], end: Rep[Int]): Rep[Interval] = mkInterval(start, end);
        def unapply(p: Rep[Interval]) = unmkInterval(p)
      };
      def Interval: Rep[IntervalCompanionAbs];
      implicit def proxyIntervalCompanion(p: Rep[IntervalCompanionAbs]): IntervalCompanionAbs = proxyOps[IntervalCompanionAbs](p);
      class IntervalCompanionElem extends CompanionElem[IntervalCompanionAbs] {
        lazy val tag = weakTypeTag[IntervalCompanionAbs];
        protected def getDefaultRep = Interval
      };
      implicit lazy val IntervalCompanionElem: IntervalCompanionElem = new IntervalCompanionElem();
      implicit def proxyInterval(p: Rep[Interval]): Interval = proxyOps[Interval](p);
      implicit class ExtendedInterval(p: Rep[Interval]) {
        def toData: Rep[IntervalData] = isoInterval.from(p)
      };
      implicit def isoInterval: Iso[IntervalData, Interval] = new IntervalIso();
      def mkInterval(start: Rep[Int], end: Rep[Int]): Rep[Interval];
      def unmkInterval(p: Rep[Interval]): Option[scala.Tuple2[Rep[Int], Rep[Int]]];
      class SliceElem(iso: Iso[SliceData, Slice]) extends SegmElem[SliceData, Slice](iso) {
        def convertSegm(x: Rep[Segm]) = Slice(x.start, x.length)
      };
      type SliceData = scala.Tuple2[Int, Int];
      class SliceIso extends Iso[SliceData, Slice] {
        override def from(p: Rep[Slice]) = unmkSlice(p) match {
          case Some(scala.Tuple2((start @ _), (length @ _))) => Pair(start, length)
          case None => !!!
        };
        override def to(p: Rep[scala.Tuple2[Int, Int]]) = {
          val x$2 = (p: @scala.unchecked) match {
            case Pair((start @ _), (length @ _)) => scala.Tuple2(start, length)
          };
          val start = x$2._1;
          val length = x$2._2;
          Slice(start, length)
        };
        lazy val tag = weakTypeTag[Slice];
        lazy val defaultRepTo = Default.defaultVal[Rep[Slice]](Slice(0, 0));
        lazy val eTo = new SliceElem(this)
      };
      abstract class SliceCompanionAbs extends CompanionBase[SliceCompanionAbs] with SliceCompanion {
        override def toString = "Slice";
        def apply(p: Rep[SliceData]): Rep[Slice] = isoSlice.to(p);
        def apply(start: Rep[Int], length: Rep[Int]): Rep[Slice] = mkSlice(start, length);
        def unapply(p: Rep[Slice]) = unmkSlice(p)
      };
      def Slice: Rep[SliceCompanionAbs];
      implicit def proxySliceCompanion(p: Rep[SliceCompanionAbs]): SliceCompanionAbs = proxyOps[SliceCompanionAbs](p);
      class SliceCompanionElem extends CompanionElem[SliceCompanionAbs] {
        lazy val tag = weakTypeTag[SliceCompanionAbs];
        protected def getDefaultRep = Slice
      };
      implicit lazy val SliceCompanionElem: SliceCompanionElem = new SliceCompanionElem();
      implicit def proxySlice(p: Rep[Slice]): Slice = proxyOps[Slice](p);
      implicit class ExtendedSlice(p: Rep[Slice]) {
        def toData: Rep[SliceData] = isoSlice.from(p)
      };
      implicit def isoSlice: Iso[SliceData, Slice] = new SliceIso();
      def mkSlice(start: Rep[Int], length: Rep[Int]): Rep[Slice];
      def unmkSlice(p: Rep[Slice]): Option[scala.Tuple2[Rep[Int], Rep[Int]]];
      class CenteredElem(iso: Iso[CenteredData, Centered]) extends SegmElem[CenteredData, Centered](iso) {
        def convertSegm(x: Rep[Segm]) = !!!("Cannot convert from Segm to Centered: missing fields List(center, radius)")
      };
      type CenteredData = scala.Tuple2[Int, Int];
      class CenteredIso extends Iso[CenteredData, Centered] {
        override def from(p: Rep[Centered]) = unmkCentered(p) match {
          case Some(scala.Tuple2((center @ _), (radius @ _))) => Pair(center, radius)
          case None => !!!
        };
        override def to(p: Rep[scala.Tuple2[Int, Int]]) = {
          val x$3 = (p: @scala.unchecked) match {
            case Pair((center @ _), (radius @ _)) => scala.Tuple2(center, radius)
          };
          val center = x$3._1;
          val radius = x$3._2;
          Centered(center, radius)
        };
        lazy val tag = weakTypeTag[Centered];
        lazy val defaultRepTo = Default.defaultVal[Rep[Centered]](Centered(0, 0));
        lazy val eTo = new CenteredElem(this)
      };
      abstract class CenteredCompanionAbs extends CompanionBase[CenteredCompanionAbs] with CenteredCompanion {
        override def toString = "Centered";
        def apply(p: Rep[CenteredData]): Rep[Centered] = isoCentered.to(p);
        def apply(center: Rep[Int], radius: Rep[Int]): Rep[Centered] = mkCentered(center, radius);
        def unapply(p: Rep[Centered]) = unmkCentered(p)
      };
      def Centered: Rep[CenteredCompanionAbs];
      implicit def proxyCenteredCompanion(p: Rep[CenteredCompanionAbs]): CenteredCompanionAbs = proxyOps[CenteredCompanionAbs](p);
      class CenteredCompanionElem extends CompanionElem[CenteredCompanionAbs] {
        lazy val tag = weakTypeTag[CenteredCompanionAbs];
        protected def getDefaultRep = Centered
      };
      implicit lazy val CenteredCompanionElem: CenteredCompanionElem = new CenteredCompanionElem();
      implicit def proxyCentered(p: Rep[Centered]): Centered = proxyOps[Centered](p);
      implicit class ExtendedCentered(p: Rep[Centered]) {
        def toData: Rep[CenteredData] = isoCentered.from(p)
      };
      implicit def isoCentered: Iso[CenteredData, Centered] = new CenteredIso();
      def mkCentered(center: Rep[Int], radius: Rep[Int]): Rep[Centered];
      def unmkCentered(p: Rep[Centered]): Option[scala.Tuple2[Rep[Int], Rep[Int]]]
    };
    trait SegmsSeq extends SegmsDsl with ScalanSeq { self: SegmsDslSeq =>
      lazy val Segm: Rep[SegmCompanionAbs] = {
        final class $anon extends SegmCompanionAbs with UserTypeSeq[SegmCompanionAbs, SegmCompanionAbs] {
          lazy val selfType = element[SegmCompanionAbs]
        };
        new $anon()
      };
      case class SeqInterval(override val start: Rep[Int], override val end: Rep[Int]) extends Interval(start, end) with UserTypeSeq[Segm, Interval] {
        lazy val selfType = element[Interval].asInstanceOf[Elem[Segm]]
      };
      lazy val Interval = {
        final class $anon extends IntervalCompanionAbs with UserTypeSeq[IntervalCompanionAbs, IntervalCompanionAbs] {
          lazy val selfType = element[IntervalCompanionAbs]
        };
        new $anon()
      };
      def mkInterval(start: Rep[Int], end: Rep[Int]): Rep[Interval] = new SeqInterval(start, end);
      def unmkInterval(p: Rep[Interval]) = Some(scala.Tuple2(p.start, p.end));
      case class SeqSlice(override val start: Rep[Int], override val length: Rep[Int]) extends Slice(start, length) with UserTypeSeq[Segm, Slice] {
        lazy val selfType = element[Slice].asInstanceOf[Elem[Segm]]
      };
      lazy val Slice = {
        final class $anon extends SliceCompanionAbs with UserTypeSeq[SliceCompanionAbs, SliceCompanionAbs] {
          lazy val selfType = element[SliceCompanionAbs]
        };
        new $anon()
      };
      def mkSlice(start: Rep[Int], length: Rep[Int]): Rep[Slice] = new SeqSlice(start, length);
      def unmkSlice(p: Rep[Slice]) = Some(scala.Tuple2(p.start, p.length));
      case class SeqCentered(override val center: Rep[Int], override val radius: Rep[Int]) extends Centered(center, radius) with UserTypeSeq[Segm, Centered] {
        lazy val selfType = element[Centered].asInstanceOf[Elem[Segm]]
      };
      lazy val Centered = {
        final class $anon extends CenteredCompanionAbs with UserTypeSeq[CenteredCompanionAbs, CenteredCompanionAbs] {
          lazy val selfType = element[CenteredCompanionAbs]
        };
        new $anon()
      };
      def mkCentered(center: Rep[Int], radius: Rep[Int]): Rep[Centered] = new SeqCentered(center, radius);
      def unmkCentered(p: Rep[Centered]) = Some(scala.Tuple2(p.center, p.radius))
    };
    trait SegmsExp extends SegmsDsl with ScalanExp { self: SegmsDslExp =>
      lazy val Segm: Rep[SegmCompanionAbs] = {
        final class $anon extends SegmCompanionAbs with UserTypeDef[SegmCompanionAbs, SegmCompanionAbs] {
          lazy val selfType = element[SegmCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      case class ExpInterval(override val start: Rep[Int], override val end: Rep[Int]) extends Interval(start, end) with UserTypeDef[Segm, Interval] {
        lazy val selfType = element[Interval].asInstanceOf[Elem[Segm]];
        override def mirror(t: Transformer) = ExpInterval(t(start), t(end))
      };
      lazy val Interval: Rep[IntervalCompanionAbs] = {
        final class $anon extends IntervalCompanionAbs with UserTypeDef[IntervalCompanionAbs, IntervalCompanionAbs] {
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
      def unmkInterval(p: Rep[Interval]) = Some(scala.Tuple2(p.start, p.end));
      case class ExpSlice(override val start: Rep[Int], override val length: Rep[Int]) extends Slice(start, length) with UserTypeDef[Segm, Slice] {
        lazy val selfType = element[Slice].asInstanceOf[Elem[Segm]];
        override def mirror(t: Transformer) = ExpSlice(t(start), t(length))
      };
      lazy val Slice: Rep[SliceCompanionAbs] = {
        final class $anon extends SliceCompanionAbs with UserTypeDef[SliceCompanionAbs, SliceCompanionAbs] {
          lazy val selfType = element[SliceCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      object SliceMethods {
        object end {
          def unapply(d: (Def[_$6] forSome { 
            type _$6
          })): Option[Rep[Slice]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[SliceElem].&&(method.getName.==("end")) => Some(receiver).asInstanceOf[Option[Rep[Slice]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$7] forSome { 
            type _$7
          })): Option[Rep[Slice]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object shift {
          def unapply(d: (Def[_$8] forSome { 
            type _$8
          })): Option[scala.Tuple2[Rep[Slice], Rep[Int]]] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((ofs @ _), _*), _) if receiver.elem.isInstanceOf[SliceElem].&&(method.getName.==("shift")) => Some(scala.Tuple2(receiver, ofs)).asInstanceOf[Option[scala.Tuple2[Rep[Slice], Rep[Int]]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$9] forSome { 
            type _$9
          })): Option[scala.Tuple2[Rep[Slice], Rep[Int]]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object SliceCompanionMethods;
      def mkSlice(start: Rep[Int], length: Rep[Int]): Rep[Slice] = new ExpSlice(start, length);
      def unmkSlice(p: Rep[Slice]) = Some(scala.Tuple2(p.start, p.length));
      case class ExpCentered(override val center: Rep[Int], override val radius: Rep[Int]) extends Centered(center, radius) with UserTypeDef[Segm, Centered] {
        lazy val selfType = element[Centered].asInstanceOf[Elem[Segm]];
        override def mirror(t: Transformer) = ExpCentered(t(center), t(radius))
      };
      lazy val Centered: Rep[CenteredCompanionAbs] = {
        final class $anon extends CenteredCompanionAbs with UserTypeDef[CenteredCompanionAbs, CenteredCompanionAbs] {
          lazy val selfType = element[CenteredCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      object CenteredMethods {
        object start {
          def unapply(d: (Def[_$10] forSome { 
            type _$10
          })): Option[Rep[Centered]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[CenteredElem].&&(method.getName.==("start")) => Some(receiver).asInstanceOf[Option[Rep[Centered]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$11] forSome { 
            type _$11
          })): Option[Rep[Centered]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object end {
          def unapply(d: (Def[_$12] forSome { 
            type _$12
          })): Option[Rep[Centered]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[CenteredElem].&&(method.getName.==("end")) => Some(receiver).asInstanceOf[Option[Rep[Centered]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$13] forSome { 
            type _$13
          })): Option[Rep[Centered]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object length {
          def unapply(d: (Def[_$14] forSome { 
            type _$14
          })): Option[Rep[Centered]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[CenteredElem].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[Rep[Centered]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$15] forSome { 
            type _$15
          })): Option[Rep[Centered]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object shift {
          def unapply(d: (Def[_$16] forSome { 
            type _$16
          })): Option[scala.Tuple2[Rep[Centered], Rep[Int]]] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((ofs @ _), _*), _) if receiver.elem.isInstanceOf[CenteredElem].&&(method.getName.==("shift")) => Some(scala.Tuple2(receiver, ofs)).asInstanceOf[Option[scala.Tuple2[Rep[Centered], Rep[Int]]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$17] forSome { 
            type _$17
          })): Option[scala.Tuple2[Rep[Centered], Rep[Int]]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object CenteredCompanionMethods;
      def mkCentered(center: Rep[Int], radius: Rep[Int]): Rep[Centered] = new ExpCentered(center, radius);
      def unmkCentered(p: Rep[Centered]) = Some(scala.Tuple2(p.center, p.radius));
      object SegmMethods {
        object start {
          def unapply(d: (Def[_$18] forSome { 
            type _$18
          })): Option[Rep[Segm]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(SegmElem[_$19, _$20] forSome { 
  type _$19;
  type _$20
})].&&(method.getName.==("start")) => Some(receiver).asInstanceOf[Option[Rep[Segm]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$21] forSome { 
            type _$21
          })): Option[Rep[Segm]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object length {
          def unapply(d: (Def[_$22] forSome { 
            type _$22
          })): Option[Rep[Segm]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(SegmElem[_$23, _$24] forSome { 
  type _$23;
  type _$24
})].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[Rep[Segm]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$25] forSome { 
            type _$25
          })): Option[Rep[Segm]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object end {
          def unapply(d: (Def[_$26] forSome { 
            type _$26
          })): Option[Rep[Segm]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(SegmElem[_$27, _$28] forSome { 
  type _$27;
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
            case MethodCall((receiver @ _), (method @ _), Seq((ofs @ _), _*), _) if receiver.elem.isInstanceOf[(SegmElem[_$31, _$32] forSome { 
  type _$31;
  type _$32
})].&&(method.getName.==("shift")) => Some(scala.Tuple2(receiver, ofs)).asInstanceOf[Option[scala.Tuple2[Rep[Segm], Rep[Int]]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$33] forSome { 
            type _$33
          })): Option[scala.Tuple2[Rep[Segm], Rep[Int]]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object SegmCompanionMethods
    };
    trait Segms extends Base with BaseTypes { self: SegmsDsl =>
      implicit def defaultSegmElem: Elem[Segm] = element[Interval].asElem[Segm];
      trait Segm extends Reifiable[Segm] {
        def start: Rep[Int];
        def length: Rep[Int];
        def end: Rep[Int];
        def shift(ofs: Rep[Int]): Rep[Segm]
      };
      abstract class Interval(val start: Rep[Int], val end: Rep[Int]) extends Segm {
        def length: Rep[Int] = end.-(start);
        def shift(ofs: Rep[Int]) = Interval(start.+(ofs), end.+(ofs))
      };
      abstract class Slice(val start: Rep[Int], val length: Rep[Int]) extends Segm {
        def end: Rep[Int] = start.+(length);
        def shift(ofs: Rep[Int]) = Slice(start.+(ofs), length)
      };
      abstract class Centered(val center: Rep[Int], val radius: Rep[Int]) extends Segm {
        def start: Rep[Int] = center.-(radius);
        def end: Rep[Int] = center.+(radius);
        def length: Rep[Int] = radius.*(2);
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
    val serializedMetaAst = "rO0ABXNyADBzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNFbnRpdHlNb2R1bGVEZWZt999AYtDjUgIACkwAEGNvbmNyZXRlU0NsYXNzZXN0ACFMc2NhbGEvY29sbGVjdGlvbi9pbW11dGFibGUvTGlzdDtMAAhlbnRpdGllc3EAfgABTAAJZW50aXR5T3BzdAArTHNjYWxhbi9wbHVnaW4vU2NhbGFuQXN0L3BhY2thZ2UkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyAClzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNDbGFzc0RlZmYjU6fLNOwNAgAMWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACxMc2NhbGFuL3BsdWdpbi9TY2FsYW5Bc3QvcGFja2FnZSRTQ2xhc3NBcmdzO0wACGFyZ3NUcmVldAASTGphdmEvbGFuZy9PYmplY3Q7TAAEYm9keXEAfgABTAAIYm9keVRyZWVxAH4ACkwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+AAlMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAc3EAfgAGc3IAKnNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU1RyYWl0Q2FsbOanoBsDnqt3AgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQABFNlZ21zcQB+AAZzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHEAfgASeHEAfgAQc3IAKnNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU0NsYXNzQXJnc47+7RDl5d50AgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgApc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTQ2xhc3NBcmcN4+OA4dRKoAIAB1oAB2ltcEZsYWdaAAhvdmVyRmxhZ1oAB3ZhbEZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBldAAqTHNjYWxhbi9wbHVnaW4vU2NhbGFuQXN0L3BhY2thZ2UkU1RwZUV4cHI7eHAAAAFxAH4AEHNyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHIADHNjYWxhLk9wdGlvbv5pN/3bDmZ0AgAAeHB0AAVzdGFydHNyAC1zY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNUcGVQcmltaXRpdmWh0XKwCxGYzwIAAkwAEmRlZmF1bHRWYWx1ZVN0cmluZ3EAfgAETAAEbmFtZXEAfgAEeHB0AAEwdAADSW50c3EAfgAWAAABcQB+ABBxAH4AG3QAA2VuZHEAfgAecQB+ABJ4c3EAfgAGcQB+ABBxAH4AEnhzcQB+AAZzcgAqc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTTWV0aG9kRGVm+YQTnXLpv+YCAAlaAAppc0ltcGxpY2l0TAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARlbGVtcQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0cGVSZXNxAH4AA3hwAHEAfgAQcQB+ABBxAH4AG3EAfgAbdAAGbGVuZ3RocQB+ABtxAH4AEHNyAApzY2FsYS5Tb21lESLyaV6hi3QCAAFMAAF4cQB+AAp4cQB+ABpxAH4AHnNxAH4AJQBxAH4AEHNxAH4ABnNyACtzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNNZXRob2RBcmdzpCk8dJrK9kACAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyACpzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5w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"
  }
}