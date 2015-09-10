package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scalanizer.NumMonoid
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WNumMonoidsAbs extends WNumMonoids with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWNumMonoid[T](p: Rep[WNumMonoid[T]]): WNumMonoid[T] = {
    proxyOps[WNumMonoid[T]](p)(scala.reflect.classTag[WNumMonoid[T]])
  }

  // TypeWrapper proxy
  //implicit def proxyNumMonoid[T:Elem](p: Rep[NumMonoid[T]]): WNumMonoid[T] =
  //  proxyOps[WNumMonoid[T]](p.asRep[WNumMonoid[T]])

  implicit def unwrapValueOfWNumMonoid[T](w: Rep[WNumMonoid[T]]): Rep[NumMonoid[T]] = w.wrappedValueOfBaseType

  implicit def numMonoidElement[T:Elem]: Elem[NumMonoid[T]]

  // familyElem
  abstract class WNumMonoidElem[T, To <: WNumMonoid[T]](implicit val eeT: Elem[T])
    extends WrapperElem[NumMonoid[T], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WNumMonoid[T]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WNumMonoid[T]] => convertWNumMonoid(x) }
      tryConvert(element[WNumMonoid[T]], this, x, conv)
    }

    def convertWNumMonoid(x : Rep[WNumMonoid[T]]): Rep[To] = {
      assert(x.selfType1 match { case _: WNumMonoidElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wNumMonoidElement[T](implicit eeT: Elem[T]): Elem[WNumMonoid[T]] =
    new WNumMonoidElem[T, WNumMonoid[T]] {
      lazy val eTo = element[WNumMonoidImpl[T]]
    }

  implicit case object WNumMonoidCompanionElem extends CompanionElem[WNumMonoidCompanionAbs] {
    lazy val tag = weakTypeTag[WNumMonoidCompanionAbs]
    protected def getDefaultRep = WNumMonoid
  }

  abstract class WNumMonoidCompanionAbs extends CompanionBase[WNumMonoidCompanionAbs] with WNumMonoidCompanion {
    override def toString = "WNumMonoid"
  }
  def WNumMonoid: Rep[WNumMonoidCompanionAbs]
  implicit def proxyWNumMonoidCompanion(p: Rep[WNumMonoidCompanion]): WNumMonoidCompanion = {
    proxyOps[WNumMonoidCompanion](p)
  }

  // default wrapper implementation
  abstract class WNumMonoidImpl[T](val wrappedValueOfBaseType: Rep[NumMonoid[T]])(implicit val eeT: Elem[T]) extends WNumMonoid[T] {
    def opName: Rep[String] =
      methodCallEx[String](self,
        this.getClass.getMethod("opName"),
        List())
  }
  trait WNumMonoidImplCompanion
  // elem for concrete class
  class WNumMonoidImplElem[T](val iso: Iso[WNumMonoidImplData[T], WNumMonoidImpl[T]])(implicit eeT: Elem[T])
    extends WNumMonoidElem[T, WNumMonoidImpl[T]]
    with ConcreteElem[WNumMonoidImplData[T], WNumMonoidImpl[T]] {
    lazy val eTo = this
    override def convertWNumMonoid(x: Rep[WNumMonoid[T]]) = WNumMonoidImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WNumMonoidImpl[T]]
    }
  }

  // state representation type
  type WNumMonoidImplData[T] = NumMonoid[T]

  // 3) Iso for concrete class
  class WNumMonoidImplIso[T](implicit eeT: Elem[T])
    extends Iso[WNumMonoidImplData[T], WNumMonoidImpl[T]] {
    override def from(p: Rep[WNumMonoidImpl[T]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[NumMonoid[T]]) = {
      val wrappedValueOfBaseType = p
      WNumMonoidImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WNumMonoidImpl[T]] = WNumMonoidImpl(DefaultOfNumMonoid[T].value)
    lazy val eTo = new WNumMonoidImplElem[T](this)
  }
  // 4) constructor and deconstructor
  abstract class WNumMonoidImplCompanionAbs extends CompanionBase[WNumMonoidImplCompanionAbs] with WNumMonoidImplCompanion {
    override def toString = "WNumMonoidImpl"

    def apply[T](wrappedValueOfBaseType: Rep[NumMonoid[T]])(implicit eeT: Elem[T]): Rep[WNumMonoidImpl[T]] =
      mkWNumMonoidImpl(wrappedValueOfBaseType)
  }
  object WNumMonoidImplMatcher {
    def unapply[T](p: Rep[WNumMonoid[T]]) = unmkWNumMonoidImpl(p)
  }
  def WNumMonoidImpl: Rep[WNumMonoidImplCompanionAbs]
  implicit def proxyWNumMonoidImplCompanion(p: Rep[WNumMonoidImplCompanionAbs]): WNumMonoidImplCompanionAbs = {
    proxyOps[WNumMonoidImplCompanionAbs](p)
  }

  implicit case object WNumMonoidImplCompanionElem extends CompanionElem[WNumMonoidImplCompanionAbs] {
    lazy val tag = weakTypeTag[WNumMonoidImplCompanionAbs]
    protected def getDefaultRep = WNumMonoidImpl
  }

  implicit def proxyWNumMonoidImpl[T](p: Rep[WNumMonoidImpl[T]]): WNumMonoidImpl[T] =
    proxyOps[WNumMonoidImpl[T]](p)

  implicit class ExtendedWNumMonoidImpl[T](p: Rep[WNumMonoidImpl[T]])(implicit eeT: Elem[T]) {
    def toData: Rep[WNumMonoidImplData[T]] = isoWNumMonoidImpl(eeT).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWNumMonoidImpl[T](implicit eeT: Elem[T]): Iso[WNumMonoidImplData[T], WNumMonoidImpl[T]] =
    new WNumMonoidImplIso[T]

  // 6) smart constructor and deconstructor
  def mkWNumMonoidImpl[T](wrappedValueOfBaseType: Rep[NumMonoid[T]])(implicit eeT: Elem[T]): Rep[WNumMonoidImpl[T]]
  def unmkWNumMonoidImpl[T](p: Rep[WNumMonoid[T]]): Option[(Rep[NumMonoid[T]])]
}

// Exp -----------------------------------
trait WNumMonoidsExp extends WNumMonoidsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WNumMonoid: Rep[WNumMonoidCompanionAbs] = new WNumMonoidCompanionAbs with UserTypeDef[WNumMonoidCompanionAbs] {
    lazy val selfType = element[WNumMonoidCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  implicit def numMonoidElement[T:Elem]: Elem[NumMonoid[T]] = {
     implicit val wT = element[T].tag;
     new ExpBaseElemEx[NumMonoid[T], WNumMonoid[T]](element[WNumMonoid[T]])(weakTypeTag[NumMonoid[T]], DefaultOfNumMonoid[T])
  }

  case class ExpWNumMonoidImpl[T]
      (override val wrappedValueOfBaseType: Rep[NumMonoid[T]])
      (implicit eeT: Elem[T])
    extends WNumMonoidImpl[T](wrappedValueOfBaseType) with UserTypeDef[WNumMonoidImpl[T]] {
    lazy val selfType = element[WNumMonoidImpl[T]]
    override def mirror(t: Transformer) = ExpWNumMonoidImpl[T](t(wrappedValueOfBaseType))
  }

  lazy val WNumMonoidImpl: Rep[WNumMonoidImplCompanionAbs] = new WNumMonoidImplCompanionAbs with UserTypeDef[WNumMonoidImplCompanionAbs] {
    lazy val selfType = element[WNumMonoidImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WNumMonoidImplMethods {
  }

  def mkWNumMonoidImpl[T]
    (wrappedValueOfBaseType: Rep[NumMonoid[T]])(implicit eeT: Elem[T]): Rep[WNumMonoidImpl[T]] =
    new ExpWNumMonoidImpl[T](wrappedValueOfBaseType)
  def unmkWNumMonoidImpl[T](p: Rep[WNumMonoid[T]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WNumMonoidImplElem[T] @unchecked =>
      Some((p.asRep[WNumMonoidImpl[T]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WNumMonoidMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WNumMonoid[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WNumMonoidElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WNumMonoid[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WNumMonoid[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object opName {
      def unapply(d: Def[_]): Option[Rep[WNumMonoid[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WNumMonoidElem[_, _]] && method.getName == "opName" =>
          Some(receiver).asInstanceOf[Option[Rep[WNumMonoid[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WNumMonoid[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WNumMonoidCompanionMethods {
  }
}
