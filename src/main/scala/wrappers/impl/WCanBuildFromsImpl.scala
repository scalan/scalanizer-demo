package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scala.collection.generic.CanBuildFrom
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WCanBuildFromsAbs extends WCanBuildFroms with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo](p: Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]): WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo] = {
    proxyOps[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]](p)(scala.reflect.classTag[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]])
  }

  // TypeWrapper proxy
  //implicit def proxyCanBuildFrom[From:Elem, WCanBuildFromsElem:Elem, WCanBuildFromsTo:Elem](p: Rep[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]): WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo] =
  //  proxyOps[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]](p.asRep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]])

  implicit def unwrapValueOfWCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo](w: Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]): Rep[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] = w.wrappedValueOfBaseType

  implicit def canBuildFromElement[From:Elem, WCanBuildFromsElem:Elem, WCanBuildFromsTo:Elem]: Elem[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]

  // familyElem
  abstract class WCanBuildFromElem[From, WCanBuildFromsElem, WCanBuildFromsTo, To <: WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]](implicit val eeFrom: Elem[From], val eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], val eeWCanBuildFromsTo: Elem[WCanBuildFromsTo])
    extends WrapperElem[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagFrom = eeFrom.tag
      implicit val tagWCanBuildFromsElem = eeWCanBuildFromsElem.tag
      implicit val tagWCanBuildFromsTo = eeWCanBuildFromsTo.tag
      weakTypeTag[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] => convertWCanBuildFrom(x) }
      tryConvert(element[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]], this, x, conv)
    }

    def convertWCanBuildFrom(x : Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]): Rep[To] = {
      assert(x.selfType1 match { case _: WCanBuildFromElem[_, _, _, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wCanBuildFromElement[From, WCanBuildFromsElem, WCanBuildFromsTo](implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo]): Elem[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] =
    new WCanBuildFromElem[From, WCanBuildFromsElem, WCanBuildFromsTo, WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] {
      lazy val eTo = element[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]]
    }

  implicit case object WCanBuildFromCompanionElem extends CompanionElem[WCanBuildFromCompanionAbs] {
    lazy val tag = weakTypeTag[WCanBuildFromCompanionAbs]
    protected def getDefaultRep = WCanBuildFrom
  }

  abstract class WCanBuildFromCompanionAbs extends CompanionBase[WCanBuildFromCompanionAbs] with WCanBuildFromCompanion {
    override def toString = "WCanBuildFrom"
  }
  def WCanBuildFrom: Rep[WCanBuildFromCompanionAbs]
  implicit def proxyWCanBuildFromCompanion(p: Rep[WCanBuildFromCompanion]): WCanBuildFromCompanion = {
    proxyOps[WCanBuildFromCompanion](p)
  }

  // default wrapper implementation
  abstract class WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](val wrappedValueOfBaseType: Rep[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]])(implicit val eeFrom: Elem[From], val eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], val eeWCanBuildFromsTo: Elem[WCanBuildFromsTo]) extends WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo] {
  }
  trait WCanBuildFromImplCompanion
  // elem for concrete class
  class WCanBuildFromImplElem[From, WCanBuildFromsElem, WCanBuildFromsTo](val iso: Iso[WCanBuildFromImplData[From, WCanBuildFromsElem, WCanBuildFromsTo], WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]])(implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo])
    extends WCanBuildFromElem[From, WCanBuildFromsElem, WCanBuildFromsTo, WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]]
    with ConcreteElem[WCanBuildFromImplData[From, WCanBuildFromsElem, WCanBuildFromsTo], WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]] {
    lazy val eTo = this
    override def convertWCanBuildFrom(x: Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]) = WCanBuildFromImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagFrom = eeFrom.tag
      implicit val tagWCanBuildFromsElem = eeWCanBuildFromsElem.tag
      implicit val tagWCanBuildFromsTo = eeWCanBuildFromsTo.tag
      weakTypeTag[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]]
    }
  }

  // state representation type
  type WCanBuildFromImplData[From, WCanBuildFromsElem, WCanBuildFromsTo] = CanBuildFrom[From,WCanBuildFromsElem,WCanBuildFromsTo]

  // 3) Iso for concrete class
  class WCanBuildFromImplIso[From, WCanBuildFromsElem, WCanBuildFromsTo](implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo])
    extends Iso[WCanBuildFromImplData[From, WCanBuildFromsElem, WCanBuildFromsTo], WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]] {
    override def from(p: Rep[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[CanBuildFrom[From,WCanBuildFromsElem,WCanBuildFromsTo]]) = {
      val wrappedValueOfBaseType = p
      WCanBuildFromImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]] = WCanBuildFromImpl(DefaultOfCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo].value)
    lazy val eTo = new WCanBuildFromImplElem[From, WCanBuildFromsElem, WCanBuildFromsTo](this)
  }
  // 4) constructor and deconstructor
  abstract class WCanBuildFromImplCompanionAbs extends CompanionBase[WCanBuildFromImplCompanionAbs] with WCanBuildFromImplCompanion {
    override def toString = "WCanBuildFromImpl"

    def apply[From, WCanBuildFromsElem, WCanBuildFromsTo](wrappedValueOfBaseType: Rep[CanBuildFrom[From,WCanBuildFromsElem,WCanBuildFromsTo]])(implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo]): Rep[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]] =
      mkWCanBuildFromImpl(wrappedValueOfBaseType)
  }
  object WCanBuildFromImplMatcher {
    def unapply[From, WCanBuildFromsElem, WCanBuildFromsTo](p: Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]) = unmkWCanBuildFromImpl(p)
  }
  def WCanBuildFromImpl: Rep[WCanBuildFromImplCompanionAbs]
  implicit def proxyWCanBuildFromImplCompanion(p: Rep[WCanBuildFromImplCompanionAbs]): WCanBuildFromImplCompanionAbs = {
    proxyOps[WCanBuildFromImplCompanionAbs](p)
  }

  implicit case object WCanBuildFromImplCompanionElem extends CompanionElem[WCanBuildFromImplCompanionAbs] {
    lazy val tag = weakTypeTag[WCanBuildFromImplCompanionAbs]
    protected def getDefaultRep = WCanBuildFromImpl
  }

  implicit def proxyWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](p: Rep[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]]): WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo] =
    proxyOps[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]](p)

  implicit class ExtendedWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](p: Rep[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]])(implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo]) {
    def toData: Rep[WCanBuildFromImplData[From, WCanBuildFromsElem, WCanBuildFromsTo]] = isoWCanBuildFromImpl(eeFrom, eeWCanBuildFromsElem, eeWCanBuildFromsTo).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo]): Iso[WCanBuildFromImplData[From, WCanBuildFromsElem, WCanBuildFromsTo], WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]] =
    new WCanBuildFromImplIso[From, WCanBuildFromsElem, WCanBuildFromsTo]

  // 6) smart constructor and deconstructor
  def mkWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](wrappedValueOfBaseType: Rep[CanBuildFrom[From,WCanBuildFromsElem,WCanBuildFromsTo]])(implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo]): Rep[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]]
  def unmkWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](p: Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]): Option[(Rep[CanBuildFrom[From,WCanBuildFromsElem,WCanBuildFromsTo]])]
}

// Exp -----------------------------------
trait WCanBuildFromsExp extends WCanBuildFromsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WCanBuildFrom: Rep[WCanBuildFromCompanionAbs] = new WCanBuildFromCompanionAbs with UserTypeDef[WCanBuildFromCompanionAbs] {
    lazy val selfType = element[WCanBuildFromCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  implicit def canBuildFromElement[From:Elem, WCanBuildFromsElem:Elem, WCanBuildFromsTo:Elem]: Elem[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] = {
     implicit val wFrom = element[From].tag;implicit val wWCanBuildFromsElem = element[WCanBuildFromsElem].tag;implicit val wWCanBuildFromsTo = element[WCanBuildFromsTo].tag;
     new ExpBaseElemEx[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo], WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]](element[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]])(weakTypeTag[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]], DefaultOfCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo])
  }

  case class ExpWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]
      (override val wrappedValueOfBaseType: Rep[CanBuildFrom[From,WCanBuildFromsElem,WCanBuildFromsTo]])
      (implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo])
    extends WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](wrappedValueOfBaseType) with UserTypeDef[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]] {
    lazy val selfType = element[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]]
    override def mirror(t: Transformer) = ExpWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](t(wrappedValueOfBaseType))
  }

  lazy val WCanBuildFromImpl: Rep[WCanBuildFromImplCompanionAbs] = new WCanBuildFromImplCompanionAbs with UserTypeDef[WCanBuildFromImplCompanionAbs] {
    lazy val selfType = element[WCanBuildFromImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WCanBuildFromImplMethods {
  }

  def mkWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]
    (wrappedValueOfBaseType: Rep[CanBuildFrom[From,WCanBuildFromsElem,WCanBuildFromsTo]])(implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo]): Rep[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]] =
    new ExpWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](wrappedValueOfBaseType)
  def unmkWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](p: Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WCanBuildFromImplElem[From, WCanBuildFromsElem, WCanBuildFromsTo] @unchecked =>
      Some((p.asRep[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WCanBuildFromMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] forSome {type From; type WCanBuildFromsElem; type WCanBuildFromsTo}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WCanBuildFromElem[_, _, _, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] forSome {type From; type WCanBuildFromsElem; type WCanBuildFromsTo}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] forSome {type From; type WCanBuildFromsElem; type WCanBuildFromsTo}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WCanBuildFromCompanionMethods {
  }
}
