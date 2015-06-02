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
        implicit def proxyLinearAlgebraExampleCompanion(p: Rep[LinearAlgebraExampleCompanion]): LinearAlgebraExampleCompanion = proxyOps[LinearAlgebraExampleCompanion](p);
        class MvMExampleElem(val iso: Iso[MvMExampleData, MvMExample]) extends LinearAlgebraExampleElem[MvMExample] with ConcreteElem[MvMExampleData, MvMExample] {
          override def convertLinearAlgebraExample(x: Rep[LinearAlgebraExample]) = MvMExample();
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = weakTypeTag[MvMExample]
        };
        type MvMExampleData = Unit;
        class MvMExampleIso extends Iso[MvMExampleData, MvMExample] {
          override def from(p: Rep[MvMExample]) = ();
          override def to(p: Rep[Unit]) = {
            val unit = p;
            MvMExample()
          };
          lazy val defaultRepTo = Default.defaultVal[Rep[MvMExample]](MvMExample());
          lazy val eTo = new MvMExampleElem(this)
        };
        abstract class MvMExampleCompanionAbs extends CompanionBase[MvMExampleCompanionAbs] with MvMExampleCompanion {
          override def toString = "MvMExample";
          def apply(p: Rep[MvMExampleData]): Rep[MvMExample] = isoMvMExample.to(p);
          def apply(): Rep[MvMExample] = mkMvMExample()
        };
        object MvMExampleMatcher {
          def unapply(p: Rep[LinearAlgebraExample]) = unmkMvMExample(p)
        };
        def MvMExample: Rep[MvMExampleCompanionAbs];
        implicit def proxyMvMExampleCompanion(p: Rep[MvMExampleCompanionAbs]): MvMExampleCompanionAbs = proxyOps[MvMExampleCompanionAbs](p);
        implicit case object MvMExampleCompanionElem extends CompanionElem[MvMExampleCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[MvMExampleCompanionAbs];
          protected def getDefaultRep = MvMExample
        };
        implicit def proxyMvMExample(p: Rep[MvMExample]): MvMExample = proxyOps[MvMExample](p);
        implicit class ExtendedMvMExample(p: Rep[MvMExample]) {
          def toData: Rep[MvMExampleData] = isoMvMExample.from(p)
        };
        implicit def isoMvMExample: Iso[MvMExampleData, MvMExample] = new MvMExampleIso();
        def mkMvMExample(): Rep[MvMExample];
        def unmkMvMExample(p: Rep[LinearAlgebraExample]): Option[Rep[Unit]]
      };
      trait LinearAlgebraExamplesSeq extends LinearAlgebraExamplesDsl with ScalanSeq { self: LinearAlgebraExamplesDslSeq =>
        lazy val LinearAlgebraExample: Rep[LinearAlgebraExampleCompanionAbs] = {
          final class $anon extends LinearAlgebraExampleCompanionAbs with UserTypeSeq[LinearAlgebraExampleCompanionAbs] {
            lazy val selfType = element[LinearAlgebraExampleCompanionAbs]
          };
          new $anon()
        };
        case class SeqMvMExample() extends MvMExample() with UserTypeSeq[MvMExample] {
          lazy val selfType = element[MvMExample]
        };
        lazy val MvMExample = {
          final class $anon extends MvMExampleCompanionAbs with UserTypeSeq[MvMExampleCompanionAbs] {
            lazy val selfType = element[MvMExampleCompanionAbs]
          };
          new $anon()
        };
        def mkMvMExample(): Rep[MvMExample] = new SeqMvMExample();
        def unmkMvMExample(p: Rep[LinearAlgebraExample]) = p match {
          case (p @ ((_): MvMExample @unchecked)) => Some(())
          case _ => None
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
        case class ExpMvMExample() extends MvMExample() with UserTypeDef[MvMExample] {
          lazy val selfType = element[MvMExample];
          override def mirror(t: Transformer) = ExpMvMExample()
        };
        lazy val MvMExample: Rep[MvMExampleCompanionAbs] = {
          final class $anon extends MvMExampleCompanionAbs with UserTypeDef[MvMExampleCompanionAbs] {
            lazy val selfType = element[MvMExampleCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object MvMExampleMethods {
          object mvm {
            def unapply(d: (Def[_$2] forSome { 
              type _$2
            })): Option[(scala.Tuple6[Rep[MvMExample], Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((matrix @ _), (vector @ _), (n @ _), (m @ _), (emT @ _), _*), _) if receiver.elem.isInstanceOf[MvMExampleElem].&&(method.getName.==("mvm")) => Some(scala.Tuple6(receiver, matrix, vector, n, m, emT)).asInstanceOf[Option[(scala.Tuple6[Rep[MvMExample], Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$3] forSome { 
              type _$3
            })): Option[(scala.Tuple6[Rep[MvMExample], Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object MvMExampleCompanionMethods;
        def mkMvMExample(): Rep[MvMExample] = new ExpMvMExample();
        def unmkMvMExample(p: Rep[LinearAlgebraExample]) = p.elem.asInstanceOf[(Elem[_$4] forSome { 
          type _$4
        })] match {
          case ((_): MvMExampleElem @unchecked) => Some(())
          case _ => None
        };
        object LinearAlgebraExampleMethods;
        object LinearAlgebraExampleCompanionMethods
      };
      trait LinearAlgebraExamples extends Base with LinearAlgebraDsl { self: LinearAlgebraExamplesDsl =>
        trait LinearAlgebraExample extends Reifiable[LinearAlgebraExample];
        abstract class MvMExample extends LinearAlgebraExample with Product with Serializable {
          implicit val doubleNumer: Numer[Double] = DoubleNumer();
          implicit val plusMonoid: NumMonoid[Double] = PlusMonoid[Double];
          def mvm[T](matrix: Rep[AbstractMatrix[T]], vector: Rep[AbstractVector[T]])(implicit n: Numer[T], m: NumMonoid[T], emT: Elem[T]): Rep[AbstractVector[T]] = DenseVector(matrix.rows.map(fun(((r: Rep[AbstractVector[T]]) => r.dot(vector)))));
          lazy val ddmvm0 = fun(((in: Rep[scala.Tuple2[Array[Array[Double]], Array[Double]]]) => {
            val m: Rep[Array[Array[Double]]] = in._1;
            val v: Rep[Array[Double]] = in._2;
            {
              val width = m(toRep(0)).length;
              val matrix: Rep[AbstractMatrix[Double]] = CompoundMatrix(Collection(array_map(m,fun(((r: Rep[Array[Double]]) => DenseVector(Collection(r)))))), width);
              val vector: Rep[AbstractVector[Double]] = DenseVector(Collection(v));
              mvm(matrix, vector).items.arr
            }
          }))
        };
        trait LinearAlgebraExampleCompanion;
        trait MvMExampleCompanion
      };
      trait LinearAlgebraExamplesDsl extends LinearAlgebraExamplesAbs with LinearAlgebraDsl { self: LinearAlgebraExamplesDsl =>
        
      };
      trait LinearAlgebraExamplesDslSeq extends LinearAlgebraExamplesSeq with LinearAlgebraDslSeq { self: LinearAlgebraExamplesDslSeq =>
        
      };
      trait LinearAlgebraExamplesDslExp extends LinearAlgebraExamplesExp with LinearAlgebraDslExp { self: LinearAlgebraExamplesDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0Q2FsbFDrGS0l7EVYAgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQADUxpbmVhckFsZ2VicmFzcQB+AAZzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHEAfgANeHEAfgALc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NEZWYH0H1o8ahwMwIACloACmlzQWJzdHJhY3RMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARhcmdzdAAiTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTQ2xhc3NBcmdzO0wABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+ABBMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAc3EAfgAGc3EAfgAIdAAUTGluZWFyQWxnZWJyYUV4YW1wbGVxAH4AC3NxAH4ACHQAB1Byb2R1Y3RxAH4AC3NxAH4ACHQADFNlcmlhbGl6YWJsZXEAfgALcQB+AA14cQB+AAtzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZ3Mn6+5kxTAAXAIAAUwABGFyZ3NxAH4AAXhwcQB+AAtzcQB+AAZzcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNWYWxEZWYrBjm6e91FDwIABVoACmlzSW1wbGljaXRaAAZpc0xhenlMAARleHBydAAdTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTRXhwcjtMAARuYW1lcQB+AARMAAN0cGVxAH4AA3hwAQBzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNBcHBseYRmKZ/Smvx/AgADTAAFYXJnc3NxAH4AAUwAA2Z1bnEAfgAdTAACdHNxAH4AAXhwc3EAfgAGcQB+AAtxAH4ADXhzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJZGVudCcSpfvwCddPAgABTAAEbmFtZXEAfgAEeHB0AAtEb3VibGVOdW1lcnEAfgALdAALZG91YmxlTnVtZXJzcgAKc2NhbGEuU29tZREi8mleoYt0AgABTAABeHQAEkxqYXZhL2xhbmcvT2JqZWN0O3hyAAxzY2FsYS5PcHRpb27+aTf92w5mdAIAAHhwc3EAfgAIdAAFTnVtZXJzcQB+AAZzcgAjc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVQcmltaXRpdmU1w7tV8L5OBQIAAkwAEmRlZmF1bHRWYWx1ZVN0cmluZ3EAfgAETAAEbmFtZXEAfgAEeHB0AAMwLjB0AAZEb3VibGVxAH4ADXhzcQB+ABwBAHNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1R5cGVBcHBseRrfr8315fpQAgACTAADZnVucQB+AB1MAAJ0c3EAfgABeHBzcQB+ACJ0AApQbHVzTW9ub2lkc3EAfgAGcQB+AC5xAH4ADXh0AApwbHVzTW9ub2lkc3EAfgAmc3EAfgAIdAAJTnVtTW9ub2lkc3EAfgAGcQB+AC5xAH4ADXhzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2REZWbdWXFxtzArDgIACloADGlzRWxlbU9yQ29udFoACmlzSW1wbGljaXRaAAppc092ZXJyaWRlTAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0cGVSZXNxAH4AA3hwAAAAcQB+AAtzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2RBcmdzKcInll51uosCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZwPc/8uTcnMDAgAHWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXQAIExzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RwZUV4cHI7eHAAAABxAH4AC3NyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHEAfgAodAAGbWF0cml4c3EAfgAIdAAOQWJzdHJhY3RNYXRyaXhzcQB+AAZzcQB+AAh0AAFUcQB+AAtxAH4ADXhzcQB+AEIAAABxAH4AC3EAfgBGdAAGdmVjdG9yc3EAfgAIdAAOQWJzdHJhY3RWZWN0b3JzcQB+AAZzcQB+AAh0AAFUcQB+AAtxAH4ADXhxAH4ADXhzcQB+AD9zcQB+AAZzcQB+AEIBAABxAH4AC3EAfgBGdAABbnNxAH4ACHQABU51bWVyc3EAfgAGc3EAfgAIdAABVHEAfgALcQB+AA14c3EAfgBCAQAAcQB+AAtxAH4ARnQAAW1zcQB+AAh0AAlOdW1Nb25vaWRzcQB+AAZzcQB+AAh0AAFUcQB+AAtxAH4ADXhxAH4ADXhxAH4ADXhzcQB+ACZzcQB+AB9zcQB+AAZzcQB+AAZzcQB+AB9zcQB+AAZzcQB+AAZzcgAbc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNGdW5jsEz4kBrFMIMCAAJMAAZwYXJhbXNxAH4AAUwAA3Jlc3EAfgAdeHBzcQB+AAZzcQB+ABwAAHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VtcHR5kWAV4UvlaL4CAAB4cHQAAXJzcQB+ACZzcQB+AAh0AA5BYnN0cmFjdFZlY3RvcnNxAH4ABnNxAH4ACHQAAVRxAH4AC3EAfgANeHEAfgANeHNxAH4AH3NxAH4ABnNxAH4ABnNxAH4AInQABnZlY3RvcnEAfgANeHEAfgANeHNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1NlbGVjdAvdaWVlQPmFAgACTAAEZXhwcnEAfgAdTAAFdG5hbWVxAH4ABHhwc3EAfgAidAABcnQAA2RvdHEAfgALcQB+AA14cQB+AA14c3EAfgB9c3EAfgB9c3EAfgAidAAGbWF0cml4dAAEcm93c3QAA21hcHEAfgALcQB+AA14cQB+AA14c3EAfgAidAALRGVuc2VWZWN0b3JxAH4AC3QAA212bXEAfgBGc3EAfgAGc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlQXJnklsmSHLxm4kCAARMAAVib3VuZHEAfgADTAAMY29udGV4dEJvdW5kcQB+AAFMAARuYW1lcQB+AARMAAd0cGFyYW1zcQB+AAF4cHEAfgBGc3EAfgAGdAAIQ2xhc3NUYWdxAH4ADXh0AAFUcQB+AAtxAH4ADXhzcQB+ACZzcQB+AAh0AA5BYnN0cmFjdFZlY3RvcnNxAH4ABnNxAH4ACHQAAVRxAH4AC3EAfgANeHNxAH4AHAABc3EAfgBrc3EAfgAGc3EAfgAcAABzcQB+AG90AAFtc3EAfgAmc3EAfgAIdAAFQXJyYXlzcQB+AAZzcQB+AAh0AAVBcnJheXNxAH4ABnEAfgAucQB+AA14cQB+AA14c3EAfgAcAABzcQB+AG90AAF2c3EAfgAmc3EAfgAIdAAFQXJyYXlzcQB+AAZxAH4ALnEAfgANeHEAfgANeHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0Jsb2NrVS3qZnEUNP0CAAJMAARpbml0cQB+AAFMAARsYXN0cQB+AB14cHNxAH4ABnNxAH4AHAAAc3EAfgB9c3EAfgAfc3EAfgAGc3EAfgAGc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ29uc3QaTRTRYktnigIAAUwAAWNxAH4AJ3hwc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAABxAH4ADXhxAH4ADXhzcQB+ACJ0AAFtcQB+AAt0AAZsZW5ndGh0AAV3aWR0aHEAfgBGc3EAfgAcAABzcQB+AB9zcQB+AAZzcQB+AAZzcQB+AB9zcQB+AAZzcQB+AAZzcQB+AB9zcQB+AAZzcQB+AAZzcQB+AGtzcQB+AAZzcQB+ABwAAHNxAH4Ab3QAAXJzcQB+ACZzcQB+AAh0AAVBcnJheXNxAH4ABnEAfgAucQB+AA14cQB+AA14c3EAfgAfc3EAfgAGc3EAfgAGc3EAfgAfc3EAfgAGc3EAfgAGc3EAfgAidAABcnEAfgANeHEAfgANeHNxAH4AInQACkNvbGxlY3Rpb25xAH4AC3EAfgANeHEAfgANeHNxAH4AInQAC0RlbnNlVmVjdG9ycQB+AAtxAH4ADXhxAH4ADXhzcQB+AH1zcQB+ACJ0AAFtcQB+AIdxAH4AC3EAfgANeHEAfgANeHNxAH4AInQACkNvbGxlY3Rpb25xAH4AC3NxAH4AInQABXdpZHRocQB+AA14cQB+AA14c3EAfgAidAAOQ29tcG91bmRNYXRyaXhxAH4AC3QABm1hdHJpeHNxAH4AJnNxAH4ACHQADkFic3RyYWN0TWF0cml4c3EAfgAGcQB+AC5xAH4ADXhzcQB+ABwAAHNxAH4AH3NxAH4ABnNxAH4ABnNxAH4AH3NxAH4ABnNxAH4ABnNxAH4AInQAAXZxAH4ADXhxAH4ADXhzcQB+ACJ0AApDb2xsZWN0aW9ucQB+AAtxAH4ADXhxAH4ADXhzcQB+ACJ0AAtEZW5zZVZlY3RvcnEAfgALdAAGdmVjdG9yc3EAfgAmc3EAfgAIdAAOQWJzdHJhY3RWZWN0b3JzcQB+AAZxAH4ALnEAfgANeHEAfgANeHNxAH4AfXNxAH4AfXNxAH4AH3NxAH4ABnNxAH4ABnNxAH4AInQABm1hdHJpeHNxAH4AInQABnZlY3RvcnEAfgANeHEAfgANeHNxAH4AInQAA212bXEAfgALdAAFaXRlbXN0AANhcnJ0AAZkZG12bTBxAH4ARnEAfgANeHEAfgBGc3EAfgAZcQB+AAt0AApNdk1FeGFtcGxlcQB+AEZxAH4AC3EAfgANeHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0RGVmAb40kjQUsbICAAlaAAhiaXRtYXAkMEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+ABBMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAcQB+AAtxAH4AC3EAfgALcQB+AEZwdAAUTGluZWFyQWxnZWJyYUV4YW1wbGVxAH4ARnEAfgALcQB+AA14cQB+AQ1xAH4ARnNxAH4ABnNyACFzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0ltcG9ydFN0YXSsgKBKw8YbIgIAAUwABG5hbWVxAH4ABHhwdAAVcGFyYWRpc2UubGluYWxnZWJyYS5fc3EAfgEQdAAWc2NhbGEucmVmbGVjdC5DbGFzc1RhZ3EAfgANeHEAfgALdAAVTGluZWFyQWxnZWJyYUV4YW1wbGVzdAAIcGFyYWRpc2VxAH4ARnEAfgBG"
    }
  }
}