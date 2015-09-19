package scalanizer {
  package implOfNums {
    object StagedEvaluation {
      import scalan._;
      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait NumsAbs extends Nums with ScalanDsl { self: LinearAlgebraDsl =>
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
      trait NumsExp extends NumsDsl with ScalanExp { self: LinearAlgebraDslExp =>
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
      trait Nums extends Base { self: LinearAlgebraDsl =>
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
      trait NumsDsl extends NumsAbs { self: LinearAlgebraDsl =>
        
      };
      trait NumsDslExp extends NumsExp { self: LinearAlgebraDslExp =>
        
      };
      val serializedMetaAst = ""
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
      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      lazy val scalanContext = new Scalan();
      def getScalanContext = scalanContext;
      class Scalan extends LinearAlgebraDslExp with CommunityLmsCompilerScala with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler {
        val lms = new CommunityLmsBackend()
      };
      import scalan.CommunityMethodMappingDSL;
      import scalan.compilation.lms.uni.LmsCompilerUni;
      lazy val scalanContextUni = new ScalanUni();
      def getScalanContextUni = scalanContextUni;
      class ScalanUni extends LinearAlgebraDslExp with LmsCompilerUni with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler with CommunityMethodMappingDSL
    }
  }
}