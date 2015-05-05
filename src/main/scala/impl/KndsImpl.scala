package knds {
  object StagedEvaluation {
    import scalan._;
    import scala.reflect.runtime.universe._;
    import scala.reflect._;
    import scalan.common.Default;
    trait KndsAbs extends Knds with ScalanDsl { self: KndsDsl =>
      implicit def proxyKnd[F[_], A](p: Rep[Knd[F, A]]): Knd[F, A] = proxyOps[Knd[F, A]](p)(classTag[Knd[F, A]]);
      class KndElem[F[_], A, To <: Knd[F, A]](implicit val cF: Cont[F], val eA: Elem[A]) extends EntityElem[To] {
        override def isEntityType = true;
        override def tag = {
          implicit val tagA = eA.tag;
          weakTypeTag[Knd[F, A]].asInstanceOf[WeakTypeTag[To]]
        };
        override def convert(x: Rep[(Reifiable[_$1] forSome { 
          type _$1
        })]) = convertKnd(x.asRep[Knd[F, A]]);
        def convertKnd(x: Rep[Knd[F, A]]): Rep[To] = x.asRep[To];
        override def getDefaultRep: Rep[To] = ???
      };
      implicit def kndElement[F[_], A](implicit cF: Cont[F], eA: Elem[A]): Elem[Knd[F, A]] = {
        final class $anon extends KndElem[F, A, Knd[F, A]];
        new $anon()
      };
      trait KndCompanionElem extends CompanionElem[KndCompanionAbs];
      implicit lazy val KndCompanionElem: KndCompanionElem = {
        final class $anon extends KndCompanionElem {
          lazy val tag = weakTypeTag[KndCompanionAbs];
          protected def getDefaultRep = Knd
        };
        new $anon()
      };
      abstract class KndCompanionAbs extends CompanionBase[KndCompanionAbs] with KndCompanion {
        override def toString = "Knd"
      };
      def Knd: Rep[KndCompanionAbs];
      implicit def proxyKndCompanion(p: Rep[KndCompanion]): KndCompanion = proxyOps[KndCompanion](p);
      class ReturnElem[F[_], A](val iso: Iso[ReturnData[F, A], Return[F, A]])(implicit cF: Cont[F], eA: Elem[A]) extends KndElem[F, A, Return[F, A]] with ConcreteElem[ReturnData[F, A], Return[F, A]] {
        override def convertKnd(x: Rep[Knd[F, A]]) = !!!("Cannot convert from Knd to Return: missing fields List(a)");
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
        def unapply[F[_], A](p: Rep[Knd[F, A]]) = unmkReturn(p)
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
      def unmkReturn[F[_], A](p: Rep[Knd[F, A]]): Option[Rep[A]];
      class BindElem[F[_], S, B](val iso: Iso[BindData[F, S, B], Bind[F, S, B]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]) extends KndElem[F, B, Bind[F, S, B]] with ConcreteElem[BindData[F, S, B], Bind[F, S, B]] {
        override def convertKnd(x: Rep[Knd[F, B]]) = !!!("Cannot convert from Knd to Bind: missing fields List(a, f)");
        override def getDefaultRep = super[ConcreteElem].getDefaultRep;
        override lazy val tag = super[ConcreteElem].tag
      };
      type BindData[F[_], S, B] = scala.Tuple2[Knd[F, S], _root_.scala.Function1[S, Knd[F, B]]];
      class BindIso[F[_], S, B](implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]) extends Iso[BindData[F, S, B], Bind[F, S, B]] {
        override def from(p: Rep[Bind[F, S, B]]) = scala.Tuple2(p.a, p.f);
        override def to(p: Rep[scala.Tuple2[Knd[F, S], _root_.scala.Function1[S, Knd[F, B]]]]) = {
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
        lazy val defaultRepTo = Default.defaultVal[Rep[Bind[F, S, B]]](Bind(element[Knd[F, S]].defaultRepValue, fun(((x: Rep[S]) => element[Knd[F, B]].defaultRepValue))));
        lazy val eTo = new BindElem[F, S, B](this)
      };
      abstract class BindCompanionAbs extends CompanionBase[BindCompanionAbs] with BindCompanion {
        override def toString = "Bind";
        def apply[F[_], S, B](p: Rep[BindData[F, S, B]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]): Rep[Bind[F, S, B]] = isoBind(cF, eS, eA).to(p);
        def apply[F[_], S, B](a: Rep[Knd[F, S]], f: Rep[_root_.scala.Function1[S, Knd[F, B]]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]): Rep[Bind[F, S, B]] = mkBind(a, f)
      };
      object BindMatcher {
        def unapply[F[_], S, B](p: Rep[Knd[F, B]]) = unmkBind(p)
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
      def mkBind[F[_], S, B](a: Rep[Knd[F, S]], f: Rep[_root_.scala.Function1[S, Knd[F, B]]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]): Rep[Bind[F, S, B]];
      def unmkBind[F[_], S, B](p: Rep[Knd[F, B]]): Option[scala.Tuple2[Rep[Knd[F, S]], Rep[_root_.scala.Function1[S, Knd[F, B]]]]]
    };
    trait KndsSeq extends KndsDsl with ScalanSeq { self: KndsDslSeq =>
      lazy val Knd: Rep[KndCompanionAbs] = {
        final class $anon extends KndCompanionAbs with UserTypeSeq[KndCompanionAbs] {
          lazy val selfType = element[KndCompanionAbs]
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
      def unmkReturn[F[_], A](p: Rep[Knd[F, A]]) = p match {
        case (p @ ((_): Return[F, A] @unchecked)) => Some(p.a)
        case _ => None
      };
      case class SeqBind[F[_], S, B](override val a: Rep[Knd[F, S]], override val f: Rep[_root_.scala.Function1[S, Knd[F, B]]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]) extends Bind[F, S, B](a, f) with UserTypeSeq[Bind[F, S, B]] {
        lazy val selfType = element[Bind[F, S, B]]
      };
      lazy val Bind = {
        final class $anon extends BindCompanionAbs with UserTypeSeq[BindCompanionAbs] {
          lazy val selfType = element[BindCompanionAbs]
        };
        new $anon()
      };
      def mkBind[F[_], S, B](a: Rep[Knd[F, S]], f: Rep[_root_.scala.Function1[S, Knd[F, B]]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]): Rep[Bind[F, S, B]] = new SeqBind[F, S, B](a, f);
      def unmkBind[F[_], S, B](p: Rep[Knd[F, B]]) = p match {
        case (p @ ((_): Bind[F, S, B] @unchecked)) => Some(scala.Tuple2(p.a, p.f))
        case _ => None
      }
    };
    trait KndsExp extends KndsDsl with ScalanExp { self: KndsDslExp =>
      lazy val Knd: Rep[KndCompanionAbs] = {
        final class $anon extends KndCompanionAbs with UserTypeDef[KndCompanionAbs] {
          lazy val selfType = element[KndCompanionAbs];
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
      def unmkReturn[F[_], A](p: Rep[Knd[F, A]]) = p.elem.asInstanceOf[(Elem[_$2] forSome { 
        type _$2
      })] match {
        case ((_): ReturnElem[F, A] @unchecked) => Some(p.asRep[Return[F, A]].a)
        case _ => None
      };
      case class ExpBind[F[_], S, B](override val a: Rep[Knd[F, S]], override val f: Rep[_root_.scala.Function1[S, Knd[F, B]]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]) extends Bind[F, S, B](a, f) with UserTypeDef[Bind[F, S, B]] {
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
      def mkBind[F[_], S, B](a: Rep[Knd[F, S]], f: Rep[_root_.scala.Function1[S, Knd[F, B]]])(implicit cF: Cont[F], eS: Elem[S], eA: Elem[B]): Rep[Bind[F, S, B]] = new ExpBind[F, S, B](a, f);
      def unmkBind[F[_], S, B](p: Rep[Knd[F, B]]) = p.elem.asInstanceOf[(Elem[_$3] forSome { 
        type _$3
      })] match {
        case ((_): BindElem[F, S, B] @unchecked) => Some(scala.Tuple2(p.asRep[Bind[F, S, B]].a, p.asRep[Bind[F, S, B]].f))
        case _ => None
      };
      object KndMethods;
      object KndCompanionMethods
    };
    trait Knds extends Base { self: KndsDsl =>
      trait Knd[F[_], A] extends Reifiable[Knd[F, A]] {
        implicit def cF: Cont[F];
        implicit def eA: Elem[A];
        def flatMap[B](f: Rep[scala.Function1[A, Knd[F, B]]])(implicit eB: Elem[B]): Rep[Knd[F, B]] = Bind(self, f);
        def mapBy[B](f: Rep[scala.Function1[A, B]])(implicit eB: Elem[B]): Rep[Knd[F, B]] = flatMap(fun(((a) => Return(f(a)))))
      };
      abstract class Return[F[_], A](val a: Rep[A])(implicit val cF: Cont[F], val eA: Elem[A]) extends Knd[F, A] {
        override def flatMap[B](f: Rep[scala.Function1[A, Knd[F, B]]])(implicit eB: Elem[B]): Rep[Knd[F, B]] = f(a)
      };
      abstract class Bind[F[_], S, B](val a: Rep[Knd[F, S]], val f: Rep[scala.Function1[S, Knd[F, B]]])(implicit val cF: Cont[F], val eS: Elem[S], val eA: Elem[B]) extends Knd[F, B] {
        override def flatMap[R](f1: Rep[scala.Function1[B, Knd[F, R]]])(implicit eR: Elem[R]): Rep[Knd[F, R]] = a.flatMap(fun(((s: Rep[S]) => f(s).flatMap(f1))))
      };
      trait KndCompanion;
      trait ReturnCompanion;
      trait BindCompanion
    };
    trait KndsDsl extends KndsAbs;
    trait KndsDslSeq extends KndsSeq;
    trait KndsDslExp extends KndsExp;
    val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZrSr+/co886dAgALTAAEYm9keXQAIUxzY2FsYS9jb2xsZWN0aW9uL2ltbXV0YWJsZS9MaXN0O0wAEGNvbmNyZXRlU0NsYXNzZXNxAH4AAUwACGVudGl0aWVzcQB+AAFMAAllbnRpdHlPcHN0ACFMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcmFpdERlZjtMABBlbnRpdHlSZXBTeW5vbnltdAAOTHNjYWxhL09wdGlvbjtMAAdpbXBvcnRzcQB+AAFMAAdtZXRob2RzcQB+AAFMAARuYW1ldAASTGphdmEvbGFuZy9TdHJpbmc7TAALcGFja2FnZU5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAApzZXFEc2xJbXBscQB+AAN4cHNyADJzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0JFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABAwAAeHBzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzRGVmB9B9aPGocDMCAApaAAppc0Fic3RyYWN0TAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYXJnc3QAIkxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU0NsYXNzQXJncztMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAMTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0Q2FsbFDrGS0l7EVYAgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQAA0tuZHNxAH4ABnNxAH4AD3QAAUZxAH4AB3NxAH4AD3QAAUFxAH4AB3EAfgAJeHEAfgAJeHEAfgAHc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NBcmdzJ+vuZMUwAFwCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzQXJnBXPY1gvUKT8CAAhaAAdpbXBGbGFnWgAMaXNFbGVtT3JDb250WgAIb3ZlckZsYWdaAAd2YWxGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXQAIExzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RwZUV4cHI7eHAAAAABcQB+AAdzcgALc2NhbGEuTm9uZSRGUCT2U8qUrAIAAHhyAAxzY2FsYS5PcHRpb27+aTf92w5mdAIAAHhwdAABYXNxAH4AD3QAAUFxAH4AB3EAfgAJeHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZERlZt1ZcXG3MCsOAgAKWgAMaXNFbGVtT3JDb250WgAKaXNJbXBsaWNpdFoACmlzT3ZlcnJpZGVMAAthbm5vdGF0aW9uc3EAfgABTAALYXJnU2VjdGlvbnNxAH4AAUwABGJvZHlxAH4AA0wABG5hbWVxAH4ABEwACm92ZXJsb2FkSWRxAH4AA0wAB3RwZUFyZ3NxAH4AAUwABnRwZVJlc3EAfgADeHAAAAFxAH4AB3NxAH4ABnNyACFzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZ3MpwieWXnW6iwIAAUwABGFyZ3NxAH4AAXhwc3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kQXJnA9z/y5NycwMCAAdaAAdpbXBGbGFnWgAMaXNFbGVtT3JDb250WgAIb3ZlckZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBlcQB+ABt4cAAAAHEAfgAHcQB+AB90AAFmc3IAHnNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlRnVuY9RJmRMPHV/hAgACTAAGZG9tYWlucQB+ABtMAAVyYW5nZXEAfgAbeHBzcQB+AA90AAFBcQB+AAdzcQB+AA90AANLbmRzcQB+AAZzcQB+AA90AAFGcQB+AAdzcQB+AA90AAFCcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhzcgAKc2NhbGEuU29t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U2NhbGFuQXN0JFNUcmFpdERlZgG+NJI0FLGyAgAJWgAIYml0bWFwJDBMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAMTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgAHcQB+AAdzcQB+AAZzcQB+ACQAAABxAH4AB3NxAH4ABnNxAH4AJ3NxAH4ABnNxAH4AKgAAAHEAfgAHcQB+AB9xAH4ALHNxAH4ALXNxAH4AD3QAAUFxAH4AB3NxAH4AD3QAA0tuZHNxAH4ABnNxAH4AD3QAAUZxAH4AB3NxAH4AD3QAAUJxAH4AB3EAfgAJeHEAfgAJeHEAfgAJeHNxAH4AOHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NvbnRy6FFJ1RpPNbUCAAJMAARhcmdzcQB+AAFMAARuYW1lcQB+AAR4cHNxAH4ABnNxAH4AQHQABHNlbGZzcQB+AEBxAH4ALHEAfgAJeHQABEJpbmRxAH4ARHEAfgAfc3EAfgAGc3EAfgBGcQB+AB9xAH4AB3QAAUJxAH4AB3EAfgAJeHNxAH4AOHNxAH4AD3QAA0tuZHNxAH4ABnNxAH4AD3QAAUZxAH4AB3NxAH4AD3QAAUJxAH4AB3EAfgAJeHNxAH4AJAAAAHEAfgAHc3EAfgAGc3EAfgAnc3EAfgAGc3EAfgAqAAAAcQB+AAdxAH4AH3EAfgAsc3EAfgAtc3EAfgAPdAABQXEAfgAHc3EAfgAPdAABQnEAfgAHcQB+AAl4cQB+AAl4c3EAfgA4c3EAfgA7c3EAfgAGc3EAfgAGc3EAfgCPc3EAfgAGc3EAfgCSAABzcQB+AJR0AAFhcQB+AB9xAH4ACXhzcQB+ANRzcQB+AAZzcQB+ADtzcQB+AAZzcQB+AAZzcQB+AEB0AAFhcQB+AAl4cQB+AAl4c3EAfgBAcQB+ACxxAH4AB3EAfgAJeHQABlJldHVybnEAfgAJeHEAfgAJeHNxAH4AQHEAfgBEcQB+AAd0AAVtYXBCeXEAfgAfc3EAfgAGc3EAfgBGcQB+AB9xAH4AB3QAAUJxAH4AB3EAfgAJeHNxAH4AOHNxAH4AD3QAA0tuZHNxAH4ABnNxAH4AD3QAAUZxAH4AB3NxAH4AD3QAAUJxAH4AB3EAfgAJeHEAfgAJeHEAfgAfcHQAA0tuZHEAfgAfc3EAfgAGc3EAfgBGcQB+AB9xAH4AB3QAAUZzcQB+AAZzcQB+AEZxAH4AH3EAfgAHcQB+AFhxAH4AB3EAfgAJeHNxAH4ARnEAfgAfcQB+AAd0AAFBcQB+AAdxAH4ACXhxAH4ACXhxAH4AwnEAfgAfcQB+AAdxAH4AB3QABEtuZHN0AARrbmRzcQB+AB9xAH4AHw=="
  }
}