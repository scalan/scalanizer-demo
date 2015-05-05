package mnds {
  object StagedEvaluation {
    import scalan._;
    import scala.reflect.runtime.universe._;
    import scala.reflect._;
    import scalan.common.Default;
    trait MndsAbs extends Mnds with ScalanDsl { self: MndsDsl =>
      implicit def proxyMnd[F[_]](p: Rep[Mnd[F]]): Mnd[F] = proxyOps[Mnd[F]](p)(classTag[Mnd[F]]);
      class MndElem[F[_], To <: Mnd[F]](implicit val cF: Cont[F]) extends EntityElem[To] {
        override def isEntityType = true;
        override def tag = weakTypeTag[Mnd[F]].asInstanceOf[WeakTypeTag[To]];
        override def convert(x: Rep[(Reifiable[_$1] forSome { 
          type _$1
        })]) = convertMnd(x.asRep[Mnd[F]]);
        def convertMnd(x: Rep[Mnd[F]]): Rep[To] = x.asRep[To];
        override def getDefaultRep: Rep[To] = ???
      };
      implicit def mndElement[F[_]](implicit cF: Cont[F]): Elem[Mnd[F]] = {
        final class $anon extends MndElem[F, Mnd[F]];
        new $anon()
      };
      trait MndCompanionElem extends CompanionElem[MndCompanionAbs];
      implicit lazy val MndCompanionElem: MndCompanionElem = {
        final class $anon extends MndCompanionElem {
          lazy val tag = weakTypeTag[MndCompanionAbs];
          protected def getDefaultRep = Mnd
        };
        new $anon()
      };
      abstract class MndCompanionAbs extends CompanionBase[MndCompanionAbs] with MndCompanion {
        override def toString = "Mnd"
      };
      def Mnd: Rep[MndCompanionAbs];
      implicit def proxyMndCompanion(p: Rep[MndCompanion]): MndCompanion = proxyOps[MndCompanion](p)
    };
    trait MndsSeq extends MndsDsl with ScalanSeq { self: MndsDslSeq =>
      lazy val Mnd: Rep[MndCompanionAbs] = {
        final class $anon extends MndCompanionAbs with UserTypeSeq[MndCompanionAbs] {
          lazy val selfType = element[MndCompanionAbs]
        };
        new $anon()
      }
    };
    trait MndsExp extends MndsDsl with ScalanExp { self: MndsDslExp =>
      lazy val Mnd: Rep[MndCompanionAbs] = {
        final class $anon extends MndCompanionAbs with UserTypeDef[MndCompanionAbs] {
          lazy val selfType = element[MndCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      object MndMethods {
        object unit {
          def unapply(d: (Def[_$2] forSome { 
            type _$2
          })): Option[(scala.Tuple3[Rep[Mnd[F]], Rep[A], Rep[Elem[A]]] forSome { 
            type F[_];
            type A
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (eA @ _), _*), _) if (receiver.elem match {
  case (ve @ ((_): ViewElem[(_), (_)])) => (ve match {
    case ((_): MndElem[(_), (_)]) => true
    case _ => false
  })
  case _ => false
}).&&(method.getName.==("unit")) => Some(scala.Tuple3(receiver, a, eA)).asInstanceOf[Option[(scala.Tuple3[Rep[Mnd[F]], Rep[A], Rep[Elem[A]]] forSome { 
              type F[_];
              type A
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$3] forSome { 
            type _$3
          })): Option[(scala.Tuple3[Rep[Mnd[F]], Rep[A], Rep[Elem[A]]] forSome { 
            type F[_];
            type A
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object join {
          def unapply(d: (Def[_$4] forSome { 
            type _$4
          })): Option[(scala.Tuple3[Rep[Mnd[F]], Rep[F[F[A]]], Rep[Elem[A]]] forSome { 
            type F[_];
            type A
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((mma @ _), (eA @ _), _*), _) if (receiver.elem match {
  case (ve @ ((_): ViewElem[(_), (_)])) => (ve match {
    case ((_): MndElem[(_), (_)]) => true
    case _ => false
  })
  case _ => false
}).&&(method.getName.==("join")) => Some(scala.Tuple3(receiver, mma, eA)).asInstanceOf[Option[(scala.Tuple3[Rep[Mnd[F]], Rep[F[F[A]]], Rep[Elem[A]]] forSome { 
              type F[_];
              type A
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$5] forSome { 
            type _$5
          })): Option[(scala.Tuple3[Rep[Mnd[F]], Rep[F[F[A]]], Rep[Elem[A]]] forSome { 
            type F[_];
            type A
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object as {
          def unapply(d: (Def[_$6] forSome { 
            type _$6
          })): Option[(scala.Tuple5[Rep[Mnd[F]], Rep[F[A]], Rep[B], Rep[Elem[A]], Rep[Elem[B]]] forSome { 
            type F[_];
            type A;
            type B
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (b @ _), (eA @ _), (eB @ _), _*), _) if (receiver.elem match {
  case (ve @ ((_): ViewElem[(_), (_)])) => (ve match {
    case ((_): MndElem[(_), (_)]) => true
    case _ => false
  })
  case _ => false
}).&&(method.getName.==("as")) => Some(scala.Tuple5(receiver, a, b, eA, eB)).asInstanceOf[Option[(scala.Tuple5[Rep[Mnd[F]], Rep[F[A]], Rep[B], Rep[Elem[A]], Rep[Elem[B]]] forSome { 
              type F[_];
              type A;
              type B
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$7] forSome { 
            type _$7
          })): Option[(scala.Tuple5[Rep[Mnd[F]], Rep[F[A]], Rep[B], Rep[Elem[A]], Rep[Elem[B]]] forSome { 
            type F[_];
            type A;
            type B
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object skip {
          def unapply(d: (Def[_$8] forSome { 
            type _$8
          })): Option[(scala.Tuple3[Rep[Mnd[F]], Rep[F[A]], Rep[Elem[A]]] forSome { 
            type F[_];
            type A
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (eA @ _), _*), _) if (receiver.elem match {
  case (ve @ ((_): ViewElem[(_), (_)])) => (ve match {
    case ((_): MndElem[(_), (_)]) => true
    case _ => false
  })
  case _ => false
}).&&(method.getName.==("skip")) => Some(scala.Tuple3(receiver, a, eA)).asInstanceOf[Option[(scala.Tuple3[Rep[Mnd[F]], Rep[F[A]], Rep[Elem[A]]] forSome { 
              type F[_];
              type A
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$9] forSome { 
            type _$9
          })): Option[(scala.Tuple3[Rep[Mnd[F]], Rep[F[A]], Rep[Elem[A]]] forSome { 
            type F[_];
            type A
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object MndCompanionMethods
    };
    trait Mnds extends Base { self: MndsDsl =>
      trait Mnd[F[_]] extends Reifiable[Mnd[F]] {
        implicit def cF: Cont[F];
        def unit[A](a: Rep[A])(implicit eA: Elem[A]): Rep[F[A]];
        def flatMap[A, B](ma: Rep[F[A]])(f: Rep[scala.Function1[A, F[B]]])(implicit eA: Elem[A], eB: Elem[B]): Rep[F[B]] = join(map(ma)(f));
        def map[A, B](ma: Rep[F[A]])(f: Rep[scala.Function1[A, B]])(implicit eA: Elem[A], eB: Elem[B]): Rep[F[B]] = flatMap(ma)(fun(((a) => unit(f(a)))));
        def join[A](mma: Rep[F[F[A]]])(implicit eA: Elem[A]): Rep[F[A]] = flatMap(mma)(fun(((ma) => ma)));
        def map2[A, B, C](ma: Rep[F[A]], mb: Rep[F[B]])(f: Rep[scala.Function1[scala.Tuple2[A, B], C]])(implicit eA: Elem[A], eB: Elem[B], eC: Elem[C]): Rep[F[C]] = flatMap(ma)(fun(((a) => map(mb)(fun(((b) => f(a, b)))))));
        def seq[A, B, C](f: Rep[scala.Function1[A, F[B]]], g: Rep[scala.Function1[B, F[C]]])(implicit eA: Elem[A], eB: Elem[B], eC: Elem[C]): Rep[scala.Function1[A, F[C]]] = compose(f, g);
        def as[A, B](a: Rep[F[A]])(b: Rep[B])(implicit eA: Elem[A], eB: Elem[B]): Rep[F[B]] = map(a)(fun(((x$1) => b)));
        def skip[A](a: Rep[F[A]])(implicit eA: Elem[A]): Rep[F[Unit]] = as(a)(toRep(()));
        def compose[A, B, C](f: Rep[scala.Function1[A, F[B]]], g: Rep[scala.Function1[B, F[C]]])(implicit eA: Elem[A], eB: Elem[B], eC: Elem[C]): Rep[scala.Function1[A, F[C]]] = fun(((a) => flatMap(f(a))(g)))
      };
      trait MndCompanion
    };
    trait MndsDsl extends MndsAbs;
    trait MndsDslSeq extends MndsSeq;
    trait MndsDslExp extends MndsExp;
    val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZrSr+/co886dAgALTAAEYm9keXQAIUxzY2FsYS9jb2xsZWN0aW9uL2ltbXV0YWJsZS9MaXN0O0wAEGNvbmNyZXRlU0NsYXNzZXNxAH4AAUwACGVudGl0aWVzcQB+AAFMAAllbnRpdHlPcHN0ACFMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcmFpdERlZjtMABBlbnRpdHlSZXBTeW5vbnltdAAOTHNjYWxhL09wdGlvbjtMAAdpbXBvcnRzcQB+AAFMAAdtZXRob2RzcQB+AAFMAARuYW1ldAASTGphdmEvbGFuZy9TdHJpbmc7TAALcGFja2FnZU5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAApzZXFEc2xJbXBscQB+AAN4cHNyADJzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0JFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABAwAAeHBzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHEAfgAHc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHJhaXREZWYBvjSSNBSxsgIACVoACGJpdG1hcCQwTAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgAHcQB+AAdzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2REZWbdWXFxtzArDgIACloADGlzRWxlbU9yQ29udFoACmlzSW1wbGljaXRaAAppc092ZXJyaWRlTAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0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YBXhS+VovgIAAHhwdAABYXEAfgAacQB+AAl4c3EAfgBFc3EAfgAGc3EAfgAGc3EAfgBFc3EAfgAGc3EAfgAGc3EAfgBNdAABYXEAfgAJeHEAfgAJeHNxAH4ATXEAfgA6cQB+AAdxAH4ACXhxAH4ACXhzcQB+AE10AAR1bml0cQB+AAdxAH4ACXhxAH4ACXhzcQB+AE1xAH4AVnEAfgAHcQB+AFNxAH4AGnNxAH4ABnNxAH4AIXEAfgAacQB+AAd0AAFBcQB+AAdzcQB+ACFxAH4AGnEAfgAHdAABQnEAfgAHcQB+AAl4c3EAfgAkc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQAAUJxAH4AB3EAfgAJeHNxAH4ADwAAAHEAfgAHc3EAfgAGc3EAfgASc3EAfgAGc3EAfgAVAAAAcQB+AAdxAH4AGnQAA21tYXNxAH4AHHQAAUZzcQB+AAZzcQB+ABx0AAFGc3EAfgAGc3EAfgAcdAABQXEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4cQB+AAl4c3EAfgAkc3EAfgBFc3EAfgAGc3EAfgAGc3EAfgBNdAADbW1hcQB+AAl4c3EAfgAGc3EAfgB8c3EAfgAGc3EAfgB/AABzcQB+AIF0AAJtYXEAfgAacQB+AAl4c3EAfgBNdAACbWFxAH4ACXhxAH4ACXhzcQB+AE1xAH4AVnEAfgAHdAAEam9pbnEAfgAac3EAfgAGc3EAfgAhcQB+ABpxAH4AB3QAAUFxAH4AB3EAfgAJeHNxAH4AJHNxAH4AHHQAAUZzcQB+AAZzcQB+ABx0AAFBcQB+AAdxAH4ACXhzcQB+AA8AAABxAH4AB3NxAH4ABnNxAH4AEnNxAH4ABnNxAH4AFQAAAHEAfgAHcQB+ABp0AAJtYXNxAH4AHHQAAUZzcQB+AAZzcQB+ABx0AAFBcQB+AAdxAH4ACXhzcQB+ABUAAABxAH4AB3EAfgAadAACbWJzcQB+ABx0AAFGc3EAfgAGc3EAfgAcdAABQnEAfgAHcQB+AAl4cQB+AAl4c3EAfgASc3EAfgAGc3EAfgAVAAAAcQB+AAdxAH4AGnEAfgA6c3EAfgA7c3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlVHVwbGU0u/leiIBIcAIAAUwACXRwZVNFeHByc3EAfgABeHBzcQB+AAZzcQB+ABx0AAFBcQB+AAdzcQB+ABx0AAFCcQB+AAdxAH4ACXhzcQB+ABx0AAFDcQB+AAdxAH4ACXhxAH4ACXhzcQB+ACRzcQB+AEVzcQB+AAZzcQB+AAZzcQB+AE10AAJtYXEAfgAJeHNxAH4ABnNxAH4AfHNxAH4ABnNxAH4AfwAAc3EAfgCBdAABYXEAfgAacQB+AAl4c3EAfgBFc3EAfgAGc3EAfgAGc3EAfgBNdAACbWJxAH4ACXhzcQB+AAZzcQB+AHxzcQB+AAZzcQB+AH8AAHNxAH4AgXQAAWJxAH4AGnEAfgAJeHNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ATXQAAWFzcQB+AE10AAFicQB+AAl4cQB+AAl4c3EAfgBNcQB+ADpxAH4AB3EAfgAJeHEAfgAJeHNxAH4ATXEAfgBTcQB+AAdxAH4ACXhxAH4ACXhzcQB+AE1xAH4AVnEAfgAHdAAEbWFwMnEAfgAac3EAfgAGc3EAfgAhcQB+ABpxAH4AB3QAAUFxAH4AB3NxAH4AIXEAfgAacQB+AAd0AAFCcQB+AAdzcQB+ACFxAH4AGnEAfgAHdAABQ3EAfgAHcQB+AAl4c3EAfgAkc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQAAUNxAH4AB3EAfgAJeHNxAH4ADwAAAHEAfgAHc3EAfgAGc3EAfgASc3EAfgAGc3EAfgAVAAAAcQB+AAdxAH4AGnEAfgA6c3EAfgA7c3EAfgAcdAABQXEAfgAHc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQAAUJxAH4AB3EAfgAJeHNxAH4AFQAAAHEAfgAHcQB+ABp0AAFnc3EAfgA7c3EAfgAcdAABQnEAfgAHc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQAAUNxAH4AB3EAfgAJeHEAfgAJeHEAfgAJeHNxAH4AJHNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ATXEAfgA6c3EAfgBNdAABZ3EAfgAJeHEAfgAJeHNxAH4ATXQAB2NvbXBvc2VxAH4AB3QAA3NlcXEAfgAac3EAfgAGc3EAfgAhcQB+ABpxAH4AB3QAAUFxAH4AB3NxAH4AIXEAfgAacQB+AAd0AAFCcQB+AAdzcQB+ACFxAH4AGnEAfgAHdAABQ3EAfgAHcQB+AAl4c3EAfgAkc3EAfgA7c3EAfgAcdAABQXEAfgAHc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQAAUNxAH4AB3EAfgAJeHNxAH4ADwAAAHEAfgAHc3EAfgAGc3EAfgASc3EAfgAGc3EAfgAVAAAAcQB+AAdxAH4AGnQAAWFzcQB+ABx0AAFGc3EAfgAGc3EAfgAcdAABQXEAfgAHcQB+AAl4cQB+AAl4c3EAfgASc3EAfgAGc3EAfgAVAAAAcQB+AAdxAH4AGnQAAWJzcQB+ABx0AAFCcQB+AAdxAH4ACXhxAH4ACXhzcQB+ACRzcQB+AEVzcQB+AAZzcQB+AAZzcQB+AE10AAFhcQB+AAl4c3EAfgAGc3EAfgB8c3EAfgAGc3EAfgB/AABzcQB+AIF0AAN4JDFxAH4AGnEAfgAJeHNxAH4ATXQAAWJxAH4ACXhxAH4ACXhzcQB+AE1xAH4AU3EAfgAHdAACYXNxAH4AGnNxAH4ABnNxAH4AIXEAfgAacQB+AAd0AAFBcQB+AAdzcQB+ACFxAH4AGnEAfgAHdAABQnEAfgAHcQB+AAl4c3EAfgAkc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQAAUJxAH4AB3EAfgAJeHNxAH4ADwAAAHEAfgAHc3EAfgAGc3EAfgASc3EAfgAGc3EAfgAVAAAAcQB+AAdxAH4AGnQAAWFzcQB+ABx0AAFGc3EAfgAGc3EAfgAcdAABQXEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4c3EAfgAkc3EAfgBFc3EAfgAGc3EAfgAGc3EAfgBNdAABYXEAfgAJeHNxAH4ABnNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NvbnN0Gk0U0WJLZ4oCAAFMAAFjcQB+ACV4cHNyABdzY2FsYS5ydW50aW1lLkJveGVkVW5pdHSmfUcd7MuaAgAAeHBxAH4ACXhxAH4ACXhzcQB+AE10AAJhc3EAfgAHdAAEc2tpcHEAfgAac3EAfgAGc3EAfgAhcQB+ABpxAH4AB3QAAUFxAH4AB3EAfgAJeHNxAH4AJHNxAH4AHHQAAUZzcQB+AAZzcQB+ABx0AARVbml0cQB+AAdxAH4ACXhzcQB+AA8AAABxAH4AB3NxAH4ABnNxAH4AEnNxAH4ABnNxAH4AFQAAAHEAfgAHcQB+ABpxAH4AOnNxAH4AO3NxAH4AHHQAAUFxAH4AB3NxAH4AHHQAAUZzcQB+AAZzcQB+ABx0AAFCcQB+AAdxAH4ACXhzcQB+ABUAAABxAH4AB3EAfgAadAABZ3NxAH4AO3NxAH4AHHQAAUJxAH4AB3NxAH4AHHQAAUZzcQB+AAZzcQB+ABx0AAFDcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhzcQB+ACRzcQB+AHxzcQB+AAZzcQB+AH8AAHNxAH4AgXQAAWFxAH4AGnEAfgAJeHNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ATXQAAWFxAH4ACXhxAH4ACXhzcQB+AE1xAH4AOnEAfgAHcQB+AAl4c3EAfgAGc3EAfgBNdAABZ3EAfgAJeHEAfgAJeHNxAH4ATXEAfgBWcQB+AAd0AAdjb21wb3NlcQB+ABpzcQB+AAZzcQB+ACFxAH4AGnEAfgAHdAABQXEAfgAHc3EAfgAhcQB+ABpxAH4AB3QAAUJxAH4AB3NxAH4AIXEAfgAacQB+AAd0AAFDcQB+AAdxAH4ACXhzcQB+ACRzcQB+ADtzcQB+ABx0AAFBcQB+AAdzcQB+ABx0AAFGc3EAfgAGc3EAfgAcdAABQ3EAfgAHcQB+AAl4cQB+AAl4cQB+ABpwdAADTW5kcQB+ABpzcQB+AAZzcQB+ACFxAH4AGnEAfgAHdAABRnNxAH4ABnNxAH4AIXEAfgAacQB+AAd0AAFfcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4ADXEAfgAacQB+AAdxAH4AB3QABE1uZHN0AARtbmRzcQB+ABpxAH4AGg=="
  }
}