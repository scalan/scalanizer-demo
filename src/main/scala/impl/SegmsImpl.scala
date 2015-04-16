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
      trait Segm extends Reifiable[Segm] {
        def start: Rep[Int];
        def length: Rep[Int];
        def end: Rep[Int];
        def shift(ofs: Rep[Int]): Rep[Segm]
      };
      trait SegmCompanion extends Reifiable[SegmCompanion];
      abstract class Interval(val start: Rep[Int], val end: Rep[Int]) extends Segm {
        def length: Rep[Int] = end.-(start);
        def shift(ofs: Rep[Int]) = Interval(start.+(ofs), end.+(ofs))
      };
      trait IntervalCompanion extends Reifiable[IntervalCompanion];
      abstract class Slice(val start: Rep[Int], val length: Rep[Int]) extends Segm {
        def end: Rep[Int] = start.+(length);
        def shift(ofs: Rep[Int]) = Slice(start.+(ofs), length)
      };
      trait SliceCompanion extends Reifiable[SliceCompanion];
      abstract class Centered(val center: Rep[Int], val radius: Rep[Int]) extends Segm {
        def start: Rep[Int] = center.-(radius);
        def end: Rep[Int] = center.+(radius);
        def length: Rep[Int] = radius.*(2);
        def shift(ofs: Rep[Int]) = Centered(center.+(ofs), radius)
      };
      trait CenteredCompanion extends Reifiable[CenteredCompanion];
      implicit def defaultSegmElem: Elem[Segm] = element[Interval].asElem[Segm]
    };
    trait SegmsDsl extends SegmsAbs;
    trait SegmsDslSeq extends SegmsSeq;
    trait SegmsDslExp extends SegmsExp;
    val serializedMetaAst = "rO0ABXNyADBzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNFbnRpdHlNb2R1bGVEZWZt999AYtDjUgIACkwAEGNvbmNyZXRlU0NsYXNzZXN0ACFMc2NhbGEvY29sbGVjdGlvbi9pbW11dGFibGUvTGlzdDtMAAhlbnRpdGllc3EAfgABTAAJZW50aXR5T3BzdAArTHNjYWxhbi9wbHVnaW4vU2NhbGFuQXN0L3BhY2thZ2UkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyAClzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNDbGFzc0RlZndoTls0GHDYAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACxMc2NhbGFuL3BsdWdpbi9TY2FsYW5Bc3QvcGFja2FnZSRTQ2xhc3NBcmdzO0wABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+AAlMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAc3EAfgAGc3IAKnNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU1RyYWl0Q2FsbOanoBsDnqt3AgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQABFNlZ21zcQB+AAZzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHEAfgAReHEAfgAPc3IAKnNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU0NsYXNzQXJnc47+7RDl5d50AgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgApc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTQ2xhc3NBcmcN4+OA4dRKoAIAB1oAB2ltcEZsYWdaAAhvdmVyRmxhZ1oAB3ZhbEZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBldAAqTHNjYWxhbi9wbHVnaW4vU2NhbGFuQXN0L3BhY2thZ2UkU1RwZUV4cHI7eHAAAAFxAH4AD3NyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHIADHNjYWxhLk9wdGlvbv5pN/3bDmZ0AgAAeHB0AAVzdGFydHNyAC1zY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNUcGVQcmltaXRpdmWh0XKwCxGYzwIAAkwAEmRlZmF1bHRWYWx1ZVN0cmluZ3EAfgAETAAEbmFtZXEAfgAEeHB0AAEwdAADSW50c3EAfgAVAAABcQB+AA9xAH4AGnQAA2VuZHEAfgAdcQB+ABF4c3EAfgAGc3IAKnNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU01ldGhvZERlZvmEE51y6b/mAgAJWgAKaXNJbXBsaWNpdEwAC2Fubm90YXRpb25zcQB+AAFMAAthcmdTZWN0aW9uc3EAfgABTAAEYm9keXEAfgADTAAEZWxlbXEAfgADTAAEbmFtZXEAfgAETAAKb3ZlcmxvYWRJZHEAfgADTAAHdHBlQXJnc3EAfgABTAAGdHBlUmVzcQB+AAN4cABxAH4AD3EAfgAPcQB+ABpxAH4AGnQABmxlbmd0aHEAfgAacQB+AA9zcgAKc2NhbGEuU29tZREi8mleoYt0AgABTAABeHQAEkxqYXZhL2xhbmcvT2JqZWN0O3hxAH4AGXEAfgAdc3EAfgAjAHEAfgAPc3EAfgAGc3IAK3NjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU01ldGhvZEFyZ3OkKTx0msr2QAIAAUwABGFyZ3NxAH4AAXhwc3EAfgAGc3IAKnNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU01ldGhvZEFyZ2XoWw9Vr6aZAgAGWgAHaW1wRmxhZ1oACG92ZXJGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXEAfgAWeHAAAHEAfgAPcQB+ABp0AANvZnNxAH4AHXEAfgAReHEAfgAReHEAfgAacQB+ABp0AAVzaGlmdHEAfgAacQB+AA9xAH4AGnEAfgAReHNxAH4AJnNyAClzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNUcmFpdERlZkQj0GTZsZq4AgAJWgAIYml0bWFwJDBMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAJTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgAPcQB+AA9xAH4AD3EAfgAacHQAEUludGVydmFsQ29tcGFuaW9ucQB+ABpxAH4AD3NxAH4AEnEAfgAPdAAISW50ZXJ2YWxxAH4AGnEAfgAPc3EAfgAIAHNxAH4ABnNxAH4ADHQABFNlZ21xAH4AD3EAfgAReHEAfgAPc3EAfgASc3EAfgAGc3EAfgAVAAABcQB+AA9xAH4AGnQABXN0YXJ0cQB+AB1zcQB+ABUAAAFxAH4AD3EAfgAacQB+ACVxAH4AHXEAfgAReHNxAH4ABnNxAH4AIwBxAH4AD3EAfgAPcQB+ABpxAH4AGnQAA2VuZHEAfgAacQB+AA9zcQB+ACZxAH4AHXNxAH4AIwBxAH4AD3NxAH4ABnNxAH4AK3NxAH4ABnNxAH4ALgAAcQB+AA9xAH4AGnQAA29mc3EAfgAdcQB+ABF4cQB+ABF4cQB+ABpxAH4AGnQABXNoaWZ0cQB+ABpxAH4AD3EAfgAacQB+ABF4c3EAfgAmc3EAfgAzAHEAfgAPcQB+AA9xAH4AD3EAfgAacHQADlNsaWNlQ29tcGFuaW9ucQB+ABpxAH4AD3NxAH4AEnEAfgAPdAAFU2xpY2VxAH4AGnEAfgAPc3EAfgAIAHNxAH4ABnNxAH4ADHQABFNlZ21xAH4AD3EAfgAReHEAfgAPc3EAfgASc3EAfgAGc3EAfgAVAAABcQB+AA9xAH4AGnQABmNlbnRlcnEAfgAdc3EAfgAVAAABcQB+AA9xAH4AGnQABnJhZGl1c3EAfgAdcQB+ABF4c3EAfgAGc3EAfgAjAHEAfgAPcQB+AA9xAH4AGnEAfgAadAAFc3RhcnRxAH4AGnEAfgAPc3EAfgAmcQB+AB1zcQB+ACMAcQB+AA9xAH4AD3EAfgAacQB+ABp0AANlbmRxAH4AGnEAfgAPc3EAfgAmcQB+AB1zcQB+ACMAcQB+AA9xAH4AD3EAfgAacQB+ABpxAH4AJXEAfgAacQB+AA9zcQB+ACZxAH4AHXNxAH4AIwBxAH4AD3NxAH4ABnNxAH4AK3NxAH4ABnNxAH4ALgAAcQB+AA9xAH4AGnQAA29mc3EAfgAdcQB+ABF4cQB+ABF4cQB+ABpxAH4AGnQABXNoaWZ0cQB+ABpxAH4AD3EAfgAacQB+ABF4c3EAfgAmc3EAfgAzAHEAfgAPcQB+AA9xAH4AD3EAfgAacHQAEUNlbnRlcmVkQ29tcGFuaW9ucQB+ABpxAH4AD3NxAH4AEnEAfgAPdAAIQ2VudGVyZWRxAH4AGnEAfgAPcQB+ABF4c3EAfgAGc3EAfgAzAHNxAH4ABnNxAH4ADHQACVJlaWZpYWJsZXNxAH4ABnNxAH4ADHQABFNlZ21xAH4AD3EAfgAReHEAfgAReHEAfgAPc3EAfgAGc3EAfgAjAHEAfgAPcQB+AA9xAH4AGnEAfgAadAAFc3RhcnRxAH4AGnEAfgAPc3EAfgAmcQB+AB1zcQB+ACMAcQB+AA9xAH4AD3EAfgAacQB+ABpxAH4AJXEAfgAacQB+AA9zcQB+ACZxAH4AHXNxAH4AIwBxAH4AD3EAfgAPcQB+ABpxAH4AGnQAA2VuZHEAfgAacQB+AA9zcQB+ACZxAH4AHXNxAH4AIwBxAH4AD3NxAH4ABnNxAH4AK3NxAH4ABnNxAH4ALgAAcQB+AA9xAH4AGnQAA29mc3EAfgAdcQB+ABF4cQB+ABF4cQB+ABpxAH4AGnQABXNoaWZ0cQB+ABpxAH4AD3NxAH4AJnNxAH4ADHQABFNlZ21xAH4AD3EAfgAReHNxAH4AJnNxAH4AMwBxAH4AD3EAfgAPcQB+AA9xAH4AGnB0AA1TZWdtQ29tcGFuaW9ucQB+ABpxAH4AD3BxAH4Ad3EAfgAacQB+AA9xAH4AEXhxAH4AcXNxAH4AJnNyACdzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNUcGVEZWalpHc0ytghWgIAA0wABG5hbWVxAH4ABEwAA3Joc3EAfgAWTAAHdHBlQXJnc3EAfgABeHB0AAdSZXBTZWdtc3EAfgAMdAADUmVwc3EAfgAGc3EAfgAMcQB+AHdxAH4AD3EAfgAReHEAfgAPc3EAfgAGc3IAK3NjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU0ltcG9ydFN0YXS5ZMraoEexdwIAAUwABG5hbWVxAH4ABHhwdAAIc2NhbGFuLl9xAH4AEXhzcQB+AAZzcQB+ACMBcQB+AA9xAH4AD3EAfgAacQB+ABp0AA9kZWZhdWx0U2VnbUVsZW1xAH4AGnEAfgAPc3EAfgAmc3EAfgAMdAAERWxlbXNxAH4ABnNxAH4ADHEAfgB3cQB+AA9xAH4AEXhxAH4AEXh0AAVTZWdtc3QABXNlZ21zc3EAfgAmc3IALHNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU1NlbGZUeXBlRGVmsJNCjwHkcv8CAAJMAApjb21wb25lbnRzcQB+AAFMAARuYW1lcQB+AAR4cHNxAH4ABnNxAH4ADHQACFNlZ21zRHNscQB+AA9xAH4AEXh0AARzZWxmcQB+ABo="
  }
}