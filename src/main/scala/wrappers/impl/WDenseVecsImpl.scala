package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scalanizer.linalgebra.DenseVec
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WDenseVecsAbs extends WDenseVecs with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWDenseVec[T](p: Rep[WDenseVec[T]]): WDenseVec[T] = {
    proxyOps[WDenseVec[T]](p)(scala.reflect.classTag[WDenseVec[T]])
  }

  // TypeWrapper proxy
  //implicit def proxyDenseVec[T:Elem](p: Rep[DenseVec[T]]): WDenseVec[T] =
  //  proxyOps[WDenseVec[T]](p.asRep[WDenseVec[T]])

  implicit def unwrapValueOfWDenseVec[T](w: Rep[WDenseVec[T]]): Rep[DenseVec[T]] = w.wrappedValueOfBaseType

  implicit def denseVecElement[T:Elem]: Elem[DenseVec[T]]

  // familyElem
  abstract class WDenseVecElem[T, To <: WDenseVec[T]](implicit val eeT: Elem[T])
    extends WrapperElem[DenseVec[T], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WDenseVec[T]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WDenseVec[T]] => convertWDenseVec(x) }
      tryConvert(element[WDenseVec[T]], this, x, conv)
    }

    def convertWDenseVec(x : Rep[WDenseVec[T]]): Rep[To] = {
      assert(x.selfType1 match { case _: WDenseVecElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wDenseVecElement[T](implicit eeT: Elem[T]): Elem[WDenseVec[T]] =
    new WDenseVecElem[T, WDenseVec[T]] {
      lazy val eTo = element[WDenseVecImpl[T]]
    }

  implicit case object WDenseVecCompanionElem extends CompanionElem[WDenseVecCompanionAbs] {
    lazy val tag = weakTypeTag[WDenseVecCompanionAbs]
    protected def getDefaultRep = WDenseVec
  }

  abstract class WDenseVecCompanionAbs extends CompanionBase[WDenseVecCompanionAbs] with WDenseVecCompanion {
    override def toString = "WDenseVec"
  }
  def WDenseVec: Rep[WDenseVecCompanionAbs]
  implicit def proxyWDenseVecCompanion(p: Rep[WDenseVecCompanion]): WDenseVecCompanion = {
    proxyOps[WDenseVecCompanion](p)
  }

  // default wrapper implementation
  abstract class WDenseVecImpl[T](val wrappedValueOfBaseType: Rep[DenseVec[T]])(implicit val eeT: Elem[T]) extends WDenseVec[T] {
  }
  trait WDenseVecImplCompanion
  // elem for concrete class
  class WDenseVecImplElem[T](val iso: Iso[WDenseVecImplData[T], WDenseVecImpl[T]])(implicit eeT: Elem[T])
    extends WDenseVecElem[T, WDenseVecImpl[T]]
    with ConcreteElem[WDenseVecImplData[T], WDenseVecImpl[T]] {
    lazy val eTo = this
    override def convertWDenseVec(x: Rep[WDenseVec[T]]) = WDenseVecImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WDenseVecImpl[T]]
    }
  }

  // state representation type
  type WDenseVecImplData[T] = DenseVec[T]

  // 3) Iso for concrete class
  class WDenseVecImplIso[T](implicit eeT: Elem[T])
    extends Iso[WDenseVecImplData[T], WDenseVecImpl[T]] {
    override def from(p: Rep[WDenseVecImpl[T]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[DenseVec[T]]) = {
      val wrappedValueOfBaseType = p
      WDenseVecImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WDenseVecImpl[T]] = WDenseVecImpl(DefaultOfDenseVec[T].value)
    lazy val eTo = new WDenseVecImplElem[T](this)
  }
  // 4) constructor and deconstructor
  abstract class WDenseVecImplCompanionAbs extends CompanionBase[WDenseVecImplCompanionAbs] with WDenseVecImplCompanion {
    override def toString = "WDenseVecImpl"

    def apply[T](wrappedValueOfBaseType: Rep[DenseVec[T]])(implicit eeT: Elem[T]): Rep[WDenseVecImpl[T]] =
      mkWDenseVecImpl(wrappedValueOfBaseType)
  }
  object WDenseVecImplMatcher {
    def unapply[T](p: Rep[WDenseVec[T]]) = unmkWDenseVecImpl(p)
  }
  def WDenseVecImpl: Rep[WDenseVecImplCompanionAbs]
  implicit def proxyWDenseVecImplCompanion(p: Rep[WDenseVecImplCompanionAbs]): WDenseVecImplCompanionAbs = {
    proxyOps[WDenseVecImplCompanionAbs](p)
  }

  implicit case object WDenseVecImplCompanionElem extends CompanionElem[WDenseVecImplCompanionAbs] {
    lazy val tag = weakTypeTag[WDenseVecImplCompanionAbs]
    protected def getDefaultRep = WDenseVecImpl
  }

  implicit def proxyWDenseVecImpl[T](p: Rep[WDenseVecImpl[T]]): WDenseVecImpl[T] =
    proxyOps[WDenseVecImpl[T]](p)

  implicit class ExtendedWDenseVecImpl[T](p: Rep[WDenseVecImpl[T]])(implicit eeT: Elem[T]) {
    def toData: Rep[WDenseVecImplData[T]] = isoWDenseVecImpl(eeT).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWDenseVecImpl[T](implicit eeT: Elem[T]): Iso[WDenseVecImplData[T], WDenseVecImpl[T]] =
    new WDenseVecImplIso[T]

  // 6) smart constructor and deconstructor
  def mkWDenseVecImpl[T](wrappedValueOfBaseType: Rep[DenseVec[T]])(implicit eeT: Elem[T]): Rep[WDenseVecImpl[T]]
  def unmkWDenseVecImpl[T](p: Rep[WDenseVec[T]]): Option[(Rep[DenseVec[T]])]
}

// Exp -----------------------------------
trait WDenseVecsExp extends WDenseVecsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WDenseVec: Rep[WDenseVecCompanionAbs] = new WDenseVecCompanionAbs with UserTypeDef[WDenseVecCompanionAbs] {
    lazy val selfType = element[WDenseVecCompanionAbs]
    override def mirror(t: Transformer) = this

    def apply[T]( items: Rep[WCol[T]])(implicit emT: Elem[T]): Rep[WDenseVec[T]] =
      newObjEx(classOf[WDenseVec[T]], List(items.asRep[Any], emT))
  }

  implicit def denseVecElement[T:Elem]: Elem[DenseVec[T]] = {
     implicit val wT = element[T].tag;
     new ExpBaseElemEx[DenseVec[T], WDenseVec[T]](element[WDenseVec[T]])(weakTypeTag[DenseVec[T]], DefaultOfDenseVec[T])
  }

  case class ExpWDenseVecImpl[T]
      (override val wrappedValueOfBaseType: Rep[DenseVec[T]])
      (implicit eeT: Elem[T])
    extends WDenseVecImpl[T](wrappedValueOfBaseType) with UserTypeDef[WDenseVecImpl[T]] {
    lazy val selfType = element[WDenseVecImpl[T]]
    override def mirror(t: Transformer) = ExpWDenseVecImpl[T](t(wrappedValueOfBaseType))
  }

  lazy val WDenseVecImpl: Rep[WDenseVecImplCompanionAbs] = new WDenseVecImplCompanionAbs with UserTypeDef[WDenseVecImplCompanionAbs] {
    lazy val selfType = element[WDenseVecImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WDenseVecImplMethods {
  }

  def mkWDenseVecImpl[T]
    (wrappedValueOfBaseType: Rep[DenseVec[T]])(implicit eeT: Elem[T]): Rep[WDenseVecImpl[T]] =
    new ExpWDenseVecImpl[T](wrappedValueOfBaseType)
  def unmkWDenseVecImpl[T](p: Rep[WDenseVec[T]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WDenseVecImplElem[T] @unchecked =>
      Some((p.asRep[WDenseVecImpl[T]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WDenseVecMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WDenseVec[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WDenseVecElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WDenseVec[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WDenseVec[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WDenseVecCompanionMethods {
    object apply {
      def unapply(d: Def[_]): Option[(Rep[WCol[T]], Elem[T]) forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(items, emT, _*), _) if receiver.elem == WDenseVecCompanionElem && method.getName == "apply" =>
          Some((items, emT)).asInstanceOf[Option[(Rep[WCol[T]], Elem[T]) forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[WCol[T]], Elem[T]) forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }
}
