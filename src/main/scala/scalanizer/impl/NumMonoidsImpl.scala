package scalanizer {
  package implOfNumMonoids {
    object StagedEvaluation {
      import scalan._;
      import scalanizer.implOfNums.StagedEvaluation._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait NumMonoidsAbs extends NumMonoids with ScalanDsl { self: NumMonoidsDsl =>
        implicit def proxyNumMonoid[T](p: Rep[NumMonoid[T]]): NumMonoid[T] = proxyOps[NumMonoid[T]](p)(scala.reflect.classTag[NumMonoid[T]]);
        class NumMonoidElem[T, To <: NumMonoid[T]](implicit val eeT: Elem[T]) extends EntityElem[To] {
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagT = eeT.tag;
            weakTypeTag[NumMonoid[T]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$1] forSome { 
            type _$1
          })]) = {
            implicit val eTo: Elem[To] = this;
            val conv = fun(((x: Rep[NumMonoid[T]]) => convertNumMonoid(x)));
            tryConvert(element[NumMonoid[T]], this, x, conv)
          };
          def convertNumMonoid(x: Rep[NumMonoid[T]]): Rep[To] = {
            assert(x.selfType1 match {
              case ((_): NumMonoidElem[(_), (_)]) => true
              case _ => false
            });
            x.asRep[To]
          };
          override def getDefaultRep: Rep[To] = ???
        };
        implicit def numMonoidElement[T](implicit eeT: Elem[T]): Elem[NumMonoid[T]] = new NumMonoidElem[T, NumMonoid[T]]();
        implicit case object NumMonoidCompanionElem extends CompanionElem[NumMonoidCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[NumMonoidCompanionAbs];
          protected def getDefaultRep = NumMonoid
        };
        abstract class NumMonoidCompanionAbs extends CompanionBase[NumMonoidCompanionAbs] with NumMonoidCompanion {
          override def toString = "NumMonoid"
        };
        def NumMonoid: Rep[NumMonoidCompanionAbs];
        implicit def proxyNumMonoidCompanion(p: Rep[NumMonoidCompanion]): NumMonoidCompanion = proxyOps[NumMonoidCompanion](p);
        class PlusMonoidElem[T](val iso: Iso[PlusMonoidData[T], PlusMonoid[T]])(implicit eeT: Elem[T]) extends NumMonoidElem[T, PlusMonoid[T]] with ConcreteElem[PlusMonoidData[T], PlusMonoid[T]] {
          override def convertNumMonoid(x: Rep[NumMonoid[T]]) = PlusMonoid(x.n);
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = {
            implicit val tagT = eeT.tag;
            weakTypeTag[PlusMonoid[T]]
          }
        };
        type PlusMonoidData[T] = Num[T];
        class PlusMonoidIso[T](implicit eeT: Elem[T]) extends Iso[PlusMonoidData[T], PlusMonoid[T]] {
          override def from(p: Rep[PlusMonoid[T]]) = p.n;
          override def to(p: Rep[Num[T]]) = {
            val n = p;
            PlusMonoid(n)
          };
          lazy val defaultRepTo: Rep[PlusMonoid[T]] = PlusMonoid(element[Num[T]].defaultRepValue);
          lazy val eTo = new PlusMonoidElem[T](this)
        };
        abstract class PlusMonoidCompanionAbs extends CompanionBase[PlusMonoidCompanionAbs] with PlusMonoidCompanion {
          override def toString = "PlusMonoid";
          def apply[T](n: Rep[Num[T]])(implicit eeT: Elem[T]): Rep[PlusMonoid[T]] = mkPlusMonoid(n)
        };
        object PlusMonoidMatcher {
          def unapply[T](p: Rep[NumMonoid[T]]) = unmkPlusMonoid(p)
        };
        def PlusMonoid: Rep[PlusMonoidCompanionAbs];
        implicit def proxyPlusMonoidCompanion(p: Rep[PlusMonoidCompanionAbs]): PlusMonoidCompanionAbs = proxyOps[PlusMonoidCompanionAbs](p);
        implicit case object PlusMonoidCompanionElem extends CompanionElem[PlusMonoidCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[PlusMonoidCompanionAbs];
          protected def getDefaultRep = PlusMonoid
        };
        implicit def proxyPlusMonoid[T](p: Rep[PlusMonoid[T]]): PlusMonoid[T] = proxyOps[PlusMonoid[T]](p);
        implicit class ExtendedPlusMonoid[T](p: Rep[PlusMonoid[T]])(implicit eeT: Elem[T]) {
          def toData: Rep[PlusMonoidData[T]] = isoPlusMonoid(eeT).from(p)
        };
        implicit def isoPlusMonoid[T](implicit eeT: Elem[T]): Iso[PlusMonoidData[T], PlusMonoid[T]] = new PlusMonoidIso[T]();
        def mkPlusMonoid[T](n: Rep[Num[T]])(implicit eeT: Elem[T]): Rep[PlusMonoid[T]];
        def unmkPlusMonoid[T](p: Rep[NumMonoid[T]]): Option[Rep[Num[T]]]
      };
      trait NumMonoidsExp extends NumMonoidsDsl with ScalanExp { self: NumMonoidsDslExp =>
        lazy val NumMonoid: Rep[NumMonoidCompanionAbs] = {
          final class $anon extends NumMonoidCompanionAbs with UserTypeDef[NumMonoidCompanionAbs] {
            lazy val selfType = element[NumMonoidCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        case class ExpPlusMonoid[T](override val n: Rep[Num[T]])(implicit eeT: Elem[T]) extends PlusMonoid[T](n) with UserTypeDef[PlusMonoid[T]] {
          lazy val selfType = element[PlusMonoid[T]];
          override def mirror(t: Transformer) = ExpPlusMonoid[T](t(n))
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
            })): Option[(Rep[PlusMonoid[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PlusMonoidElem[_$3] forSome { 
  type _$3
})].&&(__equal(method.getName, "opName")) => Some(receiver).asInstanceOf[Option[(Rep[PlusMonoid[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$4] forSome { 
              type _$4
            })): Option[(Rep[PlusMonoid[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object zero {
            def unapply(d: (Def[_$5] forSome { 
              type _$5
            })): Option[(Rep[PlusMonoid[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PlusMonoidElem[_$6] forSome { 
  type _$6
})].&&(__equal(method.getName, "zero")) => Some(receiver).asInstanceOf[Option[(Rep[PlusMonoid[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$7] forSome { 
              type _$7
            })): Option[(Rep[PlusMonoid[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object append {
            def unapply(d: (Def[_$8] forSome { 
              type _$8
            })): Option[(Rep[PlusMonoid[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PlusMonoidElem[_$9] forSome { 
  type _$9
})].&&(__equal(method.getName, "append")) => Some(receiver).asInstanceOf[Option[(Rep[PlusMonoid[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$10] forSome { 
              type _$10
            })): Option[(Rep[PlusMonoid[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object isCommutative {
            def unapply(d: (Def[_$11] forSome { 
              type _$11
            })): Option[(Rep[PlusMonoid[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PlusMonoidElem[_$12] forSome { 
  type _$12
})].&&(__equal(method.getName, "isCommutative")) => Some(receiver).asInstanceOf[Option[(Rep[PlusMonoid[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$13] forSome { 
              type _$13
            })): Option[(Rep[PlusMonoid[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object PlusMonoidCompanionMethods;
        def mkPlusMonoid[T](n: Rep[Num[T]])(implicit eeT: Elem[T]): Rep[PlusMonoid[T]] = new ExpPlusMonoid[T](n);
        def unmkPlusMonoid[T](p: Rep[NumMonoid[T]]) = p.elem.asInstanceOf[(Elem[_$14] forSome { 
          type _$14
        })] match {
          case ((_): PlusMonoidElem[T] @unchecked) => Some(p.asRep[PlusMonoid[T]].n)
          case _ => None
        };
        object NumMonoidMethods {
          object n {
            def unapply(d: (Def[_$15] forSome { 
              type _$15
            })): Option[(Rep[NumMonoid[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$16, _$17] forSome { 
  type _$16;
  type _$17
})].&&(__equal(method.getName, "n")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$18] forSome { 
              type _$18
            })): Option[(Rep[NumMonoid[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object opName {
            def unapply(d: (Def[_$19] forSome { 
              type _$19
            })): Option[(Rep[NumMonoid[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$20, _$21] forSome { 
  type _$20;
  type _$21
})].&&(__equal(method.getName, "opName")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$22] forSome { 
              type _$22
            })): Option[(Rep[NumMonoid[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object zero {
            def unapply(d: (Def[_$23] forSome { 
              type _$23
            })): Option[(Rep[NumMonoid[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$24, _$25] forSome { 
  type _$24;
  type _$25
})].&&(__equal(method.getName, "zero")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$26] forSome { 
              type _$26
            })): Option[(Rep[NumMonoid[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object append {
            def unapply(d: (Def[_$27] forSome { 
              type _$27
            })): Option[(Rep[NumMonoid[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$28, _$29] forSome { 
  type _$28;
  type _$29
})].&&(__equal(method.getName, "append")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$30] forSome { 
              type _$30
            })): Option[(Rep[NumMonoid[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object isCommutative {
            def unapply(d: (Def[_$31] forSome { 
              type _$31
            })): Option[(Rep[NumMonoid[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$32, _$33] forSome { 
  type _$32;
  type _$33
})].&&(__equal(method.getName, "isCommutative")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$34] forSome { 
              type _$34
            })): Option[(Rep[NumMonoid[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object NumMonoidCompanionMethods
      };
      trait NumMonoids extends Base with NumsDsl { self: NumMonoidsDsl =>
        trait NumMonoid[T] extends Reifiable[NumMonoid[T]] {
          implicit def eeT: Elem[T];
          def n: Rep[Num[T]];
          def opName: Rep[String];
          def zero: Rep[T];
          def append: Rep[scala.Function1[scala.Tuple2[T, T], T]];
          def isCommutative: Rep[Boolean]
        };
        abstract class PlusMonoid[T](val n: Rep[Num[T]])(implicit val eeT: Elem[T]) extends NumMonoid[T] {
          def opName: Rep[String] = toRep("+");
          def zero: Rep[T] = PlusMonoid.this.n.zero;
          def append: Rep[scala.Function1[scala.Tuple2[T, T], T]] = fun(((in: Rep[scala.Tuple2[T, T]]) => {
            val a0: Rep[T] = in._1;
            val a1: Rep[T] = in._2;
            PlusMonoid.this.n.plus(a0, a1)
          }));
          def isCommutative: Rep[Boolean] = toRep(true)
        };
        trait NumMonoidCompanion;
        trait PlusMonoidCompanion
      };
      trait NumMonoidsDsl extends NumMonoidsAbs with NumsDsl { self: NumMonoidsDsl =>
        
      };
      trait NumMonoidsDslExp extends NumMonoidsExp with NumsDslExp { self: NumMonoidsDslExp =>
        
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
      class ScalanUni extends NumMonoidsDslExp with LmsCompilerUni with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler with CommunityMethodMappingDSL
    }
  }
}