package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scalanizer.PlusMonoid
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WPlusMonoidsAbs extends WPlusMonoids with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWPlusMonoid[A](p: Rep[WPlusMonoid[A]]): WPlusMonoid[A] = {
    proxyOps[WPlusMonoid[A]](p)(scala.reflect.classTag[WPlusMonoid[A]])
  }

  // TypeWrapper proxy
  //implicit def proxyPlusMonoid[A:Elem](p: Rep[PlusMonoid[A]]): WPlusMonoid[A] =
  //  proxyOps[WPlusMonoid[A]](p.asRep[WPlusMonoid[A]])

  implicit def unwrapValueOfWPlusMonoid[A](w: Rep[WPlusMonoid[A]]): Rep[PlusMonoid[A]] = w.wrappedValueOfBaseType

  implicit def plusMonoidElement[A:Elem]: Elem[PlusMonoid[A]]

  // familyElem
  abstract class WPlusMonoidElem[A, To <: WPlusMonoid[A]](implicit val eeA: Elem[A])
    extends WrapperElem[PlusMonoid[A], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagA = eeA.tag
      weakTypeTag[WPlusMonoid[A]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WPlusMonoid[A]] => convertWPlusMonoid(x) }
      tryConvert(element[WPlusMonoid[A]], this, x, conv)
    }

    def convertWPlusMonoid(x : Rep[WPlusMonoid[A]]): Rep[To] = {
      assert(x.selfType1 match { case _: WPlusMonoidElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wPlusMonoidElement[A](implicit eeA: Elem[A]): Elem[WPlusMonoid[A]] =
    new WPlusMonoidElem[A, WPlusMonoid[A]] {
      lazy val eTo = element[WPlusMonoidImpl[A]]
    }

  implicit case object WPlusMonoidCompanionElem extends CompanionElem[WPlusMonoidCompanionAbs] {
    lazy val tag = weakTypeTag[WPlusMonoidCompanionAbs]
    protected def getDefaultRep = WPlusMonoid
  }

  abstract class WPlusMonoidCompanionAbs extends CompanionBase[WPlusMonoidCompanionAbs] with WPlusMonoidCompanion {
    override def toString = "WPlusMonoid"
  }
  def WPlusMonoid: Rep[WPlusMonoidCompanionAbs]
  implicit def proxyWPlusMonoidCompanion(p: Rep[WPlusMonoidCompanion]): WPlusMonoidCompanion = {
    proxyOps[WPlusMonoidCompanion](p)
  }

  // default wrapper implementation
  abstract class WPlusMonoidImpl[A](val wrappedValueOfBaseType: Rep[PlusMonoid[A]])(implicit val eeA: Elem[A]) extends WPlusMonoid[A] {
  }
  trait WPlusMonoidImplCompanion
  // elem for concrete class
  class WPlusMonoidImplElem[A](val iso: Iso[WPlusMonoidImplData[A], WPlusMonoidImpl[A]])(implicit eeA: Elem[A])
    extends WPlusMonoidElem[A, WPlusMonoidImpl[A]]
    with ConcreteElem[WPlusMonoidImplData[A], WPlusMonoidImpl[A]] {
    lazy val eTo = this
    override def convertWPlusMonoid(x: Rep[WPlusMonoid[A]]) = WPlusMonoidImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagA = eeA.tag
      weakTypeTag[WPlusMonoidImpl[A]]
    }
  }

  // state representation type
  type WPlusMonoidImplData[A] = PlusMonoid[A]

  // 3) Iso for concrete class
  class WPlusMonoidImplIso[A](implicit eeA: Elem[A])
    extends Iso[WPlusMonoidImplData[A], WPlusMonoidImpl[A]] {
    override def from(p: Rep[WPlusMonoidImpl[A]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[PlusMonoid[A]]) = {
      val wrappedValueOfBaseType = p
      WPlusMonoidImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WPlusMonoidImpl[A]] = WPlusMonoidImpl(DefaultOfPlusMonoid[A].value)
    lazy val eTo = new WPlusMonoidImplElem[A](this)
  }
  // 4) constructor and deconstructor
  abstract class WPlusMonoidImplCompanionAbs extends CompanionBase[WPlusMonoidImplCompanionAbs] with WPlusMonoidImplCompanion {
    override def toString = "WPlusMonoidImpl"

    def apply[A](wrappedValueOfBaseType: Rep[PlusMonoid[A]])(implicit eeA: Elem[A]): Rep[WPlusMonoidImpl[A]] =
      mkWPlusMonoidImpl(wrappedValueOfBaseType)
  }
  object WPlusMonoidImplMatcher {
    def unapply[A](p: Rep[WPlusMonoid[A]]) = unmkWPlusMonoidImpl(p)
  }
  def WPlusMonoidImpl: Rep[WPlusMonoidImplCompanionAbs]
  implicit def proxyWPlusMonoidImplCompanion(p: Rep[WPlusMonoidImplCompanionAbs]): WPlusMonoidImplCompanionAbs = {
    proxyOps[WPlusMonoidImplCompanionAbs](p)
  }

  implicit case object WPlusMonoidImplCompanionElem extends CompanionElem[WPlusMonoidImplCompanionAbs] {
    lazy val tag = weakTypeTag[WPlusMonoidImplCompanionAbs]
    protected def getDefaultRep = WPlusMonoidImpl
  }

  implicit def proxyWPlusMonoidImpl[A](p: Rep[WPlusMonoidImpl[A]]): WPlusMonoidImpl[A] =
    proxyOps[WPlusMonoidImpl[A]](p)

  implicit class ExtendedWPlusMonoidImpl[A](p: Rep[WPlusMonoidImpl[A]])(implicit eeA: Elem[A]) {
    def toData: Rep[WPlusMonoidImplData[A]] = isoWPlusMonoidImpl(eeA).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWPlusMonoidImpl[A](implicit eeA: Elem[A]): Iso[WPlusMonoidImplData[A], WPlusMonoidImpl[A]] =
    new WPlusMonoidImplIso[A]

  // 6) smart constructor and deconstructor
  def mkWPlusMonoidImpl[A](wrappedValueOfBaseType: Rep[PlusMonoid[A]])(implicit eeA: Elem[A]): Rep[WPlusMonoidImpl[A]]
  def unmkWPlusMonoidImpl[A](p: Rep[WPlusMonoid[A]]): Option[(Rep[PlusMonoid[A]])]
}

// Seq -----------------------------------
trait WPlusMonoidsSeq extends WPlusMonoidsDsl with ScalanSeq {
  self: WrappersDslSeq =>
  lazy val WPlusMonoid: Rep[WPlusMonoidCompanionAbs] = new WPlusMonoidCompanionAbs with UserTypeSeq[WPlusMonoidCompanionAbs] {
    lazy val selfType = element[WPlusMonoidCompanionAbs]

    override def apply[A]( n: Rep[WNum[A]])(implicit emA: Elem[A]): Rep[WPlusMonoid[A]] =
      WPlusMonoidImpl(new PlusMonoid[A](n))
  }

    // override proxy if we deal with TypeWrapper
  //override def proxyPlusMonoid[A:Elem](p: Rep[PlusMonoid[A]]): WPlusMonoid[A] =
  //  proxyOpsEx[PlusMonoid[A],WPlusMonoid[A], SeqWPlusMonoidImpl[A]](p, bt => SeqWPlusMonoidImpl(bt))

    implicit def plusMonoidElement[A:Elem]: Elem[PlusMonoid[A]] = {
     implicit val wA = element[A].tag;
     new SeqBaseElemEx[PlusMonoid[A], WPlusMonoid[A]](element[WPlusMonoid[A]])(weakTypeTag[PlusMonoid[A]], DefaultOfPlusMonoid[A])
  }

  case class SeqWPlusMonoidImpl[A]
      (override val wrappedValueOfBaseType: Rep[PlusMonoid[A]])
      (implicit eeA: Elem[A])
    extends WPlusMonoidImpl[A](wrappedValueOfBaseType)
        with UserTypeSeq[WPlusMonoidImpl[A]] {
    lazy val selfType = element[WPlusMonoidImpl[A]]
  }
  lazy val WPlusMonoidImpl = new WPlusMonoidImplCompanionAbs with UserTypeSeq[WPlusMonoidImplCompanionAbs] {
    lazy val selfType = element[WPlusMonoidImplCompanionAbs]
  }

  def mkWPlusMonoidImpl[A]
      (wrappedValueOfBaseType: Rep[PlusMonoid[A]])(implicit eeA: Elem[A]): Rep[WPlusMonoidImpl[A]] =
      new SeqWPlusMonoidImpl[A](wrappedValueOfBaseType)
  def unmkWPlusMonoidImpl[A](p: Rep[WPlusMonoid[A]]) = p match {
    case p: WPlusMonoidImpl[A] @unchecked =>
      Some((p.wrappedValueOfBaseType))
    case _ => None
  }

  implicit def wrapPlusMonoidToWPlusMonoid[A:Elem](v: PlusMonoid[A]): WPlusMonoid[A] = WPlusMonoidImpl(v)
}

// Exp -----------------------------------
trait WPlusMonoidsExp extends WPlusMonoidsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WPlusMonoid: Rep[WPlusMonoidCompanionAbs] = new WPlusMonoidCompanionAbs with UserTypeDef[WPlusMonoidCompanionAbs] {
    lazy val selfType = element[WPlusMonoidCompanionAbs]
    override def mirror(t: Transformer) = this

    def apply[A]( n: Rep[WNum[A]])(implicit emA: Elem[A]): Rep[WPlusMonoid[A]] =
      newObjEx(classOf[WPlusMonoid[A]], List(n.asRep[Any]/*, emA.asRep[Any]*/))
  }

  implicit def plusMonoidElement[A:Elem]: Elem[PlusMonoid[A]] = {
     implicit val wA = element[A].tag;
     new ExpBaseElemEx[PlusMonoid[A], WPlusMonoid[A]](element[WPlusMonoid[A]])(weakTypeTag[PlusMonoid[A]], DefaultOfPlusMonoid[A])
  }

  case class ExpWPlusMonoidImpl[A]
      (override val wrappedValueOfBaseType: Rep[PlusMonoid[A]])
      (implicit eeA: Elem[A])
    extends WPlusMonoidImpl[A](wrappedValueOfBaseType) with UserTypeDef[WPlusMonoidImpl[A]] {
    lazy val selfType = element[WPlusMonoidImpl[A]]
    override def mirror(t: Transformer) = ExpWPlusMonoidImpl[A](t(wrappedValueOfBaseType))
  }

  lazy val WPlusMonoidImpl: Rep[WPlusMonoidImplCompanionAbs] = new WPlusMonoidImplCompanionAbs with UserTypeDef[WPlusMonoidImplCompanionAbs] {
    lazy val selfType = element[WPlusMonoidImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WPlusMonoidImplMethods {
  }

  def mkWPlusMonoidImpl[A]
    (wrappedValueOfBaseType: Rep[PlusMonoid[A]])(implicit eeA: Elem[A]): Rep[WPlusMonoidImpl[A]] =
    new ExpWPlusMonoidImpl[A](wrappedValueOfBaseType)
  def unmkWPlusMonoidImpl[A](p: Rep[WPlusMonoid[A]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WPlusMonoidImplElem[A] @unchecked =>
      Some((p.asRep[WPlusMonoidImpl[A]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WPlusMonoidMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WPlusMonoid[A]] forSome {type A}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WPlusMonoidElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WPlusMonoid[A]] forSome {type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WPlusMonoid[A]] forSome {type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WPlusMonoidCompanionMethods {
    object apply {
      def unapply(d: Def[_]): Option[(Rep[WNum[A]], Elem[A]) forSome {type A}] = d match {
        case MethodCall(receiver, method, Seq(n, emA, _*), _) if receiver.elem == WPlusMonoidCompanionElem && method.getName == "apply" =>
          Some((n, emA)).asInstanceOf[Option[(Rep[WNum[A]], Elem[A]) forSome {type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[WNum[A]], Elem[A]) forSome {type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }
}
