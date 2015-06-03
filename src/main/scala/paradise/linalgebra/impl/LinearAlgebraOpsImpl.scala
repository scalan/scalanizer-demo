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
        class BaseOpElem(val iso: Iso[BaseOpData, BaseOp]) extends LinearAlgebraOpElem[BaseOp] with ConcreteElem[BaseOpData, BaseOp] {
          override def convertLinearAlgebraOp(x: Rep[LinearAlgebraOp]) = BaseOp();
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = weakTypeTag[BaseOp]
        };
        type BaseOpData = Unit;
        class BaseOpIso extends Iso[BaseOpData, BaseOp] {
          override def from(p: Rep[BaseOp]) = ();
          override def to(p: Rep[Unit]) = {
            val unit = p;
            BaseOp()
          };
          lazy val defaultRepTo = Default.defaultVal[Rep[BaseOp]](BaseOp());
          lazy val eTo = new BaseOpElem(this)
        };
        abstract class BaseOpCompanionAbs extends CompanionBase[BaseOpCompanionAbs] with BaseOpCompanion {
          override def toString = "BaseOp";
          def apply(p: Rep[BaseOpData]): Rep[BaseOp] = isoBaseOp.to(p);
          def apply(): Rep[BaseOp] = mkBaseOp()
        };
        object BaseOpMatcher {
          def unapply(p: Rep[LinearAlgebraOp]) = unmkBaseOp(p)
        };
        def BaseOp: Rep[BaseOpCompanionAbs];
        implicit def proxyBaseOpCompanion(p: Rep[BaseOpCompanionAbs]): BaseOpCompanionAbs = proxyOps[BaseOpCompanionAbs](p);
        implicit case object BaseOpCompanionElem extends CompanionElem[BaseOpCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[BaseOpCompanionAbs];
          protected def getDefaultRep = BaseOp
        };
        implicit def proxyBaseOp(p: Rep[BaseOp]): BaseOp = proxyOps[BaseOp](p);
        implicit class ExtendedBaseOp(p: Rep[BaseOp]) {
          def toData: Rep[BaseOpData] = isoBaseOp.from(p)
        };
        implicit def isoBaseOp: Iso[BaseOpData, BaseOp] = new BaseOpIso();
        def mkBaseOp(): Rep[BaseOp];
        def unmkBaseOp(p: Rep[LinearAlgebraOp]): Option[Rep[Unit]]
      };
      trait LinearAlgebraOpsSeq extends LinearAlgebraOpsDsl with ScalanSeq { self: LinearAlgebraDslSeq =>
        lazy val LinearAlgebraOp: Rep[LinearAlgebraOpCompanionAbs] = {
          final class $anon extends LinearAlgebraOpCompanionAbs with UserTypeSeq[LinearAlgebraOpCompanionAbs] {
            lazy val selfType = element[LinearAlgebraOpCompanionAbs]
          };
          new $anon()
        };
        case class SeqBaseOp() extends BaseOp() with UserTypeSeq[BaseOp] {
          lazy val selfType = element[BaseOp]
        };
        lazy val BaseOp = {
          final class $anon extends BaseOpCompanionAbs with UserTypeSeq[BaseOpCompanionAbs] {
            lazy val selfType = element[BaseOpCompanionAbs]
          };
          new $anon()
        };
        def mkBaseOp(): Rep[BaseOp] = new SeqBaseOp();
        def unmkBaseOp(p: Rep[LinearAlgebraOp]) = p match {
          case (p @ ((_): BaseOp @unchecked)) => Some(())
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
        case class ExpBaseOp() extends BaseOp() with UserTypeDef[BaseOp] {
          lazy val selfType = element[BaseOp];
          override def mirror(t: Transformer) = ExpBaseOp()
        };
        lazy val BaseOp: Rep[BaseOpCompanionAbs] = {
          final class $anon extends BaseOpCompanionAbs with UserTypeDef[BaseOpCompanionAbs] {
            lazy val selfType = element[BaseOpCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object BaseOpMethods {
          object mvm {
            def unapply(d: (Def[_$2] forSome { 
              type _$2
            })): Option[(scala.Tuple6[Rep[BaseOp], Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((matrix @ _), (vector @ _), (n @ _), (m @ _), (emT @ _), _*), _) if receiver.elem.isInstanceOf[BaseOpElem].&&(method.getName.==("mvm")) => Some(scala.Tuple6(receiver, matrix, vector, n, m, emT)).asInstanceOf[Option[(scala.Tuple6[Rep[BaseOp], Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$3] forSome { 
              type _$3
            })): Option[(scala.Tuple6[Rep[BaseOp], Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object BaseOpCompanionMethods;
        def mkBaseOp(): Rep[BaseOp] = new ExpBaseOp();
        def unmkBaseOp(p: Rep[LinearAlgebraOp]) = p.elem.asInstanceOf[(Elem[_$4] forSome { 
          type _$4
        })] match {
          case ((_): BaseOpElem @unchecked) => Some(())
          case _ => None
        };
        object LinearAlgebraOpMethods {
          object mvm {
            def unapply(d: (Def[_$5] forSome { 
              type _$5
            })): Option[(scala.Tuple6[Rep[LinearAlgebraOp], Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((matrix @ _), (vector @ _), (n @ _), (m @ _), (emT @ _), _*), _) if receiver.elem.isInstanceOf[(LinearAlgebraOpElem[_$6] forSome { 
  type _$6
})].&&(method.getName.==("mvm")) => Some(scala.Tuple6(receiver, matrix, vector, n, m, emT)).asInstanceOf[Option[(scala.Tuple6[Rep[LinearAlgebraOp], Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]], Rep[Elem[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$7] forSome { 
              type _$7
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
        abstract class BaseOp extends LinearAlgebraOp with Product with Serializable {
          def mvm[T](matrix: Rep[AbstractMatrix[T]], vector: Rep[AbstractVector[T]])(implicit n: Numer[T], m: NumMonoid[T], emT: Elem[T]): Rep[AbstractVector[T]] = DenseVector(matrix.rows.map(fun(((r: Rep[AbstractVector[T]]) => r.dot(vector)))))
        };
        trait LinearAlgebraOpCompanion;
        trait BaseOpCompanion
      };
      trait LinearAlgebraOpsDsl extends LinearAlgebraOpsAbs { self: LinearAlgebraDsl =>
        
      };
      trait LinearAlgebraOpsDslSeq extends LinearAlgebraOpsSeq { self: LinearAlgebraDslSeq =>
        
      };
      trait LinearAlgebraOpsDslExp extends LinearAlgebraOpsExp { self: LinearAlgebraDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AA9MaW5lYXJBbGdlYnJhT3BxAH4AB3NxAH4AD3QAB1Byb2R1Y3RxAH4AB3NxAH4AD3QADFNlcmlhbGl6YWJsZXEAfgAHcQB+AAl4cQB+AAdzcgAgc2Nh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cQB+AAdxAH4ACXhzcQB+AEJzcQB+AA90AA5BYnN0cmFjdFZlY3RvcnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgAkc3EAfgAWcQB+AAd0AAZCYXNlT3BxAH4AJHEAfgAHcQB+AAl4c3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHJhaXREZWYBvjSSNBSxsgIACVoACGJpdG1hcCQwTAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABxAH4AB3EAfgAHc3EAfgAGc3EAfgAZAAAAcQB+AAdzcQB+AAZzcQB+ABxzcQB+AAZzcQB+AB8AAABxAH4AB3EAfgAkdAAGbWF0cml4c3EAfgAPdAAOQWJzdHJhY3RNYXRyaXhzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhzcQB+AB8AAABxAH4AB3EAfgAkdAAGdmVjdG9yc3EAfgAPdAAOQWJzdHJhY3RWZWN0b3JzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+ABxzcQB+AAZzcQB+AB8BAABxAH4AB3EAfgAkdAABbnNxAH4AD3QABU51bWVyc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4c3EAfgAfAQAAcQB+AAdxAH4AJHQAAW1zcQB+AA90AAlOdW1Nb25vaWRzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4AJHQAA212bXEAfgAkc3EAfgAGc3EAfgBwcQB+ACRzcQB+AAZ0AAhDbGFzc1RhZ3EAfgAJeHQAAVRxAH4AB3EAfgAJeHNxAH4AQnNxAH4AD3QADkFic3RyYWN0VmVjdG9yc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+ACRwdAAPTGluZWFyQWxnZWJyYU9wcQB+ACRxAH4AB3EAfgAJeHEAfgB/cQB+ACRzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJbXBvcnRTdGF0rICgSsPGGyICAAFMAARuYW1lcQB+AAR4cHQAFnNjYWxhLnJlZmxlY3QuQ2xhc3NUYWdxAH4ACXhxAH4AB3QAEExpbmVhckFsZ2VicmFPcHN0ABNwYXJhZGlzZS5saW5hbGdlYnJhc3EAfgBCc3IAInNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTU2VsZlR5cGVEZWZ+951OKs6z5QIAAkwACmNvbXBvbmVudHNxAH4AAUwABG5hbWVxAH4ABHhwc3EAfgAGc3EAfgAPdAANTGluZWFyQWxnZWJyYXEAfgAHcQB+AAl4dAAEc2VsZnEAfgAk"
    }
  }
}