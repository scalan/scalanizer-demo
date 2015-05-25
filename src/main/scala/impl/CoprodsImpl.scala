package coprods {
  object StagedEvaluation {
    import scalan._;
    import scala.reflect.runtime.universe._;
    import scala.reflect._;
    import scalan.common.Default;
    trait CoprodsAbs extends Coprods with ScalanDsl { self: CoprodsDsl =>
      implicit def proxyCoprod[F[_], G[_], A](p: Rep[Coprod[F, G, A]]): Coprod[F, G, A] = proxyOps[Coprod[F, G, A]](p)(classTag[Coprod[F, G, A]]);
      class CoprodElem[F[_], G[_], A, To <: Coprod[F, G, A]](implicit val cF: Cont[F], val cG: Cont[G], val eA: Elem[A]) extends EntityElem[To] {
        override def isEntityType = true;
        override lazy val tag = {
          implicit val tagA = eA.tag;
          weakTypeTag[Coprod[F, G, A]].asInstanceOf[WeakTypeTag[To]]
        };
        override def convert(x: Rep[(Reifiable[_$1] forSome { 
          type _$1
        })]) = {
          val conv = fun(((x: Rep[Coprod[F, G, A]]) => convertCoprod(x)));
          tryConvert(element[Coprod[F, G, A]], this, x, conv)
        };
        def convertCoprod(x: Rep[Coprod[F, G, A]]): Rep[To] = {
          assert(x.selfType1.asInstanceOf[(Element[_$2] forSome { 
            type _$2
          })] match {
            case ((_): CoprodElem[(_), (_), (_), (_)]) => true
            case _ => false
          });
          x.asRep[To]
        };
        override def getDefaultRep: Rep[To] = ???
      };
      implicit def coprodElement[F[_], G[_], A](implicit cF: Cont[F], cG: Cont[G], eA: Elem[A]): Elem[Coprod[F, G, A]] = new CoprodElem[F, G, A, Coprod[F, G, A]]();
      implicit case object CoprodCompanionElem extends CompanionElem[CoprodCompanionAbs] with scala.Product with scala.Serializable {
        lazy val tag = weakTypeTag[CoprodCompanionAbs];
        protected def getDefaultRep = Coprod
      };
      abstract class CoprodCompanionAbs extends CompanionBase[CoprodCompanionAbs] with CoprodCompanion {
        override def toString = "Coprod"
      };
      def Coprod: Rep[CoprodCompanionAbs];
      implicit def proxyCoprodCompanion(p: Rep[CoprodCompanion]): CoprodCompanion = proxyOps[CoprodCompanion](p);
      class CoproductImplElem[F[_], G[_], A](val iso: Iso[CoproductImplData[F, G, A], CoproductImpl[F, G, A]])(implicit cF: Cont[F], cG: Cont[G], eA: Elem[A]) extends CoprodElem[F, G, A, CoproductImpl[F, G, A]] with ConcreteElem[CoproductImplData[F, G, A], CoproductImpl[F, G, A]] {
        override def convertCoprod(x: Rep[Coprod[F, G, A]]) = CoproductImpl(x.run);
        override def getDefaultRep = super[ConcreteElem].getDefaultRep;
        override lazy val tag = {
          implicit val tagA = eA.tag;
          weakTypeTag[CoproductImpl[F, G, A]]
        }
      };
      type CoproductImplData[F[_], G[_], A] = Either[F[A], G[A]];
      class CoproductImplIso[F[_], G[_], A](implicit cF: Cont[F], cG: Cont[G], eA: Elem[A]) extends Iso[CoproductImplData[F, G, A], CoproductImpl[F, G, A]] {
        override def from(p: Rep[CoproductImpl[F, G, A]]) = p.run;
        override def to(p: Rep[Either[F[A], G[A]]]) = {
          val run = p;
          CoproductImpl(run)
        };
        lazy val defaultRepTo = Default.defaultVal[Rep[CoproductImpl[F, G, A]]](CoproductImpl(element[Either[F[A], G[A]]].defaultRepValue));
        lazy val eTo = new CoproductImplElem[F, G, A](this)
      };
      abstract class CoproductImplCompanionAbs extends CompanionBase[CoproductImplCompanionAbs] with CoproductImplCompanion {
        override def toString = "CoproductImpl";
        def apply[F[_], G[_], A](run: Rep[Either[F[A], G[A]]])(implicit cF: Cont[F], cG: Cont[G], eA: Elem[A]): Rep[CoproductImpl[F, G, A]] = mkCoproductImpl(run)
      };
      object CoproductImplMatcher {
        def unapply[F[_], G[_], A](p: Rep[Coprod[F, G, A]]) = unmkCoproductImpl(p)
      };
      def CoproductImpl: Rep[CoproductImplCompanionAbs];
      implicit def proxyCoproductImplCompanion(p: Rep[CoproductImplCompanionAbs]): CoproductImplCompanionAbs = proxyOps[CoproductImplCompanionAbs](p);
      implicit case object CoproductImplCompanionElem extends CompanionElem[CoproductImplCompanionAbs] with scala.Product with scala.Serializable {
        lazy val tag = weakTypeTag[CoproductImplCompanionAbs];
        protected def getDefaultRep = CoproductImpl
      };
      implicit def proxyCoproductImpl[F[_], G[_], A](p: Rep[CoproductImpl[F, G, A]]): CoproductImpl[F, G, A] = proxyOps[CoproductImpl[F, G, A]](p);
      implicit class ExtendedCoproductImpl[F[_], G[_], A](p: Rep[CoproductImpl[F, G, A]])(implicit cF: Cont[F], cG: Cont[G], eA: Elem[A]) {
        def toData: Rep[CoproductImplData[F, G, A]] = isoCoproductImpl(cF, cG, eA).from(p)
      };
      implicit def isoCoproductImpl[F[_], G[_], A](implicit cF: Cont[F], cG: Cont[G], eA: Elem[A]): Iso[CoproductImplData[F, G, A], CoproductImpl[F, G, A]] = new CoproductImplIso[F, G, A]();
      def mkCoproductImpl[F[_], G[_], A](run: Rep[Either[F[A], G[A]]])(implicit cF: Cont[F], cG: Cont[G], eA: Elem[A]): Rep[CoproductImpl[F, G, A]];
      def unmkCoproductImpl[F[_], G[_], A](p: Rep[Coprod[F, G, A]]): Option[Rep[Either[F[A], G[A]]]]
    };
    trait CoprodsSeq extends CoprodsDsl with ScalanSeq { self: CoprodsDslSeq =>
      lazy val Coprod: Rep[CoprodCompanionAbs] = {
        final class $anon extends CoprodCompanionAbs with UserTypeSeq[CoprodCompanionAbs] {
          lazy val selfType = element[CoprodCompanionAbs]
        };
        new $anon()
      };
      case class SeqCoproductImpl[F[_], G[_], A](override val run: Rep[Either[F[A], G[A]]])(implicit cF: Cont[F], cG: Cont[G], eA: Elem[A]) extends CoproductImpl[F, G, A](run) with UserTypeSeq[CoproductImpl[F, G, A]] {
        lazy val selfType = element[CoproductImpl[F, G, A]]
      };
      lazy val CoproductImpl = {
        final class $anon extends CoproductImplCompanionAbs with UserTypeSeq[CoproductImplCompanionAbs] {
          lazy val selfType = element[CoproductImplCompanionAbs]
        };
        new $anon()
      };
      def mkCoproductImpl[F[_], G[_], A](run: Rep[Either[F[A], G[A]]])(implicit cF: Cont[F], cG: Cont[G], eA: Elem[A]): Rep[CoproductImpl[F, G, A]] = new SeqCoproductImpl[F, G, A](run);
      def unmkCoproductImpl[F[_], G[_], A](p: Rep[Coprod[F, G, A]]) = p match {
        case (p @ ((_): CoproductImpl[F, G, A] @unchecked)) => Some(p.run)
        case _ => None
      }
    };
    trait CoprodsExp extends CoprodsDsl with ScalanExp { self: CoprodsDslExp =>
      lazy val Coprod: Rep[CoprodCompanionAbs] = {
        final class $anon extends CoprodCompanionAbs with UserTypeDef[CoprodCompanionAbs] {
          lazy val selfType = element[CoprodCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      case class ExpCoproductImpl[F[_], G[_], A](override val run: Rep[Either[F[A], G[A]]])(implicit cF: Cont[F], cG: Cont[G], eA: Elem[A]) extends CoproductImpl[F, G, A](run) with UserTypeDef[CoproductImpl[F, G, A]] {
        lazy val selfType = element[CoproductImpl[F, G, A]];
        override def mirror(t: Transformer) = ExpCoproductImpl[F, G, A](t(run))
      };
      lazy val CoproductImpl: Rep[CoproductImplCompanionAbs] = {
        final class $anon extends CoproductImplCompanionAbs with UserTypeDef[CoproductImplCompanionAbs] {
          lazy val selfType = element[CoproductImplCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      object CoproductImplMethods;
      object CoproductImplCompanionMethods;
      def mkCoproductImpl[F[_], G[_], A](run: Rep[Either[F[A], G[A]]])(implicit cF: Cont[F], cG: Cont[G], eA: Elem[A]): Rep[CoproductImpl[F, G, A]] = new ExpCoproductImpl[F, G, A](run);
      def unmkCoproductImpl[F[_], G[_], A](p: Rep[Coprod[F, G, A]]) = p.elem.asInstanceOf[(Elem[_$3] forSome { 
        type _$3
      })] match {
        case ((_): CoproductImplElem[F, G, A] @unchecked) => Some(p.asRep[CoproductImpl[F, G, A]].run)
        case _ => None
      };
      object CoprodMethods {
        object run {
          def unapply(d: (Def[_$4] forSome { 
            type _$4
          })): Option[(Rep[Coprod[F, G, A]] forSome { 
            type F[_];
            type G[_];
            type A
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if (receiver.elem.asInstanceOf[(Element[_$5] forSome { 
  type _$5
})] match {
  case ((_): CoprodElem[(_), (_), (_), (_)]) => true
  case _ => false
}).&&(method.getName.==("run")) => Some(receiver).asInstanceOf[Option[(Rep[Coprod[F, G, A]] forSome { 
              type F[_];
              type G[_];
              type A
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$6] forSome { 
            type _$6
          })): Option[(Rep[Coprod[F, G, A]] forSome { 
            type F[_];
            type G[_];
            type A
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object CoprodCompanionMethods
    };
    trait Coprods extends Base { self: CoprodsDsl =>
      trait Coprod[F[_], G[_], A] extends Reifiable[Coprod[F, G, A]] {
        implicit def cF: Cont[F];
        implicit def cG: Cont[G];
        implicit def eA: Elem[A];
        def run: Rep[Either[F[A], G[A]]]
      };
      abstract class CoproductImpl[F[_], G[_], A](val run: Rep[Either[F[A], G[A]]])(implicit val cF: Cont[F], val cG: Cont[G], val eA: Elem[A]) extends Coprod[F, G, A];
      trait CoprodCompanion;
      trait CoproductImplCompanion
    };
    trait CoprodsDsl extends CoprodsAbs;
    trait CoprodsDslSeq extends CoprodsSeq;
    trait CoprodsDslExp extends CoprodsExp;
    val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZrSr+/co886dAgALTAAEYm9keXQAIUxzY2FsYS9jb2xsZWN0aW9uL2ltbXV0YWJsZS9MaXN0O0wAEGNvbmNyZXRlU0NsYXNzZXNxAH4AAUwACGVudGl0aWVzcQB+AAFMAAllbnRpdHlPcHN0ACFMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcmFpdERlZjtMABBlbnRpdHlSZXBTeW5vbnltdAAOTHNjYWxhL09wdGlvbjtMAAdpbXBvcnRzcQB+AAFMAAdtZXRob2RzcQB+AAFMAARuYW1ldAASTGphdmEvbGFuZy9TdHJpbmc7TAALcGFja2FnZU5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAApzZXFEc2xJbXBscQB+AAN4cHNyADJzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0JFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABAwAAeHBzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzRGVmB9B9aPGocDMCAApaAAppc0Fic3RyYWN0TAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYXJnc3QAIkxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU0NsYXNzQXJncztMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAMTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAXNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0Q2FsbFDrGS0l7EVYAgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQABkNvcHJvZHNxAH4ABnNxAH4AD3QAAUZxAH4AB3NxAH4AD3QAAUdxAH4AB3NxAH4AD3QAAUFxAH4AB3EAfgAJeHEAfgAJeHEAfgAHc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NBcmdzJ+vuZMUwAFwCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzQXJnBXPY1gvUKT8CAAhaAAdpbXBGbGFnWgAMaXNFbGVtT3JDb250WgAIb3ZlckZsYWdaAAd2YWxGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXQAIExzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RwZUV4cHI7eHAAAAABcQB+AAdzcgALc2NhbGEuTm9uZSRGUCT2U8qUrAIAAHhyAAxzY2FsYS5PcHRpb27+aTf92w5mdAIAAHhwdAADcnVuc3EAfgAPdAAGRWl0aGVyc3EAfgAGc3EAfgAPdAABRnNxAH4ABnNxAH4AD3QAAUFxAH4AB3EAfgAJeHNxAH4AD3QAAUdzcQB+AAZzcQB+AA90AAFBcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4AB3EAfgAhc3EAfgAZcQB+AAd0AA1Db3Byb2R1Y3RJbXBscQB+ACFzcQB+AAZzcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVBcmeSWyZIcvGbiQIABEwABWJvdW5kcQB+AANMAAxjb250ZXh0Qm91bmRxAH4AAUwABG5hbWVxAH4ABEwAB3RwYXJhbXNxAH4AAXhwcQB+ACFxAH4AB3QAAUZzcQB+AAZzcQB+ADNxAH4AIXEAfgAHdAABX3EAfgAHcQB+AAl4c3EAfgAzcQB+ACFxAH4AB3QAAUdzcQB+AAZzcQB+ADNxAH4AIXEAfgAHcQB+ADhxAH4AB3EAfgAJeHNxAH4AM3EAfgAhcQB+AAd0AAFBcQB+AAdxAH4ACXhxAH4ACXhzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdERlZgG+NJI0FLGyAgAJWgAIYml0bWFwJDBMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAMTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgAHcQB+AAdzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2REZWbdWXFxtzArDgIACloADGlzRWxlbU9yQ29udFoACmlzSW1wbGljaXRaAAppc092ZXJyaWRlTAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0cGVSZXNxAH4AA3hwAAAAcQB+AAdxAH4AB3EAfgAhdAADcnVucQB+ACFxAH4AB3NyAApzY2FsYS5Tb21lESLyaV6hi3QCAAFMAAF4dAASTGphdmEvbGFuZy9PYmplY3Q7eHEAfgAgc3EAfgAPdAAGRWl0aGVyc3EAfgAGc3EAfgAPdAABRnNxAH4ABnNxAH4AD3QAAUFxAH4AB3EAfgAJeHNxAH4AD3QAAUdzcQB+AAZzcQB+AA90AAFBcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4AIXB0AAZDb3Byb2RxAH4AIXNxAH4ABnNxAH4AM3EAfgAhcQB+AAd0AAFGc3EAfgAGc3EAfgAzcQB+ACFxAH4AB3EAfgA4cQB+AAdxAH4ACXhzcQB+ADNxAH4AIXEAfgAHdAABR3NxAH4ABnNxAH4AM3EAfgAhcQB+AAdxAH4AOHEAfgAHcQB+AAl4c3EAfgAzcQB+ACFxAH4AB3QAAUFxAH4AB3EAfgAJeHEAfgAJeHEAfgBBcQB+ACFxAH4AB3EAfgAHdAAHQ29wcm9kc3QAB2NvcHJvZHNxAH4AIXEAfgAh"
  }
}