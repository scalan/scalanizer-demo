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
      trait LinearAlgebraExamples extends Base with LinearAlgebraDsl { self: LinearAlgebraExamplesDsl =>
        trait LinearAlgebraExample extends Reifiable[LinearAlgebraExample] {
//          lazy val doubleNumer = DoubleNumer();
//          lazy val plusMonoid = PlusMonoid[Double];
          def ddmvm(m: Rep[Array[Array[Double]]], v: Rep[Array[Double]]) = {
            val width = m(toRep(0)).length;
            val matrix: Rep[AbstractMatrix[Double]] = ??? //CompoundMatrix(Collection(m.map(fun(((r: Rep[Array[Double]]) => DenseVector(Collection(r)))))), width);
            val vector: Rep[AbstractVector[Double]] = ??? //DenseVector(Collection(v));
            //matrix.*(vector).items.arr
            (vector).items.arr
          }
        };
        trait LinearAlgebraExampleCompanion
      };
      trait LinearAlgebraExamplesDsl extends LinearAlgebraExamplesAbs { self: LinearAlgebraExamplesDsl =>
        
      };
      trait LinearAlgebraExamplesDslSeq extends LinearAlgebraExamplesSeq with LinearAlgebraDslSeq { self: LinearAlgebraExamplesDslSeq =>
        
      };
      trait LinearAlgebraExamplesDslExp extends LinearAlgebraExamplesExp with LinearAlgebraDslExp { self: LinearAlgebraExamplesDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0Q2FsbFDrGS0l7EVYAgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQADUxpbmVhckFsZ2VicmFzcQB+AAZzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHEAfgANeHEAfgALcQB+AAtzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdERlZgG+NJI0FLGyAgAJWgAIYml0bWFwJDBMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3QAIkxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU0NsYXNzQXJncztMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAcQB+AAtxAH4AC3NxAH4ABnNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1ZhbERlZisGObp73UUPAgAFWgAKaXNJbXBsaWNpdFoABmlzTGF6eUwABGV4cHJ0AB1Mc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNFeHByO0wABG5hbWVxAH4ABEwAA3RwZXEAfgADeHAAAXNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0FwcGx5hGYpn9Ka/H8CAANMAAVhcmdzc3EAfgABTAADZnVucQB+ABRMAAJ0c3EAfgABeHBzcQB+AAZxAH4AC3EAfgANeHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0lkZW50JxKl+/AJ108CAAFMAARuYW1lcQB+AAR4cHQAC0RvdWJsZU51bWVycQB+AAt0AAtkb3VibGVOdW1lcnNyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHIADHNjYWxhLk9wdGlvbv5pN/3bDmZ0AgAAeHBzcQB+ABMAAXNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1R5cGVBcHBseRrfr8315fpQAgACTAADZnVucQB+ABRMAAJ0c3EAfgABeHBzcQB+ABl0AApQbHVzTW9ub2lkc3EAfgAGc3IAI3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlUHJpbWl0aXZlNcO7VfC+TgUCAAJMABJkZWZhdWx0VmFsdWVTdHJpbmdxAH4ABEwABG5hbWVxAH4ABHhwdAADMC4wdAAGRG91YmxlcQB+AA14dAAKcGx1c01vbm9pZHEAfgAfc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kRGVm3VlxcbcwKw4CAApaAAxpc0VsZW1PckNvbnRaAAppc0ltcGxpY2l0WgAKaXNPdmVycmlkZUwAC2Fubm90YXRpb25zcQB+AAFMAAthcmdTZWN0aW9uc3EAfgABTAAEYm9keXEAfgADTAAEbmFtZXEAfgAETAAKb3ZlcmxvYWRJZHEAfgADTAAHdHBlQXJnc3EAfgABTAAGdHBlUmVzcQB+AAN4cAAAAHEAfgALc3EAfgAGc3IAIXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kQXJncynCJ5ZedbqLAgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2RBcmcD3P/Lk3JzAwIAB1oAB2ltcEZsYWdaAAxpc0VsZW1PckNvbnRaAAhvdmVyRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGV0ACBMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcGVFeHByO3hwAAAAcQB+AAtxAH4AH3QAAW1zcQB+AAh0AAVBcnJheXNxAH4ABnNxAH4ACHQABUFycmF5c3EAfgAGcQB+ACdxAH4ADXhxAH4ADXhzcQB+ADEAAABxAH4AC3EAfgAfdAABdnNxAH4ACHQABUFycmF5c3EAfgAGcQB+ACdxAH4ADXhxAH4ADXhxAH4ADXhzcgAKc2NhbGEuU29tZREi8mleoYt0AgABTAABeHQAEkxqYXZhL2xhbmcvT2JqZWN0O3hxAH4AHnNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0Jsb2NrVS3qZnEUNP0CAAJMAARpbml0cQB+AAFMAARsYXN0cQB+ABR4cHNxAH4ABnNxAH4AEwAAc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTU2VsZWN0C91pZWVA+YUCAAJMAARleHBycQB+ABRMAAV0bmFtZXEAfgAEeHBzcQB+ABZzcQB+AAZzcQB+AAZzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDb25zdBpNFNFiS2eKAgABTAABY3EAfgBBeHBzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAHEAfgANeHEAfgANeHNxAH4AGXQAAW1xAH4AC3QABmxlbmd0aHQABXdpZHRocQB+AB9zcQB+ABMAAHNxAH4AFnNxAH4ABnNxAH4ABnNxAH4AFnNxAH4ABnNxAH4ABnNxAH4AFnNxAH4ABnNxAH4ABnNyABtzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0Z1bmOwTPiQGsUwgwIAAkwABnBhcmFtc3EAfgABTAADcmVzcQB+ABR4cHNxAH4ABnNxAH4AEwAAc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTRW1wdHmRYBXhS+VovgIAAHhwdAABcnNxAH4AQHNxAH4ACHQABUFycmF5c3EAfgAGcQB+ACdxAH4ADXhxAH4ADXhzcQB+ABZzcQB+AAZzcQB+AAZzcQB+ABZzcQB+AAZzcQB+AAZzcQB+ABl0AAFycQB+AA14cQB+AA14c3EAfgAZdAAKQ29sbGVjdGlvbnEAfgALcQB+AA14cQB+AA14c3EAfgAZdAALRGVuc2VWZWN0b3JxAH4AC3EAfgANeHEAfgANeHNxAH4AR3NxAH4AGXQAAW10AANtYXBxAH4AC3EAfgANeHEAfgANeHNxAH4AGXQACkNvbGxlY3Rpb25xAH4AC3NxAH4AGXQABXdpZHRocQB+AA14cQB+AA14c3EAfgAZdAAOQ29tcG91bmRNYXRyaXhxAH4AC3QABm1hdHJpeHNxAH4AQHNxAH4ACHQADkFic3RyYWN0TWF0cml4c3EAfgAGcQB+ACdxAH4ADXhzcQB+ABMAAHNxAH4AFnNxAH4ABnNxAH4ABnNxAH4AFnNxAH4ABnNxAH4ABnNxAH4AGXQAAXZxAH4ADXhxAH4ADXhzcQB+ABl0AApDb2xsZWN0aW9ucQB+AAtxAH4ADXhxAH4ADXhzcQB+ABl0AAtEZW5zZVZlY3RvcnEAfgALdAAGdmVjdG9yc3EAfgBAc3EAfgAIdAAOQWJzdHJhY3RWZWN0b3JzcQB+AAZxAH4AJ3EAfgANeHEAfgANeHNxAH4AR3NxAH4AR3NxAH4AFnNxAH4ABnNxAH4ABnNxAH4AGXQABnZlY3RvcnEAfgANeHEAfgANeHNxAH4AR3NxAH4AGXQABm1hdHJpeHQABiR0aW1lc3EAfgALdAAFaXRlbXN0AANhcnJ0AAVkZG12bXEAfgAfcQB+AAtxAH4AH3EAfgANeHEAfgAfcHQAFExpbmVhckFsZ2VicmFFeGFtcGxlcQB+AB9xAH4AC3EAfgANeHEAfgARcQB+AB9zcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJbXBvcnRTdGF0rICgSsPGGyICAAFMAARuYW1lcQB+AAR4cHQAFXBhcmFkaXNlLmxpbmFsZ2VicmEuX3EAfgANeHEAfgALdAAVTGluZWFyQWxnZWJyYUV4YW1wbGVzdAAIcGFyYWRpc2VxAH4AH3EAfgAf"
    }
  }
}