package scalanizer {
  package implOfCols {
    object StagedEvaluation {
      import scalan._;
      import scalanizer.implOfDemo.StagedEvaluation._;
      import scalan.compilation.KernelTypes._;
      import scala.reflect.runtime.universe._;
      import scala.reflect._;
      import scalan.common.Default;
      trait ColsAbs extends Cols with ScalanDsl { self: DemoDsl =>
        implicit def proxyCol[A](p: Rep[Col[A]]): Col[A] = proxyOps[Col[A]](p)(scala.reflect.classTag[Col[A]]);
        class ColElem[A, To <: Col[A]](implicit val eeA: Elem[A]) extends EntityElem[To] {
          override def isEntityType = true;
          override lazy val tag = {
            implicit val tagA = eeA.tag;
            weakTypeTag[Col[A]].asInstanceOf[WeakTypeTag[To]]
          };
          override def convert(x: Rep[(Reifiable[_$1] forSome { 
            type _$1
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
        class ColOverArrayElem(val iso: Iso[ColOverArrayData, ColOverArray]) extends ColElem[Int, ColOverArray] with ConcreteElem[ColOverArrayData, ColOverArray] {
          override def convertCol(x: Rep[Col[Int]]) = ColOverArray(x.arr);
          override def getDefaultRep = super[ConcreteElem].getDefaultRep;
          override lazy val tag = weakTypeTag[ColOverArray]
        };
        type ColOverArrayData = MyArrWrapper[Int];
        class ColOverArrayIso extends Iso[ColOverArrayData, ColOverArray] {
          override def from(p: Rep[ColOverArray]) = p.arr;
          override def to(p: Rep[MyArrWrapper[Int]]) = {
            val arr = p;
            ColOverArray(arr)
          };
          lazy val defaultRepTo: Rep[ColOverArray] = ColOverArray(element[MyArrWrapper[Int]].defaultRepValue);
          lazy val eTo = new ColOverArrayElem(this)
        };
        abstract class ColOverArrayCompanionAbs extends CompanionBase[ColOverArrayCompanionAbs] with ColOverArrayCompanion {
          override def toString = "ColOverArray";
          def apply(arr: Rep[MyArrWrapper[Int]]): Rep[ColOverArray] = mkColOverArray(arr)
        };
        object ColOverArrayMatcher {
          def unapply(p: Rep[Col[Int]]) = unmkColOverArray(p)
        };
        def ColOverArray: Rep[ColOverArrayCompanionAbs];
        implicit def proxyColOverArrayCompanion(p: Rep[ColOverArrayCompanionAbs]): ColOverArrayCompanionAbs = proxyOps[ColOverArrayCompanionAbs](p);
        implicit case object ColOverArrayCompanionElem extends CompanionElem[ColOverArrayCompanionAbs] with scala.Product with scala.Serializable {
          lazy val tag = weakTypeTag[ColOverArrayCompanionAbs];
          protected def getDefaultRep = ColOverArray
        };
        implicit def proxyColOverArray(p: Rep[ColOverArray]): ColOverArray = proxyOps[ColOverArray](p);
        implicit class ExtendedColOverArray(p: Rep[ColOverArray]) {
          def toData: Rep[ColOverArrayData] = isoColOverArray.from(p)
        };
        implicit def isoColOverArray: Iso[ColOverArrayData, ColOverArray] = new ColOverArrayIso();
        def mkColOverArray(arr: Rep[MyArrWrapper[Int]]): Rep[ColOverArray];
        def unmkColOverArray(p: Rep[Col[Int]]): Option[Rep[MyArrWrapper[Int]]]
      };
      trait ColsSeq extends ColsDsl with ScalanSeq { self: DemoDslSeq =>
        lazy val Col: Rep[ColCompanionAbs] = {
          final class $anon extends ColCompanionAbs with UserTypeSeq[ColCompanionAbs] {
            lazy val selfType = element[ColCompanionAbs]
          };
          new $anon()
        };
        case class SeqColOverArray(override val arr: Rep[MyArrWrapper[Int]]) extends ColOverArray(arr) with UserTypeSeq[ColOverArray] {
          lazy val selfType = element[ColOverArray]
        };
        lazy val ColOverArray = {
          final class $anon extends ColOverArrayCompanionAbs with UserTypeSeq[ColOverArrayCompanionAbs] {
            lazy val selfType = element[ColOverArrayCompanionAbs]
          };
          new $anon()
        };
        def mkColOverArray(arr: Rep[MyArrWrapper[Int]]): Rep[ColOverArray] = new SeqColOverArray(arr);
        def unmkColOverArray(p: Rep[Col[Int]]) = p match {
          case (p @ ((_): ColOverArray @unchecked)) => Some(p.arr)
          case _ => None
        }
      };
      trait ColsExp extends ColsDsl with ScalanExp { self: DemoDslExp =>
        lazy val Col: Rep[ColCompanionAbs] = {
          final class $anon extends ColCompanionAbs with UserTypeDef[ColCompanionAbs] {
            lazy val selfType = element[ColCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        case class ExpColOverArray(override val arr: Rep[MyArrWrapper[Int]]) extends ColOverArray(arr) with UserTypeDef[ColOverArray] {
          lazy val selfType = element[ColOverArray];
          override def mirror(t: Transformer) = ExpColOverArray(t(arr))
        };
        lazy val ColOverArray: Rep[ColOverArrayCompanionAbs] = {
          final class $anon extends ColOverArrayCompanionAbs with UserTypeDef[ColOverArrayCompanionAbs] {
            lazy val selfType = element[ColOverArrayCompanionAbs];
            override def mirror(t: Transformer) = this
          };
          new $anon()
        };
        object ColOverArrayMethods {
          object apply {
            def unapply(d: (Def[_$2] forSome { 
              type _$2
            })): Option[scala.Tuple2[Rep[ColOverArray], Rep[Int]]] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[ColOverArrayElem].&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[scala.Tuple2[Rep[ColOverArray], Rep[Int]]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$3] forSome { 
              type _$3
            })): Option[scala.Tuple2[Rep[ColOverArray], Rep[Int]]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };
        object ColOverArrayCompanionMethods;
        def mkColOverArray(arr: Rep[MyArrWrapper[Int]]): Rep[ColOverArray] = new ExpColOverArray(arr);
        def unmkColOverArray(p: Rep[Col[Int]]) = p.elem.asInstanceOf[(Elem[_$4] forSome { 
          type _$4
        })] match {
          case ((_): ColOverArrayElem @unchecked) => Some(p.asRep[ColOverArray].arr)
          case _ => None
        };
        object ColMethods {
          object arr {
            def unapply(d: (Def[_$5] forSome { 
              type _$5
            })): Option[(Rep[Col[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(ColElem[_$6, _$7] forSome { 
  type _$6;
  type _$7
})].&&(__equal(method.getName, "arr")) => Some(receiver).asInstanceOf[Option[(Rep[Col[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$8] forSome { 
              type _$8
            })): Option[(Rep[Col[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$9] forSome { 
              type _$9
            })): Option[(scala.Tuple2[Rep[Col[A]], Rep[Int]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(ColElem[_$10, _$11] forSome { 
  type _$10;
  type _$11
})].&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[Col[A]], Rep[Int]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$12] forSome { 
              type _$12
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
      trait Cols extends Base { self: DemoDsl =>
        trait Col[A] extends Reifiable[Col[A]] {
          implicit def eeA: Elem[A];
          def arr: Rep[MyArrWrapper[A]];
          def apply(i: Rep[Int]): Rep[A]
        };
        abstract class ColOverArray(val arr: Rep[MyArrWrapper[Int]]) extends Col[Int] {
          def eeA: Elem[Int] = element[Int];
          def apply(i: Rep[Int]): Rep[Int] = IF(ColOverArray.this.arr.length.<=(i)).THEN(ColOverArray.this.arr.apply(ColOverArray.this.arr.length.-(toRep(1)))).ELSE(IF(i.<(toRep(0))).THEN(ColOverArray.this.arr.apply(toRep(0))).ELSE(ColOverArray.this.arr.apply(i)))
        };
        trait ColCompanion;
        trait ColOverArrayCompanion
      };
      trait ColsDsl extends ColsAbs { self: DemoDsl =>
        
      };
      trait ColsDslSeq extends ColsSeq { self: DemoDslSeq =>
        
      };
      trait ColsDslExp extends ColsExp { self: DemoDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZt3ynp7b43WYAgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AANDb2xzcQB+AAZzcgAjc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVQcmltaXRpdmU1w7tV8L5OBQIAAkwAEmRlZmF1bHRWYWx1ZVN0cmluZ3EAfgAETAAEbmFtZXEAfgAEeHB0AAEwdAADSW50cQB+AAl4cQB+AAl4cQB+AAdzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZ3Mn6+5kxTAAXAIAAUwABGFyZ3NxAH4AAXhwc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NBcmcFc9jWC9QpPwIACFoAB2ltcEZsYWdaAAxpc0VsZW1PckNvbnRaAAhvdmVyRmxhZ1oAB3ZhbEZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBldAAgTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTVHBlRXhwcjt4cAAAAAFxAH4AB3NyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHIADHNjYWxhLk9wdGlvbv5pN/3bDmZ0AgAAeHB0AANhcnJzcQB+AA90AAVNeUFycnNxAH4ABnEAfgAUcQB+AAl4cQB+AAl4c3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kRGVm3VlxcbcwKw4CAApaAAxpc0VsZW1PckNvbnRaAAppc0ltcGxpY2l0WgAKaXNPdmVycmlkZUwAC2Fubm90YXRpb25zcQB+AAFMAAthcmdTZWN0aW9uc3EAfgABTAAEYm9keXEAfgADTAAEbmFtZXEAfgAETAAKb3ZlcmxvYWRJZHEAfgADTAAHdHBlQXJnc3EAfgABTAAGdHBlUmVzcQB+AAN4cAAAAHEAfgAHc3EAfgAGc3IAIXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kQXJncynCJ5ZedbqLAgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2RBcmcD3P/Lk3JzAwIAB1oAB2ltcEZsYWdaAAxpc0VsZW1PckNvbnRaAAhvdmVyRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGVxAH4AG3hwAAAAcQB+AAdxAH4AH3QAAWlxAH4AFHEAfgAJeHEAfgAJeHNyAApzY2FsYS5Tb21lESLyaV6hi3QCAAFMAAF4dAASTGphdmEvbGFuZy9PYmplY3Q7eHEAfgAec3IAGXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTSWYollZnuMMsHQIAA0wABGNvbmR0AB1Mc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNFeHByO0wAAmVscQB+ADJMAAJ0aHEAfgAyeHBzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNBcHBseYRmKZ/Smvx/AgADTAAFYXJnc3NxAH4AAUwAA2Z1bnEAfgAyTAACdHNxAH4AAXhwc3EAfgAGc3EAfgAGc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTSWRlbnQnEqX78AnXTwIAAUwABG5hbWVxAH4ABHhwdAABaXEAfgAJeHEAfgAJeHNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1NlbGVjdAvdaWVlQPmFAgACTAAEZXhwcnEAfgAyTAAFdG5hbWVxAH4ABHhwc3EAfgA7c3EAfgA7c3IAG3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVGhpc+oe0MagX4tnAgABTAAIdHlwZU5hbWVxAH4ABHhwdAAMQ29sT3ZlckFycmF5dAADYXJydAAGbGVuZ3RodAAIJGxlc3MkZXFxAH4AB3NxAH4AMXNxAH4ANHNxAH4ABnNxAH4ABnNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NvbnN0Gk0U0WJLZ4oCAAFMAAFjcQB+AC94cHNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAAAcQB+AAl4cQB+AAl4c3EAfgA7c3EAfgA4dAABaXQABSRsZXNzcQB+AAdzcQB+ADRzcQB+AAZzcQB+AAZzcQB+ADh0AAFpcQB+AAl4cQB+AAl4c3EAfgA7c3EAfgA7c3EAfgA/dAAMQ29sT3ZlckFycmF5dAADYXJydAAFYXBwbHlxAH4AB3NxAH4ANHNxAH4ABnNxAH4ABnNxAH4ASXEAfgBNcQB+AAl4cQB+AAl4c3EAfgA7c3EAfgA7c3EAfgA/dAAMQ29sT3ZlckFycmF5dAADYXJycQB+AFxxAH4AB3NxAH4ANHNxAH4ABnNxAH4ABnNxAH4ANHNxAH4ABnNxAH4ABnNxAH4ASXNxAH4ASwAAAAFxAH4ACXhxAH4ACXhzcQB+ADtzcQB+ADtzcQB+ADtzcQB+AD90AAxDb2xPdmVyQXJyYXl0AANhcnJxAH4AQ3QABiRtaW51c3EAfgAHcQB+AAl4cQB+AAl4c3EAfgA7c3EAfgA7c3EAfgA/dAAMQ29sT3ZlckFycmF5dAADYXJycQB+AFxxAH4AB3EAfgBccQB+AB9xAH4AB3NxAH4ALnEAfgAUcQB+AAl4cQB+AB9zcQB+ABdxAH4AB3QADENvbE92ZXJBcnJheXEAfgAfcQB+AAdxAH4ACXhzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdERlZgG+NJI0FLGyAgAJWgAIYml0bWFwJDBMAAlhbmNlc3RvcnNxAH4AAUwAC2Fubm90YXRpb25zcQB+AAFMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAMTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgAHcQB+AAdzcQB+AAZzcQB+ACUAAABxAH4AB3EAfgAHcQB+AB90AANhcnJxAH4AH3EAfgAHc3EAfgAuc3EAfgAPdAAFTXlBcnJzcQB+AAZzcQB+AA90AAFBcQB+AAdxAH4ACXhzcQB+ACUAAABxAH4AB3NxAH4ABnNxAH4AKHNxAH4ABnNxAH4AKwAAAHEAfgAHcQB+AB90AAFpcQB+ABRxAH4ACXhxAH4ACXhxAH4AH3EAfgBccQB+AB9xAH4AB3NxAH4ALnNxAH4AD3QAAUFxAH4AB3EAfgAJeHEAfgAfcHQAA0NvbHEAfgAfc3EAfgAGc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlQXJnklsmSHLxm4kCAARMAAVib3VuZHEAfgADTAAMY29udGV4dEJvdW5kcQB+AAFMAARuYW1lcQB+AARMAAd0cGFyYW1zcQB+AAF4cHEAfgAfcQB+AAd0AAFBcQB+AAdxAH4ACXhxAH4ACXhxAH4Af3EAfgAfc3EAfgAGc3IAIXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTSW1wb3J0U3RhdKyAoErDxhsiAgABTAAEbmFtZXEAfgAEeHB0AA5zY2FsYW4uSG90U3BvdHNxAH4AmHQAIHNjYWxhbi5jb21waWxhdGlvbi5LZXJuZWxUeXBlcy5fcQB+AAl4cQB+AAd0AARDb2xzdAAKc2NhbGFuaXplcnNxAH4ALnNyACJzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1NlbGZUeXBlRGVmfvedTirOs+UCAAJMAApjb21wb25lbnRzcQB+AAFMAARuYW1lcQB+AAR4cHNxAH4ABnNxAH4AD3QABERlbW9xAH4AB3EAfgAJeHQABHNlbGZxAH4AHw=="
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
      import scalanizer.implOfDemo.StagedEvaluation._;
      lazy val scalanContext = new Scalan();
      def getScalanContext = scalanContext;
      class Scalan extends DemoDslExp with CommunityLmsCompilerScala with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler {
        val lms = new CommunityLmsBackend()
      };
      import scalan.CommunityMethodMappingDSL;
      import scalan.compilation.lms.uni.LmsCompilerUni;
      lazy val scalanContextUni = new ScalanUni();
      def getScalanContextUni = scalanContextUni;
      class ScalanUni extends DemoDslExp with LmsCompilerUni with CoreBridge with ScalanCommunityDslExp with EffectfulCompiler with CommunityMethodMappingDSL
    }
  }
}