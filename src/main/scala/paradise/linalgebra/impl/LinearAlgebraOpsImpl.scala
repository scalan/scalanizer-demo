package paradise.linalgebra {
  package implOfLinearAlgebraOps {
    object StagedEvaluation {
      import scalan._;
      import paradise.implOfNumers.StagedEvaluation._;
      import paradise.implOfNumMonoids.StagedEvaluation._;
      import paradise.linalgebra.implOfVectors.StagedEvaluation._;
      import paradise.linalgebra.implOfMatrices.StagedEvaluation._;
      import paradise.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait LinearAlgebraOpsAbs extends LinearAlgebraOps with ScalanDsl { self: LinearAlgebraDsl =>
        implicit def proxyLinearAlgebraOp(p: Rep[LinearAlgebraOp]): LinearAlgebraOp = proxyOps[LinearAlgebraOp](p)(classTag[LinearAlgebraOp]);
        class LinearAlgebraOpElem[To <: LinearAlgebraOp] extends EntityElem[To] {
          override def isEntityType = true;
          override lazy val tag = weakTypeTag[LinearAlgebraOp].asInstanceOf[WeakTypeTag[To]];
          override def convert(x: Rep[(Reifiable[_$1] forSome { 
            type _$1
          })]) = {
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
          lazy val defaultRepTo = Default.defaultVal[Rep[LA]](LA());
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
        def unmkLA(p: Rep[LinearAlgebraOp]): Option[Rep[Unit]]
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
            def unapply(d: (Def[_$2] forSome { 
              type _$2
            })): Option[(scala.Tuple6[Rep[LA], Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((matrix @ _), (vector @ _), (n @ _), (m @ _), (emT @ _), _*), _) if receiver.elem.isInstanceOf[LAElem].&&(method.getName.==("mvm")) => Some(scala.Tuple6(receiver, matrix, vector, n, m, emT)).asInstanceOf[Option[(scala.Tuple6[Rep[LA], Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$3] forSome { 
              type _$3
            })): Option[(scala.Tuple6[Rep[LA], Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object LACompanionMethods {
          object ddmvm0 {
            def unapply(d: (Def[_$4] forSome { 
              type _$4
            })): Option[scala.Tuple2[Rep[Array[Array[Double]]], Rep[Array[Double]]]] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((m @ _), (v @ _), _*), _) if receiver.elem.==(LACompanionElem).&&(method.getName.==("ddmvm0")) => Some(scala.Tuple2(m, v)).asInstanceOf[Option[scala.Tuple2[Rep[Array[Array[Double]]], Rep[Array[Double]]]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$5] forSome { 
              type _$5
            })): Option[scala.Tuple2[Rep[Array[Array[Double]]], Rep[Array[Double]]]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        def mkLA(): Rep[LA] = new ExpLA();
        def unmkLA(p: Rep[LinearAlgebraOp]) = p.elem.asInstanceOf[(Elem[_$6] forSome { 
          type _$6
        })] match {
          case ((_): LAElem @unchecked) => Some(())
          case _ => None
        };
        object LinearAlgebraOpMethods {
          object mvm {
            def unapply(d: (Def[_$7] forSome { 
              type _$7
            })): Option[(scala.Tuple6[Rep[LinearAlgebraOp], Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((matrix @ _), (vector @ _), (n @ _), (m @ _), (emT @ _), _*), _) if receiver.elem.isInstanceOf[(LinearAlgebraOpElem[_$8] forSome { 
  type _$8
})].&&(method.getName.==("mvm")) => Some(scala.Tuple6(receiver, matrix, vector, n, m, emT)).asInstanceOf[Option[(scala.Tuple6[Rep[LinearAlgebraOp], Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$9] forSome { 
              type _$9
            })): Option[(scala.Tuple6[Rep[LinearAlgebraOp], Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object LinearAlgebraOpCompanionMethods
      };
      trait LinearAlgebraOps extends Base { self: LinearAlgebraDsl =>
        trait LinearAlgebraOp extends Reifiable[LinearAlgebraOp] {
          def mvm[T](matrix: Rep[AbstractMatrix[T]], vector: Rep[AbstractVector[T]])(implicit n: Numer[T], m: NumMonoid[T], emT: Elem[T]): Rep[AbstractVector[T]]
        };
        abstract class LA extends LinearAlgebraOp with Product with Serializable {
          def mvm[T](matrix: Rep[AbstractMatrix[T]], vector: Rep[AbstractVector[T]])(implicit n: Numer[T], m: NumMonoid[T], emT: Elem[T]): Rep[AbstractVector[T]] = DenseVector(matrix.rows.map(fun(((r: Rep[AbstractVector[T]]) => r.dot(vector)))))
        };
        trait LinearAlgebraOpCompanion;
        trait LACompanion {
          @HotSpot def ddmvm0(m: Rep[Array[Array[Double]]], v: Rep[Array[Double]]): Rep[Array[Double]] = {
            implicit val doubleNumer: Numer[Double] = DoubleNumer();
            implicit val plusMonoid: NumMonoid[Double] = PlusMonoid[Double];
            val width = m(toRep(0)).length;
            val matrix: Rep[AbstractMatrix[Double]] = CompoundMatrix(Collection(array_map(m,fun(((r: Rep[Array[Double]]) => DenseVector(Collection(r)))))), width);
            val vector: Rep[AbstractVector[Double]] = DenseVector(Collection(v));
            LA().mvm(matrix, vector).items.arr
          }
          lazy val ddmvm0HotSpot = fun(((in: Rep[scala.Tuple2[Array[Array[Double]], Array[Double]]]) => {
            ddmvm0(in._1, in._2)
          }))
        }
      };
      trait LinearAlgebraOpsDsl extends LinearAlgebraOpsAbs { self: LinearAlgebraDsl =>
        
      };
      trait LinearAlgebraOpsDslSeq extends LinearAlgebraOpsSeq { self: LinearAlgebraDslSeq =>
        
      };
      trait LinearAlgebraOpsDslExp extends LinearAlgebraOpsExp { self: LinearAlgebraDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AA9MaW5lYXJBbGdlYnJhT3BxAH4AB3NxAH4AD3QAB1Byb2R1Y3RxAH4AB3NxAH4AD3QADFNlcmlhbGl6YWJsZXEAfgAHcQB+AAl4cQB+AAdzcgAgc2Nh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cQB+AAdxAH4ACXhzcQB+AEJzcQB+AA90AA5BYnN0cmFjdFZlY3RvcnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHNxAH4AQnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU09iamVjdERlZoBV0ZN0CXpjAgADTAAJYW5jZXN0b3JzcQB+AAFMAARib2R5cQB+AAFMAARuYW1lcQB+AAR4cHEAfgAHc3EAfgAGc3EAfgAZAAAAc3EAfgAGc3IAJ3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kQW5ub3RhdGlvbi/2wXSiBBdrAgACTAAPYW5ub3RhdGlvbkNsYXNzcQB+AARMAARhcmdzcQB+AAF4cHQAB0hvdFNwb3RxAH4AB3EAfgAJeHNxAH4ABnNxAH4AHHNxAH4ABnNxAH4AHwAAAHEAfgAHcQB+ACR0AAFtc3EAfgAPdAAFQXJyYXlzcQB+AAZzcQB+AA90AAVBcnJheXNxAH4ABnNyACNzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZVByaW1pdGl2ZTXDu1Xwvk4FAgACTAASZGVmYXVsdFZhbHVlU3RyaW5ncQB+AARMAARuYW1lcQB+AAR4cHQAAzAuMHQABkRvdWJsZXEAfgAJeHEAfgAJeHNxAH4AHwAAAHEAfgAHcQB+ACR0AAF2c3EAfgAPdAAFQXJyYXlzcQB+AAZxAH4AkHEAfgAJeHEAfgAJeHEAfgAJeHNxAH4AQnNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0Jsb2NrVS3qZnEUNP0CAAJMAARpbml0cQB+AAFMAARsYXN0cQB+AEZ4cHNxAH4ABnNxAH4AUAEAc3EAfgBFc3EAfgAGcQB+AAdxAH4ACXhzcQB+AF50AAtEb3VibGVOdW1lcnEAfgAHdAALZG91YmxlTnVtZXJzcQB+AEJzcQB+AA90AAVOdW1lcnNxAH4ABnEAfgCQcQB+AAl4c3EAfgBQAQBzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUeXBlQXBwbHka36/N9eX6UAIAAkwAA2Z1bnEAfgBGTAACdHNxAH4AAXhwc3EAfgBedAAKUGx1c01vbm9pZHNxAH4ABnEAfgCQcQB+AAl4dAAKcGx1c01vbm9pZHNxAH4AQnNxAH4AD3QACU51bU1vbm9pZHNxAH4ABnEAfgCQcQB+AAl4c3EAfgBQAABzcQB+AGFzcQB+AEVzcQB+AAZzcQB+AAZzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDb25zdBpNFNFiS2eKAgABTAABY3EAfgBDeHBzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAHEAfgAJeHEAfgAJeHNxAH4AXnQAAW1xAH4AB3QABmxlbmd0aHQABXdpZHRocQB+ACRzcQB+AFAAAHNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ATXNxAH4ABnNxAH4AUAAAc3EAfgBSdAABcnNxAH4AQnNxAH4AD3QABUFycmF5c3EAfgAGcQB+AJBxAH4ACXhxAH4ACXhzcQB+AEVzcQB+AAZzcQB+AAZzcQB+AEVzcQB+AAZzcQB+AAZzcQB+AF50AAFycQB+AAl4cQB+AAl4c3EAfgBedAAKQ29sbGVjdGlvbnEAfgAHcQB+AAl4cQB+AAl4c3EAfgBedAALRGVuc2VWZWN0b3JxAH4AB3EAfgAJeHEAfgAJeHNxAH4AYXNxAH4AXnQAAW1xAH4Aa3EAfgAHcQB+AAl4cQB+AAl4c3EAfgBedAAKQ29sbGVjdGlvbnEAfgAHc3EAfgBedAAFd2lkdGhxAH4ACXhxAH4ACXhzcQB+AF50AA5Db21wb3VuZE1hdHJpeHEAfgAHdAAGbWF0cml4c3EAfgBCc3EAfgAPdAAOQWJzdHJhY3RNYXRyaXhzcQB+AAZxAH4AkHEAfgAJeHNxAH4AUAAAc3EAfgBFc3EAfgAGc3EAfgAGc3EAfgBFc3EAfgAGc3EAfgAGc3EAfgBedAABdnEAfgAJeHEAfgAJeHNxAH4AXnQACkNvbGxlY3Rpb25xAH4AB3EAfgAJeHEAfgAJeHNxAH4AXnQAC0RlbnNlVmVjdG9ycQB+AAd0AAZ2ZWN0b3JzcQB+AEJzcQB+AA90AA5BYnN0cmFjdFZlY3RvcnNxAH4ABnEAfgCQcQB+AAl4cQB+AAl4c3EAfgBhc3EAfgBhc3EAfgBFc3EAfgAGc3EAfgAGc3EAfgBedAAGbWF0cml4c3EAfgBedAAGdmVjdG9ycQB+AAl4cQB+AAl4c3EAfgBhc3EAfgBFc3EAfgAGcQB+AAdxAH4ACXhzcQB+AF50AAJMQXEAfgAHdAADbXZtcQB+AAd0AAVpdGVtc3QAA2FycnQABmRkbXZtMHEAfgAkcQB+AAdzcQB+AEJzcQB+AA90AAVBcnJheXNxAH4ABnEAfgCQcQB+AAl4cQB+AAl4dAACTEFzcQB+ABZxAH4AB3QAAkxBcQB+ACRxAH4AB3EAfgAJeHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0RGVmAb40kjQUsbICAAlaAAhiaXRtYXAkMEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+AAxMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAcQB+AAdxAH4AB3NxAH4ABnNxAH4AGQAAAHEAfgAHc3EAfgAGc3EAfgAcc3EAfgAGc3EAfgAfAAAAcQB+AAdxAH4AJHQABm1hdHJpeHNxAH4AD3QADkFic3RyYWN0TWF0cml4c3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4c3EAfgAfAAAAcQB+AAdxAH4AJHQABnZlY3RvcnNxAH4AD3QADkFic3RyYWN0VmVjdG9yc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4c3EAfgAcc3EAfgAGc3EAfgAfAQAAcQB+AAdxAH4AJHQAAW5zcQB+AA90AAVOdW1lcnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHNxAH4AHwEAAHEAfgAHcQB+ACR0AAFtc3EAfgAPdAAJTnVtTW9ub2lkc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4cQB+ACR0AANtdm1xAH4AJHNxAH4ABnNxAH4AcHEAfgAkc3EAfgAGdAAIQ2xhc3NUYWdxAH4ACXh0AAFUcQB+AAdxAH4ACXhzcQB+AEJzcQB+AA90AA5BYnN0cmFjdFZlY3RvcnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgAkcHQAD0xpbmVhckFsZ2VicmFPcHEAfgAkcQB+AAdxAH4ACXhxAH4BGXEAfgAkc3EAfgAGc3IAIXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTSW1wb3J0U3RhdKyAoErDxhsiAgABTAAEbmFtZXEAfgAEeHB0ABZzY2FsYS5yZWZsZWN0LkNsYXNzVGFnc3EAfgFLdAAOc2NhbGFuLkhvdFNwb3RxAH4ACXhxAH4AB3QAEExpbmVhckFsZ2VicmFPcHN0ABNwYXJhZGlzZS5saW5hbGdlYnJhc3EAfgBCc3IAInNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTU2VsZlR5cGVEZWZ+951OKs6z5QIAAkwACmNvbXBvbmVudHNxAH4AAUwABG5hbWVxAH4ABHhwc3EAfgAGc3EAfgAPdAANTGluZWFyQWxnZWJyYXEAfgAHcQB+AAl4dAAEc2VsZnEAfgAk"
    }
    object HotSpotLoaders {
      def ddmvm0: (Array[Array[Double]], Array[Double]) => Array[Double] = {
        /** 0. Check the cache.
          * 1. Compilation of ddmvm0HotSpot by Scalan compiler to a kernel.
          * 2. Compilation of the kernel by Scala compiler.
          * 3. Load the kernel and execute it.
          * 4. Save result to cache. */
        throw new NotImplementedError("Loading of ddmvm0 ...")
      }
    }
  }
}