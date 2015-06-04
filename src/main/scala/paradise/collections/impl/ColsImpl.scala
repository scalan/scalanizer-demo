package paradise.collections {
  package implOfCols {
    object StagedEvaluation {
      import scalan._;
      import paradise.implOfNumMonoids.StagedEvaluation._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait ColsAbs extends Cols with ScalanDsl { self: ColsDsl =>
        implicit def proxyCol[A](p: Rep[Col[A]]): Col[A] = proxyOps[Col[A]](p)(classTag[Col[A]]);
        class ColElem[A, To <: Col[A]](implicit val eeA: Elem[A]) extends EntityElem[To] {
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagA = eeA.tag;
            weakTypeTag[Col[A]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$1] forSome { 
            type _$1
          })]) = {
            val conv = fun(((x: Rep[Col[A]]) => convertCol(x)));
            tryConvert(element[Col[A]], this, x, conv)
          };
          def convertCol(x: Rep[Col[A]]): Rep[To] = {
            assert(x.selfType1 match {
              case ((_): ColElem[(_), (_)]) => true
              case _ => false
            });
            x.asRep[To]
          };
          override def getDefaultRep: Rep[To] = ???
        };
        implicit def colElement[A](implicit eeA: Elem[A]): Elem[Col[A]] = new ColElem[A, Col[A]]();
        implicit case object ColCompanionElem extends CompanionElem[ColCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[ColCompanionAbs];
          protected def getDefaultRep = Col
        };
        abstract class ColCompanionAbs extends CompanionBase[ColCompanionAbs] with ColCompanion {
          override def toString = "Col"
        };
        def Col: Rep[ColCompanionAbs];
        implicit def proxyColCompanion(p: Rep[ColCompanion]): ColCompanion = proxyOps[ColCompanion](p);
        class ColOverArrayElem[A](val iso: Iso[ColOverArrayData[A], ColOverArray[A]])(implicit eeA: Elem[A]) extends ColElem[A, ColOverArray[A]] with ConcreteElem[ColOverArrayData[A], ColOverArray[A]] {
          override def convertCol(x: Rep[Col[A]]) = ColOverArray(x.arr);
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = {
            implicit val tagA = eeA.tag;
            weakTypeTag[ColOverArray[A]]
          }
        };
        type ColOverArrayData[A] = Array[A];
        class ColOverArrayIso[A](implicit eeA: Elem[A]) extends Iso[ColOverArrayData[A], ColOverArray[A]] {
          override def from(p: Rep[ColOverArray[A]]) = p.arr;
          override def to(p: Rep[Array[A]]) = {
            val arr = p;
            ColOverArray(arr)
          };
          lazy val defaultRepTo = Default.defaultVal[Rep[ColOverArray[A]]](ColOverArray(element[Array[A]].defaultRepValue));
          lazy val eTo = new ColOverArrayElem[A](this)
        };
        abstract class ColOverArrayCompanionAbs extends CompanionBase[ColOverArrayCompanionAbs] with ColOverArrayCompanion {
          override def toString = "ColOverArray";
          def apply[A](arr: Rep[Array[A]])(implicit eeA: Elem[A]): Rep[ColOverArray[A]] = mkColOverArray(arr)
        };
        object ColOverArrayMatcher {
          def unapply[A](p: Rep[Col[A]]) = unmkColOverArray(p)
        };
        def ColOverArray: Rep[ColOverArrayCompanionAbs];
        implicit def proxyColOverArrayCompanion(p: Rep[ColOverArrayCompanionAbs]): ColOverArrayCompanionAbs = proxyOps[ColOverArrayCompanionAbs](p);
        implicit case object ColOverArrayCompanionElem extends CompanionElem[ColOverArrayCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[ColOverArrayCompanionAbs];
          protected def getDefaultRep = ColOverArray
        };
        implicit def proxyColOverArray[A](p: Rep[ColOverArray[A]]): ColOverArray[A] = proxyOps[ColOverArray[A]](p);
        implicit class ExtendedColOverArray[A](p: Rep[ColOverArray[A]])(implicit eeA: Elem[A]) {
          def toData: Rep[ColOverArrayData[A]] = isoColOverArray(eeA).from(p)
        };
        implicit def isoColOverArray[A](implicit eeA: Elem[A]): Iso[ColOverArrayData[A], ColOverArray[A]] = new ColOverArrayIso[A]();
        def mkColOverArray[A](arr: Rep[Array[A]])(implicit eeA: Elem[A]): Rep[ColOverArray[A]];
        def unmkColOverArray[A](p: Rep[Col[A]]): Option[Rep[Array[A]]];
        class PairColElem[A, B](val iso: Iso[PairColData[A, B], PairCol[A, B]])(implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]) extends ColElem[scala.Tuple2[A, B], PairCol[A, B]] with ConcreteElem[PairColData[A, B], PairCol[A, B]] {
          override def convertCol(x: Rep[Col[scala.Tuple2[A, B]]]) = !!!("Cannot convert from Col to PairCol: missing fields List(as, bs)");
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = {
            implicit val tagTuple_A_B = eeA.tag;
            implicit val tagA = ecA.tag;
            implicit val tagB = ecB.tag;
            weakTypeTag[PairCol[A, B]]
          }
        };
        type PairColData[A, B] = scala.Tuple2[Col[A], Col[B]];
        class PairColIso[A, B](implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]) extends Iso[PairColData[A, B], PairCol[A, B]]()(pairElement(implicitly[Elem[Col[A]]], implicitly[Elem[Col[B]]])) {
          override def from(p: Rep[PairCol[A, B]]) = scala.Tuple2(p.as, p.bs);
          override def to(p: Rep[scala.Tuple2[Col[A], Col[B]]]) = {
            val x$1 = (p: @scala.unchecked) match {
              case Pair((as @ _), (bs @ _)) => scala.Tuple2(as, bs)
            };
            val as = x$1._1;
            val bs = x$1._2;
            PairCol(as, bs)
          };
          lazy val defaultRepTo = Default.defaultVal[Rep[PairCol[A, B]]](PairCol(element[Col[A]].defaultRepValue, element[Col[B]].defaultRepValue));
          lazy val eTo = new PairColElem[A, B](this)
        };
        abstract class PairColCompanionAbs extends CompanionBase[PairColCompanionAbs] with PairColCompanion {
          override def toString = "PairCol";
          def apply[A, B](p: Rep[PairColData[A, B]])(implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]): Rep[PairCol[A, B]] = isoPairCol(eeA, ecA, ecB).to(p);
          def apply[A, B](as: Rep[Col[A]], bs: Rep[Col[B]])(implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]): Rep[PairCol[A, B]] = mkPairCol(as, bs)
        };
        object PairColMatcher {
          def unapply[A, B](p: Rep[Col[scala.Tuple2[A, B]]]) = unmkPairCol(p)
        };
        def PairCol: Rep[PairColCompanionAbs];
        implicit def proxyPairColCompanion(p: Rep[PairColCompanionAbs]): PairColCompanionAbs = proxyOps[PairColCompanionAbs](p);
        implicit case object PairColCompanionElem extends CompanionElem[PairColCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[PairColCompanionAbs];
          protected def getDefaultRep = PairCol
        };
        implicit def proxyPairCol[A, B](p: Rep[PairCol[A, B]]): PairCol[A, B] = proxyOps[PairCol[A, B]](p);
        implicit class ExtendedPairCol[A, B](p: Rep[PairCol[A, B]])(implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]) {
          def toData: Rep[PairColData[A, B]] = isoPairCol(eeA, ecA, ecB).from(p)
        };
        implicit def isoPairCol[A, B](implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]): Iso[PairColData[A, B], PairCol[A, B]] = new PairColIso[A, B]();
        def mkPairCol[A, B](as: Rep[Col[A]], bs: Rep[Col[B]])(implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]): Rep[PairCol[A, B]];
        def unmkPairCol[A, B](p: Rep[Col[scala.Tuple2[A, B]]]): Option[scala.Tuple2[Rep[Col[A]], Rep[Col[B]]]]
      };
      trait ColsSeq extends ColsDsl with ScalanSeq { self: ColsDslSeq =>
        lazy val Col: Rep[ColCompanionAbs] = {
          final class $anon extends ColCompanionAbs with UserTypeSeq[ColCompanionAbs] {
            lazy val selfType = element[ColCompanionAbs]
          };
          new $anon()
        };
        case class SeqColOverArray[A](override val arr: Rep[Array[A]])(implicit eeA: Elem[A]) extends ColOverArray[A](arr) with UserTypeSeq[ColOverArray[A]] {
          lazy val selfType = element[ColOverArray[A]]
        };
        lazy val ColOverArray = {
          final class $anon extends ColOverArrayCompanionAbs with UserTypeSeq[ColOverArrayCompanionAbs] {
            lazy val selfType = element[ColOverArrayCompanionAbs]
          };
          new $anon()
        };
        def mkColOverArray[A](arr: Rep[Array[A]])(implicit eeA: Elem[A]): Rep[ColOverArray[A]] = new SeqColOverArray[A](arr);
        def unmkColOverArray[A](p: Rep[Col[A]]) = p match {
          case (p @ ((_): ColOverArray[A] @unchecked)) => Some(p.arr)
          case _ => None
        };
        case class SeqPairCol[A, B](override val as: Rep[Col[A]], override val bs: Rep[Col[B]])(implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]) extends PairCol[A, B](as, bs) with UserTypeSeq[PairCol[A, B]] {
          lazy val selfType = element[PairCol[A, B]]
        };
        lazy val PairCol = {
          final class $anon extends PairColCompanionAbs with UserTypeSeq[PairColCompanionAbs] {
            lazy val selfType = element[PairColCompanionAbs]
          };
          new $anon()
        };
        def mkPairCol[A, B](as: Rep[Col[A]], bs: Rep[Col[B]])(implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]): Rep[PairCol[A, B]] = new SeqPairCol[A, B](as, bs);
        def unmkPairCol[A, B](p: Rep[Col[scala.Tuple2[A, B]]]) = p match {
          case (p @ ((_): PairCol[A, B] @unchecked)) => Some(scala.Tuple2(p.as, p.bs))
          case _ => None
        }
      };
      trait ColsExp extends ColsDsl with ScalanExp { self: ColsDslExp =>
        lazy val Col: Rep[ColCompanionAbs] = {
          final class $anon extends ColCompanionAbs with UserTypeDef[ColCompanionAbs] {
            lazy val selfType = element[ColCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        case class ExpColOverArray[A](override val arr: Rep[Array[A]])(implicit eeA: Elem[A]) extends ColOverArray[A](arr) with UserTypeDef[ColOverArray[A]] {
          lazy val selfType = element[ColOverArray[A]];
          override def mirror(t: Transformer) = ExpColOverArray[A](t(arr))
        };
        lazy val ColOverArray: Rep[ColOverArrayCompanionAbs] = {
          final class $anon extends ColOverArrayCompanionAbs with UserTypeDef[ColOverArrayCompanionAbs] {
            lazy val selfType = element[ColOverArrayCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object ColOverArrayMethods {
          object length {
            def unapply(d: (Def[_$2] forSome { 
              type _$2
            })): Option[(Rep[ColOverArray[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(ColOverArrayElem[_$3] forSome { 
  type _$3
})].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[(Rep[ColOverArray[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$4] forSome { 
              type _$4
            })): Option[(Rep[ColOverArray[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$5] forSome { 
              type _$5
            })): Option[(scala.Tuple2[Rep[ColOverArray[A]], Rep[Int]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(ColOverArrayElem[_$6] forSome { 
  type _$6
})].&&(method.getName.==("apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[ColOverArray[A]], Rep[Int]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$7] forSome { 
              type _$7
            })): Option[(scala.Tuple2[Rep[ColOverArray[A]], Rep[Int]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object ColOverArrayCompanionMethods;
        def mkColOverArray[A](arr: Rep[Array[A]])(implicit eeA: Elem[A]): Rep[ColOverArray[A]] = new ExpColOverArray[A](arr);
        def unmkColOverArray[A](p: Rep[Col[A]]) = p.elem.asInstanceOf[(Elem[_$8] forSome { 
          type _$8
        })] match {
          case ((_): ColOverArrayElem[A] @unchecked) => Some(p.asRep[ColOverArray[A]].arr)
          case _ => None
        };
        case class ExpPairCol[A, B](override val as: Rep[Col[A]], override val bs: Rep[Col[B]])(implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]) extends PairCol[A, B](as, bs) with UserTypeDef[PairCol[A, B]] {
          lazy val selfType = element[PairCol[A, B]];
          override def mirror(t: Transformer) = ExpPairCol[A, B](t(as), t(bs))
        };
        lazy val PairCol: Rep[PairColCompanionAbs] = {
          final class $anon extends PairColCompanionAbs with UserTypeDef[PairColCompanionAbs] {
            lazy val selfType = element[PairColCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object PairColMethods {
          object arr {
            def unapply(d: (Def[_$9] forSome { 
              type _$9
            })): Option[(Rep[PairCol[A, B]] forSome { 
              type A;
              type B
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PairColElem[_$10, _$11] forSome { 
  type _$10;
  type _$11
})].&&(method.getName.==("arr")) => Some(receiver).asInstanceOf[Option[(Rep[PairCol[A, B]] forSome { 
                type A;
                type B
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$12] forSome { 
              type _$12
            })): Option[(Rep[PairCol[A, B]] forSome { 
              type A;
              type B
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object length {
            def unapply(d: (Def[_$13] forSome { 
              type _$13
            })): Option[(Rep[PairCol[A, B]] forSome { 
              type A;
              type B
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PairColElem[_$14, _$15] forSome { 
  type _$14;
  type _$15
})].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[(Rep[PairCol[A, B]] forSome { 
                type A;
                type B
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$16] forSome { 
              type _$16
            })): Option[(Rep[PairCol[A, B]] forSome { 
              type A;
              type B
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$17] forSome { 
              type _$17
            })): Option[(scala.Tuple2[Rep[PairCol[A, B]], Rep[Int]] forSome { 
              type A;
              type B
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(PairColElem[_$18, _$19] forSome { 
  type _$18;
  type _$19
})].&&(method.getName.==("apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[PairCol[A, B]], Rep[Int]] forSome { 
                type A;
                type B
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$20] forSome { 
              type _$20
            })): Option[(scala.Tuple2[Rep[PairCol[A, B]], Rep[Int]] forSome { 
              type A;
              type B
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object PairColCompanionMethods;
        def mkPairCol[A, B](as: Rep[Col[A]], bs: Rep[Col[B]])(implicit eeA: Elem[scala.Tuple2[A, B]], ecA: Elem[A], ecB: Elem[B]): Rep[PairCol[A, B]] = new ExpPairCol[A, B](as, bs);
        def unmkPairCol[A, B](p: Rep[Col[scala.Tuple2[A, B]]]) = p.elem.asInstanceOf[(Elem[_$21] forSome { 
          type _$21
        })] match {
          case ((_): PairColElem[A, B] @unchecked) => Some(scala.Tuple2(p.asRep[PairCol[A, B]].as, p.asRep[PairCol[A, B]].bs))
          case _ => None
        };
        object ColMethods {
          object arr {
            def unapply(d: (Def[_$22] forSome { 
              type _$22
            })): Option[(Rep[Col[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(ColElem[_$23, _$24] forSome { 
  type _$23;
  type _$24
})].&&(method.getName.==("arr")) => Some(receiver).asInstanceOf[Option[(Rep[Col[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$25] forSome { 
              type _$25
            })): Option[(Rep[Col[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object length {
            def unapply(d: (Def[_$26] forSome { 
              type _$26
            })): Option[(Rep[Col[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(ColElem[_$27, _$28] forSome { 
  type _$27;
  type _$28
})].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[(Rep[Col[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$29] forSome { 
              type _$29
            })): Option[(Rep[Col[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$30] forSome { 
              type _$30
            })): Option[(scala.Tuple2[Rep[Col[A]], Rep[Int]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(ColElem[_$31, _$32] forSome { 
  type _$31;
  type _$32
})].&&(method.getName.==("apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[Col[A]], Rep[Int]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$33] forSome { 
              type _$33
            })): Option[(scala.Tuple2[Rep[Col[A]], Rep[Int]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object reduce {
            def unapply(d: (Def[_$34] forSome { 
              type _$34
            })): Option[(scala.Tuple2[Rep[Col[A]], Rep[NumMonoid[A]]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((m @ _), _*), _) if receiver.elem.isInstanceOf[(ColElem[_$35, _$36] forSome { 
  type _$35;
  type _$36
})].&&(method.getName.==("reduce")) => Some(scala.Tuple2(receiver, m)).asInstanceOf[Option[(scala.Tuple2[Rep[Col[A]], Rep[NumMonoid[A]]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$37] forSome { 
              type _$37
            })): Option[(scala.Tuple2[Rep[Col[A]], Rep[NumMonoid[A]]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object zip {
            def unapply(d: (Def[_$38] forSome { 
              type _$38
            })): Option[(scala.Tuple3[Rep[Col[A]], Rep[Col[B]], Rep[Elem[B]]] forSome { 
              type A;
              type B
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((ys @ _), (emB @ _), _*), _) if receiver.elem.isInstanceOf[(ColElem[_$39, _$40] forSome { 
  type _$39;
  type _$40
})].&&(method.getName.==("zip")) => Some(scala.Tuple3(receiver, ys, emB)).asInstanceOf[Option[(scala.Tuple3[Rep[Col[A]], Rep[Col[B]], Rep[Elem[B]]] forSome { 
                type A;
                type B
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$41] forSome { 
              type _$41
            })): Option[(scala.Tuple3[Rep[Col[A]], Rep[Col[B]], Rep[Elem[B]]] forSome { 
              type A;
              type B
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object ColCompanionMethods {
          object apply {
            def unapply(d: (Def[_$42] forSome { 
              type _$42
            })): Option[(scala.Tuple2[Rep[Array[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((arr @ _), (emT @ _), _*), _) if receiver.elem.==(ColCompanionElem).&&(method.getName.==("apply")) => Some(scala.Tuple2(arr, emT)).asInstanceOf[Option[(scala.Tuple2[Rep[Array[T]], Rep[Elem[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$43] forSome { 
              type _$43
            })): Option[(scala.Tuple2[Rep[Array[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object fromArray {
            def unapply(d: (Def[_$44] forSome { 
              type _$44
            })): Option[(scala.Tuple2[Rep[Array[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((arr @ _), (emT @ _), _*), _) if receiver.elem.==(ColCompanionElem).&&(method.getName.==("fromArray")) => Some(scala.Tuple2(arr, emT)).asInstanceOf[Option[(scala.Tuple2[Rep[Array[T]], Rep[Elem[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$45] forSome { 
              type _$45
            })): Option[(scala.Tuple2[Rep[Array[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        }
      };
      trait Cols extends Base with NumMonoidsDsl { self: ColsDsl =>
        trait Col[A] extends Reifiable[Col[A]] {
          implicit def eeA: Elem[A];
          def arr: Rep[Array[A]];
          def length: Rep[Int];
          def apply(i: Rep[Int]): Rep[A];
          def map[B](f: Rep[scala.Function1[A, B]])(implicit emB: Elem[B]): Rep[Col[B]] = Col(array_map(arr, f));
          def reduce(implicit m: NumMonoid[A]): Rep[A] = {
            array_reduce(arr)(RepMonoid(opName = "+", zero = m.zero, append = m.append, isCommutative = true))
          };;
          def zip[B](ys: Rep[Col[B]])(implicit emB: Elem[B]): Rep[PairCol[A, B]] = PairCol(this, ys)
        };
        abstract class ColOverArray[A](val arr: Rep[Array[A]])(implicit val eeA: Elem[A]) extends Col[A] with Product with Serializable {
          def length = array_length(arr);
          def apply(i: Rep[Int]) = array_apply(arr, i)
        };
        abstract class PairCol[A, B](val as: Rep[Col[A]], val bs: Rep[Col[B]])(implicit val eeA: Elem[scala.Tuple2[A, B]], val ecA: Elem[A], val ecB: Elem[B]) extends Col[scala.Tuple2[A, B]] with Product with Serializable {
          def arr: Rep[Array[scala.Tuple2[A, B]]] = as.arr.zip(bs.arr);
          def length = as.length;
          def apply(i: Rep[Int]) = Tuple(as(i), bs(i))
        };
        trait ColCompanion {
          def apply[T](arr: Rep[Array[T]])(implicit emT: Elem[T]): Rep[Col[T]] = fromArray(arr);
          def fromArray[T](arr: Rep[Array[T]])(implicit emT: Elem[T]): Rep[Col[T]] = ColOverArray(arr)
        };
        trait ColOverArrayCompanion;
        trait PairColCompanion
      };
      trait ColsDsl extends ColsAbs with NumMonoidsDsl { self: ColsDsl =>
        
      };
      trait ColsDslSeq extends ColsSeq with NumMonoidsDslSeq { self: ColsDslSeq =>
        
      };
      trait ColsDslExp extends ColsExp with NumMonoidsDslExp { self: ColsDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0Q2FsbFDrGS0l7EVYAgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQACk51bU1vbm9pZHNzcQB+AAZzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHEAfgANeHEAfgALc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NEZWYH0H1o8ahwMwIACloACmlzQWJzdHJhY3RMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARhcmdzdAAiTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTQ2xhc3NBcmdzO0wABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+ABBMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAc3EAfgAGc3EAfgAIdAADQ29sc3EAfgAGc3EAfgAIdAABQXEAfgALcQB+AA14c3EAfgAIdAAHUHJvZHVjdHEAfgALc3EAfgAIdAAMU2VyaWFsaXphYmxlcQB+AAtxAH4ADXhxAH4AC3NyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzQXJncyfr7mTFMABcAgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZwVz2NYL1Ck/AgAIWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnWgAHdmFsRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGV0ACBMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcGVFeHByO3hwAAAAAXEAfgALc3IAC3NjYWxhLk5vbmUkRlAk9lPKlKwCAAB4cgAMc2NhbGEuT3B0aW9u/mk3/dsOZnQCAAB4cHQAA2FycnNxAH4ACHQABUFycmF5c3EAfgAGc3EAfgAIdAABQXEAfgALcQB+AA14cQB+AA14c3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kRGVm3VlxcbcwKw4CAApaAAxpc0VsZW1PckNvbnRaAAppc0ltcGxpY2l0WgAKaXNPdmVycmlkZUwAC2Fubm90YXRpb25zcQB+AAFMAAthcmdTZWN0aW9uc3EAfgABTAAEYm9keXEAfgADTAAEbmFtZXEAfgAETAAKb3ZlcmxvYWRJZHEAfgADTAAHdHBlQXJnc3EAfgABTAAGdHBlUmVzcQB+AAN4cAAAAHEAfgALcQB+AAtzcgAKc2NhbGEuU29tZREi8mleoYt0AgABTAABeHQAEkxqYXZhL2xhbmcvT2JqZWN0O3hxAH4AI3NyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1NlbGVjdAvdaWVlQPmFAgACTAAEZXhwcnQAHUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU0V4cHI7TAAFdG5hbWVxAH4ABHhwc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTSWRlbnQnEqX78AnXTwIAAUwABG5hbWVxAH4ABHhwdAADYXJydAAGbGVuZ3RocQB+ADdxAH4AJHEAfgALcQB+ACRzcQB+ACwAAABxAH4AC3NxAH4ABnNyACFzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZ3MpwieWXnW6iwIAAUwABGFyZ3NxAH4AAXhwc3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kQXJnA9z/y5NycwMCAAdaAAdpbXBGbGFnWgAMaXNFbGVtT3JDb250WgAIb3ZlckZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBlcQB+ACB4cAAAAHEAfgALcQB+ACR0AAFpc3IAI3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlUHJpbWl0aXZlNcO7VfC+TgUCAAJMABJkZWZhdWx0VmFsdWVTdHJpbmdxAH4ABEwABG5hbWVxAH4ABHhwdAABMHQAA0ludHEAfgANeHEAfgANeHNxAH4ALnNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0FwcGx5hGYpn9Ka/H8CAANMAAVhcmdzc3EAfgABTAADZnVucQB+ADJMAAJ0c3EAfgABeHBzcQB+AAZzcQB+AAZzcQB+ADR0AAFpcQB+AA14cQB+AA14c3EAfgA0dAADYXJycQB+AAt0AAVhcHBseXEAfgAkcQB+AAtxAH4AJHEAfgANeHEAfgAkc3EAfgAccQB+AAt0AAxDb2xPdmVyQXJyYXlxAH4AJHNxAH4ABnNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZUFyZ5JbJkhy8ZuJAgAETAAFYm91bmRxAH4AA0wADGNvbnRleHRCb3VuZHEAfgABTAAEbmFtZXEAfgAETAAHdHBhcmFtc3EAfgABeHBxAH4AJHNxAH4ABnQACENsYXNzVGFncQB+AA14dAABQXEAfgALcQB+AA14c3EAfgAPAHNxAH4ABnNxAH4ACHQAA0NvbHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZVR1cGxlNLv5XoiASHACAAFMAAl0cGVTRXhwcnNxAH4AAXhwc3EAfgAGc3EAfgAIdAABQXEAfgALc3EAfgAIdAABQnEAfgALcQB+AA14cQB+AA14c3EAfgAIcQB+ABlxAH4AC3NxAH4ACHEAfgAbcQB+AAtxAH4ADXhxAH4AC3NxAH4AHHNxAH4ABnNxAH4AHwAAAAFxAH4AC3EAfgAkdAAC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AFFxAH4AJHNxAH4ABnQACENsYXNzVGFncQB+AA14dAABQnEAfgALcQB+AA14c3EAfgAuc3EAfgAIdAADQ29sc3EAfgAGc3EAfgAIdAABQnEAfgALcQB+AA14c3EAfgAsAAAAcQB+AAtzcQB+AAZzcQB+ADpzcQB+AAZzcQB+AD0BAABxAH4AC3EAfgAkdAABbXNxAH4ACHQACU51bU1vbm9pZHNxAH4ABnNxAH4ACHQAAUFxAH4AC3EAfgANeHEAfgANeHEAfgANeHNxAH4ALnNxAH4ARXNxAH4ABnNxAH4ABnNxAH4AMXNxAH4ANHQAAW10AAZhcHBlbmRxAH4ADXhxAH4ADXhzcQB+ADFzcQB+ADR0AANhcnJ0AAZyZWR1Y2VxAH4AC3QABnJlZHVjZXEAfgAkcQB+AAtzcQB+AC5zcQB+AAh0AAFBcQB+AAtzcQB+ACwAAABxAH4AC3NxAH4ABnNxAH4AOnNxAH4ABnNxAH4APQAAAHEAfgALcQB+ACR0AAJ5c3NxAH4ACHQAA0NvbHNxAH4ABnNxAH4ACHQAAUJxAH4AC3EAfgANeHEAfgANeHEAfgANeHNxAH4ALnNxAH4ARXNxAH4ABnNxAH4ABnNyABtzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RoaXPqHtDGoF+LZwIAAUwACHR5cGVOYW1lcQB+AAR4cHQAAHNxAH4ANHQAAnlzcQB+AA14cQB+AA14c3EAfgA0dAAHUGFpckNvbHEAfgALdAADemlwcQB+ACRzcQB+AAZzcQB+AFFxAH4AJHEAfgALdAABQnEAfgALcQB+AA14c3EAfgAuc3EAfgAIdAAHUGFpckNvbHNxAH4ABnNxAH4ACHQAAUFxAH4AC3NxAH4ACHQAAUJxAH4AC3EAfgANeHEAfgANeHNxAH4ALnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU09iamVjdERlZoBV0ZN0CXpjAgADTAAJYW5jZXN0b3JzcQB+AAFMAARib2R5cQB+AAFMAARuYW1lcQB+AAR4cHEAfgALc3EAfgAGc3EAfgAsAAAAcQB+AAtzcQB+AAZzcQB+ADpzcQB+AAZzcQB+AD0AAABxAH4AC3EAfgAkdAADYXJyc3EAfgAIdAAFQXJyYXlzcQB+AAZzcQB+AAh0AAFUcQB+AAtxAH4ADXhxAH4ADXhxAH4ADXhzcQB+AC5zcQB+AEVzcQB+AAZzcQB+AAZzcQB+ADR0AANhcnJxAH4ADXhxAH4ADXhzcQB+ADR0AAlmcm9tQXJyYXlxAH4AC3EAfgBNcQB+ACRzcQB+AAZzcQB+AFFxAH4AJHNxAH4ABnQACENsYXNzVGFncQB+AA14dAABVHEAfgALcQB+AA14c3EAfgAuc3EAfgAIdAADQ29sc3EAfgAGc3EAfgAIdAABVHEAfgALcQB+AA14c3EAfgAsAAAAcQB+AAtzcQB+AAZzcQB+ADpzcQB+AAZzcQB+AD0AAABxAH4AC3EAfgAkdAADYXJyc3EAfgAIdAAFQXJyYXlzcQB+AAZzcQB+AAh0AAFUcQB+AAtxAH4ADXhxAH4ADXhxAH4ADXhzcQB+AC5zcQB+AEVzcQB+AAZzcQB+AAZzcQB+ADR0AANhcnJxAH4ADXhxAH4ADXhzcQB+ADR0AAxDb2xPdmVyQXJyYXlxAH4AC3QACWZyb21BcnJheXEAfgAkc3EAfgAGc3EAfgBRcQB+ACRzcQB+AAZ0AAhDbGFzc1RhZ3EAfgANeHQAAVRxAH4AC3EAfgANeHNxAH4ALnNxAH4ACHQAA0NvbHNxAH4ABnNxAH4ACHQAAVRxAH4AC3EAfgANeHEAfgANeHQAA0NvbHB0AANDb2xxAH4AJHNxAH4ABnNxAH4AUXEAfgAkcQB+AAt0AAFBcQB+AAtxAH4ADXhxAH4ADXhxAH4AtXEAfgAkc3EAfgAGc3IAIXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTSW1wb3J0U3RhdKyAoErDxhsiAgABTAAEbmFtZXEAfgAEeHB0ABNwYXJhZGlzZS5OdW1Nb25vaWRzc3EAfgGIdAAlc2NhbGEuY29sbGVjdGlvbi5nZW5lcmljLkNhbkJ1aWxkRnJvbXNxAH4BiHQAFnNjYWxhLnJlZmxlY3QuQ2xhc3NUYWdxAH4ADXhxAH4AC3QABENvbHN0ABRwYXJhZGlzZS5jb2xsZWN0aW9uc3EAfgAkcQB+ACQ="
    }
  }
}