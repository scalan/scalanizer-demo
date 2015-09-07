package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scalanizer.Num
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WNumsAbs extends WNums with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWNum[T](p: Rep[WNum[T]]): WNum[T] = {
    proxyOps[WNum[T]](p)(scala.reflect.classTag[WNum[T]])
  }

  // TypeWrapper proxy
  //implicit def proxyNum[T:Elem](p: Rep[Num[T]]): WNum[T] =
  //  proxyOps[WNum[T]](p.asRep[WNum[T]])

  implicit def unwrapValueOfWNum[T](w: Rep[WNum[T]]): Rep[Num[T]] = w.wrappedValueOfBaseType

  implicit def numElement[T:Elem]: Elem[Num[T]]

  // familyElem
  abstract class WNumElem[T, To <: WNum[T]](implicit val eeT: Elem[T])
    extends WrapperElem[Num[T], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WNum[T]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WNum[T]] => convertWNum(x) }
      tryConvert(element[WNum[T]], this, x, conv)
    }

    def convertWNum(x : Rep[WNum[T]]): Rep[To] = {
      assert(x.selfType1 match { case _: WNumElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wNumElement[T](implicit eeT: Elem[T]): Elem[WNum[T]] =
    new WNumElem[T, WNum[T]] {
      lazy val eTo = element[WNumImpl[T]]
    }

  implicit case object WNumCompanionElem extends CompanionElem[WNumCompanionAbs] {
    lazy val tag = weakTypeTag[WNumCompanionAbs]
    protected def getDefaultRep = WNum
  }

  abstract class WNumCompanionAbs extends CompanionBase[WNumCompanionAbs] with WNumCompanion {
    override def toString = "WNum"
  }
  def WNum: Rep[WNumCompanionAbs]
  implicit def proxyWNumCompanion(p: Rep[WNumCompanion]): WNumCompanion = {
    proxyOps[WNumCompanion](p)
  }

  // default wrapper implementation
  abstract class WNumImpl[T](val wrappedValueOfBaseType: Rep[Num[T]])(implicit val eeT: Elem[T]) extends WNum[T] {
    def zero: Rep[T] =
      methodCallEx[T](self,
        this.getClass.getMethod("zero"),
        List())
  }
  trait WNumImplCompanion
  // elem for concrete class
  class WNumImplElem[T](val iso: Iso[WNumImplData[T], WNumImpl[T]])(implicit eeT: Elem[T])
    extends WNumElem[T, WNumImpl[T]]
    with ConcreteElem[WNumImplData[T], WNumImpl[T]] {
    lazy val eTo = this
    override def convertWNum(x: Rep[WNum[T]]) = WNumImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WNumImpl[T]]
    }
  }

  // state representation type
  type WNumImplData[T] = Num[T]

  // 3) Iso for concrete class
  class WNumImplIso[T](implicit eeT: Elem[T])
    extends Iso[WNumImplData[T], WNumImpl[T]] {
    override def from(p: Rep[WNumImpl[T]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[Num[T]]) = {
      val wrappedValueOfBaseType = p
      WNumImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WNumImpl[T]] = WNumImpl(DefaultOfNum[T].value)
    lazy val eTo = new WNumImplElem[T](this)
  }
  // 4) constructor and deconstructor
  abstract class WNumImplCompanionAbs extends CompanionBase[WNumImplCompanionAbs] with WNumImplCompanion {
    override def toString = "WNumImpl"

    def apply[T](wrappedValueOfBaseType: Rep[Num[T]])(implicit eeT: Elem[T]): Rep[WNumImpl[T]] =
      mkWNumImpl(wrappedValueOfBaseType)
  }
  object WNumImplMatcher {
    def unapply[T](p: Rep[WNum[T]]) = unmkWNumImpl(p)
  }
  def WNumImpl: Rep[WNumImplCompanionAbs]
  implicit def proxyWNumImplCompanion(p: Rep[WNumImplCompanionAbs]): WNumImplCompanionAbs = {
    proxyOps[WNumImplCompanionAbs](p)
  }

  implicit case object WNumImplCompanionElem extends CompanionElem[WNumImplCompanionAbs] {
    lazy val tag = weakTypeTag[WNumImplCompanionAbs]
    protected def getDefaultRep = WNumImpl
  }

  implicit def proxyWNumImpl[T](p: Rep[WNumImpl[T]]): WNumImpl[T] =
    proxyOps[WNumImpl[T]](p)

  implicit class ExtendedWNumImpl[T](p: Rep[WNumImpl[T]])(implicit eeT: Elem[T]) {
    def toData: Rep[WNumImplData[T]] = isoWNumImpl(eeT).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWNumImpl[T](implicit eeT: Elem[T]): Iso[WNumImplData[T], WNumImpl[T]] =
    new WNumImplIso[T]

  // 6) smart constructor and deconstructor
  def mkWNumImpl[T](wrappedValueOfBaseType: Rep[Num[T]])(implicit eeT: Elem[T]): Rep[WNumImpl[T]]
  def unmkWNumImpl[T](p: Rep[WNum[T]]): Option[(Rep[Num[T]])]
}

// Seq -----------------------------------
trait WNumsSeq extends WNumsDsl with ScalanSeq {
  self: WrappersDslSeq =>
  lazy val WNum: Rep[WNumCompanionAbs] = new WNumCompanionAbs with UserTypeSeq[WNumCompanionAbs] {
    lazy val selfType = element[WNumCompanionAbs]
  }

    // override proxy if we deal with TypeWrapper
  //override def proxyNum[T:Elem](p: Rep[Num[T]]): WNum[T] =
  //  proxyOpsEx[Num[T],WNum[T], SeqWNumImpl[T]](p, bt => SeqWNumImpl(bt))

    implicit def numElement[T:Elem]: Elem[Num[T]] = {
     implicit val wT = element[T].tag;
     new SeqBaseElemEx[Num[T], WNum[T]](element[WNum[T]])(weakTypeTag[Num[T]], DefaultOfNum[T])
  }

  case class SeqWNumImpl[T]
      (override val wrappedValueOfBaseType: Rep[Num[T]])
      (implicit eeT: Elem[T])
    extends WNumImpl[T](wrappedValueOfBaseType)
        with UserTypeSeq[WNumImpl[T]] {
    lazy val selfType = element[WNumImpl[T]]
    override def zero: Rep[T] =
      wrappedValueOfBaseType.zero
  }
  lazy val WNumImpl = new WNumImplCompanionAbs with UserTypeSeq[WNumImplCompanionAbs] {
    lazy val selfType = element[WNumImplCompanionAbs]
  }

  def mkWNumImpl[T]
      (wrappedValueOfBaseType: Rep[Num[T]])(implicit eeT: Elem[T]): Rep[WNumImpl[T]] =
      new SeqWNumImpl[T](wrappedValueOfBaseType)
  def unmkWNumImpl[T](p: Rep[WNum[T]]) = p match {
    case p: WNumImpl[T] @unchecked =>
      Some((p.wrappedValueOfBaseType))
    case _ => None
  }

  implicit def wrapNumToWNum[T:Elem](v: Num[T]): WNum[T] = WNumImpl(v)
}

// Exp -----------------------------------
trait WNumsExp extends WNumsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WNum: Rep[WNumCompanionAbs] = new WNumCompanionAbs with UserTypeDef[WNumCompanionAbs] {
    lazy val selfType = element[WNumCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  implicit def numElement[T:Elem]: Elem[Num[T]] = {
     implicit val wT = element[T].tag;
     new ExpBaseElemEx[Num[T], WNum[T]](element[WNum[T]])(weakTypeTag[Num[T]], DefaultOfNum[T])
  }

  case class ExpWNumImpl[T]
      (override val wrappedValueOfBaseType: Rep[Num[T]])
      (implicit eeT: Elem[T])
    extends WNumImpl[T](wrappedValueOfBaseType) with UserTypeDef[WNumImpl[T]] {
    lazy val selfType = element[WNumImpl[T]]
    override def mirror(t: Transformer) = ExpWNumImpl[T](t(wrappedValueOfBaseType))
  }

  lazy val WNumImpl: Rep[WNumImplCompanionAbs] = new WNumImplCompanionAbs with UserTypeDef[WNumImplCompanionAbs] {
    lazy val selfType = element[WNumImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WNumImplMethods {
  }

  def mkWNumImpl[T]
    (wrappedValueOfBaseType: Rep[Num[T]])(implicit eeT: Elem[T]): Rep[WNumImpl[T]] =
    new ExpWNumImpl[T](wrappedValueOfBaseType)
  def unmkWNumImpl[T](p: Rep[WNum[T]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WNumImplElem[T] @unchecked =>
      Some((p.asRep[WNumImpl[T]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WNumMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WNum[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WNumElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WNum[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WNum[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object zero {
      def unapply(d: Def[_]): Option[Rep[WNum[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WNumElem[_, _]] && method.getName == "zero" =>
          Some(receiver).asInstanceOf[Option[Rep[WNum[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WNum[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WNumCompanionMethods {
  }
}
