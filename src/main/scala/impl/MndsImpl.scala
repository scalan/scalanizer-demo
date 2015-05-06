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
        object sequence {
          def unapply(d: (Def[_$4] forSome { 
            type _$4
          })): Option[(scala.Tuple3[Rep[Mnd[F]], Rep[List[F[A]]], Rep[Elem[A]]] forSome { 
            type F[_];
            type A
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((lma @ _), (eA @ _), _*), _) if (receiver.elem match {
  case (ve @ ((_): ViewElem[(_), (_)])) => (ve match {
    case ((_): MndElem[(_), (_)]) => true
    case _ => false
  })
  case _ => false
}).&&(method.getName.==("sequence")) => Some(scala.Tuple3(receiver, lma, eA)).asInstanceOf[Option[(scala.Tuple3[Rep[Mnd[F]], Rep[List[F[A]]], Rep[Elem[A]]] forSome { 
              type F[_];
              type A
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$5] forSome { 
            type _$5
          })): Option[(scala.Tuple3[Rep[Mnd[F]], Rep[List[F[A]]], Rep[Elem[A]]] forSome { 
            type F[_];
            type A
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object replicateM {
          def unapply(d: (Def[_$6] forSome { 
            type _$6
          })): Option[(scala.Tuple4[Rep[Mnd[F]], Rep[Int], Rep[F[A]], Rep[Elem[A]]] forSome { 
            type F[_];
            type A
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((n @ _), (ma @ _), (eA @ _), _*), _) if (receiver.elem match {
  case (ve @ ((_): ViewElem[(_), (_)])) => (ve match {
    case ((_): MndElem[(_), (_)]) => true
    case _ => false
  })
  case _ => false
}).&&(method.getName.==("replicateM")) => Some(scala.Tuple4(receiver, n, ma, eA)).asInstanceOf[Option[(scala.Tuple4[Rep[Mnd[F]], Rep[Int], Rep[F[A]], Rep[Elem[A]]] forSome { 
              type F[_];
              type A
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$7] forSome { 
            type _$7
          })): Option[(scala.Tuple4[Rep[Mnd[F]], Rep[Int], Rep[F[A]], Rep[Elem[A]]] forSome { 
            type F[_];
            type A
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object replicateM_ {
          def unapply(d: (Def[_$8] forSome { 
            type _$8
          })): Option[(scala.Tuple4[Rep[Mnd[F]], Rep[Int], Rep[F[A]], Rep[Elem[A]]] forSome { 
            type F[_];
            type A
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((n @ _), (f @ _), (eA @ _), _*), _) if (receiver.elem match {
  case (ve @ ((_): ViewElem[(_), (_)])) => (ve match {
    case ((_): MndElem[(_), (_)]) => true
    case _ => false
  })
  case _ => false
}).&&(method.getName.==("replicateM_")) => Some(scala.Tuple4(receiver, n, f, eA)).asInstanceOf[Option[(scala.Tuple4[Rep[Mnd[F]], Rep[Int], Rep[F[A]], Rep[Elem[A]]] forSome { 
              type F[_];
              type A
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$9] forSome { 
            type _$9
          })): Option[(scala.Tuple4[Rep[Mnd[F]], Rep[Int], Rep[F[A]], Rep[Elem[A]]] forSome { 
            type F[_];
            type A
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object as {
          def unapply(d: (Def[_$10] forSome { 
            type _$10
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
          def unapply(exp: (Exp[_$11] forSome { 
            type _$11
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
          def unapply(d: (Def[_$12] forSome { 
            type _$12
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
          def unapply(exp: (Exp[_$13] forSome { 
            type _$13
          })): Option[(scala.Tuple3[Rep[Mnd[F]], Rep[F[A]], Rep[Elem[A]]] forSome { 
            type F[_];
            type A
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object join {
          def unapply(d: (Def[_$14] forSome { 
            type _$14
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
          def unapply(exp: (Exp[_$15] forSome { 
            type _$15
          })): Option[(scala.Tuple3[Rep[Mnd[F]], Rep[F[F[A]]], Rep[Elem[A]]] forSome { 
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
        def map2[A, B, C](ma: Rep[F[A]], mb: Rep[F[B]])(f: Rep[scala.Function1[scala.Tuple2[A, B], C]])(implicit eA: Elem[A], eB: Elem[B], eC: Elem[C]): Rep[F[C]] = flatMap(ma)(fun(((a) => map(mb)(fun(((b) => f(a, b)))))));
        def sequence[A](lma: Rep[List[F[A]]])(implicit eA: Elem[A]): Rep[F[List[A]]] = lma.foldRight[F[List[A]]](unit(List.empty[A]))(fun(((in: Rep[scala.Tuple2[F[A], F[List[A]]]]) => {
          val ma: Rep[F[A]] = in._1;
          val mla: Rep[F[List[A]]] = in._2;
          map2(ma, mla)(fun(((in: Rep[scala.Tuple2[A, List[A]]]) => {
            val a: Rep[A] = in._1;
            val la: Rep[List[A]] = in._2;
            {
              val x$1 = a;
              la.::(x$1)
            }
          })))
        })));
        def traverse[A, B](la: Rep[List[A]])(f: Rep[scala.Function1[A, F[B]]])(implicit eA: Elem[A], eB: Elem[B]): Rep[F[List[B]]] = la.foldRight[F[List[B]]](unit(List.empty[B]))(fun(((in: Rep[scala.Tuple2[A, F[List[B]]]]) => {
          val a: Rep[A] = in._1;
          val mlb: Rep[F[List[B]]] = in._2;
          map2(f(a), mlb)(fun(((in: Rep[scala.Tuple2[B, List[B]]]) => {
            val fa: Rep[B] = in._1;
            val lb: Rep[List[B]] = in._2;
            {
              val x$2 = fa;
              lb.::(x$2)
            }
          })))
        })));
        def replicateM[A](n: Rep[Int])(ma: Rep[F[A]])(implicit eA: Elem[A]): Rep[F[List[A]]] = sequence(List[F[A]]());
        def replicateM_[A](n: Rep[Int])(f: Rep[F[A]])(implicit eA: Elem[A]): Rep[F[Unit]] = foreachM(List[F[A]]())(fun(((x$3) => skip(x$3))));
        def foldM[A, B](la: Rep[List[A]])(z: Rep[B])(f: Rep[scala.Function1[scala.Tuple2[B, A], F[B]]])(implicit eA: Elem[A], eB: Elem[B]): Rep[F[B]] = la.foldLeft[F[B]](unit(z))(fun(((in: Rep[scala.Tuple2[F[B], A]]) => {
          val fb: Rep[F[B]] = in._1;
          val a: Rep[A] = in._2;
          flatMap(fb)(fun(((b: Rep[B]) => f(b, a))))
        })));
        def foldM_[A, B](l: Rep[List[A]])(z: Rep[B])(f: Rep[scala.Function1[scala.Tuple2[B, A], F[B]]])(implicit eA: Elem[A], eB: Elem[B]): Rep[F[Unit]] = skip(foldM(l)(z)(f));
        def foreachM[A](l: Rep[List[A]])(f: Rep[scala.Function1[A, F[Unit]]])(implicit eA: Elem[A]): Rep[F[Unit]] = foldM_(l)(toRep(()))(fun(((in: Rep[scala.Tuple2[Unit, A]]) => {
          val u: Rep[Unit] = in._1;
          val a: Rep[A] = in._2;
          skip(f(a))
        })));
        def seq[A, B, C](f: Rep[scala.Function1[A, F[B]]], g: Rep[scala.Function1[B, F[C]]])(implicit eA: Elem[A], eB: Elem[B], eC: Elem[C]): Rep[scala.Function1[A, F[C]]] = compose(f, g);
        def as[A, B](a: Rep[F[A]])(b: Rep[B])(implicit eA: Elem[A], eB: Elem[B]): Rep[F[B]] = map(a)(fun(((x$4) => b)));
        def skip[A](a: Rep[F[A]])(implicit eA: Elem[A]): Rep[F[Unit]] = as(a)(toRep(()));
        def compose[A, B, C](f: Rep[scala.Function1[A, F[B]]], g: Rep[scala.Function1[B, F[C]]])(implicit eA: Elem[A], eB: Elem[B], eC: Elem[C]): Rep[scala.Function1[A, F[C]]] = fun(((a) => flatMap(f(a))(g)));
        def join[A](mma: Rep[F[F[A]]])(implicit eA: Elem[A]): Rep[F[A]] = flatMap(mma)(fun(((ma) => ma)));
        def filterM[A](ms: Rep[List[A]])(f: Rep[scala.Function1[A, F[Boolean]]])(implicit eA: Elem[A]): Rep[F[List[A]]] = ms.foldRight(unit(List.empty[A]))(fun(((in: Rep[scala.Tuple2[A, F[List[A]]]]) => {
          val x: Rep[A] = in._1;
          val y: Rep[F[List[A]]] = in._2;
          {
            val h = compose(f, fun(((b: Rep[Boolean]) => IF(b).THEN(map2(unit(x), y)(fun(((in: Rep[scala.Tuple2[A, List[A]]]) => {
  val a: Rep[A] = in._1;
  val la: Rep[List[A]] = in._2;
  {
    val x$5 = a;
    la.::(x$5)
  }
})))).ELSE(y))));
            h(x)
          }
        })))
      };
      trait MndCompanion
    };
    trait MndsDsl extends MndsAbs;
    trait MndsDslSeq extends MndsSeq;
    trait MndsDslExp extends MndsExp;
    val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZrSr+/co886dAgALTAAEYm9keXQAIUxzY2FsYS9jb2xsZWN0aW9uL2ltbXV0YWJsZS9MaXN0O0wAEGNvbmNyZXRlU0NsYXNzZXNxAH4AAUwACGVudGl0aWVzcQB+AAFMAAllbnRpdHlPcHN0ACFMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcmFpdERlZjtMABBlbnRpdHlSZXBTeW5vbnltdAAOTHNjYWxhL09wdGlvbjtMAAdpbXBvcnRzcQB+AAFMAAdtZXRob2RzcQB+AAFMAARuYW1ldAASTGphdmEvbGFuZy9TdHJpbmc7TAALcGFja2FnZU5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAApzZXFEc2xJbXBscQB+AAN4cHNyADJzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0JFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABAwAAeHBzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHEAfgAHc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHJhaXREZWYBvjSSNBSxsgIACVoACGJpdG1hcCQwTAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgAHcQB+AAdzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2REZWbdWXFxtzArDgIACloADGlzRWxlbU9yQ29udFoACmlzSW1wbGljaXRaAAppc092ZXJyaWRlTAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0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AAd0AARtYXAycQB+ABpzcQB+AAZzcQB+ACFxAH4AGnEAfgAHdAABQXEAfgAHc3EAfgAhcQB+ABpxAH4AB3QAAUJxAH4AB3NxAH4AIXEAfgAacQB+AAd0AAFDcQB+AAdxAH4ACXhzcQB+ACRzcQB+ABx0AAFGc3EAfgAGc3EAfgAcdAABQ3EAfgAHcQB+AAl4c3EAfgAPAAAAcQB+AAdzcQB+AAZzcQB+ABJzcQB+AAZzcQB+ABUAAABxAH4AB3EAfgAadAADbG1hc3EAfgAcdAAETGlzdHNxAH4ABnNxAH4AHHQAAUZzcQB+AAZzcQB+ABx0AAFBcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4ACXhzcQB+ACRzcQB+AEVzcQB+AAZzcQB+AAZzcQB+AEVzcQB+AAZzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUeXBlQXBwbHka36/N9eX6UAIAAkwAA2Z1bnEAfgBGTAACdHNxAH4AAXhwc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTU2VsZWN0C91pZWVA+YUCAAJMAARleHBycQB+AEZMAAV0bmFtZXEAfgAEeHBzcQB+AE10AARMaXN0dAAFZW1wdHlzcQB+AAZzcQB+ABx0AAFBcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhzcQB+AE10AAR1bml0cQB+AAdxAH4ACXhzcQB+AAZzcQB+AHxzcQB+AAZzcQB+AH8AAHNxAH4AgXQAAm1hc3EAfgAkc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQAAUFxAH4AB3EAfgAJeHNxAH4AfwAAc3EAfgCBdAADbWxhc3EAfgAkc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQABExpc3RzcQB+AAZzcQB+ABx0AAFBcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhzcQB+AEVzcQB+AAZzcQB+AAZzcQB+AE10AAJtYXNxAH4ATXQAA21sYXEAfgAJeHNxAH4ABnNxAH4AfHNxAH4ABnNxAH4AfwAAc3EAfgCBdAABYXNxAH4AJHNxAH4AHHQAAUFxAH4AB3NxAH4AfwAAc3EAfgCBdAACbGFzcQB+ACRzcQB+ABx0AARMaXN0c3EAfgAGc3EAfgAcdAABQXEAfgAHcQB+AAl4cQB+AAl4c3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQmxvY2tVLepmcRQ0/QIAAkwABGluaXRxAH4AAUwABGxhc3RxAH4ARnhwc3EAfgAGc3EAfgB/AABzcQB+AE10AAFhdAADeCQxcQB+ABpxAH4ACXhzcQB+AEVzcQB+AAZzcQB+AAZzcQB+AE1xAH4BQXEAfgAJeHEAfgAJeHNxAH4BAHNxAH4ATXQAAmxhdAAMJGNvbG9uJGNvbG9ucQB+AAdxAH4ACXhxAH4ACXhzcQB+AE10AARtYXAycQB+AAdxAH4ACXhxAH4ACXhzcQB+AQBzcQB+AE10AANsbWF0AAlmb2xkUmlnaHRzcQB+AAZzcQB+ABx0AAFGc3EAfgAGc3EAfgAcdAAETGlzdHNxAH4ABnNxAH4AHHQAAUFxAH4AB3EAfgAJeHEAfgAJeHEAfgAJeHQACHNlcXVlbmNlcQB+ABpzcQB+AAZzcQB+ACFxAH4AGnEAfgAHdAABQXEAfgAHcQB+AAl4c3EAfgAkc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQABExpc3RzcQB+AAZzcQB+ABx0AAFBcQB+AAdxAH4ACXhxAH4ACXhzcQB+AA8AAABxAH4AB3NxAH4ABnNxAH4AEnNxAH4ABnNxAH4AFQAAAHEAfgAHcQB+ABp0AAJsYXNxAH4AHHQABExpc3RzcQB+AAZzcQB+ABx0AAFBcQB+AAdxAH4ACXhxAH4ACXhzcQB+ABJzcQB+AAZzcQB+ABUAAABxAH4AB3EAfgAacQB+ADpzcQB+ADtzcQB+ABx0AAFBcQB+AAdzcQB+ABx0AAFGc3EAfgAGc3EAfgAcdAABQnEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4c3EAfgAkc3EAfgBFc3EAfgAGc3EAfgAGc3EAfgBFc3EAfgAGc3EAfgAGc3EAfgD+c3EAfgEAc3EAfgBNcQB+AQN0AAVlbXB0eXNxAH4ABnNxAH4AHHQAAUJxAH4AB3EAfgAJeHEAfgAJeHEAfgAJeHNxAH4ATXQABHVuaXRxAH4AB3EAfgAJeHNxAH4ABnNxAH4AfHNxAH4ABnNxAH4AfwAAc3EAfgCBdAABYXNxAH4AJHNxAH4AHHQAAUFxAH4AB3NxAH4AfwAAc3EAfgCBdAADbWxic3EAfgAkc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQABExpc3RzcQB+AAZzcQB+ABx0AAFCcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhzcQB+AEVzcQB+AAZzcQB+AAZzcQB+AEVzcQB+AAZzcQB+AAZzcQB+AE10AAFhcQB+AAl4cQB+AAl4c3EAfgBNcQB+ADpxAH4AB3NxAH4ATXQAA21sYnEAfgAJeHNxAH4ABnNxAH4AfHNxAH4ABnNxAH4AfwAAc3EAfgCBdAACZmFzcQB+ACRzcQB+ABx0AAFCcQB+AAdzcQB+AH8AAHNxAH4AgXQAAmxic3EAfgAkc3EAfgAcdAAETGlzdHNxAH4ABnNxAH4AHHQAAUJxAH4AB3EAfgAJeHEAfgAJeHNxAH4BO3NxAH4ABnNxAH4AfwAAc3EAfgBNdAACZmF0AAN4JDJxAH4AGnEAfgAJeHNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ATXEAfgHDcQB+AAl4cQB+AAl4c3EAfgEAc3EAfgBNdAACbGJxAH4BSXEAfgAHcQB+AAl4cQB+AAl4c3EAfgBNdAAEbWFwMnEAfgAHcQB+AAl4cQB+AAl4c3EAfgEAc3EAfgBNdAACbGF0AAlmb2xkUmlnaHRzcQB+AAZzcQB+ABx0AAFGc3EAfgAGc3EAfgAcdAAETGlzdHNxAH4ABnNxAH4AHHQAAUJxAH4AB3EAfgAJeHEAfgAJeHEAfgAJeHQACHRyYXZlcnNlcQB+ABpzcQB+AAZzcQB+ACFxAH4AGnEAfgAHdAABQXEAfgAHc3EAfgAhcQB+ABpxAH4AB3QAAUJxAH4AB3EAfgAJeHNxAH4AJHNxAH4AHHQAAUZzcQB+AAZzcQB+ABx0AARMaXN0c3EAfgAGc3EAfgAcdAABQnEAfgAHcQB+AAl4cQB+AAl4c3EAfgAPAAAAcQB+AAdzcQB+AAZzcQB+ABJzcQB+AAZzcQB+ABUAAABxAH4AB3EAfgAadAABbnNyACNzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZVByaW1pdGl2ZTXDu1Xwvk4FAgACTAASZGVmYXVsdFZhbHVlU3RyaW5ncQB+AARMAARuYW1lcQB+AAR4cHQAATB0AANJbnRxAH4ACXhzcQB+ABJzcQB+AAZzcQB+ABUAAABxAH4AB3EAfgAadAACbWFzcQB+ABx0AAFGc3EAfgAGc3EAfgAcdAABQXEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4c3EAfgAkc3EAfgBFc3EAfgAGc3EAfgAGc3EAfgBFc3EAfgAGcQB+AAdxAH4ACXhzcQB+AE1xAH4BA3NxAH4ABnNxAH4AHHQAAUZzcQB+AAZzcQB+ABx0AAFBcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4ACXhzcQB+AE10AAhzZXF1ZW5jZXEAfgAHdAAKcmVwbGljYXRlTXEAfgAac3EAfgAGc3EAfgAhcQB+ABpxAH4AB3QAAUFxAH4AB3EAfgAJeHNxAH4AJHNxAH4AHHQAAUZzcQB+AAZzcQB+ABx0AARMaXN0c3EAfgAGc3EAfgAcdAABQXEAfgAHcQB+AAl4cQB+AAl4c3EAfgAPAAAAcQB+AAdzcQB+AAZzcQB+ABJzcQB+AAZzcQB+ABUAAABxAH4AB3EAfgAadAABbnEAfgHwcQB+AAl4c3EAfgASc3EAfgAGc3EAfgAVAAAAcQB+AAdxAH4AGnEAfgA6c3EAfgAcdAABRnNxAH4ABnNxAH4AHHQAAUFxAH4AB3EAfgAJeHEAfgAJeHEAfgAJeHNxAH4AJHNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ARXNxAH4ABnEAfgAHcQB+AAl4c3EAfgBNcQB+AQNzcQB+AAZzcQB+ABx0AAFGc3EAfgAGc3EAfgAcdAABQXEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4c3EAfgAGc3EAfgB8c3EAfgAGc3EAfgB/AABzcQB+AIF0AAN4JDNxAH4AGnEAfgAJeHNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ATXEAfgI4cQB+AAl4cQB+AAl4c3EAfgBNdAAEc2tpcHEAfgAHcQB+AAl4cQB+AAl4c3EAfgBNdAAIZm9yZWFjaE1xAH4AB3QAC3JlcGxpY2F0ZU1fcQB+ABpzcQB+AAZzcQB+ACFxAH4AGnEAfgAHdAABQXEAfgAHcQB+AAl4c3EAfgAkc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQABFVuaXRxAH4AB3EAfgAJeHNxAH4ADwAAAHEAfgAHc3EAfgAGc3EAfgASc3EAfgAGc3EAfgAVAAAAcQB+AAdxAH4AGnQAAmxhc3EAfgAcdAAETGlzdHNxAH4ABnNxAH4AHHQAAUFxAH4AB3EAfgAJeHEAfgAJeHNxAH4AEnNxAH4ABnNxAH4AFQAAAHEAfgAHcQB+ABp0AAF6c3EAfgAcdAABQnEAfgAHcQB+AAl4c3EAfgASc3EAfgAGc3EAfgAVAAAAcQB+AAdxAH4AGnEAfgA6c3EAfgA7c3EAfgCxc3EAfgAGc3EAfgAcdAABQnEAfgAHc3EAfgAcdAABQXEAfgAHcQB+AAl4c3EAfgAcdAABRnNxAH4ABnNxAH4AHHQAAUJxAH4AB3EAfgAJeHEAfgAJeHEAfgAJeHNxAH4AJHNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ATXQAAXpxAH4ACXhxAH4ACXhzcQB+AE10AAR1bml0cQB+AAdxAH4ACXhzcQB+AAZzcQB+AHxzcQB+AAZzcQB+AH8AAHNxAH4AgXQAAmZic3EAfgAkc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQAAUJxAH4AB3EAfgAJeHNxAH4AfwAAc3EAfgCBdAABYXNxAH4AJHNxAH4AHHQAAUFxAH4AB3EAfgAJeHNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ATXQAAmZicQB+AAl4c3EAfgAGc3EAfgB8c3EAfgAGc3EAfgB/AABzcQB+AIF0AAFic3EAfgAkc3EAfgAcdAABQnEAfgAHcQB+AAl4c3EAfgBFc3EAfgAGc3EAfgAGc3EAfgBNdAABYnNxAH4ATXQAAWFxAH4ACXhxAH4ACXhzcQB+AE1xAH4AOnEAfgAHcQB+AAl4cQB+AAl4c3EAfgBNcQB+AFZxAH4AB3EAfgAJeHEAfgAJeHNxAH4BAHNxAH4ATXQAAmxhdAAIZm9sZExlZnRzcQB+AAZzcQB+ABx0AAFGc3EAfgAGc3EAfgAcdAABQnEAfgAHcQB+AAl4cQB+AAl4dAAFZm9sZE1xAH4AGnNxAH4ABnNxAH4AIXEAfgAacQB+AAd0AAFBcQB+AAdzcQB+ACFxAH4AGnEAfgAHdAABQnEAfgAHcQB+AAl4c3EAfgAkc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQAAUJxAH4AB3EAfgAJeHNxAH4ADwAAAHEAfgAHc3EAfgAGc3EAfgASc3EAfgAGc3EAfgAVAAAAcQB+AAdxAH4AGnQAAWxzcQB+ABx0AARMaXN0c3EAfgAGc3EAfgAcdAABQXEAfgAHcQB+AAl4cQB+AAl4c3EA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GnEAfgAHdAABQXEAfgAHc3EAfgAhcQB+ABpxAH4AB3QAAUJxAH4AB3NxAH4AIXEAfgAacQB+AAd0AAFDcQB+AAdxAH4ACXhzcQB+ACRzcQB+ADtzcQB+ABx0AAFBcQB+AAdzcQB+ABx0AAFGc3EAfgAGc3EAfgAcdAABQ3EAfgAHcQB+AAl4c3EAfgAPAAAAcQB+AAdzcQB+AAZzcQB+ABJzcQB+AAZzcQB+ABUAAABxAH4AB3EAfgAadAADbW1hc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQAAUZzcQB+AAZzcQB+ABx0AAFBcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4ACXhzcQB+ACRzcQB+AEVzcQB+AAZzcQB+AAZzcQB+AE10AANtbWFxAH4ACXhzcQB+AAZzcQB+AHxzcQB+AAZzcQB+AH8AAHNxAH4AgXQAAm1hcQB+ABpxAH4ACXhzcQB+AE10AAJtYXEAfgAJeHEAfgAJeHNxAH4ATXEAfgBWcQB+AAd0AARqb2lucQB+ABpzcQB+AAZzcQB+ACFxAH4AGnEAfgAHdAABQXEAfgAHcQB+AAl4c3EAfgAkc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQAAUFxAH4AB3EAfgAJeHNxAH4ADwAAAHEAfgAHc3EAfgAGc3EAfgASc3EAfgAGc3EAfgAVAAAAcQB+AAdxAH4AGnQAAm1zc3EAfgAcdAAETGlzdHNxAH4ABnNxAH4AHHQAAUFxAH4AB3EAfgAJeHEAfgAJeHNxAH4AEnNxAH4ABnNxAH4AFQAAAHEAfgAHcQB+ABpxAH4AOnNxAH4AO3NxAH4AHHQAAUFxAH4AB3NxAH4AHHQAAUZzcQB+AAZzcQB+Ae90AAVmYWxzZXQAB0Jvb2xlYW5xAH4ACXhxAH4ACXhxAH4ACXhzcQB+ACRzcQB+AEVzcQB+AAZzcQB+AAZzcQB+AEVzcQB+AAZzcQB+AAZzcQB+AP5zcQB+AQBzcQB+AE1xAH4BA3QABWVtcHR5c3EAfgAGc3EAfgAcdAABQXEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4c3EAfgBNdAAEdW5pdHEAfgAHcQB+AAl4c3EAfgAGc3EAfgB8c3EAfgAGc3EAfgB/AABzcQB+AIF0AAF4c3EAfgAkc3EAfgAcdAABQXEAfgAHc3EAfgB/AABzcQB+AIF0AAF5c3EAfgAkc3EAfgAcdAABRnNxAH4ABnNxAH4AHHQABExpc3RzcQB+AAZzcQB+ABx0AAFBcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhzcQB+ATtzcQB+AAZzcQB+AH8AAHNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ATXEAfgA6c3EAfgB8c3EAfgAGc3EAfgB/AABzcQB+AIF0AAFic3EAfgAkcQB+BCdxAH4ACXhzcgAZc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJZiiWVme4wywdAgADTAAEY29uZHEAfgBGTAACZWxxAH4ARkwAAnRocQB+AEZ4cHNxAH4ATXQAAWJzcQB+AE10AAF5c3EAfgBFc3EAfgAGc3EAfgAGc3EAfgBFc3EAfgAGc3EAfgAGc3EAfgBNdAABeHEAfgAJeHEAfgAJeHNxAH4ATXQABHVuaXRxAH4AB3NxAH4ATXQAAXlxAH4ACXhzcQB+AAZzcQB+AHxzcQB+AAZzcQB+AH8AAHNxAH4AgXQAAWFzcQB+ACRzcQB+ABx0AAFBcQB+AAdzcQB+AH8AAHNxAH4AgXQAAmxhc3EAfgAkc3EAfgAcdAAETGlzdHNxAH4ABnNxAH4AHHQAAUFxAH4AB3EAfgAJeHEAfgAJeHNxAH4BO3NxAH4ABnNxAH4AfwAAc3EAfgBNdAABYXQAA3gkNXEAfgAacQB+AAl4c3EAfgBFc3EAfgAGc3EAfgAGc3EAfgBNcQB+BIVxAH4ACXhxAH4ACXhzcQB+AQBzcQB+AE10AAJsYXEAfgFJcQB+AAdxAH4ACXhxAH4ACXhzcQB+AE10AARtYXAycQB+AAdxAH4ACXhxAH4ACXhzcQB+AE10AAdjb21wb3NlcQB+AAd0AAFocQB+ABpxAH4ACXhzcQB+AEVzcQB+AAZzcQB+AAZzcQB+AE10AAF4cQB+AAl4cQB+AAl4c3EAfgBNdAABaHEAfgAHcQB+AAl4cQB+AAl4c3EAfgEAc3EAfgBNdAACbXN0AAlmb2xkUmlnaHRxAH4AB3QAB2ZpbHRlck1xAH4AGnNxAH4ABnNxAH4AIXEAfgAacQB+AAd0AAFBcQB+AAdxAH4ACXhzcQB+ACRzcQB+ABx0AAFGc3EAfgAGc3EAfgAcdAAETGlzdHNxAH4ABnNxAH4AHHQAAUFxAH4AB3EAfgAJeHEAfgAJeHEAfgAJeHEAfgAacHQAA01uZHEAfgAac3EAfgAGc3EAfgAhcQB+ABpxAH4AB3QAAUZzcQB+AAZzcQB+ACFxAH4AGnEAfgAHdAABX3EAfgAHcQB+AAl4cQB+AAl4cQB+AAl4cQB+AA1xAH4AGnEAfgAHcQB+AAd0AARNbmRzdAAEbW5kc3EAfgAacQB+ABo="
  }
}