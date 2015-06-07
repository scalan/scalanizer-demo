package paradise.linalgebra {
  package implOfMatrs {
    object StagedEvaluation {
      import scalan._;
      import paradise.implOfNumMonoids.StagedEvaluation._;
      import paradise.collections.implOfCols.StagedEvaluation._;
      import paradise.linalgebra.implOfVecs.StagedEvaluation._;
      import paradise.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait MatrsAbs extends Matrs with ScalanDsl { self: LinearAlgebraDsl =>
        implicit def proxyMatr[T](p: Rep[Matr[T]]): Matr[T] = proxyOps[Matr[T]](p)(classTag[Matr[T]]);
        class MatrElem[T, To <: Matr[T]](implicit val eeT: Elem[T]) extends EntityElem[To] {
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagT = eeT.tag;
            weakTypeTag[Matr[T]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$1] forSome { 
            type _$1
          })]) = {
            val conv = fun(((x: Rep[Matr[T]]) => convertMatr(x)));
            tryConvert(element[Matr[T]], this, x, conv)
          };
          def convertMatr(x: Rep[Matr[T]]): Rep[To] = {
            assert(x.selfType1 match {
              case ((_): MatrElem[(_), (_)]) => true
              case _ => false
            });
            x.asRep[To]
          };
          override def getDefaultRep: Rep[To] = ???
        };
        implicit def matrElement[T](implicit eeT: Elem[T]): Elem[Matr[T]] = new MatrElem[T, Matr[T]]();
        implicit case object MatrCompanionElem extends CompanionElem[MatrCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[MatrCompanionAbs];
          protected def getDefaultRep = Matr
        };
        abstract class MatrCompanionAbs extends CompanionBase[MatrCompanionAbs] with MatrCompanion {
          override def toString = "Matr"
        };
        def Matr: Rep[MatrCompanionAbs];
        implicit def proxyMatrCompanion(p: Rep[MatrCompanion]): MatrCompanion = proxyOps[MatrCompanion](p);
        class CompoundMatrElem[T](val iso: Iso[CompoundMatrData[T], CompoundMatr[T]])(implicit eeT: Elem[T]) extends MatrElem[T, CompoundMatr[T]] with ConcreteElem[CompoundMatrData[T], CompoundMatr[T]] {
          override def convertMatr(x: Rep[Matr[T]]) = CompoundMatr(x.rows, x.numColumns);
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = {
            implicit val tagT = eeT.tag;
            weakTypeTag[CompoundMatr[T]]
          }
        };
        type CompoundMatrData[T] = scala.Tuple2[Col[Vec[T]], Int];
        class CompoundMatrIso[T](implicit eeT: Elem[T]) extends Iso[CompoundMatrData[T], CompoundMatr[T]]()(pairElement(implicitly[Elem[Col[Vec[T]]]], implicitly[Elem[Int]])) {
          override def from(p: Rep[CompoundMatr[T]]) = scala.Tuple2(p.rows, p.numColumns);
          override def to(p: Rep[scala.Tuple2[Col[Vec[T]], Int]]) = {
            val x$1 = (p: @scala.unchecked) match {
              case Pair((rows @ _), (numColumns @ _)) => scala.Tuple2(rows, numColumns)
            };
            val rows = x$1._1;
            val numColumns = x$1._2;
            CompoundMatr(rows, numColumns)
          };
          lazy val defaultRepTo = Default.defaultVal[Rep[CompoundMatr[T]]](CompoundMatr(element[Col[Vec[T]]].defaultRepValue, 0));
          lazy val eTo = new CompoundMatrElem[T](this)
        };
        abstract class CompoundMatrCompanionAbs extends CompanionBase[CompoundMatrCompanionAbs] with CompoundMatrCompanion {
          override def toString = "CompoundMatr";
          def apply[T](p: Rep[CompoundMatrData[T]])(implicit eeT: Elem[T]): Rep[CompoundMatr[T]] = isoCompoundMatr(eeT).to(p);
          def apply[T](rows: Rep[Col[Vec[T]]], numColumns: Rep[Int])(implicit eeT: Elem[T]): Rep[CompoundMatr[T]] = mkCompoundMatr(rows, numColumns)
        };
        object CompoundMatrMatcher {
          def unapply[T](p: Rep[Matr[T]]) = unmkCompoundMatr(p)
        };
        def CompoundMatr: Rep[CompoundMatrCompanionAbs];
        implicit def proxyCompoundMatrCompanion(p: Rep[CompoundMatrCompanionAbs]): CompoundMatrCompanionAbs = proxyOps[CompoundMatrCompanionAbs](p);
        implicit case object CompoundMatrCompanionElem extends CompanionElem[CompoundMatrCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[CompoundMatrCompanionAbs];
          protected def getDefaultRep = CompoundMatr
        };
        implicit def proxyCompoundMatr[T](p: Rep[CompoundMatr[T]]): CompoundMatr[T] = proxyOps[CompoundMatr[T]](p);
        implicit class ExtendedCompoundMatr[T](p: Rep[CompoundMatr[T]])(implicit eeT: Elem[T]) {
          def toData: Rep[CompoundMatrData[T]] = isoCompoundMatr(eeT).from(p)
        };
        implicit def isoCompoundMatr[T](implicit eeT: Elem[T]): Iso[CompoundMatrData[T], CompoundMatr[T]] = new CompoundMatrIso[T]();
        def mkCompoundMatr[T](rows: Rep[Col[Vec[T]]], numColumns: Rep[Int])(implicit eeT: Elem[T]): Rep[CompoundMatr[T]];
        def unmkCompoundMatr[T](p: Rep[Matr[T]]): Option[scala.Tuple2[Rep[Col[Vec[T]]], Rep[Int]]]
      };
      trait MatrsSeq extends MatrsDsl with ScalanSeq { self: LinearAlgebraDslSeq =>
        lazy val Matr: Rep[MatrCompanionAbs] = {
          final class $anon extends MatrCompanionAbs with UserTypeSeq[MatrCompanionAbs] {
            lazy val selfType = element[MatrCompanionAbs]
          };
          new $anon()
        };
        case class SeqCompoundMatr[T](override val rows: Rep[Col[Vec[T]]], override val numColumns: Rep[Int])(implicit eeT: Elem[T]) extends CompoundMatr[T](rows, numColumns) with UserTypeSeq[CompoundMatr[T]] {
          lazy val selfType = element[CompoundMatr[T]]
        };
        lazy val CompoundMatr = {
          final class $anon extends CompoundMatrCompanionAbs with UserTypeSeq[CompoundMatrCompanionAbs] {
            lazy val selfType = element[CompoundMatrCompanionAbs]
          };
          new $anon()
        };
        def mkCompoundMatr[T](rows: Rep[Col[Vec[T]]], numColumns: Rep[Int])(implicit eeT: Elem[T]): Rep[CompoundMatr[T]] = new SeqCompoundMatr[T](rows, numColumns);
        def unmkCompoundMatr[T](p: Rep[Matr[T]]) = p match {
          case (p @ ((_): CompoundMatr[T] @unchecked)) => Some(scala.Tuple2(p.rows, p.numColumns))
          case _ => None
        }
      };
      trait MatrsExp extends MatrsDsl with ScalanExp { self: LinearAlgebraDslExp =>
        lazy val Matr: Rep[MatrCompanionAbs] = {
          final class $anon extends MatrCompanionAbs with UserTypeDef[MatrCompanionAbs] {
            lazy val selfType = element[MatrCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        case class ExpCompoundMatr[T](override val rows: Rep[Col[Vec[T]]], override val numColumns: Rep[Int])(implicit eeT: Elem[T]) extends CompoundMatr[T](rows, numColumns) with UserTypeDef[CompoundMatr[T]] {
          lazy val selfType = element[CompoundMatr[T]];
          override def mirror(t: Transformer) = ExpCompoundMatr[T](t(rows), t(numColumns))
        };
        lazy val CompoundMatr: Rep[CompoundMatrCompanionAbs] = {
          final class $anon extends CompoundMatrCompanionAbs with UserTypeDef[CompoundMatrCompanionAbs] {
            lazy val selfType = element[CompoundMatrCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object CompoundMatrMethods {
          object numRows {
            def unapply(d: (Def[_$2] forSome { 
              type _$2
            })): Option[(Rep[CompoundMatr[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(CompoundMatrElem[_$3] forSome { 
  type _$3
})].&&(method.getName.==("numRows")) => Some(receiver).asInstanceOf[Option[(Rep[CompoundMatr[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$4] forSome { 
              type _$4
            })): Option[(Rep[CompoundMatr[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object columns {
            def unapply(d: (Def[_$5] forSome { 
              type _$5
            })): Option[(scala.Tuple2[Rep[CompoundMatr[T]], Rep[Numer[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((n @ _), _*), _) if receiver.elem.isInstanceOf[(CompoundMatrElem[_$6] forSome { 
  type _$6
})].&&(method.getName.==("columns")) => Some(scala.Tuple2(receiver, n)).asInstanceOf[Option[(scala.Tuple2[Rep[CompoundMatr[T]], Rep[Numer[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$7] forSome { 
              type _$7
            })): Option[(scala.Tuple2[Rep[CompoundMatr[T]], Rep[Numer[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object CompoundMatrCompanionMethods;
        def mkCompoundMatr[T](rows: Rep[Col[Vec[T]]], numColumns: Rep[Int])(implicit eeT: Elem[T]): Rep[CompoundMatr[T]] = new ExpCompoundMatr[T](rows, numColumns);
        def unmkCompoundMatr[T](p: Rep[Matr[T]]) = p.elem.asInstanceOf[(Elem[_$8] forSome { 
          type _$8
        })] match {
          case ((_): CompoundMatrElem[T] @unchecked) => Some(scala.Tuple2(p.asRep[CompoundMatr[T]].rows, p.asRep[CompoundMatr[T]].numColumns))
          case _ => None
        };
        object MatrMethods {
          object numColumns {
            def unapply(d: (Def[_$9] forSome { 
              type _$9
            })): Option[(Rep[Matr[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(MatrElem[_$10, _$11] forSome { 
  type _$10;
  type _$11
})].&&(method.getName.==("numColumns")) => Some(receiver).asInstanceOf[Option[(Rep[Matr[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$12] forSome { 
              type _$12
            })): Option[(Rep[Matr[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object numRows {
            def unapply(d: (Def[_$13] forSome { 
              type _$13
            })): Option[(Rep[Matr[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(MatrElem[_$14, _$15] forSome { 
  type _$14;
  type _$15
})].&&(method.getName.==("numRows")) => Some(receiver).asInstanceOf[Option[(Rep[Matr[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$16] forSome { 
              type _$16
            })): Option[(Rep[Matr[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object rows {
            def unapply(d: (Def[_$17] forSome { 
              type _$17
            })): Option[(Rep[Matr[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(MatrElem[_$18, _$19] forSome { 
  type _$18;
  type _$19
})].&&(method.getName.==("rows")) => Some(receiver).asInstanceOf[Option[(Rep[Matr[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$20] forSome { 
              type _$20
            })): Option[(Rep[Matr[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object columns {
            def unapply(d: (Def[_$21] forSome { 
              type _$21
            })): Option[(scala.Tuple2[Rep[Matr[T]], Rep[Numer[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((n @ _), _*), _) if receiver.elem.isInstanceOf[(MatrElem[_$22, _$23] forSome { 
  type _$22;
  type _$23
})].&&(method.getName.==("columns")) => Some(scala.Tuple2(receiver, n)).asInstanceOf[Option[(scala.Tuple2[Rep[Matr[T]], Rep[Numer[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$24] forSome { 
              type _$24
            })): Option[(scala.Tuple2[Rep[Matr[T]], Rep[Numer[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object MatrCompanionMethods
      };
      trait Matrs extends Base { self: LinearAlgebraDsl =>
        trait Matr[T] extends Reifiable[Matr[T]] {
          implicit def eeT: Elem[T];
          def numColumns: Rep[Int];
          def numRows: Rep[Int];
          def rows: Rep[Col[Vec[T]]];
          def columns(implicit n: Rep[Numer[T]]): Rep[Col[Vec[T]]]
        };
        abstract class CompoundMatr[T](val rows: Rep[Col[Vec[T]]], val numColumns: Rep[Int])(implicit val eeT: Elem[T]) extends Matr[T] with Product with Serializable {
          def numRows = rows.length;
          def columns(implicit n: Rep[Numer[T]]): Rep[Col[Vec[T]]] = Col(array_map(SArray.rangeFrom0(numColumns), fun(((j: Rep[Int]) => ((DenseVec(rows.map(fun(((vec: Rep[Vec[T]]) => vec(j)))))): Rep[Vec[T]])))))
        };
        trait MatrCompanion;
        trait CompoundMatrCompanion
      };
      trait MatrsDsl extends MatrsAbs { self: LinearAlgebraDsl =>
        
      };
      trait MatrsDslSeq extends MatrsSeq { self: LinearAlgebraDslSeq =>
        
      };
      trait MatrsDslExp extends MatrsExp { self: LinearAlgebraDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AARNYXRyc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4c3EAfgAPdAAHUHJvZHVjdHEAfgAHc3EAfgAPdAAMU2VyaWFsaXphYmxlcQB+AAdxAH4ACXhxAH4AB3NyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzQXJncyfr7mTFMABcAgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZwVz2NYL1Ck/AgAIWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnWgAHdmFsRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGV0ACBMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcGVFeHByO3hwAAAAAXEAfgAHc3IAC3NjYWxhLk5vbmUkRlAk9lPKlKwCAAB4cgAMc2NhbGEuT3B0aW9u/mk3/dsOZnQCAAB4cHQABHJvd3NzcQB+AA90AANDb2xzcQB+AAZzcQB+AA90AANWZWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+ABwAAAABcQB+AAdxAH4AIXQACm51bUNvbHVtbnNzcgAjc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVQcmltaXRpdmU1w7tV8L5OBQIAAkwAEmRlZmF1bHRWYWx1ZVN0cmluZ3EAfgAETAAEbmFtZXEAfgAEeHB0AAEwdAADSW50cQB+AAl4c3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kRGVm3VlxcbcwKw4CAApaAAxpc0VsZW1PckNvbnRaAAppc0ltcGxpY2l0WgAKaXNPdmVycmlkZUwAC2Fubm90YXRpb25zcQB+AAFMAAthcmdTZWN0aW9uc3EAfgABTAAEYm9keXEAfgADTAAEbmFtZXEAfgAETAAKb3ZlcmxvYWRJZHEAfgADTAAHdHBlQXJnc3EAfgABTAAGdHBlUmVzcQB+AAN4cAAAAHEAfgAHcQB+AAdzcgAKc2NhbGEuU29tZREi8mleoYt0AgABTAABeHQAEkxqYXZhL2xhbmcvT2JqZWN0O3hxAH4AIHNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1NlbGVjdAvdaWVlQPmFAgACTAAEZXhwcnQAHUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU0V4cHI7TAAFdG5hbWVxAH4ABHhwc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTSWRlbnQnEqX78AnXTwIAAUwABG5hbWVxAH4ABHhwdAAEcm93c3QABmxlbmd0aHQAB251bVJvd3NxAH4AIXEAfgAHcQB+ACFzcQB+ADIAAABxAH4AB3NxAH4ABnNyACFzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZ3MpwieWXnW6iwIAAUwABGFyZ3NxAH4AAXhwc3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kQXJnA9z/y5NycwMCAAdaAAdpbXBGbGFnWgAMaXNFbGVtT3JDb250WgAIb3ZlckZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBlcQB+AB14cAEAAHEAfgAHcQB+ACF0AAFuc3EAfgAPdAAFTnVtZXJzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhzcQB+ADRzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNBcHBseYRmKZ/Smvx/AgADTAAFYXJnc3NxAH4AAUwAA2Z1bnEAfgA4TAACdHNxAH4AAXhwc3EAfgAGc3EAfgAGc3EAfgBNc3EAfgAGc3EAfgAGc3IAG3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTRnVuY7BM+JAaxTCDAgACTAAGcGFyYW1zcQB+AAFMAANyZXNxAH4AOHhwc3EAfgAGc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVmFsRGVmKwY5unvdRQ8CAAVaAAppc0ltcGxpY2l0WgAGaXNMYXp5TAAEZXhwcnEAfgA4TAAEbmFtZXEAfgAETAADdHBlcQB+AAN4cAAAc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTRW1wdHmRYBXhS+VovgIAAHhwdAABanNxAH4ANHEAfgAucQB+AAl4c3IAG3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQXNjcmDEeSeFkj7MAgACTAAEZXhwcnEAfgA4TAACcHRxAH4AHXhwc3EAfgBNc3EAfgAGc3EAfgAGc3EAfgBNc3EAfgAGc3EAfgAGc3EAfgBUc3EAfgAGc3EAfgBXAABzcQB+AFl0AAN2ZWNzcQB+ADRzcQB+AA90AANWZWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+AE1zcQB+AAZzcQB+AAZzcQB+ADp0AAFqcQB+AAl4cQB+AAl4c3EAfgA6dAADdmVjcQB+AAdxAH4ACXhxAH4ACXhzcQB+ADdzcQB+ADp0AARyb3dzdAADbWFwcQB+AAdxAH4ACXhxAH4ACXhzcQB+ADp0AAhEZW5zZVZlY3EAfgAHc3EAfgAPdAADVmVjc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4c3EAfgA3c3EAfgBdc3EAfgBNc3EAfgAGc3EAfgAGc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ29uc3QaTRTRYktnigIAAUwAAWNxAH4ANXhwc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAABzcQB+ADp0AApudW1Db2x1bW5zc3EAfgCHc3EAfgCJAAAAAXEAfgAJeHEAfgAJeHNxAH4AN3NxAH4AOnQABUFycmF5dAAFcmFuZ2VxAH4AB3NxAH4AD3QABUFycmF5c3EAfgAGcQB+AC5xAH4ACXhxAH4AenEAfgAHcQB+AAl4cQB+AAl4c3EAfgA6dAADQ29scQB+AAd0AAdjb2x1bW5zcQB+ACFxAH4AB3NxAH4ANHNxAH4AD3QAA0NvbHNxAH4ABnNxAH4AD3QAA1ZlY3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgAJeHEAfgAhc3EAfgAZc3EAfgAGc3EAfgAcAQAAAXEAfgAHcQB+ACF0AANjdFRzcQB+AA90AAhDbGFzc1RhZ3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHQADENvbXBvdW5kTWF0cnEAfgAhc3EAfgAGc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlQXJnklsmSHLxm4kCAARMAAVib3VuZHEAfgADTAAMY29udGV4dEJvdW5kcQB+AAFMAARuYW1lcQB+AARMAAd0cGFyYW1zcQB+AAF4cHEAfgAhcQB+AAd0AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdERlZgG+NJI0FLGyAgAJWgAIYml0bWFwJDBMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAMTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgAHcQB+AAdzcQB+AAZzcQB+ADIAAQBxAH4AB3EAfgAHcQB+ACF0AANjdFRxAH4AIXEAfgAHc3EAfgA0c3EAfgAPdAAIQ2xhc3NUYWdzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhzcQB+ADIAAABxAH4AB3EAfgAHcQB+ACF0AApudW1Db2x1bW5zcQB+ACFxAH4AB3NxAH4ANHEAfgAuc3EAfgAyAAAAcQB+AAdxAH4AB3EAfgAhdAAHbnVtUm93c3EAfgAhcQB+AAdzcQB+ADRxAH4ALnNxAH4AMgAAAHEAfgAHcQB+AAdxAH4AIXQABHJvd3NxAH4AIXEAfgAHc3EAfgA0c3EAfgAPdAADQ29sc3EAfgAGc3EAfgAPdAADVmVjc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4c3EAfgAyAAAAcQB+AAdzcQB+AAZzcQB+AEFzcQB+AAZzcQB+AEQBAABxAH4AB3EAfgAhdAABbnNxAH4AD3QABU51bWVyc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4cQB+ACF0AAdjb2x1bW5zcQB+ACFxAH4AB3NxAH4ANHNxAH4AD3QAA0NvbHNxAH4ABnNxAH4AD3QAA1ZlY3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgAJeHEAfgAhcHQABE1hdHJxAH4AIXNxAH4ABnNxAH4ArnEAfgAhcQB+AAd0AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4As3EAfgAhc3EAfgAGc3IAIXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTSW1wb3J0U3RhdKyAoErDxhsiAgABTAAEbmFtZXEAfgAEeHB0ABZzY2FsYS5yZWZsZWN0LkNsYXNzVGFncQB+AAl4cQB+AAd0AAVNYXRyc3QAE3BhcmFkaXNlLmxpbmFsZ2VicmFzcQB+ADRzcgAic2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxmVHlwZURlZn73nU4qzrPlAgACTAAKY29tcG9uZW50c3EAfgABTAAEbmFtZXEAfgAEeHBzcQB+AAZzcQB+AA90AA1MaW5lYXJBbGdlYnJhcQB+AAdxAH4ACXh0AARzZWxmcQB+ACE="
    }

    object HotSpotKernels {
      import java.io.File;
      import scalan.compilation.GraphVizConfig
    }

    object HotSpotManager {
      import scalan.ScalanCommunityDslExp;
      import scalan.compilation.lms.{CommunityLmsBackend, CoreBridge};
      import scalan.compilation.lms.scalac.CommunityLmsCompilerScala;
      import scalan.primitives.EffectfulCompiler;
      import paradise.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      lazy val scalanContext = new Scalan();
      def getScalanContext = scalanContext;
      class Scalan extends LinearAlgebraDslExp with CommunityLmsCompilerScala with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler {
        val lms = new CommunityLmsBackend()
      }
    }
  }
}