//package scalanizer.linalgebra {
//  package implOfMatrOps {
//    object StagedEvaluation {
//      import scalan._;
//      import scalanizer.implOfNums.StagedEvaluation._;
//      import scalanizer.implOfNumMonoids.StagedEvaluation._;
//      import scalanizer.linalgebra.implOfVecs.StagedEvaluation._;
//      import scalanizer.linalgebra.implOfMatrs.StagedEvaluation._;
//      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
//      import scala.reflect.runtime.universe._;
//      import scala.reflect._;
//      import scalan.common.Default;
//      trait MatrOpsAbs extends MatrOps with ScalanDsl { self: LinearAlgebraDsl =>
//        implicit def proxyMatrOp(p: Rep[MatrOp]): MatrOp = proxyOps[MatrOp](p)(scala.reflect.classTag[MatrOp]);
//        class MatrOpElem[To <: MatrOp] extends EntityElem[To] {
//          override def isEntityType = true;
//          override lazy val tag = weakTypeTag[MatrOp].asInstanceOf[WeakTypeTag[To]];
//          override def convert(x: Rep[(Reifiable[_$1] forSome {
//            type _$1
//          })]) = {
//            implicit val eTo: Elem[To] = this;
//            val conv = fun(((x: Rep[MatrOp]) => convertMatrOp(x)));
//            tryConvert(element[MatrOp], this, x, conv)
//          };
//          def convertMatrOp(x: Rep[MatrOp]): Rep[To] = {
//            assert(x.selfType1 match {
//              case ((_): MatrOpElem[(_)]) => true
//              case _ => false
//            });
//            x.asRep[To]
//          };
//          override def getDefaultRep: Rep[To] = ???
//        };
//        implicit def matrOpElement: Elem[MatrOp] = new MatrOpElem[MatrOp]();
//        implicit case object MatrOpCompanionElem extends CompanionElem[MatrOpCompanionAbs] with scala.Product with scala.Serializable {
//          lazy val tag = weakTypeTag[MatrOpCompanionAbs];
//          protected def getDefaultRep = MatrOp
//        };
//        abstract class MatrOpCompanionAbs extends CompanionBase[MatrOpCompanionAbs] with MatrOpCompanion {
//          override def toString = "MatrOp"
//        };
//        def MatrOp: Rep[MatrOpCompanionAbs];
//        implicit def proxyMatrOpCompanion(p: Rep[MatrOpCompanion]): MatrOpCompanion = proxyOps[MatrOpCompanion](p);
//        class BaseMatrOpElem(val iso: Iso[BaseMatrOpData, BaseMatrOp]) extends MatrOpElem[BaseMatrOp] with ConcreteElem[BaseMatrOpData, BaseMatrOp] {
//          override def convertMatrOp(x: Rep[MatrOp]) = BaseMatrOp();
//          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
//          override lazy val tag = weakTypeTag[BaseMatrOp]
//        };
//        type BaseMatrOpData = Unit;
//        class BaseMatrOpIso extends Iso[BaseMatrOpData, BaseMatrOp] {
//          override def from(p: Rep[BaseMatrOp]) = ();
//          override def to(p: Rep[Unit]) = {
//            val unit = p;
//            BaseMatrOp()
//          };
//          lazy val defaultRepTo: Rep[BaseMatrOp] = BaseMatrOp();
//          lazy val eTo = new BaseMatrOpElem(this)
//        };
//        abstract class BaseMatrOpCompanionAbs extends CompanionBase[BaseMatrOpCompanionAbs] with BaseMatrOpCompanion {
//          override def toString = "BaseMatrOp";
//          def apply(p: Rep[BaseMatrOpData]): Rep[BaseMatrOp] = isoBaseMatrOp.to(p);
//          def apply(): Rep[BaseMatrOp] = mkBaseMatrOp()
//        };
//        object BaseMatrOpMatcher {
//          def unapply(p: Rep[MatrOp]) = unmkBaseMatrOp(p)
//        };
//        def BaseMatrOp: Rep[BaseMatrOpCompanionAbs];
//        implicit def proxyBaseMatrOpCompanion(p: Rep[BaseMatrOpCompanionAbs]): BaseMatrOpCompanionAbs = proxyOps[BaseMatrOpCompanionAbs](p);
//        implicit case object BaseMatrOpCompanionElem extends CompanionElem[BaseMatrOpCompanionAbs] with scala.Product with scala.Serializable {
//          lazy val tag = weakTypeTag[BaseMatrOpCompanionAbs];
//          protected def getDefaultRep = BaseMatrOp
//        };
//        implicit def proxyBaseMatrOp(p: Rep[BaseMatrOp]): BaseMatrOp = proxyOps[BaseMatrOp](p);
//        implicit class ExtendedBaseMatrOp(p: Rep[BaseMatrOp]) {
//          def toData: Rep[BaseMatrOpData] = isoBaseMatrOp.from(p)
//        };
//        implicit def isoBaseMatrOp: Iso[BaseMatrOpData, BaseMatrOp] = new BaseMatrOpIso();
//        def mkBaseMatrOp(): Rep[BaseMatrOp];
//        def unmkBaseMatrOp(p: Rep[MatrOp]): Option[Rep[Unit]]
//      };
//      trait MatrOpsExp extends MatrOpsDsl with ScalanExp { self: LinearAlgebraDslExp =>
//        lazy val MatrOp: Rep[MatrOpCompanionAbs] = {
//          final class $anon extends MatrOpCompanionAbs with UserTypeDef[MatrOpCompanionAbs] {
//            lazy val selfType = element[MatrOpCompanionAbs];
//            override def mirror(t: Transformer) = this
//          };
//          new $anon()
//        };
//        case class ExpBaseMatrOp() extends BaseMatrOp() with UserTypeDef[BaseMatrOp] {
//          lazy val selfType = element[BaseMatrOp];
//          override def mirror(t: Transformer) = ExpBaseMatrOp()
//        };
//        lazy val BaseMatrOp: Rep[BaseMatrOpCompanionAbs] = {
//          final class $anon extends BaseMatrOpCompanionAbs with UserTypeDef[BaseMatrOpCompanionAbs] {
//            lazy val selfType = element[BaseMatrOpCompanionAbs];
//            override def mirror(t: Transformer) = this
//          };
//          new $anon()
//        };
//        object BaseMatrOpMethods {
//          object mvm {
//            def unapply(d: (Def[_$2] forSome {
//              type _$2
//            })): Option[(scala.Tuple6[Rep[BaseMatrOp], Rep[Matr[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]], Elem[T]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((matrix @ _), (vector @ _), (n @ _), (m @ _), (emT @ _), _*), _) if receiver.elem.isInstanceOf[BaseMatrOpElem].&&(__equal(method.getName, "mvm")) => Some(scala.Tuple6(receiver, matrix, vector, n, m, emT)).asInstanceOf[Option[(scala.Tuple6[Rep[BaseMatrOp], Rep[Matr[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]], Elem[T]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$3] forSome {
//              type _$3
//            })): Option[(scala.Tuple6[Rep[BaseMatrOp], Rep[Matr[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]], Elem[T]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          }
//        };
//        object BaseMatrOpCompanionMethods;
//        def mkBaseMatrOp(): Rep[BaseMatrOp] = new ExpBaseMatrOp();
//        def unmkBaseMatrOp(p: Rep[MatrOp]) = p.elem.asInstanceOf[(Elem[_$4] forSome {
//          type _$4
//        })] match {
//          case ((_): BaseMatrOpElem @unchecked) => Some(())
//          case _ => None
//        };
//        object MatrOpMethods {
//          object mvm {
//            def unapply(d: (Def[_$5] forSome {
//              type _$5
//            })): Option[(scala.Tuple6[Rep[MatrOp], Rep[Matr[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]], Elem[T]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((matrix @ _), (vector @ _), (n @ _), (m @ _), (emT @ _), _*), _) if receiver.elem.isInstanceOf[(MatrOpElem[_$6] forSome {
//  type _$6
//})].&&(__equal(method.getName, "mvm")) => Some(scala.Tuple6(receiver, matrix, vector, n, m, emT)).asInstanceOf[Option[(scala.Tuple6[Rep[MatrOp], Rep[Matr[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]], Elem[T]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$7] forSome {
//              type _$7
//            })): Option[(scala.Tuple6[Rep[MatrOp], Rep[Matr[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]], Elem[T]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          }
//        };
//        object MatrOpCompanionMethods
//      };
//      trait MatrOps extends Base { self: LinearAlgebraDsl =>
//        trait MatrOp extends Reifiable[MatrOp] {
//          def mvm[T](matrix: Rep[Matr[T]], vector: Rep[Vec[T]])(n: Rep[Num[T]], m: Rep[NumMonoid[T]])(implicit emT: Elem[T]): Rep[Vec[T]]
//        };
//        abstract class BaseMatrOp extends MatrOp {
//          def mvm[T](matrix: Rep[Matr[T]], vector: Rep[Vec[T]])(n: Rep[Num[T]], m: Rep[NumMonoid[T]])(implicit emT: Elem[T]): Rep[Vec[T]] = DenseVec(matrix.rows.map[T](fun(((r: Rep[Vec[T]]) => r.dot(vector)(n, m))))(evidence$2))(evidence$2)
//        };
//        trait MatrOpCompanion;
//        trait BaseMatrOpCompanion
//      };
//      trait MatrOpsDsl extends MatrOpsAbs { self: LinearAlgebraDsl =>
//
//      };
//      trait MatrOpsDslExp extends MatrOpsExp { self: LinearAlgebraDslExp =>
//
//      };
//      val serializedMetaAst = ""
//    }
//
//    object HotSpotKernels {
//      import java.io.File;
//      import scalan.compilation.GraphVizConfig
//    }
//
//    object HotSpotManager {
//      import scalan.ScalanCommunityDslExp;
//      import scalan.compilation.lms.{CommunityLmsBackend, CoreBridge};
//      import scalan.compilation.lms.scalac.CommunityLmsCompilerScala;
//      import scalan.primitives.EffectfulCompiler;
//      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
//      lazy val scalanContext = new Scalan();
//      def getScalanContext = scalanContext;
//      class Scalan extends LinearAlgebraDslExp with CommunityLmsCompilerScala with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler {
//        val lms = new CommunityLmsBackend()
//      };
//      import scalan.CommunityMethodMappingDSL;
//      import scalan.compilation.lms.uni.LmsCompilerUni;
//      lazy val scalanContextUni = new ScalanUni();
//      def getScalanContextUni = scalanContextUni;
//      class ScalanUni extends LinearAlgebraDslExp with LmsCompilerUni with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler with CommunityMethodMappingDSL
//    }
//  }
//}