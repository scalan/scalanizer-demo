package scalanizer.linalgebra {
  package implOfVecs {
    object StagedEvaluation {
      import scalan._;
      import scalanizer.implOfNumMonoids.StagedEvaluation._;
      import scalanizer.collections.implOfCols.StagedEvaluation._;
      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait VecsAbs extends Vecs with ScalanDsl { self: LinearAlgebraDsl =>
        implicit def proxyVec[T](p: Rep[Vec[T]]): Vec[T] = proxyOps[Vec[T]](p)(scala.reflect.classTag[Vec[T]]);
        class VecElem[T, To <: Vec[T]](implicit val eeT: Elem[T]) extends EntityElem[To] {
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagT = eeT.tag;
            weakTypeTag[Vec[T]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$1] forSome { 
            type _$1
          })]) = {
            implicit val eTo: Elem[To] = this;
            val conv = fun(((x: Rep[Vec[T]]) => convertVec(x)));
            tryConvert(element[Vec[T]], this, x, conv)
          };
          def convertVec(x: Rep[Vec[T]]): Rep[To] = {
            assert(x.selfType1 match {
              case ((_): VecElem[(_), (_)]) => true
              case _ => false
            });
            x.asRep[To]
          };
          override def getDefaultRep: Rep[To] = ???
        };
        implicit def vecElement[T](implicit eeT: Elem[T]): Elem[Vec[T]] = new VecElem[T, Vec[T]]();
        implicit case object VecCompanionElem extends CompanionElem[VecCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[VecCompanionAbs];
          protected def getDefaultRep = Vec
        };
        abstract class VecCompanionAbs extends CompanionBase[VecCompanionAbs] with VecCompanion {
          override def toString = "Vec"
        };
        def Vec: Rep[VecCompanionAbs];
        implicit def proxyVecCompanion(p: Rep[VecCompanion]): VecCompanion = proxyOps[VecCompanion](p);
        class DenseVecElem[T](val iso: Iso[DenseVecData[T], DenseVec[T]])(implicit eeT: Elem[T]) extends VecElem[T, DenseVec[T]] with ConcreteElem[DenseVecData[T], DenseVec[T]] {
          override def convertVec(x: Rep[Vec[T]]) = DenseVec(x.items);
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = {
            implicit val tagT = eeT.tag;
            weakTypeTag[DenseVec[T]]
          }
        };
        type DenseVecData[T] = Col[T];
        class DenseVecIso[T](implicit eeT: Elem[T]) extends Iso[DenseVecData[T], DenseVec[T]] {
          override def from(p: Rep[DenseVec[T]]) = p.items;
          override def to(p: Rep[Col[T]]) = {
            val items = p;
            DenseVec(items)
          };
          lazy val defaultRepTo: Rep[DenseVec[T]] = DenseVec(element[Col[T]].defaultRepValue);
          lazy val eTo = new DenseVecElem[T](this)
        };
        abstract class DenseVecCompanionAbs extends CompanionBase[DenseVecCompanionAbs] with DenseVecCompanion {
          override def toString = "DenseVec";
          def apply[T](items: Rep[Col[T]])(implicit eeT: Elem[T]): Rep[DenseVec[T]] = mkDenseVec(items)
        };
        object DenseVecMatcher {
          def unapply[T](p: Rep[Vec[T]]) = unmkDenseVec(p)
        };
        def DenseVec: Rep[DenseVecCompanionAbs];
        implicit def proxyDenseVecCompanion(p: Rep[DenseVecCompanionAbs]): DenseVecCompanionAbs = proxyOps[DenseVecCompanionAbs](p);
        implicit case object DenseVecCompanionElem extends CompanionElem[DenseVecCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[DenseVecCompanionAbs];
          protected def getDefaultRep = DenseVec
        };
        implicit def proxyDenseVec[T](p: Rep[DenseVec[T]]): DenseVec[T] = proxyOps[DenseVec[T]](p);
        implicit class ExtendedDenseVec[T](p: Rep[DenseVec[T]])(implicit eeT: Elem[T]) {
          def toData: Rep[DenseVecData[T]] = isoDenseVec(eeT).from(p)
        };
        implicit def isoDenseVec[T](implicit eeT: Elem[T]): Iso[DenseVecData[T], DenseVec[T]] = new DenseVecIso[T]();
        def mkDenseVec[T](items: Rep[Col[T]])(implicit eeT: Elem[T]): Rep[DenseVec[T]];
        def unmkDenseVec[T](p: Rep[Vec[T]]): Option[Rep[Col[T]]]
      };
      trait VecsExp extends VecsDsl with ScalanExp { self: LinearAlgebraDslExp =>
        lazy val Vec: Rep[VecCompanionAbs] = {
          final class $anon extends VecCompanionAbs with UserTypeDef[VecCompanionAbs] {
            lazy val selfType = element[VecCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        case class ExpDenseVec[T](override val items: Rep[Col[T]])(implicit eeT: Elem[T]) extends DenseVec[T](items) with UserTypeDef[DenseVec[T]] {
          lazy val selfType = element[DenseVec[T]];
          override def mirror(t: Transformer) = ExpDenseVec[T](t(items))
        };
        lazy val DenseVec: Rep[DenseVecCompanionAbs] = {
          final class $anon extends DenseVecCompanionAbs with UserTypeDef[DenseVecCompanionAbs] {
            lazy val selfType = element[DenseVecCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object DenseVecMethods {
          object length {
            def unapply(d: (Def[_$2] forSome { 
              type _$2
            })): Option[(Rep[DenseVec[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(DenseVecElem[_$3] forSome { 
  type _$3
})].&&(__equal(method.getName, "length")) => Some(receiver).asInstanceOf[Option[(Rep[DenseVec[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$4] forSome { 
              type _$4
            })): Option[(Rep[DenseVec[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$5] forSome { 
              type _$5
            })): Option[(scala.Tuple2[Rep[DenseVec[T]], Rep[Int]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(DenseVecElem[_$6] forSome { 
  type _$6
})].&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[DenseVec[T]], Rep[Int]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$7] forSome { 
              type _$7
            })): Option[(scala.Tuple2[Rep[DenseVec[T]], Rep[Int]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object dot {
            def unapply(d: (Def[_$8] forSome { 
              type _$8
            })): Option[(scala.Tuple4[Rep[DenseVec[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((other @ _), (n @ _), (m @ _), _*), _) if receiver.elem.isInstanceOf[(DenseVecElem[_$9] forSome { 
  type _$9
})].&&(__equal(method.getName, "dot")) => Some(scala.Tuple4(receiver, other, n, m)).asInstanceOf[Option[(scala.Tuple4[Rep[DenseVec[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$10] forSome { 
              type _$10
            })): Option[(scala.Tuple4[Rep[DenseVec[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object DenseVecCompanionMethods;
        def mkDenseVec[T](items: Rep[Col[T]])(implicit eeT: Elem[T]): Rep[DenseVec[T]] = new ExpDenseVec[T](items);
        def unmkDenseVec[T](p: Rep[Vec[T]]) = p.elem.asInstanceOf[(Elem[_$11] forSome { 
          type _$11
        })] match {
          case ((_): DenseVecElem[T] @unchecked) => Some(p.asRep[DenseVec[T]].items)
          case _ => None
        };
        object VecMethods {
          object length {
            def unapply(d: (Def[_$12] forSome { 
              type _$12
            })): Option[(Rep[Vec[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(VecElem[_$13, _$14] forSome { 
  type _$13;
  type _$14
})].&&(__equal(method.getName, "length")) => Some(receiver).asInstanceOf[Option[(Rep[Vec[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$15] forSome { 
              type _$15
            })): Option[(Rep[Vec[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object items {
            def unapply(d: (Def[_$16] forSome { 
              type _$16
            })): Option[(Rep[Vec[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(VecElem[_$17, _$18] forSome { 
  type _$17;
  type _$18
})].&&(__equal(method.getName, "items")) => Some(receiver).asInstanceOf[Option[(Rep[Vec[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$19] forSome { 
              type _$19
            })): Option[(Rep[Vec[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$20] forSome { 
              type _$20
            })): Option[(scala.Tuple2[Rep[Vec[T]], Rep[Int]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(VecElem[_$21, _$22] forSome { 
  type _$21;
  type _$22
})].&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[Vec[T]], Rep[Int]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$23] forSome { 
              type _$23
            })): Option[(scala.Tuple2[Rep[Vec[T]], Rep[Int]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object dot {
            def unapply(d: (Def[_$24] forSome { 
              type _$24
            })): Option[(scala.Tuple4[Rep[Vec[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((other @ _), (n @ _), (m @ _), _*), _) if receiver.elem.isInstanceOf[(VecElem[_$25, _$26] forSome { 
  type _$25;
  type _$26
})].&&(__equal(method.getName, "dot")) => Some(scala.Tuple4(receiver, other, n, m)).asInstanceOf[Option[(scala.Tuple4[Rep[Vec[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$27] forSome { 
              type _$27
            })): Option[(scala.Tuple4[Rep[Vec[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object VecCompanionMethods
      };
      trait Vecs extends Base { self: LinearAlgebraDsl =>
        trait Vec[T] extends Reifiable[Vec[T]] {
          implicit def eeT: Elem[T];
          def length: Rep[Int];
          def items: Rep[Col[T]];
          def apply(i: Rep[Int]): Rep[T];
          def map[R](f: Rep[scala.Function1[T, R]])(implicit emR: Elem[R]): Rep[Vec[R]];
          def dot(other: Rep[Vec[T]])(implicit n: Rep[Num[T]], m: Rep[NumMonoid[T]]): Rep[T]
        };
        abstract class DenseVec[T](val items: Rep[Col[T]])(implicit val eeT: Elem[T]) extends Vec[T] {
          def length: Rep[Int] = DenseVec.this.items.length;
          def apply(i: Rep[Int]): Rep[T] = DenseVec.this.items.apply(i);
          def map[R](f: Rep[scala.Function1[T, R]])(implicit emR: Elem[R]): Rep[Vec[R]] = DenseVec(DenseVec.this.items.map[R](f)(Predef.implicitly[Elem[R]]))(Predef.implicitly[Elem[R]]);
          def dot(other: Rep[Vec[T]])(implicit n: Rep[Num[T]], m: Rep[NumMonoid[T]]): Rep[T] = other.items.zip[T](DenseVec.this.items).map[T](fun(((v: Rep[scala.Tuple2[T, T]]) => n.times(v._1, v._2))))(Predef.implicitly[Elem[T]]).reduce(m)
        };
        trait VecCompanion;
        trait DenseVecCompanion
      };
      trait VecsDsl extends VecsAbs { self: LinearAlgebraDsl =>
        
      };
      trait VecsDslExp extends VecsExp { self: LinearAlgebraDslExp =>
        
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