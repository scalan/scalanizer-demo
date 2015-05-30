//package paradise.linalgebra {
//  package implOfMatrices {
//    object StagedEvaluation {
//      import scalan._;
//      import paradise.implOfNumMonoids.StagedEvaluation._;
//      import paradise.collections.implOfCollections.StagedEvaluation._;
//      import paradise.linalgebra.implOfVectors.StagedEvaluation._;
//      import paradise.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
//      import scala.reflect.runtime.universe._;
//      import scala.reflect._;
//      import scalan.common.Default;
//      trait MatricesAbs extends Matrices with ScalanDsl { self: LinearAlgebraDsl =>
//        implicit def proxyAbstractMatrix[T](p: Rep[AbstractMatrix[T]]): AbstractMatrix[T] = proxyOps[AbstractMatrix[T]](p)(classTag[AbstractMatrix[T]]);
//        class AbstractMatrixElem[T, To <: AbstractMatrix[T]](implicit val eT: Elem[T]) extends EntityElem[To] {
//          override def isEntityType = true;
//          override lazy val tag = {
//            implicit val tagT = eT.tag;
//            weakTypeTag[AbstractMatrix[T]].asInstanceOf[WeakTypeTag[To]]
//          };
//          override def convert(x: Rep[(Reifiable[_$1] forSome {
//            type _$1
//          })]) = {
//            val conv = fun(((x: Rep[AbstractMatrix[T]]) => convertAbstractMatrix(x)));
//            tryConvert(element[AbstractMatrix[T]], this, x, conv)
//          };
//          def convertAbstractMatrix(x: Rep[AbstractMatrix[T]]): Rep[To] = {
//            assert(x.selfType1 match {
//              case ((_): AbstractMatrixElem[(_), (_)]) => true
//              case _ => false
//            });
//            x.asRep[To]
//          };
//          override def getDefaultRep: Rep[To] = ???
//        };
//        implicit def abstractMatrixElement[T](implicit eT: Elem[T]): Elem[AbstractMatrix[T]] = new AbstractMatrixElem[T, AbstractMatrix[T]]();
//        implicit case object AbstractMatrixCompanionElem extends CompanionElem[AbstractMatrixCompanionAbs] with scala.Product with scala.Serializable {
//          lazy val tag = weakTypeTag[AbstractMatrixCompanionAbs];
//          protected def getDefaultRep = AbstractMatrix
//        };
//        abstract class AbstractMatrixCompanionAbs extends CompanionBase[AbstractMatrixCompanionAbs] with AbstractMatrixCompanion {
//          override def toString = "AbstractMatrix"
//        };
//        def AbstractMatrix: Rep[AbstractMatrixCompanionAbs];
//        implicit def proxyAbstractMatrixCompanion(p: Rep[AbstractMatrixCompanion]): AbstractMatrixCompanion = proxyOps[AbstractMatrixCompanion](p);
//        class CompoundMatrixElem[T](val iso: Iso[CompoundMatrixData[T], CompoundMatrix[T]])(implicit ctT: ClassTag[T], eT: Elem[T]) extends AbstractMatrixElem[T, CompoundMatrix[T]] with ConcreteElem[CompoundMatrixData[T], CompoundMatrix[T]] {
//          override def convertAbstractMatrix(x: Rep[AbstractMatrix[T]]) = CompoundMatrix(x.rows, x.numColumns);
//          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
//          override lazy val tag = {
//            implicit val tagT = eT.tag;
//            weakTypeTag[CompoundMatrix[T]]
//          }
//        };
//        type CompoundMatrixData[T] = scala.Tuple2[Collection[Vector[T]], Int];
//        class CompoundMatrixIso[T](implicit ctT: ClassTag[T], eT: Elem[T]) extends Iso[CompoundMatrixData[T], CompoundMatrix[T]]()(pairElement(implicitly[Elem[Collection[Vector[T]]]], implicitly[Elem[Int]])) {
//          override def from(p: Rep[CompoundMatrix[T]]) = scala.Tuple2(p.rows, p.numColumns);
//          override def to(p: Rep[scala.Tuple2[Collection[Vector[T]], Int]]) = {
//            val x$1 = (p: @scala.unchecked) match {
//              case Pair((rows @ _), (numColumns @ _)) => scala.Tuple2(rows, numColumns)
//            };
//            val rows = x$1._1;
//            val numColumns = x$1._2;
//            CompoundMatrix(rows, numColumns)
//          };
//          lazy val defaultRepTo = Default.defaultVal[Rep[CompoundMatrix[T]]](CompoundMatrix(element[Collection[Vector[T]]].defaultRepValue, 0));
//          lazy val eTo = new CompoundMatrixElem[T](this)
//        };
//        abstract class CompoundMatrixCompanionAbs extends CompanionBase[CompoundMatrixCompanionAbs] with CompoundMatrixCompanion {
//          override def toString = "CompoundMatrix";
//          def apply[T](p: Rep[CompoundMatrixData[T]])(implicit ctT: ClassTag[T], eT: Elem[T]): Rep[CompoundMatrix[T]] = isoCompoundMatrix(ctT, eT).to(p);
//          def apply[T](rows: Rep[Collection[Vector[T]]], numColumns: Rep[Int])(implicit ctT: ClassTag[T], eT: Elem[T]): Rep[CompoundMatrix[T]] = mkCompoundMatrix(rows, numColumns)
//        };
//        object CompoundMatrixMatcher {
//          def unapply[T](p: Rep[AbstractMatrix[T]]) = unmkCompoundMatrix(p)
//        };
//        def CompoundMatrix: Rep[CompoundMatrixCompanionAbs];
//        implicit def proxyCompoundMatrixCompanion(p: Rep[CompoundMatrixCompanionAbs]): CompoundMatrixCompanionAbs = proxyOps[CompoundMatrixCompanionAbs](p);
//        implicit case object CompoundMatrixCompanionElem extends CompanionElem[CompoundMatrixCompanionAbs] with scala.Product with scala.Serializable {
//          lazy val tag = weakTypeTag[CompoundMatrixCompanionAbs];
//          protected def getDefaultRep = CompoundMatrix
//        };
//        implicit def proxyCompoundMatrix[T](p: Rep[CompoundMatrix[T]]): CompoundMatrix[T] = proxyOps[CompoundMatrix[T]](p);
//        implicit class ExtendedCompoundMatrix[T](p: Rep[CompoundMatrix[T]])(implicit ctT: ClassTag[T], eT: Elem[T]) {
//          def toData: Rep[CompoundMatrixData[T]] = isoCompoundMatrix(ctT, eT).from(p)
//        };
//        implicit def isoCompoundMatrix[T](implicit ctT: ClassTag[T], eT: Elem[T]): Iso[CompoundMatrixData[T], CompoundMatrix[T]] = new CompoundMatrixIso[T]();
//        def mkCompoundMatrix[T](rows: Rep[Collection[Vector[T]]], numColumns: Rep[Int])(implicit ctT: ClassTag[T], eT: Elem[T]): Rep[CompoundMatrix[T]];
//        def unmkCompoundMatrix[T](p: Rep[AbstractMatrix[T]]): Option[scala.Tuple2[Rep[Collection[Vector[T]]], Rep[Int]]]
//      };
//      trait MatricesSeq extends MatricesDsl with ScalanSeq { self: LinearAlgebraDslSeq =>
//        lazy val AbstractMatrix: Rep[AbstractMatrixCompanionAbs] = {
//          final class $anon extends AbstractMatrixCompanionAbs with UserTypeSeq[AbstractMatrixCompanionAbs] {
//            lazy val selfType = element[AbstractMatrixCompanionAbs]
//          };
//          new $anon()
//        };
//        case class SeqCompoundMatrix[T](override val rows: Rep[Collection[Vector[T]]], override val numColumns: Rep[Int])(implicit ctT: ClassTag[T], eT: Elem[T]) extends CompoundMatrix[T](rows, numColumns) with UserTypeSeq[CompoundMatrix[T]] {
//          lazy val selfType = element[CompoundMatrix[T]]
//        };
//        lazy val CompoundMatrix = {
//          final class $anon extends CompoundMatrixCompanionAbs with UserTypeSeq[CompoundMatrixCompanionAbs] {
//            lazy val selfType = element[CompoundMatrixCompanionAbs]
//          };
//          new $anon()
//        };
//        def mkCompoundMatrix[T](rows: Rep[Collection[Vector[T]]], numColumns: Rep[Int])(implicit ctT: ClassTag[T], eT: Elem[T]): Rep[CompoundMatrix[T]] = new SeqCompoundMatrix[T](rows, numColumns);
//        def unmkCompoundMatrix[T](p: Rep[AbstractMatrix[T]]) = p match {
//          case (p @ ((_): CompoundMatrix[T] @unchecked)) => Some(scala.Tuple2(p.rows, p.numColumns))
//          case _ => None
//        }
//      };
//      trait MatricesExp extends MatricesDsl with ScalanExp { self: LinearAlgebraDslExp =>
//        lazy val AbstractMatrix: Rep[AbstractMatrixCompanionAbs] = {
//          final class $anon extends AbstractMatrixCompanionAbs with UserTypeDef[AbstractMatrixCompanionAbs] {
//            lazy val selfType = element[AbstractMatrixCompanionAbs];
//            override def mirror(t: Transformer) = this
//          };
//          new $anon()
//        };
//        case class ExpCompoundMatrix[T](override val rows: Rep[Collection[Vector[T]]], override val numColumns: Rep[Int])(implicit ctT: ClassTag[T], eT: Elem[T]) extends CompoundMatrix[T](rows, numColumns) with UserTypeDef[CompoundMatrix[T]] {
//          lazy val selfType = element[CompoundMatrix[T]];
//          override def mirror(t: Transformer) = ExpCompoundMatrix[T](t(rows), t(numColumns))
//        };
//        lazy val CompoundMatrix: Rep[CompoundMatrixCompanionAbs] = {
//          final class $anon extends CompoundMatrixCompanionAbs with UserTypeDef[CompoundMatrixCompanionAbs] {
//            lazy val selfType = element[CompoundMatrixCompanionAbs];
//            override def mirror(t: Transformer) = this
//          };
//          new $anon()
//        };
//        object CompoundMatrixMethods {
//          object numRows {
//            def unapply(d: (Def[_$2] forSome {
//              type _$2
//            })): Option[(Rep[CompoundMatrix[T]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(CompoundMatrixElem[_$3] forSome {
//  type _$3
//})].&&(method.getName.==("numRows")) => Some(receiver).asInstanceOf[Option[(Rep[CompoundMatrix[T]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$4] forSome {
//              type _$4
//            })): Option[(Rep[CompoundMatrix[T]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object columns {
//            def unapply(d: (Def[_$5] forSome {
//              type _$5
//            })): Option[(scala.Tuple2[Rep[CompoundMatrix[T]], Rep[Numeric[T]]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((n @ _), _*), _) if receiver.elem.isInstanceOf[(CompoundMatrixElem[_$6] forSome {
//  type _$6
//})].&&(method.getName.==("columns")) => Some(scala.Tuple2(receiver, n)).asInstanceOf[Option[(scala.Tuple2[Rep[CompoundMatrix[T]], Rep[Numeric[T]]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$7] forSome {
//              type _$7
//            })): Option[(scala.Tuple2[Rep[CompoundMatrix[T]], Rep[Numeric[T]]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          }
//        };
//        object CompoundMatrixCompanionMethods;
//        def mkCompoundMatrix[T](rows: Rep[Collection[Vector[T]]], numColumns: Rep[Int])(implicit ctT: ClassTag[T], eT: Elem[T]): Rep[CompoundMatrix[T]] = new ExpCompoundMatrix[T](rows, numColumns);
//        def unmkCompoundMatrix[T](p: Rep[AbstractMatrix[T]]) = p.elem.asInstanceOf[(Elem[_$8] forSome {
//          type _$8
//        })] match {
//          case ((_): CompoundMatrixElem[T] @unchecked) => Some(scala.Tuple2(p.asRep[CompoundMatrix[T]].rows, p.asRep[CompoundMatrix[T]].numColumns))
//          case _ => None
//        };
//        object AbstractMatrixMethods {
//          object ctT {
//            def unapply(d: (Def[_$9] forSome {
//              type _$9
//            })): Option[(Rep[AbstractMatrix[T]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$10, _$11] forSome {
//  type _$10;
//  type _$11
//})].&&(method.getName.==("ctT")) => Some(receiver).asInstanceOf[Option[(Rep[AbstractMatrix[T]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$12] forSome {
//              type _$12
//            })): Option[(Rep[AbstractMatrix[T]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object numColumns {
//            def unapply(d: (Def[_$13] forSome {
//              type _$13
//            })): Option[(Rep[AbstractMatrix[T]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$14, _$15] forSome {
//  type _$14;
//  type _$15
//})].&&(method.getName.==("numColumns")) => Some(receiver).asInstanceOf[Option[(Rep[AbstractMatrix[T]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$16] forSome {
//              type _$16
//            })): Option[(Rep[AbstractMatrix[T]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object numRows {
//            def unapply(d: (Def[_$17] forSome {
//              type _$17
//            })): Option[(Rep[AbstractMatrix[T]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$18, _$19] forSome {
//  type _$18;
//  type _$19
//})].&&(method.getName.==("numRows")) => Some(receiver).asInstanceOf[Option[(Rep[AbstractMatrix[T]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$20] forSome {
//              type _$20
//            })): Option[(Rep[AbstractMatrix[T]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object rows {
//            def unapply(d: (Def[_$21] forSome {
//              type _$21
//            })): Option[(Rep[AbstractMatrix[T]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$22, _$23] forSome {
//  type _$22;
//  type _$23
//})].&&(method.getName.==("rows")) => Some(receiver).asInstanceOf[Option[(Rep[AbstractMatrix[T]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$24] forSome {
//              type _$24
//            })): Option[(Rep[AbstractMatrix[T]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object columns {
//            def unapply(d: (Def[_$25] forSome {
//              type _$25
//            })): Option[(scala.Tuple2[Rep[AbstractMatrix[T]], Rep[Numeric[T]]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((n @ _), _*), _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$26, _$27] forSome {
//  type _$26;
//  type _$27
//})].&&(method.getName.==("columns")) => Some(scala.Tuple2(receiver, n)).asInstanceOf[Option[(scala.Tuple2[Rep[AbstractMatrix[T]], Rep[Numeric[T]]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$28] forSome {
//              type _$28
//            })): Option[(scala.Tuple2[Rep[AbstractMatrix[T]], Rep[Numeric[T]]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object * {
//            def unapply(d: (Def[_$29] forSome {
//              type _$29
//            })): Option[(scala.Tuple4[Rep[AbstractMatrix[T]], Rep[Vector[T]], Rep[Numeric[T]], Rep[NumMonoid[T]]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((vector @ _), (n @ _), (m @ _), _*), _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$30, _$31] forSome {
//  type _$30;
//  type _$31
//})].&&(method.getName.==("$times")) => Some(scala.Tuple4(receiver, vector, n, m)).asInstanceOf[Option[(scala.Tuple4[Rep[AbstractMatrix[T]], Rep[Vector[T]], Rep[Numeric[T]], Rep[NumMonoid[T]]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$32] forSome {
//              type _$32
//            })): Option[(scala.Tuple4[Rep[AbstractMatrix[T]], Rep[Vector[T]], Rep[Numeric[T]], Rep[NumMonoid[T]]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          }
//        };
//        object AbstractMatrixCompanionMethods
//      };
//      trait Matrices extends Base { self: LinearAlgebraDsl =>
//        trait AbstractMatrix[T] extends Reifiable[AbstractMatrix[T]] {
//          implicit def eT: Elem[T];
//          implicit def ctT: Rep[ClassTag[T]];
//          def numColumns: Rep[Int];
//          def numRows: Rep[Int];
//          def rows: Rep[Collection[Vector[T]]];
//          def columns(implicit n: Rep[Numeric[T]]): Rep[Collection[Vector[T]]];
//          def *(vector: Rep[Vector[T]])(implicit n: Rep[Numeric[T]], m: Rep[NumMonoid[T]]): Rep[Vector[T]] = DenseVector(rows.map(fun(((r: Rep[Vector[T]]) => r.dot(vector)))))
//        };
//        abstract class CompoundMatrix[T](val rows: Rep[Collection[Vector[T]]], val numColumns: Rep[Int])(implicit val ctT: Rep[ClassTag[T]], val eT: Elem[T]) extends AbstractMatrix[T] with Product with Serializable {
//          def numRows = rows.length;
//          def columns(implicit n: Rep[Numeric[T]]): Rep[Collection[Vector[T]]] = Collection(toRep(0).to(numColumns).map(fun(((j: Rep[Int]) => ((DenseVector(rows.map(fun(((vec: Rep[Vector[T]]) => vec(j)))))): Rep[Vector[T]])))).toArray)
//        };
//        trait AbstractMatrixCompanion;
//        trait CompoundMatrixCompanion
//      };
//      trait MatricesDsl extends MatricesAbs { self: LinearAlgebraDsl =>
//
//      };
//      trait MatricesDslSeq extends MatricesSeq { self: LinearAlgebraDslSeq =>
//
//      };
//      trait MatricesDslExp extends MatricesExp { self: LinearAlgebraDslExp =>
//
//      };
//      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AA5BYnN0cmFjdE1hdHJpeHNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHNxAH4AD3QAB1Byb2R1Y3RxAH4AB3NxAH4AD3QADFNlcmlhbGl6YWJsZXEAfgAHcQB+AAl4cQB+AAdzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZ3Mn6+5kxTAAXAIAAUwABGFyZ3NxAH4AAXhwc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NBcmcFc9jWC9QpPwIACFoAB2ltcEZsYWdaAAxpc0VsZW1PckNvbnRaAAhvdmVyRmxhZ1oAB3ZhbEZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBldAAgTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTVHBlRXhwcjt4cAAAAAFxAH4AB3NyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHIADHNjYWxhLk9wdGlvbv5pN/3bDmZ0AgAAeHB0AARyb3dzc3EAfgAPdAAKQ29sbGVjdGlvbnNxAH4ABnNxAH4AD3QABlZlY3RvcnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHNxAH4AHAAAAAFxAH4AB3EAfgAhdAAKbnVtQ29sdW1uc3NyACNzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZVByaW1pdGl2ZTXDu1Xwvk4FAgACTAASZGVmYXVsdFZhbHVlU3RyaW5ncQB+AARMAARuYW1lcQB+AAR4cHQAATB0AANJbnRxAH4ACXhzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2REZWbdWXFxtzArDgIACloADGlzRWxlbU9yQ29udFoACmlzSW1wbGljaXRaAAppc092ZXJyaWRlTAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0cGVSZXNxAH4AA3hwAAAAcQB+AAdxAH4AB3NyAApzY2FsYS5Tb21lESLyaV6hi3QCAAFMAAF4dAASTGphdmEvbGFuZy9PYmplY3Q7eHEAfgAgc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTU2VsZWN0C91pZWVA+YUCAAJMAARleHBydAAdTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTRXhw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bWV0YS5TY2FsYW5Bc3QkU1RyYWl0RGVmAb40kjQUsbICAAlaAAhiaXRtYXAkMEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+AAxMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAcQB+AAdxAH4AB3NxAH4ABnNxAH4AMgABAHEAfgAHcQB+AAdxAH4AIXQAA2N0VHEAfgAhcQB+AAdzcQB+ADRzcQB+AA90AAhDbGFzc1RhZ3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHNxAH4AMgAAAHEAfgAHcQB+AAdxAH4AIXQACm51bUNvbHVtbnNxAH4AIXEAfgAHc3EAfgA0cQB+AC5zcQB+ADIAAABxAH4AB3EAfgAHcQB+ACF0AAdudW1Sb3dzcQB+ACFxAH4AB3NxAH4ANHEAfgAuc3EAfgAyAAAAcQB+AAdxAH4AB3EAfgAhdAAEcm93c3EAfgAhcQB+AAdzcQB+ADRzcQB+AA90AApDb2xsZWN0aW9uc3EAfgAGc3EAfgAPdAAGVmVjdG9yc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4c3EAfgAyAAAAcQB+AAdzcQB+AAZzcQB+AEFzcQB+AAZzcQB+AEQBAABxAH4AB3EAfgAhdAABbnNxAH4AD3QAB051bWVyaWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4AIXQAB2NvbHVtbnNxAH4AIXEAfgAHc3EAfgA0c3EAfgAPdAAKQ29sbGVjdGlvbnNxAH4ABnNxAH4AD3QABlZlY3RvcnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHNxAH4AMgAAAHEAfgAHc3EAfgAGc3EAfgBBc3EAfgAGc3EAfgBEAAAAcQB+AAdxAH4AIXQABnZlY3RvcnNxAH4AD3QABlZlY3RvcnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHNxAH4AQXNxAH4ABnNxAH4ARAEAAHEAfgAHcQB+ACF0AAFuc3EAfgAPdAAHTnVtZXJpY3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHNxAH4ARAEAAHEAfgAHcQB+ACF0AAFtc3EAfgAPdAAJTnVtTW9ub2lkc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4c3EAfgA0c3EAfgBNc3EAfgAGc3EAfgAGc3EAfgBNc3EAfgAGc3EAfgAGc3EAfgBVc3EAfgAGc3EAfgBYAABzcQB+AFp0AAFyc3EAfgA0c3EAfgAPdAAGVmVjdG9yc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4c3EAfgBNc3EAfgAGc3EAfgAGc3EAfgA6dAAGdmVjdG9ycQB+AAl4cQB+AAl4c3EAfgA3c3EAfgA6dAABcnQAA2RvdHEAfgAHcQB+AAl4cQB+AAl4c3EAfgA3c3EAfgA6dAAEcm93c3EAfgB7cQB+AAdxAH4ACXhxAH4ACXhzcQB+ADp0AAtEZW5zZVZlY3RvcnEAfgAHdAAGJHRpbWVzcQB+ACFxAH4AB3NxAH4ANHNxAH4AD3QABlZlY3RvcnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgAhcHQADkFic3RyYWN0TWF0cml4cQB+ACFzcQB+AAZzcQB+AKhxAH4AIXEAfgAHdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AK1zcQB+ADRzcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVEZWZ/xjXdrTcdnAIAA0wABG5hbWVxAH4ABEwAA3Joc3EAfgAdTAAHdHBlQXJnc3EAfgABeHB0AAZNYXRyaXhzcQB+AA90AA5BYnN0cmFjdE1hdHJpeHNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHNxAH4ABnNxAH4AqHEAfgAhcQB+AAd0AAFUcQB+AAdxAH4ACXhzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJbXBvcnRTdGF0rICgSsPGGyICAAFMAARuYW1lcQB+AAR4cHQAFnNjYWxhLnJlZmxlY3QuQ2xhc3NUYWdxAH4ACXhxAH4AB3QACE1hdHJpY2VzdAATcGFyYWRpc2UubGluYWxnZWJyYXNxAH4ANHNyACJzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1NlbGZUeXBlRGVmfvedTirOs+UCAAJMAApjb21wb25lbnRzcQB+AAFMAARuYW1lcQB+AAR4cHNxAH4ABnNxAH4AD3QADUxpbmVhckFsZ2VicmFxAH4AB3EAfgAJeHQABHNlbGZxAH4AIQ=="
//    }
//  }
//}