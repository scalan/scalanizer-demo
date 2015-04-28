package knds {
  object StagedEvaluation {
    import scalan._;
    import scala.reflect.runtime.universe._;
    import scala.reflect._;
    import scalan.common.Default;
    trait KndsAbs extends Knds with ScalanDsl { self: KndsDsl =>
      implicit def proxyKnd[F[_], A](p: Rep[Knd[F, A]]): Knd[F, A] = proxyOps[Knd[F, A]](p)(classTag[Knd[F, A]]);
      class KndElem[F[_], A, To <: Knd[F, A]] extends EntityElem[To] {
        override def isEntityType = true;
        override def tag = weakTypeTag[Knd[F, A]].asInstanceOf[WeakTypeTag[To]];
        override def convert(x: Rep[(Reifiable[_$1] forSome { 
          type _$1
        })]) = convertKnd(x.asRep[Knd[F, A]]);
        def convertKnd(x: Rep[Knd[F, A]]): Rep[To] = x.asRep[To];
        override def getDefaultRep: Rep[To] = ???
      };
      implicit def kndElement[F[_], A]: Elem[Knd[F, A]] = {
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
      class ReturnElem[F[_], A](val iso: Iso[ReturnData[F, A], Return[F, A]]) extends KndElem[F, A, Return[F, A]] with ConcreteElem[ReturnData[F, A], Return[F, A]] {
        override def convertKnd(x: Rep[Knd[F, A]]) = !!!("Cannot convert from Knd to Return: missing fields List(a)");
        override def getDefaultRep = super[ConcreteElem].getDefaultRep;
        override lazy val tag = super[ConcreteElem].tag
      };
      type ReturnData[F[_], A] = A;
      class ReturnIso[F[_], A] extends Iso[ReturnData[F, A], Return[F, A]] {
        override def from(p: Rep[Return[F, A]]) = p.a;
        override def to(p: Rep[A]) = {
          val a = p;
          Return(a)
        };
        lazy val tag = weakTypeTag[Return[F, A]];
        lazy val defaultRepTo = Default.defaultVal[Rep[Return[F, A]]](Return(element[A].defaultRepValue));
        lazy val eTo = new ReturnElem[F, A](this)
      };
      abstract class ReturnCompanionAbs extends CompanionBase[ReturnCompanionAbs] with ReturnCompanion {
        override def toString = "Return";
        def apply[F[_], A](a: Rep[A]): Rep[Return[F, A]] = mkReturn(a)
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
      implicit class ExtendedReturn[F[_], A](p: Rep[Return[F, A]]) {
        def toData: Rep[ReturnData[F, A]] = isoReturn.from(p)
      };
      implicit def isoReturn[F[_], A]: Iso[ReturnData[F, A], Return[F, A]] = new ReturnIso[F, A]();
      def mkReturn[F[_], A](a: Rep[A]): Rep[Return[F, A]];
      def unmkReturn[F[_], A](p: Rep[Knd[F, A]]): Option[Rep[A]];
      class BindElem[F[_], S, B](val iso: Iso[BindData[F, S, B], Bind[F, S, B]]) extends KndElem[F, B, Bind[F, S, B]] with ConcreteElem[BindData[F, S, B], Bind[F, S, B]] {
        override def convertKnd(x: Rep[Knd[F, B]]) = !!!("Cannot convert from Knd to Bind: missing fields List(a, f)");
        override def getDefaultRep = super[ConcreteElem].getDefaultRep;
        override lazy val tag = super[ConcreteElem].tag
      };
      type BindData[F[_], S, B] = scala.Tuple2[Knd[F, S], _root_.scala.Function1[S, Knd[F, B]]];
      class BindIso[F[_], S, B] extends Iso[BindData[F, S, B], Bind[F, S, B]] {
        override def from(p: Rep[Bind[F, S, B]]) = scala.Tuple2(p.a, p.f);
        override def to(p: Rep[scala.Tuple2[Knd[F, S], _root_.scala.Function1[S, Knd[F, B]]]]) = {
          val x$1 = (p: @scala.unchecked) match {
            case Pair((a @ _), (f @ _)) => scala.Tuple2(a, f)
          };
          val a = x$1._1;
          val f = x$1._2;
          Bind(a, f)
        };
        lazy val tag = weakTypeTag[Bind[F, S, B]];
        lazy val defaultRepTo = Default.defaultVal[Rep[Bind[F, S, B]]](Bind(element[Knd[F, S]].defaultRepValue, fun(((x: Rep[S]) => element[Knd[F, B]].defaultRepValue))));
        lazy val eTo = new BindElem[F, S, B](this)
      };
      abstract class BindCompanionAbs extends CompanionBase[BindCompanionAbs] with BindCompanion {
        override def toString = "Bind";
        def apply[F[_], S, B](p: Rep[BindData[F, S, B]]): Rep[Bind[F, S, B]] = isoBind.to(p);
        def apply[F[_], S, B](a: Rep[Knd[F, S]], f: Rep[_root_.scala.Function1[S, Knd[F, B]]]): Rep[Bind[F, S, B]] = mkBind(a, f)
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
      implicit class ExtendedBind[F[_], S, B](p: Rep[Bind[F, S, B]]) {
        def toData: Rep[BindData[F, S, B]] = isoBind.from(p)
      };
      implicit def isoBind[F[_], S, B]: Iso[BindData[F, S, B], Bind[F, S, B]] = new BindIso[F, S, B]();
      def mkBind[F[_], S, B](a: Rep[Knd[F, S]], f: Rep[_root_.scala.Function1[S, Knd[F, B]]]): Rep[Bind[F, S, B]];
      def unmkBind[F[_], S, B](p: Rep[Knd[F, B]]): Option[scala.Tuple2[Rep[Knd[F, S]], Rep[_root_.scala.Function1[S, Knd[F, B]]]]]
    };
    trait KndsSeq extends KndsDsl with ScalanSeq { self: KndsDslSeq =>
      lazy val Knd: Rep[KndCompanionAbs] = {
        final class $anon extends KndCompanionAbs with UserTypeSeq[KndCompanionAbs] {
          lazy val selfType = element[KndCompanionAbs]
        };
        new $anon()
      };
      case class SeqReturn[F[_], A](override val a: Rep[A]) extends Return[F, A](a) with UserTypeSeq[Return[F, A]] {
        lazy val selfType = element[Return[F, A]]
      };
      lazy val Return = {
        final class $anon extends ReturnCompanionAbs with UserTypeSeq[ReturnCompanionAbs] {
          lazy val selfType = element[ReturnCompanionAbs]
        };
        new $anon()
      };
      def mkReturn[F[_], A](a: Rep[A]): Rep[Return[F, A]] = new SeqReturn[F, A](a);
      def unmkReturn[F[_], A](p: Rep[Knd[F, A]]) = p match {
        case (p @ ((_): Return[F, A] @unchecked)) => Some(p.a)
        case _ => None
      };
      case class SeqBind[F[_], S, B](override val a: Rep[Knd[F, S]], override val f: Rep[_root_.scala.Function1[S, Knd[F, B]]]) extends Bind[F, S, B](a, f) with UserTypeSeq[Bind[F, S, B]] {
        lazy val selfType = element[Bind[F, S, B]]
      };
      lazy val Bind = {
        final class $anon extends BindCompanionAbs with UserTypeSeq[BindCompanionAbs] {
          lazy val selfType = element[BindCompanionAbs]
        };
        new $anon()
      };
      def mkBind[F[_], S, B](a: Rep[Knd[F, S]], f: Rep[_root_.scala.Function1[S, Knd[F, B]]]): Rep[Bind[F, S, B]] = new SeqBind[F, S, B](a, f);
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
      case class ExpReturn[F[_], A](override val a: Rep[A]) extends Return[F, A](a) with UserTypeDef[Return[F, A]] {
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
      def mkReturn[F[_], A](a: Rep[A]): Rep[Return[F, A]] = new ExpReturn[F, A](a);
      def unmkReturn[F[_], A](p: Rep[Knd[F, A]]) = p.elem.asInstanceOf[(Elem[_$2] forSome { 
        type _$2
      })] match {
        case ((_): ReturnElem[F, A] @unchecked) => Some(p.asRep[Return[F, A]].a)
        case _ => None
      };
      case class ExpBind[F[_], S, B](override val a: Rep[Knd[F, S]], override val f: Rep[_root_.scala.Function1[S, Knd[F, B]]]) extends Bind[F, S, B](a, f) with UserTypeDef[Bind[F, S, B]] {
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
      def mkBind[F[_], S, B](a: Rep[Knd[F, S]], f: Rep[_root_.scala.Function1[S, Knd[F, B]]]): Rep[Bind[F, S, B]] = new ExpBind[F, S, B](a, f);
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
        implicit def elementOfA: Elem[A];
        implicit def containerOfF: Cont[F];
        def flatMap[B](f: Rep[scala.Function1[A, Knd[F, B]]])(implicit elementOfB: Elem[B]): Rep[Knd[F, B]] = Bind(self, f);
        def mapBy[B](f: Rep[scala.Function1[A, B]])(implicit elementOfB: Elem[B]): Rep[Knd[F, B]] = flatMap(((a) => Return(f(a))))
      };
      abstract class Return[F[_], A](val a: Rep[A])(implicit val elementOfA: Elem[A], val containerOfF: Cont[F]) extends Knd[F, A] {
        override def flatMap[B](f: Rep[scala.Function1[A, Knd[F, B]]])(implicit elementOfB: Elem[B]): Rep[Knd[F, B]] = f(a)
      };
      abstract class Bind[F[_], S, B](val a: Rep[Knd[F, S]], val f: Rep[scala.Function1[S, Knd[F, B]]])(implicit val elementOfS: Elem[S], val elementOfB: Elem[B], val containerOfF: Cont[F]) extends Knd[F, B] {
        override def flatMap[R](f1: Rep[scala.Function1[B, Knd[F, R]]])(implicit elementOfR: Elem[R]): Rep[Knd[F, R]] = a.flatMap(((s: Rep[S]) => f(s).flatMap(f1)))
      };
      trait KndCompanion;
      trait ReturnCompanion;
      trait BindCompanion
    };
    trait KndsDsl extends KndsAbs;
    trait KndsDslSeq extends KndsSeq;
    trait KndsDslExp extends KndsExp;
    val serializedMetaAst = "rO0ABXNyADBzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNFbnRpdHlNb2R1bGVEZWb/pfpNh3F99wIAC0wABGJvZHl0ACFMc2NhbGEvY29sbGVjdGlvbi9pbW11dGFibGUvTGlzdDtMABBjb25jcmV0ZVNDbGFzc2VzcQB+AAFMAAhlbnRpdGllc3EAfgABTAAJZW50aXR5T3BzdAArTHNjYWxhbi9wbHVnaW4vU2NhbGFuQXN0L3BhY2thZ2UkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4c3EAfgAGc3IAKXNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU0NsYXNzRGVmd2hOWzQYcNgCAApaAAppc0Fic3RyYWN0TAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYXJnc3QALExzY2FsYW4vcGx1Z2luL1NjYWxhbkFzdC9wYWNrYWdlJFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAqc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTVHJhaXRDYWxs5qegGwOeq3cCAAJMAARuYW1lcQB+AARMAAl0cGVTRXhwcnNxAH4AAXhwdAADS25kc3EAfgAGc3EAfgAPdAABRnEAfgAHc3EAfgAPdAABQXEAfgAHcQB+AAl4cQB+AAl4cQB+AAdzcgAqc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTQ2xhc3NBcmdzjv7tEOXl3nQCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyAClzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNDbGFzc0FyZw3j44Dh1EqgAgAHWgAHaW1wRmxhZ1oACG92ZXJGbGFnWgAHdmFsRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGV0ACpMc2NhbGFuL3BsdWdpbi9TY2FsYW5Bc3QvcGFja2FnZSRTVHBlRXhwcjt4cAAAAXEAfgAHc3IAC3NjYWxhLk5vbmUkRlAk9lPKlKwCAAB4cgAMc2NhbGEuT3B0aW9u/mk3/dsOZnQCAAB4cHQAAWFzcQB+AA90AAFBcQB+AAdxAH4ACXhzcQB+AAZzcgAqc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTTWV0aG9kRGVm64Uf+HJKB9MCAApaAAxpc0VsZW1PckNvbnRaAAppc0ltcGxpY2l0WgAKaXNPdmVycmlkZUwAC2Fubm90YXRpb25zcQB+AAFMAAthcmdTZWN0aW9uc3EAfgABTAAEYm9keXEAfgADTAAEbmFtZXEAfgAETAAKb3ZlcmxvYWRJZHEAfgADTAAHdHBlQXJnc3EAfgABTAAGdHBlUmVzcQB+AAN4cAAAAXEAfgAHc3EAfgAGc3IAK3NjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU01ldGhvZEFyZ3OkKTx0msr2QAIAAUwABGFyZ3NxAH4AAXhwc3EAfgAGc3IAKnNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU01ldGhvZEFyZ2XoWw9Vr6aZAgAGWgAHaW1wRmxhZ1oACG92ZXJGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXEAfgAbeHAAAHEAfgAHcQB+AB90AAFmc3IAKHNjYWxhbi5wbHVnaW4uU2NhbGFuQXN0LnBhY2thZ2UkU1RwZUZ1bmOXorUtalwVAQIAAkwABmRvbWFpbnEAfgAbTAAFcmFuZ2VxAH4AG3hwc3EAfgAP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BnNxAH4AP3QAAXNxAH4ACXhzcQB+AD9xAH4ALHEAfgAHcQB+AENxAH4AB3EAfgAJeHNxAH4AoHNxAH4AP3QAAWFxAH4AQ3EAfgAHcQB+AENxAH4AH3NxAH4ABnNxAH4ARXEAfgAfcQB+AAd0AAFScQB+AAdxAH4ACXhzcQB+ADhzcQB+AA90AANLbmRzcQB+AAZzcQB+AA90AAFGcQB+AAdzcQB+AA90AAFScQB+AAdxAH4ACXhxAH4ACXhzcQB+ADhzcQB+AFEAcQB+AAdxAH4AB3EAfgAHcQB+AB9wdAANQmluZENvbXBhbmlvbnEAfgAfcQB+AAdzcQB+ABdxAH4AB3QABEJpbmRxAH4AH3NxAH4ABnNxAH4ARXEAfgAfcQB+AAd0AAFGc3EAfgAGc3EAfgBFcQB+AB9xAH4AB3EAfgBbcQB+AAdxAH4ACXhzcQB+AEVxAH4AH3EAfgAHdAABU3EAfgAHc3EAfgBFcQB+AB9xAH4AB3QAAUJxAH4AB3EAfgAJeHEAfgAJeHNxAH4ABnNxAH4AUQFzcQB+AAZzcQB+AA90AAlSZWlmaWFibGVzcQB+AAZzcQB+AA90AANLbmRzcQB+AAZzcQB+AA90AAFGcQB+AAdzcQB+AA90AAFBcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4AB3NxAH4ABnNxAH4AJAAAAHEAfgAHc3EAfgAGc3EAfgAnc3EAfgAGc3EAfgAqAABxAH4AB3EAfgAfcQB+ACxzcQB+AC1zcQB+AA90AAFBcQB+AAdzcQB+AA90AANLbmRzcQB+AAZzcQB+AA90AAFGcQB+AAdzcQB+AA90AAFCcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhzcQB+ADhzcgAmc2NhbGFuLnBsdWdpbi5TY2FsYW5Bc3QucGFja2FnZSRTQ29udHJvOfmpIaocEgIAAkwABGFyZ3NxAH4AAUwABG5hbWVxAH4ABHhwc3EAfgAGc3EAfgA/dAAEc2VsZnNxAH4AP3EAfgAscQB+AAl4dAAEQmluZHEAfgBDcQB+AB9zcQB+AAZzcQB+AEVxAH4AH3EAfgAHdAABQnEAfgAHcQB+AAl4c3EAfgA4c3EAfgAPdAADS25kc3EAfgAGc3EAfgAPdAABRnEAfgAHc3EAfgAPdAABQnEAfgAHcQB+AAl4c3EAfgAkAAAAcQB+AAdzcQB+AAZzcQB+ACdzcQB+AAZzcQB+ACoAAHEAfgAHcQB+AB9xAH4ALHNxAH4ALXNxAH4AD3QAAUFxAH4AB3NxAH4AD3QAAUJxAH4AB3EAfgAJeHEAfgAJeHNxAH4AOHNxAH4AO3NxAH4ABnNxAH4AkXNxAH4ABnNxAH4AlAAAc3EAfgCWdAABYXEAfgAfcQB+AAl4c3EAfgDhc3EAfgAGc3EAfgA7c3EAfgAGc3EAfgA/dAABYXEAfgAJeHNxAH4AP3EAfgAscQB+AAdxAH4ACXh0AAZSZXR1cm5xAH4ACXhzcQB+AD9xAH4AQ3EAfgAHdAAFbWFwQnlxAH4AH3NxAH4ABnNxAH4ARXEAfgAfcQB+AAd0AAFCcQB+AAdxAH4ACXhzcQB+ADhzcQB+AA90AANLbmRzcQB+AAZzcQB+AA90AAFGcQB+AAdzcQB+AA90AAFCcQB+AAdxAH4ACXhxAH4ACXhzcQB+ADhzcQB+AFEAcQB+AAdxAH4AB3EAfgAHcQB+AB9wdAAMS25kQ29tcGFuaW9ucQB+AB9xAH4AB3NxAH4AF3EAfgAHcQB+AMpxAH4AH3NxAH4ABnNxAH4ARXEAfgAfcQB+AAdxAH4AzXNxAH4ABnNxAH4ARXEAfgAfcQB+AAdxAH4AW3EAfgAHcQB+AAl4c3EAfgBFcQB+AB9xAH4AB3EAfgDPcQB+AAdxAH4ACXhxAH4ACXhxAH4AxHNxAH4AOHNyACdzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNUcGVEZWalpHc0ytghWgIAA0wABG5hbWVxAH4ABEwAA3Joc3EAfgAbTAAHdHBlQXJnc3EAfgABeHB0AAZSZXBLbmRzcQB+AA90AANSZXBzcQB+AAZzcQB+AA9xAH4AynNxAH4ABnNxAH4AD3EAfgDNc3EAfgAGc3EAfgAPcQB+AFtxAH4AB3EAfgAJeHNxAH4AD3EAfgDPcQB+AAdxAH4ACXhxAH4ACXhxAH4BHnNxAH4ABnNyACtzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNJbXBvcnRTdGF0uWTK2qBHsXcCAAFMAARuYW1lcQB+AAR4cHQACHNjYWxhbi5fcQB+AAl4cQB+AAd0AARLbmRzdAAEa25kc3NxAH4AOHNyACxzY2FsYW4ucGx1Z2luLlNjYWxhbkFzdC5wYWNrYWdlJFNTZWxmVHlwZURlZrCTQo8B5HL/AgACTAAKY29tcG9uZW50c3EAfgABTAAEbmFtZXEAfgAEeHBzcQB+AAZzcQB+AA90AAdLbmRzRHNscQB+AAdxAH4ACXhxAH4A5XEAfgAf"
  }
}