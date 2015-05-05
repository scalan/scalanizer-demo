package mnds {
  object StagedEvaluation {
    import scalan._;
    import scala.reflect.runtime.universe._;
    import scala.reflect._;
    import scalan.common.Default;
    trait MndsAbs extends Mnds with ScalanDsl { self: MndsDsl =>
      implicit def proxyMonad[F[_]](p: Rep[Monad[F]]): Monad[F] = proxyOps[Monad[F]](p)(classTag[Monad[F]]);
      class MonadElem[F[_], To <: Monad[F]](implicit val containerOfF: Cont[F]) extends EntityElem[To] {
        override def isEntityType = true;
        override def tag = weakTypeTag[Monad[F]].asInstanceOf[WeakTypeTag[To]];
        override def convert(x: Rep[(Reifiable[_$1] forSome { 
          type _$1
        })]) = convertMonad(x.asRep[Monad[F]]);
        def convertMonad(x: Rep[Monad[F]]): Rep[To] = x.asRep[To];
        override def getDefaultRep: Rep[To] = ???
      };
      implicit def monadElement[F[_]](implicit containerOfF: Cont[F]): Elem[Monad[F]] = {
        final class $anon extends MonadElem[F, Monad[F]];
        new $anon()
      };
      trait MonadCompanionElem extends CompanionElem[MonadCompanionAbs];
      implicit lazy val MonadCompanionElem: MonadCompanionElem = {
        final class $anon extends MonadCompanionElem {
          lazy val tag = weakTypeTag[MonadCompanionAbs];
          protected def getDefaultRep = Monad
        };
        new $anon()
      };
      abstract class MonadCompanionAbs extends CompanionBase[MonadCompanionAbs] with MonadCompanion {
        override def toString = "Monad"
      };
      def Monad: Rep[MonadCompanionAbs];
      implicit def proxyMonadCompanion(p: Rep[MonadCompanion]): MonadCompanion = proxyOps[MonadCompanion](p)
    };
    trait MndsSeq extends MndsDsl with ScalanSeq { self: MndsDslSeq =>
      lazy val Monad: Rep[MonadCompanionAbs] = {
        final class $anon extends MonadCompanionAbs with UserTypeSeq[MonadCompanionAbs] {
          lazy val selfType = element[MonadCompanionAbs]
        };
        new $anon()
      }
    };
    trait MndsExp extends MndsDsl with ScalanExp { self: MndsDslExp =>
      lazy val Monad: Rep[MonadCompanionAbs] = {
        final class $anon extends MonadCompanionAbs with UserTypeDef[MonadCompanionAbs] {
          lazy val selfType = element[MonadCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      object MonadMethods {
        object unit {
          def unapply(d: (Def[_$2] forSome { 
            type _$2
          })): Option[(scala.Tuple3[Rep[Monad[F]], Rep[A], Rep[Elem[A]]] forSome { 
            type F[_];
            type A
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (elementOfA @ _), _*), _) if (receiver.elem match {
  case (ve @ ((_): ViewElem[(_), (_)])) => (ve match {
    case ((_): MonadElem[(_), (_)]) => true
    case _ => false
  })
  case _ => false
}).&&(method.getName.==("unit")) => Some(scala.Tuple3(receiver, a, elementOfA)).asInstanceOf[Option[(scala.Tuple3[Rep[Monad[F]], Rep[A], Rep[Elem[A]]] forSome { 
              type F[_];
              type A
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$3] forSome { 
            type _$3
          })): Option[(scala.Tuple3[Rep[Monad[F]], Rep[A], Rep[Elem[A]]] forSome { 
            type F[_];
            type A
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object MonadCompanionMethods
    };
    trait Mnds extends Base { self: MndsDsl =>
      trait Monad[F[_]] extends Reifiable[Monad[F]] {
        implicit def containerOfF: Cont[F];
        def unit[A](a: Rep[A])(implicit elementOfA: Elem[A]): Rep[F[A]]
      };
      trait MonadCompanion
    };
    trait MndsDsl extends MndsAbs;
    trait MndsDslSeq extends MndsSeq;
    trait MndsDslExp extends MndsExp;
    val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZrSr+/co886dAgALTAAEYm9keXQAIUxzY2FsYS9jb2xsZWN0aW9uL2ltbXV0YWJsZS9MaXN0O0wAEGNvbmNyZXRlU0NsYXNzZXNxAH4AAUwACGVudGl0aWVzcQB+AAFMAAllbnRpdHlPcHN0ACFMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcmFpdERlZjtMABBlbnRpdHlSZXBTeW5vbnltdAAOTHNjYWxhL09wdGlvbjtMAAdpbXBvcnRzcQB+AAFMAAdtZXRob2RzcQB+AAFMAARuYW1ldAASTGphdmEvbGFuZy9TdHJpbmc7TAALcGFja2FnZU5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAApzZXFEc2xJbXBscQB+AAN4cHNyADJzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0JFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABAwAAeHBzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHEAfgAHc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHJhaXREZWYBvjSSNBSxsgIACVoACGJpdG1hcCQwTAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgAHcQB+AAdzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2REZWbdWXFxtzArDgIACloADGlzRWxlbU9yQ29udFoACmlzSW1wbGljaXRaAAppc092ZXJyaWRlTAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0cGVSZXNxAH4AA3hwAAAAcQB+AAdzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2RBcmdzKcInll51uosCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZwPc/8uTcnMDAgAHWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXQAIExzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RwZUV4cHI7eHAAAABxAH4AB3NyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHIADHNjYWxhLk9wdGlvbv5pN/3bDmZ0AgAAeHB0AAFhc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHJhaXRDYWxsUOsZLSXsRVgCAAJMAARuYW1lcQB+AARMAAl0cGVTRXhwcnNxAH4AAXhwdAABQXEAfgAHcQB+AAl4cQB+AAl4cQB+ABp0AAR1bml0cQB+ABpzcQB+AAZzcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVBcmeSWyZIcvGbiQIABEwABWJvdW5kcQB+AANMAAxjb250ZXh0Qm91bmRxAH4AAUwABG5hbWVxAH4ABEwAB3RwYXJhbXNxAH4AAXhwcQB+ABpxAH4AB3QAAUFxAH4AB3EAfgAJeHNyAApzY2FsYS5Tb21lESLyaV6hi3QCAAFMAAF4dAASTGphdmEvbGFuZy9PYmplY3Q7eHEAfgAZc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQAAUFxAH4AB3EAfgAJeHEAfgAJeHEAfgAacHQABU1vbmFkcQB+ABpzcQB+AAZzcQB+ACFxAH4AGnEAfgAHdAABRnNxAH4ABnNxAH4AIXEAfgAacQB+AAd0AAFfcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4ADXEAfgAacQB+AAdxAH4AB3QABE1uZHN0AARtbmRzcQB+ABpxAH4AGg=="
  }
}