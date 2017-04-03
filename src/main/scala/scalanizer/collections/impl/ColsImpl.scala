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
          lazy val typeArgs = TypeArgs("A".->(eeA.->(scalan.util.Invariant)));
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
          def convertCol(x: Rep[Col[A]]): Rep[To] = x.elem match {
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
          override lazy val typeArgs = TypeArgs("A".->(eeA.->(scalan.util.Invariant)));
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
          def getDefaultRep = reifyObject(new ColOverArrayIso[A]()(eeA));
          lazy val tag = {
            implicit val tagA = eeA.tag;
            weakTypeTag[ColOverArrayIso[A]]
          };
          lazy val typeArgs = TypeArgs("A".->(eeA.->(scalan.util.Invariant)))
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
        val dump = "H4sIAAAAAAAAALVWTWwbRRR+dpz4J6FKCo3aQ0KINmqBYldUqIiAkJs4qJUbR9lCwFSg8e7YnTL7w+w4tTmUEz3ADSEOSBwqQFwiJMQFcQYJIdQD1545oIYK9dCeQLyZ/fEmjdNwYA+jmdk37735vu/NzNafMBoImAsswolbdqgkZVP3q4E0zAue3eV0mbYb2x/M/vHFyXtZmGrC2GUSLAe8CcWwU+v5Sd+Udh2mVphr11zJZN9wtAsJ5XoYo6JiVPaKYaRWLdahSFyLBtITgYQnwsUVy+OcWpJ5boU5TleSFqeVOgsk2udant1/F65Btg6TludagkpqLnESBDSI5gtUuWfJuKjH/YY/iPFgghcFYRLzwxiTof069c2+67l9R8KhKLWGr9JCm3Ha8xGIc47PdZhcHfLM8T0h46h5jHDZs+NhziU4AYfrV8gmqWDUTsWUgrkd5cwn1jukQ1fRRJmP4h4CytsX+z6NnI8H0t4Rr+cDgI+sPqszKw9AKyeglRVohkkFI5y9R9TPNeH1+hB+mRGAnnJx8iEuYg+05trGh5esN++b405WLe6pXAo6ozw6enyIwjQ/CO7P6x8Hd1+5cSYLpSaUWFBtBVIQS6Z1EOE1TlzXkzrnBEIiOkjh/DAKdZQq2uzSSdHyHJ+46CkCcwKZ4sxiUhmruUcifoaAn5c+jU2zCHyy32EVpcW0RDhfu33smYXt2uvZRAJRiCK6NLGkROxUwsiSxyPPqp2UkKlqeFVT6g3awj6REwyO375j/3QKLmUhEyEXBToYWeji8POf/bBA177JQqGpxb3CSUfTpqBZpoHVhIK3SUU4n98kXPX2pC5v0zbpchkBmkZiBJGQMDe0Ln2qYFrUes/E2x8PFbvqudRYWTPumb98sqUEKWAi/BMW6j/szN+3DrWl1ioiTISIsR3bqApB+gcGvBT6NT2HTs3fZW/d+EhqaDO9nUXdaF3BGlrU62b2QTk+b769fv3IX1++/aguiUKLSYf4xqn/UBCxfv9PwSeohKU+PRirZhahPYLibaAWNKhL6fCzqXUptI9lMjDwO4vkUFqNecjVOHVCY9U8liZEwkQ6ll6daHpmGNp6P29s2+Wjd2auZmHsPIy2UaxBHUZbXte1Y6DwSpG0J8/Gc7vKFoEhgjjJTbNJ8GREoiRMxwLuSsYrr0XzoWzxm4MBFonOMOHpKGG1qnzODf1J4+nvt66ym0+uaOGmd59C8ATsFO8IqmnXzEFPlFLqHE+Rq8cLUbCHa0Dxsgf1Ov+zaZ5U+9yDuKimlvRe2J3eiwMXC4hceQjVy9TiRFBbXZXUwas8LJnTn768cf7oxqu6aCdsbRT+SQ6fvR8eF4i/qG/JE/vckmhk1Bxf9lXn9I8v/fb+r19/lZBXiGjPKT5kzDnepyLlLUh2Nj9kZ2ZUpMjztfufrz5187vf9b1SUuWOJ6GbvD0Gkk3O+0gAk3XmUiKqvENbguCLIqUHTFCdAynWV1Wz/i+pnUk8PwoAAA=="
      };
      trait ColsDsl extends ColsAbs { self: LinearAlgebraDsl =>
        
      };
      trait ColsDslExp extends ColsExp { self: LinearAlgebraDslExp =>
        
      };
      val serializedMetaAst = "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"
    }

    object HotSpotKernels {
      import java.io.File;
      import scalan.compilation.{KernelStore};
      import scalan.meta.ScalanAst.KernelType;
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