package scalanizer.linalgebra {
  package implOfVecs {
    object StagedEvaluation {
      import scalan._;
      import scalanizer.implOfNumMonoids.StagedEvaluation._;
      import scalanizer.collections.implOfCols.StagedEvaluation._;
      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait VecsAbs extends Vecs with ScalanDsl { self: LinearAlgebraDsl =>
        implicit def proxyVec[T](p: Rep[Vec[T]]): Vec[T] = proxyOps[Vec[T]](p)(scala.reflect.classTag[Vec[T]]);
        class VecElem[T, To <: Vec[T]](implicit val eeT: Elem[T]) extends EntityElem[To] {
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagT = eeT.tag;
            weakTypeTag[Vec[T]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$1] forSome { 
            type _$1
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
        def unmkDenseVec[T](p: Rep[Vec[T]]): Option[Rep[Col[T]]]
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
            def unapply(d: (Def[_$2] forSome { 
              type _$2
            })): Option[(Rep[DenseVec[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(DenseVecElem[_$3] forSome { 
  type _$3
})].&&(__equal(method.getName, "length")) => Some(receiver).asInstanceOf[Option[(Rep[DenseVec[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$4] forSome { 
              type _$4
            })): Option[(Rep[DenseVec[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$5] forSome { 
              type _$5
            })): Option[(scala.Tuple2[Rep[DenseVec[T]], Rep[Int]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(DenseVecElem[_$6] forSome { 
  type _$6
})].&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[DenseVec[T]], Rep[Int]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$7] forSome { 
              type _$7
            })): Option[(scala.Tuple2[Rep[DenseVec[T]], Rep[Int]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object dot {
            def unapply(d: (Def[_$8] forSome { 
              type _$8
            })): Option[(scala.Tuple4[Rep[DenseVec[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((other @ _), (n @ _), (m @ _), _*), _) if receiver.elem.isInstanceOf[(DenseVecElem[_$9] forSome { 
  type _$9
})].&&(__equal(method.getName, "dot")) => Some(scala.Tuple4(receiver, other, n, m)).asInstanceOf[Option[(scala.Tuple4[Rep[DenseVec[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$10] forSome { 
              type _$10
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
        def unmkDenseVec[T](p: Rep[Vec[T]]) = p.elem.asInstanceOf[(Elem[_$11] forSome { 
          type _$11
        })] match {
          case ((_): DenseVecElem[T] @unchecked) => Some(p.asRep[DenseVec[T]].items)
          case _ => None
        };
        object VecMethods {
          object length {
            def unapply(d: (Def[_$12] forSome { 
              type _$12
            })): Option[(Rep[Vec[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(VecElem[_$13, _$14] forSome { 
  type _$13;
  type _$14
})].&&(__equal(method.getName, "length")) => Some(receiver).asInstanceOf[Option[(Rep[Vec[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$15] forSome { 
              type _$15
            })): Option[(Rep[Vec[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object items {
            def unapply(d: (Def[_$16] forSome { 
              type _$16
            })): Option[(Rep[Vec[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(VecElem[_$17, _$18] forSome { 
  type _$17;
  type _$18
})].&&(__equal(method.getName, "items")) => Some(receiver).asInstanceOf[Option[(Rep[Vec[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$19] forSome { 
              type _$19
            })): Option[(Rep[Vec[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$20] forSome { 
              type _$20
            })): Option[(scala.Tuple2[Rep[Vec[T]], Rep[Int]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(VecElem[_$21, _$22] forSome { 
  type _$21;
  type _$22
})].&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[Vec[T]], Rep[Int]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$23] forSome { 
              type _$23
            })): Option[(scala.Tuple2[Rep[Vec[T]], Rep[Int]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object dot {
            def unapply(d: (Def[_$24] forSome { 
              type _$24
            })): Option[(scala.Tuple4[Rep[Vec[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((other @ _), (n @ _), (m @ _), _*), _) if receiver.elem.isInstanceOf[(VecElem[_$25, _$26] forSome { 
  type _$25;
  type _$26
})].&&(__equal(method.getName, "dot")) => Some(scala.Tuple4(receiver, other, n, m)).asInstanceOf[Option[(scala.Tuple4[Rep[Vec[T]], Rep[Vec[T]], Rep[Num[T]], Rep[NumMonoid[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$27] forSome { 
              type _$27
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
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AANWZWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhzcQB+AA90AAdQcm9kdWN0cQB+AAdzcQB+AA90AAxTZXJpYWxpemFibGVxAH4AB3EAfgAJeHEAfgAHc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NBcmdzJ+vuZMUwAFwCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzQXJnBXPY1gvUKT8CAAhaAAdpbXBGbGFnWgAMaXNFbGVtT3JDb250WgAIb3ZlckZsYWdaAAd2YWxGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXQAIExzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RwZUV4cHI7eHAAAAABcQB+AAdzcgALc2NhbGEuTm9uZSRGUCT2U8qUrAIAAHhyAAxzY2FsYS5PcHRpb27+aTf92w5mdAIAAHhwdAAFaXRlbXNzcQB+AA90AANDb2xzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2REZWbdWXFxtzArDgIACloADGlzRWxlbU9yQ29udFoACmlzSW1wbGljaXRaAAppc092ZXJyaWRlTAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0cGVSZXNxAH4AA3hwAAAAcQB+AAdxAH4AB3NyAApzY2FsYS5Tb21lESLyaV6hi3QCAAFMAAF4dAASTGphdmEvbGFuZy9PYmplY3Q7eHEAfgAgc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTU2VsZWN0C91pZWVA+YUCAAJMAARleHBydAAdTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTRXhwcjtMAAV0bmFtZXEAfgAEeHBzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNJZGVudCcSpfvwCddPAgABTAAEbmFtZXEAfgAEeHB0AAVpdGVtc3QABmxlbmd0aHEAfgA0cQB+ACFxAH4AB3EAfgAhc3EAfgApAAAAcQB+AAdzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2RBcmdzKcInll51uosCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZwPc/8uTcnMDAgAHWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXEAfgAdeHAAAABxAH4AB3EAfgAhdAABaXNyACNzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZVByaW1pdGl2ZTXDu1Xwvk4FAgACTAASZGVmYXVsdFZhbHVlU3RyaW5ncQB+AARMAARuYW1lcQB+AAR4cHQAATB0AANJbnRxAH4ACXhxAH4ACXhzcQB+ACtzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNBcHBseYRmKZ/Smvx/AgADTAAFYXJnc3NxAH4AAUwAA2Z1bnEAfgAvTAACdHNxAH4AAXhwc3EAfgAGc3EAfgAGc3EAfgAxdAABaXEAfgAJeHEAfgAJeHNxAH4AMXQABWl0ZW1zcQB+AAd0AAVhcHBseXEAfgAhcQB+AAdzcQB+ACtzcQB+AA90AAFUcQB+AAdzcQB+ACkAAABxAH4AB3NxAH4ABnNxAH4AN3NxAH4ABnNxAH4AOgAAAHEAfgAHcQB+ACF0AAFmc3IAHnNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlRnVuY9RJmRMPHV/hAgACTAAGZG9tYWlucQB+AB1MAAVyYW5nZXEAfgAdeHBzcQB+AA90AAFUcQB+AAdzcQB+AA90AAFScQB+AAdxAH4ACXhxAH4ACXhzcQB+ACtzcQB+AEJzcQB+AAZzcQB+AAZzcQB+AEJzcQB+AAZzcQB+AAZzcQB+ADFxAH4AU3EAfgAJeHEAfgAJeHNxAH4ALnNxAH4AMXQABWl0ZW1zdAADbWFwcQB+AAdxAH4ACXhxAH4ACXhzcQB+ADF0AAhEZW5zZVZlY3EAfgAHcQB+AGVxAH4AIXNxAH4ABnNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZUFyZ5JbJkhy8ZuJAgAETAAFYm91bmRxAH4AA0wADGNvbnRleHRCb3VuZHEAfgABTAAEbmFtZXEAfgAETAAHdHBhcmFtc3EAfgABeHBxAH4AIXNxAH4ABnQACENsYXNzVGFncQB+AAl4dAABUnEAfgAHcQB+AAl4c3EAfgArc3EAfgAPdAADVmVjc3EAfgAGc3EAfgAPdAABUnEAfgAHcQB+AAl4c3EAfgApAAAAcQB+AAdzcQB+AAZzcQB+ADdzcQB+AAZzcQB+ADoAAABxAH4AB3EAfgAhdAAFb3RoZXJzcQB+AA90AANWZWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+ADdzcQB+AAZzcQB+ADoBAABxAH4AB3EAfgAhdAABbnNxAH4AD3QAA051bXNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHNxAH4AOgEAAHEAfgAHcQB+ACF0AAFtc3EAfgAPdAAJTnVtTW9ub2lkc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4c3EAfgArc3EAfgBCc3EAfgAGc3EAfgAGc3EAfgAxdAABbXEAfgAJeHEAfgAJeHNxAH4ALnNxAH4AQnNxAH4ABnNxAH4ABnNyABtzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0Z1bmOwTPiQGsUwgwIAAkwABnBhcmFtc3EAfgABTAADcmVzcQB+AC94cHNxAH4ABnNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1ZhbERlZisGObp73UUPAgAFWgAKaXNJbXBsaWNpdFoABmlzTGF6eUwABGV4cHJxAH4AL0wABG5hbWVxAH4ABEwAA3RwZXEAfgADeHAAAHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VtcHR5kWAV4UvlaL4CAAB4cHQAAXZzcQB+ACtzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVUdXBsZTS7+V6IgEhwAgABTAAJdHBlU0V4cHJzcQB+AAF4cHNxAH4ABnNxAH4AD3QAAVRxAH4AB3NxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHNxAH4AQnNxAH4ABnNxAH4ABnNxAH4ALnNxAH4AMXQAAXZ0AAJfMXNxAH4ALnNxAH4AMXQAAXZ0AAJfMnEAfgAJeHEAfgAJeHNxAH4ALnNxAH4AMXQAAW50AAV0aW1lc3EAfgAHcQB+AAl4cQB+AAl4c3EAfgAuc3EAfgBCc3EAfgAGc3EAfgAGc3EAfgAxdAAFaXRlbXNxAH4ACXhxAH4ACXhzcQB+AC5zcQB+AC5zcQB+ADF0AAVvdGhlcnQABWl0ZW1zdAADemlwcQB+AAdxAH4AZXEAfgAHdAAGcmVkdWNlcQB+AAd0AANkb3RxAH4AIXEAfgAHc3EAfgArc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+ACFzcQB+ABlzcQB+AAZzcQB+ABwBAAABcQB+AAdxAH4AIXQAA2N0VHNxAH4AD3QACENsYXNzVGFnc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4dAAIRGVuc2VWZWNxAH4AIXNxAH4ABnNxAH4AaXEAfgAhcQB+AAd0AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdERlZgG+NJI0FLGyAgAJWgAIYml0bWFwJDBMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAMTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgAHcQB+AAdzcQB+AAZzcQB+ACkAAABxAH4AB3EAfgAHcQB+ACFxAH4ANHEAfgAhcQB+AAdzcQB+ACtxAH4APnNxAH4AKQAAAHEAfgAHcQB+AAdxAH4AIXQABWl0ZW1zcQB+ACFxAH4AB3NxAH4AK3NxAH4AD3QAA0NvbHNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHNxAH4AKQAAAHEAfgAHc3EAfgAGc3EAfgA3c3EAfgAGc3EAfgA6AAAAcQB+AAdxAH4AIXQAAWlxAH4APnEAfgAJeHEAfgAJeHEAfgAhcQB+AEpxAH4AIXEAfgAHc3EAfgArc3EAfgAPdAABVHEAfgAHc3EAfgApAAAAcQB+AAdzcQB+AAZzcQB+ADdzcQB+AAZzcQB+ADoAAABxAH4AB3EAfgAhcQB+AFNzcQB+AFRzcQB+AA90AAFUcQB+AAdzcQB+AA90AAFScQB+AAdxAH4ACXhxAH4ACXhxAH4AIXEAfgBlcQB+ACFzcQB+AAZzcQB+AGlxAH4AIXNxAH4ABnQACENsYXNzVGFncQB+AAl4dAABUnEAfgAHcQB+AAl4c3EAfgArc3EAfgAPdAADVmVjc3EAfgAGc3EAfgAPdAABUnEAfgAHcQB+AAl4c3EAfgApAAAAcQB+AAdzcQB+AAZzcQB+ADdzcQB+AAZzcQB+ADoAAABxAH4AB3EAfgAhdAAFb3RoZXJzcQB+AA90AANWZWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+ADdzcQB+AAZzcQB+ADoBAABxAH4AB3EAfgAhdAABbnNxAH4AD3QAA051bXNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHNxAH4AOgEAAHEAfgAHcQB+ACF0AAFtc3EAfgAPdAAJTnVtTW9ub2lkc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+AAl4cQB+ACF0AANkb3RxAH4AIXEAfgAHc3EAfgArc3EAfgAPdAABVHEAfgAHcQB+AAl4cQB+ACFwdAADVmVjcQB+ACFzcQB+AAZzcQB+AGlxAH4AIXEAfgAHdAABVHEAfgAHcQB+AAl4cQB+AAl4cQB+ANhxAH4AIXNxAH4ABnNyACFzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0ltcG9ydFN0YXSsgKBKw8YbIgIAAUwABG5hbWVxAH4ABHhwdAAWc2NhbGEucmVmbGVjdC5DbGFzc1RhZ3EAfgAJeHEAfgAHdAAEVmVjc3QAFXNjYWxhbml6ZXIubGluYWxnZWJyYXNxAH4AK3NyACJzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1NlbGZUeXBlRGVmfvedTirOs+UCAAJMAApjb21wb25lbnRzcQB+AAFMAARuYW1lcQB+AAR4cHNxAH4ABnNxAH4AD3QADUxpbmVhckFsZ2VicmFxAH4AB3EAfgAJeHQABHNlbGZxAH4AIQ=="
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
      class ScalanUni extends LinearAlgebraDslExp with LmsCompilerUni with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler with CommunityMethodMappingDSL
    }
  }
}