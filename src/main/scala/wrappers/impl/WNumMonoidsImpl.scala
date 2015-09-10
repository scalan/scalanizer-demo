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
  implicit def proxyWNumMonoid[A](p: Rep[WNumMonoid[A]]): WNumMonoid[A] = {
    proxyOps[WNumMonoid[A]](p)(scala.reflect.classTag[WNumMonoid[A]])
  }

  // TypeWrapper proxy
  //implicit def proxyNumMonoid[A:Elem](p: Rep[NumMonoid[A]]): WNumMonoid[A] =
  //  proxyOps[WNumMonoid[A]](p.asRep[WNumMonoid[A]])

  implicit def unwrapValueOfWNumMonoid[A](w: Rep[WNumMonoid[A]]): Rep[NumMonoid[A]] = w.wrappedValueOfBaseType

  implicit def numMonoidElement[A:Elem]: Elem[NumMonoid[A]]

  // familyElem
  abstract class WNumMonoidElem[A, To <: WNumMonoid[A]](implicit val eeA: Elem[A])
    extends WrapperElem[NumMonoid[A], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagA = eeA.tag
      weakTypeTag[WNumMonoid[A]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WNumMonoid[A]] => convertWNumMonoid(x) }
      tryConvert(element[WNumMonoid[A]], this, x, conv)
    }

    def convertWNumMonoid(x : Rep[WNumMonoid[A]]): Rep[To] = {
      assert(x.selfType1 match { case _: WNumMonoidElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wNumMonoidElement[A](implicit eeA: Elem[A]): Elem[WNumMonoid[A]] =
    new WNumMonoidElem[A, WNumMonoid[A]] {
      lazy val eTo = element[WNumMonoidImpl[A]]
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
  abstract class WNumMonoidImpl[A](val wrappedValueOfBaseType: Rep[NumMonoid[A]])(implicit val eeA: Elem[A]) extends WNumMonoid[A] {
    def opName: Rep[String] =
      methodCallEx[String](self,
        this.getClass.getMethod("opName"),
        List())
  }
  trait WNumMonoidImplCompanion
  // elem for concrete class
  class WNumMonoidImplElem[A](val iso: Iso[WNumMonoidImplData[A], WNumMonoidImpl[A]])(implicit eeA: Elem[A])
    extends WNumMonoidElem[A, WNumMonoidImpl[A]]
    with ConcreteElem[WNumMonoidImplData[A], WNumMonoidImpl[A]] {
    lazy val eTo = this
    override def convertWNumMonoid(x: Rep[WNumMonoid[A]]) = WNumMonoidImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagA = eeA.tag
      weakTypeTag[WNumMonoidImpl[A]]
    }
  }

  // state representation type
  type WNumMonoidImplData[A] = NumMonoid[A]

  // 3) Iso for concrete class
  class WNumMonoidImplIso[A](implicit eeA: Elem[A])
    extends Iso[WNumMonoidImplData[A], WNumMonoidImpl[A]] {
    override def from(p: Rep[WNumMonoidImpl[A]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[NumMonoid[A]]) = {
      val wrappedValueOfBaseType = p
      WNumMonoidImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WNumMonoidImpl[A]] = WNumMonoidImpl(DefaultOfNumMonoid[A].value)
    lazy val eTo = new WNumMonoidImplElem[A](this)
  }
  // 4) constructor and deconstructor
  abstract class WNumMonoidImplCompanionAbs extends CompanionBase[WNumMonoidImplCompanionAbs] with WNumMonoidImplCompanion {
    override def toString = "WNumMonoidImpl"

    def apply[A](wrappedValueOfBaseType: Rep[NumMonoid[A]])(implicit eeA: Elem[A]): Rep[WNumMonoidImpl[A]] =
      mkWNumMonoidImpl(wrappedValueOfBaseType)
  }
  object WNumMonoidImplMatcher {
    def unapply[A](p: Rep[WNumMonoid[A]]) = unmkWNumMonoidImpl(p)
  }
  def WNumMonoidImpl: Rep[WNumMonoidImplCompanionAbs]
  implicit def proxyWNumMonoidImplCompanion(p: Rep[WNumMonoidImplCompanionAbs]): WNumMonoidImplCompanionAbs = {
    proxyOps[WNumMonoidImplCompanionAbs](p)
  }

  implicit case object WNumMonoidImplCompanionElem extends CompanionElem[WNumMonoidImplCompanionAbs] {
    lazy val tag = weakTypeTag[WNumMonoidImplCompanionAbs]
    protected def getDefaultRep = WNumMonoidImpl
  }

  implicit def proxyWNumMonoidImpl[A](p: Rep[WNumMonoidImpl[A]]): WNumMonoidImpl[A] =
    proxyOps[WNumMonoidImpl[A]](p)

  implicit class ExtendedWNumMonoidImpl[A](p: Rep[WNumMonoidImpl[A]])(implicit eeA: Elem[A]) {
    def toData: Rep[WNumMonoidImplData[A]] = isoWNumMonoidImpl(eeA).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWNumMonoidImpl[A](implicit eeA: Elem[A]): Iso[WNumMonoidImplData[A], WNumMonoidImpl[A]] =
    new WNumMonoidImplIso[A]

  // 6) smart constructor and deconstructor
  def mkWNumMonoidImpl[A](wrappedValueOfBaseType: Rep[NumMonoid[A]])(implicit eeA: Elem[A]): Rep[WNumMonoidImpl[A]]
  def unmkWNumMonoidImpl[A](p: Rep[WNumMonoid[A]]): Option[(Rep[NumMonoid[A]])]
}

// Seq -----------------------------------
trait WNumMonoidsSeq extends WNumMonoidsDsl with ScalanSeq {
  self: WrappersDslSeq =>
  lazy val WNumMonoid: Rep[WNumMonoidCompanionAbs] = new WNumMonoidCompanionAbs with UserTypeSeq[WNumMonoidCompanionAbs] {
    lazy val selfType = element[WNumMonoidCompanionAbs]
  }

    // override proxy if we deal with TypeWrapper
  //override def proxyNumMonoid[A:Elem](p: Rep[NumMonoid[A]]): WNumMonoid[A] =
  //  proxyOpsEx[NumMonoid[A],WNumMonoid[A], SeqWNumMonoidImpl[A]](p, bt => SeqWNumMonoidImpl(bt))

    implicit def numMonoidElement[A:Elem]: Elem[NumMonoid[A]] = {
     implicit val wA = element[A].tag;
     new SeqBaseElemEx[NumMonoid[A], WNumMonoid[A]](element[WNumMonoid[A]])(weakTypeTag[NumMonoid[A]], DefaultOfNumMonoid[A])
  }

  case class SeqWNumMonoidImpl[A]
      (override val wrappedValueOfBaseType: Rep[NumMonoid[A]])
      (implicit eeA: Elem[A])
    extends WNumMonoidImpl[A](wrappedValueOfBaseType)
        with UserTypeSeq[WNumMonoidImpl[A]] {
    lazy val selfType = element[WNumMonoidImpl[A]]
    override def opName: Rep[String] =
      wrappedValueOfBaseType.opName
  }
  lazy val WNumMonoidImpl = new WNumMonoidImplCompanionAbs with UserTypeSeq[WNumMonoidImplCompanionAbs] {
    lazy val selfType = element[WNumMonoidImplCompanionAbs]
  }

  def mkWNumMonoidImpl[A]
      (wrappedValueOfBaseType: Rep[NumMonoid[A]])(implicit eeA: Elem[A]): Rep[WNumMonoidImpl[A]] =
      new SeqWNumMonoidImpl[A](wrappedValueOfBaseType)
  def unmkWNumMonoidImpl[A](p: Rep[WNumMonoid[A]]) = p match {
    case p: WNumMonoidImpl[A] @unchecked =>
      Some((p.wrappedValueOfBaseType))
    case _ => None
  }

  implicit def wrapNumMonoidToWNumMonoid[A:Elem](v: NumMonoid[A]): WNumMonoid[A] = WNumMonoidImpl(v)
}

// Exp -----------------------------------
trait WNumMonoidsExp extends WNumMonoidsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WNumMonoid: Rep[WNumMonoidCompanionAbs] = new WNumMonoidCompanionAbs with UserTypeDef[WNumMonoidCompanionAbs] {
    lazy val selfType = element[WNumMonoidCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  implicit def numMonoidElement[A:Elem]: Elem[NumMonoid[A]] = {
     implicit val wA = element[A].tag;
     new ExpBaseElemEx[NumMonoid[A], WNumMonoid[A]](element[WNumMonoid[A]])(weakTypeTag[NumMonoid[A]], DefaultOfNumMonoid[A])
  }

  case class ExpWNumMonoidImpl[A]
      (override val wrappedValueOfBaseType: Rep[NumMonoid[A]])
      (implicit eeA: Elem[A])
    extends WNumMonoidImpl[A](wrappedValueOfBaseType) with UserTypeDef[WNumMonoidImpl[A]] {
    lazy val selfType = element[WNumMonoidImpl[A]]
    override def mirror(t: Transformer) = ExpWNumMonoidImpl[A](t(wrappedValueOfBaseType))
  }

  lazy val WNumMonoidImpl: Rep[WNumMonoidImplCompanionAbs] = new WNumMonoidImplCompanionAbs with UserTypeDef[WNumMonoidImplCompanionAbs] {
    lazy val selfType = element[WNumMonoidImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WNumMonoidImplMethods {
  }

  def mkWNumMonoidImpl[A]
    (wrappedValueOfBaseType: Rep[NumMonoid[A]])(implicit eeA: Elem[A]): Rep[WNumMonoidImpl[A]] =
    new ExpWNumMonoidImpl[A](wrappedValueOfBaseType)
  def unmkWNumMonoidImpl[A](p: Rep[WNumMonoid[A]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WNumMonoidImplElem[A] @unchecked =>
      Some((p.asRep[WNumMonoidImpl[A]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WNumMonoidMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WNumMonoid[A]] forSome {type A}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WNumMonoidElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WNumMonoid[A]] forSome {type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WNumMonoid[A]] forSome {type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object opName {
      def unapply(d: Def[_]): Option[Rep[WNumMonoid[A]] forSome {type A}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WNumMonoidElem[_, _]] && method.getName == "opName" =>
          Some(receiver).asInstanceOf[Option[Rep[WNumMonoid[A]] forSome {type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WNumMonoid[A]] forSome {type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WNumMonoidCompanionMethods {
  }
}
