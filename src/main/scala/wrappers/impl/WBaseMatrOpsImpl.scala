package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scalanizer.linalgebra.BaseMatrOp
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WBaseMatrOpsAbs extends WBaseMatrOps with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWBaseMatrOp(p: Rep[WBaseMatrOp]): WBaseMatrOp = {
    proxyOps[WBaseMatrOp](p)(scala.reflect.classTag[WBaseMatrOp])
  }

  // TypeWrapper proxy
  //implicit def proxyBaseMatrOp(p: Rep[BaseMatrOp]): WBaseMatrOp =
  //  proxyOps[WBaseMatrOp](p.asRep[WBaseMatrOp])

  implicit def unwrapValueOfWBaseMatrOp(w: Rep[WBaseMatrOp]): Rep[BaseMatrOp] = w.wrappedValueOfBaseType

  implicit def baseMatrOpElement: Elem[BaseMatrOp]

  // familyElem
  abstract class WBaseMatrOpElem[To <: WBaseMatrOp]
    extends WrapperElem[BaseMatrOp, To] {
    override def isEntityType = true
    override lazy val tag = {
      weakTypeTag[WBaseMatrOp].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WBaseMatrOp] => convertWBaseMatrOp(x) }
      tryConvert(element[WBaseMatrOp], this, x, conv)
    }

    def convertWBaseMatrOp(x : Rep[WBaseMatrOp]): Rep[To] = {
      assert(x.selfType1 match { case _: WBaseMatrOpElem[_] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wBaseMatrOpElement: Elem[WBaseMatrOp] =
    new WBaseMatrOpElem[WBaseMatrOp] {
      lazy val eTo = element[WBaseMatrOpImpl]
    }

  implicit case object WBaseMatrOpCompanionElem extends CompanionElem[WBaseMatrOpCompanionAbs] {
    lazy val tag = weakTypeTag[WBaseMatrOpCompanionAbs]
    protected def getDefaultRep = WBaseMatrOp
  }

  abstract class WBaseMatrOpCompanionAbs extends CompanionBase[WBaseMatrOpCompanionAbs] with WBaseMatrOpCompanion {
    override def toString = "WBaseMatrOp"
  }
  def WBaseMatrOp: Rep[WBaseMatrOpCompanionAbs]
  implicit def proxyWBaseMatrOpCompanion(p: Rep[WBaseMatrOpCompanion]): WBaseMatrOpCompanion = {
    proxyOps[WBaseMatrOpCompanion](p)
  }

  // default wrapper implementation
  abstract class WBaseMatrOpImpl(val wrappedValueOfBaseType: Rep[BaseMatrOp]) extends WBaseMatrOp {
    def mvm[T]( matrix: Rep[WMatr[T]], vector: Rep[WVec[T]])( n: Rep[WNum[T]], m: Rep[WNumMonoid[T]])(implicit emT: Elem[T]): Rep[WVec[T]] =
      methodCallEx[WVec[T]](self,
        this.getClass.getMethod("mvm", classOf[AnyRef], classOf[AnyRef], classOf[AnyRef], classOf[AnyRef], classOf[AnyRef]),
        List(matrix.asInstanceOf[AnyRef], vector.asInstanceOf[AnyRef], n.asInstanceOf[AnyRef], m.asInstanceOf[AnyRef], emT.asInstanceOf[AnyRef]))
  }
  trait WBaseMatrOpImplCompanion
  // elem for concrete class
  class WBaseMatrOpImplElem(val iso: Iso[WBaseMatrOpImplData, WBaseMatrOpImpl])
    extends WBaseMatrOpElem[WBaseMatrOpImpl]
    with ConcreteElem[WBaseMatrOpImplData, WBaseMatrOpImpl] {
    lazy val eTo = this
    override def convertWBaseMatrOp(x: Rep[WBaseMatrOp]) = WBaseMatrOpImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      weakTypeTag[WBaseMatrOpImpl]
    }
  }

  // state representation type
  type WBaseMatrOpImplData = BaseMatrOp

  // 3) Iso for concrete class
  class WBaseMatrOpImplIso
    extends Iso[WBaseMatrOpImplData, WBaseMatrOpImpl] {
    override def from(p: Rep[WBaseMatrOpImpl]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[BaseMatrOp]) = {
      val wrappedValueOfBaseType = p
      WBaseMatrOpImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WBaseMatrOpImpl] = WBaseMatrOpImpl(DefaultOfBaseMatrOp.value)
    lazy val eTo = new WBaseMatrOpImplElem(this)
  }
  // 4) constructor and deconstructor
  abstract class WBaseMatrOpImplCompanionAbs extends CompanionBase[WBaseMatrOpImplCompanionAbs] with WBaseMatrOpImplCompanion {
    override def toString = "WBaseMatrOpImpl"

    def apply(wrappedValueOfBaseType: Rep[BaseMatrOp]): Rep[WBaseMatrOpImpl] =
      mkWBaseMatrOpImpl(wrappedValueOfBaseType)
  }
  object WBaseMatrOpImplMatcher {
    def unapply(p: Rep[WBaseMatrOp]) = unmkWBaseMatrOpImpl(p)
  }
  def WBaseMatrOpImpl: Rep[WBaseMatrOpImplCompanionAbs]
  implicit def proxyWBaseMatrOpImplCompanion(p: Rep[WBaseMatrOpImplCompanionAbs]): WBaseMatrOpImplCompanionAbs = {
    proxyOps[WBaseMatrOpImplCompanionAbs](p)
  }

  implicit case object WBaseMatrOpImplCompanionElem extends CompanionElem[WBaseMatrOpImplCompanionAbs] {
    lazy val tag = weakTypeTag[WBaseMatrOpImplCompanionAbs]
    protected def getDefaultRep = WBaseMatrOpImpl
  }

  implicit def proxyWBaseMatrOpImpl(p: Rep[WBaseMatrOpImpl]): WBaseMatrOpImpl =
    proxyOps[WBaseMatrOpImpl](p)

  implicit class ExtendedWBaseMatrOpImpl(p: Rep[WBaseMatrOpImpl]) {
    def toData: Rep[WBaseMatrOpImplData] = isoWBaseMatrOpImpl.from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWBaseMatrOpImpl: Iso[WBaseMatrOpImplData, WBaseMatrOpImpl] =
    new WBaseMatrOpImplIso

  // 6) smart constructor and deconstructor
  def mkWBaseMatrOpImpl(wrappedValueOfBaseType: Rep[BaseMatrOp]): Rep[WBaseMatrOpImpl]
  def unmkWBaseMatrOpImpl(p: Rep[WBaseMatrOp]): Option[(Rep[BaseMatrOp])]
}

// Exp -----------------------------------
trait WBaseMatrOpsExp extends WBaseMatrOpsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WBaseMatrOp: Rep[WBaseMatrOpCompanionAbs] = new WBaseMatrOpCompanionAbs with UserTypeDef[WBaseMatrOpCompanionAbs] {
    lazy val selfType = element[WBaseMatrOpCompanionAbs]
    override def mirror(t: Transformer) = this

    def apply: Rep[WBaseMatrOp] =
      newObjEx(classOf[WBaseMatrOp], List())
  }

  implicit lazy val baseMatrOpElement: Elem[BaseMatrOp] = new ExpBaseElemEx[BaseMatrOp, WBaseMatrOp](element[WBaseMatrOp])(weakTypeTag[BaseMatrOp], DefaultOfBaseMatrOp)

  case class ExpWBaseMatrOpImpl
      (override val wrappedValueOfBaseType: Rep[BaseMatrOp])

    extends WBaseMatrOpImpl(wrappedValueOfBaseType) with UserTypeDef[WBaseMatrOpImpl] {
    lazy val selfType = element[WBaseMatrOpImpl]
    override def mirror(t: Transformer) = ExpWBaseMatrOpImpl(t(wrappedValueOfBaseType))
  }

  lazy val WBaseMatrOpImpl: Rep[WBaseMatrOpImplCompanionAbs] = new WBaseMatrOpImplCompanionAbs with UserTypeDef[WBaseMatrOpImplCompanionAbs] {
    lazy val selfType = element[WBaseMatrOpImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WBaseMatrOpImplMethods {
  }

  def mkWBaseMatrOpImpl
    (wrappedValueOfBaseType: Rep[BaseMatrOp]): Rep[WBaseMatrOpImpl] =
    new ExpWBaseMatrOpImpl(wrappedValueOfBaseType)
  def unmkWBaseMatrOpImpl(p: Rep[WBaseMatrOp]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WBaseMatrOpImplElem @unchecked =>
      Some((p.asRep[WBaseMatrOpImpl].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WBaseMatrOpMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WBaseMatrOp]] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WBaseMatrOpElem[_]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WBaseMatrOp]]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WBaseMatrOp]] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object mvm {
      def unapply(d: Def[_]): Option[(Rep[WBaseMatrOp], Rep[WMatr[T]], Rep[WVec[T]], Rep[WNum[T]], Rep[WNumMonoid[T]], Elem[T]) forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(matrix, vector, n, m, emT, _*), _) if receiver.elem.isInstanceOf[WBaseMatrOpElem[_]] && method.getName == "mvm" =>
          Some((receiver, matrix, vector, n, m, emT)).asInstanceOf[Option[(Rep[WBaseMatrOp], Rep[WMatr[T]], Rep[WVec[T]], Rep[WNum[T]], Rep[WNumMonoid[T]], Elem[T]) forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[WBaseMatrOp], Rep[WMatr[T]], Rep[WVec[T]], Rep[WNum[T]], Rep[WNumMonoid[T]], Elem[T]) forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WBaseMatrOpCompanionMethods {
    object apply {
      def unapply(d: Def[_]): Option[Unit] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem == WBaseMatrOpCompanionElem && method.getName == "apply" =>
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
