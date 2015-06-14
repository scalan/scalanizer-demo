package scalanizer.linalgebra {
  package implOfMatrs {
    object StagedEvaluation {
      import scalan._;
      import scalanizer.implOfNumMonoids.StagedEvaluation._;
      import scalanizer.collections.implOfCols.StagedEvaluation._;
      import scalanizer.linalgebra.implOfVecs.StagedEvaluation._;
      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait MatrsAbs extends Matrs with ScalanDsl { self: LinearAlgebraDsl =>
        implicit def proxyMatr[T](p: Rep[Matr[T]]): Matr[T] = proxyOps[Matr[T]](p)(scala.reflect.classTag[Matr[T]]);
        class MatrElem[T, To <: Matr[T]](implicit val eeT: Elem[T]) extends EntityElem[To] {
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagT = eeT.tag;
            weakTypeTag[Matr[T]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$1] forSome { 
            type _$1
          })]) = {
            implicit val eTo: Elem[To] = this;
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
          lazy val defaultRepTo: Rep[CompoundMatr[T]] = CompoundMatr(element[Col[Vec[T]]].defaultRepValue, 0);
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
})].&&(__equal(method.getName, "numRows")) => Some(receiver).asInstanceOf[Option[(Rep[CompoundMatr[T]] forSome { 
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
            })): Option[(scala.Tuple2[Rep[CompoundMatr[T]], Rep[Num[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((n @ _), _*), _) if receiver.elem.isInstanceOf[(CompoundMatrElem[_$6] forSome { 
  type _$6
})].&&(__equal(method.getName, "columns")) => Some(scala.Tuple2(receiver, n)).asInstanceOf[Option[(scala.Tuple2[Rep[CompoundMatr[T]], Rep[Num[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$7] forSome { 
              type _$7
            })): Option[(scala.Tuple2[Rep[CompoundMatr[T]], Rep[Num[T]]] forSome { 
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
})].&&(__equal(method.getName, "numColumns")) => Some(receiver).asInstanceOf[Option[(Rep[Matr[T]] forSome { 
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
})].&&(__equal(method.getName, "numRows")) => Some(receiver).asInstanceOf[Option[(Rep[Matr[T]] forSome { 
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
})].&&(__equal(method.getName, "rows")) => Some(receiver).asInstanceOf[Option[(Rep[Matr[T]] forSome { 
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
            })): Option[(scala.Tuple2[Rep[Matr[T]], Rep[Num[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((n @ _), _*), _) if receiver.elem.isInstanceOf[(MatrElem[_$22, _$23] forSome { 
  type _$22;
  type _$23
})].&&(__equal(method.getName, "columns")) => Some(scala.Tuple2(receiver, n)).asInstanceOf[Option[(scala.Tuple2[Rep[Matr[T]], Rep[Num[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$24] forSome { 
              type _$24
            })): Option[(scala.Tuple2[Rep[Matr[T]], Rep[Num[T]]] forSome { 
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
          def columns(implicit n: Rep[Num[T]]): Rep[Col[Vec[T]]]
        };
        abstract class CompoundMatr[T](val rows: Rep[Col[Vec[T]]], val numColumns: Rep[Int])(implicit val eeT: Elem[T]) extends Matr[T] with Product with Serializable {
          def numRows = rows.length;
          def columns(implicit n: Rep[Num[T]]): Rep[Col[Vec[T]]] = Col(array_map(SArray.rangeFrom0(numColumns), fun(((j: Rep[Int]) => ((DenseVec(rows.map(fun(((vec: Rep[Vec[T]]) => vec(j)))))): Rep[Vec[T]])))))
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
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AARNYXRyc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4c3EAfgAPdAAHUHJvZHVjdHEAfgAHc3EAfgAPdAAMU2VyaWFsaXphYmxlcQB+AAdxAH4ACXhxAH4AB3NyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzQXJncyfr7mTFMABcAgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZwVz2NYL1Ck/AgAIWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnWgAHdmFsRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGV0ACBMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcGVFeHByO3hwAAAAAXEAfgAHc3IAC3NjYWxhLk5vbmUkRlAk9lPKlKwCAAB4cgAMc2NhbGEuT3B0aW9u/mk3/dsOZnQCAAB4cHQABHJvd3NzcQB+AA90AANDb2xzcQB+AAZzcQB+AA90AANWZWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+ABwAAAABcQB+AAdxAH4AIXQACm51bUNvbHVtbnNzcgAjc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVQcmltaXRpdmU1w7tV8L5OBQIAAkwAEmRlZmF1bHRWYWx1ZVN0cmluZ3EAfgAETAAEbmFtZXEAfgAEeHB0AAEwdAADSW50cQB+AAl4c3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kRGVm3VlxcbcwKw4CAApaAAxpc0VsZW1PckNvbnRaAAppc0ltcGxpY2l0WgAKaXNPdmVycmlkZUwAC2Fubm90YXRpb25zcQB+AAFMAAthcmdTZWN0aW9uc3EAfgABTAAEYm9keXEAfgADTAAEbmFtZXEAfgAETAAKb3ZlcmxvYWRJZHEAfgADTAAHdHBlQXJnc3EAfgABTAAGdHBlUmVzcQB+AAN4cAAAAHEAfgAHcQB+AAdzcgAKc2NhbGEuU29tZREi8mleoYt0AgABTAABeHQAEkxqYXZhL2xhbmcvT2JqZWN0O3hxAH4AIHNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1NlbGVjdAvdaWVlQPmFAgACTAAEZXhwcnQAHUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU0V4cHI7TAAFdG5hbWVxAH4ABHhwc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTSWRlbnQnEqX78AnXTwIAAUwABG5hbWVxAH4ABHhwdAAEcm93c3QABmxlbmd0aHQAB251bVJvd3NxAH4AIXEAfgAHcQB+ACFzcQB+ADIAAABxAH4AB3NxAH4ABnNyACFzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZ3MpwieWXnW6iwIAAUwABGFyZ3NxAH4AAXhwc3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kQXJnA9z/y5NycwMCAAdaAAdpbXBGbGFnWgAMaXNFbGVtT3JDb250WgAIb3ZlckZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBlcQB+AB14cAEAAHEAfgAHcQB+ACF0AAFuc3EAfgAPdAADTnVtc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4c3EAfgA0c3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQXBwbHmEZimf0pr8fwIAA0wABWFyZ3NzcQB+AAFMAANmdW5xAH4AOEwAAnRzcQB+AAF4cHNxAH4ABnNxAH4ABnNxAH4ATXNxAH4ABnNxAH4ABnNyABtzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0Z1bmOwTPiQGsUwgwIAAkwABnBhcmFtc3EAfgABTAADcmVzcQB+ADh4cHNxAH4ABnNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1ZhbERlZisGObp73UUPAgAFWgAKaXNJbXBsaWNpdFoABmlzTGF6eUwABGV4cHJxAH4AOEwABG5hbWVxAH4ABEwAA3RwZXEAfgADeHAAAHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VtcHR5kWAV4UvlaL4CAAB4cHQAAWpzcQB+ADRxAH4ALnEAfgAJeHNyABtzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0FzY3JgxHknhZI+zAIAAkwABGV4cHJxAH4AOEwAAnB0cQB+AB14cHNxAH4ATXNxAH4ABnNxAH4ABnNxAH4ATXNxAH4ABnNxAH4ABnNxAH4AVHNxAH4ABnNxAH4AVwAAc3EAfgBZdAADdmVjc3EAfgA0c3EAfgAPdAADVmVjc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4c3EAfgBNc3EAfgAGc3EAfgAGc3EAfgA6dAABanEAfgAJeHEAfgAJeHNxAH4AOnQAA3ZlY3EAfgAHcQB+AAl4cQB+AAl4c3EAfgA3c3EAfgA6dAAEcm93c3QAA21hcHEAfgAHcQB+AAl4cQB+AAl4c3EAfgA6dAAIRGVuc2VWZWNxAH4AB3NxAH4AD3QAA1ZlY3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgAJeHNxAH4AN3NxAH4AXXNxAH4ATXNxAH4ABnNxAH4ABnNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NvbnN0Gk0U0WJLZ4oCAAFMAAFjcQB+ADV4cHNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAAAc3EAfgA6dAAKbnVtQ29sdW1uc3NxAH4Ah3NxAH4AiQAAAAFxAH4ACXhxAH4ACXhzcQB+ADdzcQB+ADp0AAVBcnJheXQABXJhbmdlcQB+AAdzcQB+AA90AAVBcnJheXNxAH4ABnEAfgAucQB+AAl4cQB+AHpxAH4AB3EAfgAJeHEAfgAJeHNxAH4AOnQAA0NvbHEAfgAHdAAHY29sdW1uc3EAfgAhcQB+AAdzcQB+ADRzcQB+AA90AANDb2xzcQB+AAZzcQB+AA90AANWZWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4AIXNxAH4AGXNxAH4ABnNxAH4AHAEAAAFxAH4AB3EAfgAhdAADY3RUc3EAfgAPdAAIQ2xhc3NUYWdzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXh0AAxDb21wb3VuZE1hdHJxAH4AIXNxAH4ABnNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZUFyZ5JbJkhy8ZuJAgAETAAFYm91bmRxAH4AA0wADGNvbnRleHRCb3VuZHEAfgABTAAEbmFtZXEAfgAETAAHdHBhcmFtc3EAfgABeHBxAH4AIXEAfgAHdAABVHEAfgAHcQB+AAl4cQB+AAl4c3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHJhaXREZWYBvjSSNBSxsgIACVoACGJpdG1hcCQwTAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABxAH4AB3EAfgAHc3EAfgAGc3EAfgAyAAEAcQB+AAdxAH4AB3EAfgAhdAADY3RUcQB+ACFxAH4AB3NxAH4ANHNxAH4AD3QACENsYXNzVGFnc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4c3EAfgAyAAAAcQB+AAdxAH4AB3EAfgAhdAAKbnVtQ29sdW1uc3EAfgAhcQB+AAdzcQB+ADRxAH4ALnNxAH4AMgAAAHEAfgAHcQB+AAdxAH4AIXQAB251bVJvd3NxAH4AIXEAfgAHc3EAfgA0cQB+AC5zcQB+ADIAAABxAH4AB3EAfgAHcQB+ACF0AARyb3dzcQB+ACFxAH4AB3NxAH4ANHNxAH4AD3QAA0NvbHNxAH4ABnNxAH4AD3QAA1ZlY3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHNxAH4AMgAAAHEAfgAHc3EAfgAGc3EAfgBBc3EAfgAGc3EAfgBEAQAAcQB+AAdxAH4AIXQAAW5zcQB+AA90AANOdW1zcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4AIXQAB2NvbHVtbnNxAH4AIXEAfgAHc3EAfgA0c3EAfgAPdAADQ29sc3EAfgAGc3EAfgAPdAADVmVjc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4cQB+ACFwdAAETWF0cnEAfgAhc3EAfgAGc3EAfgCucQB+ACFxAH4AB3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgCzcQB+ACFzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJbXBvcnRTdGF0rICgSsPGGyICAAFMAARuYW1lcQB+AAR4cHQAFnNjYWxhLnJlZmxlY3QuQ2xhc3NUYWdxAH4ACXhxAH4AB3QABU1hdHJzdAAVc2NhbGFuaXplci5saW5hbGdlYnJhc3EAfgA0c3IAInNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTU2VsZlR5cGVEZWZ+951OKs6z5QIAAkwACmNvbXBvbmVudHNxAH4AAUwABG5hbWVxAH4ABHhwc3EAfgAGc3EAfgAPdAANTGluZWFyQWxnZWJyYXEAfgAHcQB+AAl4dAAEc2VsZnEAfgAh"
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
      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      lazy val scalanContext = new Scalan();
      def getScalanContext = scalanContext;
      class Scalan extends LinearAlgebraDslExp with CommunityLmsCompilerScala with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler {
        val lms = new CommunityLmsBackend()
      };
      import scalan.CommunityMethodMappingDSL;
      import scalan.compilation.lms.uni.LmsCompilerUni;
      lazy val scalanContextUni = new ScalanUni();
      def getScalanContextUni = scalanContextUni;
      class ScalanUni extends LinearAlgebraDslExp with LmsCompilerUni with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler with CommunityMethodMappingDSL {
        val lms = new CommunityLmsBackend()
      }
    }
  }
}