package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scala.collection.GenIterable
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WGenIterablesAbs extends WGenIterables with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWGenIterable[A](p: Rep[WGenIterable[A]]): WGenIterable[A] = {
    proxyOps[WGenIterable[A]](p)(scala.reflect.classTag[WGenIterable[A]])
  }

  // TypeWrapper proxy
  //implicit def proxyGenIterable[A:Elem](p: Rep[GenIterable[A]]): WGenIterable[A] =
  //  proxyOps[WGenIterable[A]](p.asRep[WGenIterable[A]])

  implicit def unwrapValueOfWGenIterable[A](w: Rep[WGenIterable[A]]): Rep[GenIterable[A]] = w.wrappedValueOfBaseType

  implicit def genIterableElement[A:Elem]: Elem[GenIterable[A]]

  // familyElem
  abstract class WGenIterableElem[A, To <: WGenIterable[A]](implicit val eeA: Elem[A])
    extends WrapperElem[GenIterable[A], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagA = eeA.tag
      weakTypeTag[WGenIterable[A]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WGenIterable[A]] => convertWGenIterable(x) }
      tryConvert(element[WGenIterable[A]], this, x, conv)
    }

    def convertWGenIterable(x : Rep[WGenIterable[A]]): Rep[To] = {
      assert(x.selfType1 match { case _: WGenIterableElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wGenIterableElement[A](implicit eeA: Elem[A]): Elem[WGenIterable[A]] =
    new WGenIterableElem[A, WGenIterable[A]] {
      lazy val eTo = element[WGenIterableImpl[A]]
    }

  implicit case object WGenIterableCompanionElem extends CompanionElem[WGenIterableCompanionAbs] {
    lazy val tag = weakTypeTag[WGenIterableCompanionAbs]
    protected def getDefaultRep = WGenIterable
  }

  abstract class WGenIterableCompanionAbs extends CompanionBase[WGenIterableCompanionAbs] with WGenIterableCompanion {
    override def toString = "WGenIterable"
  }
  def WGenIterable: Rep[WGenIterableCompanionAbs]
  implicit def proxyWGenIterableCompanion(p: Rep[WGenIterableCompanion]): WGenIterableCompanion = {
    proxyOps[WGenIterableCompanion](p)
  }

  // default wrapper implementation
  abstract class WGenIterableImpl[A](val wrappedValueOfBaseType: Rep[GenIterable[A]])(implicit val eeA: Elem[A]) extends WGenIterable[A] {
  }
  trait WGenIterableImplCompanion
  // elem for concrete class
  class WGenIterableImplElem[A](val iso: Iso[WGenIterableImplData[A], WGenIterableImpl[A]])(implicit eeA: Elem[A])
    extends WGenIterableElem[A, WGenIterableImpl[A]]
    with ConcreteElem[WGenIterableImplData[A], WGenIterableImpl[A]] {
    lazy val eTo = this
    override def convertWGenIterable(x: Rep[WGenIterable[A]]) = WGenIterableImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagA = eeA.tag
      weakTypeTag[WGenIterableImpl[A]]
    }
  }

  // state representation type
  type WGenIterableImplData[A] = GenIterable[A]

  // 3) Iso for concrete class
  class WGenIterableImplIso[A](implicit eeA: Elem[A])
    extends Iso[WGenIterableImplData[A], WGenIterableImpl[A]] {
    override def from(p: Rep[WGenIterableImpl[A]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[GenIterable[A]]) = {
      val wrappedValueOfBaseType = p
      WGenIterableImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WGenIterableImpl[A]] = WGenIterableImpl(DefaultOfGenIterable[A].value)
    lazy val eTo = new WGenIterableImplElem[A](this)
  }
  // 4) constructor and deconstructor
  abstract class WGenIterableImplCompanionAbs extends CompanionBase[WGenIterableImplCompanionAbs] with WGenIterableImplCompanion {
    override def toString = "WGenIterableImpl"

    def apply[A](wrappedValueOfBaseType: Rep[GenIterable[A]])(implicit eeA: Elem[A]): Rep[WGenIterableImpl[A]] =
      mkWGenIterableImpl(wrappedValueOfBaseType)
  }
  object WGenIterableImplMatcher {
    def unapply[A](p: Rep[WGenIterable[A]]) = unmkWGenIterableImpl(p)
  }
  def WGenIterableImpl: Rep[WGenIterableImplCompanionAbs]
  implicit def proxyWGenIterableImplCompanion(p: Rep[WGenIterableImplCompanionAbs]): WGenIterableImplCompanionAbs = {
    proxyOps[WGenIterableImplCompanionAbs](p)
  }

  implicit case object WGenIterableImplCompanionElem extends CompanionElem[WGenIterableImplCompanionAbs] {
    lazy val tag = weakTypeTag[WGenIterableImplCompanionAbs]
    protected def getDefaultRep = WGenIterableImpl
  }

  implicit def proxyWGenIterableImpl[A](p: Rep[WGenIterableImpl[A]]): WGenIterableImpl[A] =
    proxyOps[WGenIterableImpl[A]](p)

  implicit class ExtendedWGenIterableImpl[A](p: Rep[WGenIterableImpl[A]])(implicit eeA: Elem[A]) {
    def toData: Rep[WGenIterableImplData[A]] = isoWGenIterableImpl(eeA).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWGenIterableImpl[A](implicit eeA: Elem[A]): Iso[WGenIterableImplData[A], WGenIterableImpl[A]] =
    new WGenIterableImplIso[A]

  // 6) smart constructor and deconstructor
  def mkWGenIterableImpl[A](wrappedValueOfBaseType: Rep[GenIterable[A]])(implicit eeA: Elem[A]): Rep[WGenIterableImpl[A]]
  def unmkWGenIterableImpl[A](p: Rep[WGenIterable[A]]): Option[(Rep[GenIterable[A]])]
}

// Exp -----------------------------------
trait WGenIterablesExp extends WGenIterablesDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WGenIterable: Rep[WGenIterableCompanionAbs] = new WGenIterableCompanionAbs with UserTypeDef[WGenIterableCompanionAbs] {
    lazy val selfType = element[WGenIterableCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  implicit def genIterableElement[A:Elem]: Elem[GenIterable[A]] = {
     implicit val wA = element[A].tag;
     new ExpBaseElemEx[GenIterable[A], WGenIterable[A]](element[WGenIterable[A]])(weakTypeTag[GenIterable[A]], DefaultOfGenIterable[A])
  }

  case class ExpWGenIterableImpl[A]
      (override val wrappedValueOfBaseType: Rep[GenIterable[A]])
      (implicit eeA: Elem[A])
    extends WGenIterableImpl[A](wrappedValueOfBaseType) with UserTypeDef[WGenIterableImpl[A]] {
    lazy val selfType = element[WGenIterableImpl[A]]
    override def mirror(t: Transformer) = ExpWGenIterableImpl[A](t(wrappedValueOfBaseType))
  }

  lazy val WGenIterableImpl: Rep[WGenIterableImplCompanionAbs] = new WGenIterableImplCompanionAbs with UserTypeDef[WGenIterableImplCompanionAbs] {
    lazy val selfType = element[WGenIterableImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WGenIterableImplMethods {
  }

  def mkWGenIterableImpl[A]
    (wrappedValueOfBaseType: Rep[GenIterable[A]])(implicit eeA: Elem[A]): Rep[WGenIterableImpl[A]] =
    new ExpWGenIterableImpl[A](wrappedValueOfBaseType)
  def unmkWGenIterableImpl[A](p: Rep[WGenIterable[A]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WGenIterableImplElem[A] @unchecked =>
      Some((p.asRep[WGenIterableImpl[A]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WGenIterableMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WGenIterable[A]] forSome {type A}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WGenIterableElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WGenIterable[A]] forSome {type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WGenIterable[A]] forSome {type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WGenIterableCompanionMethods {
  }
}
