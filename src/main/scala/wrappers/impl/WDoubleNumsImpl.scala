package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scalanizer.DoubleNum
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WDoubleNumsAbs extends WDoubleNums with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWDoubleNum(p: Rep[WDoubleNum]): WDoubleNum = {
    proxyOps[WDoubleNum](p)(scala.reflect.classTag[WDoubleNum])
  }

  // TypeWrapper proxy
  //implicit def proxyDoubleNum(p: Rep[DoubleNum]): WDoubleNum =
  //  proxyOps[WDoubleNum](p.asRep[WDoubleNum])

  implicit def unwrapValueOfWDoubleNum(w: Rep[WDoubleNum]): Rep[DoubleNum] = w.wrappedValueOfBaseType

  implicit def doubleNumElement: Elem[DoubleNum]

  // familyElem
  abstract class WDoubleNumElem[To <: WDoubleNum]
    extends WrapperElem[DoubleNum, To] {
    override def isEntityType = true
    override lazy val tag = {
      weakTypeTag[WDoubleNum].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WDoubleNum] => convertWDoubleNum(x) }
      tryConvert(element[WDoubleNum], this, x, conv)
    }

    def convertWDoubleNum(x : Rep[WDoubleNum]): Rep[To] = {
      assert(x.selfType1 match { case _: WDoubleNumElem[_] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wDoubleNumElement: Elem[WDoubleNum] =
    new WDoubleNumElem[WDoubleNum] {
      lazy val eTo = element[WDoubleNumImpl]
    }

  implicit case object WDoubleNumCompanionElem extends CompanionElem[WDoubleNumCompanionAbs] {
    lazy val tag = weakTypeTag[WDoubleNumCompanionAbs]
    protected def getDefaultRep = WDoubleNum
  }

  abstract class WDoubleNumCompanionAbs extends CompanionBase[WDoubleNumCompanionAbs] with WDoubleNumCompanion {
    override def toString = "WDoubleNum"
  }
  def WDoubleNum: Rep[WDoubleNumCompanionAbs]
  implicit def proxyWDoubleNumCompanion(p: Rep[WDoubleNumCompanion]): WDoubleNumCompanion = {
    proxyOps[WDoubleNumCompanion](p)
  }

  // default wrapper implementation
  abstract class WDoubleNumImpl(val wrappedValueOfBaseType: Rep[DoubleNum]) extends WDoubleNum {
  }
  trait WDoubleNumImplCompanion
  // elem for concrete class
  class WDoubleNumImplElem(val iso: Iso[WDoubleNumImplData, WDoubleNumImpl])
    extends WDoubleNumElem[WDoubleNumImpl]
    with ConcreteElem[WDoubleNumImplData, WDoubleNumImpl] {
    lazy val eTo = this
    override def convertWDoubleNum(x: Rep[WDoubleNum]) = WDoubleNumImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      weakTypeTag[WDoubleNumImpl]
    }
  }

  // state representation type
  type WDoubleNumImplData = DoubleNum

  // 3) Iso for concrete class
  class WDoubleNumImplIso
    extends Iso[WDoubleNumImplData, WDoubleNumImpl] {
    override def from(p: Rep[WDoubleNumImpl]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[DoubleNum]) = {
      val wrappedValueOfBaseType = p
      WDoubleNumImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WDoubleNumImpl] = WDoubleNumImpl(DefaultOfDoubleNum.value)
    lazy val eTo = new WDoubleNumImplElem(this)
  }
  // 4) constructor and deconstructor
  abstract class WDoubleNumImplCompanionAbs extends CompanionBase[WDoubleNumImplCompanionAbs] with WDoubleNumImplCompanion {
    override def toString = "WDoubleNumImpl"

    def apply(wrappedValueOfBaseType: Rep[DoubleNum]): Rep[WDoubleNumImpl] =
      mkWDoubleNumImpl(wrappedValueOfBaseType)
  }
  object WDoubleNumImplMatcher {
    def unapply(p: Rep[WDoubleNum]) = unmkWDoubleNumImpl(p)
  }
  def WDoubleNumImpl: Rep[WDoubleNumImplCompanionAbs]
  implicit def proxyWDoubleNumImplCompanion(p: Rep[WDoubleNumImplCompanionAbs]): WDoubleNumImplCompanionAbs = {
    proxyOps[WDoubleNumImplCompanionAbs](p)
  }

  implicit case object WDoubleNumImplCompanionElem extends CompanionElem[WDoubleNumImplCompanionAbs] {
    lazy val tag = weakTypeTag[WDoubleNumImplCompanionAbs]
    protected def getDefaultRep = WDoubleNumImpl
  }

  implicit def proxyWDoubleNumImpl(p: Rep[WDoubleNumImpl]): WDoubleNumImpl =
    proxyOps[WDoubleNumImpl](p)

  implicit class ExtendedWDoubleNumImpl(p: Rep[WDoubleNumImpl]) {
    def toData: Rep[WDoubleNumImplData] = isoWDoubleNumImpl.from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWDoubleNumImpl: Iso[WDoubleNumImplData, WDoubleNumImpl] =
    new WDoubleNumImplIso

  // 6) smart constructor and deconstructor
  def mkWDoubleNumImpl(wrappedValueOfBaseType: Rep[DoubleNum]): Rep[WDoubleNumImpl]
  def unmkWDoubleNumImpl(p: Rep[WDoubleNum]): Option[(Rep[DoubleNum])]
}

// Seq -----------------------------------
trait WDoubleNumsSeq extends WDoubleNumsDsl with ScalanSeq {
  self: WrappersDslSeq =>
  lazy val WDoubleNum: Rep[WDoubleNumCompanionAbs] = new WDoubleNumCompanionAbs with UserTypeSeq[WDoubleNumCompanionAbs] {
    lazy val selfType = element[WDoubleNumCompanionAbs]

    override def apply: Rep[WDoubleNum] =
      WDoubleNumImpl(new DoubleNum)
  }

    // override proxy if we deal with TypeWrapper
  //override def proxyDoubleNum(p: Rep[DoubleNum]): WDoubleNum =
  //  proxyOpsEx[DoubleNum,WDoubleNum, SeqWDoubleNumImpl](p, bt => SeqWDoubleNumImpl(bt))

    implicit lazy val doubleNumElement: Elem[DoubleNum] = new SeqBaseElemEx[DoubleNum, WDoubleNum](element[WDoubleNum])(weakTypeTag[DoubleNum], DefaultOfDoubleNum)

  case class SeqWDoubleNumImpl
      (override val wrappedValueOfBaseType: Rep[DoubleNum])

    extends WDoubleNumImpl(wrappedValueOfBaseType)
        with UserTypeSeq[WDoubleNumImpl] {
    lazy val selfType = element[WDoubleNumImpl]
  }
  lazy val WDoubleNumImpl = new WDoubleNumImplCompanionAbs with UserTypeSeq[WDoubleNumImplCompanionAbs] {
    lazy val selfType = element[WDoubleNumImplCompanionAbs]
  }

  def mkWDoubleNumImpl
      (wrappedValueOfBaseType: Rep[DoubleNum]): Rep[WDoubleNumImpl] =
      new SeqWDoubleNumImpl(wrappedValueOfBaseType)
  def unmkWDoubleNumImpl(p: Rep[WDoubleNum]) = p match {
    case p: WDoubleNumImpl @unchecked =>
      Some((p.wrappedValueOfBaseType))
    case _ => None
  }

  implicit def wrapDoubleNumToWDoubleNum(v: DoubleNum): WDoubleNum = WDoubleNumImpl(v)
}

// Exp -----------------------------------
trait WDoubleNumsExp extends WDoubleNumsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WDoubleNum: Rep[WDoubleNumCompanionAbs] = new WDoubleNumCompanionAbs with UserTypeDef[WDoubleNumCompanionAbs] {
    lazy val selfType = element[WDoubleNumCompanionAbs]
    override def mirror(t: Transformer) = this

    def apply: Rep[WDoubleNum] =
      newObjEx(classOf[WDoubleNum], List())
  }

  implicit lazy val doubleNumElement: Elem[DoubleNum] = new ExpBaseElemEx[DoubleNum, WDoubleNum](element[WDoubleNum])(weakTypeTag[DoubleNum], DefaultOfDoubleNum)

  case class ExpWDoubleNumImpl
      (override val wrappedValueOfBaseType: Rep[DoubleNum])

    extends WDoubleNumImpl(wrappedValueOfBaseType) with UserTypeDef[WDoubleNumImpl] {
    lazy val selfType = element[WDoubleNumImpl]
    override def mirror(t: Transformer) = ExpWDoubleNumImpl(t(wrappedValueOfBaseType))
  }

  lazy val WDoubleNumImpl: Rep[WDoubleNumImplCompanionAbs] = new WDoubleNumImplCompanionAbs with UserTypeDef[WDoubleNumImplCompanionAbs] {
    lazy val selfType = element[WDoubleNumImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WDoubleNumImplMethods {
  }

  def mkWDoubleNumImpl
    (wrappedValueOfBaseType: Rep[DoubleNum]): Rep[WDoubleNumImpl] =
    new ExpWDoubleNumImpl(wrappedValueOfBaseType)
  def unmkWDoubleNumImpl(p: Rep[WDoubleNum]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WDoubleNumImplElem @unchecked =>
      Some((p.asRep[WDoubleNumImpl].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WDoubleNumMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WDoubleNum]] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WDoubleNumElem[_]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WDoubleNum]]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WDoubleNum]] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WDoubleNumCompanionMethods {
    object apply {
      def unapply(d: Def[_]): Option[Unit] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem == WDoubleNumCompanionElem && method.getName == "apply" =>
          Some(()).asInstanceOf[Option[Unit]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Unit] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }
}
