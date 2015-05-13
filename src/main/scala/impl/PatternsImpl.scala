package patterns {
  object StagedEvaluation {
    import scalan._;
    import scala.reflect.runtime.universe._;
    import scala.reflect._;
    import scalan.common.Default;
    trait PatternsAbs extends Patterns with ScalanDsl { self: PatternsDsl =>
      implicit def proxyPattern(p: Rep[Pattern]): Pattern = proxyOps[Pattern](p)(classTag[Pattern]);
      class PatternElem[To <: Pattern] extends EntityElem[To] {
        override def isEntityType = true;
        override def tag = weakTypeTag[Pattern].asInstanceOf[WeakTypeTag[To]];
        override def convert(x: Rep[(Reifiable[_$1] forSome { 
          type _$1
        })]) = convertPattern(x.asRep[Pattern]);
        def convertPattern(x: Rep[Pattern]): Rep[To] = x.asRep[To];
        override def getDefaultRep: Rep[To] = ???
      };
      implicit def patternElement: Elem[Pattern] = {
        final class $anon extends PatternElem[Pattern];
        new $anon()
      };
      trait PatternCompanionElem extends CompanionElem[PatternCompanionAbs];
      implicit lazy val PatternCompanionElem: PatternCompanionElem = {
        final class $anon extends PatternCompanionElem {
          lazy val tag = weakTypeTag[PatternCompanionAbs];
          protected def getDefaultRep = Pattern
        };
        new $anon()
      };
      abstract class PatternCompanionAbs extends CompanionBase[PatternCompanionAbs] with PatternCompanion {
        override def toString = "Pattern"
      };
      def Pattern: Rep[PatternCompanionAbs];
      implicit def proxyPatternCompanion(p: Rep[PatternCompanion]): PatternCompanion = proxyOps[PatternCompanion](p)
    };
    trait PatternsSeq extends PatternsDsl with ScalanSeq { self: PatternsDslSeq =>
      lazy val Pattern: Rep[PatternCompanionAbs] = {
        final class $anon extends PatternCompanionAbs with UserTypeSeq[PatternCompanionAbs] {
          lazy val selfType = element[PatternCompanionAbs]
        };
        new $anon()
      }
    };
    trait PatternsExp extends PatternsDsl with ScalanExp { self: PatternsDslExp =>
      lazy val Pattern: Rep[PatternCompanionAbs] = {
        final class $anon extends PatternCompanionAbs with UserTypeDef[PatternCompanionAbs] {
          lazy val selfType = element[PatternCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      object PatternMethods {
        object get {
          def unapply(d: (Def[_$2] forSome { 
            type _$2
          })): Option[scala.Tuple3[Rep[Pattern], Rep[Any], Rep[Int]]] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((a @ _), (b @ _), _*), _) if receiver.elem.isInstanceOf[(PatternElem[_$3] forSome { 
  type _$3
})].&&(method.getName.==("get")) => Some(scala.Tuple3(receiver, a, b)).asInstanceOf[Option[scala.Tuple3[Rep[Pattern], Rep[Any], Rep[Int]]]]
            case _ => None
          };
          def unapply(exp: (Exp[_$4] forSome { 
            type _$4
          })): Option[scala.Tuple3[Rep[Pattern], Rep[Any], Rep[Int]]] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object PatternCompanionMethods
    };
    trait Patterns extends Base { self: PatternsDsl =>
      trait Pattern extends Reifiable[Pattern] {
        def get(a: Rep[Any], b: Rep[Int]): Rep[String] = IF(a.==(toRep(0))).THEN(toRep("0 or 1")).ELSE(IF(a.==(toRep(1))).THEN(toRep("0 or 1")).ELSE(IF(a.==(b)).THEN(toRep("b")).ELSE(IF(a.==(scala.math.Pi)).THEN(toRep("3.14")).ELSE(toRep("Unknown")))))
      };
      trait PatternCompanion
    };
    trait PatternsDsl extends PatternsAbs;
    trait PatternsDslSeq extends PatternsSeq;
    trait PatternsDslExp extends PatternsExp;
    val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZrSr+/co886dAgALTAAEYm9keXQAIUxzY2FsYS9jb2xsZWN0aW9uL2ltbXV0YWJsZS9MaXN0O0wAEGNvbmNyZXRlU0NsYXNzZXNxAH4AAUwACGVudGl0aWVzcQB+AAFMAAllbnRpdHlPcHN0ACFMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcmFpdERlZjtMABBlbnRpdHlSZXBTeW5vbnltdAAOTHNjYWxhL09wdGlvbjtMAAdpbXBvcnRzcQB+AAFMAAdtZXRob2RzcQB+AAFMAARuYW1ldAASTGphdmEvbGFuZy9TdHJpbmc7TAALcGFja2FnZU5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAApzZXFEc2xJbXBscQB+AAN4cHNyADJzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0JFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABAwAAeHBzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHEAfgAHc3EAfgAGc3IAH3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHJhaXREZWYBvjSSNBSxsgIACVoACGJpdG1hcCQwTAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYm9keXEAfgABTAAJY29tcGFuaW9ucQB+AANMAAxpbXBsaWNpdEFyZ3N0ACJMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNDbGFzc0FyZ3M7TAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHEAfgAHcQB+AAdzcQB+AAZzcgAgc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2REZWbdWXFxtzArDgIACloADGlzRWxlbU9yQ29udFoACmlzSW1wbGljaXRaAAppc092ZXJyaWRlTAALYW5ub3RhdGlvbnNxAH4AAUwAC2FyZ1NlY3Rpb25zcQB+AAFMAARib2R5cQB+AANMAARuYW1lcQB+AARMAApvdmVybG9hZElkcQB+AANMAAd0cGVBcmdzcQB+AAFMAAZ0cGVSZXNxAH4AA3hwAAAAcQB+AAdzcQB+AAZzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNZXRob2RBcmdzKcInll51uosCAAFMAARhcmdzcQB+AAF4cHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZwPc/8uTcnMDAgAHWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnTAALYW5ub3RhdGlvbnNxAH4AAUwAB2RlZmF1bHRxAH4AA0wABG5hbWVxAH4ABEwAA3RwZXQAIExzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU1RwZUV4cHI7eHAAAABxAH4AB3NyAAtzY2FsYS5Ob25lJEZQJPZTypSsAgAAeHIADHNjYWxhLk9wdGlvbv5pN/3bDmZ0AgAAeHB0AAFhc3IAI3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlUHJpbWl0aXZlNcO7VfC+TgUCAAJMABJkZWZhdWx0VmFsdWVTdHJpbmdxAH4ABEwABG5hbWVxAH4ABHhwdAAAdAADQW55c3EAfgAVAAAAcQB+AAdxAH4AGnQAAWJzcQB+ABx0AAEwdAADSW50cQB+AAl4cQB+AAl4c3IACnNjYWxhLlNvbWURIvJpXqGLdAIAAUwAAXh0ABJMamF2YS9sYW5nL09iamVjdDt4cQB+ABlzcgAcc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNNYXRjaEa3eft2f5xhAgACTAAFY2FzZXNxAH4AAUwACHNlbGVjdG9ydAAdTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTRXhwcjt4cHNxAH4ABnNyABtzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0Nhc2XGkOQzXVFzBgIAA0wABGJvZHlxAH4AKUwABWd1YXJkcQB+AClMAANwYXR0ACBMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNQYXR0ZXJuO3hwc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQ29uc3QaTRTRYktnigIAAUwAAWNxAH4AJnhwdAAGMCBvciAxc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTRW1wdHmRYBXhS+VovgIAAHhwc3IAIXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTQWx0UGF0dGVybuw02y3tODooAgABTAAEYWx0c3EAfgABeHBzcQB+AAZzcgAjc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDb25zdFBhdHRlcm6BR+SbzHe9CQIAAUwABWNvbnN0dAAeTHNjYWxhbi9tZXRhL1NjYWxhbkFzdCRTQ29uc3Q7eHBzcQB+AC9zcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAHNxAH4AN3NxAH4AL3NxAH4AOwAAAAFxAH4ACXhzcQB+ACxzcQB+AC90AAFic3EAfgAyc3IAJnNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTU3RhYmxlSWRQYXR0ZXJu0AH4L7dX6GACAAFMAAJpZHQAHkxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU0lkZW50O3hwc3IAHHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTSWRlbnQnEqX78AnXTwIAAUwABG5hbWVxAH4ABHhwdAABYnNxAH4ALHNxAH4AL3QABDMuMTRzcQB+ADJzcgAhc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxQYXR0ZXJusaQfbt9FamMCAAJMAARuYW1lcQB+AARMAANzZWxxAH4AKXhwdAACUGlzcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxlY3QL3WllZUD5hQIAAkwABGV4cHJxAH4AKUwABXRuYW1lcQB+AAR4cHNxAH4ASHQABXNjYWxhdAAEbWF0aHNxAH4ALHNxAH4AL3QAB1Vua25vd25zcQB+ADJzcgAmc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNXaWxkY2FyZFBhdHRlcm520t+332n/pQIAAHhwcQB+AAl4c3EAfgBIdAABYXQAA2dldHEAfgAacQB+AAdzcQB+ACVzcQB+ABx0AAIiInQABlN0cmluZ3EAfgAJeHEAfgAacHQAB1BhdHRlcm5xAH4AGnEAfgAHcQB+AAl4cQB+AA1xAH4AGnEAfgAHcQB+AAd0AAhQYXR0ZXJuc3QACHBhdHRlcm5zcQB+ABpxAH4AGg=="
  }
}