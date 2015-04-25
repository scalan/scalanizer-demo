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
        };
        object id {
          def unapply(d: (Def[_$10] forSome { 
            type _$10
          })): Option[Rep[Slice]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[SliceElem].&&(method.getName.==("id")) => Some(receiver).asInstanceOf[Option[Rep[Slice]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$11] forSome { 
            type _$11
          })): Option[Rep[Slice]] = exp match {
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
        };
        object id {
          def unapply(d: (Def[_$20] forSome { 
            type _$20
          })): Option[Rep[Centered]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[CenteredElem].&&(method.getName.==("id")) => Some(receiver).asInstanceOf[Option[Rep[Centered]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$21] forSome { 
            type _$21
          })): Option[Rep[Centered]] = exp match {
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
          def unapply(d: (Def[_$22] forSome { 
            type _$22
          })): Option[Rep[Segm]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(SegmElem[_$23, _$24] forSome { 
  type _$23;
  type _$24
})].&&(method.getName.==("start")) => Some(receiver).asInstanceOf[Option[Rep[Segm]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$25] forSome { 
            type _$25
          })): Option[Rep[Segm]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object length {
          def unapply(d: (Def[_$26] forSome { 
            type _$26
          })): Option[Rep[Segm]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(SegmElem[_$27, _$28] forSome { 
  type _$27;
  type _$28
})].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[Rep[Segm]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$29] forSome { 
            type _$29
          })): Option[Rep[Segm]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object end {
          def unapply(d: (Def[_$30] forSome { 
            type _$30
          })): Option[Rep[Segm]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(SegmElem[_$31, _$32] forSome { 
  type _$31;
  type _$32
})].&&(method.getName.==("end")) => Some(receiver).asInstanceOf[Option[Rep[Segm]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$33] forSome { 
            type _$33
          })): Option[Rep[Segm]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object shift {
          def unapply(d: (Def[_$34] forSome { 
            type _$34
          })): Option[scala.Tuple2[Rep[Segm], Rep[Int]]] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((ofs @ _), _*), _) if receiver.elem.isInstanceOf[(SegmElem[_$35, _$36] forSome { 
  type _$35;
  type _$36
})].&&(method.getName.==("shift")) => Some(scala.Tuple2(receiver, ofs)).asInstanceOf[Option[scala.Tuple2[Rep[Segm], Rep[Int]]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$37] forSome { 
            type _$37
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
        def length: Rep[Int] = {
          val flag: Rep[Boolean] = toRep(true);
          def foo[T <: Double](sec: Rep[Long]): Rep[scala.Tuple2[Int, scala.Tuple2[Long, Double]]] = Tuple(toRep(0), sec, toRep(2).asInstanceOf[Double]);
          IF(flag).THEN({
  val diff: Rep[Int] = end.-(start);
  ((diff): Rep[Int])
}).ELSE(foo[Double](toRep(10))._1)
        };
        def shift(ofs: Rep[Int]) = Interval(start.+(ofs), end.+(ofs))
      };
      abstract class Slice(val start: Rep[Int], val length: Rep[Int]) extends Segm {
        def end: Rep[Int] = start.+(length);
        def shift(ofs: Rep[Int]) = shiftBy(((x: Rep[Int]) => x.+(ofs)));
        def shiftBy(f: Rep[scala.Function1[Int, Int]]) = Slice(f(start), length);
        def id = Slice.this
      };
      @SerialVersionUID(value = 1) abstract class Centered(@SerialVersionUID(2) val center: Rep[Int], val radius: Rep[Int]) extends Segm {
        def start: Rep[Int] = center.-(radius);
        def end: Rep[Int] = center.+(radius);
        def length: Rep[Int] = radius.*(toRep(2)): @inline;
        def shift(ofs: Rep[Int]) = Centered(center = center.+(ofs), radius);
        @SerialVersionUID(3) def id = this
      };
      trait SegmCompanion;
      trait IntervalCompanion;
      trait SliceCompanion;
      trait CenteredCompanion
    };
    trait SegmsDsl extends SegmsAbs;
    trait SegmsDslSeq extends SegmsSeq;
    trait SegmsDslExp extends SegmsExp;
    val serializedMetaAst = "rO0ABXNyADBzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNFbnRpdHlNb2R1bGVEZWZt999AYtDjUgIACkwAEGNvbmNyZXRlU0NsYXNzZXN0ACFMc2NhbGEvY29sbGVjdGlvbi9pbW11dGFibGUvTGlzdDtMAAhlbnRpdGllc3EAfgABTAAJZW50aXR5T3BzdAArTHNjYWxhbi9wbHVnaW4vU2NhbGFuQXN0L3BhY2thZ2UkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyAClzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNDbGFzc0RlZndoTls0GHDYAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACxMc2NhbGFuL3BsdWdpbi9TY2FsYW5Bc3QvcGFja2FnZSRTQ2xhc3NBcmdzO0wABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+AAlMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAc3EAfgAGc3IAKnNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU1RyYWl0Q2FsbOanoBsDnqt3AgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQABFNlZ21zcQB+AAZzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHEAfgAReHEAfgAPc3IAKnNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU0NsYXNzQXJnc47+7RDl5d50AgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgApc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTQ2xhc3NBcmcN4+OA4dRKoAIAB1oAB2ltcEZsYWdaAAhvdmVyRmxhZ1oAB3ZhbEZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBldAAqTHNjYWxhbi9wbHVnaW4vU2NhbGFuQXN0L3BhY2thZ2UkU1RwZUV4cHI7eHAAAAFxAH4AD3NyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHIADHNjYWxhLk9wdGlvbv5pN/3bDmZ0AgAAeHB0AAVzdGFydHNyAC1zY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNUcGVQcmltaXRpdmWh0XKwCxGYzwIAAkwAEmRlZmF1bHRWYWx1ZVN0cmluZ3EAfgAETAAEbmFtZXEAfgAEeHB0AAEwdAADSW50c3EAfgAVAAABcQB+AA9xAH4AGnQAA2VuZHEAfgAdcQB+ABF4c3EAfgAGc3IAKnNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU01ldGhvZERlZvmEE51y6b/mAgAJWgAKaXNJbXBsaWNpdEwAC2Fubm90YXRpb25zcQB+AAFMAAthcmdTZWN0aW9uc3EAfgABTAAEYm9keXEAfgADTAAEZWxlbXEAfgADTAAEbmFtZXEAfgAETAAKb3ZlcmxvYWRJZHEAfgADTAAHdHBlQXJnc3EAfgABTAAGdHBlUmVzcQB+AAN4cABxAH4AD3EAfgAPc3IACnNjYWxhLlNvbWURIvJpXqGLdAIAAUwAAXh0ABJMamF2YS9sYW5nL09iamVjdDt4cQB+ABlzcgAmc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTQmxvY2v3ZZL5nLlJAgIAAkwABGluaXRxAH4AAUwABGxhc3R0ACdMc2NhbGFuL3BsdWdpbi9TY2FsYW5Bc3QvcGFja2FnZSRTRXhwcjt4cHNxAH4ABnNyACdzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNWYWxEZWbZTf0Fl6ohGgIABVoACmlzSW1wbGljaXRaAAZpc0xhenlMAARleHBycQB+AClMAARuYW1lcQB+AARMAAN0cGVxAH4AA3hwAABzcgAmc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTQ29uc3QX4/okMKYuSwIAAUwAAWNxAH4AJnhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXQABGZsYWdzcQB+ACVzcQB+ABx0AAVmYWxzZXQAB0Jvb2xlYW5zcQB+ACMAcQB+AA9zcQB+AAZzcgArc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTTWV0aG9kQXJnc6QpPHSayvZAAgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgAqc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTTWV0aG9kQXJnZehbD1WvppkCAAZaAAdpbXBGbGFnWgAIb3ZlckZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBlcQB+ABZ4cAAAcQB+AA9xAH4AGnQAA3NlY3NxAH4AHHQAAjBsdAAETG9uZ3EAfgAReHEAfgAReHNxAH4AJXNyACZzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNBcHBseRVK1eDtcxvVAgADTAAEYXJnc3EAfgABTAADZnVucQB+AClMAAJ0c3EAfgABeHBzcQB+AAZzcQB+AC5zcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAHNyACZzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNJZGVudNyiim13wwMbAgABTAAEbmFtZXEAfgAEeHB0AANzZWNzcgAqc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTVHlwZUFwcGx5a0htXQ6vcXECAAJMAANmdW5xAH4AKUwAAnRzcQB+AAF4cHNyACdzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNTZWxlY3SMcZPIgfIzfAIAAkwABGV4cHJxAH4AKUwABXRuYW1lcQB+AAR4cHNxAH4ALnNxAH4ARwAAAAJ0AAxhc0luc3RhbmNl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"
  }
}