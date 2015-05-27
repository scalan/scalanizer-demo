package paradise.linalgebra.vectors {
  object StagedEvaluation {
    import scalan._;
    import scala.reflect.runtime.universe._;
    import scala.reflect._;
    import scalan.common.Default;
    trait VectorsAbs extends Vectors with ScalanDsl { self: VectorsDsl =>
      implicit def proxyAbstractVector[T](p: Rep[AbstractVector[T]]): AbstractVector[T] = proxyOps[AbstractVector[T]](p)(classTag[AbstractVector[T]]);
      class AbstractVectorElem[T, To <: AbstractVector[T]](implicit val eT: Elem[T]) extends EntityElem[To] {
        override def isEntityType = true;
        override lazy val tag = {
          implicit val tagT = eT.tag;
          weakTypeTag[AbstractVector[T]].asInstanceOf[WeakTypeTag[To]]
        };
        override def convert(x: Rep[(Reifiable[_$1] forSome { 
          type _$1
        })]) = {
          val conv = fun(((x: Rep[AbstractVector[T]]) => convertAbstractVector(x)));
          tryConvert(element[AbstractVector[T]], this, x, conv)
        };
        def convertAbstractVector(x: Rep[AbstractVector[T]]): Rep[To] = {
          assert(x.selfType1 match {
            case ((_): AbstractVectorElem[(_), (_)]) => true
            case _ => false
          });
          x.asRep[To]
        };
        override def getDefaultRep: Rep[To] = ???
      };
      implicit def abstractVectorElement[T](implicit eT: Elem[T]): Elem[AbstractVector[T]] = new AbstractVectorElem[T, AbstractVector[T]]();
      implicit case object AbstractVectorCompanionElem extends CompanionElem[AbstractVectorCompanionAbs] with scala.Product with scala.Serializable {
        lazy val tag = weakTypeTag[AbstractVectorCompanionAbs];
        protected def getDefaultRep = AbstractVector
      };
      abstract class AbstractVectorCompanionAbs extends CompanionBase[AbstractVectorCompanionAbs] with AbstractVectorCompanion {
        override def toString = "AbstractVector"
      };
      def AbstractVector: Rep[AbstractVectorCompanionAbs];
      implicit def proxyAbstractVectorCompanion(p: Rep[AbstractVectorCompanion]): AbstractVectorCompanion = proxyOps[AbstractVectorCompanion](p);
      class DenseVectorElem[T](val iso: Iso[DenseVectorData[T], DenseVector[T]])(implicit eT: Elem[T]) extends AbstractVectorElem[T, DenseVector[T]] with ConcreteElem[DenseVectorData[T], DenseVector[T]] {
        override def convertAbstractVector(x: Rep[AbstractVector[T]]) = DenseVector(x.items);
        override def getDefaultRep = super[ConcreteElem].getDefaultRep;
        override lazy val tag = {
          implicit val tagT = eT.tag;
          weakTypeTag[DenseVector[T]]
        }
      };
      type DenseVectorData[T] = Collection[T];
      class DenseVectorIso[T](implicit eT: Elem[T]) extends Iso[DenseVectorData[T], DenseVector[T]] {
        override def from(p: Rep[DenseVector[T]]) = p.items;
        override def to(p: Rep[Collection[T]]) = {
          val items = p;
          DenseVector(items)
        };
        lazy val defaultRepTo = Default.defaultVal[Rep[DenseVector[T]]](DenseVector(element[Collection[T]].defaultRepValue));
        lazy val eTo = new DenseVectorElem[T](this)
      };
      abstract class DenseVectorCompanionAbs extends CompanionBase[DenseVectorCompanionAbs] with DenseVectorCompanion {
        override def toString = "DenseVector";
        def apply[T](items: Rep[Collection[T]])(implicit eT: Elem[T]): Rep[DenseVector[T]] = mkDenseVector(items)
      };
      object DenseVectorMatcher {
        def unapply[T](p: Rep[AbstractVector[T]]) = unmkDenseVector(p)
      };
      def DenseVector: Rep[DenseVectorCompanionAbs];
      implicit def proxyDenseVectorCompanion(p: Rep[DenseVectorCompanionAbs]): DenseVectorCompanionAbs = proxyOps[DenseVectorCompanionAbs](p);
      implicit case object DenseVectorCompanionElem extends CompanionElem[DenseVectorCompanionAbs] with scala.Product with scala.Serializable {
        lazy val tag = weakTypeTag[DenseVectorCompanionAbs];
        protected def getDefaultRep = DenseVector
      };
      implicit def proxyDenseVector[T](p: Rep[DenseVector[T]]): DenseVector[T] = proxyOps[DenseVector[T]](p);
      implicit class ExtendedDenseVector[T](p: Rep[DenseVector[T]])(implicit eT: Elem[T]) {
        def toData: Rep[DenseVectorData[T]] = isoDenseVector(eT).from(p)
      };
      implicit def isoDenseVector[T](implicit eT: Elem[T]): Iso[DenseVectorData[T], DenseVector[T]] = new DenseVectorIso[T]();
      def mkDenseVector[T](items: Rep[Collection[T]])(implicit eT: Elem[T]): Rep[DenseVector[T]];
      def unmkDenseVector[T](p: Rep[AbstractVector[T]]): Option[Rep[Collection[T]]]
    };
    trait VectorsSeq extends VectorsDsl with ScalanSeq { self: VectorsDslSeq =>
      lazy val AbstractVector: Rep[AbstractVectorCompanionAbs] = {
        final class $anon extends AbstractVectorCompanionAbs with UserTypeSeq[AbstractVectorCompanionAbs] {
          lazy val selfType = element[AbstractVectorCompanionAbs]
        };
        new $anon()
      };
      case class SeqDenseVector[T](override val items: Rep[Collection[T]])(implicit eT: Elem[T]) extends DenseVector[T](items) with UserTypeSeq[DenseVector[T]] {
        lazy val selfType = element[DenseVector[T]]
      };
      lazy val DenseVector = {
        final class $anon extends DenseVectorCompanionAbs with UserTypeSeq[DenseVectorCompanionAbs] {
          lazy val selfType = element[DenseVectorCompanionAbs]
        };
        new $anon()
      };
      def mkDenseVector[T](items: Rep[Collection[T]])(implicit eT: Elem[T]): Rep[DenseVector[T]] = new SeqDenseVector[T](items);
      def unmkDenseVector[T](p: Rep[AbstractVector[T]]) = p match {
        case (p @ ((_): DenseVector[T] @unchecked)) => Some(p.items)
        case _ => None
      }
    };
    trait VectorsExp extends VectorsDsl with ScalanExp { self: VectorsDslExp =>
      lazy val AbstractVector: Rep[AbstractVectorCompanionAbs] = {
        final class $anon extends AbstractVectorCompanionAbs with UserTypeDef[AbstractVectorCompanionAbs] {
          lazy val selfType = element[AbstractVectorCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      case class ExpDenseVector[T](override val items: Rep[Collection[T]])(implicit eT: Elem[T]) extends DenseVector[T](items) with UserTypeDef[DenseVector[T]] {
        lazy val selfType = element[DenseVector[T]];
        override def mirror(t: Transformer) = ExpDenseVector[T](t(items))
      };
      lazy val DenseVector: Rep[DenseVectorCompanionAbs] = {
        final class $anon extends DenseVectorCompanionAbs with UserTypeDef[DenseVectorCompanionAbs] {
          lazy val selfType = element[DenseVectorCompanionAbs];
          override def mirror(t: Transformer) = this
        };
        new $anon()
      };
      object DenseVectorMethods {
        object length {
          def unapply(d: (Def[_$2] forSome { 
            type _$2
          })): Option[(Rep[DenseVector[T]] forSome { 
            type T
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(DenseVectorElem[_$3] forSome { 
  type _$3
})].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[(Rep[DenseVector[T]] forSome { 
              type T
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$4] forSome { 
            type _$4
          })): Option[(Rep[DenseVector[T]] forSome { 
            type T
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object apply {
          def unapply(d: (Def[_$5] forSome { 
            type _$5
          })): Option[(scala.Tuple2[Rep[DenseVector[T]], Rep[Int]] forSome { 
            type T
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(DenseVectorElem[_$6] forSome { 
  type _$6
})].&&(method.getName.==("apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[DenseVector[T]], Rep[Int]] forSome { 
              type T
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$7] forSome { 
            type _$7
          })): Option[(scala.Tuple2[Rep[DenseVector[T]], Rep[Int]] forSome { 
            type T
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object dot {
          def unapply(d: (Def[_$8] forSome { 
            type _$8
          })): Option[(scala.Tuple3[Rep[DenseVector[T]], Rep[Vector[T]], Rep[Numeric[T]]] forSome { 
            type T
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((other @ _), (n @ _), _*), _) if receiver.elem.isInstanceOf[(DenseVectorElem[_$9] forSome { 
  type _$9
})].&&(method.getName.==("dot")) => Some(scala.Tuple3(receiver, other, n)).asInstanceOf[Option[(scala.Tuple3[Rep[DenseVector[T]], Rep[Vector[T]], Rep[Numeric[T]]] forSome { 
              type T
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$10] forSome { 
            type _$10
          })): Option[(scala.Tuple3[Rep[DenseVector[T]], Rep[Vector[T]], Rep[Numeric[T]]] forSome { 
            type T
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object DenseVectorCompanionMethods;
      def mkDenseVector[T](items: Rep[Collection[T]])(implicit eT: Elem[T]): Rep[DenseVector[T]] = new ExpDenseVector[T](items);
      def unmkDenseVector[T](p: Rep[AbstractVector[T]]) = p.elem.asInstanceOf[(Elem[_$11] forSome { 
        type _$11
      })] match {
        case ((_): DenseVectorElem[T] @unchecked) => Some(p.asRep[DenseVector[T]].items)
        case _ => None
      };
      object AbstractVectorMethods {
        object length {
          def unapply(d: (Def[_$12] forSome { 
            type _$12
          })): Option[(Rep[AbstractVector[T]] forSome { 
            type T
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(AbstractVectorElem[_$13, _$14] forSome { 
  type _$13;
  type _$14
})].&&(method.getName.==("length")) => Some(receiver).asInstanceOf[Option[(Rep[AbstractVector[T]] forSome { 
              type T
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$15] forSome { 
            type _$15
          })): Option[(Rep[AbstractVector[T]] forSome { 
            type T
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object items {
          def unapply(d: (Def[_$16] forSome { 
            type _$16
          })): Option[(Rep[AbstractVector[T]] forSome { 
            type T
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), _, _) if receiver.elem.isInstanceOf[(AbstractVectorElem[_$17, _$18] forSome { 
  type _$17;
  type _$18
})].&&(method.getName.==("items")) => Some(receiver).asInstanceOf[Option[(Rep[AbstractVector[T]] forSome { 
              type T
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$19] forSome { 
            type _$19
          })): Option[(Rep[AbstractVector[T]] forSome { 
            type T
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object apply {
          def unapply(d: (Def[_$20] forSome { 
            type _$20
          })): Option[(scala.Tuple2[Rep[AbstractVector[T]], Rep[Int]] forSome { 
            type T
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((i @ _), _*), _) if receiver.elem.isInstanceOf[(AbstractVectorElem[_$21, _$22] forSome { 
  type _$21;
  type _$22
})].&&(method.getName.==("apply")) => Some(scala.Tuple2(receiver, i)).asInstanceOf[Option[(scala.Tuple2[Rep[AbstractVector[T]], Rep[Int]] forSome { 
              type T
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$23] forSome { 
            type _$23
          })): Option[(scala.Tuple2[Rep[AbstractVector[T]], Rep[Int]] forSome { 
            type T
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        };
        object dot {
          def unapply(d: (Def[_$24] forSome { 
            type _$24
          })): Option[(scala.Tuple3[Rep[AbstractVector[T]], Rep[Vector[T]], Rep[Numeric[T]]] forSome { 
            type T
          })] = d match {
            case MethodCall((receiver @ _), (method @ _), Seq((other @ _), (n @ _), _*), _) if receiver.elem.isInstanceOf[(AbstractVectorElem[_$25, _$26] forSome { 
  type _$25;
  type _$26
})].&&(method.getName.==("dot")) => Some(scala.Tuple3(receiver, other, n)).asInstanceOf[Option[(scala.Tuple3[Rep[AbstractVector[T]], Rep[Vector[T]], Rep[Numeric[T]]] forSome { 
              type T
            })]]
            case _ => None
          };
          def unapply(exp: (Exp[_$27] forSome { 
            type _$27
          })): Option[(scala.Tuple3[Rep[AbstractVector[T]], Rep[Vector[T]], Rep[Numeric[T]]] forSome { 
            type T
          })] = exp match {
            case Def((d @ _)) => unapply(d)
            case _ => None
          }
        }
      };
      object AbstractVectorCompanionMethods
    };
    trait Vectors extends Base { self: VectorsDsl =>
      trait AbstractVector[T] extends Reifiable[AbstractVector[T]] {
        implicit def eT: Elem[T];
        def length: Rep[Int];
        def items: Collection[T];
        def apply(i: Rep[Int]): Rep[T];
        def map[R](f: Rep[scala.Function1[T, R]])(implicit eR: Elem[R]): Vector[R];
        def dot(other: Vector[T])(implicit n: Rep[Numeric[T]]): Rep[T]
      };
      abstract class DenseVector[T](val items: Collection[T])(implicit val eT: Elem[T]) extends AbstractVector[T] with Product with Serializable {
        def length = items.length;
        def apply(i: Rep[Int]): Rep[T] = items(i);
        def map[R](f: Rep[scala.Function1[T, R]])(implicit eR: Elem[R]): Vector[R] = DenseVector(items.map(f));
        def dot(other: Vector[T])(implicit n: Rep[Numeric[T]]): Rep[T] = other.items.zip(items).map(fun(((v: Rep[scala.Tuple2[T, T]]) => n.times(v._1, v._2)))).reduce
      };
      trait AbstractVectorCompanion;
      trait DenseVectorCompanion
    };
    trait VectorsDsl extends VectorsAbs;
    trait VectorsDslSeq extends VectorsSeq;
    trait VectorsDslExp extends VectorsExp;
    val serializedMetaAst = "rO0ABXNyACZzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VudGl0eU1vZHVsZURlZrSr+/co886dAgALTAAEYm9keXQAIUxzY2FsYS9jb2xsZWN0aW9uL2ltbXV0YWJsZS9MaXN0O0wAEGNvbmNyZXRlU0NsYXNzZXNxAH4AAUwACGVudGl0aWVzcQB+AAFMAAllbnRpdHlPcHN0ACFMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcmFpdERlZjtMABBlbnRpdHlSZXBTeW5vbnltdAAOTHNjYWxhL09wdGlvbjtMAAdpbXBvcnRzcQB+AAFMAAdtZXRob2RzcQB+AAFMAARuYW1ldAASTGphdmEvbGFuZy9TdHJpbmc7TAALcGFja2FnZU5hbWVxAH4ABEwACHNlbGZUeXBlcQB+AANMAApzZXFEc2xJbXBscQB+AAN4cHNyADJzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5MaXN0JFNlcmlhbGl6YXRpb25Qcm94eQAAAAAAAAABAwAAeHBzcgAsc2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuTGlzdFNlcmlhbGl6ZUVuZCSKXGNb91MLbQIAAHhweHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzRGVmB9B9aPGocDMCAApaAAppc0Fic3RyYWN0TAAJYW5jZXN0b3JzcQB+AAFMAAthbm5vdGF0aW9uc3EAfgABTAAEYXJnc3QAIkxzY2FsYW4vbWV0YS9TY2FsYW5Bc3QkU0NsYXNzQXJncztMAARib2R5cQB+AAFMAAljb21wYW5pb25xAH4AA0wADGltcGxpY2l0QXJnc3EAfgAMTAAEbmFtZXEAfgAETAAIc2VsZlR5cGVxAH4AA0wAB3RwZUFyZ3NxAH4AAXhwAHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0Q2FsbFDrGS0l7EVYAgACTAAEbmFtZXEAfgAETAAJdHBlU0V4cHJzcQB+AAF4cHQADkFic3RyYWN0VmVjdG9yc3EAfgAGc3EAfgAPdAABVHEAfgAHcQB+AAl4c3EAfgAPdAAHUHJvZHVjdHEAfgAHc3EAfgAPdAAMU2VyaWFsaXphYmxlcQB+AAdxAH4ACXhxAH4AB3NyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0NsYXNzQXJncyfr7mTFMABcAgABTAAEYXJnc3EAfgABeHBzcQB+AAZzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNDbGFzc0FyZwVz2NYL1Ck/AgAIWgAHaW1wRmxhZ1oADGlzRWxlbU9yQ29udFoACG92ZXJGbGFnWgAHdmFsRmxhZ0wAC2Fubm90YXRpb25zcQB+AAFMAAdkZWZhdWx0cQB+AANMAARuYW1lcQB+AARMAAN0cGV0ACBMc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNUcGVFeHByO3hwAAAAAXEAfgAHc3IAC3NjYWxhLk5vbmUkRlAk9lPKlKwCAAB4cgAMc2NhbGEuT3B0aW9u/mk3/dsOZnQCAAB4cHQABWl0ZW1zc3EAfgAPdAAKQ29sbGVjdGlvbnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHNxAH4ABnNyACBzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZERlZt1ZcXG3MCsOAgAKWgAMaXNFbGVtT3JDb250WgAKaXNJbXBsaWNpdFoACmlzT3ZlcnJpZGVMAAthbm5vdGF0aW9uc3EAfgABTAALYXJnU2VjdGlvbnNxAH4AAUwABGJvZHlxAH4AA0wABG5hbWVxAH4ABEwACm92ZXJsb2FkSWRxAH4AA0wAB3RwZUFyZ3NxAH4AAUwABnRwZVJlc3EAfgADeHAAAABxAH4AB3EAfgAHc3IACnNjYWxhLlNvbWURIvJpXqGLdAIAAUwAAXh0ABJMamF2YS9sYW5nL09iamVjdDt4cQB+ACBzcgAdc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxlY3QL3WllZUD5hQIAAkwABGV4cHJ0AB1Mc2NhbGFuL21ldGEvU2NhbGFuQXN0JFNFeHByO0wABXRuYW1lcQB+AAR4cHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0lkZW50JxKl+/AJ108CAAFMAARuYW1lcQB+AAR4cHQABWl0ZW1zdAAGbGVuZ3RocQB+ADRxAH4AIXEAfgAHcQB+ACFzcQB+ACkAAABxAH4AB3NxAH4ABnNyACFzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU01ldGhvZEFyZ3MpwieWXnW6iwIAAUwABGFyZ3NxAH4AAXhwc3EAfgAGc3IAIHNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTTWV0aG9kQXJnA9z/y5NycwMCAAdaAAdpbXBGbGFnWgAMaXNFbGVtT3JDb250WgAIb3ZlckZsYWdMAAthbm5vdGF0aW9uc3EAfgABTAAHZGVmYXVsdHEAfgADTAAEbmFtZXEAfgAETAADdHBlcQB+AB14cAAAAHEAfgAHcQB+ACF0AAFpc3IAI3NjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlUHJpbWl0aXZlNcO7VfC+TgUCAAJMABJkZWZhdWx0VmFsdWVTdHJpbmdxAH4ABEwABG5hbWVxAH4ABHhwdAABMHQAA0ludHEAfgAJeHEAfgAJeHNxAH4AK3NyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0FwcGx5hGYpn9Ka/H8CAANMAAVhcmdzc3EAfgABTAADZnVucQB+AC9MAAJ0c3EAfgABeHBzcQB+AAZzcQB+AAZzcQB+ADF0AAFpcQB+AAl4cQB+AAl4c3EAfgAxdAAFaXRlbXNxAH4AB3QABWFwcGx5cQB+ACFxAH4AB3NxAH4AK3NxAH4AD3QAAVRxAH4AB3NxAH4AKQAAAHEAfgAHc3EAfgAGc3EAfgA3c3EAfgAGc3EAfgA6AAAAcQB+AAdxAH4AIXQAAWZzcgAec2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVGdW5j1EmZEw8dX+ECAAJMAAZkb21haW5xAH4AHUwABXJhbmdlcQB+AB14cHNxAH4AD3QAAVRxAH4AB3NxAH4AD3QAAVJxAH4AB3EAfgAJeHEAfgAJeHNxAH4AK3NxAH4AQnNxAH4ABnNxAH4ABnNxAH4AQnNxAH4ABnNxAH4ABnNxAH4AMXEAfgBTcQB+AAl4cQB+AAl4c3EAfgAuc3EAfgAxdAAFaXRlbXN0AANtYXBxAH4AB3EAfgAJeHEAfgAJeHNxAH4AMXQAC0RlbnNlVmVjdG9ycQB+AAdxAH4AZXEAfgAhc3EAfgAGc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlQXJnklsmSHLxm4kCAARMAAVib3VuZHEAfgADTAAMY29udGV4dEJvdW5kcQB+AAFMAARuYW1lcQB+AARMAAd0cGFyYW1zcQB+AAF4cHEAfgAhcQB+AAd0AAFScQB+AAdxAH4ACXhzcQB+ACtzcQB+AA90AAZWZWN0b3JzcQB+AAZzcQB+AA90AAFScQB+AAdxAH4ACXhzcQB+ACkAAABxAH4AB3NxAH4ABnNxAH4AN3NxAH4ABnNxAH4AOgAAAHEAfgAHcQB+ACF0AAVvdGhlcnNxAH4AD3QABlZlY3RvcnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHNxAH4AN3NxAH4ABnNxAH4AOgEAAHEAfgAHcQB+ACF0AAFuc3EAfgAPdAAHTnVtZXJpY3NxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgAJeHNxAH4AK3NxAH4ALnNxAH4AQnNxAH4ABnNxAH4ABnNyABtzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0Z1bmOwTPiQGsUwgwIAAkwABnBhcmFtc3EAfgABTAADcmVzcQB+AC94cHNxAH4ABnNyAB1zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1ZhbERlZisGObp73UUPAgAFWgAKaXNJbXBsaWNpdFoABmlzTGF6eUwABGV4cHJxAH4AL0wABG5hbWVxAH4ABEwAA3RwZXEAfgADeHAAAHNyABxzY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU0VtcHR5kWAV4UvlaL4CAAB4cHQAAXZzcQB+ACtzcgAfc2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNUcGVUdXBsZTS7+V6IgEhwAgABTAAJdHBlU0V4cHJzcQB+AAF4cHNxAH4ABnNxAH4AD3QAAVRxAH4AB3NxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAJeHNxAH4AQnNxAH4ABnNxAH4ABnNxAH4ALnNxAH4AMXQAAXZ0AAJfMXNxAH4ALnNxAH4AMXQAAXZ0AAJfMnEAfgAJeHEAfgAJeHNxAH4ALnNxAH4AMXQAAW50AAV0aW1lc3EAfgAHcQB+AAl4cQB+AAl4c3EAfgAuc3EAfgBCc3EAfgAGc3EAfgAGc3EAfgAxdAAFaXRlbXNxAH4ACXhxAH4ACXhzcQB+AC5zcQB+AC5zcQB+ADF0AAVvdGhlcnQABWl0ZW1zdAADemlwcQB+AAdxAH4AZXEAfgAHdAAGcmVkdWNldAADZG90cQB+ACFxAH4AB3NxAH4AK3NxAH4AD3QAAVRxAH4AB3EAfgAJeHEAfgAhc3EAfgAZcQB+AAd0AAtEZW5zZVZlY3RvcnEAfgAhc3EAfgAGc3EAfgBpcQB+ACFxAH4AB3QAAVRxAH4AB3EAfgAJeHEAfgAJeHNxAH4ABnNyAB9zY2FsYW4ubWV0YS5TY2FsYW5Bc3QkU1RyYWl0RGVmAb40kjQUsbICAAlaAAhiaXRtYXAkMEwACWFuY2VzdG9yc3EAfgABTAALYW5ub3RhdGlvbnNxAH4AAUwABGJvZHlxAH4AAUwACWNvbXBhbmlvbnEAfgADTAAMaW1wbGljaXRBcmdzcQB+AAxMAARuYW1lcQB+AARMAAhzZWxmVHlwZXEAfgADTAAHdHBlQXJnc3EAfgABeHAAcQB+AAdxAH4AB3NxAH4ABnNxAH4AKQAAAHEAfgAHcQB+AAdxAH4AIXEAfgA0cQB+ACFxAH4AB3NxAH4AK3EAfgA+c3EAfgApAAAAcQB+AAdxAH4AB3EAfgAhdAAFaXRlbXNxAH4AIXEAfgAHc3EAfgArc3EAfgAPdAAKQ29sbGVjdGlvbnNxAH4ABnNxAH4AD3QAAVRxAH4AB3EAfgAJeHNxAH4AKQAAAHEAfgAHc3EAfgAGc3EAfgA3c3EAfgAGc3EAfgA6AAAAcQB+AAdxAH4AIXQAAWlxAH4APnEAfgAJeHEAfgAJeHEAfgAhcQB+AEpxAH4AIXEAfgAHc3EAfgArc3EAfgAPdAABVHEAfgAHc3EAfgApAAAAcQB+AAdzcQB+AAZzcQB+ADdzcQB+AAZzcQB+ADoAAABxAH4AB3EAfgAhcQB+AFNzcQB+AFRzcQB+AA90AAFUcQB+AAdzcQB+AA90AAFScQB+AAdxAH4ACXhxAH4ACXhxAH4AIXEAfgBlcQB+ACFzcQB+AAZzcQB+AGlxAH4AIXEAfgAHdAABUnEAfgAHcQB+AAl4c3EAfgArc3EAfgAPdAAGVmVjdG9yc3EAfgAGc3EAfgAPdAABUnEAfgAHcQB+AAl4c3EAfgApAAAAcQB+AAdzcQB+AAZzcQB+ADdzcQB+AAZzcQB+ADoAAABxAH4AB3EAfgAhdAAFb3RoZXJzcQB+AA90AAZWZWN0b3JzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhzcQB+ADdzcQB+AAZzcQB+ADoBAABxAH4AB3EAfgAhdAABbnNxAH4AD3QAB051bWVyaWNzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4ACXhxAH4ACXhxAH4AIXQAA2RvdHEAfgAhcQB+AAdzcQB+ACtzcQB+AA90AAFUcQB+AAdxAH4ACXhxAH4AIXB0AA5BYnN0cmFjdFZlY3RvcnEAfgAhc3EAfgAGc3EAfgBpcQB+ACFxAH4AB3QAAVRxAH4AB3EAfgAJeHEAfgAJeHEAfgDCc3EAfgArc3IAHXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTVHBlRGVmf8Y13a03HZwCAANMAARuYW1lcQB+AARMAANyaHNxAH4AHUwAB3RwZUFyZ3NxAH4AAXhwdAAGVmVjdG9yc3EAfgAPdAAOQWJzdHJhY3RWZWN0b3JzcQB+AAZzcQB+AA90AAFUcQB+AAdxAH4ACXhzcQB+AAZzcQB+AGlxAH4AIXEAfgAHdAABVHEAfgAHcQB+AAl4c3EAfgAGc3IAIXNjYWxhbi5tZXRhLlNjYWxhbkFzdCRTSW1wb3J0U3RhdKyAoErDxhsiAgABTAAEbmFtZXEAfgAEeHB0ACFwYXJhZGlzZS5saW5hbGdlYnJhLkxpbmVhckFsZ2VicmFxAH4ACXhxAH4AB3QAB1ZlY3RvcnN0ABtwYXJhZGlzZS5saW5hbGdlYnJhLnZlY3RvcnNzcQB+ACtzcgAic2NhbGFuLm1ldGEuU2NhbGFuQXN0JFNTZWxmVHlwZURlZn73nU4qzrPlAgACTAAKY29tcG9uZW50c3EAfgABTAAEbmFtZXEAfgAEeHBzcQB+AAZzcQB+AA90AA1MaW5lYXJBbGdlYnJhcQB+AAdxAH4ACXh0AARzZWxmcQB+ACE="
  }
}