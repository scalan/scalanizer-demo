//package scalanizer.linalgebra {
//  package implOfLinearAlgebraOps {
//    object StagedEvaluation {
//      import scalan._;
//
//      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
//      import scalan.compilation.KernelTypes._;
//      import scala.reflect.runtime.universe._;
//      import scala.reflect._;
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
//            })): Option[scala.Tuple2[Rep[Array[Array[Double]]], Rep[Array[Double]]]] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((m @ _), (v @ _), _*), _) if __equal(receiver.elem, LACompanionElem).&&(__equal(method.getName, "ddmvm")) => Some(scala.Tuple2(m, v)).asInstanceOf[Option[scala.Tuple2[Rep[Array[Array[Double]]], Rep[Array[Double]]]]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$3] forSome {
//              type _$3
//            })): Option[scala.Tuple2[Rep[Array[Array[Double]]], Rep[Array[Double]]]] = exp match {
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
//          def ddmvm(m: Rep[Array[Array[Double]]], v: Rep[Array[Double]]): Rep[Array[Double]] = {
//            val doubleNumer: Rep[WNum[Double]] = WDoubleNum()
//            v
//          }
//        }
//      };
//      trait LinearAlgebraOpsDsl extends LinearAlgebraOpsAbs { self: LinearAlgebraDsl =>
//
//      };
//      trait LinearAlgebraOpsDslExp extends LinearAlgebraOpsExp { self: LinearAlgebraDslExp =>
//
//      };
//      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZt3ynp7b43WYAgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AA9MaW5lYXJBbGdlYnJhT3BxAH4AB3EAfgAJeHEAfgAHc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NBcmdzJ+vuZMUwAFwCAAFMAARhcmdzcQB+AAF4cHEAfgAHcQB+AAdzcgAKc2NhbGEuU29tZREi8mleoYt0AgABTAABeHQAEkxqYXZhL2xhbmcvT2JqZWN0O3hyAAxzY2FsYS5PcHRpb27+aTf92w5mdAIAAHhwc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTT2JqZWN0RGVmgFXRk3QJemMCAANMAAlhbmNlc3RvcnNxAH4AAUwABGJvZHlxAH4AAUwABG5hbWVxAH4ABHhwcQB+AAdzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2REZWav430+HeZSUwIACloADGlzRWxlbU9yQ29udFoACmlzSW1wbGljaXRaAAppc092ZXJyaWRlTAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0cGVSZXNxAH4AA3hwAAAAcQB+AAdzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2RBcmdzKcInll51uosCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZwPc/8uTcnMDAgAHWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXQAIExzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RwZUV4cHI7eHAAAABxAH4AB3NyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHEAfgAWdAABbXNxAH4AD3QABUFycmF5c3EAfgAGc3EAfgAPdAAFQXJyYXlzcQB+AAZzcgAjc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVQcmltaXRpdmU1w7tV8L5OBQIAAkwAEmRlZmF1bHRWYWx1ZVN0cmluZ3EAfgAETAAEbmFtZXEAfgAEeHB0AAMwLjB0AAZEb3VibGVxAH4ACXhxAH4ACXhzcQB+ACEAAABxAH4AB3EAfgAldAABdnNxAH4AD3QABUFycmF5c3EAfgAGcQB+AC5xAH4ACXhxAH4ACXhxAH4ACXhzcQB+ABRzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNCbG9ja1Ut6mZxFDT9AgACTAAEaW5pdHEAfgABTAAEbGFzdHQAHUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU0V4cHI7eHBzcQB+AAZzcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNWYWxEZWYrBjm6e91FDwIABVoACmlzSW1wbGljaXRaAAZpc0xhenlMAARleHBycQB+ADhMAARuYW1lcQB+AARMAAN0cGVxAH4AA3hwAABzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDb250cuhRSdUaTzW1AgACTAAEYXJnc3EAfgABTAAEbmFtZXEAfgAEeHBxAH4AB3QAFHNjYWxhbml6ZXIuRG91YmxlTnVtdAALZG91YmxlTnVtZXJzcQB+ABRzcQB+AA90AANOdW1zcQB+AAZxAH4ALnEAfgAJeHEAfgAJeHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0lkZW50JxKl+/AJ108CAAFMAARuYW1lcQB+AAR4cHQAAXZ0AAVkZG12bXEAfgAlcQB+AAdzcQB+ABRzcQB+AA90AAVBcnJheXNxAH4ABnEAfgAucQB+AAl4cQB+AAl4dAACTEFzcQB+ABJxAH4AB3QAAkxBcQB+ACVxAH4AB3EAfgAJeHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0RGVmAb40kjQUsbICAAlaAAhiaXRtYXAkMEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+AAxMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAcQB+AAdxAH4AB3EAfgAHcQB+ACVwdAAPTGluZWFyQWxnZWJyYU9wcQB+ACVxAH4AB3EAfgAJeHEAfgBScQB+ACVzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJbXBvcnRTdGF0rICgSsPGGyICAAFMAARuYW1lcQB+AAR4cHQADHNjYWxhbml6ZXIuX3NxAH4AVXQAGHNjYWxhbml6ZXIuY29sbGVjdGlvbnMuX3NxAH4AVXQAFnNjYWxhLnJlZmxlY3QuQ2xhc3NUYWdzcQB+AFV0AA5zY2FsYW4uSG90U3BvdHNxAH4AVXQAIHNjYWxhbi5jb21waWxhdGlvbi5LZXJuZWxUeXBlcy5fcQB+AAl4cQB+AAd0ABBMaW5lYXJBbGdlYnJhT3BzdAAVc2NhbGFuaXplci5saW5hbGdlYnJhc3EAfgAUc3IAInNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTU2VsZlR5cGVEZWZ+951OKs6z5QIAAkwACmNvbXBvbmVudHNxAH4AAUwABG5hbWVxAH4ABHhwc3EAfgAGc3EAfgAPdAANTGluZWFyQWxnZWJyYXEAfgAHcQB+AAl4dAAEc2VsZnEAfgAl"
//    }
//
//    object HotSpotKernels {
//      import java.io.File;
//      import scalan.compilation.GraphVizConfig;
//      lazy val ddmvmKernel = {
//        val ctx = HotSpotManager.getScalanContextUni;
//        val compilerOutput = ctx.buildExecutable(new File("./it-out/".+("ddmvm")), "ddmvm", ctx.ddmvmWrapper, GraphVizConfig.default)(ctx.CompilerConfig(Some("2.11.2"), Seq.empty));
//        val x$1 = (ctx.loadMethod(compilerOutput): @scala.unchecked) match {
//          case scala.Tuple2((cls @ _), (method @ _)) => scala.Tuple2(cls, method)
//        };
//        val cls = x$1._1;
//        val method = x$1._2;
//        val instance = cls.newInstance().asInstanceOf[scala.Function1[scala.Tuple2[Array[Array[Double]], Array[Double]], Array[Double]]];
//        instance
//      }
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
//      class ScalanUni extends LinearAlgebraDslExp with LmsCompilerUni with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler with CommunityMethodMappingDSL {
//        lazy val ddmvmWrapper = fun(((in: Rep[scala.Tuple2[Array[Array[Double]], Array[Double]]]) => {
//          val m: Rep[Array[Array[Double]]] = in._1;
//          val v: Rep[Array[Double]] = in._2;
//          LA.ddmvm(m, v)
//        }))
//      }
//    }
//  }
//}