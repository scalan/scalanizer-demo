//package paradise {
//  package implOfNumMonoids {
//    object StagedEvaluation {
//      import scalan._;
//      import scala.reflect.runtime.universe._;
//      import scala.reflect._;
//      import scalan.common.Default;
//      trait NumMonoidsAbs extends NumMonoids with ScalanDsl { self: NumMonoidsDsl =>
//        implicit def proxyNumMonoid[A](p: Rep[NumMonoid[A]]): NumMonoid[A] = proxyOps[NumMonoid[A]](p)(classTag[NumMonoid[A]]);
//        class NumMonoidElem[A, To <: NumMonoid[A]](implicit val eA: Elem[A]) extends EntityElem[To] {
//          override def isEntityType = true;
//          override lazy val tag = {
//            implicit val tagA = eA.tag;
//            weakTypeTag[NumMonoid[A]].asInstanceOf[WeakTypeTag[To]]
//          };
//          override def convert(x: Rep[(Reifiable[_$1] forSome {
//            type _$1
//          })]) = {
//            val conv = fun(((x: Rep[NumMonoid[A]]) => convertNumMonoid(x)));
//            tryConvert(element[NumMonoid[A]], this, x, conv)
//          };
//          def convertNumMonoid(x: Rep[NumMonoid[A]]): Rep[To] = {
//            assert(x.selfType1 match {
//              case ((_): NumMonoidElem[(_), (_)]) => true
//              case _ => false
//            });
//            x.asRep[To]
//          };
//          override def getDefaultRep: Rep[To] = ???
//        };
//        implicit def numMonoidElement[A](implicit eA: Elem[A]): Elem[NumMonoid[A]] = new NumMonoidElem[A, NumMonoid[A]]();
//        implicit case object NumMonoidCompanionElem extends CompanionElem[NumMonoidCompanionAbs] with scala.Product with scala.Serializable {
//          lazy val tag = weakTypeTag[NumMonoidCompanionAbs];
//          protected def getDefaultRep = NumMonoid
//        };
//        abstract class NumMonoidCompanionAbs extends CompanionBase[NumMonoidCompanionAbs] with NumMonoidCompanion {
//          override def toString = "NumMonoid"
//        };
//        def NumMonoid: Rep[NumMonoidCompanionAbs];
//        implicit def proxyNumMonoidCompanion(p: Rep[NumMonoidCompanion]): NumMonoidCompanion = proxyOps[NumMonoidCompanion](p);
//        class PlusMonoidElem[A](val iso: Iso[PlusMonoidData[A], PlusMonoid[A]])(implicit n: Numeric[A], eA: Elem[A]) extends NumMonoidElem[A, PlusMonoid[A]] with ConcreteElem[PlusMonoidData[A], PlusMonoid[A]] {
//          override def convertNumMonoid(x: Rep[NumMonoid[A]]) = PlusMonoid();
//          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
//          override lazy val tag = {
//            implicit val tagA = eA.tag;
//            weakTypeTag[PlusMonoid[A]]
//          }
//        };
//        type PlusMonoidData[A] = Unit;
//        class PlusMonoidIso[A](implicit n: Numeric[A], eA: Elem[A]) extends Iso[PlusMonoidData[A], PlusMonoid[A]] {
//          override def from(p: Rep[PlusMonoid[A]]) = ();
//          override def to(p: Rep[Unit]) = {
//            val unit = p;
//            PlusMonoid()
//          };
//          lazy val defaultRepTo = Default.defaultVal[Rep[PlusMonoid[A]]](PlusMonoid());
//          lazy val eTo = new PlusMonoidElem[A](this)
//        };
//        abstract class PlusMonoidCompanionAbs extends CompanionBase[PlusMonoidCompanionAbs] with PlusMonoidCompanion {
//          override def toString = "PlusMonoid";
//          def apply[A](p: Rep[PlusMonoidData[A]])(implicit n: Numeric[A], eA: Elem[A]): Rep[PlusMonoid[A]] = isoPlusMonoid(n, eA).to(p);
//          def apply[A]()(implicit n: Numeric[A], eA: Elem[A]): Rep[PlusMonoid[A]] = mkPlusMonoid()
//        };
//        object PlusMonoidMatcher {
//          def unapply[A](p: Rep[NumMonoid[A]]) = unmkPlusMonoid(p)
//        };
//        def PlusMonoid: Rep[PlusMonoidCompanionAbs];
//        implicit def proxyPlusMonoidCompanion(p: Rep[PlusMonoidCompanionAbs]): PlusMonoidCompanionAbs = proxyOps[PlusMonoidCompanionAbs](p);
//        implicit case object PlusMonoidCompanionElem extends CompanionElem[PlusMonoidCompanionAbs] with scala.Product with scala.Serializable {
//          lazy val tag = weakTypeTag[PlusMonoidCompanionAbs];
//          protected def getDefaultRep = PlusMonoid
//        };
//        implicit def proxyPlusMonoid[A](p: Rep[PlusMonoid[A]]): PlusMonoid[A] = proxyOps[PlusMonoid[A]](p);
//        implicit class ExtendedPlusMonoid[A](p: Rep[PlusMonoid[A]])(implicit n: Numeric[A], eA: Elem[A]) {
//          def toData: Rep[PlusMonoidData[A]] = isoPlusMonoid(n, eA).from(p)
//        };
//        implicit def isoPlusMonoid[A](implicit n: Numeric[A], eA: Elem[A]): Iso[PlusMonoidData[A], PlusMonoid[A]] = new PlusMonoidIso[A]();
//        def mkPlusMonoid[A]()(implicit n: Numeric[A], eA: Elem[A]): Rep[PlusMonoid[A]];
//        def unmkPlusMonoid[A](p: Rep[NumMonoid[A]]): Option[Rep[Unit]];
//        class MultMonoidElem[A](val iso: Iso[MultMonoidData[A], MultMonoid[A]])(implicit n: Numeric[A], eA: Elem[A]) extends NumMonoidElem[A, MultMonoid[A]] with ConcreteElem[MultMonoidData[A], MultMonoid[A]] {
//          override def convertNumMonoid(x: Rep[NumMonoid[A]]) = MultMonoid();
//          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
//          override lazy val tag = {
//            implicit val tagA = eA.tag;
//            weakTypeTag[MultMonoid[A]]
//          }
//        };
//        type MultMonoidData[A] = Unit;
//        class MultMonoidIso[A](implicit n: Numeric[A], eA: Elem[A]) extends Iso[MultMonoidData[A], MultMonoid[A]] {
//          override def from(p: Rep[MultMonoid[A]]) = ();
//          override def to(p: Rep[Unit]) = {
//            val unit = p;
//            MultMonoid()
//          };
//          lazy val defaultRepTo = Default.defaultVal[Rep[MultMonoid[A]]](MultMonoid());
//          lazy val eTo = new MultMonoidElem[A](this)
//        };
//        abstract class MultMonoidCompanionAbs extends CompanionBase[MultMonoidCompanionAbs] with MultMonoidCompanion {
//          override def toString = "MultMonoid";
//          def apply[A](p: Rep[MultMonoidData[A]])(implicit n: Numeric[A], eA: Elem[A]): Rep[MultMonoid[A]] = isoMultMonoid(n, eA).to(p);
//          def apply[A]()(implicit n: Numeric[A], eA: Elem[A]): Rep[MultMonoid[A]] = mkMultMonoid()
//        };
//        object MultMonoidMatcher {
//          def unapply[A](p: Rep[NumMonoid[A]]) = unmkMultMonoid(p)
//        };
//        def MultMonoid: Rep[MultMonoidCompanionAbs];
//        implicit def proxyMultMonoidCompanion(p: Rep[MultMonoidCompanionAbs]): MultMonoidCompanionAbs = proxyOps[MultMonoidCompanionAbs](p);
//        implicit case object MultMonoidCompanionElem extends CompanionElem[MultMonoidCompanionAbs] with scala.Product with scala.Serializable {
//          lazy val tag = weakTypeTag[MultMonoidCompanionAbs];
//          protected def getDefaultRep = MultMonoid
//        };
//        implicit def proxyMultMonoid[A](p: Rep[MultMonoid[A]]): MultMonoid[A] = proxyOps[MultMonoid[A]](p);
//        implicit class ExtendedMultMonoid[A](p: Rep[MultMonoid[A]])(implicit n: Numeric[A], eA: Elem[A]) {
//          def toData: Rep[MultMonoidData[A]] = isoMultMonoid(n, eA).from(p)
//        };
//        implicit def isoMultMonoid[A](implicit n: Numeric[A], eA: Elem[A]): Iso[MultMonoidData[A], MultMonoid[A]] = new MultMonoidIso[A]();
//        def mkMultMonoid[A]()(implicit n: Numeric[A], eA: Elem[A]): Rep[MultMonoid[A]];
//        def unmkMultMonoid[A](p: Rep[NumMonoid[A]]): Option[Rep[Unit]]
//      };
//      trait NumMonoidsSeq extends NumMonoidsDsl with ScalanSeq { self: NumMonoidsDslSeq =>
//        lazy val NumMonoid: Rep[NumMonoidCompanionAbs] = {
//          final class $anon extends NumMonoidCompanionAbs with UserTypeSeq[NumMonoidCompanionAbs] {
//            lazy val selfType = element[NumMonoidCompanionAbs]
//          };
//          new $anon()
//        };
//        case class SeqPlusMonoid[A](implicit n: Numeric[A], eA: Elem[A]) extends PlusMonoid[A]() with UserTypeSeq[PlusMonoid[A]] {
//          lazy val selfType = element[PlusMonoid[A]]
//        };
//        lazy val PlusMonoid = {
//          final class $anon extends PlusMonoidCompanionAbs with UserTypeSeq[PlusMonoidCompanionAbs] {
//            lazy val selfType = element[PlusMonoidCompanionAbs]
//          };
//          new $anon()
//        };
//        def mkPlusMonoid[A]()(implicit n: Numeric[A], eA: Elem[A]): Rep[PlusMonoid[A]] = new SeqPlusMonoid[A]();
//        def unmkPlusMonoid[A](p: Rep[NumMonoid[A]]) = p match {
//          case (p @ ((_): PlusMonoid[A] @unchecked)) => Some(())
//          case _ => None
//        };
//        case class SeqMultMonoid[A](implicit n: Numeric[A], eA: Elem[A]) extends MultMonoid[A]() with UserTypeSeq[MultMonoid[A]] {
//          lazy val selfType = element[MultMonoid[A]]
//        };
//        lazy val MultMonoid = {
//          final class $anon extends MultMonoidCompanionAbs with UserTypeSeq[MultMonoidCompanionAbs] {
//            lazy val selfType = element[MultMonoidCompanionAbs]
//          };
//          new $anon()
//        };
//        def mkMultMonoid[A]()(implicit n: Numeric[A], eA: Elem[A]): Rep[MultMonoid[A]] = new SeqMultMonoid[A]();
//        def unmkMultMonoid[A](p: Rep[NumMonoid[A]]) = p match {
//          case (p @ ((_): MultMonoid[A] @unchecked)) => Some(())
//          case _ => None
//        }
//      };
//      trait NumMonoidsExp extends NumMonoidsDsl with ScalanExp { self: NumMonoidsDslExp =>
//        lazy val NumMonoid: Rep[NumMonoidCompanionAbs] = {
//          final class $anon extends NumMonoidCompanionAbs with UserTypeDef[NumMonoidCompanionAbs] {
//            lazy val selfType = element[NumMonoidCompanionAbs];
//            override def mirror(t: Transformer) = this
//          };
//          new $anon()
//        };
//        case class ExpPlusMonoid[A](implicit n: Numeric[A], eA: Elem[A]) extends PlusMonoid[A]() with UserTypeDef[PlusMonoid[A]] {
//          lazy val selfType = element[PlusMonoid[A]];
//          override def mirror(t: Transformer) = ExpPlusMonoid[A]()
//        };
//        lazy val PlusMonoid: Rep[PlusMonoidCompanionAbs] = {
//          final class $anon extends PlusMonoidCompanionAbs with UserTypeDef[PlusMonoidCompanionAbs] {
//            lazy val selfType = element[PlusMonoidCompanionAbs];
//            override def mirror(t: Transformer) = this
//          };
//          new $anon()
//        };
//        object PlusMonoidMethods {
//          object opName {
//            def unapply(d: (Def[_$2] forSome {
//              type _$2
//            })): Option[(Rep[PlusMonoid[A]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PlusMonoidElem[_$3] forSome {
//  type _$3
//})].&&(method.getName.==("opName")) => Some(receiver).asInstanceOf[Option[(Rep[PlusMonoid[A]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$4] forSome {
//              type _$4
//            })): Option[(Rep[PlusMonoid[A]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object zero {
//            def unapply(d: (Def[_$5] forSome {
//              type _$5
//            })): Option[(Rep[PlusMonoid[A]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PlusMonoidElem[_$6] forSome {
//  type _$6
//})].&&(method.getName.==("zero")) => Some(receiver).asInstanceOf[Option[(Rep[PlusMonoid[A]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$7] forSome {
//              type _$7
//            })): Option[(Rep[PlusMonoid[A]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object append {
//            def unapply(d: (Def[_$8] forSome {
//              type _$8
//            })): Option[(Rep[PlusMonoid[A]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PlusMonoidElem[_$9] forSome {
//  type _$9
//})].&&(method.getName.==("append")) => Some(receiver).asInstanceOf[Option[(Rep[PlusMonoid[A]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$10] forSome {
//              type _$10
//            })): Option[(Rep[PlusMonoid[A]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object isCommutative {
//            def unapply(d: (Def[_$11] forSome {
//              type _$11
//            })): Option[(Rep[PlusMonoid[A]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PlusMonoidElem[_$12] forSome {
//  type _$12
//})].&&(method.getName.==("isCommutative")) => Some(receiver).asInstanceOf[Option[(Rep[PlusMonoid[A]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$13] forSome {
//              type _$13
//            })): Option[(Rep[PlusMonoid[A]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          }
//        };
//        object PlusMonoidCompanionMethods;
//        def mkPlusMonoid[A]()(implicit n: Numeric[A], eA: Elem[A]): Rep[PlusMonoid[A]] = new ExpPlusMonoid[A]();
//        def unmkPlusMonoid[A](p: Rep[NumMonoid[A]]) = p.elem.asInstanceOf[(Elem[_$14] forSome {
//          type _$14
//        })] match {
//          case ((_): PlusMonoidElem[A] @unchecked) => Some(())
//          case _ => None
//        };
//        case class ExpMultMonoid[A](implicit n: Numeric[A], eA: Elem[A]) extends MultMonoid[A]() with UserTypeDef[MultMonoid[A]] {
//          lazy val selfType = element[MultMonoid[A]];
//          override def mirror(t: Transformer) = ExpMultMonoid[A]()
//        };
//        lazy val MultMonoid: Rep[MultMonoidCompanionAbs] = {
//          final class $anon extends MultMonoidCompanionAbs with UserTypeDef[MultMonoidCompanionAbs] {
//            lazy val selfType = element[MultMonoidCompanionAbs];
//            override def mirror(t: Transformer) = this
//          };
//          new $anon()
//        };
//        object MultMonoidMethods {
//          object opName {
//            def unapply(d: (Def[_$15] forSome {
//              type _$15
//            })): Option[(Rep[MultMonoid[A]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(MultMonoidElem[_$16] forSome {
//  type _$16
//})].&&(method.getName.==("opName")) => Some(receiver).asInstanceOf[Option[(Rep[MultMonoid[A]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$17] forSome {
//              type _$17
//            })): Option[(Rep[MultMonoid[A]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object zero {
//            def unapply(d: (Def[_$18] forSome {
//              type _$18
//            })): Option[(Rep[MultMonoid[A]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(MultMonoidElem[_$19] forSome {
//  type _$19
//})].&&(method.getName.==("zero")) => Some(receiver).asInstanceOf[Option[(Rep[MultMonoid[A]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$20] forSome {
//              type _$20
//            })): Option[(Rep[MultMonoid[A]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object append {
//            def unapply(d: (Def[_$21] forSome {
//              type _$21
//            })): Option[(Rep[MultMonoid[A]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(MultMonoidElem[_$22] forSome {
//  type _$22
//})].&&(method.getName.==("append")) => Some(receiver).asInstanceOf[Option[(Rep[MultMonoid[A]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$23] forSome {
//              type _$23
//            })): Option[(Rep[MultMonoid[A]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object isCommutative {
//            def unapply(d: (Def[_$24] forSome {
//              type _$24
//            })): Option[(Rep[MultMonoid[A]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(MultMonoidElem[_$25] forSome {
//  type _$25
//})].&&(method.getName.==("isCommutative")) => Some(receiver).asInstanceOf[Option[(Rep[MultMonoid[A]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$26] forSome {
//              type _$26
//            })): Option[(Rep[MultMonoid[A]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          }
//        };
//        object MultMonoidCompanionMethods;
//        def mkMultMonoid[A]()(implicit n: Numeric[A], eA: Elem[A]): Rep[MultMonoid[A]] = new ExpMultMonoid[A]();
//        def unmkMultMonoid[A](p: Rep[NumMonoid[A]]) = p.elem.asInstanceOf[(Elem[_$27] forSome {
//          type _$27
//        })] match {
//          case ((_): MultMonoidElem[A] @unchecked) => Some(())
//          case _ => None
//        };
//        object NumMonoidMethods {
//          object opName {
//            def unapply(d: (Def[_$28] forSome {
//              type _$28
//            })): Option[(Rep[NumMonoid[A]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$29, _$30] forSome {
//  type _$29;
//  type _$30
//})].&&(method.getName.==("opName")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$31] forSome {
//              type _$31
//            })): Option[(Rep[NumMonoid[A]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object zero {
//            def unapply(d: (Def[_$32] forSome {
//              type _$32
//            })): Option[(Rep[NumMonoid[A]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$33, _$34] forSome {
//  type _$33;
//  type _$34
//})].&&(method.getName.==("zero")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$35] forSome {
//              type _$35
//            })): Option[(Rep[NumMonoid[A]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object append {
//            def unapply(d: (Def[_$36] forSome {
//              type _$36
//            })): Option[(Rep[NumMonoid[A]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$37, _$38] forSome {
//  type _$37;
//  type _$38
//})].&&(method.getName.==("append")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$39] forSome {
//              type _$39
//            })): Option[(Rep[NumMonoid[A]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          };
//          object isCommutative {
//            def unapply(d: (Def[_$40] forSome {
//              type _$40
//            })): Option[(Rep[NumMonoid[A]] forSome {
//              type A
//            })] = d match {
//              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$41, _$42] forSome {
//  type _$41;
//  type _$42
//})].&&(method.getName.==("isCommutative")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome {
//                type A
//              })]]
//              case _ => None
//            };
//            def unapply(exp: (Exp[_$43] forSome {
//              type _$43
//            })): Option[(Rep[NumMonoid[A]] forSome {
//              type A
//            })] = exp match {
//              case Def((d @ _)) => unapply(d)
//              case _ => None
//            }
//          }
//        };
//        object NumMonoidCompanionMethods
//      };
//      trait NumMonoids extends Base { self: NumMonoidsDsl =>
//        trait NumMonoid[A] extends Reifiable[NumMonoid[A]] {
//          implicit def eA: Elem[A];
//          def opName: Rep[String];
//          def zero: Rep[A];
//          def append: Rep[scala.Function1[scala.Tuple2[A, A], A]];
//          def isCommutative: Rep[Boolean]
//        };
//        abstract class PlusMonoid[A](implicit val n: Rep[Numeric[A]], val eA: Elem[A]) extends NumMonoid[A] with Product with Serializable {
//          def opName = toRep("+");
//          def zero = n.zero;
//          def append = fun(((in: Rep[scala.Tuple2[A, A]]) => {
//            val a0: Rep[A] = in._1;
//            val a1: Rep[A] = in._2;
//            n.plus(a0, a1)
//          }));
//          def isCommutative: Rep[Boolean] = toRep(true)
//        };
//        abstract class MultMonoid[A](implicit val n: Rep[Numeric[A]], val eA: Elem[A]) extends NumMonoid[A] with Product with Serializable {
//          def opName = toRep("*");
//          def zero = n.one;
//          def append = fun(((in: Rep[scala.Tuple2[A, A]]) => {
//            val a0: Rep[A] = in._1;
//            val a1: Rep[A] = in._2;
//            n.times(a0, a1)
//          }));
//          def isCommutative: Rep[Boolean] = toRep(true)
//        };
//        trait NumMonoidCompanion;
//        trait PlusMonoidCompanion;
//        trait MultMonoidCompanion
//      };
//      trait NumMonoidsDsl extends NumMonoidsAbs { self: NumMonoidsDsl =>
//
//      };
//      trait NumMonoidsDslSeq extends NumMonoidsSeq { self: NumMonoidsDslSeq =>
//
//      };
//      trait NumMonoidsDslExp extends NumMonoidsExp { self: NumMonoidsDslExp =>
//
//      };
//      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZqplOx8xRQC7AgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AAlOdW1Nb25vaWRzcQB+AAZzcQB+AA90AAFBcQB+AAdxAH4ACXhzcQB+AA90AAdQcm9kdWN0cQB+AAdzcQB+AA90AAxTZXJpYWxpemFibGVxAH4AB3EAfgAJeHEAfgAHc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NBcmdzJ+vuZMUwAFwCAAFMAARhcmdzcQB+AAF4cHEAfgAHc3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kRGVm3VlxcbcwKw4CAApaAAxpc0VsZW1PckNvbnRaAAppc0ltcGxpY2l0WgAKaXNPdmVycmlkZUwAC2Fubm90YXRpb25zcQB+AAFMAAthcmdTZWN0aW9uc3EAfgABTAAEYm9keXEAfgADTAAEbmFtZXEAfgAETAAKb3ZlcmxvYWRJZHEAfgADTAAHdHBlQXJnc3EAfgABTAAGdHBlUmVzcQB+AAN4cAAAAHEAfgAHcQB+AAdzcgAKc2NhbGEuU29tZREi8mleoYt0AgABTAABeHQAEkxqYXZhL2xhbmcvT2JqZWN0O3hyAAxzY2FsYS5PcHRpb27+aTf92w5mdAIAAHhwc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ29uc3QaTRTRYktnigIAAUwAAWNxAH4AH3hwdAABK3QABm9wTmFtZXNyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHEAfgAgcQB+AAdxAH4AJ3NxAH4AHAAAAHEAfgAHcQB+AAdzcQB+AB5zcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxlY3QL3WllZUD5hQIAAkwABGV4cHJ0AB1Mc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNFeHByO0wABXRuYW1lcQB+AAR4cHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0lkZW50JxKl+/AJ108CAAFMAARuYW1lcQB+AAR4cHQAAW50AAR6ZXJvcQB+ADBxAH4AJ3EAfgAHcQB+ACdzcQB+ABwAAABxAH4AB3EAfgAHc3EAfgAec3IAG3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTRnVuY7BM+JAaxTCDAgACTAAGcGFyYW1zcQB+AAFMAANyZXNxAH4AK3hwc3EAfgAGc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVmFsRGVmKwY5unvdRQ8CAAVaAAppc0ltcGxpY2l0WgAGaXNMYXp5TAAEZXhwcnEAfgArTAAEbmFtZXEAfgAETAADdHBlcQB+AAN4cAAAc3IA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VHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAcQB+AAdxAH4AB3NxAH4ABnNxAH4AHAAAAHEAfgAHcQB+AAdxAH4AJ3QABm9wTmFtZXEAfgAncQB+AAdzcQB+AB5zcQB+AFh0AAIiInQABlN0cmluZ3NxAH4AHAAAAHEAfgAHcQB+AAdxAH4AJ3EAfgAwcQB+ACdxAH4AB3NxAH4AHnNxAH4AD3QAAUFxAH4AB3NxAH4AHAAAAHEAfgAHcQB+AAdxAH4AJ3QABmFwcGVuZHEAfgAncQB+AAdzcQB+AB5zcgAec2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVGdW5j1EmZEw8dX+ECAAJMAAZkb21haW5xAH4AX0wABXJhbmdlcQB+AF94cHNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZVR1cGxlNLv5XoiASHACAAFMAAl0cGVTRXhwcnNxAH4AAXhwc3EAfgAGc3EAfgAPdAABQXEAfgAHc3EAfgAPdAABQXEAfgAHcQB+AAl4c3EAfgAPdAABQXEAfgAHc3EAfgAcAAAAcQB+AAdxAH4AB3EAfgAndAANaXNDb21tdXRhdGl2ZXEAfgAncQB+AAdzcQB+AB5xAH4AWXEAfgAJeHEAfgAncHQACU51bU1vbm9pZHEAfgAnc3EAfgAGc3EAfgBpcQB+ACdxAH4AB3QAAUFxAH4AB3EAfgAJeHEAfgAJeHEAfgCycQB+ACdxAH4AB3EAfgAHdAAKTnVtTW9ub2lkc3QACHBhcmFkaXNlcQB+ACdxAH4AJw=="
//    }
//  }
//}