package scalanizer.linalgebra {
  package implOfLinearAlgebraOps {
    object StagedEvaluation {
      import scalan._;
      import scalanizer.implOfNums.StagedEvaluation._;
      import scalanizer.implOfNumMonoids.StagedEvaluation._;
      import scalanizer.linalgebra.implOfVecs.StagedEvaluation._;
      import scalanizer.linalgebra.implOfMatrs.StagedEvaluation._;
      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      import scalan.compilation.KernelTypes._;
      import scalan.meta.ScalanAst._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait LinearAlgebraOpsAbs extends LinearAlgebraOps with ScalanDsl { self: LinearAlgebraDsl =>
        implicit def proxyLinearAlgebraOp(p: Rep[LinearAlgebraOp]): LinearAlgebraOp = proxyOps[LinearAlgebraOp](p)(scala.reflect.classTag[LinearAlgebraOp]);
        class LinearAlgebraOpElem[To <: LinearAlgebraOp] extends EntityElem[To] {
          lazy val parent: Option[(Elem[_$1] forSome { 
            type _$1
          })] = None;
          lazy val entityDef: STraitOrClassDef = {
            val module = getModules("LinearAlgebraOps");
            module.entities.find(((x$1) => __equal(x$1.name, "LinearAlgebraOp"))).get
          };
          lazy val tyArgSubst: Map[String, TypeDesc] = Map();
          override def isEntityType = true;
          override lazy val tag = weakTypeTag[LinearAlgebraOp].asInstanceOf[WeakTypeTag[To]];
          override def convert(x: Rep[(Reifiable[_$2] forSome { 
            type _$2
          })]) = {
            implicit val eTo: Elem[To] = this;
            val conv = fun(((x: Rep[LinearAlgebraOp]) => convertLinearAlgebraOp(x)));
            tryConvert(element[LinearAlgebraOp], this, x, conv)
          };
          def convertLinearAlgebraOp(x: Rep[LinearAlgebraOp]): Rep[To] = {
            assert(x.selfType1 match {
              case ((_): LinearAlgebraOpElem[(_)]) => true
              case _ => false
            });
            x.asRep[To]
          };
          override def getDefaultRep: Rep[To] = ???
        };
        implicit def linearAlgebraOpElement: Elem[LinearAlgebraOp] = new LinearAlgebraOpElem[LinearAlgebraOp]();
        implicit case object LinearAlgebraOpCompanionElem extends CompanionElem[LinearAlgebraOpCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[LinearAlgebraOpCompanionAbs];
          protected def getDefaultRep = LinearAlgebraOp
        };
        abstract class LinearAlgebraOpCompanionAbs extends CompanionBase[LinearAlgebraOpCompanionAbs] with LinearAlgebraOpCompanion {
          override def toString = "LinearAlgebraOp"
        };
        def LinearAlgebraOp: Rep[LinearAlgebraOpCompanionAbs];
        implicit def proxyLinearAlgebraOpCompanion(p: Rep[LinearAlgebraOpCompanion]): LinearAlgebraOpCompanion = proxyOps[LinearAlgebraOpCompanion](p);
        class LAElem(val iso: Iso[LAData, LA]) extends LinearAlgebraOpElem[LA] with ConcreteElem[LAData, LA] {
          override lazy val parent: Option[(Elem[_$3] forSome { 
            type _$3
          })] = Some(linearAlgebraOpElement);
          override lazy val entityDef = {
            val module = getModules("LinearAlgebraOps");
            module.concreteSClasses.find(((x$2) => __equal(x$2.name, "LA"))).get
          };
          override lazy val tyArgSubst: Map[String, TypeDesc] = Map();
          override def convertLinearAlgebraOp(x: Rep[LinearAlgebraOp]) = LA();
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = weakTypeTag[LA]
        };
        type LAData = Unit;
        class LAIso extends Iso[LAData, LA] {
          override def from(p: Rep[LA]) = ();
          override def to(p: Rep[Unit]) = {
            val unit = p;
            LA()
          };
          lazy val defaultRepTo: Rep[LA] = LA();
          lazy val eTo = new LAElem(this)
        };
        abstract class LACompanionAbs extends CompanionBase[LACompanionAbs] with LACompanion {
          override def toString = "LA";
          def apply(p: Rep[LAData]): Rep[LA] = isoLA.to(p);
          def apply(): Rep[LA] = mkLA()
        };
        object LAMatcher {
          def unapply(p: Rep[LinearAlgebraOp]) = unmkLA(p)
        };
        def LA: Rep[LACompanionAbs];
        implicit def proxyLACompanion(p: Rep[LACompanionAbs]): LACompanionAbs = proxyOps[LACompanionAbs](p);
        implicit case object LACompanionElem extends CompanionElem[LACompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[LACompanionAbs];
          protected def getDefaultRep = LA
        };
        implicit def proxyLA(p: Rep[LA]): LA = proxyOps[LA](p);
        implicit class ExtendedLA(p: Rep[LA]) {
          def toData: Rep[LAData] = isoLA.from(p)
        };
        implicit def isoLA: Iso[LAData, LA] = new LAIso();
        def mkLA(): Rep[LA];
        def unmkLA(p: Rep[LinearAlgebraOp]): Option[Rep[Unit]];
        registerModule(scalan.meta.ScalanCodegen.loadModule(LinearAlgebraOps_Module.dump))
      };
      trait LinearAlgebraOpsSeq extends LinearAlgebraOpsDsl with ScalanSeq { self: LinearAlgebraDslSeq =>
        lazy val LinearAlgebraOp: Rep[LinearAlgebraOpCompanionAbs] = {
          final class $anon extends LinearAlgebraOpCompanionAbs with UserTypeSeq[LinearAlgebraOpCompanionAbs] {
            lazy val selfType = element[LinearAlgebraOpCompanionAbs]
          };
          new $anon()
        };
        case class SeqLA() extends LA() with UserTypeSeq[LA] {
          lazy val selfType = element[LA]
        };
        lazy val LA = {
          final class $anon extends LACompanionAbs with UserTypeSeq[LACompanionAbs] {
            lazy val selfType = element[LACompanionAbs]
          };
          new $anon()
        };
        def mkLA(): Rep[LA] = new SeqLA();
        def unmkLA(p: Rep[LinearAlgebraOp]) = p match {
          case (p @ ((_): LA @unchecked)) => Some(())
          case _ => None
        }
      };
      trait LinearAlgebraOpsExp extends LinearAlgebraOpsDsl with ScalanExp { self: LinearAlgebraDslExp =>
        lazy val LinearAlgebraOp: Rep[LinearAlgebraOpCompanionAbs] = {
          final class $anon extends LinearAlgebraOpCompanionAbs with UserTypeDef[LinearAlgebraOpCompanionAbs] {
            lazy val selfType = element[LinearAlgebraOpCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        case class ExpLA() extends LA() with UserTypeDef[LA] {
          lazy val selfType = element[LA];
          override def mirror(t: Transformer) = ExpLA()
        };
        lazy val LA: Rep[LACompanionAbs] = {
          final class $anon extends LACompanionAbs with UserTypeDef[LACompanionAbs] {
            lazy val selfType = element[LACompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object LAMethods {
          object mvm {
            def unapply(d: (Def[_$4] forSome { 
              type _$4
            })): Option[(scala.Tuple6[Rep[LA], Rep[Matr[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((matrix @ _), (vector @ _), (n @ _), (m @ _), (emT @ _), _*), _) if receiver.elem.isInstanceOf[LAElem].&&(__equal(method.getName, "mvm")) => Some(scala.Tuple6(receiver, matrix, vector, n, m, emT)).asInstanceOf[Option[(scala.Tuple6[Rep[LA], Rep[Matr[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$5] forSome { 
              type _$5
            })): Option[(scala.Tuple6[Rep[LA], Rep[Matr[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object LACompanionMethods {
          object ddmvm {
            def unapply(d: (Def[_$6] forSome { 
              type _$6
            })): Option[scala.Tuple2[Rep[Array[Array[Double]]], Rep[Array[Double]]]] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((m @ _), (v @ _), _*), _) if __equal(receiver.elem, LACompanionElem).&&(__equal(method.getName, "ddmvm")) => Some(scala.Tuple2(m, v)).asInstanceOf[Option[scala.Tuple2[Rep[Array[Array[Double]]], Rep[Array[Double]]]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$7] forSome { 
              type _$7
            })): Option[scala.Tuple2[Rep[Array[Array[Double]]], Rep[Array[Double]]]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        def mkLA(): Rep[LA] = new ExpLA();
        def unmkLA(p: Rep[LinearAlgebraOp]) = p.elem.asInstanceOf[(Elem[_$8] forSome { 
          type _$8
        })] match {
          case ((_): LAElem @unchecked) => Some(())
          case _ => None
        };
        object LinearAlgebraOpMethods {
          object mvm {
            def unapply(d: (Def[_$9] forSome { 
              type _$9
            })): Option[(scala.Tuple6[Rep[LinearAlgebraOp], Rep[Matr[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((matrix @ _), (vector @ _), (n @ _), (m @ _), (emT @ _), _*), _) if receiver.elem.isInstanceOf[(LinearAlgebraOpElem[_$10] forSome { 
  type _$10
})].&&(__equal(method.getName, "mvm")) => Some(scala.Tuple6(receiver, matrix, vector, n, m, emT)).asInstanceOf[Option[(scala.Tuple6[Rep[LinearAlgebraOp], Rep[Matr[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$11] forSome { 
              type _$11
            })): Option[(scala.Tuple6[Rep[LinearAlgebraOp], Rep[Matr[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object LinearAlgebraOpCompanionMethods
      };
      object LinearAlgebraOps_Module {
        val packageName = "scalanizer.linalgebra";
        val name = "LinearAlgebraOps";
        val dump = "H4sIAAAAAAAAALVUMW8TMRR2rqHpJVFbkKhahFSoAkgVJBVLh06hDQjp2lS9DihUSM7FSV3ufFfbRQlDB0bYECtC3bux8AeQEAMrAzNTgaECOoF49l3ukqgRLNxwsp/t9773fZ99+BWdERxdFQ52MSt6ROKircdlIQt2hUkqO6t+Y88lK6T5ZOqNs8puCQNN1NDoNhYrwq0hMxxU2kE8tsmuhUzMHCKkz4VEly1doeT4rkscSX1Wop63J3HdJSWLCrlkoXTdb3R20T5KWWjS8ZnDiST2souFICKKjxGFiMZzU8871SCpwUqqi1JPF5scUwnwocZkuH+DBHaH+azjSTQeQasGChbsyVAv8LnslshAum2/0Z2mGYYAOmft4Ee4BCVaJVtyylpwMhdg5yFukTXYoranAbAgbnOzE+j5iIWyguwCQXe9wNWRdoAQAgVuahDFhJ9izE9R8VOwCafYpY+xWlznfruDwi81glA7gBTX/5Kim4FUWKPwdMu5f2LnPEMdbisoGd3hKCSaHeIGLQXw+G7juTi+c7BooGwNZako14Xk2JG9kkds5TBjvtSYYwIxb4Fac8PU0lXKsGfAEqbjewFmkCmiMg86udShUm1WsXykzhDqMzIg3a0p4D3u99KQfrVvlrHrrh/N3LjypXLPQEZ/CRNS2mB83k0q0YRFGcG87LZIneNqkFA7IVEGhGvsObIvmI+lBaX0ivqZkSjD4cVEXTv61ni7gLaMmN4ITZxMpcmG9rB9j5ydO6YPDp5JfSDV7ndztb4D9llqc5QPT4QX4zdd/PVpvCmNyG3DTNK9bD9qC8b3mY+vDGTW0FidSg8HhYV/tMj/lD0hpctMLuxzzWekcHu98NN+/+JQtanWL4CkOau83AtiNpFvSg8l+KKcrITyRfZS/4uoZwKKmxuENqmSuz9+qncSM0SWSKpPR9kHWtJIAPf0QLpTmtAl5gehq3+hPwg9Tg6kg0t8PjQBvCm86FKGw6UIG0dzQzxiRwqBTfZPXq7Nf3j9WV+trNIaVGCy77XVwoLp+rjqRwNPag9+idLKBLqDP8IyJYniBgAA"
      };
      trait LinearAlgebraOps extends Base { self: LinearAlgebraDsl =>
        trait LinearAlgebraOp extends Reifiable[LinearAlgebraOp] {
          def mvm[T](matrix: Rep[Matr[T]], vector: Rep[Vec[T]])(n: Rep[Num[T]], m: Rep[NumMonoid[T]])(implicit emT: Elem[T]): Rep[Vec[T]]
        };
        abstract class LA extends LinearAlgebraOp with Product with Serializable {
          def mvm[T](matrix: Rep[Matr[T]], vector: Rep[Vec[T]])(n: Rep[Num[T]], m: Rep[NumMonoid[T]])(implicit emT: Elem[T]): Rep[Vec[T]] = DenseVec(matrix.rows.map(fun(((r: Rep[Vec[T]]) => r.dot(vector)(n, m)))))
        };
        trait LinearAlgebraOpCompanion;
        trait LACompanion {
          @HotSpot(CppKernel) def ddmvm(m: Rep[Array[Array[Double]]], v: Rep[Array[Double]]): Rep[Array[Double]] = {
            val doubleNumer: Rep[Num[Double]] = DoubleNum();
            val plusMonoid: Rep[NumMonoid[Double]] = PlusMonoid[Double](doubleNumer);
            val width = m(toRep(0)).length;
            val matrix: Rep[Matr[Double]] = DenseMatr(Col(array_map(m, fun(((r: Rep[Array[Double]]) => DenseVec(Col(r)))))), width);
            val vector: Rep[Vec[Double]] = DenseVec(Col(v));
            LA().mvm(matrix, vector)(doubleNumer, plusMonoid).items.arr
          }
        }
      };
      trait LinearAlgebraOpsDsl extends LinearAlgebraOpsAbs { self: LinearAlgebraDsl =>
        
      };
      trait LinearAlgebraOpsDslSeq extends LinearAlgebraOpsSeq { self: LinearAlgebraDslSeq =>
        
      };
      trait LinearAlgebraOpsDslExp extends LinearAlgebraOpsExp { self: LinearAlgebraDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZoIWuGNNbkJzAgAPWgAGaGFzRHNsWgAJaGFzRHNsRXhwWgAJaGFzRHNsU2VxTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwAAAAc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZshSknPyR6E3AgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AA9MaW5lYXJBbGdlYnJhT3BxAH4AB3NxAH4AD3QAB1Byb2R1Y3RxAH4AB3NxAH4AD3QA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cQB+AF50AAhEZW5zZVZlY3EAfgAHdAAGdmVjdG9yc3EAfgBCc3EAfgAPdAADVmVjc3EAfgAGcQB+AJhxAH4ACXhxAH4ACXhzcQB+AGZzcQB+AGZzcQB+AEVzcQB+AAZzcQB+AAZzcQB+AF50AAZtYXRyaXhzcQB+AF50AAZ2ZWN0b3JxAH4ACXhzcQB+AAZzcQB+AF50AAtkb3VibGVOdW1lcnNxAH4AXnQACnBsdXNNb25vaWRxAH4ACXhxAH4ACXhzcQB+AGZzcQB+AEVzcQB+AAZxAH4AB3EAfgAJeHNxAH4AXnQAAkxBcQB+AAd0AANtdm1xAH4AB3QABWl0ZW1zdAADYXJydAAFZGRtdm1xAH4AJHEAfgAHc3EAfgBCc3EAfgAPdAAFQXJyYXlzcQB+AAZxAH4AmHEAfgAJeHEAfgAJeHQAAkxBc3EAfgAWcQB+AAd0AAJMQXEAfgAkcQB+AAdxAH4ACXhzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdERlZvVaMAL0GdefAgAJWgAIYml0bWFwJDBMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAMTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgAHcQB+AAdzcQB+AAZzcQB+ABkAAABxAH4AB3NxAH4ABnNxAH4AHHNxAH4ABnNxAH4AHwAAAHEAfgAHcQB+ACR0AAZtYXRyaXhzcQB+AA90AARNYXRyc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4c3EAfgAfAAAAcQB+AAdxAH4AJHQABnZlY3RvcnNxAH4AD3QAA1ZlY3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHNxAH4AHHNxAH4ABnNxAH4AHwAAAHEAfgAHcQB+ACR0AAFuc3EAfgAPdAADTnVtc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4c3EAfgAfAAAAcQB+AAdxAH4AJHQAAW1zcQB+AA90AAlOdW1Nb25vaWRzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4AJHQAA212bXEAfgAkc3EAfgAGc3EAfgB1AAAAAAAAIABxAH4AJHNxAH4ABnQACENsYXNzVGFncQB+AAl4dAABVHEAfgAHcQB+AAl4c3EAfgBCc3EAfgAPdAADVmVjc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+ACRwdAAPTGluZWFyQWxnZWJyYU9wcQB+ACRxAH4AB3EAfgAJeHEAfgExcQB+ACRzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJbXBvcnRTdGF0rICgSsPGGyICAAFMAARuYW1lcQB+AAR4cHQAFnNjYWxhLnJlZmxlY3QuQ2xhc3NUYWdzcQB+AWN0AA5zY2FsYW4uSG90U3BvdHNxAH4BY3QAIHNjYWxhbi5jb21waWxhdGlvbi5LZXJuZWxUeXBlcy5fcQB+AAl4cQB+AAd0ABBMaW5lYXJBbGdlYnJhT3BzdAAVc2NhbGFuaXplci5saW5hbGdlYnJhc3EAfgBCc3IAInNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTU2VsZlR5cGVEZWZ+951OKs6z5QIAAkwACmNvbXBvbmVudHNxAH4AAUwABG5hbWVxAH4ABHhwc3EAfgAGc3EAfgAPdAANTGluZWFyQWxnZWJyYXEAfgAHcQB+AAl4dAAEc2VsZnEAfgAk"
    }

    object HotSpotKernels {
      import java.io.File;
      import scalan.compilation.GraphVizConfig;
      import scala.language.reflectiveCalls;
      lazy val ddmvmKernel = {
        val ctx = HotSpotManager.getScalanContextUni;
        val config = new ctx.CompilerConfig(Some("2.11.2"), Seq.empty);
        val compilerOutput = ctx.buildExecutable(new File("./it-out/".+("ddmvm")), "ddmvm", ctx.scalan.ddmvmWrapper, GraphVizConfig.default)(config);
        val x$1 = (ctx.loadMethod(compilerOutput): @scala.unchecked) match {
          case scala.Tuple2((cls @ _), (method @ _)) => scala.Tuple2(cls, method)
        };
        val cls = x$1._1;
        val method = x$1._2;
        val instance = cls.newInstance().asInstanceOf[scala.Function1[scala.Tuple2[Array[Array[Double]], Array[Double]], Array[Double]]];
        instance
      }
    }

    object HotSpotManager {
      import scalan.ScalanCommunityDslExp;
      import scalan.compilation.lms.scalac.CommunityLmsCompilerScala;
      import scalan.{CommunityMethodMappingDSL, JNIExtractorOpsExp};
      import scalan.compilation.lms.CommunityBridge;
      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      lazy val prog = {
        final class $anon extends LinearAlgebraDslExp with ScalanCommunityDslExp with JNIExtractorOpsExp;
        new $anon()
      };
      lazy val compiler = {
        final class $anon extends CommunityLmsCompilerScala(prog) with CommunityBridge with CommunityMethodMappingDSL;
        new $anon()
      };
      def getScalanContext = compiler;
      import scalan.compilation.lms.uni.LmsCompilerUni;
      lazy val progUni = {
        final class $anon extends LinearAlgebraDslExp with ScalanCommunityDslExp with JNIExtractorOpsExp {
          lazy val ddmvmWrapper = fun(((in: Rep[scala.Tuple2[Array[Array[Double]], Array[Double]]]) => {
            val m: Rep[Array[Array[Double]]] = in._1;
            val v: Rep[Array[Double]] = in._2;
            LA.ddmvm(m, v)
          }))
        };
        new $anon()
      };
      lazy val compilerUni = {
        final class $anon extends LmsCompilerUni(progUni) with CommunityBridge with CommunityMethodMappingDSL;
        new $anon()
      };
      def getScalanContextUni = compilerUni
    }
  }
}