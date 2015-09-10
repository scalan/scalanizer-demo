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
  implicit def proxyWPlusMonoid[T](p: Rep[WPlusMonoid[T]]): WPlusMonoid[T] = {
    proxyOps[WPlusMonoid[T]](p)(scala.reflect.classTag[WPlusMonoid[T]])
  }

  // TypeWrapper proxy
  //implicit def proxyPlusMonoid[T:Elem](p: Rep[PlusMonoid[T]]): WPlusMonoid[T] =
  //  proxyOps[WPlusMonoid[T]](p.asRep[WPlusMonoid[T]])

  implicit def unwrapValueOfWPlusMonoid[T](w: Rep[WPlusMonoid[T]]): Rep[PlusMonoid[T]] = w.wrappedValueOfBaseType

  implicit def plusMonoidElement[T:Elem]: Elem[PlusMonoid[T]]

  // familyElem
  abstract class WPlusMonoidElem[T, To <: WPlusMonoid[T]](implicit val eeT: Elem[T])
    extends WrapperElem[PlusMonoid[T], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WPlusMonoid[T]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WPlusMonoid[T]] => convertWPlusMonoid(x) }
      tryConvert(element[WPlusMonoid[T]], this, x, conv)
    }

    def convertWPlusMonoid(x : Rep[WPlusMonoid[T]]): Rep[To] = {
      assert(x.selfType1 match { case _: WPlusMonoidElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wPlusMonoidElement[T](implicit eeT: Elem[T]): Elem[WPlusMonoid[T]] =
    new WPlusMonoidElem[T, WPlusMonoid[T]] {
      lazy val eTo = element[WPlusMonoidImpl[T]]
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
  abstract class WPlusMonoidImpl[T](val wrappedValueOfBaseType: Rep[PlusMonoid[T]])(implicit val eeT: Elem[T]) extends WPlusMonoid[T] {
  }
  trait WPlusMonoidImplCompanion
  // elem for concrete class
  class WPlusMonoidImplElem[T](val iso: Iso[WPlusMonoidImplData[T], WPlusMonoidImpl[T]])(implicit eeT: Elem[T])
    extends WPlusMonoidElem[T, WPlusMonoidImpl[T]]
    with ConcreteElem[WPlusMonoidImplData[T], WPlusMonoidImpl[T]] {
    lazy val eTo = this
    override def convertWPlusMonoid(x: Rep[WPlusMonoid[T]]) = WPlusMonoidImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WPlusMonoidImpl[T]]
    }
  }

  // state representation type
  type WPlusMonoidImplData[T] = PlusMonoid[T]

  // 3) Iso for concrete class
  class WPlusMonoidImplIso[T](implicit eeT: Elem[T])
    extends Iso[WPlusMonoidImplData[T], WPlusMonoidImpl[T]] {
    override def from(p: Rep[WPlusMonoidImpl[T]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[PlusMonoid[T]]) = {
      val wrappedValueOfBaseType = p
      WPlusMonoidImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WPlusMonoidImpl[T]] = WPlusMonoidImpl(DefaultOfPlusMonoid[T].value)
    lazy val eTo = new WPlusMonoidImplElem[T](this)
  }
  // 4) constructor and deconstructor
  abstract class WPlusMonoidImplCompanionAbs extends CompanionBase[WPlusMonoidImplCompanionAbs] with WPlusMonoidImplCompanion {
    override def toString = "WPlusMonoidImpl"

    def apply[T](wrappedValueOfBaseType: Rep[PlusMonoid[T]])(implicit eeT: Elem[T]): Rep[WPlusMonoidImpl[T]] =
      mkWPlusMonoidImpl(wrappedValueOfBaseType)
  }
  object WPlusMonoidImplMatcher {
    def unapply[T](p: Rep[WPlusMonoid[T]]) = unmkWPlusMonoidImpl(p)
  }
  def WPlusMonoidImpl: Rep[WPlusMonoidImplCompanionAbs]
  implicit def proxyWPlusMonoidImplCompanion(p: Rep[WPlusMonoidImplCompanionAbs]): WPlusMonoidImplCompanionAbs = {
    proxyOps[WPlusMonoidImplCompanionAbs](p)
  }

  implicit case object WPlusMonoidImplCompanionElem extends CompanionElem[WPlusMonoidImplCompanionAbs] {
    lazy val tag = weakTypeTag[WPlusMonoidImplCompanionAbs]
    protected def getDefaultRep = WPlusMonoidImpl
  }

  implicit def proxyWPlusMonoidImpl[T](p: Rep[WPlusMonoidImpl[T]]): WPlusMonoidImpl[T] =
    proxyOps[WPlusMonoidImpl[T]](p)

  implicit class ExtendedWPlusMonoidImpl[T](p: Rep[WPlusMonoidImpl[T]])(implicit eeT: Elem[T]) {
    def toData: Rep[WPlusMonoidImplData[T]] = isoWPlusMonoidImpl(eeT).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWPlusMonoidImpl[T](implicit eeT: Elem[T]): Iso[WPlusMonoidImplData[T], WPlusMonoidImpl[T]] =
    new WPlusMonoidImplIso[T]

  // 6) smart constructor and deconstructor
  def mkWPlusMonoidImpl[T](wrappedValueOfBaseType: Rep[PlusMonoid[T]])(implicit eeT: Elem[T]): Rep[WPlusMonoidImpl[T]]
  def unmkWPlusMonoidImpl[T](p: Rep[WPlusMonoid[T]]): Option[(Rep[PlusMonoid[T]])]
}

// Seq -----------------------------------
trait WPlusMonoidsSeq extends WPlusMonoidsDsl with ScalanSeq {
  self: WrappersDslSeq =>
  lazy val WPlusMonoid: Rep[WPlusMonoidCompanionAbs] = new WPlusMonoidCompanionAbs with UserTypeSeq[WPlusMonoidCompanionAbs] {
    lazy val selfType = element[WPlusMonoidCompanionAbs]

    override def apply[T:Elem]( n: Rep[WNum[T]]): Rep[WPlusMonoid[T]] =
      WPlusMonoidImpl(new PlusMonoid[T](n))
  }

    // override proxy if we deal with TypeWrapper
  //override def proxyPlusMonoid[T:Elem](p: Rep[PlusMonoid[T]]): WPlusMonoid[T] =
  //  proxyOpsEx[PlusMonoid[T],WPlusMonoid[T], SeqWPlusMonoidImpl[T]](p, bt => SeqWPlusMonoidImpl(bt))

    implicit def plusMonoidElement[T:Elem]: Elem[PlusMonoid[T]] = {
     implicit val wT = element[T].tag;
     new SeqBaseElemEx[PlusMonoid[T], WPlusMonoid[T]](element[WPlusMonoid[T]])(weakTypeTag[PlusMonoid[T]], DefaultOfPlusMonoid[T])
  }

  case class SeqWPlusMonoidImpl[T]
      (override val wrappedValueOfBaseType: Rep[PlusMonoid[T]])
      (implicit eeT: Elem[T])
    extends WPlusMonoidImpl[T](wrappedValueOfBaseType)
        with UserTypeSeq[WPlusMonoidImpl[T]] {
    lazy val selfType = element[WPlusMonoidImpl[T]]
  }
  lazy val WPlusMonoidImpl = new WPlusMonoidImplCompanionAbs with UserTypeSeq[WPlusMonoidImplCompanionAbs] {
    lazy val selfType = element[WPlusMonoidImplCompanionAbs]
  }

  def mkWPlusMonoidImpl[T]
      (wrappedValueOfBaseType: Rep[PlusMonoid[T]])(implicit eeT: Elem[T]): Rep[WPlusMonoidImpl[T]] =
      new SeqWPlusMonoidImpl[T](wrappedValueOfBaseType)
  def unmkWPlusMonoidImpl[T](p: Rep[WPlusMonoid[T]]) = p match {
    case p: WPlusMonoidImpl[T] @unchecked =>
      Some((p.wrappedValueOfBaseType))
    case _ => None
  }

  implicit def wrapPlusMonoidToWPlusMonoid[T:Elem](v: PlusMonoid[T]): WPlusMonoid[T] = WPlusMonoidImpl(v)
}

// Exp -----------------------------------
trait WPlusMonoidsExp extends WPlusMonoidsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WPlusMonoid: Rep[WPlusMonoidCompanionAbs] = new WPlusMonoidCompanionAbs with UserTypeDef[WPlusMonoidCompanionAbs] {
    lazy val selfType = element[WPlusMonoidCompanionAbs]
    override def mirror(t: Transformer) = this

    def apply[T:Elem]( n: Rep[WNum[T]]): Rep[WPlusMonoid[T]] =
      newObjEx(classOf[WPlusMonoid[T]], List(n.asRep[Any]))
  }

  implicit def plusMonoidElement[T:Elem]: Elem[PlusMonoid[T]] = {
     implicit val wT = element[T].tag;
     new ExpBaseElemEx[PlusMonoid[T], WPlusMonoid[T]](element[WPlusMonoid[T]])(weakTypeTag[PlusMonoid[T]], DefaultOfPlusMonoid[T])
  }

  case class ExpWPlusMonoidImpl[T]
      (override val wrappedValueOfBaseType: Rep[PlusMonoid[T]])
      (implicit eeT: Elem[T])
    extends WPlusMonoidImpl[T](wrappedValueOfBaseType) with UserTypeDef[WPlusMonoidImpl[T]] {
    lazy val selfType = element[WPlusMonoidImpl[T]]
    override def mirror(t: Transformer) = ExpWPlusMonoidImpl[T](t(wrappedValueOfBaseType))
  }

  lazy val WPlusMonoidImpl: Rep[WPlusMonoidImplCompanionAbs] = new WPlusMonoidImplCompanionAbs with UserTypeDef[WPlusMonoidImplCompanionAbs] {
    lazy val selfType = element[WPlusMonoidImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WPlusMonoidImplMethods {
  }

  def mkWPlusMonoidImpl[T]
    (wrappedValueOfBaseType: Rep[PlusMonoid[T]])(implicit eeT: Elem[T]): Rep[WPlusMonoidImpl[T]] =
    new ExpWPlusMonoidImpl[T](wrappedValueOfBaseType)
  def unmkWPlusMonoidImpl[T](p: Rep[WPlusMonoid[T]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WPlusMonoidImplElem[T] @unchecked =>
      Some((p.asRep[WPlusMonoidImpl[T]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WPlusMonoidMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WPlusMonoid[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WPlusMonoidElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WPlusMonoid[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WPlusMonoid[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WPlusMonoidCompanionMethods {
    object apply {
      def unapply(d: Def[_]): Option[(Rep[WNum[T]], Elem[T]) forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(n, emT, _*), _) if receiver.elem == WPlusMonoidCompanionElem && method.getName == "apply" =>
          Some((n, emT)).asInstanceOf[Option[(Rep[WNum[T]], Elem[T]) forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[WNum[T]], Elem[T]) forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }
}
