package paradise.linalgebra.matrices {
  object StagedEvaluation {
    import scalan._;
    import scala.reflect.runtime.universe._;
    import scala.reflect._;
    import scalan.common.Default;
    trait MatricesAbs extends Matrices with ScalanDsl { self: MatricesDsl =>
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
      class CompoundMatrixElem[T](val iso: Iso[CompoundMatrixData[T], CompoundMatrix[T]])(implicit eT: Elem[T]) extends AbstractMatrixElem[T, CompoundMatrix[T]] with ConcreteElem[CompoundMatrixData[T], CompoundMatrix[T]] {
        override def convertAbstractMatrix(x: Rep[AbstractMatrix[T]]) = CompoundMatrix(x.rows, x.numColumns);
        override def getDefaultRep = super[ConcreteElem].getDefaultRep;
        override lazy val tag = {
          implicit val tagT = eT.tag;
          weakTypeTag[CompoundMatrix[T]]
        }
      };
      type CompoundMatrixData[T] = scala.Tuple2[Collection[Vector[T]], Int];
      class CompoundMatrixIso[T](implicit eT: Elem[T]) extends Iso[CompoundMatrixData[T], CompoundMatrix[T]]()(pairElement(implicitly[Elem[Collection[Vector[T]]]], implicitly[Elem[Int]])) {
        override def from(p: Rep[CompoundMatrix[T]]) = scala.Tuple2(p.rows, p.numColumns);
        override def to(p: Rep[scala.Tuple2[Collection[Vector[T]], Int]]) = {
          val x$1 = (p: @scala.unchecked) match {
            case Pair((rows @ _), (numColumns @ _)) => scala.Tuple2(rows, numColumns)
          };
          val rows = x$1._1;
          val numColumns = x$1._2;
          CompoundMatrix(rows, numColumns)
        };
        lazy val defaultRepTo = Default.defaultVal[Rep[CompoundMatrix[T]]](CompoundMatrix(element[Collection[Vector[T]]].defaultRepValue, 0));
        lazy val eTo = new CompoundMatrixElem[T](this)
      };
      abstract class CompoundMatrixCompanionAbs extends CompanionBase[CompoundMatrixCompanionAbs] with CompoundMatrixCompanion {
        override def toString = "CompoundMatrix";
        def apply[T](p: Rep[CompoundMatrixData[T]])(implicit eT: Elem[T]): Rep[CompoundMatrix[T]] = isoCompoundMatrix(eT).to(p);
        def apply[T](rows: Rep[Collection[Vector[T]]], numColumns: Rep[Int])(implicit eT: Elem[T]): Rep[CompoundMatrix[T]] = mkCompoundMatrix(rows, numColumns)
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
      implicit class ExtendedCompoundMatrix[T](p: Rep[CompoundMatrix[T]])(implicit eT: Elem[T]) {
        def toData: Rep[CompoundMatrixData[T]] = isoCompoundMatrix(eT).from(p)
      };
      implicit def isoCompoundMatrix[T](implicit eT: Elem[T]): Iso[CompoundMatrixData[T], CompoundMatrix[T]] = new CompoundMatrixIso[T]();
      def mkCompoundMatrix[T](rows: Rep[Collection[Vector[T]]], numColumns: Rep[Int])(implicit eT: Elem[T]): Rep[CompoundMatrix[T]];
      def unmkCompoundMatrix[T](p: Rep[AbstractMatrix[T]]): Option[scala.Tuple2[Rep[Collection[Vector[T]]], Rep[Int]]]
    };
    trait MatricesSeq extends MatricesDsl with ScalanSeq { self: MatricesDslSeq =>
      lazy val AbstractMatrix: Rep[AbstractMatrixCompanionAbs] = {
        final class $anon extends AbstractMatrixCompanionAbs with UserTypeSeq[AbstractMatrixCompanionAbs] {
          lazy val selfType = element[AbstractMatrixCompanionAbs]
        };
        new $anon()
      };
      case class SeqCompoundMatrix[T](override val rows: Rep[Collection[Vector[T]]], override val numColumns: Rep[Int])(implicit eT: Elem[T]) extends CompoundMatrix[T](rows, numColumns) with UserTypeSeq[CompoundMatrix[T]] {
        lazy val selfType = element[CompoundMatrix[T]]
      };
      lazy val CompoundMatrix = {
        final class $anon extends CompoundMatrixCompanionAbs with UserTypeSeq[CompoundMatrixCompanionAbs] {
          lazy val selfType = element[CompoundMatrixCompanionAbs]
        };
        new $anon()
      };
      def mkCompoundMatrix[T](rows: Rep[Collection[Vector[T]]], numColumns: Rep[Int])(implicit eT: Elem[T]): Rep[CompoundMatrix[T]] = new SeqCompoundMatrix[T](rows, numColumns);
      def unmkCompoundMatrix[T](p: Rep[AbstractMatrix[T]]) = p match {
        case (p @ ((_): CompoundMatrix[T] @unchecked)) => Some(scala.Tuple2(p.rows, p.numColumns))
        case _ => None
      }
    };
    trait MatricesExp extends MatricesDsl with ScalanExp { self: MatricesDslExp =>
      lazy val AbstractMatrix: Rep[AbstractMatrixCompanionAbs] = {
        final class $anon extends AbstractMatrixCompanionAbs with UserTypeDef[AbstractMatrixCompanionAbs] {
          lazy val selfType = element[AbstractMatrixCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      case class ExpCompoundMatrix[T](override val rows: Rep[Collection[Vector[T]]], override val numColumns: Rep[Int])(implicit eT: Elem[T]) extends CompoundMatrix[T](rows, numColumns) with UserTypeDef[CompoundMatrix[T]] {
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
          })): Option[(scala.Tuple2[Rep[CompoundMatrix[T]], Rep[Numeric[T]]] forSome { 
            type T
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((n @ _), _*), _) if receiver.elem.isInstanceOf[(CompoundMatrixElem[_$6] forSome { 
  type _$6
})].&&(method.getName.==("columns")) => Some(scala.Tuple2(receiver, n)).asInstanceOf[Option[(scala.Tuple2[Rep[CompoundMatrix[T]], Rep[Numeric[T]]] forSome { 
              type T
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$7] forSome { 
            type _$7
          })): Option[(scala.Tuple2[Rep[CompoundMatrix[T]], Rep[Numeric[T]]] forSome { 
            type T
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object * {
          def unapply(d: (Def[_$8] forSome { 
            type _$8
          })): Option[(scala.Tuple3[Rep[CompoundMatrix[T]], Rep[Matrix[T]], Rep[Numeric[T]]] forSome { 
            type T
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((matrix @ _), (n @ _), _*), _) if receiver.elem.isInstanceOf[(CompoundMatrixElem[_$9] forSome { 
  type _$9
})].&&(method.getName.==("$times")) => Some(scala.Tuple3(receiver, matrix, n)).asInstanceOf[Option[(scala.Tuple3[Rep[CompoundMatrix[T]], Rep[Matrix[T]], Rep[Numeric[T]]] forSome { 
              type T
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$10] forSome { 
            type _$10
          })): Option[(scala.Tuple3[Rep[CompoundMatrix[T]], Rep[Matrix[T]], Rep[Numeric[T]]] forSome { 
            type T
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object CompoundMatrixCompanionMethods;
      def mkCompoundMatrix[T](rows: Rep[Collection[Vector[T]]], numColumns: Rep[Int])(implicit eT: Elem[T]): Rep[CompoundMatrix[T]] = new ExpCompoundMatrix[T](rows, numColumns);
      def unmkCompoundMatrix[T](p: Rep[AbstractMatrix[T]]) = p.elem.asInstanceOf[(Elem[_$11] forSome { 
        type _$11
      })] match {
        case ((_): CompoundMatrixElem[T] @unchecked) => Some(scala.Tuple2(p.asRep[CompoundMatrix[T]].rows, p.asRep[CompoundMatrix[T]].numColumns))
        case _ => None
      };
      object AbstractMatrixMethods {
        object numColumns {
          def unapply(d: (Def[_$12] forSome { 
            type _$12
          })): Option[(Rep[AbstractMatrix[T]] forSome { 
            type T
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$13, _$14] forSome { 
  type _$13;
  type _$14
})].&&(method.getName.==("numColumns")) => Some(receiver).asInstanceOf[Option[(Rep[AbstractMatrix[T]] forSome { 
              type T
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$15] forSome { 
            type _$15
          })): Option[(Rep[AbstractMatrix[T]] forSome { 
            type T
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object numRows {
          def unapply(d: (Def[_$16] forSome { 
            type _$16
          })): Option[(Rep[AbstractMatrix[T]] forSome { 
            type T
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$17, _$18] forSome { 
  type _$17;
  type _$18
})].&&(method.getName.==("numRows")) => Some(receiver).asInstanceOf[Option[(Rep[AbstractMatrix[T]] forSome { 
              type T
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$19] forSome { 
            type _$19
          })): Option[(Rep[AbstractMatrix[T]] forSome { 
            type T
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object rows {
          def unapply(d: (Def[_$20] forSome { 
            type _$20
          })): Option[(Rep[AbstractMatrix[T]] forSome { 
            type T
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$21, _$22] forSome { 
  type _$21;
  type _$22
})].&&(method.getName.==("rows")) => Some(receiver).asInstanceOf[Option[(Rep[AbstractMatrix[T]] forSome { 
              type T
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$23] forSome { 
            type _$23
          })): Option[(Rep[AbstractMatrix[T]] forSome { 
            type T
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object columns {
          def unapply(d: (Def[_$24] forSome { 
            type _$24
          })): Option[(scala.Tuple2[Rep[AbstractMatrix[T]], Rep[Numeric[T]]] forSome { 
            type T
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((n @ _), _*), _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$25, _$26] forSome { 
  type _$25;
  type _$26
})].&&(method.getName.==("columns")) => Some(scala.Tuple2(receiver, n)).asInstanceOf[Option[(scala.Tuple2[Rep[AbstractMatrix[T]], Rep[Numeric[T]]] forSome { 
              type T
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$27] forSome { 
            type _$27
          })): Option[(scala.Tuple2[Rep[AbstractMatrix[T]], Rep[Numeric[T]]] forSome { 
            type T
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object * {
          def unapply(d: (Def[_$28] forSome { 
            type _$28
          })): Option[(scala.Tuple3[Rep[AbstractMatrix[T]], Rep[Vector[T]], Rep[Numeric[T]]] forSome { 
            type T
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((vector @ _), (n @ _), _*), _) if receiver.elem.isInstanceOf[(AbstractMatrixElem[_$29, _$30] forSome { 
  type _$29;
  type _$30
})].&&(method.getName.==("$times")) => Some(scala.Tuple3(receiver, vector, n)).asInstanceOf[Option[(scala.Tuple3[Rep[AbstractMatrix[T]], Rep[Vector[T]], Rep[Numeric[T]]] forSome { 
              type T
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$31] forSome { 
            type _$31
          })): Option[(scala.Tuple3[Rep[AbstractMatrix[T]], Rep[Vector[T]], Rep[Numeric[T]]] forSome { 
            type T
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object AbstractMatrixCompanionMethods
    };
    trait Matrices extends Base { self: MatricesDsl =>
      trait AbstractMatrix[T] extends Reifiable[AbstractMatrix[T]] {
        implicit def eT: Elem[T];
        def numColumns: Rep[Int];
        def numRows: Rep[Int];
        def rows: Collection[Vector[T]];
        def columns(implicit n: Rep[Numeric[T]]): Collection[Vector[T]];
        def *(vector: Vector[T])(implicit n: Rep[Numeric[T]]): Vector[T] = DenseVector(rows.map(fun(((r: Vector[T]) => r.dot(vector)))))
      };
      abstract class CompoundMatrix[T](val rows: Collection[Vector[T]], val numColumns: Rep[Int])(implicit val eT: Elem[T]) extends AbstractMatrix[T] with Product with Serializable {
        def numRows = rows.length;
        def columns(implicit n: Rep[Numeric[T]]): Collection[Vector[T]] = Collection(toRep(0).to(numColumns).map(fun(((j: Rep[Int]) => ((DenseVector(rows.map(fun(((vec: Vector[T]) => vec(j)))))): Vector[T])))).toArray);
        def *(matrix: Matrix[T])(implicit n: Rep[Numeric[T]]): Matrix[T] = {
          val mT = CompoundMatrix(matrix.columns, matrix.numRows);
          CompoundMatrix(this.rows.map(fun(((row: Vector[T]) => mT.*(row)))), matrix.numColumns)
        }
      };
      trait AbstractMatrixCompanion;
      trait CompoundMatrixCompanion
    };
    trait MatricesDsl extends MatricesAbs;
    trait MatricesDslSeq extends MatricesSeq;
    trait MatricesDslExp extends MatricesExp;
    val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZrSr+/co886dAgALTAAEYm9keXQAIUxzY2FsYS9jb2xsZWN0aW9uL2ltbXV0YWJsZS9MaXN0O0wAEGNvbmNyZXRlU0NsYXNzZXNxAH4AAUwACGVudGl0aWVzcQB+AAFMAAllbnRpdHlPcHN0ACFMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcmFpdERlZjtMABBlbnRpdHlSZXBTeW5vbnltdAAOTHNjYWxhL09wdGlvbjtMAAdpbXBvcnRzcQB+AAFMAAdtZXRob2RzcQB+AAFMAARuYW1ldAASTGphdmEvbGFuZy9TdHJpbmc7TAALcGFja2FnZU5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAApzZXFEc2xJbXBscQB+AAN4cHNyADJzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0JFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABAwAAeHBzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzRGVmB9B9aPGocDMCAApaAAppc0Fic3RyYWN0TAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYXJnc3QAIkxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU0NsYXNzQXJncztMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAMTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0Q2FsbFDrGS0l7EVYAgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQADkFic3RyYWN0TWF0cml4c3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4c3EAfgAPdAAHUHJvZHVjdHEAfgAHc3EAfgAPdAAMU2VyaWFsaXphYmxlcQB+AAdxAH4ACXhxAH4AB3NyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzQXJncyfr7mTFMABcAgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZwVz2NYL1Ck/AgAIWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnWgAHdmFsRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGV0ACBMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcGVFeHByO3hwAAAAAXEAfgAHc3IAC3NjYWxhLk5vbmUkRlAk9lPKlKwCAAB4cgAMc2NhbGEuT3B0aW9u/mk3/dsOZnQCAAB4cHQABHJvd3NzcQB+AA90AApDb2xsZWN0aW9uc3EAfgAGc3EAfgAPdAAGVmVjdG9yc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4c3EAfgAcAAAAAXEAfgAHcQB+ACF0AApudW1Db2x1bW5zc3IAI3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlUHJpbWl0aXZlNcO7VfC+TgUCAAJMABJkZWZhdWx0VmFsdWVTdHJpbmdxAH4ABEwABG5hbWVxAH4ABHhwdAABMHQAA0ludHEAfgAJeHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZERlZt1ZcXG3MCsOAgAKWgAMaXNFbGVtT3JDb250WgAKaXNJbXBsaWNpdFoACmlzT3ZlcnJpZGVMAAthbm5vdGF0aW9uc3EAfgABTAALYXJnU2VjdGlvbnNxAH4AAUwABGJvZHlxAH4AA0wABG5hbWVxAH4ABEwACm92ZXJsb2FkSWRxAH4AA0wAB3RwZUFyZ3NxAH4AAUwABnRwZVJlc3EAfgADeHAAAABxAH4AB3EAfgAHc3IACnNjYWxhLlNvbWURIvJpXqGLdAIAAUwAAXh0ABJMamF2YS9sYW5nL09iamVjdDt4cQB+ACBzcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxlY3QL3WllZUD5hQIAAkwABGV4cHJ0AB1Mc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNFeHByO0wABXRuYW1lcQB+AAR4cHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0lkZW50JxKl+/AJ108CAAFMAARuYW1lcQB+AAR4cHQABHJvd3N0AAZsZW5ndGh0AAdudW1Sb3dzcQB+ACFxAH4AB3EAfgAhc3EAfgAyAAAAcQB+AAdzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2RBcmdzKcInll51uosCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZwPc/8uTcnMDAgAHWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXEAfgAdeHABAABxAH4AB3EAfgAhdAABbnNxAH4AD3QAB051bWVyaWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhzcQB+ADRzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNBcHBseYRmKZ/Smvx/AgADTAAFYXJnc3NxAH4AAUwAA2Z1bnEAfgA4TAACdHNxAH4AAXhwc3EAfgAGc3EAfgAGc3EAfgA3c3EAfgBNc3EAfgAGc3EAfgAGc3IAG3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTRnVuY7BM+JAaxTCDAgACTAAGcGFyYW1zcQB+AAFMAANyZXNxAH4AOHhwc3EAfgAGc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVmFsRGVmKwY5unvdRQ8CAAVaAAppc0ltcGxpY2l0WgAGaXNMYXp5TAAEZXhwcnEAfgA4TAAEbmFtZXEAfgAETAADdHBlcQB+AAN4cAAAc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTRW1wdHmRYBXhS+VovgIAAHhwdAABanNxAH4ANHEAfgAucQB+AAl4c3IAG3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQXNjcmDEeSeFkj7MAgACTAAEZXhwcnEAfgA4TAACcHRxAH4AHXhwc3EAfgBNc3EAfgAGc3EAfgAGc3EAfgBNc3EAfgAGc3EAfgAGc3EAfgBVc3EAfgAGc3EAfgBYAABzcQB+AFp0AAN2ZWNzcQB+ADRzcQB+AA90AAZWZWN0b3JzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+AE1zcQB+AAZzcQB+AAZzcQB+ADp0AAFqcQB+AAl4cQB+AAl4c3EAfgA6dAADdmVjcQB+AAdxAH4ACXhxAH4ACXhzcQB+ADdzcQB+ADp0AARyb3dzdAADbWFwcQB+AAdxAH4ACXhxAH4ACXhzcQB+ADp0AAtEZW5zZVZlY3RvcnEAfgAHc3EAfgAPdAAGVmVjdG9yc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4c3EAfgA3c3EAfgBNc3EAfgAGc3EAfgAGc3EAfgA6dAAKbnVtQ29sdW1uc3EAfgAJeHEAfgAJeHNxAH4AN3NyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NvbnN0Gk0U0WJLZ4oCAAFMAAFjcQB+ADV4cHNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAAAdAACdG9xAH4AB3EAfgB7cQB+AAd0AAd0b0FycmF5cQB+AAl4cQB+AAl4c3EAfgA6dAAKQ29sbGVjdGlvbnEAfgAHdAAHY29sdW1uc3EAfgAhcQB+AAdzcQB+ADRzcQB+AA90AApDb2xsZWN0aW9uc3EAfgAGc3EAfgAPdAAGVmVjdG9yc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4c3EAfgAyAAAAcQB+AAdzcQB+AAZzcQB+AEFzcQB+AAZzcQB+AEQAAABxAH4AB3EAfgAhdAAGbWF0cml4c3EAfgAPdAAGTWF0cml4c3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4c3EAfgBBc3EAfgAGc3EAfgBEAQAAcQB+AAdxAH4AIXQAAW5zcQB+AA90AAdOdW1lcmljc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4c3EAfgA0c3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQmxvY2tVLepmcRQ0/QIAAkwABGluaXRxAH4AAUwABGxhc3RxAH4AOHhwc3EAfgAGc3EAfgBYAABzcQB+AE1zcQB+AAZzcQB+AAZzcQB+ADdzcQB+ADp0AAZtYXRyaXh0AAdjb2x1bW5zc3EAfgA3c3EAfgA6dAAGbWF0cml4dAAHbnVtUm93c3EAfgAJeHEAfgAJeHNxAH4AOnQADkNvbXBvdW5kTWF0cml4cQB+AAd0AAJtVHEAfgAhcQB+AAl4c3EAfgBNc3EAfgAGc3EAfgAGc3EAfgBNc3EAfgAGc3EAfgAGc3EAfgBVc3EAfgAGc3EAfgBYAABzcQB+AFp0AANyb3dzcQB+ADRzcQB+AA90AAZWZWN0b3JzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+AE1zcQB+AAZzcQB+AAZzcQB+ADp0AANyb3dxAH4ACXhxAH4ACXhzcQB+ADdzcQB+ADp0AAJtVHQABiR0aW1lc3EAfgAHcQB+AAl4cQB+AAl4c3EAfgA3c3EAfgA3c3IAG3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVGhpc+oe0MagX4tnAgABTAAIdHlwZU5hbWVxAH4ABHhwdAAAdAAEcm93c3EAfgB7cQB+AAdzcQB+ADdzcQB+ADp0AAZtYXRyaXh0AApudW1Db2x1bW5zcQB+AAl4cQB+AAl4c3EAfgA6dAAOQ29tcG91bmRNYXRyaXhxAH4AB3EAfgDdcQB+ACFxAH4AB3NxAH4ANHNxAH4AD3QABk1hdHJpeHNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgAhc3EAfgAZcQB+AAd0AA5Db21wb3VuZE1hdHJpeHEAfgAhc3EAfgAGc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlQXJnklsmSHLxm4kCAARMAAVib3VuZHEAfgADTAAMY29udGV4dEJvdW5kcQB+AAFMAARuYW1lcQB+AARMAAd0cGFyYW1zcQB+AAF4cHEAfgAhcQB+AAd0AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdERlZgG+NJI0FLGyAgAJWgAIYml0bWFwJDBMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAMTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgAHcQB+AAdzcQB+AAZzcQB+ADIAAABxAH4AB3EAfgAHcQB+ACF0AApudW1Db2x1bW5zcQB+ACFxAH4AB3NxAH4ANHEAfgAuc3EAfgAyAAAAcQB+AAdxAH4AB3EAfgAhdAAHbnVtUm93c3EAfgAhcQB+AAdzcQB+ADRxAH4ALnNxAH4AMgAAAHEAfgAHcQB+AAdxAH4AIXQABHJvd3NxAH4AIXEAfgAHc3EAfgA0c3EAfgAPdAAKQ29sbGVjdGlvbnNxAH4ABnNxAH4AD3QABlZlY3RvcnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHNxAH4AMgAAAHEAfgAHc3EAfgAGc3EAfgBBc3EAfgAGc3EAfgBEAQAAcQB+AAdxAH4AIXQAAW5zcQB+AA90AAdOdW1lcmljc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4cQB+ACF0AAdjb2x1bW5zcQB+ACFxAH4AB3NxAH4ANHNxAH4AD3QACkNvbGxlY3Rpb25zcQB+AAZzcQB+AA90AAZWZWN0b3JzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+ADIAAABxAH4AB3NxAH4ABnNxAH4AQXNxAH4ABnNxAH4ARAAAAHEAfgAHcQB+ACF0AAZ2ZWN0b3JzcQB+AA90AAZWZWN0b3JzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+AEFzcQB+AAZzcQB+AEQBAABxAH4AB3EAfgAhdAABbnNxAH4AD3QAB051bWVyaWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhzcQB+ADRzcQB+AE1zcQB+AAZzcQB+AAZzcQB+AE1zcQB+AAZzcQB+AAZzcQB+AFVzcQB+AAZzcQB+AFgAAHNxAH4AWnQAAXJzcQB+ADRzcQB+AA90AAZWZWN0b3JzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+AE1zcQB+AAZzcQB+AAZzcQB+ADp0AAZ2ZWN0b3JxAH4ACXhxAH4ACXhzcQB+ADdzcQB+ADp0AAFydAADZG90cQB+AAdxAH4ACXhxAH4ACXhzcQB+ADdzcQB+ADp0AARyb3dzcQB+AHtxAH4AB3EAfgAJeHEAfgAJeHNxAH4AOnQAC0RlbnNlVmVjdG9ycQB+AAdxAH4A3XEAfgAhcQB+AAdzcQB+ADRzcQB+AA90AAZWZWN0b3JzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4AIXB0AA5BYnN0cmFjdE1hdHJpeHEAfgAhc3EAfgAGc3EAfgDzcQB+ACFxAH4AB3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgD4c3EAfgA0c3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlRGVmf8Y13a03HZwCAANMAARuYW1lcQB+AARMAANyaHNxAH4AHUwAB3RwZUFyZ3NxAH4AAXhwdAAGTWF0cml4c3EAfgAPdAAOQWJzdHJhY3RNYXRyaXhzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhzcQB+AAZzcQB+APNxAH4AIXEAfgAHdAABVHEAfgAHcQB+AAl4c3EAfgAGc3IAIXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTSW1wb3J0U3RhdKyAoErDxhsiAgABTAAEbmFtZXEAfgAEeHB0ACFwYXJhZGlzZS5saW5hbGdlYnJhLkxpbmVhckFsZ2VicmFxAH4ACXhxAH4AB3QACE1hdHJpY2VzdAAccGFyYWRpc2UubGluYWxnZWJyYS5tYXRyaWNlc3NxAH4ANHNyACJzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1NlbGZUeXBlRGVmfvedTirOs+UCAAJMAApjb21wb25lbnRzcQB+AAFMAARuYW1lcQB+AAR4cHNxAH4ABnNxAH4AD3QADUxpbmVhckFsZ2VicmFxAH4AB3EAfgAJeHQABHNlbGZxAH4AIQ=="
  }
}