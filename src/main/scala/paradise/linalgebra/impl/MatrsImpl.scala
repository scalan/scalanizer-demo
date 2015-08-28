package scalanizer.linalgebra {
  package implOfMatrs {
    object StagedEvaluation {
      import scalan._;
      import scalan.meta.ScalanAst._;
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
          lazy val parent: Option[(Elem[_$1] forSome { 
            type _$1
          })] = None;
          lazy val entityDef: STraitOrClassDef = {
            val module = getModules("Matrs");
            module.entities.find(((x$1) => __equal(x$1.name, "Matr"))).get
          };
          lazy val tyArgSubst: Map[String, TypeDesc] = Map("T".->(Left(eeT)));
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagT = eeT.tag;
            weakTypeTag[Matr[T]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$2] forSome { 
            type _$2
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
        class DenseMatrElem[T](val iso: Iso[DenseMatrData[T], DenseMatr[T]])(implicit eeT: Elem[T]) extends MatrElem[T, DenseMatr[T]] with ConcreteElem[DenseMatrData[T], DenseMatr[T]] {
          override lazy val parent: Option[(Elem[_$3] forSome { 
            type _$3
          })] = Some(matrElement(element[T]));
          override lazy val entityDef = {
            val module = getModules("Matrs");
            module.concreteSClasses.find(((x$2) => __equal(x$2.name, "DenseMatr"))).get
          };
          override lazy val tyArgSubst: Map[String, TypeDesc] = Map("T".->(Left(eeT)));
          override def convertMatr(x: Rep[Matr[T]]) = DenseMatr(x.rows, x.numColumns);
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = {
            implicit val tagT = eeT.tag;
            weakTypeTag[DenseMatr[T]]
          }
        };
        type DenseMatrData[T] = scala.Tuple2[Col[Vec[T]], Int];
        class DenseMatrIso[T](implicit eeT: Elem[T]) extends Iso[DenseMatrData[T], DenseMatr[T]]()(pairElement(implicitly[Elem[Col[Vec[T]]]], implicitly[Elem[Int]])) {
          override def from(p: Rep[DenseMatr[T]]) = scala.Tuple2(p.rows, p.numColumns);
          override def to(p: Rep[scala.Tuple2[Col[Vec[T]], Int]]) = {
            val x$3 = (p: @scala.unchecked) match {
              case Pair((rows @ _), (numColumns @ _)) => scala.Tuple2(rows, numColumns)
            };
            val rows = x$3._1;
            val numColumns = x$3._2;
            DenseMatr(rows, numColumns)
          };
          lazy val defaultRepTo: Rep[DenseMatr[T]] = DenseMatr(element[Col[Vec[T]]].defaultRepValue, 0);
          lazy val eTo = new DenseMatrElem[T](this)
        };
        abstract class DenseMatrCompanionAbs extends CompanionBase[DenseMatrCompanionAbs] with DenseMatrCompanion {
          override def toString = "DenseMatr";
          def apply[T](p: Rep[DenseMatrData[T]])(implicit eeT: Elem[T]): Rep[DenseMatr[T]] = isoDenseMatr(eeT).to(p);
          def apply[T](rows: Rep[Col[Vec[T]]], numColumns: Rep[Int])(implicit eeT: Elem[T]): Rep[DenseMatr[T]] = mkDenseMatr(rows, numColumns)
        };
        object DenseMatrMatcher {
          def unapply[T](p: Rep[Matr[T]]) = unmkDenseMatr(p)
        };
        def DenseMatr: Rep[DenseMatrCompanionAbs];
        implicit def proxyDenseMatrCompanion(p: Rep[DenseMatrCompanionAbs]): DenseMatrCompanionAbs = proxyOps[DenseMatrCompanionAbs](p);
        implicit case object DenseMatrCompanionElem extends CompanionElem[DenseMatrCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[DenseMatrCompanionAbs];
          protected def getDefaultRep = DenseMatr
        };
        implicit def proxyDenseMatr[T](p: Rep[DenseMatr[T]]): DenseMatr[T] = proxyOps[DenseMatr[T]](p);
        implicit class ExtendedDenseMatr[T](p: Rep[DenseMatr[T]])(implicit eeT: Elem[T]) {
          def toData: Rep[DenseMatrData[T]] = isoDenseMatr(eeT).from(p)
        };
        implicit def isoDenseMatr[T](implicit eeT: Elem[T]): Iso[DenseMatrData[T], DenseMatr[T]] = new DenseMatrIso[T]();
        def mkDenseMatr[T](rows: Rep[Col[Vec[T]]], numColumns: Rep[Int])(implicit eeT: Elem[T]): Rep[DenseMatr[T]];
        def unmkDenseMatr[T](p: Rep[Matr[T]]): Option[scala.Tuple2[Rep[Col[Vec[T]]], Rep[Int]]];
        registerModule(scalan.meta.ScalanCodegen.loadModule(Matrs_Module.dump))
      };
      trait MatrsSeq extends MatrsDsl with ScalanSeq { self: LinearAlgebraDslSeq =>
        lazy val Matr: Rep[MatrCompanionAbs] = {
          final class $anon extends MatrCompanionAbs with UserTypeSeq[MatrCompanionAbs] {
            lazy val selfType = element[MatrCompanionAbs]
          };
          new $anon()
        };
        case class SeqDenseMatr[T](override val rows: Rep[Col[Vec[T]]], override val numColumns: Rep[Int])(implicit eeT: Elem[T]) extends DenseMatr[T](rows, numColumns) with UserTypeSeq[DenseMatr[T]] {
          lazy val selfType = element[DenseMatr[T]]
        };
        lazy val DenseMatr = {
          final class $anon extends DenseMatrCompanionAbs with UserTypeSeq[DenseMatrCompanionAbs] {
            lazy val selfType = element[DenseMatrCompanionAbs]
          };
          new $anon()
        };
        def mkDenseMatr[T](rows: Rep[Col[Vec[T]]], numColumns: Rep[Int])(implicit eeT: Elem[T]): Rep[DenseMatr[T]] = new SeqDenseMatr[T](rows, numColumns);
        def unmkDenseMatr[T](p: Rep[Matr[T]]) = p match {
          case (p @ ((_): DenseMatr[T] @unchecked)) => Some(scala.Tuple2(p.rows, p.numColumns))
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
        case class ExpDenseMatr[T](override val rows: Rep[Col[Vec[T]]], override val numColumns: Rep[Int])(implicit eeT: Elem[T]) extends DenseMatr[T](rows, numColumns) with UserTypeDef[DenseMatr[T]] {
          lazy val selfType = element[DenseMatr[T]];
          override def mirror(t: Transformer) = ExpDenseMatr[T](t(rows), t(numColumns))
        };
        lazy val DenseMatr: Rep[DenseMatrCompanionAbs] = {
          final class $anon extends DenseMatrCompanionAbs with UserTypeDef[DenseMatrCompanionAbs] {
            lazy val selfType = element[DenseMatrCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object DenseMatrMethods {
          object numRows {
            def unapply(d: (Def[_$4] forSome { 
              type _$4
            })): Option[(Rep[DenseMatr[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(DenseMatrElem[_$5] forSome { 
  type _$5
})].&&(__equal(method.getName, "numRows")) => Some(receiver).asInstanceOf[Option[(Rep[DenseMatr[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$6] forSome { 
              type _$6
            })): Option[(Rep[DenseMatr[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object columns {
            def unapply(d: (Def[_$7] forSome { 
              type _$7
            })): Option[(scala.Tuple2[Rep[DenseMatr[T]], Rep[Num[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((n @ _), _*), _) if receiver.elem.isInstanceOf[(DenseMatrElem[_$8] forSome { 
  type _$8
})].&&(__equal(method.getName, "columns")) => Some(scala.Tuple2(receiver, n)).asInstanceOf[Option[(scala.Tuple2[Rep[DenseMatr[T]], Rep[Num[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$9] forSome { 
              type _$9
            })): Option[(scala.Tuple2[Rep[DenseMatr[T]], Rep[Num[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object DenseMatrCompanionMethods;
        def mkDenseMatr[T](rows: Rep[Col[Vec[T]]], numColumns: Rep[Int])(implicit eeT: Elem[T]): Rep[DenseMatr[T]] = new ExpDenseMatr[T](rows, numColumns);
        def unmkDenseMatr[T](p: Rep[Matr[T]]) = p.elem.asInstanceOf[(Elem[_$10] forSome { 
          type _$10
        })] match {
          case ((_): DenseMatrElem[T] @unchecked) => Some(scala.Tuple2(p.asRep[DenseMatr[T]].rows, p.asRep[DenseMatr[T]].numColumns))
          case _ => None
        };
        object MatrMethods {
          object numColumns {
            def unapply(d: (Def[_$11] forSome { 
              type _$11
            })): Option[(Rep[Matr[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(MatrElem[_$12, _$13] forSome { 
  type _$12;
  type _$13
})].&&(__equal(method.getName, "numColumns")) => Some(receiver).asInstanceOf[Option[(Rep[Matr[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$14] forSome { 
              type _$14
            })): Option[(Rep[Matr[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object numRows {
            def unapply(d: (Def[_$15] forSome { 
              type _$15
            })): Option[(Rep[Matr[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(MatrElem[_$16, _$17] forSome { 
  type _$16;
  type _$17
})].&&(__equal(method.getName, "numRows")) => Some(receiver).asInstanceOf[Option[(Rep[Matr[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$18] forSome { 
              type _$18
            })): Option[(Rep[Matr[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object rows {
            def unapply(d: (Def[_$19] forSome { 
              type _$19
            })): Option[(Rep[Matr[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(MatrElem[_$20, _$21] forSome { 
  type _$20;
  type _$21
})].&&(__equal(method.getName, "rows")) => Some(receiver).asInstanceOf[Option[(Rep[Matr[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$22] forSome { 
              type _$22
            })): Option[(Rep[Matr[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object columns {
            def unapply(d: (Def[_$23] forSome { 
              type _$23
            })): Option[(scala.Tuple2[Rep[Matr[T]], Rep[Num[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((n @ _), _*), _) if receiver.elem.isInstanceOf[(MatrElem[_$24, _$25] forSome { 
  type _$24;
  type _$25
})].&&(__equal(method.getName, "columns")) => Some(scala.Tuple2(receiver, n)).asInstanceOf[Option[(scala.Tuple2[Rep[Matr[T]], Rep[Num[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$26] forSome { 
              type _$26
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
      object Matrs_Module {
        val packageName = "scalanizer.linalgebra";
        val name = "Matrs";
        val dump = "H4sIAAAAAAAAALVWPYwbRRR+Xtvx2T6S44BIJwVxdxgQCOwDgVJcES6+C0rk+9HtESETIY3XY2fC7OzezPiwKVJQQodoEUqfjgYJKQ1CQhRUCJCoqUIQioBUIN7M/ngdskkaXDztzL5573vf996sr9+CspLwrPIIJ6LpU02arn3eULrhbgnN9GQ76I843aSDD05+4W2Ls8qBE104dpmoTcW7UI0etsZh+uzSww5UifCo0oFUGlY6NkPLCzinnmaBaDHfH2nS47TVYUqvd6DUC/qTQ7gKhQ4seIHwJNXUbXOiFFXx/hw1iFi6rtr1ZDec5hAtU0UrU8WBJEwjfMyxEPnv09CdiEBMfA3HY2i7oYGFPhXmh4HUSYoKhrsc9JNlSRDcgMXOFXJEWphi2HK1ZGKIJ+sh8d4lQ7qDLsa9hIAV5YODSWjXxQ7UFD1Egs77Ibc74xAAUIFXLIjmlJ9myk/T8NNwqWSEs/eJebkng/EEol+hCDAOMcSLDwiRRKBbot/48JL39h237jvm8NhAqdgKj2Ggp3K6wUqBPH6z/7G6/ca10w7UulBjaqOntCSezkoes1UnQgTaYk4JJHKIaq3mqWWzbKDPXS1R9QI/JAIjxVTOo06ceUwbZ7M3H6uTQ31FhzRxLSDvab3LOfXavmkTzvduLr30zK9bbzngzKaoYkgXG18mQTWUtomWcWhjT2goHFh+jamOk80KatgfeXpKPW7OpyqjaJlDkVM+0pSz527+1v96DS45KdMxsIcTF0OU1U8/1L9//owDc107Cuc4GXaRbLXFqb8r24HQXZgLjqiM3lSOCDdP9xS70qcDMuI6liDLXRG507CcO7QhNcSu2wEpJATUox7fCQRtnNtr/OV++8l108IS5qM30RT/w07//fPxgbbdjZrI4D2VcFxsB3xWn+JF6t1HsYxup1IsxqxoqImRj/FGvlCI7um8RgrpnmQ+XlxH9LWvvnzz9xs7ZdtLizE9Fwkf0egaidmZMmUKKKwhyvNCz3ZDLSrZDXz66Opt9s61j7TVvTCevZ92e1fwQli355bv0wLJPflnd835Y+nHzxyootI9pn0SNtYecrr/z4lNmZ+aFWRncZMKRc3ctbMJV6ajtZSR91ShAJDVsEjpQSJ8yTR55GzMY9kO0FBNE9mj6Uw9ma87wj+533mc3zpzw4HyBSgPcFRUB8q9YCT6CS/4tdN0rM8me4VZXpAHIomf8mB/yzAtMr9jo5pfhdn+ru5TNmDmkpndf9DldbetTCl+OU6Uo9EjOfKExl7I0mns6/8t0Zjte0LZnHVCMsq2DA1PRLrgJ082OROED2lPkhiuhNUc2dy4C3EUrt75dOeF7z7/xU5rzfQz3jwi/W+QndJZ2hY6TFAiN6KU+MXPQEeeTaNb2P8CNpaPI4EJAAA="
      };
      trait Matrs extends Base { self: LinearAlgebraDsl =>
        trait Matr[T] extends Reifiable[Matr[T]] {
          implicit def eeT: Elem[T];
          def numColumns: Rep[Int];
          def numRows: Rep[Int];
          def rows: Rep[Col[Vec[T]]];
          def columns(implicit n: Rep[Num[T]]): Rep[Col[Vec[T]]]
        };
        abstract class DenseMatr[T](val rows: Rep[Col[Vec[T]]], val numColumns: Rep[Int])(implicit val eeT: Elem[T]) extends Matr[T] with Product with Serializable {
          def numRows = rows.length;
          def columns(implicit n: Rep[Num[T]]): Rep[Col[Vec[T]]] = Col(array_map(SArray.rangeFrom0(numColumns), fun(((j: Rep[Int]) => ((DenseVec(rows.map(fun(((vec: Rep[Vec[T]]) => vec(j)))))): Rep[Vec[T]])))))
        };
        trait MatrCompanion;
        trait DenseMatrCompanion
      };
      trait MatrsDsl extends MatrsAbs { self: LinearAlgebraDsl =>
        
      };
      trait MatrsDslSeq extends MatrsSeq { self: LinearAlgebraDslSeq =>
        
      };
      trait MatrsDslExp extends MatrsExp { self: LinearAlgebraDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZoIWuGNNbkJzAgAPWgAGaGFzRHNsWgAJaGFzRHNsRXhwWgAJaGFzRHNsU2VxTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwAAAAc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZshSknPyR6E3AgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AARNYXRyc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4c3EAfgAPdAAHUHJvZHVjdHEAfgAHc3EAfgAPdAAMU2VyaWFsaXphYmxlcQB+AAdxAH4ACXhxAH4AB3NyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzQXJncyfr7mTFMABcAgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZwVz2NYL1Ck/AgAIWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnWgAHdmFsRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGV0ACBMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcGVFeHByO3hwAAAAAXEAfgAHc3IAC3NjYWxhLk5vbmUkRlAk9lPKlKwCAAB4cgAMc2NhbGEuT3B0aW9u/mk3/dsOZnQCAAB4cHQABHJvd3NzcQB+AA90AANDb2xzcQB+AAZzcQB+AA90AANWZWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+ABwAAAABcQB+AAdxAH4AIXQACm51bUNvbHVtbnNzcgAjc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVQcmltaXRpdmU1w7tV8L5OBQIAAkwAEmRlZmF1bHRWYWx1ZVN0cmluZ3EAfgAETAAEbmFtZXEAfgAEeHB0AAEwdAADSW50cQB+AAl4c3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kRGVm3VlxcbcwKw4CAApaAAxpc0VsZW1PckNvbnRaAAppc0ltcGxpY2l0WgAKaXNPdmVycmlkZUwAC2Fubm90YXRpb25zcQB+AAFMAAthcmdTZWN0aW9uc3EAfgABTAAEYm9keXEAfgADTAAEbmFtZXEAfgAETAAKb3ZlcmxvYWRJZHEAfgADTAAHdHBlQXJnc3EAfgABTAAGdHBlUmVzcQB+AAN4cAAAAHEAfgAHcQB+AAdzcgAKc2NhbGEuU29tZREi8mleoYt0AgABTAABeHQAEkxqYXZhL2xhbmcvT2JqZWN0O3hxAH4AIHNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1NlbGVjdAvdaWVlQPmFAgACTAAEZXhwcnQAHUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU0V4cHI7TAAFdG5hbWVxAH4ABHhwc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTSWRlbnQnEqX78AnXTwIAAUwABG5hbWVxAH4ABHhwdAAEcm93c3QABmxlbmd0aHQAB251bVJvd3NxAH4AIXEAfgAHcQB+ACFzcQB+ADIAAABxAH4AB3NxAH4ABnNyACFzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZ3MpwieWXnW6iwIAAUwABGFyZ3NxAH4AAXhwc3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kQXJnA9z/y5NycwMCAAdaAAdpbXBGbGFnWgAMaXNFbGVtT3JDb250WgAIb3ZlckZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBlcQB+AB14cAEAAHEAfgAHcQB+ACF0AAFuc3EAfgAPdAADTnVtc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4c3EAfgA0c3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQXBwbHmEZimf0pr8fwIAA0wABWFyZ3NzcQB+AAFMAANmdW5xAH4AOEwAAnRzcQB+AAF4cHNxAH4ABnNxAH4ABnNxAH4ATXNxAH4ABnNxAH4ABnNyABtzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0Z1bmOwTPiQGsUwgwIAAkwABnBhcmFtc3EAfgABTAADcmVzcQB+ADh4cHNxAH4ABnNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1ZhbERlZisGObp73UUPAgAFWgAKaXNJbXBsaWNpdFoABmlzTGF6eUwABGV4cHJxAH4AOEwABG5hbWVxAH4ABEwAA3RwZXEAfgADeHAAAHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VtcHR5kWAV4UvlaL4CAAB4cHQAAWpzcQB+ADRxAH4ALnEAfgAJeHNyABtzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0FzY3JgxHknhZI+zAIAAkwABGV4cHJxAH4AOEwAAnB0cQB+AB14cHNxAH4ATXNxAH4ABnNxAH4ABnNxAH4ATXNxAH4ABnNxAH4ABnNxAH4AVHNxAH4ABnNxAH4AVwAAc3EAfgBZdAADdmVjc3EAfgA0c3EAfgAPdAADVmVjc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4c3EAfgBNc3EAfgAGc3EAfgAGc3EAfgA6dAABanEAfgAJeHEAfgAJeHNxAH4AOnQAA3ZlY3EAfgAHcQB+AAl4cQB+AAl4c3EAfgA3c3EAfgA6dAAEcm93c3QAA21hcHEAfgAHcQB+AAl4cQB+AAl4c3EAfgA6dAAIRGVuc2VWZWNxAH4AB3NxAH4AD3QAA1ZlY3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgAJeHNxAH4AN3NxAH4AXXNxAH4ATXNxAH4ABnNxAH4ABnNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NvbnN0Gk0U0WJLZ4oCAAFMAAFjcQB+ADV4cHNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAAAc3EAfgA6dAAKbnVtQ29sdW1uc3NxAH4Ah3NxAH4AiQAAAAFxAH4ACXhxAH4ACXhzcQB+ADdzcQB+ADp0AAVBcnJheXQABXJhbmdlcQB+AAdzcQB+AA90AAVBcnJheXNxAH4ABnEAfgAucQB+AAl4cQB+AHpxAH4AB3EAfgAJeHEAfgAJeHNxAH4AOnQAA0NvbHEAfgAHdAAHY29sdW1uc3EAfgAhcQB+AAdzcQB+ADRzcQB+AA90AANDb2xzcQB+AAZzcQB+AA90AANWZWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4AIXNxAH4AGXNxAH4ABnNxAH4AHAEAAAFxAH4AB3EAfgAhdAADY3RUc3EAfgAPdAAIQ2xhc3NUYWdzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXh0AAlEZW5zZU1hdHJxAH4AIXNxAH4ABnNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZUFyZxZSTBRs7T++AgAFSgAFZmxhZ3NMAAVib3VuZHEAfgADTAAMY29udGV4dEJvdW5kcQB+AAFMAARuYW1lcQB+AARMAAd0cGFyYW1zcQB+AAF4cAAAAAAAACAAcQB+ACFxAH4AB3QAAVRxAH4AB3EAfgAJeHEAfgAJeHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0RGVm9VowAvQZ158CAAlaAAhiaXRtYXAkMEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+AAxMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAcQB+AAdxAH4AB3NxAH4ABnNxAH4AMgABAHEAfgAHcQB+AAdxAH4AIXQAA2N0VHEAfgAhcQB+AAdzcQB+ADRzcQB+AA90AAhDbGFzc1RhZ3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHNxAH4AMgAAAHEAfgAHcQB+AAdxAH4AIXQACm51bUNvbHVtbnNxAH4AIXEAfgAHc3EAfgA0cQB+AC5zcQB+ADIAAABxAH4AB3EAfgAHcQB+ACF0AAdudW1Sb3dzcQB+ACFxAH4AB3NxAH4ANHEAfgAuc3EAfgAyAAAAcQB+AAdxAH4AB3EAfgAhdAAEcm93c3EAfgAhcQB+AAdzcQB+ADRzcQB+AA90AANDb2xzcQB+AAZzcQB+AA90AANWZWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+ADIAAABxAH4AB3NxAH4ABnNxAH4AQXNxAH4ABnNxAH4ARAEAAHEAfgAHcQB+ACF0AAFuc3EAfgAPdAADTnVtc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4cQB+ACF0AAdjb2x1bW5zcQB+ACFxAH4AB3NxAH4ANHNxAH4AD3QAA0NvbHNxAH4ABnNxAH4AD3QAA1ZlY3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgAJeHEAfgAhcHQABE1hdHJxAH4AIXNxAH4ABnNxAH4ArgAAAAAAACAAcQB+ACFxAH4AB3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgCzcQB+ACFzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJbXBvcnRTdGF0rICgSsPGGyICAAFMAARuYW1lcQB+AAR4cHQAFnNjYWxhLnJlZmxlY3QuQ2xhc3NUYWdxAH4ACXhxAH4AB3QABU1hdHJzdAAVc2NhbGFuaXplci5saW5hbGdlYnJhc3EAfgA0c3IAInNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTU2VsZlR5cGVEZWZ+951OKs6z5QIAAkwACmNvbXBvbmVudHNxAH4AAUwABG5hbWVxAH4ABHhwc3EAfgAGc3EAfgAPdAANTGluZWFyQWxnZWJyYXEAfgAHcQB+AAl4dAAEc2VsZnEAfgAh"
    }

    object HotSpotKernels {
      import java.io.File;
      import scalan.compilation.GraphVizConfig
    }

    object HotSpotManager {
      import scalan.ScalanCommunityDslExp;
      import scalan.compilation.lms.scalac.CommunityLmsCompilerScala;
      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      import scalan.{CommunityMethodMappingDSL, JNIExtractorOpsExp}
      import scalan.compilation.lms.CommunityBridge

      lazy val moduleExp = new LinearAlgebraDslExp with ScalanCommunityDslExp with JNIExtractorOpsExp

      lazy val compiler = new CommunityLmsCompilerScala(moduleExp) with CommunityBridge with CommunityMethodMappingDSL;
      def getScalanContext = compiler;

      import scalan.compilation.lms.uni.LmsCompilerUni;
      lazy val compilerUni = new LmsCompilerUni(moduleExp) with CommunityBridge with CommunityMethodMappingDSL;
      def getScalanContextUni = compilerUni;
    }
  }
}