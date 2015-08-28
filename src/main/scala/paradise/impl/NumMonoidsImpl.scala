package scalanizer {
  package implOfNumMonoids {
    object StagedEvaluation {
      import scalan._;
      import scalan.meta.ScalanAst._;
      import scalanizer.implOfNums.StagedEvaluation._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait NumMonoidsAbs extends NumMonoids with ScalanDsl { self: NumMonoidsDsl =>
        implicit def proxyNumMonoid[A](p: Rep[NumMonoid[A]]): NumMonoid[A] = proxyOps[NumMonoid[A]](p)(scala.reflect.classTag[NumMonoid[A]]);
        class NumMonoidElem[A, To <: NumMonoid[A]](implicit val eeA: Elem[A]) extends EntityElem[To] {
          lazy val parent: Option[(Elem[_$1] forSome { 
            type _$1
          })] = None;
          lazy val entityDef: STraitOrClassDef = {
            val module = getModules("NumMonoids");
            module.entities.find(((x$1) => __equal(x$1.name, "NumMonoid"))).get
          };
          lazy val tyArgSubst: Map[String, TypeDesc] = Map("A".->(Left(eeA)));
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagA = eeA.tag;
            weakTypeTag[NumMonoid[A]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$2] forSome { 
            type _$2
          })]) = {
            implicit val eTo: Elem[To] = this;
            val conv = fun(((x: Rep[NumMonoid[A]]) => convertNumMonoid(x)));
            tryConvert(element[NumMonoid[A]], this, x, conv)
          };
          def convertNumMonoid(x: Rep[NumMonoid[A]]): Rep[To] = {
            assert(x.selfType1 match {
              case ((_): NumMonoidElem[(_), (_)]) => true
              case _ => false
            });
            x.asRep[To]
          };
          override def getDefaultRep: Rep[To] = ???
        };
        implicit def numMonoidElement[A](implicit eeA: Elem[A]): Elem[NumMonoid[A]] = new NumMonoidElem[A, NumMonoid[A]]();
        implicit case object NumMonoidCompanionElem extends CompanionElem[NumMonoidCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[NumMonoidCompanionAbs];
          protected def getDefaultRep = NumMonoid
        };
        abstract class NumMonoidCompanionAbs extends CompanionBase[NumMonoidCompanionAbs] with NumMonoidCompanion {
          override def toString = "NumMonoid"
        };
        def NumMonoid: Rep[NumMonoidCompanionAbs];
        implicit def proxyNumMonoidCompanion(p: Rep[NumMonoidCompanion]): NumMonoidCompanion = proxyOps[NumMonoidCompanion](p);
        class PlusMonoidElem[A](val iso: Iso[PlusMonoidData[A], PlusMonoid[A]])(implicit eeA: Elem[A]) extends NumMonoidElem[A, PlusMonoid[A]] with ConcreteElem[PlusMonoidData[A], PlusMonoid[A]] {
          override lazy val parent: Option[(Elem[_$3] forSome { 
            type _$3
          })] = Some(numMonoidElement(element[A]));
          override lazy val entityDef = {
            val module = getModules("NumMonoids");
            module.concreteSClasses.find(((x$2) => __equal(x$2.name, "PlusMonoid"))).get
          };
          override lazy val tyArgSubst: Map[String, TypeDesc] = Map("A".->(Left(eeA)));
          override def convertNumMonoid(x: Rep[NumMonoid[A]]) = PlusMonoid(x.n);
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = {
            implicit val tagA = eeA.tag;
            weakTypeTag[PlusMonoid[A]]
          }
        };
        type PlusMonoidData[A] = Num[A];
        class PlusMonoidIso[A](implicit eeA: Elem[A]) extends Iso[PlusMonoidData[A], PlusMonoid[A]] {
          override def from(p: Rep[PlusMonoid[A]]) = p.n;
          override def to(p: Rep[Num[A]]) = {
            val n = p;
            PlusMonoid(n)
          };
          lazy val defaultRepTo: Rep[PlusMonoid[A]] = PlusMonoid(element[Num[A]].defaultRepValue);
          lazy val eTo = new PlusMonoidElem[A](this)
        };
        abstract class PlusMonoidCompanionAbs extends CompanionBase[PlusMonoidCompanionAbs] with PlusMonoidCompanion {
          override def toString = "PlusMonoid";
          def apply[A](n: Rep[Num[A]])(implicit eeA: Elem[A]): Rep[PlusMonoid[A]] = mkPlusMonoid(n)
        };
        object PlusMonoidMatcher {
          def unapply[A](p: Rep[NumMonoid[A]]) = unmkPlusMonoid(p)
        };
        def PlusMonoid: Rep[PlusMonoidCompanionAbs];
        implicit def proxyPlusMonoidCompanion(p: Rep[PlusMonoidCompanionAbs]): PlusMonoidCompanionAbs = proxyOps[PlusMonoidCompanionAbs](p);
        implicit case object PlusMonoidCompanionElem extends CompanionElem[PlusMonoidCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[PlusMonoidCompanionAbs];
          protected def getDefaultRep = PlusMonoid
        };
        implicit def proxyPlusMonoid[A](p: Rep[PlusMonoid[A]]): PlusMonoid[A] = proxyOps[PlusMonoid[A]](p);
        implicit class ExtendedPlusMonoid[A](p: Rep[PlusMonoid[A]])(implicit eeA: Elem[A]) {
          def toData: Rep[PlusMonoidData[A]] = isoPlusMonoid(eeA).from(p)
        };
        implicit def isoPlusMonoid[A](implicit eeA: Elem[A]): Iso[PlusMonoidData[A], PlusMonoid[A]] = new PlusMonoidIso[A]();
        def mkPlusMonoid[A](n: Rep[Num[A]])(implicit eeA: Elem[A]): Rep[PlusMonoid[A]];
        def unmkPlusMonoid[A](p: Rep[NumMonoid[A]]): Option[Rep[Num[A]]];
        registerModule(scalan.meta.ScalanCodegen.loadModule(NumMonoids_Module.dump))
      };
      trait NumMonoidsSeq extends NumMonoidsDsl with ScalanSeq { self: NumMonoidsDslSeq =>
        lazy val NumMonoid: Rep[NumMonoidCompanionAbs] = {
          final class $anon extends NumMonoidCompanionAbs with UserTypeSeq[NumMonoidCompanionAbs] {
            lazy val selfType = element[NumMonoidCompanionAbs]
          };
          new $anon()
        };
        case class SeqPlusMonoid[A](override val n: Rep[Num[A]])(implicit eeA: Elem[A]) extends PlusMonoid[A](n) with UserTypeSeq[PlusMonoid[A]] {
          lazy val selfType = element[PlusMonoid[A]]
        };
        lazy val PlusMonoid = {
          final class $anon extends PlusMonoidCompanionAbs with UserTypeSeq[PlusMonoidCompanionAbs] {
            lazy val selfType = element[PlusMonoidCompanionAbs]
          };
          new $anon()
        };
        def mkPlusMonoid[A](n: Rep[Num[A]])(implicit eeA: Elem[A]): Rep[PlusMonoid[A]] = new SeqPlusMonoid[A](n);
        def unmkPlusMonoid[A](p: Rep[NumMonoid[A]]) = p match {
          case (p @ ((_): PlusMonoid[A] @unchecked)) => Some(p.n)
          case _ => None
        }
      };
      trait NumMonoidsExp extends NumMonoidsDsl with ScalanExp { self: NumMonoidsDslExp =>
        lazy val NumMonoid: Rep[NumMonoidCompanionAbs] = {
          final class $anon extends NumMonoidCompanionAbs with UserTypeDef[NumMonoidCompanionAbs] {
            lazy val selfType = element[NumMonoidCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        case class ExpPlusMonoid[A](override val n: Rep[Num[A]])(implicit eeA: Elem[A]) extends PlusMonoid[A](n) with UserTypeDef[PlusMonoid[A]] {
          lazy val selfType = element[PlusMonoid[A]];
          override def mirror(t: Transformer) = ExpPlusMonoid[A](t(n))
        };
        lazy val PlusMonoid: Rep[PlusMonoidCompanionAbs] = {
          final class $anon extends PlusMonoidCompanionAbs with UserTypeDef[PlusMonoidCompanionAbs] {
            lazy val selfType = element[PlusMonoidCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object PlusMonoidMethods {
          object opName {
            def unapply(d: (Def[_$4] forSome { 
              type _$4
            })): Option[(Rep[PlusMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PlusMonoidElem[_$5] forSome { 
  type _$5
})].&&(__equal(method.getName, "opName")) => Some(receiver).asInstanceOf[Option[(Rep[PlusMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$6] forSome { 
              type _$6
            })): Option[(Rep[PlusMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object zero {
            def unapply(d: (Def[_$7] forSome { 
              type _$7
            })): Option[(Rep[PlusMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PlusMonoidElem[_$8] forSome { 
  type _$8
})].&&(__equal(method.getName, "zero")) => Some(receiver).asInstanceOf[Option[(Rep[PlusMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$9] forSome { 
              type _$9
            })): Option[(Rep[PlusMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object append {
            def unapply(d: (Def[_$10] forSome { 
              type _$10
            })): Option[(Rep[PlusMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PlusMonoidElem[_$11] forSome { 
  type _$11
})].&&(__equal(method.getName, "append")) => Some(receiver).asInstanceOf[Option[(Rep[PlusMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$12] forSome { 
              type _$12
            })): Option[(Rep[PlusMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object isCommutative {
            def unapply(d: (Def[_$13] forSome { 
              type _$13
            })): Option[(Rep[PlusMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(PlusMonoidElem[_$14] forSome { 
  type _$14
})].&&(__equal(method.getName, "isCommutative")) => Some(receiver).asInstanceOf[Option[(Rep[PlusMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$15] forSome { 
              type _$15
            })): Option[(Rep[PlusMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object PlusMonoidCompanionMethods;
        def mkPlusMonoid[A](n: Rep[Num[A]])(implicit eeA: Elem[A]): Rep[PlusMonoid[A]] = new ExpPlusMonoid[A](n);
        def unmkPlusMonoid[A](p: Rep[NumMonoid[A]]) = p.elem.asInstanceOf[(Elem[_$16] forSome { 
          type _$16
        })] match {
          case ((_): PlusMonoidElem[A] @unchecked) => Some(p.asRep[PlusMonoid[A]].n)
          case _ => None
        };
        object NumMonoidMethods {
          object n {
            def unapply(d: (Def[_$17] forSome { 
              type _$17
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$18, _$19] forSome { 
  type _$18;
  type _$19
})].&&(__equal(method.getName, "n")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$20] forSome { 
              type _$20
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object opName {
            def unapply(d: (Def[_$21] forSome { 
              type _$21
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$22, _$23] forSome { 
  type _$22;
  type _$23
})].&&(__equal(method.getName, "opName")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$24] forSome { 
              type _$24
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object zero {
            def unapply(d: (Def[_$25] forSome { 
              type _$25
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$26, _$27] forSome { 
  type _$26;
  type _$27
})].&&(__equal(method.getName, "zero")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$28] forSome { 
              type _$28
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object append {
            def unapply(d: (Def[_$29] forSome { 
              type _$29
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$30, _$31] forSome { 
  type _$30;
  type _$31
})].&&(__equal(method.getName, "append")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$32] forSome { 
              type _$32
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object isCommutative {
            def unapply(d: (Def[_$33] forSome { 
              type _$33
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumMonoidElem[_$34, _$35] forSome { 
  type _$34;
  type _$35
})].&&(__equal(method.getName, "isCommutative")) => Some(receiver).asInstanceOf[Option[(Rep[NumMonoid[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$36] forSome { 
              type _$36
            })): Option[(Rep[NumMonoid[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object NumMonoidCompanionMethods
      };
      object NumMonoids_Module {
        val packageName = "scalanizer";
        val name = "NumMonoids";
        val dump = "H4sIAAAAAAAAALWVPYwbRRTHn/fO8a19JCFBkU4CcXcyH+HDPtFccUXk3DkI5PvQbQpkIqTxeuxMmJ3Z2xmfbIoUlNAhWoTSp6OhokNCFFQIkKipQigiIBWIN7OfTm6Ta9hitDv75n38/u/t3r0PVRXBy8onnIhWQDVpefa+o3TT6wrN9GxXDiec7tDRx5e+9nfFVeXAuT6cuUnUjuJ9cOOb7jTM7j161AOXCJ8qLSOlYa1nI7R9yTn1NZOizYJgosmA03aPKb3Vg8WBHM6O4DZUenDel8KPqKbeNidKUZXsL1GTEcueXfs82w/zGKJtqmgXqrgeEaYxfYxxPrY/pKE3E1LMAg1nk9T2Q5MW2tRYEMpIpyFq6O6mHKaPi4LgBlzo3SLHpI0hxm1PR0yM8WQjJP6HZEz30MSYL2LCivLR9Vlonxd6UFf0CAG9E4Tc7kxDAEAF3rJJtHI+rYxPy/BpejRihLOPiHl5EMnpDOKrsgAwDdHFG09xkXqgXTFsfnLDf/+h1wgcc3hqUqnZCs+goxdLusFKgRy/O/xMPXj7zqYD9T7UmeoMlI6Ir4uSJ7QaRAipbc4ZQBKNUa31MrVslA7aPNISri+DkAj0lKBcRp0485k2xmZvOVGnBH1NhzQ1rSD3rN7Vknpt32wTzg/urbz50u/d9xxw5kO46NLDxo9SpxrcvUmwK4Vkw8S/Wc9pqHQsZLO403SzhkIOJ77O+ePmciY1Klc4FBuVp5uBe+XeH8NvN+CGk+FOsjudwuiiqn75qfHj5SsOLPXtPFzjZNxH4qrLabAfbUuh+7Akj2kUv6kdE27uTlS8NqQjMuE60aEIcAEBalgtndyQGrpbdkoqKYBG3Oh7UtDmtYPm3973n981fRzBcvwmHuV/2eY/v54dadviKIBIAS+gRE8QZ552PXbpyYA+u/6AfXDnU225VqbzH4H9wS2cui17bvUJiNOP0V/9DefPlZ+/dMBFkgOmAxI2N045Qv/nWGQk8mUN8V084BMVN/Z2MeJa3rsrBajPVyqQu1pD6JR2UtyLpotiY7NcLHLXUM8j2bNZ175QhtQWcOmw9xy/f+UbB6rvQnWEzah6UB3IiRimZPCnoulUX033KvNkkASJSJCRsNcq5FWe2CiFol+H+bZyDykbMTPGj+yf5hvxWDPmoF9NopVIdSFzf4JSoVk7RbBm3Xy8WLPsnJjP1rwRYqnnBelkYgT+aKIk2wjWS6Tzkl7Egbj98Iu913746jf7ka2brsb5FtlvOBcq+44l1J7Jg+OftZAxNprpdZvtfyIZqbXpCAAA"
      };
      trait NumMonoids extends Base with NumsDsl { self: NumMonoidsDsl =>
        trait NumMonoid[A] extends Reifiable[NumMonoid[A]] {
          implicit def eeA: Elem[A];
          def n: Rep[Num[A]];
          def opName: Rep[String];
          def zero: Rep[A];
          def append: Rep[scala.Function1[scala.Tuple2[A, A], A]];
          def isCommutative: Rep[Boolean]
        };
        abstract class PlusMonoid[A](val n: Rep[Num[A]])(implicit val eeA: Elem[A]) extends NumMonoid[A] with Product with Serializable {
          def opName = toRep("+");
          def zero = n.zero;
          def append = fun(((in: Rep[scala.Tuple2[A, A]]) => {
            val a0: Rep[A] = in._1;
            val a1: Rep[A] = in._2;
            n.plus(a0, a1)
          }));
          def isCommutative: Rep[Boolean] = toRep(true)
        };
        trait NumMonoidCompanion;
        trait PlusMonoidCompanion
      };
      trait NumMonoidsDsl extends NumMonoidsAbs with NumsDsl { self: NumMonoidsDsl =>
        
      };
      trait NumMonoidsDslSeq extends NumMonoidsSeq with NumsDslSeq { self: NumMonoidsDslSeq =>
        
      };
      trait NumMonoidsDslExp extends NumMonoidsExp with NumsDslExp { self: NumMonoidsDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZoIWuGNNbkJzAgAPWgAGaGFzRHNsWgAJaGFzRHNsRXhwWgAJaGFzRHNsU2VxTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwAAAAc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0Q2FsbFDrGS0l7EVYAgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQABE51bXNzcQB+AAZzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHEAfgANeHEAfgALc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NEZWbIUpJz8kehNwIACloACmlzQWJzdHJhY3RMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARhcmdzdAAiTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTQ2xhc3NBcmdzO0wABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+ABBMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAc3EAfgAGc3EAfgAIdAAJTnVtTW9ub2lkc3EAfgAGc3EAfgAIdAABQXEAfgALcQB+AA14c3EAfgAIdAAHUHJvZHVjdHEAfgALc3EAfgAIdAAMU2VyaWFsaXphYmxlcQB+AAtxAH4ADXhxAH4AC3NyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzQXJncyfr7mTFMABcAgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZwVz2NYL1Ck/AgAIWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnWgAHdmFsRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGV0ACBMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcGVFeHByO3hwAAAAAXEAfgALc3IAC3NjYWxhLk5vbmUkRlAk9lPKlKwCAAB4cgAMc2NhbGEuT3B0aW9u/mk3/dsOZnQCAAB4cHQAAW5zcQB+AAh0AANOdW1zcQB+AAZzcQB+AAh0AAFBcQB+AAtxAH4ADXhxAH4ADXhzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2REZWbdWXFxtzArDgIACloADGlzRWxlbU9yQ29udFoACmlzSW1wbGljaXRaAAppc092ZXJyaWRlTAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0cGVSZXNxAH4AA3hwAAAAcQB+AAtxAH4AC3NyAApzY2FsYS5Tb21lESLyaV6hi3QCAAFMAAF4dAASTGphdmEvbGFuZy9PYmplY3Q7eHEAfgAjc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ29uc3QaTRTRYktnigIAAUwAAWNxAH4AL3hwdAABK3QABm9wTmFtZXEAfgAkcQB+AAtxAH4AJHNxAH4ALAAAAHEAfgALcQB+AAtzcQB+AC5zcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxlY3QL3WllZUD5hQIAAkwABGV4cHJ0AB1Mc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNFeHByO0wABXRu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"
    }

    object HotSpotKernels {
      import java.io.File;
      import scalan.compilation.GraphVizConfig
    }

    object HotSpotManager {
      import scalan.ScalanCommunityDslExp;
      import scalan.compilation.lms.scalac.CommunityLmsCompilerScala;
      import scalanizer.implOfNumMonoids.StagedEvaluation._;
      import scalan.{CommunityMethodMappingDSL, JNIExtractorOpsExp}
      import scalan.compilation.lms.CommunityBridge

      lazy val moduleExp = new NumMonoidsDslExp with ScalanCommunityDslExp with JNIExtractorOpsExp

      lazy val compiler = new CommunityLmsCompilerScala(moduleExp) with CommunityBridge with CommunityMethodMappingDSL;
      def getScalanContext = compiler;

      import scalan.compilation.lms.uni.LmsCompilerUni;
      lazy val compilerUni = new LmsCompilerUni(moduleExp) with CommunityBridge with CommunityMethodMappingDSL;
      def getScalanContextUni = compilerUni;
    }
  }
}