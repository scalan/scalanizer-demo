package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scala.Array
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WArraysAbs extends WArrays with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWArray[T](p: Rep[WArray[T]]): WArray[T] = {
    proxyOps[WArray[T]](p)(scala.reflect.classTag[WArray[T]])
  }

  // TypeWrapper proxy
  //implicit def proxyArray[T:Elem](p: Rep[Array[T]]): WArray[T] =
  //  proxyOps[WArray[T]](p.asRep[WArray[T]])

  implicit def unwrapValueOfWArray[T](w: Rep[WArray[T]]): Rep[Array[T]] = w.wrappedValueOfBaseType

  implicit def arrayElement[T:Elem]: Elem[Array[T]]

  // familyElem
  abstract class WArrayElem[T, To <: WArray[T]](implicit val eeT: Elem[T])
    extends WrapperElem[Array[T], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WArray[T]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WArray[T]] => convertWArray(x) }
      tryConvert(element[WArray[T]], this, x, conv)
    }

    def convertWArray(x : Rep[WArray[T]]): Rep[To] = {
      assert(x.selfType1 match { case _: WArrayElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wArrayElement[T](implicit eeT: Elem[T]): Elem[WArray[T]] =
    new WArrayElem[T, WArray[T]] {
      lazy val eTo = element[WArrayImpl[T]]
    }

  implicit case object WArrayCompanionElem extends CompanionElem[WArrayCompanionAbs] {
    lazy val tag = weakTypeTag[WArrayCompanionAbs]
    protected def getDefaultRep = WArray
  }

  abstract class WArrayCompanionAbs extends CompanionBase[WArrayCompanionAbs] with WArrayCompanion {
    override def toString = "WArray"
  }
  def WArray: Rep[WArrayCompanionAbs]
  implicit def proxyWArrayCompanion(p: Rep[WArrayCompanion]): WArrayCompanion = {
    proxyOps[WArrayCompanion](p)
  }

  // default wrapper implementation
  abstract class WArrayImpl[T](val wrappedValueOfBaseType: Rep[Array[T]])(implicit val eeT: Elem[T]) extends WArray[T] {
    def apply( i: Rep[Int]): Rep[T] =
      methodCallEx[T](self,
        this.getClass.getMethod("apply", classOf[AnyRef]),
        List(i.asInstanceOf[AnyRef]))

    def length: Rep[Int] =
      methodCallEx[Int](self,
        this.getClass.getMethod("length"),
        List())
  }
  trait WArrayImplCompanion
  // elem for concrete class
  class WArrayImplElem[T](val iso: Iso[WArrayImplData[T], WArrayImpl[T]])(implicit eeT: Elem[T])
    extends WArrayElem[T, WArrayImpl[T]]
    with ConcreteElem[WArrayImplData[T], WArrayImpl[T]] {
    lazy val eTo = this
    override def convertWArray(x: Rep[WArray[T]]) = WArrayImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WArrayImpl[T]]
    }
  }

  // state representation type
  type WArrayImplData[T] = Array[T]

  // 3) Iso for concrete class
  class WArrayImplIso[T](implicit eeT: Elem[T])
    extends Iso[WArrayImplData[T], WArrayImpl[T]] {
    override def from(p: Rep[WArrayImpl[T]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[Array[T]]) = {
      val wrappedValueOfBaseType = p
      WArrayImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WArrayImpl[T]] = WArrayImpl(DefaultOfArray[T].value)
    lazy val eTo = new WArrayImplElem[T](this)
  }
  // 4) constructor and deconstructor
  abstract class WArrayImplCompanionAbs extends CompanionBase[WArrayImplCompanionAbs] with WArrayImplCompanion {
    override def toString = "WArrayImpl"

    def apply[T](wrappedValueOfBaseType: Rep[Array[T]])(implicit eeT: Elem[T]): Rep[WArrayImpl[T]] =
      mkWArrayImpl(wrappedValueOfBaseType)
  }
  object WArrayImplMatcher {
    def unapply[T](p: Rep[WArray[T]]) = unmkWArrayImpl(p)
  }
  def WArrayImpl: Rep[WArrayImplCompanionAbs]
  implicit def proxyWArrayImplCompanion(p: Rep[WArrayImplCompanionAbs]): WArrayImplCompanionAbs = {
    proxyOps[WArrayImplCompanionAbs](p)
  }

  implicit case object WArrayImplCompanionElem extends CompanionElem[WArrayImplCompanionAbs] {
    lazy val tag = weakTypeTag[WArrayImplCompanionAbs]
    protected def getDefaultRep = WArrayImpl
  }

  implicit def proxyWArrayImpl[T](p: Rep[WArrayImpl[T]]): WArrayImpl[T] =
    proxyOps[WArrayImpl[T]](p)

  implicit class ExtendedWArrayImpl[T](p: Rep[WArrayImpl[T]])(implicit eeT: Elem[T]) {
    def toData: Rep[WArrayImplData[T]] = isoWArrayImpl(eeT).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWArrayImpl[T](implicit eeT: Elem[T]): Iso[WArrayImplData[T], WArrayImpl[T]] =
    new WArrayImplIso[T]

  // 6) smart constructor and deconstructor
  def mkWArrayImpl[T](wrappedValueOfBaseType: Rep[Array[T]])(implicit eeT: Elem[T]): Rep[WArrayImpl[T]]
  def unmkWArrayImpl[T](p: Rep[WArray[T]]): Option[(Rep[Array[T]])]
}

// Exp -----------------------------------
trait WArraysExp extends WArraysDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WArray: Rep[WArrayCompanionAbs] = new WArrayCompanionAbs with UserTypeDef[WArrayCompanionAbs] {
    lazy val selfType = element[WArrayCompanionAbs]
    override def mirror(t: Transformer) = this

    def canBuildFrom[T](implicit emT: Elem[T]): Rep[WCanBuildFrom[WArray[_$4] forSome {type _$4},T,WArray[T]]] = {
      val wtag = weakTypeTag[WArray[_]]
      val defaultOfWArray: Default[WArray[_]] = Default.defaultVal(null)
      implicit val warrElem: Elem[WArray[_]] = new BaseElem[WArray[_]]()(wtag, defaultOfWArray)

      methodCallEx[WCanBuildFrom[WArray[_$4] forSome {type _$4}, T, WArray[T]]](self,
        this.getClass.getMethod("canBuildFrom", classOf[AnyRef]),
        List(emT.asInstanceOf[AnyRef]))
    }
  }

  implicit override def arrayElement[T:Elem]: Elem[Array[T]] = {
     implicit val wT = element[T].tag;
     new ExpBaseElemEx[Array[T], WArray[T]](element[WArray[T]])(weakTypeTag[Array[T]], DefaultOfArray[T])
  }

  case class ExpWArrayImpl[T]
      (override val wrappedValueOfBaseType: Rep[Array[T]])
      (implicit eeT: Elem[T])
    extends WArrayImpl[T](wrappedValueOfBaseType) with UserTypeDef[WArrayImpl[T]] {
    lazy val selfType = element[WArrayImpl[T]]
    override def mirror(t: Transformer) = ExpWArrayImpl[T](t(wrappedValueOfBaseType))
  }

  lazy val WArrayImpl: Rep[WArrayImplCompanionAbs] = new WArrayImplCompanionAbs with UserTypeDef[WArrayImplCompanionAbs] {
    lazy val selfType = element[WArrayImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WArrayImplMethods {
  }

  def mkWArrayImpl[T]
    (wrappedValueOfBaseType: Rep[Array[T]])(implicit eeT: Elem[T]): Rep[WArrayImpl[T]] =
    new ExpWArrayImpl[T](wrappedValueOfBaseType)
  def unmkWArrayImpl[T](p: Rep[WArray[T]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WArrayImplElem[T] @unchecked =>
      Some((p.asRep[WArrayImpl[T]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WArrayMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WArray[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WArrayElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WArray[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WArray[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object apply {
      def unapply(d: Def[_]): Option[(Rep[WArray[T]], Rep[Int]) forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(i, _*), _) if receiver.elem.isInstanceOf[WArrayElem[_, _]] && method.getName == "apply" =>
          Some((receiver, i)).asInstanceOf[Option[(Rep[WArray[T]], Rep[Int]) forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[WArray[T]], Rep[Int]) forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object length {
      def unapply(d: Def[_]): Option[Rep[WArray[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WArrayElem[_, _]] && method.getName == "length" =>
          Some(receiver).asInstanceOf[Option[Rep[WArray[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WArray[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WArrayCompanionMethods {
    object canBuildFrom {
      def unapply(d: Def[_]): Option[Elem[T] forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(emT, _*), _) if receiver.elem == WArrayCompanionElem && method.getName == "canBuildFrom" =>
          Some(emT).asInstanceOf[Option[Elem[T] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Elem[T] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }
}
