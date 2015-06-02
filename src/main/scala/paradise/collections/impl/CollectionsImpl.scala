package paradise.collections {
  package implOfCollections {
    object StagedEvaluation {
      import scalan._;
      import paradise.implOfNumMonoids.StagedEvaluation._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait CollectionsAbs extends Collections with ScalanDsl { self: CollectionsDsl =>
        implicit def proxyCollection[A](p: Rep[Collection[A]]): Collection[A] = proxyOps[Collection[A]](p)(classTag[Collection[A]]);
        class CollectionElem[A, To <: Collection[A]](implicit val eA: Elem[A]) extends EntityElem[To] {
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagA = eA.tag;
            weakTypeTag[Collection[A]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$1] forSome { 
            type _$1
          })]) = {
            val conv = fun(((x: Rep[Collection[A]]) => convertCollection(x)));
            tryConvert(element[Collection[A]], this, x, conv)
          };
          def convertCollection(x: Rep[Collection[A]]): Rep[To] = {
            assert(x.selfType1 match {
              case ((_): CollectionElem[(_), (_)]) => true
              case _ => false
            });
            x.asRep[To]
          };
          override def getDefaultRep: Rep[To] = ???
        };
        implicit def collectionElement[A](implicit eA: Elem[A]): Elem[Collection[A]] = new CollectionElem[A, Collection[A]]();
        implicit case object CollectionCompanionElem extends CompanionElem[CollectionCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[CollectionCompanionAbs];
          protected def getDefaultRep = Collection
        };
        abstract class CollectionCompanionAbs extends CompanionBase[CollectionCompanionAbs] with CollectionCompanion {
          override def toString = "Collection"
        };
        def Collection: Rep[CollectionCompanionAbs];
        implicit def proxyCollectionCompanion(p: Rep[CollectionCompanion]): CollectionCompanion = proxyOps[CollectionCompanion](p);
        class CollectionOverArrayElem[A](val iso: Iso[CollectionOverArrayData[A], CollectionOverArray[A]])(implicit eA: Elem[A]) extends CollectionElem[A, CollectionOverArray[A]] with ConcreteElem[CollectionOverArrayData[A], CollectionOverArray[A]] {
          override def convertCollection(x: Rep[Collection[A]]) = CollectionOverArray(x.arr);
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = {
            implicit val tagA = eA.tag;
            weakTypeTag[CollectionOverArray[A]]
          }
        };
        type CollectionOverArrayData[A] = Array[A];
        class CollectionOverArrayIso[A](implicit eA: Elem[A]) extends Iso[CollectionOverArrayData[A], CollectionOverArray[A]] {
          override def from(p: Rep[CollectionOverArray[A]]) = p.arr;
          override def to(p: Rep[Array[A]]) = {
            val arr = p;
            CollectionOverArray(arr)
          };
          lazy val defaultRepTo = Default.defaultVal[Rep[CollectionOverArray[A]]](CollectionOverArray(element[Array[A]].defaultRepValue));
          lazy val eTo = new CollectionOverArrayElem[A](this)
        };
        abstract class CollectionOverArrayCompanionAbs extends CompanionBase[CollectionOverArrayCompanionAbs] with CollectionOverArrayCompanion {
          override def toString = "CollectionOverArray";
          def apply[A](arr: Rep[Array[A]])(implicit eA: Elem[A]): Rep[CollectionOverArray[A]] = mkCollectionOverArray(arr)
        };
        object CollectionOverArrayMatcher {
          def unapply[A](p: Rep[Collection[A]]) = unmkCollectionOverArray(p)
        };
        def CollectionOverArray: Rep[CollectionOverArrayCompanionAbs];
        implicit def proxyCollectionOverArrayCompanion(p: Rep[CollectionOverArrayCompanionAbs]): CollectionOverArrayCompanionAbs = proxyOps[CollectionOverArrayCompanionAbs](p);
        implicit case object CollectionOverArrayCompanionElem extends CompanionElem[CollectionOverArrayCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[CollectionOverArrayCompanionAbs];
          protected def getDefaultRep = CollectionOverArray
        };
        implicit def proxyCollectionOverArray[A](p: Rep[CollectionOverArray[A]]): CollectionOverArray[A] = proxyOps[CollectionOverArray[A]](p);
        implicit class ExtendedCollectionOverArray[A](p: Rep[CollectionOverArray[A]])(implicit eA: Elem[A]) {
          def toData: Rep[CollectionOverArrayData[A]] = isoCollectionOverArray(eA).from(p)
        };
        implicit def isoCollectionOverArray[A](implicit eA: Elem[A]): Iso[CollectionOverArrayData[A], CollectionOverArray[A]] = new CollectionOverArrayIso[A]();
        def mkCollectionOverArray[A](arr: Rep[Array[A]])(implicit eA: Elem[A]): Rep[CollectionOverArray[A]];
        def unmkCollectionOverArray[A](p: Rep[Collection[A]]): Option[Rep[Array[A]]];
        class PairCollectionElem[A, B](val iso: Iso[PairCollectionData[A, B], PairCollection[A, B]])(implicit eA: Elem[A], eB: Elem[B]) extends CollectionElem[scala.Tuple2[A, B], PairCollection[A, B]] with ConcreteElem[PairCollectionData[A, B], PairCollection[A, B]] {
          override def convertCollection(x: Rep[Collection[scala.Tuple2[A, B]]]) = !!!("Cannot convert from Collection to PairCollection: missing fields List(as, bs)");
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = {
            implicit val tagA = eA.tag;
            implicit val tagB = eB.tag;
            weakTypeTag[PairCollection[A, B]]
          }
        };
        type PairCollectionData[A, B] = scala.Tuple2[Collection[A], Collection[B]];
        class PairCollectionIso[A, B](implicit eA: Elem[A], eB: Elem[B]) extends Iso[PairCollectionData[A, B], PairCollection[A, B]]()(pairElement(implicitly[Elem[Collection[A]]], implicitly[Elem[Collection[B]]])) {
          override def from(p: Rep[PairCollection[A, B]]) = scala.Tuple2(p.as, p.bs);
          override def to(p: Rep[scala.Tuple2[Collection[A], Collection[B]]]) = {
            val x$1 = (p: @scala.unchecked) match {
              case Pair((as @ _), (bs @ _)) => scala.Tuple2(as, bs)
            };
            val as = x$1._1;
            val bs = x$1._2;
            PairCollection(as, bs)
          };
          lazy val defaultRepTo = Default.defaultVal[Rep[PairCollection[A, B]]](PairCollection(element[Collection[A]].defaultRepValue, element[Collection[B]].defaultRepValue));
          lazy val eTo = new PairCollectionElem[A, B](this)
        };
        abstract class PairCollectionCompanionAbs extends CompanionBase[PairCollectionCompanionAbs] with PairCollectionCompanion {
          override def toString = "PairCollection";
          def apply[A, B](p: Rep[PairCollectionData[A, B]])(implicit eA: Elem[A], eB: Elem[B]): Rep[PairCollection[A, B]] = isoPairCollection(eA, eB).to(p);
          def apply[A, B](as: Rep[Collection[A]], bs: Rep[Collection[B]])(implicit eA: Elem[A], eB: Elem[B]): Rep[PairCollection[A, B]] = mkPairCollection(as, bs)
        };
        object PairCollectionMatcher {
          def unapply[A, B](p: Rep[Collection[scala.Tuple2[A, B]]]) = unmkPairCollection(p)
        };
        def PairCollection: Rep[PairCollectionCompanionAbs];
        implicit def proxyPairCollectionCompanion(p: Rep[PairCollectionCompanionAbs]): PairCollectionCompanionAbs = proxyOps[PairCollectionCompanionAbs](p);
        implicit case object PairCollectionCompanionElem extends CompanionElem[PairCollectionCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[PairCollectionCompanionAbs];
          protected def getDefaultRep = PairCollection
        };
        implicit def proxyPairCollection[A, B](p: Rep[PairCollection[A, B]]): PairCollection[A, B] = proxyOps[PairCollection[A, B]](p);
        implicit class ExtendedPairCollection[A, B](p: Rep[PairCollection[A, B]])(implicit eA: Elem[A], eB: Elem[B]) {
          def toData: Rep[PairCollectionData[A, B]] = isoPairCollection(eA, eB).from(p)
        };
        implicit def isoPairCollection[A, B](implicit eA: Elem[A], eB: Elem[B]): Iso[PairCollectionData[A, B], PairCollection[A, B]] = new PairCollectionIso[A, B]();
        def mkPairCollection[A, B](as: Rep[Collection[A]], bs: Rep[Collection[B]])(implicit eA: Elem[A], eB: Elem[B]): Rep[PairCollection[A, B]];
        def unmkPairCollection[A, B](p: Rep[Collection[scala.Tuple2[A, B]]]): Option[scala.Tuple2[Rep[Collection[A]], Rep[Collection[B]]]]
      };
      trait CollectionsSeq extends CollectionsDsl with ScalanSeq { self: CollectionsDslSeq =>
        lazy val Collection: Rep[CollectionCompanionAbs] = {
          final class $anon extends CollectionCompanionAbs with UserTypeSeq[CollectionCompanionAbs] {
            lazy val selfType = element[CollectionCompanionAbs]
          };
          new $anon()
        };
        case class SeqCollectionOverArray[A](override val arr: Rep[Array[A]])(implicit eA: Elem[A]) extends CollectionOverArray[A](arr) with UserTypeSeq[CollectionOverArray[A]] {
          lazy val selfType = element[CollectionOverArray[A]]
        };
        lazy val CollectionOverArray = {
          final class $anon extends CollectionOverArrayCompanionAbs with UserTypeSeq[CollectionOverArrayCompanionAbs] {
            lazy val selfType = element[CollectionOverArrayCompanionAbs]
          };
          new $anon()
        };
        def mkCollectionOverArray[A](arr: Rep[Array[A]])(implicit eA: Elem[A]): Rep[CollectionOverArray[A]] = new SeqCollectionOverArray[A](arr);
        def unmkCollectionOverArray[A](p: Rep[Collection[A]]) = p match {
          case (p @ ((_): CollectionOverArray[A] @unchecked)) => Some(p.arr)
          case _ => None
        };
        case class SeqPairCollection[A, B](override val as: Rep[Collection[A]], override val bs: Rep[Collection[B]])(implicit eA: Elem[A], eB: Elem[B]) extends PairCollection[A, B](as, bs) with UserTypeSeq[PairCollection[A, B]] {
          lazy val selfType = element[PairCollection[A, B]]
        };
        lazy val PairCollection = {
          final class $anon extends PairCollectionCompanionAbs with UserTypeSeq[PairCollectionCompanionAbs] {
            lazy val selfType = element[PairCollectionCompanionAbs]
          };
          new $anon()
        };
        def mkPairCollection[A, B](as: Rep[Collection[A]], bs: Rep[Collection[B]])(implicit eA: Elem[A], eB: Elem[B]): Rep[PairCollection[A, B]] = new SeqPairCollection[A, B](as, bs);
        def unmkPairCollection[A, B](p: Rep[Collection[scala.Tuple2[A, B]]]) = p match {
          case (p @ ((_): PairCollection[A, B] @unchecked)) => Some(scala.Tuple2(p.as, p.bs))
          case _ => None
        }
      };
      trait CollectionsExp extends CollectionsDsl with ScalanExp { self: CollectionsDslExp =>
        lazy val Collection: Rep[CollectionCompanionAbs] = {
          final class $anon extends CollectionCompanionAbs with UserTypeDef[CollectionCompanionAbs] {
            lazy val selfType = element[CollectionCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        case class ExpCollectionOverArray[A](override val arr: Rep[Array[A]])(implicit eA: Elem[A]) extends CollectionOverArray[A](arr) with UserTypeDef[CollectionOverArray[A]] {
          lazy val selfType = element[CollectionOverArray[A]];
          override def mirror(t: Transformer) = ExpCollectionOverArray[A](t(arr))
        };
        lazy val CollectionOverArray: Rep[CollectionOverArrayCompanionAbs] = {
          final class $anon extends CollectionOverArrayCompanionAbs with UserTypeDef[CollectionOverArrayCompanionAbs] {
            lazy val selfType = element[CollectionOverArrayCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object CollectionOverArrayMethods {
          object length {
            def unapply(d: (Def[_$2] forSome { 
              type _$2
            })): Option[(Rep[CollectionOverArray[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(CollectionOverArrayElem[_$3] forSome { 
  type _$3
})].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[(Rep[CollectionOverArray[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$4] forSome { 
              type _$4
            })): Option[(Rep[CollectionOverArray[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$5] forSome { 
              type _$5
            })): Option[(scala.Tuple2[Rep[CollectionOverArray[A]], Rep[Int]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(CollectionOverArrayElem[_$6] forSome { 
  type _$6
})].&&(method.getName.==("apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[CollectionOverArray[A]], Rep[Int]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$7] forSome { 
              type _$7
            })): Option[(scala.Tuple2[Rep[CollectionOverArray[A]], Rep[Int]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object CollectionOverArrayCompanionMethods;
        def mkCollectionOverArray[A](arr: Rep[Array[A]])(implicit eA: Elem[A]): Rep[CollectionOverArray[A]] = new ExpCollectionOverArray[A](arr);
        def unmkCollectionOverArray[A](p: Rep[Collection[A]]) = p.elem.asInstanceOf[(Elem[_$8] forSome { 
          type _$8
        })] match {
          case ((_): CollectionOverArrayElem[A] @unchecked) => Some(p.asRep[CollectionOverArray[A]].arr)
          case _ => None
        };
        case class ExpPairCollection[A, B](override val as: Rep[Collection[A]], override val bs: Rep[Collection[B]])(implicit eA: Elem[A], eB: Elem[B]) extends PairCollection[A, B](as, bs) with UserTypeDef[PairCollection[A, B]] {
          lazy val selfType = element[PairCollection[A, B]];
          override def mirror(t: Transformer) = ExpPairCollection[A, B](t(as), t(bs))
        };
        lazy val PairCollection: Rep[PairCollectionCompanionAbs] = {
          final class $anon extends PairCollectionCompanionAbs with UserTypeDef[PairCollectionCompanionAbs] {
            lazy val selfType = element[PairCollectionCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object PairCollectionMethods {
          object arr {
            def unapply(d: (Def[_$9] forSome { 
              type _$9
            })): Option[(Rep[PairCollection[A, B]] forSome { 
              type A;
              type B
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PairCollectionElem[_$10, _$11] forSome { 
  type _$10;
  type _$11
})].&&(method.getName.==("arr")) => Some(receiver).asInstanceOf[Option[(Rep[PairCollection[A, B]] forSome { 
                type A;
                type B
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$12] forSome { 
              type _$12
            })): Option[(Rep[PairCollection[A, B]] forSome { 
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
            })): Option[(Rep[PairCollection[A, B]] forSome { 
              type A;
              type B
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PairCollectionElem[_$14, _$15] forSome { 
  type _$14;
  type _$15
})].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[(Rep[PairCollection[A, B]] forSome { 
                type A;
                type B
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$16] forSome { 
              type _$16
            })): Option[(Rep[PairCollection[A, B]] forSome { 
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
            })): Option[(scala.Tuple2[Rep[PairCollection[A, B]], Rep[Int]] forSome { 
              type A;
              type B
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(PairCollectionElem[_$18, _$19] forSome { 
  type _$18;
  type _$19
})].&&(method.getName.==("apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[PairCollection[A, B]], Rep[Int]] forSome { 
                type A;
                type B
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$20] forSome { 
              type _$20
            })): Option[(scala.Tuple2[Rep[PairCollection[A, B]], Rep[Int]] forSome { 
              type A;
              type B
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object PairCollectionCompanionMethods;
        def mkPairCollection[A, B](as: Rep[Collection[A]], bs: Rep[Collection[B]])(implicit eA: Elem[A], eB: Elem[B]): Rep[PairCollection[A, B]] = new ExpPairCollection[A, B](as, bs);
        def unmkPairCollection[A, B](p: Rep[Collection[scala.Tuple2[A, B]]]) = p.elem.asInstanceOf[(Elem[_$21] forSome { 
          type _$21
        })] match {
          case ((_): PairCollectionElem[A, B] @unchecked) => Some(scala.Tuple2(p.asRep[PairCollection[A, B]].as, p.asRep[PairCollection[A, B]].bs))
          case _ => None
        };
        object CollectionMethods {
          object arr {
            def unapply(d: (Def[_$22] forSome { 
              type _$22
            })): Option[(Rep[Collection[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(CollectionElem[_$23, _$24] forSome { 
  type _$23;
  type _$24
})].&&(method.getName.==("arr")) => Some(receiver).asInstanceOf[Option[(Rep[Collection[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$25] forSome { 
              type _$25
            })): Option[(Rep[Collection[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object length {
            def unapply(d: (Def[_$26] forSome { 
              type _$26
            })): Option[(Rep[Collection[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(CollectionElem[_$27, _$28] forSome { 
  type _$27;
  type _$28
})].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[(Rep[Collection[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$29] forSome { 
              type _$29
            })): Option[(Rep[Collection[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$30] forSome { 
              type _$30
            })): Option[(scala.Tuple2[Rep[Collection[A]], Rep[Int]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(CollectionElem[_$31, _$32] forSome { 
  type _$31;
  type _$32
})].&&(method.getName.==("apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[Collection[A]], Rep[Int]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$33] forSome { 
              type _$33
            })): Option[(scala.Tuple2[Rep[Collection[A]], Rep[Int]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object reduce {
            def unapply(d: (Def[_$34] forSome { 
              type _$34
            })): Option[(scala.Tuple2[Rep[Collection[A]], Rep[NumMonoid[A]]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((m @ _), _*), _) if receiver.elem.isInstanceOf[(CollectionElem[_$35, _$36] forSome { 
  type _$35;
  type _$36
})].&&(method.getName.==("reduce")) => Some(scala.Tuple2(receiver, m)).asInstanceOf[Option[(scala.Tuple2[Rep[Collection[A]], Rep[NumMonoid[A]]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$37] forSome { 
              type _$37
            })): Option[(scala.Tuple2[Rep[Collection[A]], Rep[NumMonoid[A]]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object zip {
            def unapply(d: (Def[_$38] forSome { 
              type _$38
            })): Option[(scala.Tuple3[Rep[Collection[A]], Rep[Collection[B]], Rep[Elem[B]]] forSome { 
              type A;
              type B
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((ys @ _), (eB @ _), _*), _) if receiver.elem.isInstanceOf[(CollectionElem[_$39, _$40] forSome { 
  type _$39;
  type _$40
})].&&(method.getName.==("zip")) => Some(scala.Tuple3(receiver, ys, eB)).asInstanceOf[Option[(scala.Tuple3[Rep[Collection[A]], Rep[Collection[B]], Rep[Elem[B]]] forSome { 
                type A;
                type B
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$41] forSome { 
              type _$41
            })): Option[(scala.Tuple3[Rep[Collection[A]], Rep[Collection[B]], Rep[Elem[B]]] forSome { 
              type A;
              type B
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object CollectionCompanionMethods {
          object apply {
            def unapply(d: (Def[_$42] forSome { 
              type _$42
            })): Option[(scala.Tuple2[Rep[Array[T]], Rep[Elem[T]]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((arr @ _), (eT @ _), _*), _) if receiver.elem.==(CollectionCompanionElem).&&(method.getName.==("apply")) => Some(scala.Tuple2(arr, eT)).asInstanceOf[Option[(scala.Tuple2[Rep[Array[T]], Rep[Elem[T]]] forSome { 
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
              case MethodCall((receiver @ _), (method @ _), Seq((arr @ _), (eT @ _), _*), _) if receiver.elem.==(CollectionCompanionElem).&&(method.getName.==("fromArray")) => Some(scala.Tuple2(arr, eT)).asInstanceOf[Option[(scala.Tuple2[Rep[Array[T]], Rep[Elem[T]]] forSome { 
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
      trait Collections extends Base with NumMonoidsDsl { self: CollectionsDsl =>
        trait Collection[A] extends Reifiable[Collection[A]] {
          implicit def eA: Elem[A];
          def arr: Rep[Array[A]];
          def length: Rep[Int];
          def apply(i: Rep[Int]): Rep[A];
          def map[B](f: Rep[scala.Function1[A, B]])(implicit eB: Elem[B]): Rep[Collection[B]] = Collection(array_map(arr, f));
          def reduce(implicit m: NumMonoid[A]): Rep[A] = {
            implicit val monoid = RepMonoid(opName = "+", zero = m.zero, append = m.append, isCommutative = true)
            array_reduce(arr)
          };
          def zip[B](ys: Rep[Collection[B]])(implicit eB: Elem[B]): Rep[PairCollection[A, B]] = PairCollection(this, ys)
        };
        abstract class CollectionOverArray[A](val arr: Rep[Array[A]])(implicit val eA: Elem[A]) extends Collection[A] with Product with Serializable {
          def length = array_length(arr);
          def apply(i: Rep[Int]) = array_apply(arr, i);
        };
        abstract class PairCollection[A, B](val as: Rep[Collection[A]], val bs: Rep[Collection[B]])(implicit val eA: Elem[A], val eB: Elem[B]) extends Collection[scala.Tuple2[A, B]] with Product with Serializable {
          def arr: Rep[Array[scala.Tuple2[A, B]]] = as.arr.zip(bs.arr);
          def length = as.length;
          def apply(i: Rep[Int]) = Tuple(as(i), bs(i))
        };
        trait CollectionCompanion {
          def apply[T](arr: Rep[Array[T]])(implicit eT: Elem[T]): Rep[Collection[T]] = fromArray(arr);
          def fromArray[T](arr: Rep[Array[T]])(implicit eT: Elem[T]): Rep[Collection[T]] = CollectionOverArray(arr)
        };
        trait CollectionOverArrayCompanion;
        trait PairCollectionCompanion
      };
      trait CollectionsDsl extends CollectionsAbs with NumMonoidsDsl { self: CollectionsDsl =>
        
      };
      trait CollectionsDslSeq extends CollectionsSeq with NumMonoidsDslSeq { self: CollectionsDslSeq =>
        
      };
      trait CollectionsDslExp extends CollectionsExp with NumMonoidsDslExp { self: CollectionsDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0Q2FsbFDrGS0l7EVYAgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQACk51bU1vbm9pZHNzcQB+AAZzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHEAfgANeHEAfgALc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NEZWYH0H1o8ahwMwIACloACmlzQWJzdHJhY3RMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARhcmdzdAAiTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTQ2xhc3NBcmdzO0wABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+ABBMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAc3EAfgAGc3EAfgAIdAAKQ29sbGVjdGlvbnNxAH4ABnNxAH4ACHQAAUFxAH4AC3EAfgANeHNxAH4ACHQAB1Byb2R1Y3RxAH4AC3NxAH4ACHQADFNlcmlhbGl6YWJsZXEAfgALcQB+AA14cQB+AAtzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZ3Mn6+5kxTAAXAIAAUwABGFyZ3NxAH4AAXhwc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NBcmcFc9jWC9QpPwIACFoAB2ltcEZsYWdaAAxpc0VsZW1PckNvbnRaAAhvdmVyRmxhZ1oAB3ZhbEZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBldAAgTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTVHBlRXhwcjt4cAAAAAFxAH4AC3NyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHIADHNjYWxhLk9wdGlvbv5pN/3bDmZ0AgAAeHB0AANhcnJzcQB+AAh0AAVBcnJheXNxAH4ABnNxAH4ACHQAAUFxAH4AC3EAfgANeHEAfgANeHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZERlZt1ZcXG3MCsOAgAKWgAMaXNFbGVtT3JDb250WgAKaXNJbXBsaWNpdFoACmlzT3ZlcnJpZGVMAAthbm5vdGF0aW9uc3EAfgABTAALYXJnU2VjdGlvbnNxAH4AAUwABGJvZHlxAH4AA0wABG5hbWVxAH4ABEwACm92ZXJsb2FkSWRxAH4AA0wAB3RwZUFyZ3NxAH4AAUwABnRwZVJlc3EAfgADeHAAAABxAH4AC3EAfgALc3IACnNjYWxhLlNvbWURIvJpXqGLdAIAAUwAAXh0ABJMamF2YS9sYW5nL09iamVjdDt4cQB+ACNzcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxlY3QL3WllZUD5hQIAAkwABGV4cHJ0AB1Mc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNFeHByO0wABXRuYW1lcQB+AAR4cHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0lkZW50JxKl+/AJ108CAAFMAARuYW1lcQB+AAR4cHQAA2FycnQABmxlbmd0aHEAfgA3cQB+ACRxAH4AC3EAfgAkc3EAfgAsAAAAcQB+AAtzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2RBcmdzKcInll51uosCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZwPc/8uTcnMDAgAHWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXEAfgAgeHAAAABxAH4AC3EAfgAkdAABaXNyACNzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZVByaW1pdGl2ZTXDu1Xwvk4FAgACTAASZGVmYXVsdFZhbHVlU3RyaW5ncQB+AARMAARuYW1lcQB+AAR4cHQAATB0AANJbnRxAH4ADXhxAH4ADXhzcQB+AC5zcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNBcHBseYRmKZ/Smvx/AgADTAAFYXJnc3NxAH4AAUwAA2Z1bnEAfgAyTAACdHNxAH4AAXhwc3EAfgAGc3EAfgAGc3EAfgA0dAABaXEAfgANeHEAfgANeHNxAH4ANHQAA2FycnEAfgALdAAFYXBwbHlxAH4AJHEAfgALcQB+ACRxAH4ADXhxAH4AJHNxAH4AHHEAfgALdAATQ29sbGVjdGlvbk92ZXJBcnJheXEAfgAkc3EAfgAGc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlQXJnklsmSHLxm4kCAARMAAVib3VuZHEAfgADTAAMY29udGV4dEJvdW5kcQB+AAFMAARuYW1lcQB+AARMAAd0cGFyYW1zcQB+AAF4cHEAfgAkc3EAfgAGdAAIQ2xhc3NUYWdxAH4ADXh0AAFBcQB+AAtxAH4ADXhzcQB+AA8Ac3EAfgAGc3EAfgAIdAAKQ29sbGVjdGlvbnNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZVR1cGxlNLv5XoiASHACAAFMAAl0cGVTRXhwcnNxAH4AAXhwc3EAfgAGc3EAfgAIdAABQXEAfgALc3EAfgAIdAABQnEAfgALcQB+AA14cQB+AA14c3EAfgAIcQB+ABlxAH4AC3NxAH4ACHEAfgAbcQB+AAtxAH4ADXhxAH4AC3NxAH4AHHNxAH4ABnNxAH4AHwAAAAFxAH4AC3EAfgAkdAACYXNzcQB+AAh0AApDb2xsZWN0aW9uc3EAfgAGc3EAfgAIdAABQXEAfgALcQB+AA14c3EAfgAfAAAAAXEAfgALcQB+ACR0AAJic3NxAH4ACHQACkNvbGxlY3Rpb25zcQB+AAZzcQB+AAh0AAFCcQB+AAtxAH4ADXhxAH4ADXhzcQB+AAZzcQB+ACwAAABxAH4AC3EAfgALc3EAfgAuc3EAfgBFc3EAfgAGc3EAfgAGc3EAfgAxc3EAfgA0dAACYnN0AANhcnJxAH4ADXhxAH4ADXhzcQB+ADFzcQB+ADFzcQB+ADR0AAJhc3QAA2FycnQAA3ppcHEAfgALdAADYXJycQB+ACRxAH4AC3NxAH4ALnNxAH4ACHQABUFycmF5c3EAfgAGc3EAfgBbc3EAfgAGc3EAfgAIdAABQXEAfgALc3EAfgAIdAABQnEAfgALcQB+AA14cQB+AA14c3EAfgAsAAAAcQB+AAtxAH4AC3NxAH4ALnNxAH4AMXNxAH4ANHQAAmFzcQB+ADdxAH4AN3EAfgAkcQB+AAtxAH4AJHNxAH4ALAAAAHEAfgALc3EAfgAGc3EAfgA6c3EAfgAGc3EAfgA9AAAAcQB+AAtxAH4AJHQAAWlxAH4AQXEAfgANeHEAfgANeHNxAH4ALnNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1R1cGxlax4pptr5lS8CAAFMAAVleHByc3EAfgABeHBzcQB+AAZzcQB+AEVzcQB+AAZzcQB+AAZzcQB+ADR0AAFpcQB+AA14cQB+AA14c3EAfgA0dAACYXNxAH4AC3NxAH4ARXNxAH4ABnNxAH4ABnNxAH4ANHQAAWlxAH4ADXhxAH4ADXhzcQB+ADR0AAJic3EAfgALcQB+AA14cQB+AE1xAH4AJHEAfgALcQB+ACRxAH4ADXhxAH4AJHNxAH4AHHEAfgALdAAOUGFpckNvbGxlY3Rpb25xAH4AJHNxAH4ABnNxAH4AUXEAfgAkcQB+AAt0AAFBcQB+AAtzcQB+AFFxAH4AJHEAfgALdAABQnEAfgALcQB+AA14cQB+AA14c3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHJhaXREZWYBvjSSNBSxsgIACVoACGJpdG1hcCQwTAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4AEEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABxAH4AC3EAfgALc3EAfgAGc3EAfgAsAAAAcQB+AAtxAH4AC3EAfgAkdAADYXJycQB+ACRxAH4AC3NxAH4ALnNxAH4ACHQABUFycmF5c3EAfgAGc3EAfgAIdAABQXEAfgALcQB+AA14c3EAfgAsAAAAcQB+AAtxAH4AC3EAfgAkcQB+ADdxAH4AJHEAfgALc3EAfgAucQB+AEFzcQB+ACwAAABxAH4AC3NxAH4ABnNxAH4AOnNxAH4ABnNxAH4APQAAAHEAfgALcQB+ACR0AAFpcQB+AEFxAH4ADXhxAH4ADXhxAH4AJHEAfgBNcQB+ACRxAH4AC3NxAH4ALnNxAH4ACHQAAUFxAH4AC3NxAH4ALAAAAHEAfgALc3EAfgAGc3EAfgA6c3EAfgAGc3EAfgA9AAAAcQB+AAtxAH4AJHQAAWZzcgAec2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVGdW5j1EmZEw8dX+ECAAJMAAZkb21haW5xAH4AIEwABXJhbmdlcQB+ACB4cHNxAH4ACHQAAUFxAH4AC3NxAH4ACHQAAUJxAH4AC3EAfgANeHEAfgANeHNxAH4ALnNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ANHEAfgDPcQB+AA14c3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHlwZUFwcGx5Gt+vzfXl+lACAAJMAANmdW5xAH4AMkwAAnRzcQB+AAF4cHNxAH4ANHQACmltcGxpY2l0bHlzcQB+AAZzcQB+AAh0AAxDYW5CdWlsZEZyb21zcQB+AAZzcQB+AAh0AAVBcnJheXNxAH4ABnNxAH4ACHQAAUFxAH4AC3EAfgANeHNxAH4ACHQAAUJxAH4AC3NxAH4ACHQABUFycmF5c3EAfgAGc3EAfgAIdAABQnEAfgALcQB+AA14cQB+AA14cQB+AA14cQB+AA14cQB+AA14c3EAfgAxc3EAfgA0dAADYXJydAADbWFwcQB+AAtxAH4ADXhxAH4ADXhzcQB+ADR0AApDb2xsZWN0aW9ucQB+AAtxAH4A9nEAfgAkc3EAfgAGc3EAfgBRcQB+ACRzcQB+AAZ0AAhDbGFzc1RhZ3EAfgANeHQAAUJxAH4AC3EAfgANeHNxAH4ALnNxAH4ACHQACkNvbGxlY3Rpb25zcQB+AAZzcQB+AAh0AAFCcQB+AAtxAH4ADXhzcQB+ACwAAABxAH4AC3NxAH4ABnNxAH4AOnNxAH4ABnNxAH4APQEAAHEAfgALcQB+ACR0AAFtc3EAfgAIdAAJTnVtTW9ub2lkc3EAfgAGc3EAfgAIdAABQXEAfgALcQB+AA14cQB+AA14cQB+AA14c3EAfgAuc3EAfgBFc3EAfgAGc3EAfgAGc3EAfgAxc3EAfgA0dAABbXQABmFwcGVuZHEAfgANeHEAfgANeHNxAH4AMXNxAH4ANHQAA2FycnQABnJlZHVjZXEAfgALdAAGcmVkdWNlcQB+ACRxAH4AC3NxAH4ALnNxAH4ACHQAAUFxAH4AC3NxAH4ALAAAAHEAfgALc3EAfgAGc3EAfgA6c3EAfgAGc3EAfgA9AAAAcQB+AAtxAH4AJHQAAnlzc3EAfgAIdAAKQ29sbGVjdGlvbnNxAH4ABnNxAH4ACHQAAUJxAH4AC3EAfgANeHEAfgANeHEAfgANeHNxAH4ALnNxAH4ARXNxAH4ABnNxAH4ABnNyABtzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RoaXPqHtDGoF+LZwIAAUwACHR5cGVOYW1lcQB+AAR4cHQAAHNxAH4ANHQAAnlzcQB+AA14cQB+AA14c3EAfgA0dAAOUGFpckNvbGxlY3Rpb25xAH4AC3QAA3ppcHEAfgAkc3EAfgAGc3EAfgBRcQB+ACRxAH4AC3QAAUJxAH4AC3EAfgANeHNxAH4ALnNxAH4ACHQADlBhaXJDb2xsZWN0aW9uc3EAfgAGc3EAfgAIdAABQXEAfgALc3EAfgAIdAABQnEAfgALcQB+AA14cQB+AA14c3EAfgAuc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTT2JqZWN0RGVmgFXRk3QJemMCAANMAAlhbmNlc3RvcnNxAH4AAUwABGJvZHlxAH4AAUwABG5hbWVxAH4ABHhwcQB+AAtzcQB+AAZzcQB+ACwAAABxAH4AC3NxAH4ABnNxAH4AOnNxAH4ABnNxAH4APQAAAHEAfgALcQB+ACR0AANhcnJzcQB+AAh0AAVBcnJheXNxAH4ABnNxAH4ACHQAAVRxAH4AC3EAfgANeHEAfgANeHEAfgANeHNxAH4ALnNxAH4ARXNxAH4ABnNxAH4ABnNxAH4ANHQAA2FycnEAfgANeHEAfgANeHNxAH4ANHQACWZyb21BcnJheXEAfgALcQB+AE1xAH4AJHNxAH4ABnNxAH4AUXEAfgAkc3EAfgAGdAAIQ2xhc3NUYWdxAH4ADXh0AAFUcQB+AAtxAH4ADXhzcQB+AC5zcQB+AAh0AApDb2xsZWN0aW9uc3EAfgAGc3EAfgAIdAABVHEAfgALcQB+AA14c3EAfgAsAAAAcQB+AAtzcQB+AAZzcQB+ADpzcQB+AAZzcQB+AD0AAABxAH4AC3EAfgAkdAADYXJyc3EAfgAIdAAFQXJyYXlzcQB+AAZzcQB+AAh0AAFUcQB+AAtxAH4ADXhxAH4ADXhxAH4ADXhzcQB+AC5zcQB+AEVzcQB+AAZzcQB+AAZzcQB+ADR0AANhcnJxAH4ADXhxAH4ADXhzcQB+ADR0ABNDb2xsZWN0aW9uT3ZlckFycmF5cQB+AAt0AAlmcm9tQXJyYXlxAH4AJHNxAH4ABnNxAH4AUXEAfgAkc3EAfgAGdAAIQ2xhc3NUYWdxAH4ADXh0AAFUcQB+AAtxAH4ADXhzcQB+AC5zcQB+AAh0AApDb2xsZWN0aW9uc3EAfgAGc3EAfgAIdAABVHEAfgALcQB+AA14cQB+AA14dAAKQ29sbGVjdGlvbnB0AApDb2xsZWN0aW9ucQB+ACRzcQB+AAZzcQB+AFFxAH4AJHEAfgALdAABQXEAfgALcQB+AA14cQB+AA14cQB+ALVxAH4AJHNxAH4ABnNyACFzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0ltcG9ydFN0YXSsgKBKw8YbIgIAAUwABG5hbWVxAH4ABHhwdAATcGFyYWRpc2UuTnVtTW9ub2lkc3NxAH4BiHQAJXNjYWxhLmNvbGxlY3Rpb24uZ2VuZXJpYy5DYW5CdWlsZEZyb21zcQB+AYh0ABZzY2FsYS5yZWZsZWN0LkNsYXNzVGFncQB+AA14cQB+AAt0AAtDb2xsZWN0aW9uc3QAFHBhcmFkaXNlLmNvbGxlY3Rpb25zcQB+ACRxAH4AJA=="
    }
  }
}