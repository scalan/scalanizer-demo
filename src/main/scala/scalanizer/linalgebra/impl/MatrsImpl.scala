//package scalanizer.linalgebra {
//  package implOfMatrs {
//    object StagedEvaluation {
//      import scalan._;
//      import scalanizer.implOfNumMonoids.StagedEvaluation._;
//      import scalanizer.collections.implOfCols.StagedEvaluation._;
//      import scalanizer.linalgebra.implOfVecs.StagedEvaluation._;
//      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
//      import scala.reflect.runtime.universe._;
//      import scala.reflect._;
//      import scalan.common.Default;
//      trait MatrsAbs extends Matrs with ScalanDsl { self: LinearAlgebraDsl =>
//        implicit def proxyMatr[T](p: Rep[Matr[T]]): Matr[T] = proxyOps[Matr[T]](p)(scala.reflect.classTag[Matr[T]]);
//        class MatrElem[T, To <: Matr[T]](implicit val eeT: Elem[T]) extends EntityElem[To] {
//          override def isEntityType = true;
//          override lazy val tag = {
//            implicit val tagT = eeT.tag;
//            weakTypeTag[Matr[T]].asInstanceOf[WeakTypeTag[To]]
//          };
//          override def convert(x: Rep[(Reifiable[_$1] forSome {
//            type _$1
//          })]) = {
//            implicit val eTo: Elem[To] = this;
//            val conv = fun(((x: Rep[Matr[T]]) => convertMatr(x)));
//            tryConvert(element[Matr[T]], this, x, conv)
//          };
//          def convertMatr(x: Rep[Matr[T]]): Rep[To] = {
//            assert(x.selfType1 match {
//              case ((_): MatrElem[(_), (_)]) => true
//              case _ => false
//            });
//            x.asRep[To]
//          };
//          override def getDefaultRep: Rep[To] = ???
//        };
//        implicit def matrElement[T](implicit eeT: Elem[T]): Elem[Matr[T]] = new MatrElem[T, Matr[T]]();
//        implicit case object MatrCompanionElem extends CompanionElem[MatrCompanionAbs] with scala.Product with scala.Serializable {
//          lazy val tag = weakTypeTag[MatrCompanionAbs];
//          protected def getDefaultRep = Matr
//        };
//        abstract class MatrCompanionAbs extends CompanionBase[MatrCompanionAbs] with MatrCompanion {
//          override def toString = "Matr"
//        };
//        def Matr: Rep[MatrCompanionAbs];
//        implicit def proxyMatrCompanion(p: Rep[MatrCompanion]): MatrCompanion = proxyOps[MatrCompanion](p);
//        class DenseMatrElem[T](val iso: Iso[DenseMatrData[T], DenseMatr[T]])(implicit eeT: Elem[T]) extends MatrElem[T, DenseMatr[T]] with ConcreteElem[DenseMatrData[T], DenseMatr[T]] {
//          override def convertMatr(x: Rep[Matr[T]]) = DenseMatr(x.rows, x.numColumns);
//          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
//          override lazy val tag = {
//            implicit val tagT = eeT.tag;
//            weakTypeTag[DenseMatr[T]]
//          }
//        };
//        type DenseMatrData[T] = scala.Tuple2[Col[Vec[T]], Int];
//        class DenseMatrIso[T](implicit eeT: Elem[T]) extends Iso[DenseMatrData[T], DenseMatr[T]]()(pairElement(implicitly[Elem[Col[Vec[T]]]], implicitly[Elem[Int]])) {
//          override def from(p: Rep[DenseMatr[T]]) = scala.Tuple2(p.rows, p.numColumns);
//          override def to(p: Rep[scala.Tuple2[Col[Vec[T]], Int]]) = {
//            val x$1 = (p: @scala.unchecked) match {
//              case Pair((rows @ _), (numColumns @ _)) => scala.Tuple2(rows, numColumns)
//            };
//            val rows = x$1._1;
//            val numColumns = x$1._2;
//            DenseMatr(rows, numColumns)
//          };
//          lazy val defaultRepTo: Rep[DenseMatr[T]] = DenseMatr(element[Col[Vec[T]]].defaultRepValue, 0);
//          lazy val eTo = new DenseMatrElem[T](this)
//        };
//        abstract class DenseMatrCompanionAbs extends CompanionBase[DenseMatrCompanionAbs] with DenseMatrCompanion {
//          override def toString = "DenseMatr";
//          def apply[T](p: Rep[DenseMatrData[T]])(implicit eeT: Elem[T]): Rep[DenseMatr[T]] = isoDenseMatr(eeT).to(p);
//          def apply[T](rows: Rep[Col[Vec[T]]], numColumns: Rep[Int])(implicit eeT: Elem[T]): Rep[DenseMatr[T]] = mkDenseMatr(rows, numColumns)
//        };
//        object DenseMatrMatcher {
//          def unapply[T](p: Rep[Matr[T]]) = unmkDenseMatr(p)
//        };
//        def DenseMatr: Rep[DenseMatrCompanionAbs];
//        implicit def proxyDenseMatrCompanion(p: Rep[DenseMatrCompanionAbs]): DenseMatrCompanionAbs = proxyOps[DenseMatrCompanionAbs](p);
//        implicit case object DenseMatrCompanionElem extends CompanionElem[DenseMatrCompanionAbs] with scala.Product with scala.Serializable {
//          lazy val tag = weakTypeTag[DenseMatrCompanionAbs];
//          protected def getDefaultRep = DenseMatr
//        };
//        implicit def proxyDenseMatr[T](p: Rep[DenseMatr[T]]): DenseMatr[T] = proxyOps[DenseMatr[T]](p);
//        implicit class ExtendedDenseMatr[T](p: Rep[DenseMatr[T]])(implicit eeT: Elem[T]) {
//          def toData: Rep[DenseMatrData[T]] = isoDenseMatr(eeT).from(p)
//        };
//        implicit def isoDenseMatr[T](implicit eeT: Elem[T]): Iso[DenseMatrData[T], DenseMatr[T]] = new DenseMatrIso[T]();
//        def mkDenseMatr[T](rows: Rep[Col[Vec[T]]], numColumns: Rep[Int])(implicit eeT: Elem[T]): Rep[DenseMatr[T]];
//        def unmkDenseMatr[T](p: Rep[Matr[T]]): Option[scala.Tuple2[Rep[Col[Vec[T]]], Rep[Int]]]
//      };
//      trait MatrsExp extends MatrsDsl with ScalanExp { self: LinearAlgebraDslExp =>
//        lazy val Matr: Rep[MatrCompanionAbs] = {
//          final class $anon extends MatrCompanionAbs with UserTypeDef[MatrCompanionAbs] {
//            lazy val selfType = element[MatrCompanionAbs];
//            override def mirror(t: Transformer) = this
//          };
//          new $anon()
//        };
//        case class ExpDenseMatr[T](override val rows: Rep[Col[Vec[T]]], override val numColumns: Rep[Int])(implicit eeT: Elem[T]) extends DenseMatr[T](rows, numColumns) with UserTypeDef[DenseMatr[T]] {
//          lazy val selfType = element[DenseMatr[T]];
//          override def mirror(t: Transformer) = ExpDenseMatr[T](t(rows), t(numColumns))
//        };
//        lazy val DenseMatr: Rep[DenseMatrCompanionAbs] = {
//          final class $anon extends DenseMatrCompanionAbs with UserTypeDef[DenseMatrCompanionAbs] {
//            lazy val selfType = element[DenseMatrCompanionAbs];
//            override def mirror(t: Transformer) = this
//          };
//          new $anon()
//        };
//        object DenseMatrMethods {
//          object numRows {
//            def unapply(d: (Def[_$2] forSome {
//              type _$2
//            })): Option[(Rep[DenseMatr[T]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(DenseMatrElem[_$3] forSome {
//  type _$3
//})].&&(__equal(method.getName, "numRows")) => Some(receiver).asInstanceOf[Option[(Rep[DenseMatr[T]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$4] forSome {
//              type _$4
//            })): Option[(Rep[DenseMatr[T]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object columns {
//            def unapply(d: (Def[_$5] forSome {
//              type _$5
//            })): Option[(scala.Tuple2[Rep[DenseMatr[T]], Rep[Num[T]]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((n @ _), _*), _) if receiver.elem.isInstanceOf[(DenseMatrElem[_$6] forSome {
//  type _$6
//})].&&(__equal(method.getName, "columns")) => Some(scala.Tuple2(receiver, n)).asInstanceOf[Option[(scala.Tuple2[Rep[DenseMatr[T]], Rep[Num[T]]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$7] forSome {
//              type _$7
//            })): Option[(scala.Tuple2[Rep[DenseMatr[T]], Rep[Num[T]]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          }
//        };
//        object DenseMatrCompanionMethods;
//        def mkDenseMatr[T](rows: Rep[Col[Vec[T]]], numColumns: Rep[Int])(implicit eeT: Elem[T]): Rep[DenseMatr[T]] = new ExpDenseMatr[T](rows, numColumns);
//        def unmkDenseMatr[T](p: Rep[Matr[T]]) = p.elem.asInstanceOf[(Elem[_$8] forSome {
//          type _$8
//        })] match {
//          case ((_): DenseMatrElem[T] @unchecked) => Some(scala.Tuple2(p.asRep[DenseMatr[T]].rows, p.asRep[DenseMatr[T]].numColumns))
//          case _ => None
//        };
//        object MatrMethods {
//          object numColumns {
//            def unapply(d: (Def[_$9] forSome {
//              type _$9
//            })): Option[(Rep[Matr[T]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(MatrElem[_$10, _$11] forSome {
//  type _$10;
//  type _$11
//})].&&(__equal(method.getName, "numColumns")) => Some(receiver).asInstanceOf[Option[(Rep[Matr[T]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$12] forSome {
//              type _$12
//            })): Option[(Rep[Matr[T]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object numRows {
//            def unapply(d: (Def[_$13] forSome {
//              type _$13
//            })): Option[(Rep[Matr[T]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(MatrElem[_$14, _$15] forSome {
//  type _$14;
//  type _$15
//})].&&(__equal(method.getName, "numRows")) => Some(receiver).asInstanceOf[Option[(Rep[Matr[T]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$16] forSome {
//              type _$16
//            })): Option[(Rep[Matr[T]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object rows {
//            def unapply(d: (Def[_$17] forSome {
//              type _$17
//            })): Option[(Rep[Matr[T]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(MatrElem[_$18, _$19] forSome {
//  type _$18;
//  type _$19
//})].&&(__equal(method.getName, "rows")) => Some(receiver).asInstanceOf[Option[(Rep[Matr[T]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$20] forSome {
//              type _$20
//            })): Option[(Rep[Matr[T]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object columns {
//            def unapply(d: (Def[_$21] forSome {
//              type _$21
//            })): Option[(scala.Tuple2[Rep[Matr[T]], Rep[Num[T]]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((n @ _), _*), _) if receiver.elem.isInstanceOf[(MatrElem[_$22, _$23] forSome {
//  type _$22;
//  type _$23
//})].&&(__equal(method.getName, "columns")) => Some(scala.Tuple2(receiver, n)).asInstanceOf[Option[(scala.Tuple2[Rep[Matr[T]], Rep[Num[T]]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$24] forSome {
//              type _$24
//            })): Option[(scala.Tuple2[Rep[Matr[T]], Rep[Num[T]]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          }
//        };
//        object MatrCompanionMethods
//      };
//      trait Matrs extends Base { self: LinearAlgebraDsl =>
//        trait Matr[T] extends Reifiable[Matr[T]] {
//          implicit def eeT: Elem[T];
//          def numColumns: Rep[Int];
//          def numRows: Rep[Int];
//          def rows: Rep[Col[Vec[T]]];
//          def columns(implicit n: Rep[Num[T]]): Rep[Col[Vec[T]]]
//        };
//        abstract class DenseMatr[T](val rows: Rep[Col[Vec[T]]], val numColumns: Rep[Int])(implicit val eeT: Elem[T]) extends Matr[T] {
//          def numRows: Rep[Int] = DenseMatr.this.rows.length;
//          def columns(implicit n: Rep[Num[T]]): Rep[Col[Vec[T]]] = Matrs.this.Col.apply[Vec[T]](scala.this.Predef.intArrayOps(((scala.Array.range(toRep(0), DenseMatr.this.numColumns, toRep(1))): Rep[Array[Int]])).map[Vec[T], Array[Vec[T]]](fun(((j: Rep[Int]) => ((DenseVec(DenseMatr.this.rows.map[T](fun(((vec: Rep[Vec[T]]) => vec.apply(j))))(DenseMatr.this.ctT))(DenseMatr.this.ctT)): Rep[Vec[T]]))))(scala.this.Array.canBuildFrom[Vec[T]](((ClassTag.apply[Vec[T]](toRep(classOf[scalanizer.linalgebra.Vecs$Vec]))): Rep[ClassTag[Vec[T]]]))))(((ClassTag.apply[Vec[T]](toRep(classOf[scalanizer.linalgebra.Vecs$Vec]))): Rep[ClassTag[Vec[T]]]))
//        };
//        trait MatrCompanion;
//        trait DenseMatrCompanion
//      };
//      trait MatrsDsl extends MatrsAbs { self: LinearAlgebraDsl =>
//
//      };
//      trait MatrsDslExp extends MatrsExp { self: LinearAlgebraDslExp =>
//
//      };
//      val serializedMetaAst = ""
//    }
//
//    object HotSpotKernels {
//      import java.io.File;
//      import scalan.compilation.GraphVizConfig
//    }
//
//    object HotSpotManager {
//      import scalan.ScalanCommunityDslExp;
//      import scalan.compilation.lms.{CommunityLmsBackend, CoreBridge};
//      import scalan.compilation.lms.scalac.CommunityLmsCompilerScala;
//      import scalan.primitives.EffectfulCompiler;
//      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
//      lazy val scalanContext = new Scalan();
//      def getScalanContext = scalanContext;
//      class Scalan extends LinearAlgebraDslExp with CommunityLmsCompilerScala with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler {
//        val lms = new CommunityLmsBackend()
//      };
//      import scalan.CommunityMethodMappingDSL;
//      import scalan.compilation.lms.uni.LmsCompilerUni;
//      lazy val scalanContextUni = new ScalanUni();
//      def getScalanContextUni = scalanContextUni;
//      class ScalanUni extends LinearAlgebraDslExp with LmsCompilerUni with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler with CommunityMethodMappingDSL
//    }
//  }
//}