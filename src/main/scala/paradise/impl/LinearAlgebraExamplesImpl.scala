package paradise {
  package implOfLinearAlgebraExamples {
    object StagedEvaluation {
      import scalan._;
      import paradise.implOfNumMonoids.StagedEvaluation._;
      import paradise.collections.implOfCollections.StagedEvaluation._;
      import paradise.linalgebra.implOfVectors.StagedEvaluation._;
      import paradise.linalgebra.implOfMatrices.StagedEvaluation._;
      import paradise.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait LinearAlgebraExamplesAbs extends LinearAlgebraExamples with ScalanDsl { self: LinearAlgebraExamplesDsl =>
        implicit def proxyLinearAlgebraExample(p: Rep[LinearAlgebraExample]): LinearAlgebraExample = proxyOps[LinearAlgebraExample](p)(classTag[LinearAlgebraExample]);
        class LinearAlgebraExampleElem[To <: LinearAlgebraExample] extends EntityElem[To] {
          override def isEntityType = true;
          override lazy val tag = weakTypeTag[LinearAlgebraExample].asInstanceOf[WeakTypeTag[To]];
          override def convert(x: Rep[(Reifiable[_$1] forSome { 
            type _$1
          })]) = {
            val conv = fun(((x: Rep[LinearAlgebraExample]) => convertLinearAlgebraExample(x)));
            tryConvert(element[LinearAlgebraExample], this, x, conv)
          };
          def convertLinearAlgebraExample(x: Rep[LinearAlgebraExample]): Rep[To] = {
            assert(x.selfType1 match {
              case ((_): LinearAlgebraExampleElem[(_)]) => true
              case _ => false
            });
            x.asRep[To]
          };
          override def getDefaultRep: Rep[To] = ???
        };
        implicit def linearAlgebraExampleElement: Elem[LinearAlgebraExample] = new LinearAlgebraExampleElem[LinearAlgebraExample]();
        implicit case object LinearAlgebraExampleCompanionElem extends CompanionElem[LinearAlgebraExampleCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[LinearAlgebraExampleCompanionAbs];
          protected def getDefaultRep = LinearAlgebraExample
        };
        abstract class LinearAlgebraExampleCompanionAbs extends CompanionBase[LinearAlgebraExampleCompanionAbs] with LinearAlgebraExampleCompanion {
          override def toString = "LinearAlgebraExample"
        };
        def LinearAlgebraExample: Rep[LinearAlgebraExampleCompanionAbs];
        implicit def proxyLinearAlgebraExampleCompanion(p: Rep[LinearAlgebraExampleCompanion]): LinearAlgebraExampleCompanion = proxyOps[LinearAlgebraExampleCompanion](p)
      };
      trait LinearAlgebraExamplesSeq extends LinearAlgebraExamplesDsl with ScalanSeq { self: LinearAlgebraExamplesDslSeq =>
        lazy val LinearAlgebraExample: Rep[LinearAlgebraExampleCompanionAbs] = {
          final class $anon extends LinearAlgebraExampleCompanionAbs with UserTypeSeq[LinearAlgebraExampleCompanionAbs] {
            lazy val selfType = element[LinearAlgebraExampleCompanionAbs]
          };
          new $anon()
        }
      };
      trait LinearAlgebraExamplesExp extends LinearAlgebraExamplesDsl with ScalanExp { self: LinearAlgebraExamplesDslExp =>
        lazy val LinearAlgebraExample: Rep[LinearAlgebraExampleCompanionAbs] = {
          final class $anon extends LinearAlgebraExampleCompanionAbs with UserTypeDef[LinearAlgebraExampleCompanionAbs] {
            lazy val selfType = element[LinearAlgebraExampleCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object LinearAlgebraExampleMethods {
          object ddmvm {
            def unapply(d: (Def[_$2] forSome { 
              type _$2
            })): Option[scala.Tuple3[Rep[LinearAlgebraExample], Rep[Array[Array[Double]]], Rep[Array[Double]]]] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((m @ _), (v @ _), _*), _) if receiver.elem.isInstanceOf[(LinearAlgebraExampleElem[_$3] forSome { 
  type _$3
})].&&(method.getName.==("ddmvm")) => Some(scala.Tuple3(receiver, m, v)).asInstanceOf[Option[scala.Tuple3[Rep[LinearAlgebraExample], Rep[Array[Array[Double]]], Rep[Array[Double]]]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$4] forSome { 
              type _$4
            })): Option[scala.Tuple3[Rep[LinearAlgebraExample], Rep[Array[Array[Double]]], Rep[Array[Double]]]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object LinearAlgebraExampleCompanionMethods
      };
      trait LinearAlgebraExamples extends Base with LinearAlgebra { self: LinearAlgebraExamplesDsl =>
        trait LinearAlgebraExample extends Reifiable[LinearAlgebraExample] {
          lazy val plusMonoid = PlusMonoid[Double];
          def ddmvm(m: Rep[Array[Array[Double]]], v: Rep[Array[Double]]) = {
            val width = m(toRep(0)).length;
            val matrix: Rep[AbstractMatrix[Double]] = CompoundMatrix(Collection(m.map(fun(((r: Rep[Array[Double]]) => DenseVector(Collection(r)))))), width);
            val vector: Rep[AbstractVector[Double]] = DenseVector(Collection(v));
            matrix.*(vector).items.arr
          }
        };
        trait LinearAlgebraExampleCompanion
      };
      trait LinearAlgebraExamplesDsl extends LinearAlgebraExamplesAbs { self: LinearAlgebraExamplesDsl =>
        
      };
      trait LinearAlgebraExamplesDslSeq extends LinearAlgebraExamplesSeq { self: LinearAlgebraExamplesDslSeq =>
        
      };
      trait LinearAlgebraExamplesDslExp extends LinearAlgebraExamplesExp { self: LinearAlgebraExamplesDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0Q2FsbFDrGS0l7EVYAgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQADUxpbmVhckFsZ2VicmFzcQB+AAZzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHEAfgANeHEAfgALcQB+AAtzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdERlZgG+NJI0FLGyAgAJWgAIYml0bWFwJDBMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3QAIkxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU0NsYXNzQXJncztMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAcQB+AAtxAH4AC3NxAH4ABnNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1ZhbERlZisGObp73UUPAgAFWgAKaXNJbXBsaWNpdFoABmlzTGF6eUwABGV4cHJ0AB1Mc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNFeHByO0wABG5hbWVxAH4ABEwAA3RwZXEAfgADeHAAAXNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1R5cGVBcHBseRrfr8315fpQAgACTAADZnVucQB+ABRMAAJ0c3EAfgABeHBzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJZGVudCcSpfvwCddPAgABTAAEbmFtZXEAfgAEeHB0AApQbHVzTW9ub2lkc3EAfgAGc3IAI3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlUHJpbWl0aXZlNcO7VfC+TgUCAAJMABJkZWZhdWx0VmFsdWVTdHJpbmdxAH4ABEwABG5hbWVxAH4ABHhwdAADMC4wdAAGRG91YmxlcQB+AA14dAAKcGx1c01vbm9pZHNyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHIADHNjYWxhLk9wdGlvbv5pN/3bDmZ0AgAAeHBzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2REZWbdWXFxtzArDgIACloADGlzRWxlbU9yQ29udFoACmlzSW1wbGljaXRaAAppc092ZXJyaWRlTAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0cGVSZXNxAH4AA3hwAAAAcQB+AAtzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2RBcmdzKcInll51uosCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZwPc/8uTcnMDAgAHWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXQAIExzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RwZUV4cHI7eHAAAABxAH4AC3EAfgAjdAABbXNxAH4ACHQABUFycmF5c3EAfgAGc3EAfgAIdAAFQXJy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"
    }
  }
}