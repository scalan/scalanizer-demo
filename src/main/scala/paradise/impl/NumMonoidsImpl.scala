package paradise {
  package implOfNumMonoids {
    object StagedEvaluation {
      import scalan._;
      import paradise.implOfNumers.StagedEvaluation._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait NumMonoidsAbs extends NumMonoids with ScalanDsl { self: NumMonoidsDsl =>
        implicit def proxyNumMonoid[A](p: Rep[NumMonoid[A]]): NumMonoid[A] = proxyOps[NumMonoid[A]](p)(scala.reflect.classTag[NumMonoid[A]]);
        class NumMonoidElem[A, To <: NumMonoid[A]](implicit val eeA: Elem[A]) extends EntityElem[To] {
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagA = eeA.tag;
            weakTypeTag[NumMonoid[A]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$1] forSome { 
            type _$1
          })]) = {
            implicit val eTo: Elem[To] = this;
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
        implicit def numMonoidElement[A](implicit eeA: Elem[A]): Elem[NumMonoid[A]] = new NumMonoidElem[A, NumMonoid[A]]();
        implicit case object NumMonoidCompanionElem extends CompanionElem[NumMonoidCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[NumMonoidCompanionAbs];
          protected def getDefaultRep = NumMonoid
        };
        abstract class NumMonoidCompanionAbs extends CompanionBase[NumMonoidCompanionAbs] with NumMonoidCompanion {
          override def toString = "NumMonoid"
        };
        def NumMonoid: Rep[NumMonoidCompanionAbs];
        implicit def proxyNumMonoidCompanion(p: Rep[NumMonoidCompanion]): NumMonoidCompanion = proxyOps[NumMonoidCompanion](p);
        class PlusMonoidElem[A](val iso: Iso[PlusMonoidData[A], PlusMonoid[A]])(implicit eeA: Elem[A]) extends NumMonoidElem[A, PlusMonoid[A]] with ConcreteElem[PlusMonoidData[A], PlusMonoid[A]] {
          override def convertNumMonoid(x: Rep[NumMonoid[A]]) = PlusMonoid(x.n);
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = {
            implicit val tagA = eeA.tag;
            weakTypeTag[PlusMonoid[A]]
          }
        };
        type PlusMonoidData[A] = Numer[A];
        class PlusMonoidIso[A](implicit eeA: Elem[A]) extends Iso[PlusMonoidData[A], PlusMonoid[A]] {
          override def from(p: Rep[PlusMonoid[A]]) = p.n;
          override def to(p: Rep[Numer[A]]) = {
            val n = p;
            PlusMonoid(n)
          };
          lazy val defaultRepTo: Rep[PlusMonoid[A]] = PlusMonoid(element[Numer[A]].defaultRepValue);
          lazy val eTo = new PlusMonoidElem[A](this)
        };
        abstract class PlusMonoidCompanionAbs extends CompanionBase[PlusMonoidCompanionAbs] with PlusMonoidCompanion {
          override def toString = "PlusMonoid";
          def apply[A](n: Rep[Numer[A]])(implicit eeA: Elem[A]): Rep[PlusMonoid[A]] = mkPlusMonoid(n)
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
        implicit class ExtendedPlusMonoid[A](p: Rep[PlusMonoid[A]])(implicit eeA: Elem[A]) {
          def toData: Rep[PlusMonoidData[A]] = isoPlusMonoid(eeA).from(p)
        };
        implicit def isoPlusMonoid[A](implicit eeA: Elem[A]): Iso[PlusMonoidData[A], PlusMonoid[A]] = new PlusMonoidIso[A]();
        def mkPlusMonoid[A](n: Rep[Numer[A]])(implicit eeA: Elem[A]): Rep[PlusMonoid[A]];
        def unmkPlusMonoid[A](p: Rep[NumMonoid[A]]): Option[Rep[Numer[A]]]
      };
      trait NumMonoidsSeq extends NumMonoidsDsl with ScalanSeq { self: NumMonoidsDslSeq =>
        lazy val NumMonoid: Rep[NumMonoidCompanionAbs] = {
          final class $anon extends NumMonoidCompanionAbs with UserTypeSeq[NumMonoidCompanionAbs] {
            lazy val selfType = element[NumMonoidCompanionAbs]
          };
          new $anon()
        };
        case class SeqPlusMonoid[A](override val n: Rep[Numer[A]])(implicit eeA: Elem[A]) extends PlusMonoid[A](n) with UserTypeSeq[PlusMonoid[A]] {
          lazy val selfType = element[PlusMonoid[A]]
        };
        lazy val PlusMonoid = {
          final class $anon extends PlusMonoidCompanionAbs with UserTypeSeq[PlusMonoidCompanionAbs] {
            lazy val selfType = element[PlusMonoidCompanionAbs]
          };
          new $anon()
        };
        def mkPlusMonoid[A](n: Rep[Numer[A]])(implicit eeA: Elem[A]): Rep[PlusMonoid[A]] = new SeqPlusMonoid[A](n);
        def unmkPlusMonoid[A](p: Rep[NumMonoid[A]]) = p match {
          case (p @ ((_): PlusMonoid[A] @unchecked)) => Some(p.n)
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
        case class ExpPlusMonoid[A](override val n: Rep[Numer[A]])(implicit eeA: Elem[A]) extends PlusMonoid[A](n) with UserTypeDef[PlusMonoid[A]] {
          lazy val selfType = element[PlusMonoid[A]];
          override def mirror(t: Transformer) = ExpPlusMonoid[A](t(n))
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
        def mkPlusMonoid[A](n: Rep[Numer[A]])(implicit eeA: Elem[A]): Rep[PlusMonoid[A]] = new ExpPlusMonoid[A](n);
        def unmkPlusMonoid[A](p: Rep[NumMonoid[A]]) = p.elem.asInstanceOf[(Elem[_$14] forSome { 
          type _$14
        })] match {
          case ((_): PlusMonoidElem[A] @unchecked) => Some(p.asRep[PlusMonoid[A]].n)
          case _ => None
        };
        object NumMonoidMethods {
          object n {
            def unapply(d: (Def[_$15] forSome { 
              type _$15
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$16, _$17] forSome { 
  type _$16;
  type _$17
})].&&(method.getName.==("n")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome { 
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
          object opName {
            def unapply(d: (Def[_$19] forSome { 
              type _$19
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$20, _$21] forSome { 
  type _$20;
  type _$21
})].&&(method.getName.==("opName")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome { 
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
          object zero {
            def unapply(d: (Def[_$23] forSome { 
              type _$23
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$24, _$25] forSome { 
  type _$24;
  type _$25
})].&&(method.getName.==("zero")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome { 
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
          object append {
            def unapply(d: (Def[_$27] forSome { 
              type _$27
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$28, _$29] forSome { 
  type _$28;
  type _$29
})].&&(method.getName.==("append")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome { 
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
          };
          object isCommutative {
            def unapply(d: (Def[_$31] forSome { 
              type _$31
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$32, _$33] forSome { 
  type _$32;
  type _$33
})].&&(method.getName.==("isCommutative")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$34] forSome { 
              type _$34
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
          implicit def eeA: Elem[A];
          def n: Rep[Numer[A]];
          def opName: Rep[String];
          def zero: Rep[A];
          def append: Rep[scala.Function1[scala.Tuple2[A, A], A]];
          def isCommutative: Rep[Boolean]
        };
        abstract class PlusMonoid[A](val n: Rep[Numer[A]])(implicit val eeA: Elem[A]) extends NumMonoid[A] with Product with Serializable {
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
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0Q2FsbFDrGS0l7EVYAgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQABk51bWVyc3NxAH4ABnNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AA14cQB+AAtzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4AEEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcQB+AAh0AAlOdW1Nb25vaWRzcQB+AAZzcQB+AAh0AAFBcQB+AAtxAH4ADXhzcQB+AAh0AAdQcm9kdWN0cQB+AAtzcQB+AAh0AAxTZXJpYWxpemFibGVxAH4AC3EAfgANeHEAfgALc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NBcmdzJ+vuZMUwAFwCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzQXJnBXPY1gvUKT8CAAhaAAdpbXBGbGFnWgAMaXNFbGVtT3JDb250WgAIb3ZlckZsYWdaAAd2YWxGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXQAIExzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RwZUV4cHI7eHAAAAABcQB+AAtzcgALc2NhbGEuTm9uZSRGUCT2U8qUrAIAAHhyAAxzY2FsYS5PcHRpb27+aTf92w5mdAIAAHhwdAABbnNxAH4ACHQABU51bWVyc3EAfgAGc3EAfgAIdAABQXEAfgALcQB+AA14cQB+AA14c3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kRGVm3VlxcbcwKw4CAApaAAxpc0VsZW1PckNvbnRaAAppc0ltcGxpY2l0WgAKaXNPdmVycmlkZUwAC2Fubm90YXRpb25zcQB+AAFMAAthcmdTZWN0aW9uc3EAfgABTAAEYm9keXEAfgADTAAEbmFtZXEAfgAETAAKb3ZlcmxvYWRJZHEAfgADTAAHdHBlQXJnc3EAfgABTAAGdHBlUmVzcQB+AAN4cAAAAHEAfgALcQB+AAtzcgAKc2NhbGEuU29tZREi8mleoYt0AgABTAABeHQAEkxqYXZhL2xhbmcvT2JqZWN0O3hxAH4AI3NyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NvbnN0Gk0U0WJLZ4oCAAFMAAFjcQB+AC94cHQAASt0AAZvcE5hbWVxAH4AJHEAfgALcQB+ACRzcQB+ACwAAABxAH4AC3EAfgALc3EAfgAuc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTU2VsZWN0C91pZWVA+YUCAAJMAARleHBydAAdTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTRXhwcjtMAAV0bmFtZXEAfgAEeHBzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJZGVudCcSpfvwCddPAgABTAAEbmFtZXEAfgAEeHB0AAFudAAEemVyb3EAfgA9cQB+ACRxAH4AC3EAfgAkc3EAfgAsAAAAcQB+AAtxAH4AC3NxAH4ALnNyABtzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0Z1bmOwTPiQGsUwgwIAAkwABnBhcmFtc3EAfgABTAADcmVzcQB+ADh4cHNxAH4ABnNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1ZhbERlZisGObp73UUPAgAFWgAKaXNJbXBsaWNpdFoABmlzTGF6eUwABGV4cHJxAH4AOEwABG5hbWVxAH4ABEwAA3RwZXEAfgADeHAAAHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VtcHR5kWAV4UvlaL4CAAB4cHQAAmEwc3EAfgAuc3EAfgAIdAABQXEAfgALc3EAfgBDAABzcQB+AEV0AAJhMXNxAH4ALnNxAH4ACHQAAUFxAH4AC3EAfgANeHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0FwcGx5hGYpn9Ka/H8CAANMAAVhcmdzc3EAfgABTAADZnVucQB+ADhMAAJ0c3EAfgABeHBzcQB+AAZzcQB+AAZzcQB+ADp0AAJhMHNxAH4AOnQAAmExcQB+AA14cQB+AA14c3EAfgA3c3EAfgA6dAABbnQABHBsdXNxAH4AC3QABmFwcGVuZHEAfgAkcQB+AAtxAH4AJHNxAH4ALAAAAHEAfgALcQB+AAtzcQB+AC5zcQB+ADFzcgARamF2YS5sYW5nLkJvb2xlYW7NIHKA1Zz67gIAAVoABXZhbHVleHABdAANaXNDb21tdXRhdGl2ZXEAfgAkcQB+AAtzcQB+AC5zcgAjc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVQcmltaXRpdmU1w7tV8L5OBQIAAkwAEmRlZmF1bHRWYWx1ZVN0cmluZ3EAfgAETAAEbmFtZXEAfgAEeHB0AAVmYWxzZXQAB0Jvb2xlYW5xAH4ADXhxAH4AJHNxAH4AHHEAfgALdAAKUGx1c01vbm9pZHEAfgAkc3EAfgAGc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlQXJnklsmSHLxm4kCAARMAAVib3VuZHEAfgADTAAMY29udGV4dEJvdW5kcQB+AAFMAARuYW1lcQB+AARMAAd0cGFyYW1zcQB+AAF4cHEAfgAkcQB+AAt0AAFBcQB+AAtxAH4ADXhxAH4ADXhzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdERlZgG+NJI0FLGyAgAJWgAIYml0bWFwJDBMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAQTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgALcQB+AAtzcQB+AAZzcQB+ACwAAABxAH4AC3EAfgALcQB+ACR0AAFucQB+ACRxAH4AC3NxAH4ALnNxAH4ACHQABU51bWVyc3EAfgAGc3EAfgAIdAABQXEAfgALcQB+AA14c3EAfgAsAAAAcQB+AAtxAH4AC3EAfgAkdAAGb3BOYW1lcQB+ACRxAH4AC3NxAH4ALnNxAH4AZXQAAiIidAAGU3RyaW5nc3EAfgAsAAAAcQB+AAtxAH4AC3EAfgAkcQB+AD1xAH4AJHEAfgALc3EAfgAuc3EAfgAIdAABQXEAfgALc3EAfgAsAAAAcQB+AAtxAH4AC3EAfgAkdAAGYXBwZW5kcQB+ACRxAH4AC3NxAH4ALnNyAB5zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZUZ1bmPUSZkTDx1f4QIAAkwABmRvbWFpbnEAfgAgTAAFcmFuZ2VxAH4AIHhwc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlVHVwbGU0u/leiIBIcAIAAUwACXRwZVNFeHByc3EAfgABeHBzcQB+AAZzcQB+AAh0AAFBcQB+AAtzcQB+AAh0AAFBcQB+AAtxAH4ADXhzcQB+AAh0AAFBcQB+AAtzcQB+ACwAAABxAH4AC3EAfgALcQB+ACR0AA1pc0NvbW11dGF0aXZlcQB+ACRxAH4AC3NxAH4ALnEAfgBmcQB+AA14cQB+ACRwdAAJTnVtTW9ub2lkcQB+ACRzcQB+AAZzcQB+AGxxAH4AJHEAfgALdAABQXEAfgALcQB+AA14cQB+AA14cQB+AHFxAH4AJHEAfgALcQB+AAt0AApOdW1Nb25vaWRzdAAIcGFyYWRpc2VxAH4AJHEAfgAk"
    }

    object HotSpotKernels {
      import java.io.File;
      import scalan.compilation.GraphVizConfig
    }

    object HotSpotManager {
      import scalan.ScalanCommunityDslExp;
      import scalan.compilation.lms.{CommunityLmsBackend, CoreBridge};
      import scalan.compilation.lms.scalac.CommunityLmsCompilerScala;
      import scalan.primitives.EffectfulCompiler;
      import paradise.implOfNumMonoids.StagedEvaluation._;
      lazy val scalanContext = new Scalan();
      def getScalanContext = scalanContext;
      class Scalan extends NumMonoidsDslExp with CommunityLmsCompilerScala with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler {
        val lms = new CommunityLmsBackend()
      }
    }
  }
}