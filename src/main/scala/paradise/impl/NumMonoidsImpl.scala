package paradise {
  package implOfNumMonoids {
    object StagedEvaluation {
      import scalan._;
      import paradise.implOfNumers.StagedEvaluation._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait NumMonoidsAbs extends NumMonoids with ScalanDsl { self: NumMonoidsDsl =>
        implicit def proxyNumMonoid[A](p: Rep[NumMonoid[A]]): NumMonoid[A] = proxyOps[NumMonoid[A]](p)(classTag[NumMonoid[A]]);
        class NumMonoidElem[A, To <: NumMonoid[A]](implicit val eA: Elem[A]) extends EntityElem[To] {
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagA = eA.tag;
            weakTypeTag[NumMonoid[A]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$1] forSome { 
            type _$1
          })]) = {
            val conv = fun(((x: Rep[NumMonoid[A]]) => convertNumMonoid(x)));
            tryConvert(element[NumMonoid[A]], this, x, conv)
          };
          def convertNumMonoid(x: Rep[NumMonoid[A]]): Rep[To] = {
            assert(x.selfType1 match {
              case ((_): NumMonoidElem[(_), (_)]) => true
              case _ => false
            });
            x.asRep[To]
          };
          override def getDefaultRep: Rep[To] = ???
        };
        implicit def numMonoidElement[A](implicit eA: Elem[A]): Elem[NumMonoid[A]] = new NumMonoidElem[A, NumMonoid[A]]();
        implicit case object NumMonoidCompanionElem extends CompanionElem[NumMonoidCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[NumMonoidCompanionAbs];
          protected def getDefaultRep = NumMonoid
        };
        abstract class NumMonoidCompanionAbs extends CompanionBase[NumMonoidCompanionAbs] with NumMonoidCompanion {
          override def toString = "NumMonoid"
        };
        def NumMonoid: Rep[NumMonoidCompanionAbs];
        implicit def proxyNumMonoidCompanion(p: Rep[NumMonoidCompanion]): NumMonoidCompanion = proxyOps[NumMonoidCompanion](p);
        class PlusMonoidElem[A](val iso: Iso[PlusMonoidData[A], PlusMonoid[A]])(implicit n: Numer[A], eA: Elem[A]) extends NumMonoidElem[A, PlusMonoid[A]] with ConcreteElem[PlusMonoidData[A], PlusMonoid[A]] {
          override def convertNumMonoid(x: Rep[NumMonoid[A]]) = PlusMonoid();
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = {
            implicit val tagA = eA.tag;
            weakTypeTag[PlusMonoid[A]]
          }
        };
        type PlusMonoidData[A] = Unit;
        class PlusMonoidIso[A](implicit n: Numer[A], eA: Elem[A]) extends Iso[PlusMonoidData[A], PlusMonoid[A]] {
          override def from(p: Rep[PlusMonoid[A]]) = ();
          override def to(p: Rep[Unit]) = {
            val unit = p;
            PlusMonoid()
          };
          lazy val defaultRepTo = Default.defaultVal[Rep[PlusMonoid[A]]](PlusMonoid());
          lazy val eTo = new PlusMonoidElem[A](this)
        };
        abstract class PlusMonoidCompanionAbs extends CompanionBase[PlusMonoidCompanionAbs] with PlusMonoidCompanion {
          override def toString = "PlusMonoid";
          def apply[A](p: Rep[PlusMonoidData[A]])(implicit n: Numer[A], eA: Elem[A]): Rep[PlusMonoid[A]] = isoPlusMonoid(n, eA).to(p);
          def apply[A]()(implicit n: Numer[A], eA: Elem[A]): Rep[PlusMonoid[A]] = mkPlusMonoid()
        };
        object PlusMonoidMatcher {
          def unapply[A](p: Rep[NumMonoid[A]]) = unmkPlusMonoid(p)
        };
        def PlusMonoid: Rep[PlusMonoidCompanionAbs];
        implicit def proxyPlusMonoidCompanion(p: Rep[PlusMonoidCompanionAbs]): PlusMonoidCompanionAbs = proxyOps[PlusMonoidCompanionAbs](p);
        implicit case object PlusMonoidCompanionElem extends CompanionElem[PlusMonoidCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[PlusMonoidCompanionAbs];
          protected def getDefaultRep = PlusMonoid
        };
        implicit def proxyPlusMonoid[A](p: Rep[PlusMonoid[A]]): PlusMonoid[A] = proxyOps[PlusMonoid[A]](p);
        implicit class ExtendedPlusMonoid[A](p: Rep[PlusMonoid[A]])(implicit n: Numer[A], eA: Elem[A]) {
          def toData: Rep[PlusMonoidData[A]] = isoPlusMonoid(n, eA).from(p)
        };
        implicit def isoPlusMonoid[A](implicit n: Numer[A], eA: Elem[A]): Iso[PlusMonoidData[A], PlusMonoid[A]] = new PlusMonoidIso[A]();
        def mkPlusMonoid[A]()(implicit n: Numer[A], eA: Elem[A]): Rep[PlusMonoid[A]];
        def unmkPlusMonoid[A](p: Rep[NumMonoid[A]]): Option[Rep[Unit]]
      };
      trait NumMonoidsSeq extends NumMonoidsDsl with ScalanSeq { self: NumMonoidsDslSeq =>
        lazy val NumMonoid: Rep[NumMonoidCompanionAbs] = {
          final class $anon extends NumMonoidCompanionAbs with UserTypeSeq[NumMonoidCompanionAbs] {
            lazy val selfType = element[NumMonoidCompanionAbs]
          };
          new $anon()
        };
        case class SeqPlusMonoid[A](implicit override val n: Numer[A], override val eA: Elem[A]) extends PlusMonoid[A]() with UserTypeSeq[PlusMonoid[A]] {
          lazy val selfType = element[PlusMonoid[A]]
        };
        lazy val PlusMonoid = {
          final class $anon extends PlusMonoidCompanionAbs with UserTypeSeq[PlusMonoidCompanionAbs] {
            lazy val selfType = element[PlusMonoidCompanionAbs]
          };
          new $anon()
        };
        def mkPlusMonoid[A]()(implicit n: Numer[A], eA: Elem[A]): Rep[PlusMonoid[A]] = new SeqPlusMonoid[A]();
        def unmkPlusMonoid[A](p: Rep[NumMonoid[A]]) = p match {
          case (p @ ((_): PlusMonoid[A] @unchecked)) => Some(())
          case _ => None
        }
      };
      trait NumMonoidsExp extends NumMonoidsDsl with ScalanExp { self: NumMonoidsDslExp =>
        lazy val NumMonoid: Rep[NumMonoidCompanionAbs] = {
          final class $anon extends NumMonoidCompanionAbs with UserTypeDef[NumMonoidCompanionAbs] {
            lazy val selfType = element[NumMonoidCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        case class ExpPlusMonoid[A](implicit override val n: Numer[A], override val eA: Elem[A]) extends PlusMonoid[A]() with UserTypeDef[PlusMonoid[A]] {
          lazy val selfType = element[PlusMonoid[A]];
          override def mirror(t: Transformer) = ExpPlusMonoid[A]()
        };
        lazy val PlusMonoid: Rep[PlusMonoidCompanionAbs] = {
          final class $anon extends PlusMonoidCompanionAbs with UserTypeDef[PlusMonoidCompanionAbs] {
            lazy val selfType = element[PlusMonoidCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object PlusMonoidMethods {
          object opName {
            def unapply(d: (Def[_$2] forSome { 
              type _$2
            })): Option[(Rep[PlusMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PlusMonoidElem[_$3] forSome { 
  type _$3
})].&&(method.getName.==("opName")) => Some(receiver).asInstanceOf[Option[(Rep[PlusMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$4] forSome { 
              type _$4
            })): Option[(Rep[PlusMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object zero {
            def unapply(d: (Def[_$5] forSome { 
              type _$5
            })): Option[(Rep[PlusMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PlusMonoidElem[_$6] forSome { 
  type _$6
})].&&(method.getName.==("zero")) => Some(receiver).asInstanceOf[Option[(Rep[PlusMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$7] forSome { 
              type _$7
            })): Option[(Rep[PlusMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object append {
            def unapply(d: (Def[_$8] forSome { 
              type _$8
            })): Option[(Rep[PlusMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PlusMonoidElem[_$9] forSome { 
  type _$9
})].&&(method.getName.==("append")) => Some(receiver).asInstanceOf[Option[(Rep[PlusMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$10] forSome { 
              type _$10
            })): Option[(Rep[PlusMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object isCommutative {
            def unapply(d: (Def[_$11] forSome { 
              type _$11
            })): Option[(Rep[PlusMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PlusMonoidElem[_$12] forSome { 
  type _$12
})].&&(method.getName.==("isCommutative")) => Some(receiver).asInstanceOf[Option[(Rep[PlusMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$13] forSome { 
              type _$13
            })): Option[(Rep[PlusMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object PlusMonoidCompanionMethods;
        def mkPlusMonoid[A]()(implicit n: Numer[A], eA: Elem[A]): Rep[PlusMonoid[A]] = new ExpPlusMonoid[A]();
        def unmkPlusMonoid[A](p: Rep[NumMonoid[A]]) = p.elem.asInstanceOf[(Elem[_$14] forSome { 
          type _$14
        })] match {
          case ((_): PlusMonoidElem[A] @unchecked) => Some(())
          case _ => None
        };
        object NumMonoidMethods {
          object opName {
            def unapply(d: (Def[_$15] forSome { 
              type _$15
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$16, _$17] forSome { 
  type _$16;
  type _$17
})].&&(method.getName.==("opName")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$18] forSome { 
              type _$18
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object zero {
            def unapply(d: (Def[_$19] forSome { 
              type _$19
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$20, _$21] forSome { 
  type _$20;
  type _$21
})].&&(method.getName.==("zero")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$22] forSome { 
              type _$22
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object append {
            def unapply(d: (Def[_$23] forSome { 
              type _$23
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$24, _$25] forSome { 
  type _$24;
  type _$25
})].&&(method.getName.==("append")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$26] forSome { 
              type _$26
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object isCommutative {
            def unapply(d: (Def[_$27] forSome { 
              type _$27
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$28, _$29] forSome { 
  type _$28;
  type _$29
})].&&(method.getName.==("isCommutative")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$30] forSome { 
              type _$30
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object NumMonoidCompanionMethods
      };
      trait NumMonoids extends Base with NumersDsl { self: NumMonoidsDsl =>
        trait NumMonoid[A] extends Reifiable[NumMonoid[A]] {
          implicit def n: Numer[A]
          implicit def eA: Elem[A];
          def opName: Rep[String];
          def zero: Rep[A];
          def append: Rep[scala.Function1[scala.Tuple2[A, A], A]];
          def isCommutative: Rep[Boolean]
        };
        abstract class PlusMonoid[A](implicit val n: Numer[A], val eA: Elem[A]) extends NumMonoid[A] with Product with Serializable {
          def opName = toRep("+");
          def zero = n.zero;
          def append = fun(((in: Rep[scala.Tuple2[A, A]]) => {
            val a0: Rep[A] = in._1;
            val a1: Rep[A] = in._2;
            n.plus(a0, a1)
          }));
          def isCommutative: Rep[Boolean] = toRep(true)
        };
        trait NumMonoidCompanion;
        trait PlusMonoidCompanion
      };
      trait NumMonoidsDsl extends NumMonoidsAbs with NumersDsl { self: NumMonoidsDsl =>
        
      };
      trait NumMonoidsDslSeq extends NumMonoidsSeq with NumersDslSeq { self: NumMonoidsDslSeq =>
        
      };
      trait NumMonoidsDslExp extends NumMonoidsExp with NumersDslExp { self: NumMonoidsDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0Q2FsbFDrGS0l7EVYAgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQABk51bWVyc3NxAH4ABnNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AA14cQB+AAtzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4AEEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcQB+AAh0AAlOdW1Nb25vaWRzcQB+AAZzcQB+AAh0AAFBcQB+AAtxAH4ADXhzcQB+AAh0AAdQcm9kdWN0cQB+AAtzcQB+AAh0AAxTZXJpYWxpemFibGVxAH4AC3EAfgANeHEAfgALc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NBcmdzJ+vuZMUwAFwCAAFMAARhcmdzcQB+AAF4cHEAfgALc3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kRGVm3VlxcbcwKw4CAApaAAxpc0VsZW1PckNvbnRaAAppc0ltcGxpY2l0WgAKaXNPdmVycmlkZUwAC2Fubm90YXRpb25zcQB+AAFMAAthcmdTZWN0aW9uc3EAfgABTAAEYm9keXEAfgADTAAEbmFtZXEAfgAETAAKb3ZlcmxvYWRJZHEAfgADTAAHdHBlQXJnc3EAfgABTAAGdHBlUmVzcQB+AAN4cAAAAHEAfgALcQB+AAtzcgAKc2NhbGEuU29tZREi8mleoYt0AgABTAABeHQAEkxqYXZhL2xhbmcvT2JqZWN0O3hyAAxzY2FsYS5PcHRpb27+aTf92w5mdAIAAHhwc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ29uc3QaTRTRYktnigIAAUwAAWNxAH4AInhwdAABK3QABm9wTmFtZXNyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHEAfgAjcQB+AAtxAH4AKnNxAH4AHwAAAHEAfgALcQB+AAtzcQB+ACFzcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxlY3QL3WllZUD5hQIAAkwABGV4cHJ0AB1Mc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNFeHByO0wABXRuYW1lcQB+AAR4cHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0lkZW50JxKl+/AJ108CAAFMAARuYW1lcQB+AAR4cHQAAW50AAR6ZXJvcQB+ADNxAH4AKnEAfgALcQB+ACpzcQB+AB8AAABxAH4AC3EAfgALc3EAfgAhc3IAG3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTRnVuY7BM+JAaxTCDAgACTAAGcGFyYW1zcQB+AAFMAANyZXNxAH4ALnhwc3EAfgAGc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVmFsRGVmKwY5unvdRQ8CAAVaAAppc0ltcGxpY2l0WgAGaXNMYXp5TAAEZXhwcnEAfgAuTAAEbmFtZXEAfgAETAADdHBlcQB+AAN4cAAAc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTRW1wdHmRYBXhS+VovgIAAHhwdAACYTBzcQB+ACFzcQB+AAh0AAFBcQB+AAtzcQB+ADkAAHNxAH4AO3QAAmExc3EAfgAhc3EAfgAIdAABQXEAfgALcQB+AA14c3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQXBwbHmEZimf0pr8fwIAA0wABWFyZ3NzcQB+AAFMAANmdW5xAH4ALkwAAnRzcQB+AAF4cHNxAH4ABnNxAH4ABnNxAH4AMHQAAmEwc3EAfgAwdAACYTFxAH4ADXhxAH4ADXhzcQB+AC1zcQB+ADB0AAFudAAEcGx1c3EAfgALdAAGYXBwZW5kcQB+ACpxAH4AC3EAfgAqc3EAfgAfAAAAcQB+AAtxAH4AC3NxAH4AIXNxAH4AJXNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAF0AA1pc0NvbW11dGF0aXZlcQB+ACpxAH4AC3NxAH4AIXNyACNzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZVByaW1pdGl2ZTXDu1Xwvk4FAgACTAASZGVmYXVsdFZhbHVlU3RyaW5ncQB+AARMAARuYW1lcQB+AAR4cHQABWZhbHNldAAHQm9vbGVhbnEAfgANeHEAfgAqc3EAfgAcc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NBcmcFc9jWC9QpPwIACFoAB2ltcEZsYWdaAAxpc0VsZW1PckNvbnRaAAhvdmVyRmxhZ1oAB3ZhbEZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBldAAgTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTVHBlRXhwcjt4cAEAAAFxAH4AC3EAfgAqdAABbnNxAH4ACHQABU51bWVyc3EAfgAGc3EAfgAIdAABQXEAfgALcQB+AA14cQB+AA14dAAKUGx1c01vbm9pZHEAfgAqc3EAfgAGc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlQXJnklsmSHLxm4kCAARMAAVib3VuZHEAfgADTAAMY29udGV4dEJvdW5kcQB+AAFMAARuYW1lcQB+AARMAAd0cGFyYW1zcQB+AAF4cHEAfgAqcQB+AAt0AAFBcQB+AAtxAH4ADXhxAH4ADXhzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdERlZgG+NJI0FLGyAgAJWgAIYml0bWFwJDBMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAQTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgALcQB+AAtzcQB+AAZzcQB+AB8AAABxAH4AC3EAfgALcQB+ACp0AAZvcE5hbWVxAH4AKnEAfgALc3EAfgAhc3EAfgBbdAACIiJ0AAZTdHJpbmdzcQB+AB8AAABxAH4AC3EAfgALcQB+ACpxAH4AM3EAfgAqcQB+AAtzcQB+ACFzcQB+AAh0AAFBcQB+AAtzcQB+AB8AAABxAH4AC3EAfgALcQB+ACp0AAZhcHBlbmRxAH4AKnEAfgALc3EAfgAhc3IAHnNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlRnVuY9RJmRMPHV/hAgACTAAGZG9tYWlucQB+AGJMAAVyYW5nZXEAfgBieHBzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVUdXBsZTS7+V6IgEhwAgABTAAJdHBlU0V4cHJzcQB+AAF4cHNxAH4ABnNxAH4ACHQAAUFxAH4AC3NxAH4ACHQAAUFxAH4AC3EAfgANeHNxAH4ACHQAAUFxAH4AC3NxAH4AHwAAAHEAfgALcQB+AAtxAH4AKnQADWlzQ29tbXV0YXRpdmVxAH4AKnEAfgALc3EAfgAhcQB+AFxxAH4ADXhxAH4AKnB0AAlOdW1Nb25vaWRxAH4AKnNxAH4ABnNxAH4AbHEAfgAqcQB+AAt0AAFBcQB+AAtxAH4ADXhxAH4ADXhxAH4AcXEAfgAqcQB+AAtxAH4AC3QACk51bU1vbm9pZHN0AAhwYXJhZGlzZXEAfgAqcQB+ACo="
    }
  }
}