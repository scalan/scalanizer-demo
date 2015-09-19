//package scalanizer.linalgebra {
//  package implOfLinearAlgebraOps {
//    object StagedEvaluation {
//      import scalan._;
//      import scalan.compilation.KernelTypes._;
//      import scala.reflect.runtime.universe._;
//      import scala.reflect._;
//      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._
//      import scalan.common.Default;
//      trait LinearAlgebraOpsAbs extends LinearAlgebraOps with ScalanDsl { self: LinearAlgebraDsl =>
//        implicit def proxyLinearAlgebraOp(p: Rep[LinearAlgebraOp]): LinearAlgebraOp = proxyOps[LinearAlgebraOp](p)(scala.reflect.classTag[LinearAlgebraOp]);
//        class LinearAlgebraOpElem[To <: LinearAlgebraOp] extends EntityElem[To] {
//          override def isEntityType = true;
//          override lazy val tag = weakTypeTag[LinearAlgebraOp].asInstanceOf[WeakTypeTag[To]];
//          override def convert(x: Rep[(Reifiable[_$1] forSome {
//            type _$1
//          })]) = {
//            implicit val eTo: Elem[To] = this;
//            val conv = fun(((x: Rep[LinearAlgebraOp]) => convertLinearAlgebraOp(x)));
//            tryConvert(element[LinearAlgebraOp], this, x, conv)
//          };
//          def convertLinearAlgebraOp(x: Rep[LinearAlgebraOp]): Rep[To] = {
//            assert(x.selfType1 match {
//              case ((_): LinearAlgebraOpElem[(_)]) => true
//              case _ => false
//            });
//            x.asRep[To]
//          };
//          override def getDefaultRep: Rep[To] = ???
//        };
//        implicit def linearAlgebraOpElement: Elem[LinearAlgebraOp] = new LinearAlgebraOpElem[LinearAlgebraOp]();
//        implicit case object LinearAlgebraOpCompanionElem extends CompanionElem[LinearAlgebraOpCompanionAbs] with scala.Product with scala.Serializable {
//          lazy val tag = weakTypeTag[LinearAlgebraOpCompanionAbs];
//          protected def getDefaultRep = LinearAlgebraOp
//        };
//        abstract class LinearAlgebraOpCompanionAbs extends CompanionBase[LinearAlgebraOpCompanionAbs] with LinearAlgebraOpCompanion {
//          override def toString = "LinearAlgebraOp"
//        };
//        def LinearAlgebraOp: Rep[LinearAlgebraOpCompanionAbs];
//        implicit def proxyLinearAlgebraOpCompanion(p: Rep[LinearAlgebraOpCompanion]): LinearAlgebraOpCompanion = proxyOps[LinearAlgebraOpCompanion](p);
//        class LAElem(val iso: Iso[LAData, LA]) extends LinearAlgebraOpElem[LA] with ConcreteElem[LAData, LA] {
//          override def convertLinearAlgebraOp(x: Rep[LinearAlgebraOp]) = LA();
//          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
//          override lazy val tag = weakTypeTag[LA]
//        };
//        type LAData = Unit;
//        class LAIso extends Iso[LAData, LA] {
//          override def from(p: Rep[LA]) = ();
//          override def to(p: Rep[Unit]) = {
//            val unit = p;
//            LA()
//          };
//          lazy val defaultRepTo: Rep[LA] = LA();
//          lazy val eTo = new LAElem(this)
//        };
//        abstract class LACompanionAbs extends CompanionBase[LACompanionAbs] with LACompanion {
//          override def toString = "LA";
//          def apply(p: Rep[LAData]): Rep[LA] = isoLA.to(p);
//          def apply(): Rep[LA] = mkLA()
//        };
//        object LAMatcher {
//          def unapply(p: Rep[LinearAlgebraOp]) = unmkLA(p)
//        };
//        def LA: Rep[LACompanionAbs];
//        implicit def proxyLACompanion(p: Rep[LACompanionAbs]): LACompanionAbs = proxyOps[LACompanionAbs](p);
//        implicit case object LACompanionElem extends CompanionElem[LACompanionAbs] with scala.Product with scala.Serializable {
//          lazy val tag = weakTypeTag[LACompanionAbs];
//          protected def getDefaultRep = LA
//        };
//        implicit def proxyLA(p: Rep[LA]): LA = proxyOps[LA](p);
//        implicit class ExtendedLA(p: Rep[LA]) {
//          def toData: Rep[LAData] = isoLA.from(p)
//        };
//        implicit def isoLA: Iso[LAData, LA] = new LAIso();
//        def mkLA(): Rep[LA];
//        def unmkLA(p: Rep[LinearAlgebraOp]): Option[Rep[Unit]]
//      };
//      trait LinearAlgebraOpsExp extends LinearAlgebraOpsDsl with ScalanExp { self: LinearAlgebraDslExp =>
//        lazy val LinearAlgebraOp: Rep[LinearAlgebraOpCompanionAbs] = {
//          final class $anon extends LinearAlgebraOpCompanionAbs with UserTypeDef[LinearAlgebraOpCompanionAbs] {
//            lazy val selfType = element[LinearAlgebraOpCompanionAbs];
//            override def mirror(t: Transformer) = this
//          };
//          new $anon()
//        };
//        case class ExpLA() extends LA() with UserTypeDef[LA] {
//          lazy val selfType = element[LA];
//          override def mirror(t: Transformer) = ExpLA()
//        };
//        lazy val LA: Rep[LACompanionAbs] = {
//          final class $anon extends LACompanionAbs with UserTypeDef[LACompanionAbs] {
//            lazy val selfType = element[LACompanionAbs];
//            override def mirror(t: Transformer) = this
//          };
//          new $anon()
//        };
//        object LAMethods;
//        object LACompanionMethods {
//          object ddmvm {
//            def unapply(d: (Def[_$2] forSome {
//              type _$2
//            })): Option[scala.Tuple2[Rep[WArray[WArray[Double]]], Rep[WArray[Double]]]] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((m @ _), (v @ _), _*), _) if __equal(receiver.elem, LACompanionElem).&&(__equal(method.getName, "ddmvm")) => Some(scala.Tuple2(m, v)).asInstanceOf[Option[scala.Tuple2[Rep[WArray[WArray[Double]]], Rep[WArray[Double]]]]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$3] forSome {
//              type _$3
//            })): Option[scala.Tuple2[Rep[WArray[WArray[Double]]], Rep[WArray[Double]]]] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          }
//        };
//        def mkLA(): Rep[LA] = new ExpLA();
//        def unmkLA(p: Rep[LinearAlgebraOp]) = p.elem.asInstanceOf[(Elem[_$4] forSome {
//          type _$4
//        })] match {
//          case ((_): LAElem @unchecked) => Some(())
//          case _ => None
//        };
//        object LinearAlgebraOpMethods;
//        object LinearAlgebraOpCompanionMethods
//      };
//      trait LinearAlgebraOps extends Base { self: LinearAlgebraDsl =>
//        trait LinearAlgebraOp extends Reifiable[LinearAlgebraOp];
//        abstract class LA extends LinearAlgebraOp;
//        trait LinearAlgebraOpCompanion;
//        trait LACompanion {
//          def ddmvm(m: Rep[WArray[WArray[Double]]], v: Rep[WArray[Double]]): Rep[WArray[Double]] = {
//            val doubleNumer: Rep[DoubleNum] = DoubleNum();
//            val plusMonoid: Rep[PlusMonoid[Double]] = PlusMonoid(doubleNumer);
//            val width: Rep[Int] = m.apply(toRep(0)).length;
//            val vector: Rep[DenseVec[Double]] = DenseVec(LinearAlgebraOps.this.Col.apply[Double](v)(((ClassTag.Double): Rep[ClassTag[Double]])))(((ClassTag.Double): Rep[ClassTag[Double]]));
//            val matrix: Rep[DenseMatr[Double]] = DenseMatr(LinearAlgebraOps.this.Col.apply[Vec[Double]](scala.this.Predef.refArrayOps[Array[Double]](m).map[DenseVec[Double], Array[Vec[Double]]](fun(((r: Rep[Array[Double]]) => DenseVec(LinearAlgebraOps.this.Col.apply[Double](r)(((ClassTag.Double): Rep[ClassTag[Double]])))(((ClassTag.Double): Rep[ClassTag[Double]])))))(scala.this.Array.canBuildFrom[Vec[Double]](((ClassTag.apply[Vec[Double]](toRep(classOf[scalanizer.linalgebra.Vecs$Vec]))): Rep[ClassTag[Vec[Double]]]))))(((ClassTag.apply[Vec[Double]](toRep(classOf[scalanizer.linalgebra.Vecs$Vec]))): Rep[ClassTag[Vec[Double]]])), width)(((ClassTag.Double): Rep[ClassTag[Double]]));
//            BaseMatrOp().mvm[Double](matrix, vector)(doubleNumer, plusMonoid)(((ClassTag.Double): Rep[ClassTag[Double]])).items.arr
//          }
//        }
//      };
//      trait LinearAlgebraOpsDsl extends LinearAlgebraOpsAbs { self: LinearAlgebraDsl =>
//
//      };
//      trait LinearAlgebraOpsDslExp extends LinearAlgebraOpsExp { self: LinearAlgebraDslExp =>
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