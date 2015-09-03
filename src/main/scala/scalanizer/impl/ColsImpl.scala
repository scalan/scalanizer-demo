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
        type ColOverArrayData = WMyArr[Int];
        class ColOverArrayIso extends Iso[ColOverArrayData, ColOverArray] {
          override def from(p: Rep[ColOverArray]) = p.arr;

          override def to(p: Rep[WMyArr[Int]]) = {
            val arr = p;
            ColOverArray(arr)
          };
          lazy val defaultRepTo: Rep[ColOverArray] = ColOverArray(element[WMyArr[Int]].defaultRepValue);
          lazy val eTo = new ColOverArrayElem(this)
        };
        abstract class ColOverArrayCompanionAbs extends CompanionBase[ColOverArrayCompanionAbs] with ColOverArrayCompanion {
          override def toString = "ColOverArray";

          def apply(arr: Rep[WMyArr[Int]]): Rep[ColOverArray] = mkColOverArray(arr)
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

        def mkColOverArray(arr: Rep[WMyArr[Int]]): Rep[ColOverArray];

        def unmkColOverArray(p: Rep[Col[Int]]): Option[Rep[WMyArr[Int]]]
      };
      trait ColsSeq extends ColsDsl with ScalanSeq { self: DemoDslSeq =>
        lazy val Col: Rep[ColCompanionAbs] = {
          final class $anon extends ColCompanionAbs with UserTypeSeq[ColCompanionAbs] {
            lazy val selfType = element[ColCompanionAbs]
          };
          new $anon()
        };

        case class SeqColOverArray(override val arr: Rep[WMyArr[Int]]) extends ColOverArray(arr) with UserTypeSeq[ColOverArray] {
          lazy val selfType = element[ColOverArray]
        };
        lazy val ColOverArray = {
          final class $anon extends ColOverArrayCompanionAbs with UserTypeSeq[ColOverArrayCompanionAbs] {
            lazy val selfType = element[ColOverArrayCompanionAbs]
          };
          new $anon()
        };

        def mkColOverArray(arr: Rep[WMyArr[Int]]): Rep[ColOverArray] = new SeqColOverArray(arr);
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

        case class ExpColOverArray(override val arr: Rep[WMyArr[Int]]) extends ColOverArray(arr) with UserTypeDef[ColOverArray] {
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
        object ColOverArrayCompanionMethods {
          object safeApply {
            def unapply(d: (Def[_$4] forSome { 
              type _$4
            })): Option[scala.Tuple2[Rep[WMyArr[Int]], Rep[Int]]] = d match {
              case MethodCall((receiver@_), (method@_), Seq((arr@_), (index@_), _*), _) if __equal(receiver.elem, ColOverArrayCompanionElem).&&(__equal(method.getName, "safeApply")) => Some(scala.Tuple2(arr, index)).asInstanceOf[Option[scala.Tuple2[Rep[WMyArr[Int]], Rep[Int]]]]
              case _ => None
            };
            def unapply(exp: (Exp[_$5] forSome { 
              type _$5
            })): Option[scala.Tuple2[Rep[WMyArr[Int]], Rep[Int]]] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          }
        };

        def mkColOverArray(arr: Rep[WMyArr[Int]]): Rep[ColOverArray] = new ExpColOverArray(arr);
        def unmkColOverArray(p: Rep[Col[Int]]) = p.elem.asInstanceOf[(Elem[_$6] forSome { 
          type _$6
        })] match {
          case ((_): ColOverArrayElem @unchecked) => Some(p.asRep[ColOverArray].arr)
          case _ => None
        };
        object ColMethods {
          object arr {
            def unapply(d: (Def[_$7] forSome { 
              type _$7
            })): Option[(Rep[Col[A]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(ColElem[_$8, _$9] forSome { 
  type _$8;
  type _$9
})].&&(__equal(method.getName, "arr")) => Some(receiver).asInstanceOf[Option[(Rep[Col[A]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$10] forSome { 
              type _$10
            })): Option[(Rep[Col[A]] forSome { 
              type A
            })] = exp match {
              case Def((d @ _)) => unapply(d)
              case _ => None
            }
          };
          object apply {
            def unapply(d: (Def[_$11] forSome { 
              type _$11
            })): Option[(scala.Tuple2[Rep[Col[A]], Rep[Int]] forSome { 
              type A
            })] = d match {
              case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(ColElem[_$12, _$13] forSome { 
  type _$12;
  type _$13
})].&&(__equal(method.getName, "apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[Col[A]], Rep[Int]] forSome { 
                type A
              })]]
              case _ => None
            };
            def unapply(exp: (Exp[_$14] forSome { 
              type _$14
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

          def arr: Rep[WMyArr[A]];
          def apply(i: Rep[Int]): Rep[A]
        };

        abstract class ColOverArray(val arr: Rep[WMyArr[Int]]) extends Col[Int] {
          def eeA: Elem[Int] = element[Int];
          def apply(i: Rep[Int]): Rep[Int] = ColOverArray.this.arr.apply(i)
        };
        trait ColCompanion;
        trait ColOverArrayCompanion {
          def safeApply(arr: Rep[WMyArr[Int]], index: Rep[Int]): Rep[Int] = IF(arr.length.<=(index)).THEN(toRep(2147483647)).ELSE(IF(index.<(toRep(0))).THEN(toRep(-2147483648)).ELSE(arr.apply(index)))
        }
      };
      trait ColsDsl extends ColsAbs { self: DemoDsl =>
        
      };
      trait ColsDslSeq extends ColsSeq { self: DemoDslSeq =>
        
      };
      trait ColsDslExp extends ColsExp { self: DemoDslExp =>
        
      };
      val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZt3ynp7b43WYAgAMTAAJYW5jZXN0b3JzdAAhTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL0xpc3Q7TAAEYm9keXEAfgABTAAQY29uY3JldGVTQ2xhc3Nlc3EAfgABTAAIZW50aXRpZXNxAH4AAUwACWVudGl0eU9wc3QAIUxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RyYWl0RGVmO0wAEGVudGl0eVJlcFN5bm9ueW10AA5Mc2NhbGEvT3B0aW9uO0wAB2ltcG9ydHNxAH4AAUwAB21ldGhvZHNxAH4AAUwABG5hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAtwYWNrYWdlTmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wACnNlcURzbEltcGxxAH4AA3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLkxpc3QkU2VyaWFsaXphdGlvblByb3h5AAAAAAAAAAEDAAB4cHNyACxzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0U2VyaWFsaXplRW5kJIpcY1v3UwttAgAAeHB4cQB+AAdzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0RlZgfQfWjxqHAzAgAKWgAKaXNBYnN0cmFjdEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcmFpdENhbGxQ6xktJexFWAIAAkwABG5hbWVxAH4ABEwACXRwZVNFeHByc3EAfgABeHB0AANDb2xzcQB+AAZzcgAjc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVQcmltaXRpdmU1w7tV8L5OBQIAAkwAEmRlZmF1bHRWYWx1ZVN0cmluZ3EAfgAETAAEbmFtZXEAfgAEeHB0AAEwdAADSW50cQB+AAl4cQB+AAl4cQB+AAdzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZ3Mn6+5kxTAAXAIAAUwABGFyZ3NxAH4AAXhwc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ2xhc3NBcmcFc9jWC9QpPwIACFoAB2ltcEZsYWdaAAxpc0VsZW1PckNvbnRaAAhvdmVyRmxhZ1oAB3ZhbEZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBldAAgTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTVHBlRXhwcjt4cAAAAAFxAH4AB3NyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHIADHNjYWxhLk9wdGlvbv5pN/3bDmZ0AgAAeHB0AANhcnJzcQB+AA90AAVNeUFycnNxAH4ABnEAfgAUcQB+AAl4cQB+AAl4c3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kRGVm3VlxcbcwKw4CAApaAAxpc0VsZW1PckNvbnRaAAppc0ltcGxpY2l0WgAKaXNPdmVycmlkZUwAC2Fubm90YXRpb25zcQB+AAFMAAthcmdTZWN0aW9uc3EAfgABTAAEYm9keXEAfgADTAAEbmFtZXEAfgAETAAKb3ZlcmxvYWRJZHEAfgADTAAHdHBlQXJnc3EAfgABTAAGdHBlUmVzcQB+AAN4cAAAAHEAfgAHc3EAfgAGc3IAIXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kQXJncynCJ5ZedbqLAgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2RBcmcD3P/Lk3JzAwIAB1oAB2ltcEZsYWdaAAxpc0VsZW1PckNvbnRaAAhvdmVyRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGVxAH4AG3hwAAAAcQB+AAdxAH4AH3QAAWlxAH4AFHEAfgAJeHEAfgAJeHNyAApzY2FsYS5Tb21lESLyaV6hi3QCAAFMAAF4dAASTGphdmEvbGFuZy9PYmplY3Q7eHEAfgAec3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQXBwbHmEZimf0pr8fwIAA0wABWFyZ3NzcQB+AAFMAANmdW50AB1Mc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNFeHByO0wAAnRzcQB+AAF4cHNxAH4ABnNxAH4ABnNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0lkZW50JxKl+/AJ108CAAFMAARuYW1lcQB+AAR4cHQAAWlxAH4ACXhxAH4ACXhzcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxlY3QL3WllZUD5hQIAAkwABGV4cHJxAH4AMkwABXRuYW1lcQB+AAR4cHNxAH4AOXNyABtzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RoaXPqHtDGoF+LZwIAAUwACHR5cGVOYW1lcQB+AAR4cHQADENvbE92ZXJBcnJheXQAA2FycnQABWFwcGx5cQB+AAdxAH4AQHEAfgAfcQB+AAdzcQB+AC5xAH4AFHEAfgAJeHNxAH4ALnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU09iamVjdERlZoBV0ZN0CXpjAgADTAAJYW5jZXN0b3JzcQB+AAFMAARib2R5cQB+AAFMAARuYW1lcQB+AAR4cHEAfgAHc3EAfgAGc3EAfgAlAAAAcQB+AAdzcQB+AAZzcQB+AChzcQB+AAZzcQB+ACsAAABxAH4AB3EAfgAfdAADYXJyc3EAfgAPdAAFTXlBcnJzcQB+AAZxAH4AFHEAfgAJeHNxAH4AKwAAAHEAfgAHcQB+AB90AAVpbmRleHEAfgAUcQB+AAl4cQB+AAl4c3EAfgAuc3IAGXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTSWYollZnuMMsHQIAA0wABGNvbmRxAH4AMkwAAmVscQB+ADJMAAJ0aHEAfgAyeHBzcQB+ADFzcQB+AAZzcQB+AAZzcQB+ADZ0AAVpbmRleHEAfgAJeHEAfgAJeHNxAH4AOXNxAH4AOXNxAH4ANnQAA2FycnQABmxlbmd0aHQACCRsZXNzJGVxcQB+AAdzcQB+AFJzcQB+ADFzcQB+AAZzcQB+AAZzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDb25zdBpNFNFiS2eKAgABTAABY3EAfgAveHBzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAHEAfgAJeHEAfgAJeHNxAH4AOXNxAH4ANnQABWluZGV4dAAFJGxlc3NxAH4AB3NxAH4AMXNxAH4ABnNxAH4ABnNxAH4ANnQABWluZGV4cQB+AAl4cQB+AAl4c3EAfgA5c3EAfgA2dAADYXJycQB+AEBxAH4AB3NxAH4AY3NxAH4AZYAAAABzcQB+AGNzcQB+AGV/////dAAJc2FmZUFwcGx5cQB+AB9xAH4AB3NxAH4ALnEAfgAUcQB+AAl4dAAMQ29sT3ZlckFycmF5c3EAfgAXcQB+AAd0AAxDb2xPdmVyQXJyYXlxAH4AH3EAfgAHcQB+AAl4c3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHJhaXREZWYBvjSSNBSxsgIACVoACGJpdG1hcCQwTAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3NxAH4ADEwABG5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAAd0cGVBcmdzcQB+AAF4cABxAH4AB3EAfgAHc3EAfgAGc3EAfgAlAAAAcQB+AAdxAH4AB3EAfgAfdAADYXJycQB+AB9xAH4AB3NxAH4ALnNxAH4AD3QABU15QXJyc3EAfgAGc3EAfgAPdAABQXEAfgAHcQB+AAl4c3EAfgAlAAAAcQB+AAdzcQB+AAZzcQB+AChzcQB+AAZzcQB+ACsAAABxAH4AB3EAfgAfdAABaXEAfgAUcQB+AAl4cQB+AAl4cQB+AB9xAH4AQHEAfgAfcQB+AAdzcQB+AC5zcQB+AA90AAFBcQB+AAdxAH4ACXhxAH4AH3B0AANDb2xxAH4AH3NxAH4ABnNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RwZUFyZ5JbJkhy8ZuJAgAETAAFYm91bmRxAH4AA0wADGNvbnRleHRCb3VuZHEAfgABTAAEbmFtZXEAfgAETAAHdHBhcmFtc3EAfgABeHBxAH4AH3EAfgAHdAABQXEAfgAHcQB+AAl4cQB+AAl4cQB+AH9xAH4AH3NxAH4ABnNyACFzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0ltcG9ydFN0YXSsgKBKw8YbIgIAAUwABG5hbWVxAH4ABHhwdAAOc2NhbGFuLkhvdFNwb3RzcQB+AJh0ACBzY2FsYW4uY29tcGlsYXRpb24uS2VybmVsVHlwZXMuX3EAfgAJeHEAfgAHdAAEQ29sc3QACnNjYWxhbml6ZXJzcQB+AC5zcgAic2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxmVHlwZURlZn73nU4qzrPlAgACTAAKY29tcG9uZW50c3EAfgABTAAEbmFtZXEAfgAEeHBzcQB+AAZzcQB+AA90AAREZW1vcQB+AAdxAH4ACXh0AARzZWxmcQB+AB8="
    }

    object HotSpotKernels {
      import java.io.File;
      import scalan.compilation.GraphVizConfig;
      lazy val safeApplyKernel = {
        val ctx = HotSpotManager.getScalanContext;
        val compilerOutput = ctx.buildExecutable(new File("./it-out/".+("safeApply")), "safeApply", ctx.safeApplyWrapper, GraphVizConfig.default)(ctx.CompilerConfig(Some("2.11.2"), Seq.empty));
        val x$1 = (ctx.loadMethod(compilerOutput): @scala.unchecked) match {
          case scala.Tuple2((cls @ _), (method @ _)) => scala.Tuple2(cls, method)
        };
        val cls = x$1._1;
        val method = x$1._2;
        val instance = cls.newInstance().asInstanceOf[scala.Function1[scala.Tuple2[MyArr[Int], Int], Int]];
        instance
      }
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
        lazy val safeApplyWrapper = fun(((in: Rep[scala.Tuple2[WMyArr[Int], Int]]) => {
          val arr: Rep[WMyArr[Int]] = in._1;
          val index: Rep[Int] = in._2;
          ColOverArray.safeApply(arr, index)
        }));
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