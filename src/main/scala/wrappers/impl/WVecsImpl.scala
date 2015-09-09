package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scalanizer.linalgebra.Vec
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WVecsAbs extends WVecs with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWVec[T](p: Rep[WVec[T]]): WVec[T] = {
    proxyOps[WVec[T]](p)(scala.reflect.classTag[WVec[T]])
  }

  // TypeWrapper proxy
  //implicit def proxyVec[T:Elem](p: Rep[Vec[T]]): WVec[T] =
  //  proxyOps[WVec[T]](p.asRep[WVec[T]])

  implicit def unwrapValueOfWVec[T](w: Rep[WVec[T]]): Rep[Vec[T]] = w.wrappedValueOfBaseType

  implicit def vecElement[T:Elem]: Elem[Vec[T]]

  // familyElem
  abstract class WVecElem[T, To <: WVec[T]](implicit val eeT: Elem[T])
    extends WrapperElem[Vec[T], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WVec[T]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WVec[T]] => convertWVec(x) }
      tryConvert(element[WVec[T]], this, x, conv)
    }

    def convertWVec(x : Rep[WVec[T]]): Rep[To] = {
      assert(x.selfType1 match { case _: WVecElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wVecElement[T](implicit eeT: Elem[T]): Elem[WVec[T]] =
    new WVecElem[T, WVec[T]] {
      lazy val eTo = element[WVecImpl[T]]
    }

  implicit case object WVecCompanionElem extends CompanionElem[WVecCompanionAbs] {
    lazy val tag = weakTypeTag[WVecCompanionAbs]
    protected def getDefaultRep = WVec
  }

  abstract class WVecCompanionAbs extends CompanionBase[WVecCompanionAbs] with WVecCompanion {
    override def toString = "WVec"
  }
  def WVec: Rep[WVecCompanionAbs]
  implicit def proxyWVecCompanion(p: Rep[WVecCompanion]): WVecCompanion = {
    proxyOps[WVecCompanion](p)
  }

  // default wrapper implementation
  abstract class WVecImpl[T](val wrappedValueOfBaseType: Rep[Vec[T]])(implicit val eeT: Elem[T]) extends WVec[T] {
    def length: Rep[Int] =
      methodCallEx[Int](self,
        this.getClass.getMethod("length"),
        List())
  }
  trait WVecImplCompanion
  // elem for concrete class
  class WVecImplElem[T](val iso: Iso[WVecImplData[T], WVecImpl[T]])(implicit eeT: Elem[T])
    extends WVecElem[T, WVecImpl[T]]
    with ConcreteElem[WVecImplData[T], WVecImpl[T]] {
    lazy val eTo = this
    override def convertWVec(x: Rep[WVec[T]]) = WVecImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WVecImpl[T]]
    }
  }

  // state representation type
  type WVecImplData[T] = Vec[T]

  // 3) Iso for concrete class
  class WVecImplIso[T](implicit eeT: Elem[T])
    extends Iso[WVecImplData[T], WVecImpl[T]] {
    override def from(p: Rep[WVecImpl[T]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[Vec[T]]) = {
      val wrappedValueOfBaseType = p
      WVecImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WVecImpl[T]] = WVecImpl(DefaultOfVec[T].value)
    lazy val eTo = new WVecImplElem[T](this)
  }
  // 4) constructor and deconstructor
  abstract class WVecImplCompanionAbs extends CompanionBase[WVecImplCompanionAbs] with WVecImplCompanion {
    override def toString = "WVecImpl"

    def apply[T](wrappedValueOfBaseType: Rep[Vec[T]])(implicit eeT: Elem[T]): Rep[WVecImpl[T]] =
      mkWVecImpl(wrappedValueOfBaseType)
  }
  object WVecImplMatcher {
    def unapply[T](p: Rep[WVec[T]]) = unmkWVecImpl(p)
  }
  def WVecImpl: Rep[WVecImplCompanionAbs]
  implicit def proxyWVecImplCompanion(p: Rep[WVecImplCompanionAbs]): WVecImplCompanionAbs = {
    proxyOps[WVecImplCompanionAbs](p)
  }

  implicit case object WVecImplCompanionElem extends CompanionElem[WVecImplCompanionAbs] {
    lazy val tag = weakTypeTag[WVecImplCompanionAbs]
    protected def getDefaultRep = WVecImpl
  }

  implicit def proxyWVecImpl[T](p: Rep[WVecImpl[T]]): WVecImpl[T] =
    proxyOps[WVecImpl[T]](p)

  implicit class ExtendedWVecImpl[T](p: Rep[WVecImpl[T]])(implicit eeT: Elem[T]) {
    def toData: Rep[WVecImplData[T]] = isoWVecImpl(eeT).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWVecImpl[T](implicit eeT: Elem[T]): Iso[WVecImplData[T], WVecImpl[T]] =
    new WVecImplIso[T]

  // 6) smart constructor and deconstructor
  def mkWVecImpl[T](wrappedValueOfBaseType: Rep[Vec[T]])(implicit eeT: Elem[T]): Rep[WVecImpl[T]]
  def unmkWVecImpl[T](p: Rep[WVec[T]]): Option[(Rep[Vec[T]])]
}

// Seq -----------------------------------
trait WVecsSeq extends WVecsDsl with ScalanSeq {
  self: WrappersDslSeq =>
  lazy val WVec: Rep[WVecCompanionAbs] = new WVecCompanionAbs with UserTypeSeq[WVecCompanionAbs] {
    lazy val selfType = element[WVecCompanionAbs]
  }

    // override proxy if we deal with TypeWrapper
  //override def proxyVec[T:Elem](p: Rep[Vec[T]]): WVec[T] =
  //  proxyOpsEx[Vec[T],WVec[T], SeqWVecImpl[T]](p, bt => SeqWVecImpl(bt))

    implicit def vecElement[T:Elem]: Elem[Vec[T]] = {
     implicit val wT = element[T].tag;
     new SeqBaseElemEx[Vec[T], WVec[T]](element[WVec[T]])(weakTypeTag[Vec[T]], DefaultOfVec[T])
  }

  case class SeqWVecImpl[T]
      (override val wrappedValueOfBaseType: Rep[Vec[T]])
      (implicit eeT: Elem[T])
    extends WVecImpl[T](wrappedValueOfBaseType)
        with UserTypeSeq[WVecImpl[T]] {
    lazy val selfType = element[WVecImpl[T]]
    override def length: Rep[Int] =
      wrappedValueOfBaseType.length
  }
  lazy val WVecImpl = new WVecImplCompanionAbs with UserTypeSeq[WVecImplCompanionAbs] {
    lazy val selfType = element[WVecImplCompanionAbs]
  }

  def mkWVecImpl[T]
      (wrappedValueOfBaseType: Rep[Vec[T]])(implicit eeT: Elem[T]): Rep[WVecImpl[T]] =
      new SeqWVecImpl[T](wrappedValueOfBaseType)
  def unmkWVecImpl[T](p: Rep[WVec[T]]) = p match {
    case p: WVecImpl[T] @unchecked =>
      Some((p.wrappedValueOfBaseType))
    case _ => None
  }

  implicit def wrapVecToWVec[T:Elem](v: Vec[T]): WVec[T] = WVecImpl(v)
}

// Exp -----------------------------------
trait WVecsExp extends WVecsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WVec: Rep[WVecCompanionAbs] = new WVecCompanionAbs with UserTypeDef[WVecCompanionAbs] {
    lazy val selfType = element[WVecCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  implicit def vecElement[T:Elem]: Elem[Vec[T]] = {
     implicit val wT = element[T].tag;
     new ExpBaseElemEx[Vec[T], WVec[T]](element[WVec[T]])(weakTypeTag[Vec[T]], DefaultOfVec[T])
  }

  case class ExpWVecImpl[T]
      (override val wrappedValueOfBaseType: Rep[Vec[T]])
      (implicit eeT: Elem[T])
    extends WVecImpl[T](wrappedValueOfBaseType) with UserTypeDef[WVecImpl[T]] {
    lazy val selfType = element[WVecImpl[T]]
    override def mirror(t: Transformer) = ExpWVecImpl[T](t(wrappedValueOfBaseType))
  }

  lazy val WVecImpl: Rep[WVecImplCompanionAbs] = new WVecImplCompanionAbs with UserTypeDef[WVecImplCompanionAbs] {
    lazy val selfType = element[WVecImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WVecImplMethods {
  }

  def mkWVecImpl[T]
    (wrappedValueOfBaseType: Rep[Vec[T]])(implicit eeT: Elem[T]): Rep[WVecImpl[T]] =
    new ExpWVecImpl[T](wrappedValueOfBaseType)
  def unmkWVecImpl[T](p: Rep[WVec[T]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WVecImplElem[T] @unchecked =>
      Some((p.asRep[WVecImpl[T]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WVecMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WVec[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WVecElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WVec[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WVec[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object length {
      def unapply(d: Def[_]): Option[Rep[WVec[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WVecElem[_, _]] && method.getName == "length" =>
          Some(receiver).asInstanceOf[Option[Rep[WVec[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WVec[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WVecCompanionMethods {
  }
}
