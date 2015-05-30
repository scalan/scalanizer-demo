//package paradise.linalgebra {
//  package implOfVectors {
//    object StagedEvaluation {
//      import scalan._;
//      import paradise.implOfNumMonoids.StagedEvaluation._;
//      import paradise.collections.implOfCollections.StagedEvaluation._;
//      import paradise.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
//      import scala.reflect.runtime.universe._;
//      import scala.reflect._;
//      import scalan.common.Default;
//      trait VectorsAbs extends Vectors with ScalanDsl { self: LinearAlgebraDsl =>
//        implicit def proxyAbstractVector[T](p: Rep[AbstractVector[T]]): AbstractVector[T] = proxyOps[AbstractVector[T]](p)(classTag[AbstractVector[T]]);
//        class AbstractVectorElem[T, To <: AbstractVector[T]](implicit val eT: Elem[T]) extends EntityElem[To] {
//          override def isEntityType = true;
//          override lazy val tag = {
//            implicit val tagT = eT.tag;
//            weakTypeTag[AbstractVector[T]].asInstanceOf[WeakTypeTag[To]]
//          };
//          override def convert(x: Rep[(Reifiable[_$1] forSome {
//            type _$1
//          })]) = {
//            val conv = fun(((x: Rep[AbstractVector[T]]) => convertAbstractVector(x)));
//            tryConvert(element[AbstractVector[T]], this, x, conv)
//          };
//          def convertAbstractVector(x: Rep[AbstractVector[T]]): Rep[To] = {
//            assert(x.selfType1 match {
//              case ((_): AbstractVectorElem[(_), (_)]) => true
//              case _ => false
//            });
//            x.asRep[To]
//          };
//          override def getDefaultRep: Rep[To] = ???
//        };
//        implicit def abstractVectorElement[T](implicit eT: Elem[T]): Elem[AbstractVector[T]] = new AbstractVectorElem[T, AbstractVector[T]]();
//        implicit case object AbstractVectorCompanionElem extends CompanionElem[AbstractVectorCompanionAbs] with scala.Product with scala.Serializable {
//          lazy val tag = weakTypeTag[AbstractVectorCompanionAbs];
//          protected def getDefaultRep = AbstractVector
//        };
//        abstract class AbstractVectorCompanionAbs extends CompanionBase[AbstractVectorCompanionAbs] with AbstractVectorCompanion {
//          override def toString = "AbstractVector"
//        };
//        def AbstractVector: Rep[AbstractVectorCompanionAbs];
//        implicit def proxyAbstractVectorCompanion(p: Rep[AbstractVectorCompanion]): AbstractVectorCompanion = proxyOps[AbstractVectorCompanion](p);
//        class DenseVectorElem[T](val iso: Iso[DenseVectorData[T], DenseVector[T]])(implicit ctT: ClassTag[T], eT: Elem[T]) extends AbstractVectorElem[T, DenseVector[T]] with ConcreteElem[DenseVectorData[T], DenseVector[T]] {
//          override def convertAbstractVector(x: Rep[AbstractVector[T]]) = DenseVector(x.items);
//          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
//          override lazy val tag = {
//            implicit val tagT = eT.tag;
//            weakTypeTag[DenseVector[T]]
//          }
//        };
//        type DenseVectorData[T] = Collection[T];
//        class DenseVectorIso[T](implicit ctT: ClassTag[T], eT: Elem[T]) extends Iso[DenseVectorData[T], DenseVector[T]] {
//          override def from(p: Rep[DenseVector[T]]) = p.items;
//          override def to(p: Rep[Collection[T]]) = {
//            val items = p;
//            DenseVector(items)
//          };
//          lazy val defaultRepTo = Default.defaultVal[Rep[DenseVector[T]]](DenseVector(element[Collection[T]].defaultRepValue));
//          lazy val eTo = new DenseVectorElem[T](this)
//        };
//        abstract class DenseVectorCompanionAbs extends CompanionBase[DenseVectorCompanionAbs] with DenseVectorCompanion {
//          override def toString = "DenseVector";
//          def apply[T](items: Rep[Collection[T]])(implicit ctT: ClassTag[T], eT: Elem[T]): Rep[DenseVector[T]] = mkDenseVector(items)
//        };
//        object DenseVectorMatcher {
//          def unapply[T](p: Rep[AbstractVector[T]]) = unmkDenseVector(p)
//        };
//        def DenseVector: Rep[DenseVectorCompanionAbs];
//        implicit def proxyDenseVectorCompanion(p: Rep[DenseVectorCompanionAbs]): DenseVectorCompanionAbs = proxyOps[DenseVectorCompanionAbs](p);
//        implicit case object DenseVectorCompanionElem extends CompanionElem[DenseVectorCompanionAbs] with scala.Product with scala.Serializable {
//          lazy val tag = weakTypeTag[DenseVectorCompanionAbs];
//          protected def getDefaultRep = DenseVector
//        };
//        implicit def proxyDenseVector[T](p: Rep[DenseVector[T]]): DenseVector[T] = proxyOps[DenseVector[T]](p);
//        implicit class ExtendedDenseVector[T](p: Rep[DenseVector[T]])(implicit ctT: ClassTag[T], eT: Elem[T]) {
//          def toData: Rep[DenseVectorData[T]] = isoDenseVector(ctT, eT).from(p)
//        };
//        implicit def isoDenseVector[T](implicit ctT: ClassTag[T], eT: Elem[T]): Iso[DenseVectorData[T], DenseVector[T]] = new DenseVectorIso[T]();
//        def mkDenseVector[T](items: Rep[Collection[T]])(implicit ctT: ClassTag[T], eT: Elem[T]): Rep[DenseVector[T]];
//        def unmkDenseVector[T](p: Rep[AbstractVector[T]]): Option[Rep[Collection[T]]]
//      };
//      trait VectorsSeq extends VectorsDsl with ScalanSeq { self: LinearAlgebraDslSeq =>
//        lazy val AbstractVector: Rep[AbstractVectorCompanionAbs] = {
//          final class $anon extends AbstractVectorCompanionAbs with UserTypeSeq[AbstractVectorCompanionAbs] {
//            lazy val selfType = element[AbstractVectorCompanionAbs]
//          };
//          new $anon()
//        };
//        case class SeqDenseVector[T](override val items: Rep[Collection[T]])(implicit ctT: ClassTag[T], eT: Elem[T]) extends DenseVector[T](items) with UserTypeSeq[DenseVector[T]] {
//          lazy val selfType = element[DenseVector[T]]
//        };
//        lazy val DenseVector = {
//          final class $anon extends DenseVectorCompanionAbs with UserTypeSeq[DenseVectorCompanionAbs] {
//            lazy val selfType = element[DenseVectorCompanionAbs]
//          };
//          new $anon()
//        };
//        def mkDenseVector[T](items: Rep[Collection[T]])(implicit ctT: ClassTag[T], eT: Elem[T]): Rep[DenseVector[T]] = new SeqDenseVector[T](items);
//        def unmkDenseVector[T](p: Rep[AbstractVector[T]]) = p match {
//          case (p @ ((_): DenseVector[T] @unchecked)) => Some(p.items)
//          case _ => None
//        }
//      };
//      trait VectorsExp extends VectorsDsl with ScalanExp { self: LinearAlgebraDslExp =>
//        lazy val AbstractVector: Rep[AbstractVectorCompanionAbs] = {
//          final class $anon extends AbstractVectorCompanionAbs with UserTypeDef[AbstractVectorCompanionAbs] {
//            lazy val selfType = element[AbstractVectorCompanionAbs];
//            override def mirror(t: Transformer) = this
//          };
//          new $anon()
//        };
//        case class ExpDenseVector[T](override val items: Rep[Collection[T]])(implicit ctT: ClassTag[T], eT: Elem[T]) extends DenseVector[T](items) with UserTypeDef[DenseVector[T]] {
//          lazy val selfType = element[DenseVector[T]];
//          override def mirror(t: Transformer) = ExpDenseVector[T](t(items))
//        };
//        lazy val DenseVector: Rep[DenseVectorCompanionAbs] = {
//          final class $anon extends DenseVectorCompanionAbs with UserTypeDef[DenseVectorCompanionAbs] {
//            lazy val selfType = element[DenseVectorCompanionAbs];
//            override def mirror(t: Transformer) = this
//          };
//          new $anon()
//        };
//        object DenseVectorMethods {
//          object length {
//            def unapply(d: (Def[_$2] forSome {
//              type _$2
//            })): Option[(Rep[DenseVector[T]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(DenseVectorElem[_$3] forSome {
//  type _$3
//})].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[(Rep[DenseVector[T]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$4] forSome {
//              type _$4
//            })): Option[(Rep[DenseVector[T]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object apply {
//            def unapply(d: (Def[_$5] forSome {
//              type _$5
//            })): Option[(scala.Tuple2[Rep[DenseVector[T]], Rep[Int]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(DenseVectorElem[_$6] forSome {
//  type _$6
//})].&&(method.getName.==("apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[DenseVector[T]], Rep[Int]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$7] forSome {
//              type _$7
//            })): Option[(scala.Tuple2[Rep[DenseVector[T]], Rep[Int]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object dot {
//            def unapply(d: (Def[_$8] forSome {
//              type _$8
//            })): Option[(scala.Tuple4[Rep[DenseVector[T]], Rep[Vector[T]], Rep[Numeric[T]], Rep[NumMonoid[T]]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((other @ _), (n @ _), (m @ _), _*), _) if receiver.elem.isInstanceOf[(DenseVectorElem[_$9] forSome {
//  type _$9
//})].&&(method.getName.==("dot")) => Some(scala.Tuple4(receiver, other, n, m)).asInstanceOf[Option[(scala.Tuple4[Rep[DenseVector[T]], Rep[Vector[T]], Rep[Numeric[T]], Rep[NumMonoid[T]]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$10] forSome {
//              type _$10
//            })): Option[(scala.Tuple4[Rep[DenseVector[T]], Rep[Vector[T]], Rep[Numeric[T]], Rep[NumMonoid[T]]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          }
//        };
//        object DenseVectorCompanionMethods;
//        def mkDenseVector[T](items: Rep[Collection[T]])(implicit ctT: ClassTag[T], eT: Elem[T]): Rep[DenseVector[T]] = new ExpDenseVector[T](items);
//        def unmkDenseVector[T](p: Rep[AbstractVector[T]]) = p.elem.asInstanceOf[(Elem[_$11] forSome {
//          type _$11
//        })] match {
//          case ((_): DenseVectorElem[T] @unchecked) => Some(p.asRep[DenseVector[T]].items)
//          case _ => None
//        };
//        object AbstractVectorMethods {
//          object length {
//            def unapply(d: (Def[_$12] forSome {
//              type _$12
//            })): Option[(Rep[AbstractVector[T]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(AbstractVectorElem[_$13, _$14] forSome {
//  type _$13;
//  type _$14
//})].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[(Rep[AbstractVector[T]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$15] forSome {
//              type _$15
//            })): Option[(Rep[AbstractVector[T]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object items {
//            def unapply(d: (Def[_$16] forSome {
//              type _$16
//            })): Option[(Rep[AbstractVector[T]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(AbstractVectorElem[_$17, _$18] forSome {
//  type _$17;
//  type _$18
//})].&&(method.getName.==("items")) => Some(receiver).asInstanceOf[Option[(Rep[AbstractVector[T]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$19] forSome {
//              type _$19
//            })): Option[(Rep[AbstractVector[T]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object apply {
//            def unapply(d: (Def[_$20] forSome {
//              type _$20
//            })): Option[(scala.Tuple2[Rep[AbstractVector[T]], Rep[Int]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(AbstractVectorElem[_$21, _$22] forSome {
//  type _$21;
//  type _$22
//})].&&(method.getName.==("apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[AbstractVector[T]], Rep[Int]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$23] forSome {
//              type _$23
//            })): Option[(scala.Tuple2[Rep[AbstractVector[T]], Rep[Int]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object dot {
//            def unapply(d: (Def[_$24] forSome {
//              type _$24
//            })): Option[(scala.Tuple4[Rep[AbstractVector[T]], Rep[Vector[T]], Rep[Numeric[T]], Rep[NumMonoid[T]]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((other @ _), (n @ _), (m @ _), _*), _) if receiver.elem.isInstanceOf[(AbstractVectorElem[_$25, _$26] forSome {
//  type _$25;
//  type _$26
//})].&&(method.getName.==("dot")) => Some(scala.Tuple4(receiver, other, n, m)).asInstanceOf[Option[(scala.Tuple4[Rep[AbstractVector[T]], Rep[Vector[T]], Rep[Numeric[T]], Rep[NumMonoid[T]]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$27] forSome {
//              type _$27
//            })): Option[(scala.Tuple4[Rep[AbstractVector[T]], Rep[Vector[T]], Rep[Numeric[T]], Rep[NumMonoid[T]]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          }
//        };
//        object AbstractVectorCompanionMethods
//      };
//      trait Vectors extends Base { self: LinearAlgebraDsl =>
//        trait AbstractVector[T] extends Reifiable[AbstractVector[T]] {
//          implicit def eT: Elem[T];
//          def length: Rep[Int];
//          def items: Rep[Collection[T]];
//          def apply(i: Rep[Int]): Rep[T];
//          def map[R](f: Rep[scala.Function1[T, R]])(implicit eR: Elem[R]): Rep[Vector[R]];
//          def dot(other: Rep[Vector[T]])(implicit n: Rep[Numeric[T]], m: Rep[NumMonoid[T]]): Rep[T]
//        };
//        abstract class DenseVector[T](val items: Rep[Collection[T]])(implicit val ctT: Rep[ClassTag[T]], val eT: Elem[T]) extends AbstractVector[T] with Product with Serializable {
//          def length = items.length;
//          def apply(i: Rep[Int]): Rep[T] = items(i);
//          def map[R](f: Rep[scala.Function1[T, R]])(implicit eR: Elem[R]): Rep[Vector[R]] = DenseVector(items.map(f));
//          def dot(other: Rep[Vector[T]])(implicit n: Rep[Numeric[T]], m: Rep[NumMonoid[T]]): Rep[T] = other.items.zip(items).map(fun(((v: Rep[scala.Tuple2[T, T]]) => n.times(v._1, v._2)))).reduce
//        };
//        trait AbstractVectorCompanion;
//        trait DenseVectorCompanion
//      };
//      trait VectorsDsl extends VectorsAbs { self: LinearAlgebraDsl =>
//
//      };
//      trait VectorsDslSeq extends VectorsSeq { self: LinearAlgebraDslSeq =>
//
//      };
//      trait VectorsDslExp extends VectorsExp { self: LinearAlgebraDslExp =>
//
//      };
//      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AA5BYnN0cmFjdFZlY3RvcnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHNxAH4AD3QAB1Byb2R1Y3RxAH4AB3NxAH4AD3QADFNlcmlhbGl6YWJsZXEAfgAHcQB+AAl4cQB+AAdzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZ3Mn6+5kxTAAXAIAAUwABGFyZ3NxAH4AAXhwc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NBcmcFc9jWC9QpPwIACFoAB2ltcEZsYWdaAAxpc0VsZW1PckNvbnRaAAhvdmVyRmxhZ1oAB3ZhbEZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBldAAgTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTVHBlRXhwcjt4cAAAAAFxAH4AB3NyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHIADHNjYWxhLk9wdGlvbv5pN/3bDmZ0AgAAeHB0AAVpdGVtc3NxAH4AD3QACkNvbGxlY3Rpb25zcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2REZWbdWXFxtzArDgIACloADGlzRWxlbU9yQ29udFoACmlzSW1wbGljaXRaAAppc092ZXJyaWRlTAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0cGVSZXNxAH4AA3hwAAAAcQB+AAdxAH4AB3NyAApzY2FsYS5Tb21lESLyaV6hi3QCAAFMAAF4dAASTGphdmEvbGFuZy9PYmplY3Q7eHEAfgAgc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTU2VsZWN0C91pZWVA+YUCAAJMAARleHBydAAdTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTRXhwcjtMAAV0bmFtZXEAfgAEeHBzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJZGVudCcSpfvwCddPAgABTAAEbmFtZXEAfgAEeHB0AAVpdGVtc3QABmxlbmd0aHEAfgA0cQB+ACFxAH4AB3EAfgAhc3EAfgApAAAAcQB+AAdzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNN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AH4ACXhxAH4ACXhzcQB+AC5zcQB+AC5zcQB+ADF0AAVvdGhlcnQABWl0ZW1zdAADemlwcQB+AAdxAH4AZXEAfgAHdAAGcmVkdWNldAADZG90cQB+ACFxAH4AB3NxAH4AK3NxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAhc3EAfgAZc3EAfgAGc3EAfgAcAQAAAXEAfgAHcQB+ACF0AANjdFRzcQB+AA90AAhDbGFzc1RhZ3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHQAC0RlbnNlVmVjdG9ycQB+ACFzcQB+AAZzcQB+AGlxAH4AIXEAfgAHdAABVHEAfgAHcQB+AAl4cQB+AAl4c3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHJhaXREZWYBvjSSNBSxsgIACVoACGJpdG1hcCQwTAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABxAH4AB3EAfgAHc3EAfgAGc3EAfgApAAAAcQB+AAdxAH4AB3EAfgAhcQB+ADRxAH4AIXEAfgAHc3EAfgArcQB+AD5zcQB+ACkAAABxAH4AB3EAfgAHcQB+ACF0AAVpdGVtc3EAfgAhcQB+AAdzcQB+ACtzcQB+AA90AApDb2xsZWN0aW9uc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4c3EAfgApAAAAcQB+AAdzcQB+AAZzcQB+ADdzcQB+AAZzcQB+ADoAAABxAH4AB3EAfgAhdAABaXEAfgA+cQB+AAl4cQB+AAl4cQB+ACFxAH4ASnEAfgAhcQB+AAdzcQB+ACtzcQB+AA90AAFUcQB+AAdzcQB+ACkAAABxAH4AB3NxAH4ABnNxAH4AN3NxAH4ABnNxAH4AOgAAAHEAfgAHcQB+ACFxAH4AU3NxAH4AVHNxAH4AD3QAAVRxAH4AB3NxAH4AD3QAAVJxAH4AB3EAfgAJeHEAfgAJeHEAfgAhcQB+AGVxAH4AIXNxAH4ABnNxAH4AaXEAfgAhc3EAfgAGdAAIQ2xhc3NUYWdxAH4ACXh0AAFScQB+AAdxAH4ACXhzcQB+ACtzcQB+AA90AAZWZWN0b3JzcQB+AAZzcQB+AA90AAFScQB+AAdxAH4ACXhzcQB+ACkAAABxAH4AB3NxAH4ABnNxAH4AN3NxAH4ABnNxAH4AOgAAAHEAfgAHcQB+ACF0AAVvdGhlcnNxAH4AD3QABlZlY3RvcnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHNxAH4AN3NxAH4ABnNxAH4AOgEAAHEAfgAHcQB+ACF0AAFuc3EAfgAPdAAHTnVtZXJpY3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHNxAH4AOgEAAHEAfgAHcQB+ACF0AAFtc3EAfgAPdAAJTnVtTW9ub2lkc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4cQB+ACF0AANkb3RxAH4AIXEAfgAHc3EAfgArc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+ACFwdAAOQWJzdHJhY3RWZWN0b3JxAH4AIXNxAH4ABnNxAH4AaXEAfgAhcQB+AAd0AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4A03NxAH4AK3NyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZURlZn/GNd2tNx2cAgADTAAEbmFtZXEAfgAETAADcmhzcQB+AB1MAAd0cGVBcmdzcQB+AAF4cHQABlZlY3RvcnNxAH4AD3QADkFic3RyYWN0VmVjdG9yc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4c3EAfgAGc3EAfgBpcQB+ACFxAH4AB3QAAVRxAH4AB3EAfgAJeHNxAH4ABnNyACFzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0ltcG9ydFN0YXSsgKBKw8YbIgIAAUwABG5hbWVxAH4ABHhwdAAWc2NhbGEucmVmbGVjdC5DbGFzc1RhZ3EAfgAJeHEAfgAHdAAHVmVjdG9yc3QAE3BhcmFkaXNlLmxpbmFsZ2VicmFzcQB+ACtzcgAic2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxmVHlwZURlZn73nU4qzrPlAgACTAAKY29tcG9uZW50c3EAfgABTAAEbmFtZXEAfgAEeHBzcQB+AAZzcQB+AA90AA1MaW5lYXJBbGdlYnJhcQB+AAdxAH4ACXh0AARzZWxmcQB+ACE="
//    }
//  }
//}