package wrappers

import scalan._
import impl._
import scala.collection.mutable.WrappedArray
import scala.reflect.runtime.universe._
import scala.reflect._

package impl {
// Abs -----------------------------------
trait WWrappedArraysAbs extends ScalanDsl with WWrappedArrays {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWWrappedArray[T](p: Rep[WWrappedArray[T]]): WWrappedArray[T] = {
    proxyOps[WWrappedArray[T]](p)(scala.reflect.classTag[WWrappedArray[T]])
  }

  // TypeWrapper proxy
  //implicit def proxyWrappedArray[T:Elem](p: Rep[WrappedArray[T]]): WWrappedArray[T] =
  //  proxyOps[WWrappedArray[T]](p.asRep[WWrappedArray[T]])

  implicit def unwrapValueOfWWrappedArray[T](w: Rep[WWrappedArray[T]]): Rep[WrappedArray[T]] = w.wrappedValue

  implicit def wrappedArrayElement[T:Elem]: Elem[WrappedArray[T]] =
    element[WWrappedArray[T]].asInstanceOf[WrapperElem[_, _]].baseElem.asInstanceOf[Elem[WrappedArray[T]]]

  // familyElem
  class WWrappedArrayElem[T, To <: WWrappedArray[T]](implicit _eeT: Elem[T])
    extends WrapperElem[WrappedArray[T], To] {
    def eeT = _eeT
    lazy val parent: Option[Elem[_]] = None
    lazy val typeArgs = TypeArgs("T" -> eeT)
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WWrappedArray[T]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Def[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WWrappedArray[T]] => convertWWrappedArray(x) }
      tryConvert(element[WWrappedArray[T]], this, x, conv)
    }

    def convertWWrappedArray(x: Rep[WWrappedArray[T]]): Rep[To] = {
      x.selfType1 match {
        case _: WWrappedArrayElem[_, _] => x.asRep[To]
        case e => !!!(s"Expected $x to have WWrappedArrayElem[_, _], but got $e", x)
      }
    }
    lazy val baseElem = {
            implicit val wT = element[T].tag
      new BaseTypeElem[WrappedArray[T], WWrappedArray[T]](this.asInstanceOf[Elem[WWrappedArray[T]]])
    }
    lazy val eTo: Elem[_] = new WWrappedArrayImplElem[T](isoWWrappedArrayImpl(eeT))(eeT)
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wWrappedArrayElement[T](implicit eeT: Elem[T]): Elem[WWrappedArray[T]] =
    elemCache.getOrElseUpdate(
      (classOf[WWrappedArrayElem[T, WWrappedArray[T]]], Seq(eeT)),
      new WWrappedArrayElem[T, WWrappedArray[T]]).asInstanceOf[Elem[WWrappedArray[T]]]

  implicit case object WWrappedArrayCompanionElem extends CompanionElem[WWrappedArrayCompanionAbs] {
    lazy val tag = weakTypeTag[WWrappedArrayCompanionAbs]
    protected def getDefaultRep = WWrappedArray
  }

  abstract class WWrappedArrayCompanionAbs extends CompanionDef[WWrappedArrayCompanionAbs] with WWrappedArrayCompanion {
    def selfType = WWrappedArrayCompanionElem
    override def toString = "WWrappedArray"
  }
  def WWrappedArray: Rep[WWrappedArrayCompanionAbs]
  implicit def proxyWWrappedArrayCompanionAbs(p: Rep[WWrappedArrayCompanionAbs]): WWrappedArrayCompanionAbs =
    proxyOps[WWrappedArrayCompanionAbs](p)

  // default wrapper implementation
  abstract class WWrappedArrayImpl[T](val wrappedValue: Rep[WrappedArray[T]])(implicit val eeT: Elem[T]) extends WWrappedArray[T] with Def[WWrappedArrayImpl[T]] {
    lazy val selfType = element[WWrappedArrayImpl[T]]
  }
  trait WWrappedArrayImplCompanion
  // elem for concrete class
  class WWrappedArrayImplElem[T](val iso: Iso[WWrappedArrayImplData[T], WWrappedArrayImpl[T]])(implicit override val eeT: Elem[T])
    extends WWrappedArrayElem[T, WWrappedArrayImpl[T]]
    with ConcreteElem[WWrappedArrayImplData[T], WWrappedArrayImpl[T]] {
    override lazy val parent: Option[Elem[_]] = Some(wWrappedArrayElement(element[T]))
    override lazy val typeArgs = TypeArgs("T" -> eeT)
    override lazy val eTo: Elem[_] = this
    override def convertWWrappedArray(x: Rep[WWrappedArray[T]]) = WWrappedArrayImpl(x.wrappedValue)
    override def getDefaultRep = WWrappedArrayImpl(DefaultOfWrappedArray[T])
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WWrappedArrayImpl[T]]
    }
  }

  // state representation type
  type WWrappedArrayImplData[T] = WrappedArray[T]

  // 3) Iso for concrete class
  class WWrappedArrayImplIso[T](implicit eeT: Elem[T])
    extends EntityIso[WWrappedArrayImplData[T], WWrappedArrayImpl[T]] with Def[WWrappedArrayImplIso[T]] {
    override def from(p: Rep[WWrappedArrayImpl[T]]) =
      p.wrappedValue
    override def to(p: Rep[WrappedArray[T]]) = {
      val wrappedValue = p
      WWrappedArrayImpl(wrappedValue)
    }
    lazy val eFrom = element[WrappedArray[T]]
    lazy val eTo = new WWrappedArrayImplElem[T](self)
    lazy val selfType = new WWrappedArrayImplIsoElem[T](eeT)
    def productArity = 1
    def productElement(n: Int) = eeT
  }
  case class WWrappedArrayImplIsoElem[T](eeT: Elem[T]) extends Elem[WWrappedArrayImplIso[T]] {
    def isEntityType = true
    def getDefaultRep = reifyObject(new WWrappedArrayImplIso[T]()(eeT))
    lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WWrappedArrayImplIso[T]]
    }
    lazy val typeArgs = TypeArgs("T" -> eeT)
  }
  // 4) constructor and deconstructor
  class WWrappedArrayImplCompanionAbs extends CompanionDef[WWrappedArrayImplCompanionAbs] {
    def selfType = WWrappedArrayImplCompanionElem
    override def toString = "WWrappedArrayImpl"

    @scalan.OverloadId("fromFields")
    def apply[T](wrappedValue: Rep[WrappedArray[T]])(implicit eeT: Elem[T]): Rep[WWrappedArrayImpl[T]] =
      mkWWrappedArrayImpl(wrappedValue)

    def unapply[T](p: Rep[WWrappedArray[T]]) = unmkWWrappedArrayImpl(p)
  }
  lazy val WWrappedArrayImplRep: Rep[WWrappedArrayImplCompanionAbs] = new WWrappedArrayImplCompanionAbs
  lazy val WWrappedArrayImpl: WWrappedArrayImplCompanionAbs = proxyWWrappedArrayImplCompanion(WWrappedArrayImplRep)
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
    reifyObject(new WWrappedArrayImplIso[T]()(eeT))

  // 6) smart constructor and deconstructor
  def mkWWrappedArrayImpl[T](wrappedValue: Rep[WrappedArray[T]])(implicit eeT: Elem[T]): Rep[WWrappedArrayImpl[T]]
  def unmkWWrappedArrayImpl[T](p: Rep[WWrappedArray[T]]): Option[(Rep[WrappedArray[T]])]

  registerModule(WWrappedArrays_Module)
}

// Exp -----------------------------------
trait WWrappedArraysExp extends ScalanExp with WWrappedArraysDsl {
  self: WrappersDslExp =>

  lazy val WWrappedArray: Rep[WWrappedArrayCompanionAbs] = new WWrappedArrayCompanionAbs {
  }

  case class ExpWWrappedArrayImpl[T]
      (override val wrappedValue: Rep[WrappedArray[T]])(implicit eeT: Elem[T])
    extends WWrappedArrayImpl[T](wrappedValue)

  object WWrappedArrayImplMethods {
  }

  def mkWWrappedArrayImpl[T]
    (wrappedValue: Rep[WrappedArray[T]])(implicit eeT: Elem[T]): Rep[WWrappedArrayImpl[T]] =
    new ExpWWrappedArrayImpl[T](wrappedValue)
  def unmkWWrappedArrayImpl[T](p: Rep[WWrappedArray[T]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WWrappedArrayImplElem[T] @unchecked =>
      Some((p.asRep[WWrappedArrayImpl[T]].wrappedValue))
    case _ =>
      None
  }

  object WWrappedArrayMethods {
    object wrappedValue {
      def unapply(d: Def[_]): Option[Rep[WWrappedArray[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WWrappedArrayElem[_, _]] && method.getName == "wrappedValue" =>
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

object WWrappedArrays_Module extends scalan.ModuleInfo {
  val dump = "H4sIAAAAAAAAALVVz28bRRR+6/ywHZuQFNIWiSohuCAhare9FCmnkLilyE2ibkSRWwWNd8fultnZYec5XffQYyXaW4WEhMShiGMFQty4IyEO/AOcOZVWVQ9UQgIxM/vLdmtKD/gwmp19fu+b7/ve27v3YUaG8IZ0CCO87lMkddvs1yXW7CZHDwfnArfP6Cbt7r331Z8X/U8PFWChDbOXidyUrA3leNOMRLa30W1BmXCHSgxCifBay1RoOAFj1EEv4A3P9/tIOow2Wp7EtRZMdwJ38AlcB6sFC07AnZAitTcYkZLK5LxENSIvey6b58G2yGvwhr5FY+gWuyHxUMFXNRbi+PNU2AMe8IGPMJ9A2xYaloqp0EioO5z1BTNlplpQ9HwRhJhWLaoKlwM3fZzmRB3AgdYVsk8aqmqvYWPo8Z5OJojzMenRLRWiw6fVHSRl3d2BoEnyikR3pF4kAECpctIAq+ec1TPO6pqzmk1DjzDvGtEvd8IgGkD8s6YAIqFSvP2MFGkG2uRu7eYl5+Jju+IX9J8jDaVoAM2qRMsTHGLkUdz+dP62fHTmzqkCzLVhzpPrHYkhcXDYBgldFcJ5gAZzxiAJe0rB1UkKmirrKmbMJmUn8AXhKlPCZVUJxTzHQx2sz6qJPBO4L6KgaagVCSu778qE+xovbRDGdu69cuzo780PC1AYLVFWKW3VDGGaFOGFCxdCIgR118OQDJIaen0Rwdo1ROulHOVr8V8wZGy8ee+B++NxuFTIOExK/jfZVIoD73zxw1G6800BSm3j8tOM9IyAmqRNKp02lIJ9GsbnxX3C9O6pIhZd2iV9hgm1w5xMKU4QViY2qKCasDVjfCu9fiX27lbAae30Tu0P++fP7mprhlCN38Qd+7d36q9f57toXItQvRpT/QFhfZqSXH1+/vVyxAQtDf3hsGUlAM17hClKd9NU001G/eHsenn5ifQIiyN+0I2fV9OqHZlkPmNWfsg+9/m3y3sFmHkfZrpKDtmCmU7Q527aBWp6Io3w3fTMGpVDuZ6ExE/dEs+MFTAgMrhPAn+mq9JB+92NG0sPv/7oJTMMSh0PfSJqx59jFKSd+z+2OozaoKIjY1VidLN6Wc51XBwX9vWn+qY8NDeXsght57nYtHbg08XVR97enVto+taKRj8d250ralSvmTyvmjwnxrBWm9FGSsaJcViLE8bJGCCjtWqXgyNW3BjmOLaCyC+/pm6xOkF6OyFbqX/98Zdbb/3y/W9mMs5p2VQH8+zjmWsUmdxF1ThaKr0/liM+mUHILalC50dHKULpaiJaAlGvZ8a0TXVVX9kxds6aAv8As8nr6QYJAAA="
}
}

trait WWrappedArraysDsl extends impl.WWrappedArraysAbs {self: WrappersDsl =>}
trait WWrappedArraysDslStd extends impl.WWrappedArraysStd {self: WrappersDslStd =>}
trait WWrappedArraysDslExp extends impl.WWrappedArraysExp {self: WrappersDslExp =>}
