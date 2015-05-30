package paradise.linalgebra {
  package implOfMatrices {
    object StagedEvaluation {
      import scalan._;
      import paradise.implOfNumMonoids.StagedEvaluation._;
      import paradise.collections.implOfCollections.StagedEvaluation._;
      import paradise.linalgebra.implOfVectors.StagedEvaluation._;
      import paradise.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait MatricesAbs extends Matrices with ScalanDsl { self: LinearAlgebraDsl =>
        implicit def proxyAbstractMatrix[T](p: Rep[AbstractMatrix[T]]): AbstractMatrix[T] = proxyOps[AbstractMatrix[T]](p)(classTag[AbstractMatrix[T]]);
        class AbstractMatrixElem[T, To <: AbstractMatrix[T]](implicit val eT: Elem[T]) extends EntityElem[To] {
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagT = eT.tag;
            weakTypeTag[AbstractMatrix[T]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$1] forSome { 
            type _$1
          })]) = {
            val conv = fun(((x: Rep[AbstractMatrix[T]]) => convertAbstractMatrix(x)));
            tryConvert(element[AbstractMatrix[T]], this, x, conv)
          };
          def convertAbstractMatrix(x: Rep[AbstractMatrix[T]]): Rep[To] = {
            assert(x.selfType1 match {
              case ((_): AbstractMatrixElem[(_), (_)]) => true
              case _ => false
            });
            x.asRep[To]
          };
          override def getDefaultRep: Rep[To] = ???
        };
        implicit def abstractMatrixElement[T](implicit eT: Elem[T]): Elem[AbstractMatrix[T]] = new AbstractMatrixElem[T, AbstractMatrix[T]]();
        implicit case object AbstractMatrixCompanionElem extends CompanionElem[AbstractMatrixCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[AbstractMatrixCompanionAbs];
          protected def getDefaultRep = AbstractMatrix
        };
        abstract class AbstractMatrixCompanionAbs extends CompanionBase[AbstractMatrixCompanionAbs] with AbstractMatrixCompanion {
          override def toString = "AbstractMatrix"
        };
        def AbstractMatrix: Rep[AbstractMatrixCompanionAbs];
        implicit def proxyAbstractMatrixCompanion(p: Rep[AbstractMatrixCompanion]): AbstractMatrixCompanion = proxyOps[AbstractMatrixCompanion](p);
        class CompoundMatrixElem[T](val iso: Iso[CompoundMatrixData[T], CompoundMatrix[T]])(implicit ctT: ClassTag[T], eT: Elem[T]) extends AbstractMatrixElem[T, CompoundMatrix[T]] with ConcreteElem[CompoundMatrixData[T], CompoundMatrix[T]] {
          override def convertAbstractMatrix(x: Rep[AbstractMatrix[T]]) = CompoundMatrix(x.rows, x.numColumns);
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = {
            implicit val tagT = eT.tag;
            weakTypeTag[CompoundMatrix[T]]
          }
        };
        type CompoundMatrixData[T] = scala.Tuple2[Collection[AbstractVector[T]], Int];
        class CompoundMatrixIso[T](implicit ctT: ClassTag[T], eT: Elem[T]) extends Iso[CompoundMatrixData[T], CompoundMatrix[T]]()(pairElement(implicitly[Elem[Collection[AbstractVector[T]]]], implicitly[Elem[Int]])) {
          override def from(p: Rep[CompoundMatrix[T]]) = scala.Tuple2(p.rows, p.numColumns);
          override def to(p: Rep[scala.Tuple2[Collection[AbstractVector[T]], Int]]) = {
            val x$1 = (p: @scala.unchecked) match {
              case Pair((rows @ _), (numColumns @ _)) => scala.Tuple2(rows, numColumns)
            };
            val rows = x$1._1;
            val numColumns = x$1._2;
            CompoundMatrix(rows, numColumns)
          };
          lazy val defaultRepTo = Default.defaultVal[Rep[CompoundMatrix[T]]](CompoundMatrix(element[Collection[AbstractVector[T]]].defaultRepValue, 0));
          lazy val eTo = new CompoundMatrixElem[T](this)
        };
        abstract class CompoundMatrixCompanionAbs extends CompanionBase[CompoundMatrixCompanionAbs] with CompoundMatrixCompanion {
          override def toString = "CompoundMatrix";
          def apply[T](p: Rep[CompoundMatrixData[T]])(implicit ctT: ClassTag[T], eT: Elem[T]): Rep[CompoundMatrix[T]] = isoCompoundMatrix(ctT, eT).to(p);
          def apply[T](rows: Rep[Collection[AbstractVector[T]]], numColumns: Rep[Int])(implicit ctT: ClassTag[T], eT: Elem[T]): Rep[CompoundMatrix[T]] = mkCompoundMatrix(rows, numColumns)
        };
        object CompoundMatrixMatcher {
          def unapply[T](p: Rep[AbstractMatrix[T]]) = unmkCompoundMatrix(p)
        };
        def CompoundMatrix: Rep[CompoundMatrixCompanionAbs];
        implicit def proxyCompoundMatrixCompanion(p: Rep[CompoundMatrixCompanionAbs]): CompoundMatrixCompanionAbs = proxyOps[CompoundMatrixCompanionAbs](p);
        implicit case object CompoundMatrixCompanionElem extends CompanionElem[CompoundMatrixCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[CompoundMatrixCompanionAbs];
          protected def getDefaultRep = CompoundMatrix
        };
        implicit def proxyCompoundMatrix[T](p: Rep[CompoundMatrix[T]]): CompoundMatrix[T] = proxyOps[CompoundMatrix[T]](p);
        implicit class ExtendedCompoundMatrix[T](p: Rep[CompoundMatrix[T]])(implicit ctT: ClassTag[T], eT: Elem[T]) {
          def toData: Rep[CompoundMatrixData[T]] = isoCompoundMatrix(ctT, eT).from(p)
        };
        implicit def isoCompoundMatrix[T](implicit ctT: ClassTag[T], eT: Elem[T]): Iso[CompoundMatrixData[T], CompoundMatrix[T]] = new CompoundMatrixIso[T]();
        def mkCompoundMatrix[T](rows: Rep[Collection[AbstractVector[T]]], numColumns: Rep[Int])(implicit ctT: ClassTag[T], eT: Elem[T]): Rep[CompoundMatrix[T]];
        def unmkCompoundMatrix[T](p: Rep[AbstractMatrix[T]]): Option[scala.Tuple2[Rep[Collection[AbstractVector[T]]], Rep[Int]]]
      };
      trait MatricesSeq extends MatricesDsl with ScalanSeq { self: LinearAlgebraDslSeq =>
        lazy val AbstractMatrix: Rep[AbstractMatrixCompanionAbs] = {
          final class $anon extends AbstractMatrixCompanionAbs with UserTypeSeq[AbstractMatrixCompanionAbs] {
            lazy val selfType = element[AbstractMatrixCompanionAbs]
          };
          new $anon()
        };
        case class SeqCompoundMatrix[T](override val rows: Rep[Collection[AbstractVector[T]]], override val numColumns: Rep[Int])(implicit ctT: ClassTag[T], eT: Elem[T]) extends CompoundMatrix[T](rows, numColumns) with UserTypeSeq[CompoundMatrix[T]] {
          lazy val selfType = element[CompoundMatrix[T]]
        };
        lazy val CompoundMatrix = {
          final class $anon extends CompoundMatrixCompanionAbs with UserTypeSeq[CompoundMatrixCompanionAbs] {
            lazy val selfType = element[CompoundMatrixCompanionAbs]
          };
          new $anon()
        };
        def mkCompoundMatrix[T](rows: Rep[Collection[AbstractVector[T]]], numColumns: Rep[Int])(implicit ctT: ClassTag[T], eT: Elem[T]): Rep[CompoundMatrix[T]] = new SeqCompoundMatrix[T](rows, numColumns);
        def unmkCompoundMatrix[T](p: Rep[AbstractMatrix[T]]) = p match {
          case (p @ ((_): CompoundMatrix[T] @unchecked)) => Some(scala.Tuple2(p.rows, p.numColumns))
          case _ => None
        }
      };
      trait MatricesExp extends MatricesDsl with ScalanExp { self: LinearAlgebraDslExp =>
        lazy val AbstractMatrix: Rep[AbstractMatrixCompanionAbs] = {
          final class $anon extends AbstractMatrixCompanionAbs with UserTypeDef[AbstractMatrixCompanionAbs] {
            lazy val selfType = element[AbstractMatrixCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        case class ExpCompoundMatrix[T](override val rows: Rep[Collection[AbstractVector[T]]], override val numColumns: Rep[Int])(implicit ctT: ClassTag[T], eT: Elem[T]) extends CompoundMatrix[T](rows, numColumns) with UserTypeDef[CompoundMatrix[T]] {
          lazy val selfType = element[CompoundMatrix[T]];
          override def mirror(t: Transformer) = ExpCompoundMatrix[T](t(rows), t(numColumns))
        };
        lazy val CompoundMatrix: Rep[CompoundMatrixCompanionAbs] = {
          final class $anon extends CompoundMatrixCompanionAbs with UserTypeDef[CompoundMatrixCompanionAbs] {
            lazy val selfType = element[CompoundMatrixCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object CompoundMatrixMethods {
          object numRows {
            def unapply(d: (Def[_$2] forSome { 
              type _$2
            })): Option[(Rep[CompoundMatrix[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(CompoundMatrixElem[_$3] forSome { 
  type _$3
})].&&(method.getName.==("numRows")) => Some(receiver).asInstanceOf[Option[(Rep[CompoundMatrix[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$4] forSome { 
              type _$4
            })): Option[(Rep[CompoundMatrix[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object columns {
            def unapply(d: (Def[_$5] forSome { 
              type _$5
            })): Option[(scala.Tuple2[Rep[CompoundMatrix[T]], Rep[Numer[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((n @ _), _*), _) if receiver.elem.isInstanceOf[(CompoundMatrixElem[_$6] forSome { 
  type _$6
})].&&(method.getName.==("columns")) => Some(scala.Tuple2(receiver, n)).asInstanceOf[Option[(scala.Tuple2[Rep[CompoundMatrix[T]], Rep[Numer[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$7] forSome { 
              type _$7
            })): Option[(scala.Tuple2[Rep[CompoundMatrix[T]], Rep[Numer[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object CompoundMatrixCompanionMethods;
        def mkCompoundMatrix[T](rows: Rep[Collection[AbstractVector[T]]], numColumns: Rep[Int])(implicit ctT: ClassTag[T], eT: Elem[T]): Rep[CompoundMatrix[T]] = new ExpCompoundMatrix[T](rows, numColumns);
        def unmkCompoundMatrix[T](p: Rep[AbstractMatrix[T]]) = p.elem.asInstanceOf[(Elem[_$8] forSome { 
          type _$8
        })] match {
          case ((_): CompoundMatrixElem[T] @unchecked) => Some(scala.Tuple2(p.asRep[CompoundMatrix[T]].rows, p.asRep[CompoundMatrix[T]].numColumns))
          case _ => None
        };
        object AbstractMatrixMethods {
          object ctT {
            def unapply(d: (Def[_$9] forSome { 
              type _$9
            })): Option[(Rep[AbstractMatrix[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$10, _$11] forSome { 
  type _$10;
  type _$11
})].&&(method.getName.==("ctT")) => Some(receiver).asInstanceOf[Option[(Rep[AbstractMatrix[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$12] forSome { 
              type _$12
            })): Option[(Rep[AbstractMatrix[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object numColumns {
            def unapply(d: (Def[_$13] forSome { 
              type _$13
            })): Option[(Rep[AbstractMatrix[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$14, _$15] forSome { 
  type _$14;
  type _$15
})].&&(method.getName.==("numColumns")) => Some(receiver).asInstanceOf[Option[(Rep[AbstractMatrix[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$16] forSome { 
              type _$16
            })): Option[(Rep[AbstractMatrix[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object numRows {
            def unapply(d: (Def[_$17] forSome { 
              type _$17
            })): Option[(Rep[AbstractMatrix[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$18, _$19] forSome { 
  type _$18;
  type _$19
})].&&(method.getName.==("numRows")) => Some(receiver).asInstanceOf[Option[(Rep[AbstractMatrix[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$20] forSome { 
              type _$20
            })): Option[(Rep[AbstractMatrix[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object rows {
            def unapply(d: (Def[_$21] forSome { 
              type _$21
            })): Option[(Rep[AbstractMatrix[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$22, _$23] forSome { 
  type _$22;
  type _$23
})].&&(method.getName.==("rows")) => Some(receiver).asInstanceOf[Option[(Rep[AbstractMatrix[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$24] forSome { 
              type _$24
            })): Option[(Rep[AbstractMatrix[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object columns {
            def unapply(d: (Def[_$25] forSome { 
              type _$25
            })): Option[(scala.Tuple2[Rep[AbstractMatrix[T]], Rep[Numer[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((n @ _), _*), _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$26, _$27] forSome { 
  type _$26;
  type _$27
})].&&(method.getName.==("columns")) => Some(scala.Tuple2(receiver, n)).asInstanceOf[Option[(scala.Tuple2[Rep[AbstractMatrix[T]], Rep[Numer[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$28] forSome { 
              type _$28
            })): Option[(scala.Tuple2[Rep[AbstractMatrix[T]], Rep[Numer[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object * {
            def unapply(d: (Def[_$29] forSome { 
              type _$29
            })): Option[(scala.Tuple4[Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((vector @ _), (n @ _), (m @ _), _*), _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$30, _$31] forSome { 
  type _$30;
  type _$31
})].&&(method.getName.==("$times")) => Some(scala.Tuple4(receiver, vector, n, m)).asInstanceOf[Option[(scala.Tuple4[Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$32] forSome { 
              type _$32
            })): Option[(scala.Tuple4[Rep[AbstractMatrix[T]], Rep[AbstractVector[T]], Rep[Numer[T]], Rep[NumMonoid[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object AbstractMatrixCompanionMethods
      };
      trait Matrices extends Base { self: LinearAlgebraDsl =>
        trait AbstractMatrix[T] extends Reifiable[AbstractMatrix[T]] {
          implicit def eT: Elem[T];
          implicit def ctT: Rep[ClassTag[T]];
          def numColumns: Rep[Int];
          def numRows: Rep[Int];
          def rows: Rep[Collection[AbstractVector[T]]];
          def columns(implicit n: Rep[Numer[T]]): Rep[Collection[AbstractVector[T]]];
          def *(vector: Rep[AbstractVector[T]])(implicit n: Rep[Numer[T]], m: Rep[NumMonoid[T]]): Rep[AbstractVector[T]] = DenseVector(rows.map(fun(((r: Rep[AbstractVector[T]]) => r.dot(vector)))))
        };
        abstract class CompoundMatrix[T](val rows: Rep[Collection[AbstractVector[T]]], val numColumns: Rep[Int])(implicit val ctT: Rep[ClassTag[T]], val eT: Elem[T]) extends AbstractMatrix[T] with Product with Serializable {
          def numRows = rows.length;
          def columns(implicit n: Rep[Numer[T]]): Rep[Collection[AbstractVector[T]]] = Collection(toRep(0).to(numColumns).map(fun(((j: Rep[Int]) => ((DenseVector(rows.map(fun(((vec: Rep[AbstractVector[T]]) => vec(j)))))): Rep[AbstractVector[T]])))).toArray)
        };
        trait AbstractMatrixCompanion;
        trait CompoundMatrixCompanion
      };
      trait MatricesDsl extends MatricesAbs { self: LinearAlgebraDsl =>
        
      };
      trait MatricesDslSeq extends MatricesSeq { self: LinearAlgebraDslSeq =>
        
      };
      trait MatricesDslExp extends MatricesExp { self: LinearAlgebraDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AA5BYnN0cmFjdE1hdHJpeHNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHNxAH4AD3QAB1Byb2R1Y3RxAH4AB3NxAH4AD3QADFNlcmlhbGl6YWJsZXEAfgAHcQB+AAl4cQB+AAdzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZ3Mn6+5kxTAAXAIAAUwABGFyZ3NxAH4AAXhwc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NBcmcFc9jWC9QpPwIACFoAB2ltcEZsYWdaAAxpc0VsZW1PckNvbnRaAAhvdmVyRmxhZ1oAB3ZhbEZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBldAAgTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTVHBlRXhwcjt4cAAAAAFxAH4AB3NyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHIADHNjYWxhLk9wdGlvbv5pN/3bDmZ0AgAAeHB0AARyb3dzc3EAfgAPdAAKQ29sbGVjdGlvbnNxAH4ABnNxAH4AD3QADkFic3RyYWN0VmVjdG9yc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4c3EAfgAcAAAAAXEAfgAHcQB+ACF0AApudW1Db2x1bW5zc3IAI3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlUHJpbWl0aXZlNcO7VfC+TgUCAAJMABJkZWZhdWx0VmFsdWVTdHJpbmdxAH4ABEwABG5hbWVxAH4ABHhwdAABMHQAA0ludHEAfgAJeHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZERlZt1ZcXG3MCsOAgAKWgAMaXNFbGVtT3JDb250WgAKaXNJbXBsaWNpdFoACmlzT3ZlcnJpZGVMAAthbm5vdGF0aW9uc3EAfgABTAALYXJnU2VjdGlvbnNxAH4AAUwABGJvZHlxAH4AA0wABG5hbWVxAH4ABEwACm92ZXJsb2FkSWRxAH4AA0wAB3RwZUFyZ3NxAH4AAUwABnRwZVJlc3EAfgADeHAAAABxAH4AB3EAfgAHc3IACnNjYWxhLlNvbWURIvJpXqGLdAIAAUwAAXh0ABJMamF2YS9sYW5nL09iamVjdDt4cQB+ACBzcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxlY3QL3WllZUD5hQIAAkwABGV4cHJ0AB1Mc2NhbGFuL21ldGEvU2NhbGFu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B3EAfgAJeHEAfgAJeHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0RGVmAb40kjQUsbICAAlaAAhiaXRtYXAkMEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+AAxMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAcQB+AAdxAH4AB3NxAH4ABnNxAH4AMgABAHEAfgAHcQB+AAdxAH4AIXQAA2N0VHEAfgAhcQB+AAdzcQB+ADRzcQB+AA90AAhDbGFzc1RhZ3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHNxAH4AMgAAAHEAfgAHcQB+AAdxAH4AIXQACm51bUNvbHVtbnNxAH4AIXEAfgAHc3EAfgA0cQB+AC5zcQB+ADIAAABxAH4AB3EAfgAHcQB+ACF0AAdudW1Sb3dzcQB+ACFxAH4AB3NxAH4ANHEAfgAuc3EAfgAyAAAAcQB+AAdxAH4AB3EAfgAhdAAEcm93c3EAfgAhcQB+AAdzcQB+ADRzcQB+AA90AApDb2xsZWN0aW9uc3EAfgAGc3EAfgAPdAAOQWJzdHJhY3RWZWN0b3JzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+ADIAAABxAH4AB3NxAH4ABnNxAH4AQXNxAH4ABnNxAH4ARAEAAHEAfgAHcQB+ACF0AAFuc3EAfgAPdAAFTnVtZXJzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4AIXQAB2NvbHVtbnNxAH4AIXEAfgAHc3EAfgA0c3EAfgAPdAAKQ29sbGVjdGlvbnNxAH4ABnNxAH4AD3QADkFic3RyYWN0VmVjdG9yc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4c3EAfgAyAAAAcQB+AAdzcQB+AAZzcQB+AEFzcQB+AAZzcQB+AEQAAABxAH4AB3EAfgAhdAAGdmVjdG9yc3EAfgAPdAAOQWJzdHJhY3RWZWN0b3JzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+AEFzcQB+AAZzcQB+AEQBAABxAH4AB3EAfgAhdAABbnNxAH4AD3QABU51bWVyc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4c3EAfgBEAQAAcQB+AAdxAH4AIXQAAW1zcQB+AA90AAlOdW1Nb25vaWRzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhzcQB+ADRzcQB+AE1zcQB+AAZzcQB+AAZzcQB+AE1zcQB+AAZzcQB+AAZzcQB+AFVzcQB+AAZzcQB+AFgAAHNxAH4AWnQAAXJzcQB+ADRzcQB+AA90AA5BYnN0cmFjdFZlY3RvcnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHNxAH4ATXNxAH4ABnNxAH4ABnNxAH4AOnQABnZlY3RvcnEAfgAJeHEAfgAJeHNxAH4AN3NxAH4AOnQAAXJ0AANkb3RxAH4AB3EAfgAJeHEAfgAJeHNxAH4AN3NxAH4AOnQABHJvd3NxAH4Ae3EAfgAHcQB+AAl4cQB+AAl4c3EAfgA6dAALRGVuc2VWZWN0b3JxAH4AB3QABiR0aW1lc3EAfgAhcQB+AAdzcQB+ADRzcQB+AA90AA5BYnN0cmFjdFZlY3RvcnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgAhcHQADkFic3RyYWN0TWF0cml4cQB+ACFzcQB+AAZzcQB+AKhxAH4AIXEAfgAHdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AK1xAH4AIXNxAH4ABnNyACFzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0ltcG9ydFN0YXSsgKBKw8YbIgIAAUwABG5hbWVxAH4ABHhwdAAWc2NhbGEucmVmbGVjdC5DbGFzc1RhZ3EAfgAJeHEAfgAHdAAITWF0cmljZXN0ABNwYXJhZGlzZS5saW5hbGdlYnJhc3EAfgA0c3IAInNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTU2VsZlR5cGVEZWZ+951OKs6z5QIAAkwACmNvbXBvbmVudHNxAH4AAUwABG5hbWVxAH4ABHhwc3EAfgAGc3EAfgAPdAANTGluZWFyQWxnZWJyYXEAfgAHcQB+AAl4dAAEc2VsZnEAfgAh"
    }
  }
}