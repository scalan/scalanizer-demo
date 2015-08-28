package scalanizer.linalgebra {
  package implOfVecs {
    object StagedEvaluation {
      import scalan._;
      import scalan.meta.ScalanAst._;
      import scalanizer.implOfNumMonoids.StagedEvaluation._;
      import scalanizer.collections.implOfCols.StagedEvaluation._;
      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait VecsAbs extends Vecs with ScalanDsl { self: LinearAlgebraDsl =>
        implicit def proxyVec[T](p: Rep[Vec[T]]): Vec[T] = proxyOps[Vec[T]](p)(scala.reflect.classTag[Vec[T]]);
        class VecElem[T, To <: Vec[T]](implicit val eeT: Elem[T]) extends EntityElem[To] {
          lazy val parent: Option[(Elem[_$1] forSome { 
            type _$1
          })] = None;
          lazy val entityDef: STraitOrClassDef = {
            val module = getModules("Vecs");
            module.entities.find(((x$1) => __equal(x$1.name, "Vec"))).get
          };
          lazy val tyArgSubst: Map[String, TypeDesc] = Map("T".->(Left(eeT)));
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagT = eeT.tag;
            weakTypeTag[Vec[T]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$2] forSome { 
            type _$2
          })]) = {
            implicit val eTo: Elem[To] = this;
            val conv = fun(((x: Rep[Vec[T]]) => convertVec(x)));
            tryConvert(element[Vec[T]], this, x, conv)
          };
          def convertVec(x: Rep[Vec[T]]): Rep[To] = {
            assert(x.selfType1 match {
              case ((_): VecElem[(_), (_)]) => true
              case _ => false
            });
            x.asRep[To]
          };
          override def getDefaultRep: Rep[To] = ???
        };
        implicit def vecElement[T](implicit eeT: Elem[T]): Elem[Vec[T]] = new VecElem[T, Vec[T]]();
        implicit case object VecCompanionElem extends CompanionElem[VecCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[VecCompanionAbs];
          protected def getDefaultRep = Vec
        };
        abstract class VecCompanionAbs extends CompanionBase[VecCompanionAbs] with VecCompanion {
          override def toString = "Vec"
        };
        def Vec: Rep[VecCompanionAbs];
        implicit def proxyVecCompanion(p: Rep[VecCompanion]): VecCompanion = proxyOps[VecCompanion](p);
        class DenseVecElem[T](val iso: Iso[DenseVecData[T], DenseVec[T]])(implicit eeT: Elem[T]) extends VecElem[T, DenseVec[T]] with ConcreteElem[DenseVecData[T], DenseVec[T]] {
          override lazy val parent: Option[(Elem[_$3] forSome { 
            type _$3
          })] = Some(vecElement(element[T]));
          override lazy val entityDef = {
            val module = getModules("Vecs");
            module.concreteSClasses.find(((x$2) => __equal(x$2.name, "DenseVec"))).get
          };
          override lazy val tyArgSubst: Map[String, TypeDesc] = Map("T".->(Left(eeT)));
          override def convertVec(x: Rep[Vec[T]]) = DenseVec(x.items);
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = {
            implicit val tagT = eeT.tag;
            weakTypeTag[DenseVec[T]]
          }
        };
        type DenseVecData[T] = Col[T];
        class DenseVecIso[T](implicit eeT: Elem[T]) extends Iso[DenseVecData[T], DenseVec[T]] {
          override def from(p: Rep[DenseVec[T]]) = p.items;
          override def to(p: Rep[Col[T]]) = {
            val items = p;
            DenseVec(items)
          };
          lazy val defaultRepTo: Rep[DenseVec[T]] = DenseVec(element[Col[T]].defaultRepValue);
          lazy val eTo = new DenseVecElem[T](this)
        };
        abstract class DenseVecCompanionAbs extends CompanionBase[DenseVecCompanionAbs] with DenseVecCompanion {
          override def toString = "DenseVec";
          def apply[T](items: Rep[Col[T]])(implicit eeT: Elem[T]): Rep[DenseVec[T]] = mkDenseVec(items)
        };
        object DenseVecMatcher {
          def unapply[T](p: Rep[Vec[T]]) = unmkDenseVec(p)
        };
        def DenseVec: Rep[DenseVecCompanionAbs];
        implicit def proxyDenseVecCompanion(p: Rep[DenseVecCompanionAbs]): DenseVecCompanionAbs = proxyOps[DenseVecCompanionAbs](p);
        implicit case object DenseVecCompanionElem extends CompanionElem[DenseVecCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[DenseVecCompanionAbs];
          protected def getDefaultRep = DenseVec
        };
        implicit def proxyDenseVec[T](p: Rep[DenseVec[T]]): DenseVec[T] = proxyOps[DenseVec[T]](p);
        implicit class ExtendedDenseVec[T](p: Rep[DenseVec[T]])(implicit eeT: Elem[T]) {
          def toData: Rep[DenseVecData[T]] = isoDenseVec(eeT).from(p)
        };
        implicit def isoDenseVec[T](implicit eeT: Elem[T]): Iso[DenseVecData[T], DenseVec[T]] = new DenseVecIso[T]();
        def mkDenseVec[T](items: Rep[Col[T]])(implicit eeT: Elem[T]): Rep[DenseVec[T]];
        def unmkDenseVec[T](p: Rep[Vec[T]]): Option[Rep[Col[T]]];
        registerModule(scalan.meta.ScalanCodegen.loadModule(Vecs_Module.dump))
      };
      trait VecsSeq extends VecsDsl with ScalanSeq { self: LinearAlgebraDslSeq =>
        lazy val Vec: Rep[VecCompanionAbs] = {
          final class $anon extends VecCompanionAbs with UserTypeSeq[VecCompanionAbs] {
            lazy val selfType = element[VecCompanionAbs]
          };
          new $anon()
        };
        case class SeqDenseVec[T](override val items: Rep[Col[T]])(implicit eeT: Elem[T]) extends DenseVec[T](items) with UserTypeSeq[DenseVec[T]] {
          lazy val selfType = element[DenseVec[T]]
        };
        lazy val DenseVec = {
          final class $anon extends DenseVecCompanionAbs with UserTypeSeq[DenseVecCompanionAbs] {
            lazy val selfType = element[DenseVecCompanionAbs]
          };
          new $anon()
        };
        def mkDenseVec[T](items: Rep[Col[T]])(implicit eeT: Elem[T]): Rep[DenseVec[T]] = new SeqDenseVec[T](items);
        def unmkDenseVec[T](p: Rep[Vec[T]]) = p match {
          case (p @ ((_): DenseVec[T] @unchecked)) => Some(p.items)
          case _ => None
        }
      };
      trait VecsExp extends VecsDsl with ScalanExp { self: LinearAlgebraDslExp =>
        lazy val Vec: Rep[VecCompanionAbs] = {
          final class $anon extends VecCompanionAbs with UserTypeDef[VecCompanionAbs] {
            lazy val selfType = element[VecCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        case class ExpDenseVec[T](override val items: Rep[Col[T]])(implicit eeT: Elem[T]) extends DenseVec[T](items) with UserTypeDef[DenseVec[T]] {
          lazy val selfType = element[DenseVec[T]];
          override def mirror(t: Transformer) = ExpDenseVec[T](t(items))
        };
        lazy val DenseVec: Rep[DenseVecCompanionAbs] = {
          final class $anon extends DenseVecCompanionAbs with UserTypeDef[DenseVecCompanionAbs] {
            lazy val selfType = element[DenseVecCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object DenseVecMethods {
          object length {
            def unapply(d: (Def[_$4] forSome { 
              type _$4
            })): Option[(Rep[DenseVec[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(DenseVecElem[_$5] forSome { 
  type _$5
})].&&(__equal(method.getName, "length")) => Some(receiver).asInstanceOf[Option[(Rep[DenseVec[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$6] forSome { 
              type _$6
            })): Option[(Rep[DenseVec[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$7] forSome { 
              type _$7
            })): Option[(scala.Tuple2[Rep[DenseVec[T]], Rep[Int]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(DenseVecElem[_$8] forSome { 
  type _$8
})].&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[DenseVec[T]], Rep[Int]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$9] forSome { 
              type _$9
            })): Option[(scala.Tuple2[Rep[DenseVec[T]], Rep[Int]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object dot {
            def unapply(d: (Def[_$10] forSome { 
              type _$10
            })): Option[(scala.Tuple4[Rep[DenseVec[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((other @ _), (n @ _), (m @ _), _*), _) if receiver.elem.isInstanceOf[(DenseVecElem[_$11] forSome { 
  type _$11
})].&&(__equal(method.getName, "dot")) => Some(scala.Tuple4(receiver, other, n, m)).asInstanceOf[Option[(scala.Tuple4[Rep[DenseVec[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$12] forSome { 
              type _$12
            })): Option[(scala.Tuple4[Rep[DenseVec[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object DenseVecCompanionMethods;
        def mkDenseVec[T](items: Rep[Col[T]])(implicit eeT: Elem[T]): Rep[DenseVec[T]] = new ExpDenseVec[T](items);
        def unmkDenseVec[T](p: Rep[Vec[T]]) = p.elem.asInstanceOf[(Elem[_$13] forSome { 
          type _$13
        })] match {
          case ((_): DenseVecElem[T] @unchecked) => Some(p.asRep[DenseVec[T]].items)
          case _ => None
        };
        object VecMethods {
          object length {
            def unapply(d: (Def[_$14] forSome { 
              type _$14
            })): Option[(Rep[Vec[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(VecElem[_$15, _$16] forSome { 
  type _$15;
  type _$16
})].&&(__equal(method.getName, "length")) => Some(receiver).asInstanceOf[Option[(Rep[Vec[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$17] forSome { 
              type _$17
            })): Option[(Rep[Vec[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object items {
            def unapply(d: (Def[_$18] forSome { 
              type _$18
            })): Option[(Rep[Vec[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(VecElem[_$19, _$20] forSome { 
  type _$19;
  type _$20
})].&&(__equal(method.getName, "items")) => Some(receiver).asInstanceOf[Option[(Rep[Vec[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$21] forSome { 
              type _$21
            })): Option[(Rep[Vec[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$22] forSome { 
              type _$22
            })): Option[(scala.Tuple2[Rep[Vec[T]], Rep[Int]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(VecElem[_$23, _$24] forSome { 
  type _$23;
  type _$24
})].&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[Vec[T]], Rep[Int]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$25] forSome { 
              type _$25
            })): Option[(scala.Tuple2[Rep[Vec[T]], Rep[Int]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object dot {
            def unapply(d: (Def[_$26] forSome { 
              type _$26
            })): Option[(scala.Tuple4[Rep[Vec[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((other @ _), (n @ _), (m @ _), _*), _) if receiver.elem.isInstanceOf[(VecElem[_$27, _$28] forSome { 
  type _$27;
  type _$28
})].&&(__equal(method.getName, "dot")) => Some(scala.Tuple4(receiver, other, n, m)).asInstanceOf[Option[(scala.Tuple4[Rep[Vec[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$29] forSome { 
              type _$29
            })): Option[(scala.Tuple4[Rep[Vec[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object VecCompanionMethods
      };
      object Vecs_Module {
        val packageName = "scalanizer.linalgebra";
        val name = "Vecs";
        val dump = "H4sIAAAAAAAAALWVPYwbRRTHn9d2fP5QcuEj0kkg7k4Gwpd9orniisjxOQi0uTvdnhAyEdJ4PXYmzM7uzYxPNkUKSugQLULp09FQ0SEhCioESNRUIRQRkArEm9kPr5NzkoYtRruzb9578/u/N3P7LpSVhJeUTzgRrYBq0vLse0fpptcTmunZ1XA44XSXjj6+8LV/VVxWDpzrw5nrRO0q3odq/NKbRtm7R49dqBLhU6VDqTRsuDZC2w85p75moWizIJhoMuC07TKld1woDcLh7BhuQsGFVT8UvqSael1OlKIqmV+hJiOWfVft92w/mscQbbOLdm4XR5IwjeljjNXY/pBG3kyEYhZoOJukth+ZtNCmwoIolDoNUUF318Nh+lkSBCfgKfcGOSFtDDFue1oyMcaV9Yj4H5Ix3UMTY17ChBXlo6NZZL+LLtQUPUZAbwcRtzPTCABQgTdtEq05n1bGp2X4ND0qGeHsI2J+HshwOoP4KRQBphG6eP0xLlIPtCeGzU+u+e/f9+qBYxZPTSoVu8Mz6OiFJdVgpUCO3x1+pu69dWvbgVofakx1BkpL4uu85AmtOhEi1DbnDCCRY1Rrc5laNkoHbR4oiaofBhER6ClB2UCdOPOZNsZmrpGoswR9RUc0NS0g92y/60v2a+umSzg/uLP2xou/995zwFkMUUWXHha+TJ1qKL5L/cSzGc9pKBxZvGaoTtPJCko4nPh6Th4nG5nIqFluUWy0PNEM2ct3/hh+uwXXnAx0kteTaYsuyuqXn+o/vnLJgZW+7YQrnIz7yFr1OA32ZTcUug8r4QmV8Z/KCeHm7VStK0M6IhOuEwXy6IqITsP60p6NqOG6Y/ujkAKoxyW+FwravHLQ/Nv7/vPbpoIlNOI/cRP/y7b/+fXsSNvi1lBmmgYqhVzshvwRAi0Sr8VuvTCg5zfvsQ9ufaot28J08QjYH9zAntux69YfgTk9iv7qbzl/rv38pQNVpDlgOiBRc+sJG+j/bIqMxHzYQITnd6lQFEu7m4+3Ma/etRzS5woFmDvaQOSUHqWwS6aOYmMzPJ2nrmEljWNXZlX7/DKcNvkLh+4z/O6lbxwovwPlERajcqE8CCdimFLB60TTqb6czhUWqSAFIkmQUbDPOsz3eGqR5Lb8GiyWVPWQshEzbbw4/5jT4aESnAO+mMRZIlDjdG0iM3byMM24/fAGzbB7aiY7i0aIomQ2oeHZWBS8UGSLM0H4mA4kSZKVsLlEMy8pQOyCm/e/2Hv1h69+s+dqzZQyNrbIbt65QtkBlkBbdZmgRHbikHif5jLH/EyN26z/A6MqB/7fCAAA"
      };
      trait Vecs extends Base { self: LinearAlgebraDsl =>
        trait Vec[T] extends Reifiable[Vec[T]] {
          implicit def eeT: Elem[T];
          def length: Rep[Int];
          def items: Rep[Col[T]];
          def apply(i: Rep[Int]): Rep[T];
          def map[R](f: Rep[scala.Function1[T, R]])(implicit emR: Elem[R]): Rep[Vec[R]];
          def dot(other: Rep[Vec[T]])(implicit n: Rep[Num[T]], m: Rep[NumMonoid[T]]): Rep[T]
        };
        abstract class DenseVec[T](val items: Rep[Col[T]])(implicit val eeT: Elem[T]) extends Vec[T] with Product with Serializable {
          def length = items.length;
          def apply(i: Rep[Int]): Rep[T] = items(i);
          def map[R](f: Rep[scala.Function1[T, R]])(implicit emR: Elem[R]): Rep[Vec[R]] = DenseVec(items.map(f));
          def dot(other: Rep[Vec[T]])(implicit n: Rep[Num[T]], m: Rep[NumMonoid[T]]): Rep[T] = other.items.zip(items).map(fun(((v: Rep[scala.Tuple2[T, T]]) => n.times(v._1, v._2)))).reduce(m)
        };
        trait VecCompanion;
        trait DenseVecCompanion
      };
      trait VecsDsl extends VecsAbs { self: LinearAlgebraDsl =>
        
      };
      trait VecsDslSeq extends VecsSeq { self: LinearAlgebraDslSeq =>
        
      };
      trait VecsDslExp extends VecsExp { self: LinearAlgebraDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZoIWuGNNbkJzAgAPWgAGaGFzRHNsWgAJaGFzRHNsRXhwWgAJaGFzRHNsU2VxTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwAAAAc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZshSknPyR6E3AgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AANWZWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhzcQB+AA90AAdQcm9kdWN0cQB+AAdzcQB+AA90AAxTZXJpYWxpemFibGVxAH4AB3EAfgAJeHEAfgAHc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NBcmdzJ+vuZMUwAFwCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzQXJnBXPY1gvUKT8CAAhaAAdpbXBGbGFnWgAMaXNFbGVtT3JDb250WgAIb3ZlckZsYWdaAAd2YWxGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXQAIExzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RwZUV4cHI7eHAAAAABcQB+AAdzcgALc2NhbGEuTm9uZSRGUCT2U8qUrAIAAHhyAAxzY2FsYS5PcHRpb27+aTf92w5mdAIAAHhwdAAFaXRlbXNzcQB+AA90AANDb2xzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2REZWbdWXFxtzArDgIACloADGlzRWxlbU9yQ29udFoACmlzSW1wbGljaXRaAAppc092ZXJyaWRlTAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0cGVSZXNxAH4AA3hwAAAAcQB+AAdxAH4AB3NyAApzY2FsYS5Tb21lESLyaV6hi3QCAAFMAAF4dAASTGphdmEvbGFuZy9PYmplY3Q7eHEAfgAgc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTU2VsZWN0C91pZWVA+YUCAAJMAARleHBydAAdTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTRXhwcjtMAAV0bmFtZXEAfgAEeHBzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJZGVudCcSpfvwCddPAgABTAAEbmFtZXEAfgAEeHB0AAVpdGVtc3QABmxlbmd0aHEAfgA0cQB+ACFxAH4AB3EAfgAhc3EAfgApAAAAcQB+AAdzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2RBcmdzKcInll51uosCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZwPc/8uTcnMDAgAHWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXEAfgAdeHAAAABxAH4AB3EAfgAhdAABaXNyACNzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZVByaW1pdGl2ZTXDu1Xwvk4FAgACTAASZGVmYXVsdFZhbHVlU3RyaW5ncQB+AARMAARuYW1lcQB+AAR4cHQAATB0AANJbnRxAH4ACXhxAH4ACXhzcQB+ACtzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNBcHBseYRmKZ/Smvx/AgADTAAFYXJnc3NxAH4AAUwAA2Z1bnEAfgAvTAACdHNxAH4AAXhwc3EAfgAGc3EAfgAGc3EAfgAxdAABaXEAfgAJeHEAfgAJeHNxAH4AMXQABWl0ZW1zcQB+AAd0AAVhcHBseXEAfgAhcQB+AAdzcQB+ACtzcQB+AA90AAFUcQB+AAdzcQB+ACkAAABxAH4AB3NxAH4ABnNxAH4AN3NxAH4ABnNxAH4AOgAAAHEAfgAHcQB+ACF0AAFmc3IAHnNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlRnVuY9RJmRMPHV/hAgACTAAGZG9tYWlucQB+AB1MAAVyYW5nZXEAfgAdeHBzcQB+AA90AAFUcQB+AAdzcQB+AA90AAFScQB+AAdxAH4ACXhxAH4ACXhzcQB+ACtzcQB+AEJzcQB+AAZzcQB+AAZzcQB+AEJzcQB+AAZzcQB+AAZzcQB+ADFxAH4AU3EAfgAJeHEAfgAJeHNxAH4ALnNxAH4AMXQABWl0ZW1zdAADbWFwcQB+AAdxAH4ACXhxAH4ACXhzcQB+ADF0AAhEZW5zZVZlY3EAfgAHcQB+AGVxAH4AIXNxAH4ABnNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZUFyZxZSTBRs7T++AgAFSgAFZmxhZ3NMAAVib3VuZHEAfgADTAAMY29udGV4dEJvdW5kcQB+AAFMAARuYW1lcQB+AARMAAd0cGFyYW1zcQB+AAF4cAAAAAAAACAAcQB+ACFzcQB+AAZ0AAhDbGFzc1RhZ3EAfgAJeHQAAVJxAH4AB3EAfgAJeHNxAH4AK3NxAH4AD3QAA1ZlY3NxAH4ABnNxAH4AD3QAAVJxAH4AB3EAfgAJeHNxAH4AKQAAAHEAfgAHc3EAfgAGc3EAfgA3c3EAfgAGc3EAfgA6AAAAcQB+AAdxAH4AIXQABW90aGVyc3EAfgAPdAADVmVjc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4c3EAfgA3c3EAfgAGc3EAfgA6AQAAcQB+AAdxAH4AIXQAAW5zcQB+AA90AANOdW1zcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhzcQB+ADoBAABxAH4AB3EAfgAhdAABbXNxAH4AD3QACU51bU1vbm9pZHNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgAJeHNxAH4AK3NxAH4AQnNxAH4ABnNxAH4ABnNxAH4AMXQAAW1xAH4ACXhxAH4ACXhzcQB+AC5zcQB+AEJzcQB+AAZzcQB+AAZzcgAbc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNGdW5jsEz4kBrFMIMCAAJMAAZwYXJhbXNxAH4AAUwAA3Jlc3EAfgAveHBzcQB+AAZzcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNWYWxEZWYrBjm6e91FDwIABVoACmlzSW1wbGljaXRaAAZpc0xhenlMAARleHBycQB+AC9MAARuYW1lcQB+AARMAAN0cGVxAH4AA3hwAABzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNFbXB0eZFgFeFL5Wi+AgAAeHB0AAF2c3EAfgArc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlVHVwbGU0u/leiIBIcAIAAUwACXRwZVNFeHByc3EAfgABeHBzcQB+AAZzcQB+AA90AAFUcQB+AAdzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+AEJzcQB+AAZzcQB+AAZzcQB+AC5zcQB+ADF0AAF2dAACXzFzcQB+AC5zcQB+ADF0AAF2dAACXzJxAH4ACXhxAH4ACXhzcQB+AC5zcQB+ADF0AAFudAAFdGltZXNxAH4AB3EAfgAJeHEAfgAJeHNxAH4ALnNxAH4AQnNxAH4ABnNxAH4ABnNxAH4AMXQABWl0ZW1zcQB+AAl4cQB+AAl4c3EAfgAuc3EAfgAuc3EAfgAxdAAFb3RoZXJ0AAVpdGVtc3QAA3ppcHEAfgAHcQB+AGVxAH4AB3QABnJlZHVjZXEAfgAHdAADZG90cQB+ACFxAH4AB3NxAH4AK3NxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAhc3EAfgAZc3EAfgAGc3EAfgAcAQAAAXEAfgAHcQB+ACF0AANjdFRzcQB+AA90AAhDbGFzc1RhZ3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHQACERlbnNlVmVjcQB+ACFzcQB+AAZzcQB+AGkAAAAAAAAgAHEAfgAhcQB+AAd0AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdERlZvVaMAL0GdefAgAJWgAIYml0bWFwJDBMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAMTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgAHcQB+AAdzcQB+AAZzcQB+ACkAAABxAH4AB3EAfgAHcQB+ACFxAH4ANHEAfgAhcQB+AAdzcQB+ACtxAH4APnNxAH4AKQAAAHEAfgAHcQB+AAdxAH4AIXQABWl0ZW1zcQB+ACFxAH4AB3NxAH4AK3NxAH4AD3QAA0NvbHNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHNxAH4AKQAAAHEAfgAHc3EAfgAGc3EAfgA3c3EAfgAGc3EAfgA6AAAAcQB+AAdxAH4AIXQAAWlxAH4APnEAfgAJeHEAfgAJeHEAfgAhcQB+AEpxAH4AIXEAfgAHc3EAfgArc3EAfgAPdAABVHEAfgAHc3EAfgApAAAAcQB+AAdzcQB+AAZzcQB+ADdzcQB+AAZzcQB+ADoAAABxAH4AB3EAfgAhcQB+AFNzcQB+AFRzcQB+AA90AAFUcQB+AAdzcQB+AA90AAFScQB+AAdxAH4ACXhxAH4ACXhxAH4AIXEAfgBlcQB+ACFzcQB+AAZzcQB+AGkAAAAAAAAgAHEAfgAhc3EAfgAGdAAIQ2xhc3NUYWdxAH4ACXh0AAFScQB+AAdxAH4ACXhzcQB+ACtzcQB+AA90AANWZWNzcQB+AAZzcQB+AA90AAFScQB+AAdxAH4ACXhzcQB+ACkAAABxAH4AB3NxAH4ABnNxAH4AN3NxAH4ABnNxAH4AOgAAAHEAfgAHcQB+ACF0AAVvdGhlcnNxAH4AD3QAA1ZlY3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHNxAH4AN3NxAH4ABnNxAH4AOgEAAHEAfgAHcQB+ACF0AAFuc3EAfgAPdAADTnVtc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4c3EAfgA6AQAAcQB+AAdxAH4AIXQAAW1zcQB+AA90AAlOdW1Nb25vaWRzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4AIXQAA2RvdHEAfgAhcQB+AAdzcQB+ACtzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4AIXB0AANWZWNxAH4AIXNxAH4ABnNxAH4AaQAAAAAAACAAcQB+ACFxAH4AB3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgDYcQB+ACFzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJbXBvcnRTdGF0rICgSsPGGyICAAFMAARuYW1lcQB+AAR4cHQAFnNjYWxhLnJlZmxlY3QuQ2xhc3NUYWdxAH4ACXhxAH4AB3QABFZlY3N0ABVzY2FsYW5pemVyLmxpbmFsZ2VicmFzcQB+ACtzcgAic2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxmVHlwZURlZn73nU4qzrPlAgACTAAKY29tcG9uZW50c3EAfgABTAAEbmFtZXEAfgAEeHBzcQB+AAZzcQB+AA90AA1MaW5lYXJBbGdlYnJhcQB+AAdxAH4ACXh0AARzZWxmcQB+ACE="
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