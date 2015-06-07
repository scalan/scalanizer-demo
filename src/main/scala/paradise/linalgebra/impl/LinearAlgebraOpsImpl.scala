package paradise.linalgebra {
  package implOfLinearAlgebraOps {
    object StagedEvaluation {
      import scalan._;
      import paradise.implOfNumers.StagedEvaluation._;
      import paradise.implOfNumMonoids.StagedEvaluation._;
      import paradise.linalgebra.implOfVecs.StagedEvaluation._;
      import paradise.linalgebra.implOfMatrs.StagedEvaluation._;
      import paradise.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      import scalan.Kernels._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait LinearAlgebraOpsAbs extends LinearAlgebraOps with ScalanDsl { self: LinearAlgebraDsl =>
        implicit def proxyLinearAlgebraOp(p: Rep[LinearAlgebraOp]): LinearAlgebraOp = proxyOps[LinearAlgebraOp](p)(scala.reflect.classTag[LinearAlgebraOp]);
        class LinearAlgebraOpElem[To <: LinearAlgebraOp] extends EntityElem[To] {
          override def isEntityType = true;
          override lazy val tag = weakTypeTag[LinearAlgebraOp].asInstanceOf[WeakTypeTag[To]];
          override def convert(x: Rep[(Reifiable[_$1] forSome { 
            type _$1
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
            })): Option[(scala.Tuple6[Rep[LA], Rep[Matr[T]], Rep[Vec[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((matrix @ _), (vector @ _), (n @ _), (m @ _), (emT @ _), _*), _) if receiver.elem.isInstanceOf[LAElem].&&(method.getName.==("mvm")) => Some(scala.Tuple6(receiver, matrix, vector, n, m, emT)).asInstanceOf[Option[(scala.Tuple6[Rep[LA], Rep[Matr[T]], Rep[Vec[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$3] forSome { 
              type _$3
            })): Option[(scala.Tuple6[Rep[LA], Rep[Matr[T]], Rep[Vec[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object LACompanionMethods {
          object ddmvm {
            def unapply(d: (Def[_$4] forSome { 
              type _$4
            })): Option[scala.Tuple2[Rep[Array[Array[Double]]], Rep[Array[Double]]]] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((m @ _), (v @ _), _*), _) if receiver.elem.==(LACompanionElem).&&(method.getName.==("ddmvm")) => Some(scala.Tuple2(m, v)).asInstanceOf[Option[scala.Tuple2[Rep[Array[Array[Double]]], Rep[Array[Double]]]]]
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
            })): Option[(scala.Tuple6[Rep[LinearAlgebraOp], Rep[Matr[T]], Rep[Vec[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((matrix @ _), (vector @ _), (n @ _), (m @ _), (emT @ _), _*), _) if receiver.elem.isInstanceOf[(LinearAlgebraOpElem[_$8] forSome { 
  type _$8
})].&&(method.getName.==("mvm")) => Some(scala.Tuple6(receiver, matrix, vector, n, m, emT)).asInstanceOf[Option[(scala.Tuple6[Rep[LinearAlgebraOp], Rep[Matr[T]], Rep[Vec[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$9] forSome { 
              type _$9
            })): Option[(scala.Tuple6[Rep[LinearAlgebraOp], Rep[Matr[T]], Rep[Vec[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
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
          def mvm[T](matrix: Rep[Matr[T]], vector: Rep[Vec[T]])(n: Rep[Numer[T]], m: Rep[NumMonoid[T]])(implicit emT: Elem[T]): Rep[Vec[T]]
        };
        abstract class LA extends LinearAlgebraOp with Product with Serializable {
          def mvm[T](matrix: Rep[Matr[T]], vector: Rep[Vec[T]])(n: Rep[Numer[T]], m: Rep[NumMonoid[T]])(implicit emT: Elem[T]): Rep[Vec[T]] = DenseVec(matrix.rows.map(fun(((r: Rep[Vec[T]]) => r.dot(vector)(n, m)))))
        };
        trait LinearAlgebraOpCompanion;
        trait LACompanion {
          @HotSpot(CppKernel) def ddmvm(m: Rep[Array[Array[Double]]], v: Rep[Array[Double]]): Rep[Array[Double]] = {
            val doubleNumer: Rep[Numer[Double]] = DoubleNumer();
            val plusMonoid: Rep[NumMonoid[Double]] = PlusMonoid[Double](doubleNumer);
            val width = m(toRep(0)).length;
            val matrix: Rep[Matr[Double]] = CompoundMatr(Col(array_map(m, fun(((r: Rep[Array[Double]]) => DenseVec(Col(r)))))), width);
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
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AA9MaW5lYXJBbGdlYnJhT3BxAH4AB3NxAH4AD3QAB1Byb2R1Y3RxAH4AB3NxAH4AD3QADFNlcmlhbGl6YWJsZXEAfgAHcQB+AAl4cQB+AAdzcgAgc2Nh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AAdxAH4ACXhzcQB+AEJzcQB+AA90AANWZWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+AEJzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNPYmplY3REZWaAVdGTdAl6YwIAA0wACWFuY2VzdG9yc3EAfgABTAAEYm9keXEAfgABTAAEbmFtZXEAfgAEeHBxAH4AB3NxAH4ABnNxAH4AGQAAAHNxAH4ABnNyACdzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFubm90YXRpb24v9sF0ogQXawIAAkwAD2Fubm90YXRpb25DbGFzc3EAfgAETAAEYXJnc3EAfgABeHB0AAdIb3RTcG90c3EAfgAGc3EAfgBedAAJQ3BwS2VybmVscQB+AAl4cQB+AAl4c3EAfgAGc3EAfgAcc3EAfgAGc3EAfgAfAAAAcQB+AAdxAH4AJHQAAW1zcQB+AA90AAVBcnJheXNxAH4ABnNxAH4AD3QABUFycmF5c3EAfgAGc3IAI3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlUHJpbWl0aXZlNcO7VfC+TgUCAAJMABJkZWZhdWx0VmFsdWVTdHJpbmdxAH4ABEwABG5hbWVxAH4ABHhwdAADMC4wdAAGRG91YmxlcQB+AAl4cQB+AAl4c3EAfgAfAAAAcQB+AAdxAH4AJHQAAXZzcQB+AA90AAVBcnJheXNxAH4ABnEAfgCYcQB+AAl4cQB+AAl4cQB+AAl4c3EAfgBCc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQmxvY2tVLepmcRQ0/QIAAkwABGluaXRxAH4AAUwABGxhc3RxAH4ARnhwc3EAfgAGc3EAfgBQAABzcQB+AEVzcQB+AAZxAH4AB3EAfgAJeHNxAH4AXnQAC0RvdWJsZU51bWVycQB+AAd0AAtkb3VibGVOdW1lcnNxAH4AQnNxAH4AD3QABU51bWVyc3EAfgAGcQB+AJhxAH4ACXhzcQB+AFAAAHNxAH4ARXNxAH4ABnNxAH4ABnNxAH4AXnQAC2RvdWJsZU51bWVycQB+AAl4cQB+AAl4c3EAfgBedAAKUGx1c01vbm9pZHNxAH4ABnEAfgCYcQB+AAl4dAAKcGx1c01vbm9pZHNxAH4AQnNxAH4AD3QACU51bU1vbm9pZHNxAH4ABnEAfgCYcQB+AAl4c3EAfgBQAABzcQB+AGZzcQB+AEVzcQB+AAZzcQB+AAZzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDb25zdBpNFNFiS2eKAgABTAABY3EAfgBDeHBzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAHEAfgAJeHEAfgAJeHNxAH4AXnQAAW1xAH4AB3QABmxlbmd0aHQABXdpZHRocQB+ACRzcQB+AFAAAHNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ATXNxAH4ABnNxAH4AUAAAc3EAfgBSdAABcnNxAH4AQnNxAH4AD3QABUFycmF5c3EAfgAGcQB+AJhxAH4ACXhxAH4ACXhzcQB+AEVzcQB+AAZzcQB+AAZzcQB+AEVzcQB+AAZzcQB+AAZzcQB+AF50AAFycQB+AAl4cQB+AAl4c3EAfgBedAADQ29scQB+AAdxAH4ACXhxAH4ACXhzcQB+AF50AAhEZW5zZVZlY3EAfgAHcQB+AAl4cQB+AAl4c3EAfgBmc3IAG3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQXNjcmDEeSeFkj7MAgACTAAEZXhwcnEAfgBGTAACcHRxAH4AIHhwc3EAfgBedAABbXNxAH4AD3QABUFycmF5c3EAfgAGc3EAfgAPdAAFQXJyYXlzcQB+AAZxAH4AmHEAfgAJeHEAfgAJeHEAfgBwcQB+AAdxAH4ACXhxAH4ACXhzcQB+AF50AANDb2xxAH4AB3NxAH4AXnQABXdpZHRocQB+AAl4cQB+AAl4c3EAfgBedAAMQ29tcG91bmRNYXRycQB+AAd0AAZtYXRyaXhzcQB+AEJzcQB+AA90AARNYXRyc3EAfgAGcQB+AJhxAH4ACXhzcQB+AFAAAHNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ARXNxAH4ABnNxAH4ABnNxAH4AXnQAAXZxAH4ACXhxAH4ACXhzcQB+AF50AANDb2xxAH4AB3EAfgAJeHEAfgAJeHNxAH4AXnQACERlbnNlVmVjcQB+AAd0AAZ2ZWN0b3JzcQB+AEJzcQB+AA90AANWZWNzcQB+AAZxAH4AmHEAfgAJeHEAfgAJeHNxAH4AZnNxAH4AZnNxAH4ARXNxAH4ABnNxAH4ABnNxAH4AXnQABm1hdHJpeHNxAH4AXnQABnZlY3RvcnEAfgAJeHNxAH4ABnNxAH4AXnQAC2RvdWJsZU51bWVyc3EAfgBedAAKcGx1c01vbm9pZHEAfgAJeHEAfgAJeHNxAH4AZnNxAH4ARXNxAH4ABnEAfgAHcQB+AAl4c3EAfgBedAACTEFxAH4AB3QAA212bXEAfgAHdAAFaXRlbXN0AANhcnJ0AAVkZG12bXEAfgAkcQB+AAdzcQB+AEJzcQB+AA90AAVBcnJheXNxAH4ABnEAfgCYcQB+AAl4cQB+AAl4dAACTEFzcQB+ABZxAH4AB3QAAkxBcQB+ACRxAH4AB3EAfgAJeHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0RGVmAb40kjQUsbICAAlaAAhiaXRtYXAkMEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+AAxMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAcQB+AAdxAH4AB3NxAH4ABnNxAH4AGQAAAHEAfgAHc3EAfgAGc3EAfgAcc3EAfgAGc3EAfgAfAAAAcQB+AAdxAH4AJHQABm1hdHJpeHNxAH4AD3QABE1hdHJzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhzcQB+AB8AAABxAH4AB3EAfgAkdAAGdmVjdG9yc3EAfgAPdAADVmVjc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4c3EAfgAcc3EAfgAGc3EAfgAfAAAAcQB+AAdxAH4AJHQAAW5zcQB+AA90AAVOdW1lcnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHNxAH4AHwAAAHEAfgAHcQB+ACR0AAFtc3EAfgAPdAAJTnVtTW9ub2lkc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4cQB+ACR0AANtdm1xAH4AJHNxAH4ABnNxAH4AdXEAfgAkc3EAfgAGdAAIQ2xhc3NUYWdxAH4ACXh0AAFUcQB+AAdxAH4ACXhzcQB+AEJzcQB+AA90AANWZWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4AJHB0AA9MaW5lYXJBbGdlYnJhT3BxAH4AJHEAfgAHcQB+AAl4cQB+ATFxAH4AJHNxAH4ABnNyACFzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0ltcG9ydFN0YXSsgKBKw8YbIgIAAUwABG5hbWVxAH4ABHhwdAAWc2NhbGEucmVmbGVjdC5DbGFzc1RhZ3NxAH4BY3QADnNjYWxhbi5Ib3RTcG90c3EAfgFjdAAQc2NhbGFuLktlcm5lbHMuX3EAfgAJeHEAfgAHdAAQTGluZWFyQWxnZWJyYU9wc3QAE3BhcmFkaXNlLmxpbmFsZ2VicmFzcQB+AEJzcgAic2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxmVHlwZURlZn73nU4qzrPlAgACTAAKY29tcG9uZW50c3EAfgABTAAEbmFtZXEAfgAEeHBzcQB+AAZzcQB+AA90AA1MaW5lYXJBbGdlYnJhcQB+AAdxAH4ACXh0AARzZWxmcQB+ACQ="
    }

    object HotSpotKernels {
      import java.io.File;
      import scalan.compilation.GraphVizConfig;
      lazy val ddmvmKernel = {
        val ctx = HotSpotManager.getScalanContextUni;
        val compilerOutput = ctx.buildExecutable(new File("./"), "ddmvm", ctx.ddmvmWrapper, GraphVizConfig.default)(ctx.defaultCompilerConfig);
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
      import scalan.compilation.lms.{CommunityLmsBackend, CoreBridge};
      import scalan.compilation.lms.scalac.CommunityLmsCompilerScala;
      import scalan.primitives.EffectfulCompiler;
      import paradise.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      lazy val scalanContext = new Scalan();
      def getScalanContext = scalanContext;
      class Scalan extends LinearAlgebraDslExp with CommunityLmsCompilerScala with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler {
        val lms = new CommunityLmsBackend()
      };
      import scalan.CommunityMethodMappingDSL;
      import scalan.compilation.lms.uni.LmsCompilerUni;
      lazy val scalanContextUni = new ScalanUni();
      def getScalanContextUni = scalanContextUni;
      class ScalanUni extends LinearAlgebraDslExp with LmsCompilerUni with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler with CommunityMethodMappingDSL {
        lazy val ddmvmWrapper = fun(((in: Rep[scala.Tuple2[Array[Array[Double]], Array[Double]]]) => {
          val m: Rep[Array[Array[Double]]] = in._1;
          val v: Rep[Array[Double]] = in._2;
          LA.ddmvm(m, v)
        }));
        val lms = new CommunityLmsBackend()
      }
    }
  }
}