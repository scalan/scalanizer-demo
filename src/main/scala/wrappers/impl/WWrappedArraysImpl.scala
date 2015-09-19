package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scala.collection.mutable.WrappedArray
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WWrappedArraysAbs extends WWrappedArrays with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWWrappedArray[T](p: Rep[WWrappedArray[T]]): WWrappedArray[T] = {
    proxyOps[WWrappedArray[T]](p)(scala.reflect.classTag[WWrappedArray[T]])
  }

  // TypeWrapper proxy
  //implicit def proxyWrappedArray[T:Elem](p: Rep[WrappedArray[T]]): WWrappedArray[T] =
  //  proxyOps[WWrappedArray[T]](p.asRep[WWrappedArray[T]])

  implicit def unwrapValueOfWWrappedArray[T](w: Rep[WWrappedArray[T]]): Rep[WrappedArray[T]] = w.wrappedValueOfBaseType

  implicit def wrappedArrayElement[T:Elem]: Elem[WrappedArray[T]]

  // familyElem
  abstract class WWrappedArrayElem[T, To <: WWrappedArray[T]](implicit val eeT: Elem[T])
    extends WrapperElem[WrappedArray[T], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WWrappedArray[T]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WWrappedArray[T]] => convertWWrappedArray(x) }
      tryConvert(element[WWrappedArray[T]], this, x, conv)
    }

    def convertWWrappedArray(x : Rep[WWrappedArray[T]]): Rep[To] = {
      assert(x.selfType1 match { case _: WWrappedArrayElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wWrappedArrayElement[T](implicit eeT: Elem[T]): Elem[WWrappedArray[T]] =
    new WWrappedArrayElem[T, WWrappedArray[T]] {
      lazy val eTo = element[WWrappedArrayImpl[T]]
    }

  implicit case object WWrappedArrayCompanionElem extends CompanionElem[WWrappedArrayCompanionAbs] {
    lazy val tag = weakTypeTag[WWrappedArrayCompanionAbs]
    protected def getDefaultRep = WWrappedArray
  }

  abstract class WWrappedArrayCompanionAbs extends CompanionBase[WWrappedArrayCompanionAbs] with WWrappedArrayCompanion {
    override def toString = "WWrappedArray"
  }
  def WWrappedArray: Rep[WWrappedArrayCompanionAbs]
  implicit def proxyWWrappedArrayCompanion(p: Rep[WWrappedArrayCompanion]): WWrappedArrayCompanion = {
    proxyOps[WWrappedArrayCompanion](p)
  }

  // default wrapper implementation
  abstract class WWrappedArrayImpl[T](val wrappedValueOfBaseType: Rep[WrappedArray[T]])(implicit val eeT: Elem[T]) extends WWrappedArray[T] {
  }
  trait WWrappedArrayImplCompanion
  // elem for concrete class
  class WWrappedArrayImplElem[T](val iso: Iso[WWrappedArrayImplData[T], WWrappedArrayImpl[T]])(implicit eeT: Elem[T])
    extends WWrappedArrayElem[T, WWrappedArrayImpl[T]]
    with ConcreteElem[WWrappedArrayImplData[T], WWrappedArrayImpl[T]] {
    lazy val eTo = this
    override def convertWWrappedArray(x: Rep[WWrappedArray[T]]) = WWrappedArrayImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WWrappedArrayImpl[T]]
    }
  }

  // state representation type
  type WWrappedArrayImplData[T] = WrappedArray[T]

  // 3) Iso for concrete class
  class WWrappedArrayImplIso[T](implicit eeT: Elem[T])
    extends Iso[WWrappedArrayImplData[T], WWrappedArrayImpl[T]] {
    override def from(p: Rep[WWrappedArrayImpl[T]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[WrappedArray[T]]) = {
      val wrappedValueOfBaseType = p
      WWrappedArrayImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WWrappedArrayImpl[T]] = WWrappedArrayImpl(DefaultOfWrappedArray[T].value)
    lazy val eTo = new WWrappedArrayImplElem[T](this)
  }
  // 4) constructor and deconstructor
  abstract class WWrappedArrayImplCompanionAbs extends CompanionBase[WWrappedArrayImplCompanionAbs] with WWrappedArrayImplCompanion {
    override def toString = "WWrappedArrayImpl"

    def apply[T](wrappedValueOfBaseType: Rep[WrappedArray[T]])(implicit eeT: Elem[T]): Rep[WWrappedArrayImpl[T]] =
      mkWWrappedArrayImpl(wrappedValueOfBaseType)
  }
  object WWrappedArrayImplMatcher {
    def unapply[T](p: Rep[WWrappedArray[T]]) = unmkWWrappedArrayImpl(p)
  }
  def WWrappedArrayImpl: Rep[WWrappedArrayImplCompanionAbs]
  implicit def proxyWWrappedArrayImplCompanion(p: Rep[WWrappedArrayImplCompanionAbs]): WWrappedArrayImplCompanionAbs = {
    proxyOps[WWrappedArrayImplCompanionAbs](p)
  }

  implicit case object WWrappedArrayImplCompanionElem extends CompanionElem[WWrappedArrayImplCompanionAbs] {
    lazy val tag = weakTypeTag[WWrappedArrayImplCompanionAbs]
    protected def getDefaultRep = WWrappedArrayImpl
  }

  implicit def proxyWWrappedArrayImpl[T](p: Rep[WWrappedArrayImpl[T]]): WWrappedArrayImpl[T] =
    proxyOps[WWrappedArrayImpl[T]](p)

  implicit class ExtendedWWrappedArrayImpl[T](p: Rep[WWrappedArrayImpl[T]])(implicit eeT: Elem[T]) {
    def toData: Rep[WWrappedArrayImplData[T]] = isoWWrappedArrayImpl(eeT).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWWrappedArrayImpl[T](implicit eeT: Elem[T]): Iso[WWrappedArrayImplData[T], WWrappedArrayImpl[T]] =
    new WWrappedArrayImplIso[T]

  // 6) smart constructor and deconstructor
  def mkWWrappedArrayImpl[T](wrappedValueOfBaseType: Rep[WrappedArray[T]])(implicit eeT: Elem[T]): Rep[WWrappedArrayImpl[T]]
  def unmkWWrappedArrayImpl[T](p: Rep[WWrappedArray[T]]): Option[(Rep[WrappedArray[T]])]
}

// Exp -----------------------------------
trait WWrappedArraysExp extends WWrappedArraysDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WWrappedArray: Rep[WWrappedArrayCompanionAbs] = new WWrappedArrayCompanionAbs with UserTypeDef[WWrappedArrayCompanionAbs] {
    lazy val selfType = element[WWrappedArrayCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  implicit def wrappedArrayElement[T:Elem]: Elem[WrappedArray[T]] = {
     implicit val wT = element[T].tag;
     new ExpBaseElemEx[WrappedArray[T], WWrappedArray[T]](element[WWrappedArray[T]])(weakTypeTag[WrappedArray[T]], DefaultOfWrappedArray[T])
  }

  case class ExpWWrappedArrayImpl[T]
      (override val wrappedValueOfBaseType: Rep[WrappedArray[T]])
      (implicit eeT: Elem[T])
    extends WWrappedArrayImpl[T](wrappedValueOfBaseType) with UserTypeDef[WWrappedArrayImpl[T]] {
    lazy val selfType = element[WWrappedArrayImpl[T]]
    override def mirror(t: Transformer) = ExpWWrappedArrayImpl[T](t(wrappedValueOfBaseType))
  }

  lazy val WWrappedArrayImpl: Rep[WWrappedArrayImplCompanionAbs] = new WWrappedArrayImplCompanionAbs with UserTypeDef[WWrappedArrayImplCompanionAbs] {
    lazy val selfType = element[WWrappedArrayImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WWrappedArrayImplMethods {
  }

  def mkWWrappedArrayImpl[T]
    (wrappedValueOfBaseType: Rep[WrappedArray[T]])(implicit eeT: Elem[T]): Rep[WWrappedArrayImpl[T]] =
    new ExpWWrappedArrayImpl[T](wrappedValueOfBaseType)
  def unmkWWrappedArrayImpl[T](p: Rep[WWrappedArray[T]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WWrappedArrayImplElem[T] @unchecked =>
      Some((p.asRep[WWrappedArrayImpl[T]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WWrappedArrayMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WWrappedArray[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WWrappedArrayElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WWrappedArray[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WWrappedArray[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WWrappedArrayCompanionMethods {
  }
}
