package scalanizer {
  package implOfNums {
    object StagedEvaluation {
      import scalan._;
      import scalan.meta.ScalanAst._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait NumsAbs extends Nums with ScalanDsl { self: NumsDsl =>
        implicit def proxyNum[T](p: Rep[Num[T]]): Num[T] = proxyOps[Num[T]](p)(scala.reflect.classTag[Num[T]]);
        class NumElem[T, To <: Num[T]](implicit val eeT: Elem[T]) extends EntityElem[To] {
          lazy val parent: Option[(Elem[_$1] forSome { 
            type _$1
          })] = None;
          lazy val entityDef: STraitOrClassDef = {
            val module = getModules("Nums");
            module.entities.find(((x$1) => __equal(x$1.name, "Num"))).get
          };
          lazy val tyArgSubst: Map[String, TypeDesc] = Map("T".->(Left(eeT)));
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagT = eeT.tag;
            weakTypeTag[Num[T]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$2] forSome { 
            type _$2
          })]) = {
            implicit val eTo: Elem[To] = this;
            val conv = fun(((x: Rep[Num[T]]) => convertNum(x)));
            tryConvert(element[Num[T]], this, x, conv)
          };
          def convertNum(x: Rep[Num[T]]): Rep[To] = {
            assert(x.selfType1 match {
              case ((_): NumElem[(_), (_)]) => true
              case _ => false
            });
            x.asRep[To]
          };
          override def getDefaultRep: Rep[To] = ???
        };
        implicit def numElement[T](implicit eeT: Elem[T]): Elem[Num[T]] = new NumElem[T, Num[T]]();
        implicit case object NumCompanionElem extends CompanionElem[NumCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[NumCompanionAbs];
          protected def getDefaultRep = Num
        };
        abstract class NumCompanionAbs extends CompanionBase[NumCompanionAbs] with NumCompanion {
          override def toString = "Num"
        };
        def Num: Rep[NumCompanionAbs];
        implicit def proxyNumCompanion(p: Rep[NumCompanion]): NumCompanion = proxyOps[NumCompanion](p);
        class DoubleNumElem(val iso: Iso[DoubleNumData, DoubleNum]) extends NumElem[Double, DoubleNum] with ConcreteElem[DoubleNumData, DoubleNum] {
          override lazy val parent: Option[(Elem[_$3] forSome { 
            type _$3
          })] = Some(numElement(DoubleElement));
          override lazy val entityDef = {
            val module = getModules("Nums");
            module.concreteSClasses.find(((x$2) => __equal(x$2.name, "DoubleNum"))).get
          };
          override lazy val tyArgSubst: Map[String, TypeDesc] = Map();
          override def convertNum(x: Rep[Num[Double]]) = DoubleNum();
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = weakTypeTag[DoubleNum]
        };
        type DoubleNumData = Unit;
        class DoubleNumIso extends Iso[DoubleNumData, DoubleNum] {
          override def from(p: Rep[DoubleNum]) = ();
          override def to(p: Rep[Unit]) = {
            val unit = p;
            DoubleNum()
          };
          lazy val defaultRepTo: Rep[DoubleNum] = DoubleNum();
          lazy val eTo = new DoubleNumElem(this)
        };
        abstract class DoubleNumCompanionAbs extends CompanionBase[DoubleNumCompanionAbs] with DoubleNumCompanion {
          override def toString = "DoubleNum";
          def apply(p: Rep[DoubleNumData]): Rep[DoubleNum] = isoDoubleNum.to(p);
          def apply(): Rep[DoubleNum] = mkDoubleNum()
        };
        object DoubleNumMatcher {
          def unapply(p: Rep[Num[Double]]) = unmkDoubleNum(p)
        };
        def DoubleNum: Rep[DoubleNumCompanionAbs];
        implicit def proxyDoubleNumCompanion(p: Rep[DoubleNumCompanionAbs]): DoubleNumCompanionAbs = proxyOps[DoubleNumCompanionAbs](p);
        implicit case object DoubleNumCompanionElem extends CompanionElem[DoubleNumCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[DoubleNumCompanionAbs];
          protected def getDefaultRep = DoubleNum
        };
        implicit def proxyDoubleNum(p: Rep[DoubleNum]): DoubleNum = proxyOps[DoubleNum](p);
        implicit class ExtendedDoubleNum(p: Rep[DoubleNum]) {
          def toData: Rep[DoubleNumData] = isoDoubleNum.from(p)
        };
        implicit def isoDoubleNum: Iso[DoubleNumData, DoubleNum] = new DoubleNumIso();
        def mkDoubleNum(): Rep[DoubleNum];
        def unmkDoubleNum(p: Rep[Num[Double]]): Option[Rep[Unit]];
        registerModule(scalan.meta.ScalanCodegen.loadModule(Nums_Module.dump))
      };
      trait NumsSeq extends NumsDsl with ScalanSeq { self: NumsDslSeq =>
        lazy val Num: Rep[NumCompanionAbs] = {
          final class $anon extends NumCompanionAbs with UserTypeSeq[NumCompanionAbs] {
            lazy val selfType = element[NumCompanionAbs]
          };
          new $anon()
        };
        case class SeqDoubleNum() extends DoubleNum() with UserTypeSeq[DoubleNum] {
          lazy val selfType = element[DoubleNum]
        };
        lazy val DoubleNum = {
          final class $anon extends DoubleNumCompanionAbs with UserTypeSeq[DoubleNumCompanionAbs] {
            lazy val selfType = element[DoubleNumCompanionAbs]
          };
          new $anon()
        };
        def mkDoubleNum(): Rep[DoubleNum] = new SeqDoubleNum();
        def unmkDoubleNum(p: Rep[Num[Double]]) = p match {
          case (p @ ((_): DoubleNum @unchecked)) => Some(())
          case _ => None
        }
      };
      trait NumsExp extends NumsDsl with ScalanExp { self: NumsDslExp =>
        lazy val Num: Rep[NumCompanionAbs] = {
          final class $anon extends NumCompanionAbs with UserTypeDef[NumCompanionAbs] {
            lazy val selfType = element[NumCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        case class ExpDoubleNum() extends DoubleNum() with UserTypeDef[DoubleNum] {
          lazy val selfType = element[DoubleNum];
          override def mirror(t: Transformer) = ExpDoubleNum()
        };
        lazy val DoubleNum: Rep[DoubleNumCompanionAbs] = {
          final class $anon extends DoubleNumCompanionAbs with UserTypeDef[DoubleNumCompanionAbs] {
            lazy val selfType = element[DoubleNumCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object DoubleNumMethods {
          object zero {
            def unapply(d: (Def[_$4] forSome { 
              type _$4
            })): Option[Rep[DoubleNum]] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[DoubleNumElem].&&(__equal(method.getName, "zero")) => Some(receiver).asInstanceOf[Option[Rep[DoubleNum]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$5] forSome { 
              type _$5
            })): Option[Rep[DoubleNum]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object one {
            def unapply(d: (Def[_$6] forSome { 
              type _$6
            })): Option[Rep[DoubleNum]] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[DoubleNumElem].&&(__equal(method.getName, "one")) => Some(receiver).asInstanceOf[Option[Rep[DoubleNum]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$7] forSome { 
              type _$7
            })): Option[Rep[DoubleNum]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object plus {
            def unapply(d: (Def[_$8] forSome { 
              type _$8
            })): Option[scala.Tuple3[Rep[DoubleNum], Rep[Double], Rep[Double]]] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (b @ _), _*), _) if receiver.elem.isInstanceOf[DoubleNumElem].&&(__equal(method.getName, "plus")) => Some(scala.Tuple3(receiver, a, b)).asInstanceOf[Option[scala.Tuple3[Rep[DoubleNum], Rep[Double], Rep[Double]]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$9] forSome { 
              type _$9
            })): Option[scala.Tuple3[Rep[DoubleNum], Rep[Double], Rep[Double]]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object times {
            def unapply(d: (Def[_$10] forSome { 
              type _$10
            })): Option[scala.Tuple3[Rep[DoubleNum], Rep[Double], Rep[Double]]] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (b @ _), _*), _) if receiver.elem.isInstanceOf[DoubleNumElem].&&(__equal(method.getName, "times")) => Some(scala.Tuple3(receiver, a, b)).asInstanceOf[Option[scala.Tuple3[Rep[DoubleNum], Rep[Double], Rep[Double]]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$11] forSome { 
              type _$11
            })): Option[scala.Tuple3[Rep[DoubleNum], Rep[Double], Rep[Double]]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object DoubleNumCompanionMethods;
        def mkDoubleNum(): Rep[DoubleNum] = new ExpDoubleNum();
        def unmkDoubleNum(p: Rep[Num[Double]]) = p.elem.asInstanceOf[(Elem[_$12] forSome { 
          type _$12
        })] match {
          case ((_): DoubleNumElem @unchecked) => Some(())
          case _ => None
        };
        object NumMethods {
          object zero {
            def unapply(d: (Def[_$13] forSome { 
              type _$13
            })): Option[(Rep[Num[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumElem[_$14, _$15] forSome { 
  type _$14;
  type _$15
})].&&(__equal(method.getName, "zero")) => Some(receiver).asInstanceOf[Option[(Rep[Num[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$16] forSome { 
              type _$16
            })): Option[(Rep[Num[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object one {
            def unapply(d: (Def[_$17] forSome { 
              type _$17
            })): Option[(Rep[Num[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(NumElem[_$18, _$19] forSome { 
  type _$18;
  type _$19
})].&&(__equal(method.getName, "one")) => Some(receiver).asInstanceOf[Option[(Rep[Num[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$20] forSome { 
              type _$20
            })): Option[(Rep[Num[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object plus {
            def unapply(d: (Def[_$21] forSome { 
              type _$21
            })): Option[(scala.Tuple3[Rep[Num[T]], Rep[T], Rep[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (b @ _), _*), _) if receiver.elem.isInstanceOf[(NumElem[_$22, _$23] forSome { 
  type _$22;
  type _$23
})].&&(__equal(method.getName, "plus")) => Some(scala.Tuple3(receiver, a, b)).asInstanceOf[Option[(scala.Tuple3[Rep[Num[T]], Rep[T], Rep[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$24] forSome { 
              type _$24
            })): Option[(scala.Tuple3[Rep[Num[T]], Rep[T], Rep[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object times {
            def unapply(d: (Def[_$25] forSome { 
              type _$25
            })): Option[(scala.Tuple3[Rep[Num[T]], Rep[T], Rep[T]] forSome { 
              type T
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (b @ _), _*), _) if receiver.elem.isInstanceOf[(NumElem[_$26, _$27] forSome { 
  type _$26;
  type _$27
})].&&(__equal(method.getName, "times")) => Some(scala.Tuple3(receiver, a, b)).asInstanceOf[Option[(scala.Tuple3[Rep[Num[T]], Rep[T], Rep[T]] forSome { 
                type T
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$28] forSome { 
              type _$28
            })): Option[(scala.Tuple3[Rep[Num[T]], Rep[T], Rep[T]] forSome { 
              type T
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object NumCompanionMethods
      };
      object Nums_Module {
        val packageName = "scalanizer";
        val name = "Nums";
        val dump = "H4sIAAAAAAAAALVVPYwTRxR+3jufWZ8F5BQhnSIEHCZRELEtpIiCIrq/RIkW3+n2iJCDIo3XYzNkdnZvZ3xap7giZdIh2iiip6NBikQTIaEUaSmoqfgRQvxUoLyZ/bF9sCJNtljNPL9573vf94335hMoywg+lR7hRDR8qkjDNetlqeruulBMjS4EvSGna7T/y5Hb3gWxIi041IG5K0SuSd4BO1msx2G+dumOAzYRHpUqiKSCE47p0PQCzqmnWCCazPeHinQ5bTpMqvMOzHaD3mgH9qDkwGEvEF5EFXVXOZGSyjR+gGpELN/bZj/aCMc9RFNP0ZyYYjsiTCF87HE4yd+ioTsSgRj5Cg6m0DZCDQtzKswPg0hlLSpY7krQy7azgmAAFpyrZJc0scWg6aqIiQGenA+J9xMZ0Dam6PRZBCwp72+PQrOfcaAq6Q4S9K0fchOJQwBABc4aEI0xP42cn4bmp+7SiBHOfib6x80oiEeQPKUZgDjEEmc+UCKrQNdFr/7rZe+H1+68b+nDsYZSMRPOYaFjBW4wUiCP97auyeff3DhnQbUDVSaXu1JFxFOTkqdszRMhAmUw5wSSaIBqLRWpZbosY84+S9he4IdEYKWUyhrqxJnHlE7WsVqqTgH1FRXSLLWEvOfzHi+Y1/hmlXC++Wjxi1OP1y9ZYE23sLGki8aPsqIKZtpDP698sqhySDcj5qOTd+mXf/158dmddtkUX+jRPhly9T3hQ5r4Km01bmu6tBotBXNrwRDV1VE7Nk0PKaigOXpDT401xWAtt0+aX0kOJUnFFORifPboae9uCy5buYTpxHkxXaaaWNANfPrR0nP2443flDlQiqdvzEb3Klr0fBxBLTmRXL637NybBwf7ykodXWTE7EK/7LSsF4v3/7DA7sCBLlM+Ceut/2jD/9NaY1IyZuaTOduBoPWvN+uv3L+v39Rj6t+PoaALiZRontVJLPWxip+YpQI7zxwnTDhgzrxPwMQG9be3KOszLf50fMKtaaS0vc8ek+/KGM7RtM++UQ0mnKf2/kmMXZpmn1+So8WXBDk9suV8zJ98dceC8ndQ7nMykA6Uu8FQ9DKx8FuhaKxWslhpWiwUh0TEz8Uxz3GYZu/se6f+fDoJ2Z/VhKnU6AL/TKOUjQiWCuZwU6+gYfde/94+/c+th+auV7Xr0A9CyXdQo/2nVKnotvjdmECJWLQLDcJ/AcTmk3vHBwAA"
      };
      trait Nums extends Base { self: NumsDsl =>
        trait Num[T] extends Reifiable[Num[T]] {
          implicit def eeT: Elem[T];
          def zero: Rep[T];
          def one: Rep[T];
          def plus(a: Rep[T], b: Rep[T]): Rep[T];
          def times(a: Rep[T], b: Rep[T]): Rep[T]
        };
        abstract class DoubleNum extends Num[Double] with Product with Serializable {
          def eeT: Elem[Double] = element[Double];
          def zero: Rep[Double] = toRep(0.0);
          def one: Rep[Double] = toRep(1.0);
          def plus(a: Rep[Double], b: Rep[Double]) = a.+(b);
          def times(a: Rep[Double], b: Rep[Double]) = a.*(b)
        };
        trait NumCompanion;
        trait DoubleNumCompanion
      };
      trait NumsDsl extends NumsAbs { self: NumsDsl =>
        
      };
      trait NumsDslSeq extends NumsSeq { self: NumsDslSeq =>
        
      };
      trait NumsDslExp extends NumsExp { self: NumsDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZoIWuGNNbkJzAgAPWgAGaGFzRHNsWgAJaGFzRHNsRXhwWgAJaGFzRHNsU2VxTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwAAAAc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZshSknPyR6E3AgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AANOdW1zcQB+AAZzcgAjc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVQcmltaXRpdmU1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"
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
      import scalanizer.implOfNums.StagedEvaluation._;
      lazy val prog = {
        final class $anon extends NumsDslExp with ScalanCommunityDslExp with JNIExtractorOpsExp;
        new $anon()
      };
      lazy val compiler = {
        final class $anon extends CommunityLmsCompilerScala(prog) with CommunityBridge with CommunityMethodMappingDSL;
        new $anon()
      };
      def getScalanContext = compiler;
      import scalan.compilation.lms.uni.LmsCompilerUni;
      lazy val progUni = {
        final class $anon extends NumsDslExp with ScalanCommunityDslExp with JNIExtractorOpsExp;
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