//package scalanizer.collections {
//  package implOfCols {
//    object StagedEvaluation {
//      import scalan._;
//      import scalanizer.implOfNumMonoids.StagedEvaluation._;
//      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
//      import scala.reflect.runtime.universe._;
//      import scala.reflect._;
//      import scalan.common.Default;
//      trait ColsAbs extends Cols with ScalanDsl { self: LinearAlgebraDsl =>
//        implicit def proxyCol[A](p: Rep[Col[A]]): Col[A] = proxyOps[Col[A]](p)(scala.reflect.classTag[Col[A]]);
//        class ColElem[A, To <: Col[A]](implicit val eeA: Elem[A]) extends EntityElem[To] {
//          override def isEntityType = true;
//          override lazy val tag = {
//            implicit val tagA = eeA.tag;
//            weakTypeTag[Col[A]].asInstanceOf[WeakTypeTag[To]]
//          };
//          override def convert(x: Rep[(Reifiable[_$1] forSome {
//            type _$1
//          })]) = {
//            implicit val eTo: Elem[To] = this;
//            val conv = fun(((x: Rep[Col[A]]) => convertCol(x)));
//            tryConvert(element[Col[A]], this, x, conv)
//          };
//          def convertCol(x: Rep[Col[A]]): Rep[To] = {
//            assert(x.selfType1 match {
//              case ((_): ColElem[(_), (_)]) => true
//              case _ => false
//            });
//            x.asRep[To]
//          };
//          override def getDefaultRep: Rep[To] = ???
//        };
//        implicit def colElement[A](implicit eeA: Elem[A]): Elem[Col[A]] = new ColElem[A, Col[A]]();
//        implicit case object ColCompanionElem extends CompanionElem[ColCompanionAbs] with scala.Product with scala.Serializable {
//          lazy val tag = weakTypeTag[ColCompanionAbs];
//          protected def getDefaultRep = Col
//        };
//        abstract class ColCompanionAbs extends CompanionBase[ColCompanionAbs] with ColCompanion {
//          override def toString = "Col"
//        };
//        def Col: Rep[ColCompanionAbs];
//        implicit def proxyColCompanion(p: Rep[ColCompanion]): ColCompanion = proxyOps[ColCompanion](p);
//        class ColOverArrayElem[A](val iso: Iso[ColOverArrayData[A], ColOverArray[A]])(implicit eeA: Elem[A]) extends ColElem[A, ColOverArray[A]] with ConcreteElem[ColOverArrayData[A], ColOverArray[A]] {
//          override def convertCol(x: Rep[Col[A]]) = ColOverArray(x.arr);
//          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
//          override lazy val tag = {
//            implicit val tagA = eeA.tag;
//            weakTypeTag[ColOverArray[A]]
//          }
//        };
//        type ColOverArrayData[A] = WArray[A];
//        class ColOverArrayIso[A](implicit eeA: Elem[A]) extends Iso[ColOverArrayData[A], ColOverArray[A]] {
//          override def from(p: Rep[ColOverArray[A]]) = p.arr;
//          override def to(p: Rep[WArray[A]]) = {
//            val arr = p;
//            ColOverArray(arr)
//          };
//          lazy val defaultRepTo: Rep[ColOverArray[A]] = ColOverArray(element[WArray[A]].defaultRepValue);
//          lazy val eTo = new ColOverArrayElem[A](this)
//        };
//        abstract class ColOverArrayCompanionAbs extends CompanionBase[ColOverArrayCompanionAbs] with ColOverArrayCompanion {
//          override def toString = "ColOverArray";
//          def apply[A](arr: Rep[WArray[A]])(implicit eeA: Elem[A]): Rep[ColOverArray[A]] = mkColOverArray(arr)
//        };
//        object ColOverArrayMatcher {
//          def unapply[A](p: Rep[Col[A]]) = unmkColOverArray(p)
//        };
//        def ColOverArray: Rep[ColOverArrayCompanionAbs];
//        implicit def proxyColOverArrayCompanion(p: Rep[ColOverArrayCompanionAbs]): ColOverArrayCompanionAbs = proxyOps[ColOverArrayCompanionAbs](p);
//        implicit case object ColOverArrayCompanionElem extends CompanionElem[ColOverArrayCompanionAbs] with scala.Product with scala.Serializable {
//          lazy val tag = weakTypeTag[ColOverArrayCompanionAbs];
//          protected def getDefaultRep = ColOverArray
//        };
//        implicit def proxyColOverArray[A](p: Rep[ColOverArray[A]]): ColOverArray[A] = proxyOps[ColOverArray[A]](p);
//        implicit class ExtendedColOverArray[A](p: Rep[ColOverArray[A]])(implicit eeA: Elem[A]) {
//          def toData: Rep[ColOverArrayData[A]] = isoColOverArray(eeA).from(p)
//        };
//        implicit def isoColOverArray[A](implicit eeA: Elem[A]): Iso[ColOverArrayData[A], ColOverArray[A]] = new ColOverArrayIso[A]();
//        def mkColOverArray[A](arr: Rep[WArray[A]])(implicit eeA: Elem[A]): Rep[ColOverArray[A]];
//        def unmkColOverArray[A](p: Rep[Col[A]]): Option[Rep[WArray[A]]];
//        class PairColElem[A, B](val iso: Iso[PairColData[A, B], PairCol[A, B]])(implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]) extends ColElem[scala.Tuple2[A, B], PairCol[A, B]] with ConcreteElem[PairColData[A, B], PairCol[A, B]] {
//          override def convertCol(x: Rep[Col[scala.Tuple2[A, B]]]) = !!!("Cannot convert from Col to PairCol: missing fields List(as, bs)");
//          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
//          override lazy val tag = {
//            implicit val tagTuple_A_B = eeA.tag;
//            implicit val tagA = ecA.tag;
//            implicit val tagB = ecB.tag;
//            weakTypeTag[PairCol[A, B]]
//          }
//        };
//        type PairColData[A, B] = scala.Tuple2[Col[A], Col[B]];
//        class PairColIso[A, B](implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]) extends Iso[PairColData[A, B], PairCol[A, B]]()(pairElement(implicitly[Elem[Col[A]]], implicitly[Elem[Col[B]]])) {
//          override def from(p: Rep[PairCol[A, B]]) = scala.Tuple2(p.as, p.bs);
//          override def to(p: Rep[scala.Tuple2[Col[A], Col[B]]]) = {
//            val x$1 = (p: @scala.unchecked) match {
//              case Pair((as @ _), (bs @ _)) => scala.Tuple2(as, bs)
//            };
//            val as = x$1._1;
//            val bs = x$1._2;
//            PairCol(as, bs)
//          };
//          lazy val defaultRepTo: Rep[PairCol[A, B]] = PairCol(element[Col[A]].defaultRepValue, element[Col[B]].defaultRepValue);
//          lazy val eTo = new PairColElem[A, B](this)
//        };
//        abstract class PairColCompanionAbs extends CompanionBase[PairColCompanionAbs] with PairColCompanion {
//          override def toString = "PairCol";
//          def apply[A, B](p: Rep[PairColData[A, B]])(implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]): Rep[PairCol[A, B]] = isoPairCol(eeA, ecA, ecB).to(p);
//          def apply[A, B](as: Rep[Col[A]], bs: Rep[Col[B]])(implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]): Rep[PairCol[A, B]] = mkPairCol(as, bs)
//        };
//        object PairColMatcher {
//          def unapply[A, B](p: Rep[Col[scala.Tuple2[A, B]]]) = unmkPairCol(p)
//        };
//        def PairCol: Rep[PairColCompanionAbs];
//        implicit def proxyPairColCompanion(p: Rep[PairColCompanionAbs]): PairColCompanionAbs = proxyOps[PairColCompanionAbs](p);
//        implicit case object PairColCompanionElem extends CompanionElem[PairColCompanionAbs] with scala.Product with scala.Serializable {
//          lazy val tag = weakTypeTag[PairColCompanionAbs];
//          protected def getDefaultRep = PairCol
//        };
//        implicit def proxyPairCol[A, B](p: Rep[PairCol[A, B]]): PairCol[A, B] = proxyOps[PairCol[A, B]](p);
//        implicit class ExtendedPairCol[A, B](p: Rep[PairCol[A, B]])(implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]) {
//          def toData: Rep[PairColData[A, B]] = isoPairCol(eeA, ecA, ecB).from(p)
//        };
//        implicit def isoPairCol[A, B](implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]): Iso[PairColData[A, B], PairCol[A, B]] = new PairColIso[A, B]();
//        def mkPairCol[A, B](as: Rep[Col[A]], bs: Rep[Col[B]])(implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]): Rep[PairCol[A, B]];
//        def unmkPairCol[A, B](p: Rep[Col[scala.Tuple2[A, B]]]): Option[scala.Tuple2[Rep[Col[A]], Rep[Col[B]]]]
//      };
//      trait ColsExp extends ColsDsl with ScalanExp { self: LinearAlgebraDslExp =>
//        lazy val Col: Rep[ColCompanionAbs] = {
//          final class $anon extends ColCompanionAbs with UserTypeDef[ColCompanionAbs] {
//            lazy val selfType = element[ColCompanionAbs];
//            override def mirror(t: Transformer) = this
//          };
//          new $anon()
//        };
//        case class ExpColOverArray[A](override val arr: Rep[WArray[A]])(implicit eeA: Elem[A]) extends ColOverArray[A](arr) with UserTypeDef[ColOverArray[A]] {
//          lazy val selfType = element[ColOverArray[A]];
//          override def mirror(t: Transformer) = ExpColOverArray[A](t(arr))
//        };
//        lazy val ColOverArray: Rep[ColOverArrayCompanionAbs] = {
//          final class $anon extends ColOverArrayCompanionAbs with UserTypeDef[ColOverArrayCompanionAbs] {
//            lazy val selfType = element[ColOverArrayCompanionAbs];
//            override def mirror(t: Transformer) = this
//          };
//          new $anon()
//        };
//        object ColOverArrayMethods {
//          object length {
//            def unapply(d: (Def[_$2] forSome {
//              type _$2
//            })): Option[(Rep[ColOverArray[A]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(ColOverArrayElem[_$3] forSome {
//  type _$3
//})].&&(__equal(method.getName, "length")) => Some(receiver).asInstanceOf[Option[(Rep[ColOverArray[A]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$4] forSome {
//              type _$4
//            })): Option[(Rep[ColOverArray[A]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object apply {
//            def unapply(d: (Def[_$5] forSome {
//              type _$5
//            })): Option[(scala.Tuple2[Rep[ColOverArray[A]], Rep[Int]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(ColOverArrayElem[_$6] forSome {
//  type _$6
//})].&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[ColOverArray[A]], Rep[Int]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$7] forSome {
//              type _$7
//            })): Option[(scala.Tuple2[Rep[ColOverArray[A]], Rep[Int]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          }
//        };
//        object ColOverArrayCompanionMethods;
//        def mkColOverArray[A](arr: Rep[WArray[A]])(implicit eeA: Elem[A]): Rep[ColOverArray[A]] = new ExpColOverArray[A](arr);
//        def unmkColOverArray[A](p: Rep[Col[A]]) = p.elem.asInstanceOf[(Elem[_$8] forSome {
//          type _$8
//        })] match {
//          case ((_): ColOverArrayElem[A] @unchecked) => Some(p.asRep[ColOverArray[A]].arr)
//          case _ => None
//        };
//        case class ExpPairCol[A, B](override val as: Rep[Col[A]], override val bs: Rep[Col[B]])(implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]) extends PairCol[A, B](as, bs) with UserTypeDef[PairCol[A, B]] {
//          lazy val selfType = element[PairCol[A, B]];
//          override def mirror(t: Transformer) = ExpPairCol[A, B](t(as), t(bs))
//        };
//        lazy val PairCol: Rep[PairColCompanionAbs] = {
//          final class $anon extends PairColCompanionAbs with UserTypeDef[PairColCompanionAbs] {
//            lazy val selfType = element[PairColCompanionAbs];
//            override def mirror(t: Transformer) = this
//          };
//          new $anon()
//        };
//        object PairColMethods {
//          object arr {
//            def unapply(d: (Def[_$9] forSome {
//              type _$9
//            })): Option[(Rep[PairCol[A, B]] forSome {
//              type A;
//              type B
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PairColElem[_$10, _$11] forSome {
//  type _$10;
//  type _$11
//})].&&(__equal(method.getName, "arr")) => Some(receiver).asInstanceOf[Option[(Rep[PairCol[A, B]] forSome {
//                type A;
//                type B
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$12] forSome {
//              type _$12
//            })): Option[(Rep[PairCol[A, B]] forSome {
//              type A;
//              type B
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object length {
//            def unapply(d: (Def[_$13] forSome {
//              type _$13
//            })): Option[(Rep[PairCol[A, B]] forSome {
//              type A;
//              type B
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PairColElem[_$14, _$15] forSome {
//  type _$14;
//  type _$15
//})].&&(__equal(method.getName, "length")) => Some(receiver).asInstanceOf[Option[(Rep[PairCol[A, B]] forSome {
//                type A;
//                type B
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$16] forSome {
//              type _$16
//            })): Option[(Rep[PairCol[A, B]] forSome {
//              type A;
//              type B
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object apply {
//            def unapply(d: (Def[_$17] forSome {
//              type _$17
//            })): Option[(scala.Tuple2[Rep[PairCol[A, B]], Rep[Int]] forSome {
//              type A;
//              type B
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(PairColElem[_$18, _$19] forSome {
//  type _$18;
//  type _$19
//})].&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[PairCol[A, B]], Rep[Int]] forSome {
//                type A;
//                type B
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$20] forSome {
//              type _$20
//            })): Option[(scala.Tuple2[Rep[PairCol[A, B]], Rep[Int]] forSome {
//              type A;
//              type B
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          }
//        };
//        object PairColCompanionMethods;
//        def mkPairCol[A, B](as: Rep[Col[A]], bs: Rep[Col[B]])(implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]): Rep[PairCol[A, B]] = new ExpPairCol[A, B](as, bs);
//        def unmkPairCol[A, B](p: Rep[Col[scala.Tuple2[A, B]]]) = p.elem.asInstanceOf[(Elem[_$21] forSome {
//          type _$21
//        })] match {
//          case ((_): PairColElem[A, B] @unchecked) => Some(scala.Tuple2(p.asRep[PairCol[A, B]].as, p.asRep[PairCol[A, B]].bs))
//          case _ => None
//        };
//        object ColMethods {
//          object arr {
//            def unapply(d: (Def[_$22] forSome {
//              type _$22
//            })): Option[(Rep[Col[A]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(ColElem[_$23, _$24] forSome {
//  type _$23;
//  type _$24
//})].&&(__equal(method.getName, "arr")) => Some(receiver).asInstanceOf[Option[(Rep[Col[A]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$25] forSome {
//              type _$25
//            })): Option[(Rep[Col[A]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object length {
//            def unapply(d: (Def[_$26] forSome {
//              type _$26
//            })): Option[(Rep[Col[A]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(ColElem[_$27, _$28] forSome {
//  type _$27;
//  type _$28
//})].&&(__equal(method.getName, "length")) => Some(receiver).asInstanceOf[Option[(Rep[Col[A]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$29] forSome {
//              type _$29
//            })): Option[(Rep[Col[A]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object apply {
//            def unapply(d: (Def[_$30] forSome {
//              type _$30
//            })): Option[(scala.Tuple2[Rep[Col[A]], Rep[Int]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(ColElem[_$31, _$32] forSome {
//  type _$31;
//  type _$32
//})].&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[Col[A]], Rep[Int]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$33] forSome {
//              type _$33
//            })): Option[(scala.Tuple2[Rep[Col[A]], Rep[Int]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object reduce {
//            def unapply(d: (Def[_$34] forSome {
//              type _$34
//            })): Option[(scala.Tuple2[Rep[Col[A]], Rep[NumMonoid[A]]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((m @ _), _*), _) if receiver.elem.isInstanceOf[(ColElem[_$35, _$36] forSome {
//  type _$35;
//  type _$36
//})].&&(__equal(method.getName, "reduce")) => Some(scala.Tuple2(receiver, m)).asInstanceOf[Option[(scala.Tuple2[Rep[Col[A]], Rep[NumMonoid[A]]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$37] forSome {
//              type _$37
//            })): Option[(scala.Tuple2[Rep[Col[A]], Rep[NumMonoid[A]]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object zip {
//            def unapply(d: (Def[_$38] forSome {
//              type _$38
//            })): Option[(scala.Tuple3[Rep[Col[A]], Rep[Col[B]], Elem[B]] forSome {
//              type A;
//              type B
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((ys @ _), (emB @ _), _*), _) if receiver.elem.isInstanceOf[(ColElem[_$39, _$40] forSome {
//  type _$39;
//  type _$40
//})].&&(__equal(method.getName, "zip")) => Some(scala.Tuple3(receiver, ys, emB)).asInstanceOf[Option[(scala.Tuple3[Rep[Col[A]], Rep[Col[B]], Elem[B]] forSome {
//                type A;
//                type B
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$41] forSome {
//              type _$41
//            })): Option[(scala.Tuple3[Rep[Col[A]], Rep[Col[B]], Elem[B]] forSome {
//              type A;
//              type B
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          }
//        };
//        object ColCompanionMethods {
//          object apply {
//            def unapply(d: (Def[_$42] forSome {
//              type _$42
//            })): Option[(scala.Tuple2[Rep[Array[T]], Elem[T]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((arr @ _), (emT @ _), _*), _) if __equal(receiver.elem, ColCompanionElem).&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(arr, emT)).asInstanceOf[Option[(scala.Tuple2[Rep[Array[T]], Elem[T]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$43] forSome {
//              type _$43
//            })): Option[(scala.Tuple2[Rep[Array[T]], Elem[T]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object fromArray {
//            def unapply(d: (Def[_$44] forSome {
//              type _$44
//            })): Option[(scala.Tuple2[Rep[Array[T]], Elem[T]] forSome {
//              type T
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), Seq((arr @ _), (emT @ _), _*), _) if __equal(receiver.elem, ColCompanionElem).&&(__equal(method.getName, "fromArray")) => Some(scala.Tuple2(arr, emT)).asInstanceOf[Option[(scala.Tuple2[Rep[Array[T]], Elem[T]] forSome {
//                type T
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$45] forSome {
//              type _$45
//            })): Option[(scala.Tuple2[Rep[Array[T]], Elem[T]] forSome {
//              type T
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          }
//        }
//      };
//      trait Cols extends Base { self: LinearAlgebraDsl =>
//        trait Col[A] extends Reifiable[Col[A]] {
//          implicit def eeA: Elem[A];
//          def arr: Rep[WArray[A]];
//          def length: Rep[Int];
//          def apply(i: Rep[Int]): Rep[A];
//          def map[B](f: Rep[scala.Function1[A, B]])(implicit emB: Elem[B]): Rep[Col[B]] = Cols.this.Col.apply[B](scala.this.Predef.genericArrayOps[A](((Col.this.arr): Rep[Array[A]])).map[B, Array[B]](f)(scala.this.Predef.implicitly[CanBuildFrom[Array[A], B, Array[B]]](scala.this.Array.canBuildFrom[B](evidence$1))))(evidence$1);
//          def reduce(implicit m: Rep[NumMonoid[A]]): Rep[A] = scala.this.Predef.genericArrayOps[A](((Col.this.arr): Rep[Array[A]])).reduce[A](m.append);
//          def zip[B](ys: Rep[Col[B]])(implicit emB: Elem[B]): Rep[PairCol[A, B]] = PairCol(this, ys)
//        };
//        abstract class ColOverArray[A](val arr: Rep[WArray[A]])(implicit val eeA: Elem[A]) extends Col[A] {
//          def length: Rep[Int] = array_length(ColOverArray.this.arr);
//          def apply(i: Rep[Int]): Rep[A] = array_apply(ColOverArray.this.arr, i)
//        };
//        abstract class PairCol[A, B](val as: Rep[Col[A]], val bs: Rep[Col[B]])(implicit val eeA: Elem[scala.Tuple2[A, B]], val ecA: Elem[A], val ecB: Elem[B]) extends Col[scala.Tuple2[A, B]] {
//          def arr: Rep[Array[scala.Tuple2[A, B]]] = scala.this.Predef.genericArrayOps[A](PairCol.this.as.arr).zip[A, B, Array[scala.Tuple2[A, B]]](scala.this.Predef.genericWrapArray[B](PairCol.this.bs.arr))(scala.this.Array.canBuildFrom[scala.Tuple2[A, B]](((ClassTag.apply[scala.Tuple2[A, B]](toRep(classOf[scala.Tuple2]))): Rep[ClassTag[scala.Tuple2[A, B]]])));
//          def length: Rep[Int] = PairCol.this.as.length;
//          def apply(i: Rep[Int]): Rep[scala.Tuple2[A, B]] = scala.Tuple2.apply[A, B](PairCol.this.as.apply(i), PairCol.this.bs.apply(i))
//        };
//        trait ColCompanion {
//          def apply[T](arr: Rep[Array[T]])(implicit emT: Elem[T]): Rep[Col[T]] = Col.this.fromArray[T](arr)(evidence$2);
//          def fromArray[T](arr: Rep[Array[T]])(implicit emT: Elem[T]): Rep[Col[T]] = ColOverArray(arr)(evidence$3)
//        };
//        trait ColOverArrayCompanion;
//        trait PairColCompanion
//      };
//      trait ColsDsl extends ColsAbs { self: LinearAlgebraDsl =>
//
//      };
//      trait ColsDslExp extends ColsExp { self: LinearAlgebraDslExp =>
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