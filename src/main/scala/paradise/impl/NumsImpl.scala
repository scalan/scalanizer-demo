package scalanizer {
  package implOfNums {
    object StagedEvaluation {
      import scalan._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait NumsAbs extends Nums with ScalanDsl { self: NumsDsl =>
        implicit def proxyNum[T](p: Rep[Num[T]]): Num[T] = proxyOps[Num[T]](p)(scala.reflect.classTag[Num[T]]);
        class NumElem[T, To <: Num[T]](implicit val eeT: Elem[T]) extends EntityElem[To] {
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagT = eeT.tag;
            weakTypeTag[Num[T]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$1] forSome { 
            type _$1
          })]) = {
            implicit val eTo: Elem[To] = this;
            val conv = fun(((x: Rep[Num[T]]) => convertNum(x)));
            tryConvert(element[Num[T]], this, x, conv)
          };
          def convertNum(x: Rep[Num[T]]): Rep[To] = {
            assert(x.selfType1 match {
              case ((_): NumElem[(_), (_)]) => true
              case _ => false
            });
            x.asRep[To]
          };
          override def getDefaultRep: Rep[To] = ???
        };
        implicit def numElement[T](implicit eeT: Elem[T]): Elem[Num[T]] = new NumElem[T, Num[T]]();
        implicit case object NumCompanionElem extends CompanionElem[NumCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[NumCompanionAbs];
          protected def getDefaultRep = Num
        };
        abstract class NumCompanionAbs extends CompanionBase[NumCompanionAbs] with NumCompanion {
          override def toString = "Num"
        };
        def Num: Rep[NumCompanionAbs];
        implicit def proxyNumCompanion(p: Rep[NumCompanion]): NumCompanion = proxyOps[NumCompanion](p);
        class DoubleNumElem(val iso: Iso[DoubleNumData, DoubleNum]) extends NumElem[Double, DoubleNum] with ConcreteElem[DoubleNumData, DoubleNum] {
          override def convertNum(x: Rep[Num[Double]]) = DoubleNum();
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = weakTypeTag[DoubleNum]
        };
        type DoubleNumData = Unit;
        class DoubleNumIso extends Iso[DoubleNumData, DoubleNum] {
          override def from(p: Rep[DoubleNum]) = ();
          override def to(p: Rep[Unit]) = {
            val unit = p;
            DoubleNum()
          };
          lazy val defaultRepTo: Rep[DoubleNum] = DoubleNum();
          lazy val eTo = new DoubleNumElem(this)
        };
        abstract class DoubleNumCompanionAbs extends CompanionBase[DoubleNumCompanionAbs] with DoubleNumCompanion {
          override def toString = "DoubleNum";
          def apply(p: Rep[DoubleNumData]): Rep[DoubleNum] = isoDoubleNum.to(p);
          def apply(): Rep[DoubleNum] = mkDoubleNum()
        };
        object DoubleNumMatcher {
          def unapply(p: Rep[Num[Double]]) = unmkDoubleNum(p)
        };
        def DoubleNum: Rep[DoubleNumCompanionAbs];
        implicit def proxyDoubleNumCompanion(p: Rep[DoubleNumCompanionAbs]): DoubleNumCompanionAbs = proxyOps[DoubleNumCompanionAbs](p);
        implicit case object DoubleNumCompanionElem extends CompanionElem[DoubleNumCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[DoubleNumCompanionAbs];
          protected def getDefaultRep = DoubleNum
        };
        implicit def proxyDoubleNum(p: Rep[DoubleNum]): DoubleNum = proxyOps[DoubleNum](p);
        implicit class ExtendedDoubleNum(p: Rep[DoubleNum]) {
          def toData: Rep[DoubleNumData] = isoDoubleNum.from(p)
        };
        implicit def isoDoubleNum: Iso[DoubleNumData, DoubleNum] = new DoubleNumIso();
        def mkDoubleNum(): Rep[DoubleNum];
        def unmkDoubleNum(p: Rep[Num[Double]]): Option[Rep[Unit]]
      };
      trait NumsSeq extends NumsDsl with ScalanSeq { self: NumsDslSeq =>
        lazy val Num: Rep[NumCompanionAbs] = {
          final class $anon extends NumCompanionAbs with UserTypeSeq[NumCompanionAbs] {
            lazy val selfType = element[NumCompanionAbs]
          };
          new $anon()
        };
        case class SeqDoubleNum() extends DoubleNum() with UserTypeSeq[DoubleNum] {
          lazy val selfType = element[DoubleNum]
        };
        lazy val DoubleNum = {
          final class $anon extends DoubleNumCompanionAbs with UserTypeSeq[DoubleNumCompanionAbs] {
            lazy val selfType = element[DoubleNumCompanionAbs]
          };
          new $anon()
        };
        def mkDoubleNum(): Rep[DoubleNum] = new SeqDoubleNum();
        def unmkDoubleNum(p: Rep[Num[Double]]) = p match {
          case (p @ ((_): DoubleNum @unchecked)) => Some(())
          case _ => None
        }
      };
      trait NumsExp extends NumsDsl with ScalanExp { self: NumsDslExp =>
        lazy val Num: Rep[NumCompanionAbs] = {
          final class $anon extends NumCompanionAbs with UserTypeDef[NumCompanionAbs] {
            lazy val selfType = element[NumCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        case class ExpDoubleNum() extends DoubleNum() with UserTypeDef[DoubleNum] {
          lazy val selfType = element[DoubleNum];
          override def mirror(t: Transformer) = ExpDoubleNum()
        };
        lazy val DoubleNum: Rep[DoubleNumCompanionAbs] = {
          final class $anon extends DoubleNumCompanionAbs with UserTypeDef[DoubleNumCompanionAbs] {
            lazy val selfType = element[DoubleNumCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object DoubleNumMethods {
          object zero {
            def unapply(d: (Def[_$2] forSome { 
              type _$2
            })): Option[Rep[DoubleNum]] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[DoubleNumElem].&&(__equal(method.getName, "zero")) => Some(receiver).asInstanceOf[Option[Rep[DoubleNum]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$3] forSome { 
              type _$3
            })): Option[Rep[DoubleNum]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object one {
            def unapply(d: (Def[_$4] forSome { 
              type _$4
            })): Option[Rep[DoubleNum]] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[DoubleNumElem].&&(__equal(method.getName, "one")) => Some(receiver).asInstanceOf[Option[Rep[DoubleNum]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$5] forSome { 
              type _$5
            })): Option[Rep[DoubleNum]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object plus {
            def unapply(d: (Def[_$6] forSome { 
              type _$6
            })): Option[scala.Tuple3[Rep[DoubleNum], Rep[Double], Rep[Double]]] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (b @ _), _*), _) if receiver.elem.isInstanceOf[DoubleNumElem].&&(__equal(method.getName, "plus")) => Some(scala.Tuple3(receiver, a, b)).asInstanceOf[Option[scala.Tuple3[Rep[DoubleNum], Rep[Double], Rep[Double]]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$7] forSome { 
              type _$7
            })): Option[scala.Tuple3[Rep[DoubleNum], Rep[Double], Rep[Double]]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object times {
            def unapply(d: (Def[_$8] forSome { 
              type _$8
            })): Option[scala.Tuple3[Rep[DoubleNum], Rep[Double], Rep[Double]]] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (b @ _), _*), _) if receiver.elem.isInstanceOf[DoubleNumElem].&&(__equal(method.getName, "times")) => Some(scala.Tuple3(receiver, a, b)).asInstanceOf[Option[scala.Tuple3[Rep[DoubleNum], Rep[Double], Rep[Double]]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$9] forSome { 
              type _$9
            })): Option[scala.Tuple3[Rep[DoubleNum], Rep[Double], Rep[Double]]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object DoubleNumCompanionMethods;
        def mkDoubleNum(): Rep[DoubleNum] = new ExpDoubleNum();
        def unmkDoubleNum(p: Rep[Num[Double]]) = p.elem.asInstanceOf[(Elem[_$10] forSome { 
          type _$10
        })] match {
          case ((_): DoubleNumElem @unchecked) => Some(())
          case _ => None
        };
        object NumMethods {
          object zero {
            def unapply(d: (Def[_$11] forSome { 
              type _$11
            })): Option[(Rep[Num[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumElem[_$12, _$13] forSome { 
  type _$12;
  type _$13
})].&&(__equal(method.getName, "zero")) => Some(receiver).asInstanceOf[Option[(Rep[Num[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$14] forSome { 
              type _$14
            })): Option[(Rep[Num[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object one {
            def unapply(d: (Def[_$15] forSome { 
              type _$15
            })): Option[(Rep[Num[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumElem[_$16, _$17] forSome { 
  type _$16;
  type _$17
})].&&(__equal(method.getName, "one")) => Some(receiver).asInstanceOf[Option[(Rep[Num[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$18] forSome { 
              type _$18
            })): Option[(Rep[Num[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object plus {
            def unapply(d: (Def[_$19] forSome { 
              type _$19
            })): Option[(scala.Tuple3[Rep[Num[T]], Rep[T], Rep[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (b @ _), _*), _) if receiver.elem.isInstanceOf[(NumElem[_$20, _$21] forSome { 
  type _$20;
  type _$21
})].&&(__equal(method.getName, "plus")) => Some(scala.Tuple3(receiver, a, b)).asInstanceOf[Option[(scala.Tuple3[Rep[Num[T]], Rep[T], Rep[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$22] forSome { 
              type _$22
            })): Option[(scala.Tuple3[Rep[Num[T]], Rep[T], Rep[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object times {
            def unapply(d: (Def[_$23] forSome { 
              type _$23
            })): Option[(scala.Tuple3[Rep[Num[T]], Rep[T], Rep[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (b @ _), _*), _) if receiver.elem.isInstanceOf[(NumElem[_$24, _$25] forSome { 
  type _$24;
  type _$25
})].&&(__equal(method.getName, "times")) => Some(scala.Tuple3(receiver, a, b)).asInstanceOf[Option[(scala.Tuple3[Rep[Num[T]], Rep[T], Rep[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$26] forSome { 
              type _$26
            })): Option[(scala.Tuple3[Rep[Num[T]], Rep[T], Rep[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object NumCompanionMethods
      };
      trait Nums extends Base { self: NumsDsl =>
        trait Num[T] extends Reifiable[Num[T]] {
          implicit def eeT: Elem[T];
          def zero: Rep[T];
          def one: Rep[T];
          def plus(a: Rep[T], b: Rep[T]): Rep[T];
          def times(a: Rep[T], b: Rep[T]): Rep[T]
        };
        abstract class DoubleNum extends Num[Double] {
          def eeT: Elem[Double] = element[Double];
          def zero: Rep[Double] = toRep(0.0);
          def one: Rep[Double] = toRep(1.0);
          def plus(a: Rep[Double], b: Rep[Double]): Rep[Double] = a.+(b);
          def times(a: Rep[Double], b: Rep[Double]): Rep[Double] = a.*(b)
        };
        trait NumCompanion;
        trait DoubleNumCompanion
      };
      trait NumsDsl extends NumsAbs { self: NumsDsl =>
        
      };
      trait NumsDslSeq extends NumsSeq { self: NumsDslSeq =>
        
      };
      trait NumsDslExp extends NumsExp { self: NumsDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZt3ynp7b43WYAgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AANOdW1zcQB+AAZzcgAjc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVQcmltaXRpdmU1w7tV8L5OBQIAAkwAEmRlZmF1bHRWYWx1ZVN0cmluZ3EAfgAE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cQB+ACVxAH4AJ3EAfgAHc3EAfgAcc3EAfgAPdAABVHEAfgAHc3EAfgAaAAAAcQB+AAdxAH4AB3EAfgAndAADb25lcQB+ACdxAH4AB3NxAH4AHHNxAH4AD3QAAVRxAH4AB3NxAH4AGgAAAHEAfgAHc3EAfgAGc3EAfgAxc3EAfgAGc3EAfgA0AAAAcQB+AAdxAH4AJ3QAAWFzcQB+AA90AAFUcQB+AAdzcQB+ADQAAABxAH4AB3EAfgAndAABYnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgAndAAEcGx1c3EAfgAncQB+AAdzcQB+ABxzcQB+AA90AAFUcQB+AAdzcQB+ABoAAABxAH4AB3NxAH4ABnNxAH4AMXNxAH4ABnNxAH4ANAAAAHEAfgAHcQB+ACd0AAFhc3EAfgAPdAABVHEAfgAHc3EAfgA0AAAAcQB+AAdxAH4AJ3QAAWJzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4AJ3QABXRpbWVzcQB+ACdxAH4AB3NxAH4AHHNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAncHQAA051bXEAfgAnc3EAfgAGc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlQXJnklsmSHLxm4kCAARMAAVib3VuZHEAfgADTAAMY29udGV4dEJvdW5kcQB+AAFMAARuYW1lcQB+AARMAAd0cGFyYW1zcQB+AAF4cHEAfgAncQB+AAd0AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4AYnEAfgAncQB+AAdxAH4AB3QABE51bXN0AApzY2FsYW5pemVycQB+ACdxAH4AJw=="
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
      import scalanizer.implOfNums.StagedEvaluation._;
      lazy val scalanContext = new Scalan();
      def getScalanContext = scalanContext;
      class Scalan extends NumsDslExp with CommunityLmsCompilerScala with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler {
        val lms = new CommunityLmsBackend()
      };
      import scalan.CommunityMethodMappingDSL;
      import scalan.compilation.lms.uni.LmsCompilerUni;
      lazy val scalanContextUni = new ScalanUni();
      def getScalanContextUni = scalanContextUni;
      class ScalanUni extends NumsDslExp with LmsCompilerUni with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler with CommunityMethodMappingDSL
    }
  }
}