package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scalanizer.linalgebra.DenseMatr
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WDenseMatrsAbs extends WDenseMatrs with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWDenseMatr[T](p: Rep[WDenseMatr[T]]): WDenseMatr[T] = {
    proxyOps[WDenseMatr[T]](p)(scala.reflect.classTag[WDenseMatr[T]])
  }

  // TypeWrapper proxy
  //implicit def proxyDenseMatr[T:Elem](p: Rep[DenseMatr[T]]): WDenseMatr[T] =
  //  proxyOps[WDenseMatr[T]](p.asRep[WDenseMatr[T]])

  implicit def unwrapValueOfWDenseMatr[T](w: Rep[WDenseMatr[T]]): Rep[DenseMatr[T]] = w.wrappedValueOfBaseType

  implicit def denseMatrElement[T:Elem]: Elem[DenseMatr[T]]

  // familyElem
  abstract class WDenseMatrElem[T, To <: WDenseMatr[T]](implicit val eeT: Elem[T])
    extends WrapperElem[DenseMatr[T], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WDenseMatr[T]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WDenseMatr[T]] => convertWDenseMatr(x) }
      tryConvert(element[WDenseMatr[T]], this, x, conv)
    }

    def convertWDenseMatr(x : Rep[WDenseMatr[T]]): Rep[To] = {
      assert(x.selfType1 match { case _: WDenseMatrElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wDenseMatrElement[T](implicit eeT: Elem[T]): Elem[WDenseMatr[T]] =
    new WDenseMatrElem[T, WDenseMatr[T]] {
      lazy val eTo = element[WDenseMatrImpl[T]]
    }

  implicit case object WDenseMatrCompanionElem extends CompanionElem[WDenseMatrCompanionAbs] {
    lazy val tag = weakTypeTag[WDenseMatrCompanionAbs]
    protected def getDefaultRep = WDenseMatr
  }

  abstract class WDenseMatrCompanionAbs extends CompanionBase[WDenseMatrCompanionAbs] with WDenseMatrCompanion {
    override def toString = "WDenseMatr"
  }
  def WDenseMatr: Rep[WDenseMatrCompanionAbs]
  implicit def proxyWDenseMatrCompanion(p: Rep[WDenseMatrCompanion]): WDenseMatrCompanion = {
    proxyOps[WDenseMatrCompanion](p)
  }

  // default wrapper implementation
  abstract class WDenseMatrImpl[T](val wrappedValueOfBaseType: Rep[DenseMatr[T]])(implicit val eeT: Elem[T]) extends WDenseMatr[T] {
  }
  trait WDenseMatrImplCompanion
  // elem for concrete class
  class WDenseMatrImplElem[T](val iso: Iso[WDenseMatrImplData[T], WDenseMatrImpl[T]])(implicit eeT: Elem[T])
    extends WDenseMatrElem[T, WDenseMatrImpl[T]]
    with ConcreteElem[WDenseMatrImplData[T], WDenseMatrImpl[T]] {
    lazy val eTo = this
    override def convertWDenseMatr(x: Rep[WDenseMatr[T]]) = WDenseMatrImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WDenseMatrImpl[T]]
    }
  }

  // state representation type
  type WDenseMatrImplData[T] = DenseMatr[T]

  // 3) Iso for concrete class
  class WDenseMatrImplIso[T](implicit eeT: Elem[T])
    extends Iso[WDenseMatrImplData[T], WDenseMatrImpl[T]] {
    override def from(p: Rep[WDenseMatrImpl[T]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[DenseMatr[T]]) = {
      val wrappedValueOfBaseType = p
      WDenseMatrImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WDenseMatrImpl[T]] = WDenseMatrImpl(DefaultOfDenseMatr[T].value)
    lazy val eTo = new WDenseMatrImplElem[T](this)
  }
  // 4) constructor and deconstructor
  abstract class WDenseMatrImplCompanionAbs extends CompanionBase[WDenseMatrImplCompanionAbs] with WDenseMatrImplCompanion {
    override def toString = "WDenseMatrImpl"

    def apply[T](wrappedValueOfBaseType: Rep[DenseMatr[T]])(implicit eeT: Elem[T]): Rep[WDenseMatrImpl[T]] =
      mkWDenseMatrImpl(wrappedValueOfBaseType)
  }
  object WDenseMatrImplMatcher {
    def unapply[T](p: Rep[WDenseMatr[T]]) = unmkWDenseMatrImpl(p)
  }
  def WDenseMatrImpl: Rep[WDenseMatrImplCompanionAbs]
  implicit def proxyWDenseMatrImplCompanion(p: Rep[WDenseMatrImplCompanionAbs]): WDenseMatrImplCompanionAbs = {
    proxyOps[WDenseMatrImplCompanionAbs](p)
  }

  implicit case object WDenseMatrImplCompanionElem extends CompanionElem[WDenseMatrImplCompanionAbs] {
    lazy val tag = weakTypeTag[WDenseMatrImplCompanionAbs]
    protected def getDefaultRep = WDenseMatrImpl
  }

  implicit def proxyWDenseMatrImpl[T](p: Rep[WDenseMatrImpl[T]]): WDenseMatrImpl[T] =
    proxyOps[WDenseMatrImpl[T]](p)

  implicit class ExtendedWDenseMatrImpl[T](p: Rep[WDenseMatrImpl[T]])(implicit eeT: Elem[T]) {
    def toData: Rep[WDenseMatrImplData[T]] = isoWDenseMatrImpl(eeT).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWDenseMatrImpl[T](implicit eeT: Elem[T]): Iso[WDenseMatrImplData[T], WDenseMatrImpl[T]] =
    new WDenseMatrImplIso[T]

  // 6) smart constructor and deconstructor
  def mkWDenseMatrImpl[T](wrappedValueOfBaseType: Rep[DenseMatr[T]])(implicit eeT: Elem[T]): Rep[WDenseMatrImpl[T]]
  def unmkWDenseMatrImpl[T](p: Rep[WDenseMatr[T]]): Option[(Rep[DenseMatr[T]])]
}

// Exp -----------------------------------
trait WDenseMatrsExp extends WDenseMatrsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WDenseMatr: Rep[WDenseMatrCompanionAbs] = new WDenseMatrCompanionAbs with UserTypeDef[WDenseMatrCompanionAbs] {
    lazy val selfType = element[WDenseMatrCompanionAbs]
    override def mirror(t: Transformer) = this

    def apply[T]( rows: Rep[WCol[WVec[T]]], numColumns: Rep[Int])(implicit emT: Elem[T]): Rep[WDenseMatr[T]] =
      newObjEx(classOf[WDenseMatr[T]], List(rows.asRep[Any], numColumns.asRep[Any], emT))
  }

  implicit def denseMatrElement[T:Elem]: Elem[DenseMatr[T]] = {
     implicit val wT = element[T].tag;
     new ExpBaseElemEx[DenseMatr[T], WDenseMatr[T]](element[WDenseMatr[T]])(weakTypeTag[DenseMatr[T]], DefaultOfDenseMatr[T])
  }

  case class ExpWDenseMatrImpl[T]
      (override val wrappedValueOfBaseType: Rep[DenseMatr[T]])
      (implicit eeT: Elem[T])
    extends WDenseMatrImpl[T](wrappedValueOfBaseType) with UserTypeDef[WDenseMatrImpl[T]] {
    lazy val selfType = element[WDenseMatrImpl[T]]
    override def mirror(t: Transformer) = ExpWDenseMatrImpl[T](t(wrappedValueOfBaseType))
  }

  lazy val WDenseMatrImpl: Rep[WDenseMatrImplCompanionAbs] = new WDenseMatrImplCompanionAbs with UserTypeDef[WDenseMatrImplCompanionAbs] {
    lazy val selfType = element[WDenseMatrImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WDenseMatrImplMethods {
  }

  def mkWDenseMatrImpl[T]
    (wrappedValueOfBaseType: Rep[DenseMatr[T]])(implicit eeT: Elem[T]): Rep[WDenseMatrImpl[T]] =
    new ExpWDenseMatrImpl[T](wrappedValueOfBaseType)
  def unmkWDenseMatrImpl[T](p: Rep[WDenseMatr[T]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WDenseMatrImplElem[T] @unchecked =>
      Some((p.asRep[WDenseMatrImpl[T]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WDenseMatrMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WDenseMatr[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WDenseMatrElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WDenseMatr[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WDenseMatr[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WDenseMatrCompanionMethods {
    object apply {
      def unapply(d: Def[_]): Option[(Rep[WCol[WVec[T]]], Rep[Int], Elem[T]) forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(rows, numColumns, emT, _*), _) if receiver.elem == WDenseMatrCompanionElem && method.getName == "apply" =>
          Some((rows, numColumns, emT)).asInstanceOf[Option[(Rep[WCol[WVec[T]]], Rep[Int], Elem[T]) forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[WCol[WVec[T]]], Rep[Int], Elem[T]) forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }
}
