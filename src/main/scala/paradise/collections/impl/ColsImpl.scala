package scalanizer.collections {
  package implOfCols {
    object StagedEvaluation {
      import scalan._;
      import scalan.meta.ScalanAst._;
      import scalanizer.implOfNumMonoids.StagedEvaluation._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait ColsAbs extends Cols with ScalanDsl { self: ColsDsl =>
        implicit def proxyCol[A](p: Rep[Col[A]]): Col[A] = proxyOps[Col[A]](p)(scala.reflect.classTag[Col[A]]);
        class ColElem[A, To <: Col[A]](implicit val eeA: Elem[A]) extends EntityElem[To] {
          lazy val parent: Option[(Elem[_$1] forSome { 
            type _$1
          })] = None;
          lazy val entityDef: STraitOrClassDef = {
            val module = getModules("Cols");
            module.entities.find(((x$1) => __equal(x$1.name, "Col"))).get
          };
          lazy val tyArgSubst: Map[String, TypeDesc] = Map("A".->(Left(eeA)));
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagA = eeA.tag;
            weakTypeTag[Col[A]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$2] forSome { 
            type _$2
          })]) = {
            implicit val eTo: Elem[To] = this;
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
          override lazy val parent: Option[(Elem[_$3] forSome { 
            type _$3
          })] = Some(colElement(element[A]));
          override lazy val entityDef = {
            val module = getModules("Cols");
            module.concreteSClasses.find(((x$2) => __equal(x$2.name, "ColOverArray"))).get
          };
          override lazy val tyArgSubst: Map[String, TypeDesc] = Map("A".->(Left(eeA)));
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
          lazy val defaultRepTo: Rep[ColOverArray[A]] = ColOverArray(element[Array[A]].defaultRepValue);
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
          override lazy val parent: Option[(Elem[_$4] forSome { 
            type _$4
          })] = Some(colElement(pairElement(element[A], element[B])));
          override lazy val entityDef = {
            val module = getModules("Cols");
            module.concreteSClasses.find(((x$3) => __equal(x$3.name, "PairCol"))).get
          };
          override lazy val tyArgSubst: Map[String, TypeDesc] = Map("A".->(Left(ecA)), "B".->(Left(ecB)));
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
            val x$4 = (p: @scala.unchecked) match {
              case Pair((as @ _), (bs @ _)) => scala.Tuple2(as, bs)
            };
            val as = x$4._1;
            val bs = x$4._2;
            PairCol(as, bs)
          };
          lazy val defaultRepTo: Rep[PairCol[A, B]] = PairCol(element[Col[A]].defaultRepValue, element[Col[B]].defaultRepValue);
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
        def unmkPairCol[A, B](p: Rep[Col[scala.Tuple2[A, B]]]): Option[scala.Tuple2[Rep[Col[A]], Rep[Col[B]]]];
        registerModule(scalan.meta.ScalanCodegen.loadModule(Cols_Module.dump))
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
            def unapply(d: (Def[_$5] forSome { 
              type _$5
            })): Option[(Rep[ColOverArray[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(ColOverArrayElem[_$6] forSome { 
  type _$6
})].&&(__equal(method.getName, "length")) => Some(receiver).asInstanceOf[Option[(Rep[ColOverArray[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$7] forSome { 
              type _$7
            })): Option[(Rep[ColOverArray[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$8] forSome { 
              type _$8
            })): Option[(scala.Tuple2[Rep[ColOverArray[A]], Rep[Int]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(ColOverArrayElem[_$9] forSome { 
  type _$9
})].&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[ColOverArray[A]], Rep[Int]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$10] forSome { 
              type _$10
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
        def unmkColOverArray[A](p: Rep[Col[A]]) = p.elem.asInstanceOf[(Elem[_$11] forSome { 
          type _$11
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
            def unapply(d: (Def[_$12] forSome { 
              type _$12
            })): Option[(Rep[PairCol[A, B]] forSome { 
              type A;
              type B
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PairColElem[_$13, _$14] forSome { 
  type _$13;
  type _$14
})].&&(__equal(method.getName, "arr")) => Some(receiver).asInstanceOf[Option[(Rep[PairCol[A, B]] forSome { 
                type A;
                type B
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$15] forSome { 
              type _$15
            })): Option[(Rep[PairCol[A, B]] forSome { 
              type A;
              type B
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object length {
            def unapply(d: (Def[_$16] forSome { 
              type _$16
            })): Option[(Rep[PairCol[A, B]] forSome { 
              type A;
              type B
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PairColElem[_$17, _$18] forSome { 
  type _$17;
  type _$18
})].&&(__equal(method.getName, "length")) => Some(receiver).asInstanceOf[Option[(Rep[PairCol[A, B]] forSome { 
                type A;
                type B
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$19] forSome { 
              type _$19
            })): Option[(Rep[PairCol[A, B]] forSome { 
              type A;
              type B
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$20] forSome { 
              type _$20
            })): Option[(scala.Tuple2[Rep[PairCol[A, B]], Rep[Int]] forSome { 
              type A;
              type B
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(PairColElem[_$21, _$22] forSome { 
  type _$21;
  type _$22
})].&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[PairCol[A, B]], Rep[Int]] forSome { 
                type A;
                type B
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$23] forSome { 
              type _$23
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
        def unmkPairCol[A, B](p: Rep[Col[scala.Tuple2[A, B]]]) = p.elem.asInstanceOf[(Elem[_$24] forSome { 
          type _$24
        })] match {
          case ((_): PairColElem[A, B] @unchecked) => Some(scala.Tuple2(p.asRep[PairCol[A, B]].as, p.asRep[PairCol[A, B]].bs))
          case _ => None
        };
        object ColMethods {
          object arr {
            def unapply(d: (Def[_$25] forSome { 
              type _$25
            })): Option[(Rep[Col[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(ColElem[_$26, _$27] forSome { 
  type _$26;
  type _$27
})].&&(__equal(method.getName, "arr")) => Some(receiver).asInstanceOf[Option[(Rep[Col[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$28] forSome { 
              type _$28
            })): Option[(Rep[Col[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object length {
            def unapply(d: (Def[_$29] forSome { 
              type _$29
            })): Option[(Rep[Col[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(ColElem[_$30, _$31] forSome { 
  type _$30;
  type _$31
})].&&(__equal(method.getName, "length")) => Some(receiver).asInstanceOf[Option[(Rep[Col[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$32] forSome { 
              type _$32
            })): Option[(Rep[Col[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$33] forSome { 
              type _$33
            })): Option[(scala.Tuple2[Rep[Col[A]], Rep[Int]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(ColElem[_$34, _$35] forSome { 
  type _$34;
  type _$35
})].&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[Col[A]], Rep[Int]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$36] forSome { 
              type _$36
            })): Option[(scala.Tuple2[Rep[Col[A]], Rep[Int]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object reduce {
            def unapply(d: (Def[_$37] forSome { 
              type _$37
            })): Option[(scala.Tuple2[Rep[Col[A]], Rep[NumMonoid[A]]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((m @ _), _*), _) if receiver.elem.isInstanceOf[(ColElem[_$38, _$39] forSome { 
  type _$38;
  type _$39
})].&&(__equal(method.getName, "reduce")) => Some(scala.Tuple2(receiver, m)).asInstanceOf[Option[(scala.Tuple2[Rep[Col[A]], Rep[NumMonoid[A]]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$40] forSome { 
              type _$40
            })): Option[(scala.Tuple2[Rep[Col[A]], Rep[NumMonoid[A]]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object zip {
            def unapply(d: (Def[_$41] forSome { 
              type _$41
            })): Option[(scala.Tuple3[Rep[Col[A]], Rep[Col[B]], Rep[Elem[B]]] forSome { 
              type A;
              type B
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((ys @ _), (emB @ _), _*), _) if receiver.elem.isInstanceOf[(ColElem[_$42, _$43] forSome { 
  type _$42;
  type _$43
})].&&(__equal(method.getName, "zip")) => Some(scala.Tuple3(receiver, ys, emB)).asInstanceOf[Option[(scala.Tuple3[Rep[Col[A]], Rep[Col[B]], Rep[Elem[B]]] forSome { 
                type A;
                type B
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$44] forSome { 
              type _$44
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
            def unapply(d: (Def[_$45] forSome { 
              type _$45
            })): Option[(scala.Tuple2[Rep[Array[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((arr @ _), (emT @ _), _*), _) if __equal(receiver.elem, ColCompanionElem).&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(arr, emT)).asInstanceOf[Option[(scala.Tuple2[Rep[Array[T]], Rep[Elem[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$46] forSome { 
              type _$46
            })): Option[(scala.Tuple2[Rep[Array[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object fromArray {
            def unapply(d: (Def[_$47] forSome { 
              type _$47
            })): Option[(scala.Tuple2[Rep[Array[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((arr @ _), (emT @ _), _*), _) if __equal(receiver.elem, ColCompanionElem).&&(__equal(method.getName, "fromArray")) => Some(scala.Tuple2(arr, emT)).asInstanceOf[Option[(scala.Tuple2[Rep[Array[T]], Rep[Elem[T]]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$48] forSome { 
              type _$48
            })): Option[(scala.Tuple2[Rep[Array[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        }
      };
      object Cols_Module {
        val packageName = "scalanizer.collections";
        val name = "Cols";
        val dump = "H4sIAAAAAAAAALVWTYgjRRSu9Ewmkx92x7/BAcWZIf5rMqgwhzksSTbrD9nJMD0HictCpVPJ9Fpd3VNVGZIFF/EkehOvInvfmxdB8CaIB0+igmdP63pY1MWD4qvq32yn44DYh6K7+r1X733f9173rTsoLzh6SliYYlZziMQ1U983hKyabSZtOb3sDsaUXCTD99Y/ty6zpjDQ+R5aOcbioqA9VPRv2hMvujfJSQcVMbOIkC4XEm119Al1y6WUWNJ2Wd12nLHEfUrqHVvIvQ5a7ruD6Qm6gXIdtGa5zOJEErNFsRBEBPurRGVkR89F/TztevEZrK6qqCeqOOLYlpA+nLHm2x8Sz5wyl00dic4FqXU9lRbYFGzHc7kMjyhAuGN3ED4uMwwb6MHONXyK63DEqG5KbrMReJY9bL2NR2QfTJT5MiQsCB0eTT39vNRBJUFOAKDXHY/qnYmHEAIGXtJJ1GJ8ahE+NYVP1STcxtS+jtXLA+5Opsi/cksITTwI8cK/hAgjkDYbVD+4Yr11zyw7hnKeqFQKusIVCPREhho0FYDj14cfibuv3tw1UKmHSrZo9IXk2JJJygO0ypgxV+qcIwAxHwFb21ls6VMaYHOfJIqW63iYQaQAygrwRG3LlspY7VUCdjKgL0iPhKY5wD2qdzOjXq2bFqb04PbGi0/+0n7TQMbsEUUIaYLweRhUoqWWS4PIaj0vUa6h4VVLcRJuFoDCwdiSMfKwWYlIBs4STr5RdqIRZE/f/nXw1Q66YkRAB3mdjVsIkRc/fl/+7tkLBlrt6U64RPGoB1iLNiVOl7dcJnto1T0l3H9TOMVU3c3lujAgQzymMmAgCd0SQCfRZmbPekThuqf7IxcCUPYlvu8yUr10UP3D/ObjW0rBHFX8N34T/23v/vXTuaHU4gZKMOchxPkG53i6gKBZxEt+WNN1yAPbd+2rNz+UGtvcZHYEdPvXoOf2tN/mApjDUfR7b8f4beOHTw1UBDT7tnSwV905YwP9n00RIREvWwDhI6DqLnCu0Wslz9yKFbyRgPWxXA7FwbaABEIaIeDLSku+sVoeSiIPXZA8S3tH6n08C1ZdxPph52F658KXBsq/gfJDEKXooHzfHbNBiA58ViSZyGa4l5tFB9DAHDsRGvraRIkkJFrVrXKER2G+6f4uo1mBJWbCAml45GjsUfLKF39eff/d1zyts9SAmavc6LGZErL/Si3rCVO1PJoaMCkOo9YLODSg8HRJmWMu7d9f5J9OPk7rGb0+nyXPtQNsw2ii/1GZank51mUzWUvay0p7LcBijn8z2z+Nhfpk+EUmW0KtuzMyjRyP4/IzLNh8tawEWM+mVDwk9tBWH6ZMbc8rPkXnmTmtzOfTU+v1M2LwztxM5KxRAQaSKkKidb814ReJJ36jRJAtR9sZrWsGMxUG+417n+w/9+1nP+tfhZKazvCtYtHPZDxs7m/lgkoB/gwTGUNealrrbP8BsZgAwqkLAAA="
      };
      trait Cols extends Base with NumMonoidsDsl { self: ColsDsl =>
        trait Col[A] extends Reifiable[Col[A]] {
          implicit def eeA: Elem[A];
          def arr: Rep[Array[A]];
          def length: Rep[Int];
          def apply(i: Rep[Int]): Rep[A];
          def map[B](f: Rep[scala.Function1[A, B]])(implicit emB: Elem[B]): Rep[Col[B]] = Col(array_map(arr, f));
          def reduce(implicit m: Rep[NumMonoid[A]]): Rep[A] = array_reduce(arr)(RepMonoid(opName = "+", zero = m.zero, append = m.append, isCommutative = true));
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
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZoIWuGNNbkJzAgAPWgAGaGFzRHNsWgAJaGFzRHNsRXhwWgAJaGFzRHNsU2VxTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwAAAAc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0Q2FsbFDrGS0l7EVYAgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQACk51bU1vbm9pZHNzcQB+AAZzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHEAfgANeHEAfgALc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NEZWbIUpJz8kehNwIACloACmlzQWJzdHJhY3RMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARhcmdzdAAiTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTQ2xhc3NBcmdzO0wABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+ABBMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAc3EAfgAGc3EAfgAIdAADQ29sc3EAfgAGc3EAfgAIdAABQXEAfgALcQB+AA14c3EAfgAIdAAHUHJvZHVjdHEAfgALc3EAfgAIdAAMU2VyaWFsaXphYmxlcQB+AAtxAH4ADXhxAH4AC3NyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzQXJncyfr7mTFMABcAgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZwVz2NYL1Ck/AgAIWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnWgAHdmFsRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGV0ACBMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcGVFeHByO3hwAAAAAXEAfgALc3IAC3NjYWxhLk5vbmUkRlAk9lPKlKwCAAB4cgAMc2NhbGEuT3B0aW9u/mk3/dsOZnQCAAB4cHQAA2FycnNxAH4ACHQABUFycmF5c3EAfgAGc3EAfgAIdAABQXEAfgALcQB+AA14cQB+AA14c3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kRGVm3VlxcbcwKw4CAApaAAxpc0VsZW1PckNvbnRaAAppc0ltcGxpY2l0WgAKaXNPdmVycmlkZUwAC2Fubm90YXRpb25zcQB+AAFMAAthcmdTZWN0aW9uc3EAfgABTAAEYm9keXEAfgADTAAEbmFtZXEAfgAETAAKb3ZlcmxvYWRJZHEAfgADTAAHdHBlQXJnc3EAfgABTAAGdHBlUmVzcQB+AAN4cAAAAHEAfgALcQB+AAtzcgAKc2NhbGEuU29tZREi8mleoYt0AgABTAABeHQAEkxqYXZhL2xhbmcvT2JqZWN0O3hxAH4AI3NyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1NlbGVjdAvdaWVlQPmFAgACTAAEZXhwcnQAHUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU0V4cHI7TAAFdG5hbWVxAH4ABHhwc3IAG3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQXNjcmDEeSeFkj7MAgACTAAEZXhwcnEAfgAyTAACcHRxAH4AIHhwc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTSWRlbnQnEqX78AnXTwIAAUwABG5hbWVxAH4ABHhwdAADYXJyc3EAfgAIdAAFQXJyYXlzcQB+AAZzcQB+AAh0AAFBcQB+AAtxAH4ADXh0AAZsZW5ndGhxAH4APnEAfgAkcQB+AAtxAH4AJHNxAH4ALAAAAHEAfgALc3EAfgAGc3IAIXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kQXJncynCJ5ZedbqLAgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2RBcmcD3P/Lk3JzAwIAB1oAB2ltcEZsYWdaAAxpc0VsZW1PckNvbnRaAAhvdmVyRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGVxAH4AIHhwAAAAcQB+AAtxAH4AJHQAAWlzcgAjc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVQcmltaXRpdmU1w7tV8L5OBQIAAkwAEmRlZmF1bHRWYWx1ZVN0cmluZ3EAfgAETAAEbmFtZXEAfgAEeHB0AAEwdAADSW50cQB+AA14cQB+AA14c3EAfgAuc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQXBwbHmEZimf0pr8fwIAA0wABWFyZ3NzcQB+AAFMAANmdW5xAH4AMkwAAnRzcQB+AAF4cHNxAH4ABnNxAH4ABnNxAH4ANnQAAWlxAH4ADXhxAH4ADXhzcQB+ADFzcQB+ADRzcQB+ADZ0AANhcnJzcQB+AAh0AAVBcnJheXNxAH4ABnNxAH4ACHQAAUFxAH4AC3EAfgANeHQABWFwcGx5cQB+AAtxAH4AW3EAfgAkcQB+AAtxAH4AJHEAfgANeHEAfgAkc3EAfgAccQB+AAt0AAxDb2xPdmVyQXJyYXlxAH4AJHNxAH4ABnNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZUFyZxZSTBRs7T++AgAFSgAFZmxhZ3NMAAVib3VuZHEAfgADTAAMY29udGV4dEJvdW5kcQB+AAFMAARuYW1lcQB+AARMAAd0cGFyYW1zcQB+AAF4cAAAAAAAACAAcQB+ACRzcQB+AAZ0AAhDbGFzc1RhZ3EAfgANeHQAAUFxAH4AC3EAfgANeHNxAH4ADwBzcQB+AAZzcQB+AAh0AANDb2xzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVUdXBsZTS7+V6IgEhwAgABTAAJdHBlU0V4cHJzcQB+AAF4cHNxAH4ABnNxAH4ACHQAAUFxAH4AC3NxAH4ACHQAAUJxAH4AC3EAfgANeHEAfgANeHNxAH4ACHEAfgAZcQB+AAtzcQB+AAhxAH4AG3EAfgALcQB+AA14cQB+AAtzcQB+ABxzcQB+AAZzcQB+AB8AAAABcQB+AAtxAH4AJHQAAmFzc3EAfgAIdAADQ29sc3EAfgAGc3EAfgAIdAABQXEAfgALcQB+AA14c3EAfgAfAAAAAXEAfgALcQB+ACR0AAJic3NxAH4ACHQAA0NvbHNxAH4ABnNxAH4ACHQAAUJxAH4AC3EAfgANeHEAfgANeHNxAH4ABnNxAH4ALAAAAHEAfgALcQB+AAtzcQB+AC5zcQB+AExzcQB+AAZzcQB+AAZzcQB+ADFzcQB+ADZ0AAJic3QAA2FycnEAfgANeHEAfgANeHNxAH4AMXNxAH4AMXNxAH4ANnQAAmFzdAADYXJydAADemlwcQB+AAt0AANhcnJxAH4AJHEAfgALc3EAfgAuc3EAfgAIdAAFQXJyYXlzcQB+AAZzcQB+AGlzcQB+AAZzcQB+AAh0AAFBcQB+AAtzcQB+AAh0AAFCcQB+AAtxAH4ADXhxAH4ADXhzcQB+ACwAAABxAH4AC3EAfgALc3EAfgAuc3EAfgAxc3EAfgA2dAACYXNxAH4APnEAfgA+cQB+ACRxAH4AC3EAfgAkc3EAfgAsAAAAcQB+AAtzcQB+AAZzcQB+AEFzcQB+AAZzcQB+AEQAAABxAH4AC3EAfgAkdAABaXEAfgBIcQB+AA14cQB+AA14c3EAfgAuc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHVwbGVrHimm2vmVLwIAAUwABWV4cHJzcQB+AAF4cHNxAH4ABnNxAH4ATHNxAH4ABnNxAH4ABnNxAH4ANnQAAWlxAH4ADXhxAH4ADXhzcQB+ADZ0AAJhc3EAfgALc3EAfgBMc3EAfgAGc3EAfgAGc3EAfgA2dAABaXEAfgANeHEAfgANeHNxAH4ANnQAAmJzcQB+AAtxAH4ADXhxAH4AW3EAfgAkcQB+AAtxAH4AJHEAfgANeHEAfgAkc3EAfgAccQB+AAt0AAdQYWlyQ29scQB+ACRzcQB+AAZzcQB+AF8AAAAAAAAgAHEAfgAkcQB+AAt0AAFBcQB+AAtzcQB+AF8AAAAAAAAgAHEAfgAkcQB+AAt0AAFCcQB+AAtxAH4ADXhxAH4ADXhzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdERlZvVaMAL0GdefAgAJWgAIYml0bWFwJDBMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAQTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgALcQB+AAtzcQB+AAZzcQB+ACwAAABxAH4AC3EAfgALcQB+ACR0AANhcnJxAH4AJHEAfgALc3EAfgAuc3EAfgAIdAAFQXJyYXlzcQB+AAZzcQB+AAh0AAFBcQB+AAtxAH4ADXhzcQB+ACwAAABxAH4AC3EAfgALcQB+ACRxAH4APnEAfgAkcQB+AAtzcQB+AC5xAH4ASHNxAH4ALAAAAHEAfgALc3EAfgAGc3EAfgBBc3EAfgAGc3EAfgBEAAAAcQB+AAtxAH4AJHQAAWlxAH4ASHEAfgANeHEAfgANeHEAfgAkcQB+AFtxAH4AJHEAfgALc3EAfgAuc3EAfgAIdAABQXEAfgALc3EAfgAsAAAAcQB+AAtzcQB+AAZzcQB+AEFzcQB+AAZzcQB+AEQAAABxAH4AC3EAfgAkdAABZnNyAB5zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZUZ1bmPUSZkTDx1f4QIAAkwABmRvbWFpbnEAfgAgTAAFcmFuZ2VxAH4AIHhwc3EAfgAIdAABQXEAfgALc3EAfgAIdAABQnEAfgALcQB+AA14cQB+AA14c3EAfgAuc3EAfgBMc3EAfgAGc3EAfgAGc3EAfgBMc3EAfgAGc3EAfgAGc3EAfgA2cQB+AN1xAH4ADXhzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUeXBlQXBwbHka36/N9eX6UAIAAkwAA2Z1bnEAfgAyTAACdHNxAH4AAXhwc3EAfgA2dAAKaW1wbGljaXRseXNxAH4ABnNxAH4ACHQADENhbkJ1aWxkRnJvbXNxAH4ABnNxAH4ACHQABUFycmF5c3EAfgAGc3EAfgAIdAABQXEAfgALcQB+AA14c3EAfgAIdAABQnEAfgALc3EAfgAIdAAFQXJyYXlzcQB+AAZzcQB+AAh0AAFCcQB+AAtxAH4ADXhxAH4ADXhxAH4ADXhxAH4ADXhxAH4ADXhzcQB+ADFzcQB+ADRzcQB+ADZ0AANhcnJzcQB+AAh0AAVBcnJheXNxAH4ABnNxAH4ACHQAAUFxAH4AC3EAfgANeHQAA21hcHEAfgALcQB+AA14cQB+AA14c3EAfgA2dAADQ29scQB+AAtxAH4BCnEAfgAkc3EAfgAGc3EAfgBfAAAAAAAAIABxAH4AJHNxAH4ABnQACENsYXNzVGFncQB+AA14dAABQnEAfgALcQB+AA14c3EAfgAuc3EAfgAIdAADQ29sc3EAfgAGc3EAfgAIdAABQnEAfgALcQB+AA14c3EAfgAsAAAAcQB+AAtzcQB+AAZzcQB+AEFzcQB+AAZzcQB+AEQBAABxAH4AC3EAfgAkdAABbXNxAH4ACHQACU51bU1vbm9pZHNxAH4ABnNxAH4ACHQAAUFxAH4AC3EAfgANeHEAfgANeHEAfgANeHNxAH4ALnNxAH4ATHNxAH4ABnNxAH4ABnNxAH4AMXNxAH4ANnQAAW10AAZhcHBlbmRxAH4ADXhxAH4ADXhzcQB+ADFzcQB+ADRzcQB+ADZ0AANhcnJzcQB+AAh0AAVBcnJheXNxAH4ABnNxAH4ACHQAAUFxAH4AC3EAfgANeHQABnJlZHVjZXEAfgALdAAGcmVkdWNlcQB+ACRxAH4AC3NxAH4ALnNxAH4ACHQAAUFxAH4AC3NxAH4ALAAAAHEAfgALc3EAfgAGc3EAfgBBc3EAfgAGc3EAfgBEAAAAcQB+AAtxAH4AJHQAAnlzc3EAfgAIdAADQ29sc3EAfgAGc3EAfgAIdAABQnEAfgALcQB+AA14cQB+AA14cQB+AA14c3EAfgAuc3EAfgBMc3EAfgAGc3EAfgAGc3IAG3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVGhpc+oe0MagX4tnAgABTAAIdHlwZU5hbWVxAH4ABHhwdAAAc3EAfgA2dAACeXNxAH4ADXhxAH4ADXhzcQB+ADZ0AAdQYWlyQ29scQB+AAt0AAN6aXBxAH4AJHNxAH4ABnNxAH4AXwAAAAAAACAAcQB+ACRxAH4AC3QAAUJxAH4AC3EAfgANeHNxAH4ALnNxAH4ACHQAB1BhaXJDb2xzcQB+AAZzcQB+AAh0AAFBcQB+AAtzcQB+AAh0AAFCcQB+AAtxAH4ADXhxAH4ADXhzcQB+AC5zcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNPYmplY3REZWbiZrKG4r59UQIAA0wACWFuY2VzdG9yc3EAfgABTAAEYm9keXEAfgABTAAEbmFtZXEAfgAEeHBxAH4AC3NxAH4ABnNxAH4ALAAAAHEAfgALc3EAfgAGc3EAfgBBc3EAfgAGc3EAfgBEAAAAcQB+AAtxAH4AJHQAA2FycnNxAH4ACHQABUFycmF5c3EAfgAGc3EAfgAIdAABVHEAfgALcQB+AA14cQB+AA14cQB+AA14c3EAfgAuc3EAfgBMc3EAfgAGc3EAfgAGc3EAfgA2dAADYXJycQB+AA14cQB+AA14c3EAfgA2dAAJZnJvbUFycmF5cQB+AAtxAH4AW3EAfgAkc3EAfgAGc3EAfgBfAAAAAAAAIABxAH4AJHNxAH4ABnQACENsYXNzVGFncQB+AA14dAABVHEAfgALcQB+AA14c3EAfgAuc3EAfgAIdAADQ29sc3EAfgAGc3EAfgAIdAABVHEAfgALcQB+AA14c3EAfgAsAAAAcQB+AAtzcQB+AAZzcQB+AEFzcQB+AAZzcQB+AEQAAABxAH4AC3EAfgAkdAADYXJyc3EAfgAIdAAFQXJyYXlzcQB+AAZzcQB+AAh0AAFUcQB+AAtxAH4ADXhxAH4ADXhxAH4ADXhzcQB+AC5zcQB+AExzcQB+AAZzcQB+AAZzcQB+ADZ0AANhcnJxAH4ADXhxAH4ADXhzcQB+ADZ0AAxDb2xPdmVyQXJyYXlxAH4AC3QACWZyb21BcnJheXEAfgAkc3EAfgAGc3EAfgBfAAAAAAAAIABxAH4AJHNxAH4ABnQACENsYXNzVGFncQB+AA14dAABVHEAfgALcQB+AA14c3EAfgAuc3EAfgAIdAADQ29sc3EAfgAGc3EAfgAIdAABVHEAfgALcQB+AA14cQB+AA14dAADQ29scHQAA0NvbHEAfgAkc3EAfgAGc3EAfgBfAAAAAAAAIABxAH4AJHEAfgALdAABQXEAfgALcQB+AA14cQB+AA14cQB+AMNxAH4AJHNxAH4ABnNyACFzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0ltcG9ydFN0YXSsgKBKw8YbIgIAAUwABG5hbWVxAH4ABHhwdAAVc2NhbGFuaXplci5OdW1Nb25vaWRzc3EAfgGidAAlc2NhbGEuY29sbGVjdGlvbi5nZW5lcmljLkNhbkJ1aWxkRnJvbXNxAH4BonQAFnNjYWxhLnJlZmxlY3QuQ2xhc3NUYWdxAH4ADXhxAH4AC3QABENvbHN0ABZzY2FsYW5pemVyLmNvbGxlY3Rpb25zcQB+ACRxAH4AJA=="
    }

    object HotSpotKernels {
      import java.io.File;
      import scalan.compilation.GraphVizConfig;
      import scala.language.reflectiveCalls
    }

    object HotSpotManager {
      import scalan.ScalanCommunityDslExp;
      import scalan.compilation.lms.scalac.CommunityLmsCompilerScala;
      import scalan.{CommunityMethodMappingDSL, JNIExtractorOpsExp};
      import scalan.compilation.lms.CommunityBridge;
      import scalanizer.collections.implOfCols.StagedEvaluation._;
      lazy val prog = {
        final class $anon extends ColsDslExp with ScalanCommunityDslExp with JNIExtractorOpsExp;
        new $anon()
      };
      lazy val compiler = {
        final class $anon extends CommunityLmsCompilerScala(prog) with CommunityBridge with CommunityMethodMappingDSL;
        new $anon()
      };
      def getScalanContext = compiler;
      import scalan.compilation.lms.uni.LmsCompilerUni;
      lazy val progUni = {
        final class $anon extends ColsDslExp with ScalanCommunityDslExp with JNIExtractorOpsExp;
        new $anon()
      };
      lazy val compilerUni = {
        final class $anon extends LmsCompilerUni(progUni) with CommunityBridge with CommunityMethodMappingDSL;
        new $anon()
      };
      def getScalanContextUni = compilerUni
    }
  }
}