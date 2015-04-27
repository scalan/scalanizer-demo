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
        override def tag = weakTypeTag[Segm].asInstanceOf[WeakTypeTag[To]];
        override def convert(x: Rep[(Reifiable[_$1] forSome { 
          type _$1
        })]) = convertSegm(x.asRep[Segm]);
        def convertSegm(x: Rep[Segm]): Rep[To] = x.asRep[To];
        override def getDefaultRep: Rep[To] = ???
      };
      implicit def segmElement: Elem[Segm] = {
        final class $anon extends SegmElem[Segm];
        new $anon()
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
      class IntervalElem(val iso: Iso[IntervalData, Interval]) extends SegmElem[Interval] with ConcreteElem[IntervalData, Interval] {
        override def convertSegm(x: Rep[Segm]) = Interval(x.start, x.end);
        override def getDefaultRep = super[ConcreteElem].getDefaultRep;
        override lazy val tag = super[ConcreteElem].tag
      };
      type IntervalData = scala.Tuple2[Int, Int];
      class IntervalIso extends Iso[IntervalData, Interval] {
        override def from(p: Rep[Interval]) = scala.Tuple2(p.start, p.end);
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
        def apply(start: Rep[Int], end: Rep[Int]): Rep[Interval] = mkInterval(start, end)
      };
      object IntervalMatcher {
        def unapply(p: Rep[Segm]) = unmkInterval(p)
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
      def unmkInterval(p: Rep[Segm]): Option[scala.Tuple2[Rep[Int], Rep[Int]]];
      class SliceElem(val iso: Iso[SliceData, Slice]) extends SegmElem[Slice] with ConcreteElem[SliceData, Slice] {
        override def convertSegm(x: Rep[Segm]) = Slice(x.start, x.length);
        override def getDefaultRep = super[ConcreteElem].getDefaultRep;
        override lazy val tag = super[ConcreteElem].tag
      };
      type SliceData = scala.Tuple2[Int, Int];
      class SliceIso extends Iso[SliceData, Slice] {
        override def from(p: Rep[Slice]) = scala.Tuple2(p.start, p.length);
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
        def apply(start: Rep[Int], length: Rep[Int]): Rep[Slice] = mkSlice(start, length)
      };
      object SliceMatcher {
        def unapply(p: Rep[Segm]) = unmkSlice(p)
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
      def unmkSlice(p: Rep[Segm]): Option[scala.Tuple2[Rep[Int], Rep[Int]]];
      class CenteredElem(val iso: Iso[CenteredData, Centered]) extends SegmElem[Centered] with ConcreteElem[CenteredData, Centered] {
        override def convertSegm(x: Rep[Segm]) = !!!("Cannot convert from Segm to Centered: missing fields List(center, radius)");
        override def getDefaultRep = super[ConcreteElem].getDefaultRep;
        override lazy val tag = super[ConcreteElem].tag
      };
      type CenteredData = scala.Tuple2[Int, Int];
      class CenteredIso extends Iso[CenteredData, Centered] {
        override def from(p: Rep[Centered]) = scala.Tuple2(p.center, p.radius);
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
        def apply(center: Rep[Int], radius: Rep[Int]): Rep[Centered] = mkCentered(center, radius)
      };
      object CenteredMatcher {
        def unapply(p: Rep[Segm]) = unmkCentered(p)
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
        };
        object id {
          def unapply(d: (Def[_$11] forSome { 
            type _$11
          })): Option[Rep[Slice]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[SliceElem].&&(method.getName.==("id")) => Some(receiver).asInstanceOf[Option[Rep[Slice]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$12] forSome { 
            type _$12
          })): Option[Rep[Slice]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object inc {
          def unapply(d: (Def[_$13] forSome { 
            type _$13
          })): Option[scala.Tuple2[Rep[Slice], Rep[Int]]] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((x @ _), _*), _) if receiver.elem.isInstanceOf[SliceElem].&&(method.getName.==("inc")) => Some(scala.Tuple2(receiver, x)).asInstanceOf[Option[scala.Tuple2[Rep[Slice], Rep[Int]]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$14] forSome { 
            type _$14
          })): Option[scala.Tuple2[Rep[Slice], Rep[Int]]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object SliceCompanionMethods;
      def mkSlice(start: Rep[Int], length: Rep[Int]): Rep[Slice] = new ExpSlice(start, length);
      def unmkSlice(p: Rep[Segm]) = p.elem.asInstanceOf[(Elem[_$15] forSome { 
        type _$15
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
          def unapply(d: (Def[_$16] forSome { 
            type _$16
          })): Option[Rep[Centered]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[CenteredElem].&&(method.getName.==("start")) => Some(receiver).asInstanceOf[Option[Rep[Centered]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$17] forSome { 
            type _$17
          })): Option[Rep[Centered]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object end {
          def unapply(d: (Def[_$18] forSome { 
            type _$18
          })): Option[Rep[Centered]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[CenteredElem].&&(method.getName.==("end")) => Some(receiver).asInstanceOf[Option[Rep[Centered]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$19] forSome { 
            type _$19
          })): Option[Rep[Centered]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object length {
          def unapply(d: (Def[_$20] forSome { 
            type _$20
          })): Option[Rep[Centered]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[CenteredElem].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[Rep[Centered]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$21] forSome { 
            type _$21
          })): Option[Rep[Centered]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object shift {
          def unapply(d: (Def[_$22] forSome { 
            type _$22
          })): Option[scala.Tuple2[Rep[Centered], Rep[Int]]] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((ofs @ _), _*), _) if receiver.elem.isInstanceOf[CenteredElem].&&(method.getName.==("shift")) => Some(scala.Tuple2(receiver, ofs)).asInstanceOf[Option[scala.Tuple2[Rep[Centered], Rep[Int]]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$23] forSome { 
            type _$23
          })): Option[scala.Tuple2[Rep[Centered], Rep[Int]]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object id {
          def unapply(d: (Def[_$24] forSome { 
            type _$24
          })): Option[Rep[Centered]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[CenteredElem].&&(method.getName.==("id")) => Some(receiver).asInstanceOf[Option[Rep[Centered]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$25] forSome { 
            type _$25
          })): Option[Rep[Centered]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object CenteredCompanionMethods;
      def mkCentered(center: Rep[Int], radius: Rep[Int]): Rep[Centered] = new ExpCentered(center, radius);
      def unmkCentered(p: Rep[Segm]) = p.elem.asInstanceOf[(Elem[_$26] forSome { 
        type _$26
      })] match {
        case ((_): CenteredElem @unchecked) => Some(scala.Tuple2(p.asRep[Centered].center, p.asRep[Centered].radius))
        case _ => None
      };
      object SegmMethods {
        object start {
          def unapply(d: (Def[_$27] forSome { 
            type _$27
          })): Option[Rep[Segm]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(SegmElem[_$28] forSome { 
  type _$28
})].&&(method.getName.==("start")) => Some(receiver).asInstanceOf[Option[Rep[Segm]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$29] forSome { 
            type _$29
          })): Option[Rep[Segm]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object length {
          def unapply(d: (Def[_$30] forSome { 
            type _$30
          })): Option[Rep[Segm]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(SegmElem[_$31] forSome { 
  type _$31
})].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[Rep[Segm]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$32] forSome { 
            type _$32
          })): Option[Rep[Segm]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object end {
          def unapply(d: (Def[_$33] forSome { 
            type _$33
          })): Option[Rep[Segm]] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(SegmElem[_$34] forSome { 
  type _$34
})].&&(method.getName.==("end")) => Some(receiver).asInstanceOf[Option[Rep[Segm]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$35] forSome { 
            type _$35
          })): Option[Rep[Segm]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object shift {
          def unapply(d: (Def[_$36] forSome { 
            type _$36
          })): Option[scala.Tuple2[Rep[Segm], Rep[Int]]] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((ofs @ _), _*), _) if receiver.elem.isInstanceOf[(SegmElem[_$37] forSome { 
  type _$37
})].&&(method.getName.==("shift")) => Some(scala.Tuple2(receiver, ofs)).asInstanceOf[Option[scala.Tuple2[Rep[Segm], Rep[Int]]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$38] forSome { 
            type _$38
          })): Option[scala.Tuple2[Rep[Segm], Rep[Int]]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object inc {
          def unapply(d: (Def[_$39] forSome { 
            type _$39
          })): Option[scala.Tuple2[Rep[Segm], Rep[Int]]] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((x @ _), _*), _) if receiver.elem.isInstanceOf[(SegmElem[_$40] forSome { 
  type _$40
})].&&(method.getName.==("inc")) => Some(scala.Tuple2(receiver, x)).asInstanceOf[Option[scala.Tuple2[Rep[Segm], Rep[Int]]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$41] forSome { 
            type _$41
          })): Option[scala.Tuple2[Rep[Segm], Rep[Int]]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object SegmCompanionMethods
    };
    trait Segms extends Base { self: SegmsDsl =>
      implicit def defaultSegmElem: Elem[Segm] = element[Interval].asElem[Segm];
      trait Segm extends Reifiable[Segm] {
        def start: Rep[Int];
        def length: Rep[Int];
        def end: Rep[Int];
        def shift(ofs: Rep[Int]): Rep[Segm];
        def inc(x: Rep[Int]): Rep[Int] = x.+(toRep(1))
      };
      abstract class Interval(val start: Rep[Int], val end: Rep[Int]) extends Segm {
        def length: Rep[Int] = {
          type IT = (Segms)#Interval;
          val flag: Rep[Boolean] = toRep(true);
          def foo[T <: Double](sec: Rep[Long]): Rep[scala.Tuple2[Int, scala.Tuple2[Long, Double]]] = Tuple(toRep(0), sec, toRep(2).asInstanceOf[Double]);
          IF(flag).THEN({
  val diff: Rep[Int] = end.-(start);
  ((diff): Rep[Int])
}).ELSE(foo[Double](toRep(10))._1)
        };
        def shift(ofs: Rep[Int]) = {
          val res = Interval(start.+(ofs), end.+(ofs));
          res
        }
      };
      abstract class Slice(val start: Rep[Int], val length: Rep[Int]) extends Segm {
        def end: Rep[Int] = start.+(length);
        def shift(ofs: Rep[Int]) = shiftBy(((x: Rep[Int]) => x.+(ofs)));
        def shiftBy(f: Rep[scala.Function1[Int, Int]]) = Slice(f(start), length);
        def id = Slice.this;
        override def inc(x: Rep[Int]): Rep[Int] = x.+(toRep(2))
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
    val serializedMetaAst = "rO0ABXNyADBzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNFbnRpdHlNb2R1bGVEZWb/pfpNh3F99wIAC0wABGJvZHl0ACFMc2NhbGEvY29sbGVjdGlvbi9pbW11dGFibGUvTGlzdDtMABBjb25jcmV0ZVNDbGFzc2VzcQB+AAFMAAhlbnRpdGllc3EAfgABTAAJZW50aXR5T3BzdAArTHNjYWxhbi9wbHVnaW4vU2NhbGFuQXN0L3BhY2thZ2UkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4c3EAfgAGc3IAKXNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU0NsYXNzRGVmd2hOWzQYcNgCAApaAAppc0Fic3RyYWN0TAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYXJnc3QALExzY2FsYW4vcGx1Z2luL1NjYWxhbkFzdC9wYWNrYWdlJFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAqc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTVHJhaXRDYWxs5qegGwOeq3cCAAJMAARuYW1lcQB+AARMAAl0cGVTRXhwcnNxAH4AAXhwdAAEU2VnbXEAfgAHcQB+AAl4cQB+AAdzcgAqc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTQ2xhc3NBcmdzjv7tEOXl3nQCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyAClzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNDbGFzc0FyZw3j44Dh1EqgAgAHWgAHaW1wRmxhZ1oACG92ZXJGbGFnWgAHdmFsRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGV0ACpMc2NhbGFuL3BsdWdpbi9TY2FsYW5Bc3QvcGFja2FnZSRTVHBlRXhwcjt4cAAAAXEAfgAHc3IAC3NjYWxhLk5vbmUkRlAk9lPKlKwCAAB4cgAMc2NhbGEuT3B0aW9u/mk3/dsOZnQCAAB4cHQABXN0YXJ0c3IALXNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU1RwZVByaW1pdGl2ZaHRcrALEZjPAgACTAASZGVmYXVsdFZhbHVlU3RyaW5ncQB+AARMAARuYW1lcQB+AAR4cHQAATB0AANJbnRzcQB+ABUAAAFxAH4AB3EAfgAadAADZW5kcQB+AB1xAH4ACXhzcQB+AAZzcgAqc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTTWV0aG9kRGVm64Uf+HJKB9MCAApaAAxpc0VsZW1PckNvbnRaAAppc0ltcGxpY2l0WgAKaXNPdmVycmlkZUwAC2Fubm90YXRpb25zcQB+AAFMAAthcmdTZWN0aW9uc3EAfgABTAAEYm9keXEAfgADTAAEbmFtZXEAfgAETAAKb3ZlcmxvYWRJZHEAfgADTAAHdHBlQXJnc3EAfgABTAAGdHBlUmVzcQB+AAN4cAAAAHEAfgAHcQB+AAdzcgAKc2NhbGEuU29tZREi8mleoYt0AgABTAABeHQAEkxqYXZhL2xhbmcvT2JqZWN0O3hxAH4AGXNyACZzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNCbG9ja/dlkvmcuUkCAgACTAAEaW5pdHEAfgABTAAEbGFzdHQAJ0xzY2FsYW4vcGx1Z2luL1NjYWxhbkFzdC9wYWNrYWdlJFNFeHByO3hwc3EAfgAGc3IAJ3NjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU1RwZURlZqWkdzTK2CFaAgADTAAEbmFtZXEAfgAETAADcmhzcQB+ABZMAAd0cGVBcmdzcQB+AAF4cHQAAklUc3IAMHNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU1RwZVNlbGVjdEZyb21UVEJt3N5IxOFdAgACTAAJcXVhbGlmaWVycQB+ABZMAAV0bmFtZXEAfgAEeHBzcQB+AA90AAVTZWdtc3EAfgAHdAAISW50ZXJ2YWxxAH4AB3NyACdzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNWYWxEZWbZTf0Fl6ohGgIABVoACmlzSW1wbGljaXRaAAZpc0xhenlMAARleHBycQB+AClMAARuYW1lcQB+AARMAAN0cGVxAH4AA3hwAABzcgAmc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTQ29uc3QX4/okMKYuSwIAAUwAAWNxAH4AJnhwc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAXQABGZsYWdzcQB+ACVzcQB+ABx0AAVmYWxzZXQAB0Jvb2xlYW5zcQB+ACMAAABxAH4AB3NxAH4ABnNyACtzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNNZXRob2RBcmdzpCk8dJrK9kACAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyACpzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNNZXRob2RBcmdl6FsPVa+mmQIABloAB2ltcEZsYWdaAAhvdmVyRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGVxAH4AFnhwAABxAH4AB3EAfgAadAADc2Vjc3EAfgAcdAACMGx0AARMb25ncQB+AAl4cQB+AAl4c3EAfgAlc3IAJnNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU0FwcGx5FUrV4O1zG9UCAANMAARhcmdzcQB+AAFMAANmdW5xAH4AKUwAAnRzcQB+AAF4cHNxAH4ABnNxAH4ANnNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAA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AH4AHXEAfgAJeHNxAH4ABnNxAH4AIwAAAHEAfgAHcQB+AAdzcQB+ACVzcQB+AEtzcQB+AAZzcQB+AFJxAH4AjHEAfgAJeHNxAH4AV3NxAH4AUnQABXN0YXJ0cQB+AKJxAH4AB3QAA2VuZHEAfgAacQB+AAdzcQB+ACVxAH4AHXNxAH4AIwAAAHEAfgAHc3EAfgAGc3EAfgBBc3EAfgAGc3EAfgBEAABxAH4AB3EAfgAadAADb2ZzcQB+AB1xAH4ACXhxAH4ACXhzcQB+ACVzcQB+AEtzcQB+AAZzcgAlc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTRnVuY/wRT20zEwyUAgACTAAGcGFyYW1zcQB+AAFMAANyZXNxAH4AKXhwc3EAfgAGc3EAfgA0AABzcgAmc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTRW1wdHn1UQzqOi9vZAIAAHhwdAABeHNxAH4AJXEAfgAdcQB+AAl4c3EAfgBLc3EAfgAGc3EAfgBSdAADb2ZzcQB+AAl4c3EAfgBXc3EAfgBSdAABeHEAfgCicQB+AAdxAH4ACXhzcQB+AFJ0AAdzaGlmdEJ5cQB+AAd0AAVzaGlmdHEAfgAacQB+AAdxAH4AGnNxAH4AIwAAAHEAfgAHc3EAfgAGc3EAfgBBc3EAfgAGc3EAfgBEAABxAH4AB3EAfgAadAABZnNyAChzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNUcGVGdW5jl6K1LWpcFQECAAJMAAZkb21haW5xAH4AFkwABXJhbmdlcQB+ABZ4cHEAfgAdcQB+AB1xAH4ACXhxAH4ACXhzcQB+ACVzcQB+AJhzcQB+AAZzcQB+AEtzcQB+AAZzcQB+AFJ0AAVzdGFydHEAfgAJeHNxAH4AUnEAfgDpcQB+AAdzcQB+AFJxAH4AjHEAfgAJeHQABVNsaWNldAAHc2hpZnRCeXEAfgAacQB+AAdxAH4AGnNxAH4AIwAAAHEAfgAHcQB+AAdzcQB+ACVzcgAlc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTVGhpc3G4t7hVUKvjAgABTAAIdHlwZU5hbWVxAH4ABHhwdAAFU2xpY2V0AAJpZHEAfgAacQB+AAdxAH4AGnNxAH4AIwAA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ZmlhYmxlc3EAfgAGc3EAfgAPdAAEU2VnbXEAfgAHcQB+AAl4cQB+AAl4cQB+AAdzcQB+AAZzcQB+ACMAAABxAH4AB3EAfgAHcQB+ABp0AAVzdGFydHEAfgAacQB+AAdzcQB+ACVxAH4AHXNxAH4AIwAAAHEAfgAHcQB+AAdxAH4AGnEAfgCMcQB+ABpxAH4AB3NxAH4AJXEAfgAdc3EAfgAjAAAAcQB+AAdxAH4AB3EAfgAadAADZW5kcQB+ABpxAH4AB3NxAH4AJXEAfgAdc3EAfgAjAAAAcQB+AAdzcQB+AAZzcQB+AEFzcQB+AAZzcQB+AEQAAHEAfgAHcQB+ABp0AANvZnNxAH4AHXEAfgAJeHEAfgAJeHEAfgAadAAFc2hpZnRxAH4AGnEAfgAHc3EAfgAlc3EAfgAPdAAEU2VnbXEAfgAHc3EAfgAjAAAAcQB+AAdzcQB+AAZzcQB+AEFzcQB+AAZzcQB+AEQAAHEAfgAHcQB+ABp0AAF4cQB+AB1xAH4ACXhxAH4ACXhzcQB+ACVzcQB+AEtzcQB+AAZzcQB+ADZxAH4BH3EAfgAJeHNxAH4AV3NxAH4AUnQAAXhxAH4AonEAfgAHdAADaW5jcQB+ABpxAH4AB3NxAH4AJXEAfgAdcQB+AAl4c3EAfgAlc3EAfgCwAHEAfgAHcQB+AAdxAH4AB3EAfgAacHQADVNlZ21Db21wYW5pb25xAH4AGnEAfgAHc3EAfgAScQB+AAdxAH4Bf3EAfgAacQB+AAdxAH4ACXhxAH4BeXNxAH4AJXNxAH4ALHQAB1JlcFNlZ21zcQB+AA90AANSZXBzcQB+AAZzcQB+AA9xAH4Bf3EAfgAHcQB+AAl4cQB+AAdzcQB+AAZzcgArc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTSW1wb3J0U3RhdLlkytqgR7F3AgABTAAEbmFtZXEAfgAEeHB0AAhzY2FsYW4uX3EAfgAJeHEAfgAHdAAFU2VnbXN0AAVzZWdtc3NxAH4AJXNyACxzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNTZWxmVHlwZURlZrCTQo8B5HL/AgACTAAKY29tcG9uZW50c3EAfgABTAAEbmFtZXEAfgAEeHBzcQB+AAZzcQB+AA90AAhTZWdtc0RzbHEAfgAHcQB+AAl4dAAEc2VsZnEAfgAa"
  }
}