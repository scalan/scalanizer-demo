package scalanizer {
  package implOfNumMonoids {
    object StagedEvaluation {
      import scalan._;
      import scalanizer.implOfNums.StagedEvaluation._;
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
        type PlusMonoidData[A] = Num[A];
        class PlusMonoidIso[A](implicit eeA: Elem[A]) extends Iso[PlusMonoidData[A], PlusMonoid[A]] {
          override def from(p: Rep[PlusMonoid[A]]) = p.n;
          override def to(p: Rep[Num[A]]) = {
            val n = p;
            PlusMonoid(n)
          };
          lazy val defaultRepTo: Rep[PlusMonoid[A]] = PlusMonoid(element[Num[A]].defaultRepValue);
          lazy val eTo = new PlusMonoidElem[A](this)
        };
        abstract class PlusMonoidCompanionAbs extends CompanionBase[PlusMonoidCompanionAbs] with PlusMonoidCompanion {
          override def toString = "PlusMonoid";
          def apply[A](n: Rep[Num[A]])(implicit eeA: Elem[A]): Rep[PlusMonoid[A]] = mkPlusMonoid(n)
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
        def mkPlusMonoid[A](n: Rep[Num[A]])(implicit eeA: Elem[A]): Rep[PlusMonoid[A]];
        def unmkPlusMonoid[A](p: Rep[NumMonoid[A]]): Option[Rep[Num[A]]]
      };
      trait NumMonoidsSeq extends NumMonoidsDsl with ScalanSeq { self: NumMonoidsDslSeq =>
        lazy val NumMonoid: Rep[NumMonoidCompanionAbs] = {
          final class $anon extends NumMonoidCompanionAbs with UserTypeSeq[NumMonoidCompanionAbs] {
            lazy val selfType = element[NumMonoidCompanionAbs]
          };
          new $anon()
        };
        case class SeqPlusMonoid[A](override val n: Rep[Num[A]])(implicit eeA: Elem[A]) extends PlusMonoid[A](n) with UserTypeSeq[PlusMonoid[A]] {
          lazy val selfType = element[PlusMonoid[A]]
        };
        lazy val PlusMonoid = {
          final class $anon extends PlusMonoidCompanionAbs with UserTypeSeq[PlusMonoidCompanionAbs] {
            lazy val selfType = element[PlusMonoidCompanionAbs]
          };
          new $anon()
        };
        def mkPlusMonoid[A](n: Rep[Num[A]])(implicit eeA: Elem[A]): Rep[PlusMonoid[A]] = new SeqPlusMonoid[A](n);
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
        case class ExpPlusMonoid[A](override val n: Rep[Num[A]])(implicit eeA: Elem[A]) extends PlusMonoid[A](n) with UserTypeDef[PlusMonoid[A]] {
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
        def mkPlusMonoid[A](n: Rep[Num[A]])(implicit eeA: Elem[A]): Rep[PlusMonoid[A]] = new ExpPlusMonoid[A](n);
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
      trait NumMonoids extends Base with NumsDsl { self: NumMonoidsDsl =>
        trait NumMonoid[A] extends Reifiable[NumMonoid[A]] {
          implicit def eeA: Elem[A];
          def n: Rep[Num[A]];
          def opName: Rep[String];
          def zero: Rep[A];
          def append: Rep[scala.Function1[scala.Tuple2[A, A], A]];
          def isCommutative: Rep[Boolean]
        };
        abstract class PlusMonoid[A](val n: Rep[Num[A]])(implicit val eeA: Elem[A]) extends NumMonoid[A] with Product with Serializable {
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
      trait NumMonoidsDsl extends NumMonoidsAbs with NumsDsl { self: NumMonoidsDsl =>
        
      };
      trait NumMonoidsDslSeq extends NumMonoidsSeq with NumsDslSeq { self: NumMonoidsDslSeq =>
        
      };
      trait NumMonoidsDslExp extends NumMonoidsExp with NumsDslExp { self: NumMonoidsDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0Q2FsbFDrGS0l7EVYAgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQABE51bXNzcQB+AAZzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHEAfgANeHEAfgALc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NEZWYH0H1o8ahwMwIACloACmlzQWJzdHJhY3RMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARhcmdzdAAiTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTQ2xhc3NBcmdzO0wABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+ABBMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAc3EAfgAGc3EAfgAIdAAJTnVtTW9ub2lkc3EAfgAGc3EAfgAIdAABQXEAfgALcQB+AA14c3EAfgAIdAAHUHJvZHVjdHEAfgALc3EAfgAIdAAMU2VyaWFsaXphYmxlcQB+AAtxAH4ADXhxAH4AC3NyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzQXJncyfr7mTFMABcAgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZwVz2NYL1Ck/AgAIWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnWgAHdmFsRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGV0ACBMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcGVFeHByO3hwAAAAAXEAfgALc3IAC3NjYWxhLk5vbmUkRlAk9lPKlKwCAAB4cgAMc2NhbGEuT3B0aW9u/mk3/dsOZnQCAAB4cHQAAW5zcQB+AAh0AANOdW1zcQB+AAZzcQB+AAh0AAFBcQB+AAtxAH4ADXhxAH4ADXhzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2REZWbdWXFxtzArDgIACloADGlzRWxlbU9yQ29udFoACmlzSW1wbGljaXRaAAppc092ZXJyaWRlTAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0cGVSZXNxAH4AA3hwAAAAcQB+AAtxAH4AC3NyAApzY2FsYS5Tb21lESLyaV6hi3QCAAFMAAF4dAASTGphdmEvbGFuZy9PYmplY3Q7eHEAfgAjc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ29uc3QaTRTRYktnigIAAUwAAWNxAH4AL3hwdAABK3QABm9wTmFtZXEAfgAkcQB+AAtxAH4AJHNxAH4ALAAAAHEAfgALcQB+AAtzcQB+AC5zcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxlY3QL3WllZUD5hQIAAkwABGV4cHJ0AB1Mc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNFeHByO0wABXRuYW1lcQB+AAR4cHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0lkZW50JxKl+/AJ108CAAFMAARuYW1lcQB+AAR4cHQAAW50AAR6ZXJvcQB+AD1xAH4AJHEAfgALcQB+ACRzcQB+ACwAAABxAH4AC3EAfgALc3EAfgAuc3IAG3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTRnVuY7BM+JAaxTCDAgACTAAGcGFyYW1zcQB+AAFMAANyZXNxAH4AOHhwc3EAfgAGc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVmFsRGVmKwY5unvdRQ8CAAVaAAppc0ltcGxpY2l0WgAGaXNMYXp5TAAEZXhwcnEAfgA4TAAEbmFtZXEAfgAETAADdHBlcQB+AAN4cAAAc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTRW1wdHmRYBXhS+VovgIAAHhwdAACYTBzcQB+AC5zcQB+AAh0AAFBcQB+AAtzcQB+AEMAAHNxAH4ARXQAAmExc3EAfgAuc3EAfgAIdAABQXEAfgALcQB+AA14c3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQXBwbHmEZimf0pr8fwIAA0wABWFyZ3NzcQB+AAFMAANmdW5xAH4AOEwAAnRzcQB+AAF4cHNxAH4ABnNxAH4ABnNxAH4AOnQAAmEwc3EAfgA6dAACYTFxAH4ADXhxAH4ADXhzcQB+ADdzcQB+ADp0AAFudAAEcGx1c3EAfgALdAAGYXBwZW5kcQB+ACRxAH4AC3EAfgAkc3EAfgAsAAAAcQB+AAtxAH4AC3NxAH4ALnNxAH4AMXNyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAF0AA1pc0NvbW11dGF0aXZlcQB+ACRxAH4AC3NxAH4ALnNyACNzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZVByaW1pdGl2ZTXDu1Xwvk4FAgACTAASZGVmYXVsdFZhbHVlU3RyaW5ncQB+AARMAARuYW1lcQB+AAR4cHQABWZhbHNldAAHQm9vbGVhbnEAfgANeHEAfgAkc3EAfgAccQB+AAt0AApQbHVzTW9ub2lkcQB+ACRzcQB+AAZzcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVBcmeSWyZIcvGbiQIABEwABWJvdW5kcQB+AANMAAxjb250ZXh0Qm91bmRxAH4AAUwABG5hbWVxAH4ABEwAB3RwYXJhbXNxAH4AAXhwcQB+ACRxAH4AC3QAAUFxAH4AC3EAfgANeHEAfgANeHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0RGVmAb40kjQUsbICAAlaAAhiaXRtYXAkMEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+ABBMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAcQB+AAtxAH4AC3NxAH4ABnNxAH4ALAAAAHEAfgALcQB+AAtxAH4AJHQAAW5xAH4AJHEAfgALc3EAfgAuc3EAfgAIdAADTnVtc3EAfgAGc3EAfgAIdAABQXEAfgALcQB+AA14c3EAfgAsAAAAcQB+AAtxAH4AC3EAfgAkdAAGb3BOYW1lcQB+ACRxAH4AC3NxAH4ALnNxAH4AZXQAAiIidAAGU3RyaW5nc3EAfgAsAAAAcQB+AAtxAH4AC3EAfgAkcQB+AD1xAH4AJHEAfgALc3EAfgAuc3EAfgAIdAABQXEAfgALc3EAfgAsAAAAcQB+AAtxAH4AC3EAfgAkdAAGYXBwZW5kcQB+ACRxAH4AC3NxAH4ALnNyAB5zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZUZ1bmPUSZkTDx1f4QIAAkwABmRvbWFpbnEAfgAgTAAFcmFuZ2VxAH4AIHhwc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlVHVwbGU0u/leiIBIcAIAAUwACXRwZVNFeHByc3EAfgABeHBzcQB+AAZzcQB+AAh0AAFBcQB+AAtzcQB+AAh0AAFBcQB+AAtxAH4ADXhzcQB+AAh0AAFBcQB+AAtzcQB+ACwAAABxAH4AC3EAfgALcQB+ACR0AA1pc0NvbW11dGF0aXZlcQB+ACRxAH4AC3NxAH4ALnEAfgBmcQB+AA14cQB+ACRwdAAJTnVtTW9ub2lkcQB+ACRzcQB+AAZzcQB+AGxxAH4AJHEAfgALdAABQXEAfgALcQB+AA14cQB+AA14cQB+AHFxAH4AJHEAfgALcQB+AAt0AApOdW1Nb25vaWRzdAAKc2NhbGFuaXplcnEAfgAkcQB+ACQ="
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
      import scalanizer.implOfNumMonoids.StagedEvaluation._;
      lazy val scalanContext = new Scalan();
      def getScalanContext = scalanContext;
      class Scalan extends NumMonoidsDslExp with CommunityLmsCompilerScala with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler {
        val lms = new CommunityLmsBackend()
      };
      import scalan.CommunityMethodMappingDSL;
      import scalan.compilation.lms.uni.LmsCompilerUni;
      lazy val scalanContextUni = new ScalanUni();
      def getScalanContextUni = scalanContextUni;
      class ScalanUni extends NumMonoidsDslExp with LmsCompilerUni with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler with CommunityMethodMappingDSL {
        val lms = new CommunityLmsBackend()
      }
    }
  }
}