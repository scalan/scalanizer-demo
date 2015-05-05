package fres {
  object StagedEvaluation {
    import scalan._;
    import scala.reflect.runtime.universe._;
    import scala.reflect._;
    import scalan.common.Default;
    trait FresAbs extends Fres with ScalanDsl { self: FresDsl =>
      implicit def proxyFree[F[_], A](p: Rep[Free[F, A]]): Free[F, A] = proxyOps[Free[F, A]](p)(classTag[Free[F, A]]);
      class FreeElem[F[_], A, To <: Free[F, A]](implicit val cF: Cont[F], val eA: Elem[A]) extends EntityElem[To] {
        override def isEntityType = true;
        override def tag = {
          implicit val tagA = eA.tag;
          weakTypeTag[Free[F, A]].asInstanceOf[WeakTypeTag[To]]
        };
        override def convert(x: Rep[(Reifiable[_$1] forSome { 
          type _$1
        })]) = convertFree(x.asRep[Free[F, A]]);
        def convertFree(x: Rep[Free[F, A]]): Rep[To] = x.asRep[To];
        override def getDefaultRep: Rep[To] = ???
      };
      implicit def freeElement[F[_], A](implicit cF: Cont[F], eA: Elem[A]): Elem[Free[F, A]] = {
        final class $anon extends FreeElem[F, A, Free[F, A]];
        new $anon()
      };
      trait FreeCompanionElem extends CompanionElem[FreeCompanionAbs];
      implicit lazy val FreeCompanionElem: FreeCompanionElem = {
        final class $anon extends FreeCompanionElem {
          lazy val tag = weakTypeTag[FreeCompanionAbs];
          protected def getDefaultRep = Free
        };
        new $anon()
      };
      abstract class FreeCompanionAbs extends CompanionBase[FreeCompanionAbs] with FreeCompanion {
        override def toString = "Free"
      };
      def Free: Rep[FreeCompanionAbs];
      implicit def proxyFreeCompanion(p: Rep[FreeCompanion]): FreeCompanion = proxyOps[FreeCompanion](p);
      class ReturnElem[F[_], A](val iso: Iso[ReturnData[F, A], Return[F, A]])(implicit cF: Cont[F], eA: Elem[A]) extends FreeElem[F, A, Return[F, A]] with ConcreteElem[ReturnData[F, A], Return[F, A]] {
        override def convertFree(x: Rep[Free[F, A]]) = !!!("Cannot convert from Free to Return: missing fields List(a)");
        override def getDefaultRep = super[ConcreteElem].getDefaultRep;
        override lazy val tag = super[ConcreteElem].tag
      };
      type ReturnData[F[_], A] = A;
      class ReturnIso[F[_], A](implicit cF: Cont[F], eA: Elem[A]) extends Iso[ReturnData[F, A], Return[F, A]] {
        override def from(p: Rep[Return[F, A]]) = p.a;
        override def to(p: Rep[A]) = {
          val a = p;
          Return(a)
        };
        lazy val tag = {
          implicit val tagA = eA.tag;
          weakTypeTag[Return[F, A]]
        };
        lazy val defaultRepTo = Default.defaultVal[Rep[Return[F, A]]](Return(element[A].defaultRepValue));
        lazy val eTo = new ReturnElem[F, A](this)
      };
      abstract class ReturnCompanionAbs extends CompanionBase[ReturnCompanionAbs] with ReturnCompanion {
        override def toString = "Return";
        def apply[F[_], A](a: Rep[A])(implicit cF: Cont[F], eA: Elem[A]): Rep[Return[F, A]] = mkReturn(a)
      };
      object ReturnMatcher {
        def unapply[F[_], A](p: Rep[Free[F, A]]) = unmkReturn(p)
      };
      def Return: Rep[ReturnCompanionAbs];
      implicit def proxyReturnCompanion(p: Rep[ReturnCompanionAbs]): ReturnCompanionAbs = proxyOps[ReturnCompanionAbs](p);
      class ReturnCompanionElem extends CompanionElem[ReturnCompanionAbs] {
        lazy val tag = weakTypeTag[ReturnCompanionAbs];
        protected def getDefaultRep = Return
      };
      implicit lazy val ReturnCompanionElem: ReturnCompanionElem = new ReturnCompanionElem();
      implicit def proxyReturn[F[_], A](p: Rep[Return[F, A]]): Return[F, A] = proxyOps[Return[F, A]](p);
      implicit class ExtendedReturn[F[_], A](p: Rep[Return[F, A]])(implicit cF: Cont[F], eA: Elem[A]) {
        def toData: Rep[ReturnData[F, A]] = isoReturn(cF, eA).from(p)
      };
      implicit def isoReturn[F[_], A](implicit cF: Cont[F], eA: Elem[A]): Iso[ReturnData[F, A], Return[F, A]] = new ReturnIso[F, A]();
      def mkReturn[F[_], A](a: Rep[A])(implicit cF: Cont[F], eA: Elem[A]): Rep[Return[F, A]];
      def unmkReturn[F[_], A](p: Rep[Free[F, A]]): Option[Rep[A]];
      class SuspendElem[F[_], A](val iso: Iso[SuspendData[F, A], Suspend[F, A]])(implicit cF: Cont[F], eA: Elem[A]) extends FreeElem[F, A, Suspend[F, A]] with ConcreteElem[SuspendData[F, A], Suspend[F, A]] {
        override def convertFree(x: Rep[Free[F, A]]) = !!!("Cannot convert from Free to Suspend: missing fields List(a)");
        override def getDefaultRep = super[ConcreteElem].getDefaultRep;
        override lazy val tag = super[ConcreteElem].tag
      };
      type SuspendData[F[_], A] = F[A];
      class SuspendIso[F[_], A](implicit cF: Cont[F], eA: Elem[A]) extends Iso[SuspendData[F, A], Suspend[F, A]] {
        override def from(p: Rep[Suspend[F, A]]) = p.a;
        override def to(p: Rep[F[A]]) = {
          val a = p;
          Suspend(a)
        };
        lazy val tag = {
          implicit val tagA = eA.tag;
          weakTypeTag[Suspend[F, A]]
        };
        lazy val defaultRepTo = Default.defaultVal[Rep[Suspend[F, A]]](Suspend(cF.lift(eA).defaultRepValue));
        lazy val eTo = new SuspendElem[F, A](this)
      };
      abstract class SuspendCompanionAbs extends CompanionBase[SuspendCompanionAbs] with SuspendCompanion {
        override def toString = "Suspend";
        def apply[F[_], A](a: Rep[F[A]])(implicit cF: Cont[F], eA: Elem[A]): Rep[Suspend[F, A]] = mkSuspend(a)
      };
      object SuspendMatcher {
        def unapply[F[_], A](p: Rep[Free[F, A]]) = unmkSuspend(p)
      };
      def Suspend: Rep[SuspendCompanionAbs];
      implicit def proxySuspendCompanion(p: Rep[SuspendCompanionAbs]): SuspendCompanionAbs = proxyOps[SuspendCompanionAbs](p);
      class SuspendCompanionElem extends CompanionElem[SuspendCompanionAbs] {
        lazy val tag = weakTypeTag[SuspendCompanionAbs];
        protected def getDefaultRep = Suspend
      };
      implicit lazy val SuspendCompanionElem: SuspendCompanionElem = new SuspendCompanionElem();
      implicit def proxySuspend[F[_], A](p: Rep[Suspend[F, A]]): Suspend[F, A] = proxyOps[Suspend[F, A]](p);
      implicit class ExtendedSuspend[F[_], A](p: Rep[Suspend[F, A]])(implicit cF: Cont[F], eA: Elem[A]) {
        def toData: Rep[SuspendData[F, A]] = isoSuspend(cF, eA).from(p)
      };
      implicit def isoSuspend[F[_], A](implicit cF: Cont[F], eA: Elem[A]): Iso[SuspendData[F, A], Suspend[F, A]] = new SuspendIso[F, A]();
      def mkSuspend[F[_], A](a: Rep[F[A]])(implicit cF: Cont[F], eA: Elem[A]): Rep[Suspend[F, A]];
      def unmkSuspend[F[_], A](p: Rep[Free[F, A]]): Option[Rep[F[A]]];
      class BindElem[F[_], S, B](val iso: Iso[BindData[F, S, B], Bind[F, S, B]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]) extends FreeElem[F, B, Bind[F, S, B]] with ConcreteElem[BindData[F, S, B], Bind[F, S, B]] {
        override def convertFree(x: Rep[Free[F, B]]) = !!!("Cannot convert from Free to Bind: missing fields List(a, f)");
        override def getDefaultRep = super[ConcreteElem].getDefaultRep;
        override lazy val tag = super[ConcreteElem].tag
      };
      type BindData[F[_], S, B] = scala.Tuple2[Free[F, S], _root_.scala.Function1[S, Free[F, B]]];
      class BindIso[F[_], S, B](implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]) extends Iso[BindData[F, S, B], Bind[F, S, B]] {
        override def from(p: Rep[Bind[F, S, B]]) = scala.Tuple2(p.a, p.f);
        override def to(p: Rep[scala.Tuple2[Free[F, S], _root_.scala.Function1[S, Free[F, B]]]]) = {
          val x$1 = (p: @scala.unchecked) match {
            case Pair((a @ _), (f @ _)) => scala.Tuple2(a, f)
          };
          val a = x$1._1;
          val f = x$1._2;
          Bind(a, f)
        };
        lazy val tag = {
          implicit val tagS = eS.tag;
          implicit val tagB = eA.tag;
          weakTypeTag[Bind[F, S, B]]
        };
        lazy val defaultRepTo = Default.defaultVal[Rep[Bind[F, S, B]]](Bind(element[Free[F, S]].defaultRepValue, fun(((x: Rep[S]) => element[Free[F, B]].defaultRepValue))));
        lazy val eTo = new BindElem[F, S, B](this)
      };
      abstract class BindCompanionAbs extends CompanionBase[BindCompanionAbs] with BindCompanion {
        override def toString = "Bind";
        def apply[F[_], S, B](p: Rep[BindData[F, S, B]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]): Rep[Bind[F, S, B]] = isoBind(cF, eS, eA).to(p);
        def apply[F[_], S, B](a: Rep[Free[F, S]], f: Rep[_root_.scala.Function1[S, Free[F, B]]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]): Rep[Bind[F, S, B]] = mkBind(a, f)
      };
      object BindMatcher {
        def unapply[F[_], S, B](p: Rep[Free[F, B]]) = unmkBind(p)
      };
      def Bind: Rep[BindCompanionAbs];
      implicit def proxyBindCompanion(p: Rep[BindCompanionAbs]): BindCompanionAbs = proxyOps[BindCompanionAbs](p);
      class BindCompanionElem extends CompanionElem[BindCompanionAbs] {
        lazy val tag = weakTypeTag[BindCompanionAbs];
        protected def getDefaultRep = Bind
      };
      implicit lazy val BindCompanionElem: BindCompanionElem = new BindCompanionElem();
      implicit def proxyBind[F[_], S, B](p: Rep[Bind[F, S, B]]): Bind[F, S, B] = proxyOps[Bind[F, S, B]](p);
      implicit class ExtendedBind[F[_], S, B](p: Rep[Bind[F, S, B]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]) {
        def toData: Rep[BindData[F, S, B]] = isoBind(cF, eS, eA).from(p)
      };
      implicit def isoBind[F[_], S, B](implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]): Iso[BindData[F, S, B], Bind[F, S, B]] = new BindIso[F, S, B]();
      def mkBind[F[_], S, B](a: Rep[Free[F, S]], f: Rep[_root_.scala.Function1[S, Free[F, B]]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]): Rep[Bind[F, S, B]];
      def unmkBind[F[_], S, B](p: Rep[Free[F, B]]): Option[scala.Tuple2[Rep[Free[F, S]], Rep[_root_.scala.Function1[S, Free[F, B]]]]]
    };
    trait FresSeq extends FresDsl with ScalanSeq { self: FresDslSeq =>
      lazy val Free: Rep[FreeCompanionAbs] = {
        final class $anon extends FreeCompanionAbs with UserTypeSeq[FreeCompanionAbs] {
          lazy val selfType = element[FreeCompanionAbs]
        };
        new $anon()
      };
      case class SeqReturn[F[_], A](override val a: Rep[A])(implicit cF: Cont[F], eA: Elem[A]) extends Return[F, A](a) with UserTypeSeq[Return[F, A]] {
        lazy val selfType = element[Return[F, A]]
      };
      lazy val Return = {
        final class $anon extends ReturnCompanionAbs with UserTypeSeq[ReturnCompanionAbs] {
          lazy val selfType = element[ReturnCompanionAbs]
        };
        new $anon()
      };
      def mkReturn[F[_], A](a: Rep[A])(implicit cF: Cont[F], eA: Elem[A]): Rep[Return[F, A]] = new SeqReturn[F, A](a);
      def unmkReturn[F[_], A](p: Rep[Free[F, A]]) = p match {
        case (p @ ((_): Return[F, A] @unchecked)) => Some(p.a)
        case _ => None
      };
      case class SeqSuspend[F[_], A](override val a: Rep[F[A]])(implicit cF: Cont[F], eA: Elem[A]) extends Suspend[F, A](a) with UserTypeSeq[Suspend[F, A]] {
        lazy val selfType = element[Suspend[F, A]]
      };
      lazy val Suspend = {
        final class $anon extends SuspendCompanionAbs with UserTypeSeq[SuspendCompanionAbs] {
          lazy val selfType = element[SuspendCompanionAbs]
        };
        new $anon()
      };
      def mkSuspend[F[_], A](a: Rep[F[A]])(implicit cF: Cont[F], eA: Elem[A]): Rep[Suspend[F, A]] = new SeqSuspend[F, A](a);
      def unmkSuspend[F[_], A](p: Rep[Free[F, A]]) = p match {
        case (p @ ((_): Suspend[F, A] @unchecked)) => Some(p.a)
        case _ => None
      };
      case class SeqBind[F[_], S, B](override val a: Rep[Free[F, S]], override val f: Rep[_root_.scala.Function1[S, Free[F, B]]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]) extends Bind[F, S, B](a, f) with UserTypeSeq[Bind[F, S, B]] {
        lazy val selfType = element[Bind[F, S, B]]
      };
      lazy val Bind = {
        final class $anon extends BindCompanionAbs with UserTypeSeq[BindCompanionAbs] {
          lazy val selfType = element[BindCompanionAbs]
        };
        new $anon()
      };
      def mkBind[F[_], S, B](a: Rep[Free[F, S]], f: Rep[_root_.scala.Function1[S, Free[F, B]]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]): Rep[Bind[F, S, B]] = new SeqBind[F, S, B](a, f);
      def unmkBind[F[_], S, B](p: Rep[Free[F, B]]) = p match {
        case (p @ ((_): Bind[F, S, B] @unchecked)) => Some(scala.Tuple2(p.a, p.f))
        case _ => None
      }
    };
    trait FresExp extends FresDsl with ScalanExp { self: FresDslExp =>
      lazy val Free: Rep[FreeCompanionAbs] = {
        final class $anon extends FreeCompanionAbs with UserTypeDef[FreeCompanionAbs] {
          lazy val selfType = element[FreeCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      case class ExpReturn[F[_], A](override val a: Rep[A])(implicit cF: Cont[F], eA: Elem[A]) extends Return[F, A](a) with UserTypeDef[Return[F, A]] {
        lazy val selfType = element[Return[F, A]];
        override def mirror(t: Transformer) = ExpReturn[F, A](t(a))
      };
      lazy val Return: Rep[ReturnCompanionAbs] = {
        final class $anon extends ReturnCompanionAbs with UserTypeDef[ReturnCompanionAbs] {
          lazy val selfType = element[ReturnCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      object ReturnMethods;
      object ReturnCompanionMethods;
      def mkReturn[F[_], A](a: Rep[A])(implicit cF: Cont[F], eA: Elem[A]): Rep[Return[F, A]] = new ExpReturn[F, A](a);
      def unmkReturn[F[_], A](p: Rep[Free[F, A]]) = p.elem.asInstanceOf[(Elem[_$2] forSome { 
        type _$2
      })] match {
        case ((_): ReturnElem[F, A] @unchecked) => Some(p.asRep[Return[F, A]].a)
        case _ => None
      };
      case class ExpSuspend[F[_], A](override val a: Rep[F[A]])(implicit cF: Cont[F], eA: Elem[A]) extends Suspend[F, A](a) with UserTypeDef[Suspend[F, A]] {
        lazy val selfType = element[Suspend[F, A]];
        override def mirror(t: Transformer) = ExpSuspend[F, A](t(a))
      };
      lazy val Suspend: Rep[SuspendCompanionAbs] = {
        final class $anon extends SuspendCompanionAbs with UserTypeDef[SuspendCompanionAbs] {
          lazy val selfType = element[SuspendCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      object SuspendMethods;
      object SuspendCompanionMethods;
      def mkSuspend[F[_], A](a: Rep[F[A]])(implicit cF: Cont[F], eA: Elem[A]): Rep[Suspend[F, A]] = new ExpSuspend[F, A](a);
      def unmkSuspend[F[_], A](p: Rep[Free[F, A]]) = p.elem.asInstanceOf[(Elem[_$3] forSome { 
        type _$3
      })] match {
        case ((_): SuspendElem[F, A] @unchecked) => Some(p.asRep[Suspend[F, A]].a)
        case _ => None
      };
      case class ExpBind[F[_], S, B](override val a: Rep[Free[F, S]], override val f: Rep[_root_.scala.Function1[S, Free[F, B]]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]) extends Bind[F, S, B](a, f) with UserTypeDef[Bind[F, S, B]] {
        lazy val selfType = element[Bind[F, S, B]];
        override def mirror(t: Transformer) = ExpBind[F, S, B](t(a), t(f))
      };
      lazy val Bind: Rep[BindCompanionAbs] = {
        final class $anon extends BindCompanionAbs with UserTypeDef[BindCompanionAbs] {
          lazy val selfType = element[BindCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      object BindMethods;
      object BindCompanionMethods;
      def mkBind[F[_], S, B](a: Rep[Free[F, S]], f: Rep[_root_.scala.Function1[S, Free[F, B]]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]): Rep[Bind[F, S, B]] = new ExpBind[F, S, B](a, f);
      def unmkBind[F[_], S, B](p: Rep[Free[F, B]]) = p.elem.asInstanceOf[(Elem[_$4] forSome { 
        type _$4
      })] match {
        case ((_): BindElem[F, S, B] @unchecked) => Some(scala.Tuple2(p.asRep[Bind[F, S, B]].a, p.asRep[Bind[F, S, B]].f))
        case _ => None
      };
      object FreeMethods {
        object step {
          def unapply(d: (Def[_$5] forSome { 
            type _$5
          })): Option[(Rep[Free[F, A]] forSome { 
            type F[_];
            type A
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if (receiver.elem match {
  case (ve @ ((_): ViewElem[(_), (_)])) => (ve match {
    case ((_): FreeElem[(_), (_), (_)]) => true
    case _ => false
  })
  case _ => false
}).&&(method.getName.==("step")) => Some(receiver).asInstanceOf[Option[(Rep[Free[F, A]] forSome { 
              type F[_];
              type A
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$6] forSome { 
            type _$6
          })): Option[(Rep[Free[F, A]] forSome { 
            type F[_];
            type A
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object FreeCompanionMethods
    };
    trait Fres extends Base { self: FresDsl =>
      trait Free[F[_], A] extends Reifiable[Free[F, A]] {
        implicit def cF: Cont[F];
        implicit def eA: Elem[A];
        def flatMap[B](f: Rep[scala.Function1[A, Free[F, B]]])(implicit eB: Elem[B]): Rep[Free[F, B]] = flatMapBy(f);
        def flatMapBy[B](f: Rep[scala.Function1[A, Free[F, B]]])(implicit eB: Elem[B]): Rep[Free[F, B]] = Bind(this, f);
        def mapBy[B](f: Rep[scala.Function1[A, B]])(implicit eB: Elem[B]): Rep[Free[F, B]] = flatMap(fun(((a) => Return(f(a)))));
        def map[B](f: Rep[scala.Function1[A, B]])(implicit eB: Elem[B]): Rep[Free[F, B]] = mapBy(f);
        def step: Rep[Free[F, A]] = this
      };
      abstract class Return[F[_], A](val a: Rep[A])(implicit val cF: Cont[F], val eA: Elem[A]) extends Free[F, A] {
        override def flatMap[B](f: Rep[scala.Function1[A, Free[F, B]]])(implicit eB: Elem[B]): Rep[Free[F, B]] = f(a);
        override def flatMapBy[B](f: Rep[scala.Function1[A, Free[F, B]]])(implicit eB: Elem[B]): Rep[Free[F, B]] = f(a)
      };
      abstract class Suspend[F[_], A](val a: Rep[F[A]])(implicit val cF: Cont[F], val eA: Elem[A]) extends Free[F, A];
      abstract class Bind[F[_], S, B](val a: Rep[Free[F, S]], val f: Rep[scala.Function1[S, Free[F, B]]])(implicit val cF: Cont[F], val eS: Elem[S], val eA: Elem[B]) extends Free[F, B] {
        override def flatMap[R](f1: Rep[scala.Function1[B, Free[F, R]]])(implicit eR: Elem[R]): Rep[Free[F, R]] = a.flatMap(fun(((s: Rep[S]) => f(s).flatMap(f1))));
        override def flatMapBy[R](f1: Rep[scala.Function1[B, Free[F, R]]])(implicit eR: Elem[R]): Rep[Free[F, R]] = a.flatMap(fun(((s: Rep[S]) => f(s).flatMapBy(f1))))
      };
      trait FreeCompanion;
      trait ReturnCompanion;
      trait SuspendCompanion;
      trait BindCompanion
    };
    trait FresDsl extends FresAbs;
    trait FresDslSeq extends FresSeq;
    trait FresDslExp extends FresExp;
    val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZrSr+/co886dAgALTAAEYm9keXQAIUxzY2FsYS9jb2xsZWN0aW9uL2ltbXV0YWJsZS9MaXN0O0wAEGNvbmNyZXRlU0NsYXNzZXNxAH4AAUwACGVudGl0aWVzcQB+AAFMAAllbnRpdHlPcHN0ACFMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcmFpdERlZjtMABBlbnRpdHlSZXBTeW5vbnltdAAOTHNjYWxhL09wdGlvbjtMAAdpbXBvcnRzcQB+AAFMAAdtZXRob2RzcQB+AAFMAARuYW1ldAASTGphdmEvbGFuZy9TdHJpbmc7TAALcGFja2FnZU5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAApzZXFEc2xJbXBscQB+AAN4cHNyADJzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0JFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABAwAAeHBzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzRGVmB9B9aPGocDMCAApaAAppc0Fic3RyYWN0TAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYXJnc3QAIkxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU0NsYXNzQXJncztMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAMTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0Q2FsbFDrGS0l7EVYAgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQABEZyZWVzcQB+AAZzcQB+AA90AAFGcQB+AAdzcQB+AA90AAFBcQB+AAdxAH4ACXhxAH4ACXhxAH4AB3NyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzQXJncyfr7mTFMABcAgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZwVz2NYL1Ck/AgAIWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnWgAHdmFsRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGV0ACBMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcGVFeHByO3hwAAAAAXEAfgAHc3IAC3NjYWxhLk5vbmUkRlAk9lPKlKwCAAB4cgAMc2NhbGEuT3B0aW9u/mk3/dsOZnQCAAB4cHQAAWFzcQB+AA90AAFBcQB+AAdxAH4ACXhzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2REZWbdWXFxtzArDgIACloADGlzRWxlbU9yQ29udFoACmlzSW1wbGljaXRaAAppc092ZXJyaWRlTAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0cGVSZXNxAH4AA3hwAAABcQB+AAdzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2RBcmdzKcInll51uosCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZwPc/8uTcnMDAgAHWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXEAfgAbeHAAAABxAH4AB3EAfgAfdAABZnNyAB5zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZUZ1bmPUSZkTDx1f4QIAAkwABmRvbWFpbnEAfgAbTAAFcmFuZ2VxAH4AG3hwc3EAfgAPdAABQXEAfgAHc3EAfgAPdAAERnJlZXNxAH4ABnNxAH4AD3QAAUZxAH4AB3NxAH4AD3QAAUJxAH4AB3EAfgAJeHEAfgAJeHEAfgAJeHNyAApzY2FsYS5T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dCRTQ29udHLoUUnVGk81tQIAAkwABGFyZ3NxAH4AAUwABG5hbWVxAH4ABHhwc3EAfgAGc3IAG3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVGhpc+oe0MagX4tnAgABTAAIdHlwZU5hbWVxAH4ABHhwdAAAc3EAfgBAcQB+ACxxAH4ACXh0AARCaW5kdAAJZmxhdE1hcEJ5cQB+AB9zcQB+AAZzcQB+AEZxAH4AH3EAfgAHdAABQnEAfgAHcQB+AAl4c3EAfgA4c3EAfgAPdAAERnJlZXNxAH4ABnNxAH4AD3QAAUZxAH4AB3NxAH4AD3QAAUJxAH4AB3EAfgAJeHNxAH4AJAAAAHEAfgAHc3EAfgAGc3EAfgAnc3EAfgAGc3EAfgAqAAAAcQB+AAdxAH4AH3EAfgAsc3EAfgAtc3EAfgAPdAABQXEAfgAHc3EAfgAPdAABQnEAfgAHcQB+AAl4cQB+AAl4c3EAfgA4c3EAfgA7c3EAfgAGc3EAfgAGc3EAfgDMc3EAfgAGc3EAfgDPAABzcQB+ANF0AAFhcQB+AB9xAH4ACXhzcQB+AWpzcQB+AAZzcQB+ADtzcQB+AAZzcQB+AAZzcQB+AEB0AAFhcQB+AAl4cQB+AAl4c3EAfgBAcQB+ACxxAH4AB3EAfgAJeHQABlJldHVybnEAfgAJeHEAfgAJeHNxAH4AQHEAfgBEcQB+AAd0AAVtYXBCeXEAfgAfc3EAfgAGc3EAfgBGcQB+AB9xAH4AB3QAAUJxAH4AB3EAfgAJeHNxAH4AOHNxAH4AD3QABEZyZWVzcQB+AAZzcQB+AA90AAFGcQB+AAdzcQB+AA90AAFCcQB+AAdxAH4ACXhzcQB+ACQAAABxAH4AB3NxAH4ABnNxAH4AJ3NxAH4ABnNxAH4AKgAAAHEAfgAHcQB+AB9xAH4ALHNxAH4ALXNxAH4AD3QAAUFxAH4AB3NxAH4AD3QAAUJxAH4AB3EAfgAJeHEAfgAJeHNxAH4AOHNxAH4AO3NxAH4ABnNxAH4ABnNxAH4AQHEAfgAscQB+AAl4cQB+AAl4c3EAfgBAdAAFbWFwQnlxAH4AB3QAA21hcHEAfgAfc3EAfgAGc3EAfgBGcQB+AB9xAH4AB3QAAUJxAH4AB3EAfgAJeHNxAH4AOHNxAH4AD3QABEZyZWVzcQB+AAZzcQB+AA90AAFGcQB+AAdzcQB+AA90AAFCcQB+AAdxAH4ACXhzcQB+ACQAAABxAH4AB3EAfgAHc3EAfgA4c3EAfgFtcQB+AW90AARzdGVwcQB+AB9xAH4AB3NxAH4AOHNxAH4AD3QABEZyZWVzcQB+AAZzcQB+AA90AAFGcQB+AAdzcQB+AA90AAFBcQB+AAdxAH4ACXhxAH4ACXhxAH4AH3B0AARGcmVlcQB+AB9zcQB+AAZzcQB+AEZxAH4AH3EAfgAHdAABRnNxAH4ABnNxAH4ARnEAfgAfcQB+AAdxAH4AenEAfgAHcQB+AAl4c3EAfgBGcQB+AB9xAH4AB3QAAUFxAH4AB3EAfgAJeHEAfgAJeHEAfgE3cQB+AB9xAH4AB3EAfgAHdAAERnJlc3QABGZyZXNxAH4AH3EAfgAf"
  }
}