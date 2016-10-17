package scalanizer.collections {
  package implOfCols {
    import scalan._

    import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._

    import scala.reflect.runtime.universe._

    import scala.reflect._

    object StagedEvaluation {
      trait Cols extends Base { self: LinearAlgebraDsl =>
        trait Col[A] extends Def[Col[A]] {
          implicit def eeA: Elem[A];
          def arr: Rep[WArray[A]];
          def length: Rep[Int];
          def apply(i: Rep[Int]): Rep[A]
        };
        abstract class ColOverArray[A](val arr: Rep[WArray[A]])(implicit val eeA: Elem[A]) extends Col[A] {
          def length: Rep[Int] = ColOverArray.this.arr.length;
          def apply(i: Rep[Int]): Rep[A] = ColOverArray.this.arr.apply(i)
        };
        trait ColCompanion;
        trait ColOverArrayCompanion
      };
      trait ColsAbs extends ScalanDsl with Cols { self: LinearAlgebraDsl =>
        implicit def proxyCol[A](p: Rep[Col[A]]): Col[A] = proxyOps[Col[A]](p)(scala.reflect.classTag[Col[A]]);
        class ColElem[A, To <: Col[A]](implicit _eeA: Elem[A]) extends EntityElem[To] {
          def eeA = _eeA;
          lazy val parent: Option[(Elem[_$1] forSome { 
            type _$1
          })] = None;
          lazy val typeArgs = TypeArgs("A".->(eeA));
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagA = eeA.tag;
            weakTypeTag[Col[A]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Def[_$2] forSome { 
            type _$2
          })]) = {
            implicit val eTo: Elem[To] = this;
            val conv = fun(((x: Rep[Col[A]]) => convertCol(x)));
            tryConvert(element[Col[A]], this, x, conv)
          };
          def convertCol(x: Rep[Col[A]]): Rep[To] = x.selfType1 match {
            case ((_): ColElem[(_), (_)]) => x.asRep[To]
            case (e @ _) => !!!(StringContext("Expected ", " to have ColElem[_, _], but got ", "").s(x, e), x)
          };
          override def getDefaultRep: Rep[To] = ???
        };
        implicit def colElement[A](implicit eeA: Elem[A]): Elem[Col[A]] = cachedElem[ColElem[A, Col[A]]](eeA);
        implicit case object ColCompanionElem extends CompanionElem[ColCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[ColCompanionAbs];
          protected def getDefaultRep = Col
        };
        abstract class ColCompanionAbs extends CompanionDef[ColCompanionAbs] with ColCompanion {
          def selfType = ColCompanionElem;
          override def toString = "Col"
        };
        def Col: Rep[ColCompanionAbs];
        implicit def proxyColCompanionAbs(p: Rep[ColCompanionAbs]): ColCompanionAbs = proxyOps[ColCompanionAbs](p);
        abstract class AbsColOverArray[A](arr: Rep[WArray[A]])(implicit eeA: Elem[A]) extends ColOverArray[A](arr) with Def[ColOverArray[A]] {
          lazy val selfType = element[ColOverArray[A]]
        };
        class ColOverArrayElem[A](val iso: Iso[ColOverArrayData[A], ColOverArray[A]])(implicit override val eeA: Elem[A]) extends ColElem[A, ColOverArray[A]] with ConcreteElem[ColOverArrayData[A], ColOverArray[A]] {
          override lazy val parent: Option[(Elem[_$3] forSome { 
            type _$3
          })] = Some(colElement(element[A]));
          override lazy val typeArgs = TypeArgs("A".->(eeA));
          override def convertCol(x: Rep[Col[A]]) = ColOverArray(x.arr);
          override def getDefaultRep = ColOverArray(element[WArray[A]].defaultRepValue);
          override lazy val tag = {
            implicit val tagA = eeA.tag;
            weakTypeTag[ColOverArray[A]]
          }
        };
        type ColOverArrayData[A] = WArray[A];
        class ColOverArrayIso[A](implicit eeA: Elem[A]) extends EntityIso[ColOverArrayData[A], ColOverArray[A]] with Def[ColOverArrayIso[A]] {
          override def from(p: Rep[ColOverArray[A]]) = p.arr;
          override def to(p: Rep[WArray[A]]) = {
            val arr = p;
            ColOverArray(arr)
          };
          lazy val eFrom = element[WArray[A]];
          lazy val eTo = new ColOverArrayElem[A](self);
          lazy val selfType = new ColOverArrayIsoElem[A](eeA);
          def productArity = 1;
          def productElement(n: Int) = eeA
        };
        case class ColOverArrayIsoElem[A](eeA: Elem[A]) extends Elem[ColOverArrayIso[A]] {
          def isEntityType = true;
          def getDefaultRep = reifyObject(new ColOverArrayIso[A]()(eeA));
          lazy val tag = {
            implicit val tagA = eeA.tag;
            weakTypeTag[ColOverArrayIso[A]]
          };
          lazy val typeArgs = TypeArgs("A".->(eeA))
        };
        class ColOverArrayCompanionAbs extends CompanionDef[ColOverArrayCompanionAbs] with ColOverArrayCompanion {
          def selfType = ColOverArrayCompanionElem;
          override def toString = "ColOverArray";
          @scalan.OverloadId("fromFields") def apply[A](arr: Rep[WArray[A]])(implicit eeA: Elem[A]): Rep[ColOverArray[A]] = mkColOverArray(arr);
          def unapply[A](p: Rep[Col[A]]) = unmkColOverArray(p)
        };
        lazy val ColOverArrayRep: Rep[ColOverArrayCompanionAbs] = new ColOverArrayCompanionAbs();
        lazy val ColOverArray: ColOverArrayCompanionAbs = proxyColOverArrayCompanion(ColOverArrayRep);
        implicit def proxyColOverArrayCompanion(p: Rep[ColOverArrayCompanionAbs]): ColOverArrayCompanionAbs = proxyOps[ColOverArrayCompanionAbs](p);
        implicit case object ColOverArrayCompanionElem extends CompanionElem[ColOverArrayCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[ColOverArrayCompanionAbs];
          protected def getDefaultRep = ColOverArray
        };
        implicit def proxyColOverArray[A](p: Rep[ColOverArray[A]]): ColOverArray[A] = proxyOps[ColOverArray[A]](p);
        implicit class ExtendedColOverArray[A](p: Rep[ColOverArray[A]])(implicit eeA: Elem[A]) {
          def toData: Rep[ColOverArrayData[A]] = isoColOverArray(eeA).from(p)
        };
        implicit def isoColOverArray[A](implicit eeA: Elem[A]): Iso[ColOverArrayData[A], ColOverArray[A]] = reifyObject(new ColOverArrayIso[A]()(eeA));
        def mkColOverArray[A](arr: Rep[WArray[A]])(implicit eeA: Elem[A]): Rep[ColOverArray[A]];
        def unmkColOverArray[A](p: Rep[Col[A]]): Option[Rep[WArray[A]]];
        registerModule(Cols_Module)
      };
      trait ColsExp extends ScalanExp with ColsDsl { self: LinearAlgebraDslExp =>
        lazy val Col: Rep[ColCompanionAbs] = {
          final class $anon extends ColCompanionAbs;
          new $anon()
        };
        case class ExpColOverArray[A](override val arr: Rep[WArray[A]])(implicit eeA: Elem[A]) extends AbsColOverArray[A](arr);
        object ColOverArrayMethods {
          object length {
            def unapply(d: (Def[_$4] forSome { 
              type _$4
            })): Option[(Rep[ColOverArray[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(ColOverArrayElem[_$5] forSome { 
  type _$5
})].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[(Rep[ColOverArray[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$6] forSome { 
              type _$6
            })): Option[(Rep[ColOverArray[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$7] forSome { 
              type _$7
            })): Option[(scala.Tuple2[Rep[ColOverArray[A]], Rep[Int]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(ColOverArrayElem[_$8] forSome { 
  type _$8
})].&&(method.getName.==("apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[ColOverArray[A]], Rep[Int]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$9] forSome { 
              type _$9
            })): Option[(scala.Tuple2[Rep[ColOverArray[A]], Rep[Int]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object ColOverArrayCompanionMethods;
        def mkColOverArray[A](arr: Rep[WArray[A]])(implicit eeA: Elem[A]): Rep[ColOverArray[A]] = new ExpColOverArray[A](arr);
        def unmkColOverArray[A](p: Rep[Col[A]]) = p.elem.asInstanceOf[(Elem[_$10] forSome { 
          type _$10
        })] match {
          case ((_): ColOverArrayElem[A] @unchecked) => Some(p.asRep[ColOverArray[A]].arr)
          case _ => None
        };
        object ColMethods {
          object arr {
            def unapply(d: (Def[_$11] forSome { 
              type _$11
            })): Option[(Rep[Col[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(ColElem[_$12, _$13] forSome { 
  type _$12;
  type _$13
})].&&(method.getName.==("arr")) => Some(receiver).asInstanceOf[Option[(Rep[Col[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$14] forSome { 
              type _$14
            })): Option[(Rep[Col[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object length {
            def unapply(d: (Def[_$15] forSome { 
              type _$15
            })): Option[(Rep[Col[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(ColElem[_$16, _$17] forSome { 
  type _$16;
  type _$17
})].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[(Rep[Col[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$18] forSome { 
              type _$18
            })): Option[(Rep[Col[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$19] forSome { 
              type _$19
            })): Option[(scala.Tuple2[Rep[Col[A]], Rep[Int]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(ColElem[_$20, _$21] forSome { 
  type _$20;
  type _$21
})].&&(method.getName.==("apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[Col[A]], Rep[Int]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$22] forSome { 
              type _$22
            })): Option[(scala.Tuple2[Rep[Col[A]], Rep[Int]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object ColCompanionMethods
      };
      object Cols_Module extends scalan.ModuleInfo {
        val dump = "H4sIAAAAAAAAALVWTYgcRRR+M7O7s7MzxDVhTQSXrMskkRBnghESWFTG3Ym69P6QjqxMwkpNd82kY3V1W1W7zHiIt4B6E1EQPEQUL0ERb54VRMSDV8+eEkVySECI+Kr6Z3rizGY92Ieiq/rV+/m+71X1zd9hUgo4Lh3CCK/5VJGabd4bUlXtJlee6q8F7g6jK7Sz/fKnf13y3zmch9kWTF0hckWyFpSil2YvTN9t5VpQItyhUgVCKnjSMhHqTsAYdZQX8Lrn+zuKtBmtW55USxZMtAO3/yZcg5wFs07AHUEVtZcZkZLKeH2a6oy8dF4y8/5GOIjB67qKeqaKi4J4CtPHGLOR/QUa2n0e8L6v4ECc2kao00KbMu2FWMMrfshMmIIFRc8PA6GSqEWMcCVwk+kEJ7gAB62rZJfUMWq3bivh8a52FhLnDdKl62iizSewBklZ52I/pLHzslTuULxeCADIyjMmsdoAs1qKWU1jVrWp8Ajz3iL646YIen2InlwBoBeii1MPcZF4oE3uVt+97Fy6Z5f9vN7c06kUTUJT6OjoGIUYehDbHy68L++8dONsHmZaMOPJRlsqQRyVlUEMV5lwHiiTc4ogEV1kcHEcgyZKA20ekEnJCfyQcPQUY1lBopjneEob67VKTM8Y7IsqpIlpDnFP610YU6/R0jJhbPPW408fu918LQ/54RAldGljM4jEqYLCcsBiz3p8REGuYeDVQ6k3GIt7RE4xOHHrD/f703A5nyIXB9ofWeji4LmPvz1GN7/Mw3TLaPs8I11Dm4ZmhUqnBdPBLhXRenGXMP02krqiSztkh6kY0CwSBURCwcLYtgyphmnJyD2XlF+OFLsecFo9v1m9a//4wU0tSAGV6EvUp397Z+//eqCjjFYRYSJEgu3UVkMI0t834DORXzvw6aOLd7ztG+8pA22uN9zTG+2r2ENLZt8Te6CcHDdfX78+9+dnrx8yLTHd9pRPwurp/9AQiX7/T8GnqEStPjeY62EeoZ1D8W6gFgyoy9nw85l9GbSP5HIw8DuP5FDaSHiYaDLqR8Z6OJQlREElG8vsTjU9Pw5tUw8/bK999NXR7TxMrsJkB8UqLZhsBzvcTYDCG0XRnnoxWcsNA4XAEEH8FBjzLMCgzpEaypR9AoYVV0AJPLCy32OglDl8M4yYeTUO9nDiNJgj+Ar1+HwWXD0++++C9TA6v3OD3VWkpjaGmhXqMCKoq2826uPNG0n8zIcvbK0e2XrVNFnFNUbRl/SwGP2fsEbCJXOrPbXHrYZG1aYf4l8Lvpz57rlf3v7pi8/NKTFACGWoqVDwWJQ53n8i402mlS2OqcyOmwopvnbvk/WTP3/zm7kHZnR74snF01+FgcTS8znmftbyOCWiwbq0LQj+AGSgxgR132YIX9XD+j9p3ZrmrwkAAA=="
      };
      trait ColsDsl extends ColsAbs { self: LinearAlgebraDsl =>
        
      };
      trait ColsDslExp extends ColsExp { self: LinearAlgebraDslExp =>
        
      };
      val serializedMetaAst = "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"
    }

    object HotSpotKernels {
      import java.io.File;
      import scalan.compilation.{KernelStore, KernelType};
      lazy val ddmvmKernel: scala.Function1[Array[Double], Int] = {
        val methodName = "ddmvm";
        val kernelsDir = new File("./test-out/".+("ddmvm"));
        val ctx = HotSpotManager.scalanContext;
        val store = KernelStore.open(ctx, kernelsDir);
        val k = store.createKernel(methodName, KernelType.Scala, ctx.ddmvmWrapper);
        k.asInstanceOf[scala.Function1[Array[Double], Int]]
      }
    }

    object HotSpotManager {
      import scalanizer.linalgebra.implOfLinearAlgebra.StagedEvaluation._;
      val scalanContext: Scalan = new Scalan();
      class Scalan extends LinearAlgebraDslExp {
        lazy val ddmvmWrapper: Rep[scala.Function1[WArray[Double], Int]] = ???
      }
    }
  }
}