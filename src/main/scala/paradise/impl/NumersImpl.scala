package paradise {
  package implOfNumers {
    object StagedEvaluation {
      import scalan._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait NumersAbs extends Numers with ScalanDsl { self: NumersDsl =>
        implicit def proxyNumer[T](p: Rep[Numer[T]]): Numer[T] = proxyOps[Numer[T]](p)(classTag[Numer[T]]);
        class NumerElem[T, To <: Numer[T]](implicit val eT: Elem[T]) extends EntityElem[To] {
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagT = eT.tag;
            weakTypeTag[Numer[T]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$1] forSome { 
            type _$1
          })]) = {
            val conv = fun(((x: Rep[Numer[T]]) => convertNumer(x)));
            tryConvert(element[Numer[T]], this, x, conv)
          };
          def convertNumer(x: Rep[Numer[T]]): Rep[To] = {
            assert(x.selfType1 match {
              case ((_): NumerElem[(_), (_)]) => true
              case _ => false
            });
            x.asRep[To]
          };
          override def getDefaultRep: Rep[To] = ???
        };
        implicit def numerElement[T](implicit eT: Elem[T]): Elem[Numer[T]] = new NumerElem[T, Numer[T]]();
        implicit case object NumerCompanionElem extends CompanionElem[NumerCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[NumerCompanionAbs];
          protected def getDefaultRep = Numer
        };
        abstract class NumerCompanionAbs extends CompanionBase[NumerCompanionAbs] with NumerCompanion {
          override def toString = "Numer"
        };
        def Numer: Rep[NumerCompanionAbs];
        implicit def proxyNumerCompanion(p: Rep[NumerCompanion]): NumerCompanion = proxyOps[NumerCompanion](p);
        class DoubleNumerElem(val iso: Iso[DoubleNumerData, DoubleNumer]) extends NumerElem[Double, DoubleNumer] with ConcreteElem[DoubleNumerData, DoubleNumer] {
          override def convertNumer(x: Rep[Numer[Double]]) = DoubleNumer();
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = weakTypeTag[DoubleNumer]
        };
        type DoubleNumerData = Unit;
        class DoubleNumerIso extends Iso[DoubleNumerData, DoubleNumer] {
          override def from(p: Rep[DoubleNumer]) = ();
          override def to(p: Rep[Unit]) = {
            val unit = p;
            DoubleNumer()
          };
          lazy val defaultRepTo = Default.defaultVal[Rep[DoubleNumer]](DoubleNumer());
          lazy val eTo = new DoubleNumerElem(this)
        };
        abstract class DoubleNumerCompanionAbs extends CompanionBase[DoubleNumerCompanionAbs] with DoubleNumerCompanion {
          override def toString = "DoubleNumer";
          def apply(p: Rep[DoubleNumerData]): Rep[DoubleNumer] = isoDoubleNumer.to(p);
          def apply(): Rep[DoubleNumer] = mkDoubleNumer()
        };
        object DoubleNumerMatcher {
          def unapply(p: Rep[Numer[Double]]) = unmkDoubleNumer(p)
        };
        def DoubleNumer: Rep[DoubleNumerCompanionAbs];
        implicit def proxyDoubleNumerCompanion(p: Rep[DoubleNumerCompanionAbs]): DoubleNumerCompanionAbs = proxyOps[DoubleNumerCompanionAbs](p);
        implicit case object DoubleNumerCompanionElem extends CompanionElem[DoubleNumerCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[DoubleNumerCompanionAbs];
          protected def getDefaultRep = DoubleNumer
        };
        implicit def proxyDoubleNumer(p: Rep[DoubleNumer]): DoubleNumer = proxyOps[DoubleNumer](p);
        implicit class ExtendedDoubleNumer(p: Rep[DoubleNumer]) {
          def toData: Rep[DoubleNumerData] = isoDoubleNumer.from(p)
        };
        implicit def isoDoubleNumer: Iso[DoubleNumerData, DoubleNumer] = new DoubleNumerIso();
        def mkDoubleNumer(): Rep[DoubleNumer];
        def unmkDoubleNumer(p: Rep[Numer[Double]]): Option[Rep[Unit]]
      };
      trait NumersSeq extends NumersDsl with ScalanSeq { self: NumersDslSeq =>
        lazy val Numer: Rep[NumerCompanionAbs] = {
          final class $anon extends NumerCompanionAbs with UserTypeSeq[NumerCompanionAbs] {
            lazy val selfType = element[NumerCompanionAbs]
          };
          new $anon()
        };
        case class SeqDoubleNumer() extends DoubleNumer() with UserTypeSeq[DoubleNumer] {
          lazy val selfType = element[DoubleNumer]
        };
        lazy val DoubleNumer = {
          final class $anon extends DoubleNumerCompanionAbs with UserTypeSeq[DoubleNumerCompanionAbs] {
            lazy val selfType = element[DoubleNumerCompanionAbs]
          };
          new $anon()
        };
        def mkDoubleNumer(): Rep[DoubleNumer] = new SeqDoubleNumer();
        def unmkDoubleNumer(p: Rep[Numer[Double]]) = p match {
          case (p @ ((_): DoubleNumer @unchecked)) => Some(())
          case _ => None
        }
      };
      trait NumersExp extends NumersDsl with ScalanExp { self: NumersDslExp =>
        lazy val Numer: Rep[NumerCompanionAbs] = {
          final class $anon extends NumerCompanionAbs with UserTypeDef[NumerCompanionAbs] {
            lazy val selfType = element[NumerCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        case class ExpDoubleNumer() extends DoubleNumer() with UserTypeDef[DoubleNumer] {
          lazy val selfType = element[DoubleNumer];
          override def mirror(t: Transformer) = ExpDoubleNumer()
        };
        lazy val DoubleNumer: Rep[DoubleNumerCompanionAbs] = {
          final class $anon extends DoubleNumerCompanionAbs with UserTypeDef[DoubleNumerCompanionAbs] {
            lazy val selfType = element[DoubleNumerCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object DoubleNumerMethods {
          object zero {
            def unapply(d: (Def[_$2] forSome { 
              type _$2
            })): Option[Rep[DoubleNumer]] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[DoubleNumerElem].&&(method.getName.==("zero")) => Some(receiver).asInstanceOf[Option[Rep[DoubleNumer]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$3] forSome { 
              type _$3
            })): Option[Rep[DoubleNumer]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object one {
            def unapply(d: (Def[_$4] forSome { 
              type _$4
            })): Option[Rep[DoubleNumer]] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[DoubleNumerElem].&&(method.getName.==("one")) => Some(receiver).asInstanceOf[Option[Rep[DoubleNumer]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$5] forSome { 
              type _$5
            })): Option[Rep[DoubleNumer]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object plus {
            def unapply(d: (Def[_$6] forSome { 
              type _$6
            })): Option[scala.Tuple3[Rep[DoubleNumer], Rep[Double], Rep[Double]]] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (b @ _), _*), _) if receiver.elem.isInstanceOf[DoubleNumerElem].&&(method.getName.==("plus")) => Some(scala.Tuple3(receiver, a, b)).asInstanceOf[Option[scala.Tuple3[Rep[DoubleNumer], Rep[Double], Rep[Double]]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$7] forSome { 
              type _$7
            })): Option[scala.Tuple3[Rep[DoubleNumer], Rep[Double], Rep[Double]]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object times {
            def unapply(d: (Def[_$8] forSome { 
              type _$8
            })): Option[scala.Tuple3[Rep[DoubleNumer], Rep[Double], Rep[Double]]] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (b @ _), _*), _) if receiver.elem.isInstanceOf[DoubleNumerElem].&&(method.getName.==("times")) => Some(scala.Tuple3(receiver, a, b)).asInstanceOf[Option[scala.Tuple3[Rep[DoubleNumer], Rep[Double], Rep[Double]]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$9] forSome { 
              type _$9
            })): Option[scala.Tuple3[Rep[DoubleNumer], Rep[Double], Rep[Double]]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object DoubleNumerCompanionMethods;
        def mkDoubleNumer(): Rep[DoubleNumer] = new ExpDoubleNumer();
        def unmkDoubleNumer(p: Rep[Numer[Double]]) = p.elem.asInstanceOf[(Elem[_$10] forSome { 
          type _$10
        })] match {
          case ((_): DoubleNumerElem @unchecked) => Some(())
          case _ => None
        };
        object NumerMethods {
          object zero {
            def unapply(d: (Def[_$11] forSome { 
              type _$11
            })): Option[(Rep[Numer[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumerElem[_$12, _$13] forSome { 
  type _$12;
  type _$13
})].&&(method.getName.==("zero")) => Some(receiver).asInstanceOf[Option[(Rep[Numer[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$14] forSome { 
              type _$14
            })): Option[(Rep[Numer[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object one {
            def unapply(d: (Def[_$15] forSome { 
              type _$15
            })): Option[(Rep[Numer[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumerElem[_$16, _$17] forSome { 
  type _$16;
  type _$17
})].&&(method.getName.==("one")) => Some(receiver).asInstanceOf[Option[(Rep[Numer[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$18] forSome { 
              type _$18
            })): Option[(Rep[Numer[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object plus {
            def unapply(d: (Def[_$19] forSome { 
              type _$19
            })): Option[(scala.Tuple3[Rep[Numer[T]], Rep[T], Rep[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (b @ _), _*), _) if receiver.elem.isInstanceOf[(NumerElem[_$20, _$21] forSome { 
  type _$20;
  type _$21
})].&&(method.getName.==("plus")) => Some(scala.Tuple3(receiver, a, b)).asInstanceOf[Option[(scala.Tuple3[Rep[Numer[T]], Rep[T], Rep[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$22] forSome { 
              type _$22
            })): Option[(scala.Tuple3[Rep[Numer[T]], Rep[T], Rep[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object times {
            def unapply(d: (Def[_$23] forSome { 
              type _$23
            })): Option[(scala.Tuple3[Rep[Numer[T]], Rep[T], Rep[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (b @ _), _*), _) if receiver.elem.isInstanceOf[(NumerElem[_$24, _$25] forSome { 
  type _$24;
  type _$25
})].&&(method.getName.==("times")) => Some(scala.Tuple3(receiver, a, b)).asInstanceOf[Option[(scala.Tuple3[Rep[Numer[T]], Rep[T], Rep[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$26] forSome { 
              type _$26
            })): Option[(scala.Tuple3[Rep[Numer[T]], Rep[T], Rep[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object NumerCompanionMethods
      };
      trait Numers extends Base { self: NumersDsl =>
        trait Numer[T] extends Reifiable[Numer[T]] {
          implicit def eT: Elem[T];
          def zero: Rep[T];
          def one: Rep[T];
          def plus(a: Rep[T], b: Rep[T]): Rep[T];
          def times(a: Rep[T], b: Rep[T]): Rep[T]
        };
        abstract class DoubleNumer() extends Numer[Double] with Product with Serializable {
          def eT = element[Double]
          def zero: Rep[Double] = toRep(0.0);
          def one: Rep[Double] = toRep(1.0);
          def plus(a: Rep[Double], b: Rep[Double]) = a.+(b);
          def times(a: Rep[Double], b: Rep[Double]) = a.*(b)
        };
        trait NumerCompanion;
        trait DoubleNumerCompanion
      };
      trait NumersDsl extends NumersAbs { self: NumersDsl =>
        
      };
      trait NumersDslSeq extends NumersSeq { self: NumersDslSeq =>
        
      };
      trait NumersDslExp extends NumersExp { self: NumersDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AAVOdW1lcnNxAH4ABnNyACNzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZVByaW1pdGl2ZTXDu1Xwvk4FAgACTAASZGVmYXVsdFZhbHVlU3RyaW5ncQB+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AH4AB3EAfgAHc3EAfgAGc3EAfgAeAAAAcQB+AAdxAH4AB3EAfgArcQB+AClxAH4AK3EAfgAHc3EAfgAgc3EAfgAPdAABVHEAfgAHc3EAfgAeAAAAcQB+AAdxAH4AB3EAfgArdAADb25lcQB+ACtxAH4AB3NxAH4AIHNxAH4AD3QAAVRxAH4AB3NxAH4AHgAAAHEAfgAHc3EAfgAGc3EAfgA1c3EAfgAGc3EAfgA4AAAAcQB+AAdxAH4AK3QAAWFzcQB+AA90AAFUcQB+AAdzcQB+ADgAAABxAH4AB3EAfgArdAABYnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgArdAAEcGx1c3EAfgArcQB+AAdzcQB+ACBzcQB+AA90AAFUcQB+AAdzcQB+AB4AAABxAH4AB3NxAH4ABnNxAH4ANXNxAH4ABnNxAH4AOAAAAHEAfgAHcQB+ACt0AAFhc3EAfgAPdAABVHEAfgAHc3EAfgA4AAAAcQB+AAdxAH4AK3QAAWJzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4AK3QABXRpbWVzcQB+ACtxAH4AB3NxAH4AIHNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgArcHQABU51bWVycQB+ACtzcQB+AAZzcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVBcmeSWyZIcvGbiQIABEwABWJvdW5kcQB+AANMAAxjb250ZXh0Qm91bmRxAH4AAUwABG5hbWVxAH4ABEwAB3RwYXJhbXNxAH4AAXhwcQB+ACtxAH4AB3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgBkcQB+ACtxAH4AB3EAfgAHdAAGTnVtZXJzdAAIcGFyYWRpc2VxAH4AK3EAfgAr"
    }
  }
}