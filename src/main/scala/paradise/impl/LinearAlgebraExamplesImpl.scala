package paradise {
  object StagedEvaluation {
    import scalan._;
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
    trait LinearAlgebraExamples extends Base { self: LinearAlgebraExamplesDsl =>
      trait LinearAlgebraExample extends Reifiable[LinearAlgebraExample] {
        def ddmvm(m: Array[Array[Double]], v: Array[Double]) = {
          val width = m(toRep(0)).length;
          val matrix: Matrix[Double] = CompoundMatrix(Collection(m.map(fun(((r: Array[Double]) => DenseVector(Collection(r)))))), width);
          val vector: Vector[Double] = DenseVector(Collection(v));
          matrix.*(vector).items.arr
        }
      };
      trait LinearAlgebraExampleCompanion
    };
    trait LinearAlgebraExamplesDsl extends LinearAlgebraExamplesAbs;
    trait LinearAlgebraExamplesDslSeq extends LinearAlgebraExamplesSeq;
    trait LinearAlgebraExamplesDslExp extends LinearAlgebraExamplesExp;
    val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZrSr+/co886dAgALTAAEYm9keXQAIUxzY2FsYS9jb2xsZWN0aW9uL2ltbXV0YWJsZS9MaXN0O0wAEGNvbmNyZXRlU0NsYXNzZXNxAH4AAUwACGVudGl0aWVzcQB+AAFMAAllbnRpdHlPcHN0ACFMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcmFpdERlZjtMABBlbnRpdHlSZXBTeW5vbnltdAAOTHNjYWxhL09wdGlvbjtMAAdpbXBvcnRzcQB+AAFMAAdtZXRob2RzcQB+AAFMAARuYW1ldAASTGphdmEvbGFuZy9TdHJpbmc7TAALcGFja2FnZU5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAApzZXFEc2xJbXBscQB+AAN4cHNyADJzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0JFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABAwAAeHBzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHEAfgAHc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHJhaXREZWYBvjSSNBSxsgIACVoACGJpdG1hcCQwTAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgAHcQB+AAdzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2REZWbdWXFxtzArDgIACloADGlzRWxlbU9yQ29udFoACmlzSW1wbGljaXRaAAppc092ZXJyaWRlTAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0cGVSZXNxAH4AA3hwAAAAcQB+AAdzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2RBcmdzKcInll51uosCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZwPc/8uTcnMDAgAHWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXQAIExzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RwZUV4cHI7eHAAAABxAH4AB3NyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHIADHNjYWxhLk9wdGlvbv5pN/3bDmZ0AgAAeHB0AAFtc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHJhaXRDYWxsUOsZLSXsRVgCAAJMAARuYW1lcQB+AARMAAl0cGVTRXhwcnNxAH4AAXhwdAAFQXJyYXlzcQB+AAZzcQB+ABx0AAVBcnJheXNxAH4ABnNyACNzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZVByaW1pdGl2ZTXDu1Xwvk4FAgACTAASZGVmYXVsdFZhbHVlU3RyaW5ncQB+AARMAARuYW1lcQB+AAR4cHQAAzAuMHQABkRvdWJsZXEAfgAJeHEAfgAJeHNxAH4AFQAAAHEAfgAHcQB+ABp0AAF2c3EAfgAcdAAFQXJyYXlzcQB+AAZxAH4AJHEAfgAJeHEAfgAJeHEAfgAJeHNyAApzY2FsYS5Tb21lESLyaV6hi3QCAAFMAAF4dAASTGphdmEvbGFuZy9PYmplY3Q7eHEAfgAZc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQmxvY2tVLepmcRQ0/QIAAkwABGluaXRxAH4AAUwABGxhc3R0AB1Mc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNFeHByO3hwc3EAfgAGc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVmFsRGVmKwY5unvdRQ8CAAVaAAppc0ltcGxpY2l0WgAGaXNMYXp5TAAEZXhwcnEAfgAwTAAEbmFtZXEAfgAETAADdHBlcQB+AAN4cAAAc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTU2VsZWN0C91pZWVA+YUCAAJMAARleHBycQB+ADBMAAV0bmFtZXEAfgAEeHBzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNBcHBseYRmKZ/Smvx/AgADTAAFYXJnc3NxAH4AAUwAA2Z1bnEAfgAwTAACdHNxAH4AAXhwc3EAfgAGc3EAfgAGc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ29uc3QaTRTRYktnigIAAUwAAWNxAH4ALXhwc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAABxAH4ACXhxAH4ACXhzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJZGVudCcSpfvwCddPAgABTAAEbmFtZXEAfgAEeHB0AAFtcQB+AAd0AAZsZW5ndGh0AAV3aWR0aHEAfgAac3EAfgAzAABzcQB+ADdzcQB+AAZzcQB+AAZzcQB+ADdzcQB+AAZzcQB+AAZzcQB+ADdzcQB+AAZzcQB+AAZzcgAbc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNGdW5jsEz4kBrFMIMCAAJMAAZwYXJhbXNxAH4AAUwAA3Jlc3EAfgAweHBzcQB+AAZzcQB+ADMAAHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VtcHR5kWAV4UvlaL4CAAB4cHQAAXJzcQB+ACxzcQB+ABx0AAVBcnJheXNxAH4ABnEAfgAkcQB+AAl4cQB+AAl4c3EAfgA3c3EAfgAGc3EAfgAGc3EAfgA3c3EAfgAGc3EAfgAGc3EAfgBAdAABcnEAfgAJeHEAfgAJeHNxAH4AQHQACkNvbGxlY3Rpb25xAH4AB3EAfgAJeHEAfgAJeHNxAH4AQHQAC0RlbnNlVmVjdG9ycQB+AAdxAH4ACXhxAH4ACXhzcQB+ADVzcQB+AEB0AAFtdAADbWFwcQB+AAdxAH4ACXhxAH4ACXhzcQB+AEB0AApDb2xsZWN0aW9ucQB+AAdzcQB+AEB0AAV3aWR0aHEAfgAJeHEAfgAJeHNxAH4AQHQADkNvbXBvdW5kTWF0cml4cQB+AAd0AAZtYXRyaXhzcQB+ACxzcQB+ABx0AAZNYXRyaXhzcQB+AAZxAH4AJHEAfgAJeHNxAH4AMwAAc3EAfgA3c3EAfgAGc3EAfgAGc3EAfgA3c3EAfgAGc3EAfgAGc3EAfgBAdAABdnEAfgAJeHEAfgAJeHNxAH4AQHQACkNvbGxlY3Rpb25xAH4AB3EAfgAJeHEAfgAJeHNxAH4AQHQAC0RlbnNlVmVjdG9ycQB+AAd0AAZ2ZWN0b3JzcQB+ACxzcQB+ABx0AAZWZWN0b3JzcQB+AAZxAH4AJHEAfgAJeHEAfgAJeHNxAH4ANXNxAH4ANXNxAH4AN3NxAH4ABnNxAH4ABnNxAH4AQHQABnZlY3RvcnEAfgAJeHEAfgAJeHNxAH4ANXNxAH4AQHQABm1hdHJpeHQABiR0aW1lc3EAfgAHdAAFaXRlbXN0AANhcnJ0AAVkZG12bXEAfgAacQB+AAdxAH4AGnEAfgAJeHEAfgAacHQAFExpbmVhckFsZ2VicmFFeGFtcGxlcQB+ABpxAH4AB3EAfgAJeHEAfgANcQB+ABpzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJbXBvcnRTdGF0rICgSsPGGyICAAFMAARuYW1lcQB+AAR4cHQADGxpbmFsZ2VicmEuX3NxAH4Al3QAIHBhcmFkaXNlLmNvbGxlY3Rpb25zLkNvbGxlY3Rpb25zcQB+AAl4cQB+AAd0ABVMaW5lYXJBbGdlYnJhRXhhbXBsZXN0AAhwYXJhZGlzZXEAfgAacQB+ABo="
  }
}