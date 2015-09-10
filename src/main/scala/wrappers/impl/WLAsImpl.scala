package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scalanizer.linalgebra.LA
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WLAsAbs extends WLAs with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWLA(p: Rep[WLA]): WLA = {
    proxyOps[WLA](p)(scala.reflect.classTag[WLA])
  }

  // TypeWrapper proxy
  //implicit def proxyLA(p: Rep[LA]): WLA =
  //  proxyOps[WLA](p.asRep[WLA])

  implicit def unwrapValueOfWLA(w: Rep[WLA]): Rep[LA] = w.wrappedValueOfBaseType

  implicit def lAElement: Elem[LA]

  // familyElem
  abstract class WLAElem[To <: WLA]
    extends WrapperElem[LA, To] {
    override def isEntityType = true
    override lazy val tag = {
      weakTypeTag[WLA].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WLA] => convertWLA(x) }
      tryConvert(element[WLA], this, x, conv)
    }

    def convertWLA(x : Rep[WLA]): Rep[To] = {
      assert(x.selfType1 match { case _: WLAElem[_] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wLAElement: Elem[WLA] =
    new WLAElem[WLA] {
      lazy val eTo = element[WLAImpl]
    }

  implicit case object WLACompanionElem extends CompanionElem[WLACompanionAbs] {
    lazy val tag = weakTypeTag[WLACompanionAbs]
    protected def getDefaultRep = WLA
  }

  abstract class WLACompanionAbs extends CompanionBase[WLACompanionAbs] with WLACompanion {
    override def toString = "WLA"
  }
  def WLA: Rep[WLACompanionAbs]
  implicit def proxyWLACompanion(p: Rep[WLACompanion]): WLACompanion = {
    proxyOps[WLACompanion](p)
  }

  // default wrapper implementation
  abstract class WLAImpl(val wrappedValueOfBaseType: Rep[LA]) extends WLA {
    def mvm[T]( matrix: Rep[WMatr[T]], vector: Rep[WVec[T]])( n: Rep[WNum[T]], m: Rep[WNumMonoid[T]])(implicit emT: Elem[T]): Rep[WVec[T]] =
      methodCallEx[WVec[T]](self,
        this.getClass.getMethod("mvm", classOf[AnyRef], classOf[AnyRef], classOf[AnyRef], classOf[AnyRef], classOf[AnyRef]),
        List(matrix.asInstanceOf[AnyRef], vector.asInstanceOf[AnyRef], n.asInstanceOf[AnyRef], m.asInstanceOf[AnyRef], emT.asInstanceOf[AnyRef]))
  }
  trait WLAImplCompanion
  // elem for concrete class
  class WLAImplElem(val iso: Iso[WLAImplData, WLAImpl])
    extends WLAElem[WLAImpl]
    with ConcreteElem[WLAImplData, WLAImpl] {
    lazy val eTo = this
    override def convertWLA(x: Rep[WLA]) = WLAImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      weakTypeTag[WLAImpl]
    }
  }

  // state representation type
  type WLAImplData = LA

  // 3) Iso for concrete class
  class WLAImplIso
    extends Iso[WLAImplData, WLAImpl] {
    override def from(p: Rep[WLAImpl]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[LA]) = {
      val wrappedValueOfBaseType = p
      WLAImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WLAImpl] = WLAImpl(DefaultOfLA.value)
    lazy val eTo = new WLAImplElem(this)
  }
  // 4) constructor and deconstructor
  abstract class WLAImplCompanionAbs extends CompanionBase[WLAImplCompanionAbs] with WLAImplCompanion {
    override def toString = "WLAImpl"

    def apply(wrappedValueOfBaseType: Rep[LA]): Rep[WLAImpl] =
      mkWLAImpl(wrappedValueOfBaseType)
  }
  object WLAImplMatcher {
    def unapply(p: Rep[WLA]) = unmkWLAImpl(p)
  }
  def WLAImpl: Rep[WLAImplCompanionAbs]
  implicit def proxyWLAImplCompanion(p: Rep[WLAImplCompanionAbs]): WLAImplCompanionAbs = {
    proxyOps[WLAImplCompanionAbs](p)
  }

  implicit case object WLAImplCompanionElem extends CompanionElem[WLAImplCompanionAbs] {
    lazy val tag = weakTypeTag[WLAImplCompanionAbs]
    protected def getDefaultRep = WLAImpl
  }

  implicit def proxyWLAImpl(p: Rep[WLAImpl]): WLAImpl =
    proxyOps[WLAImpl](p)

  implicit class ExtendedWLAImpl(p: Rep[WLAImpl]) {
    def toData: Rep[WLAImplData] = isoWLAImpl.from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWLAImpl: Iso[WLAImplData, WLAImpl] =
    new WLAImplIso

  // 6) smart constructor and deconstructor
  def mkWLAImpl(wrappedValueOfBaseType: Rep[LA]): Rep[WLAImpl]
  def unmkWLAImpl(p: Rep[WLA]): Option[(Rep[LA])]
}

// Seq -----------------------------------
trait WLAsSeq extends WLAsDsl with ScalanSeq {
  self: WrappersDslSeq =>
  lazy val WLA: Rep[WLACompanionAbs] = new WLACompanionAbs with UserTypeSeq[WLACompanionAbs] {
    lazy val selfType = element[WLACompanionAbs]

    override def apply: Rep[WLA] =
      WLAImpl(new LA)
  }

    // override proxy if we deal with TypeWrapper
  //override def proxyLA(p: Rep[LA]): WLA =
  //  proxyOpsEx[LA,WLA, SeqWLAImpl](p, bt => SeqWLAImpl(bt))

    implicit lazy val lAElement: Elem[LA] = new SeqBaseElemEx[LA, WLA](element[WLA])(weakTypeTag[LA], DefaultOfLA)

  case class SeqWLAImpl
      (override val wrappedValueOfBaseType: Rep[LA])

    extends WLAImpl(wrappedValueOfBaseType)
        with UserTypeSeq[WLAImpl] {
    lazy val selfType = element[WLAImpl]
    override def mvm[T]( matrix: Rep[WMatr[T]], vector: Rep[WVec[T]])( n: Rep[WNum[T]], m: Rep[WNumMonoid[T]])(implicit emT: Elem[T]): Rep[WVec[T]] =
      wrappedValueOfBaseType.mvm[T](matrix, vector)(n, m)(emT.classTag)
  }
  lazy val WLAImpl = new WLAImplCompanionAbs with UserTypeSeq[WLAImplCompanionAbs] {
    lazy val selfType = element[WLAImplCompanionAbs]
  }

  def mkWLAImpl
      (wrappedValueOfBaseType: Rep[LA]): Rep[WLAImpl] =
      new SeqWLAImpl(wrappedValueOfBaseType)
  def unmkWLAImpl(p: Rep[WLA]) = p match {
    case p: WLAImpl @unchecked =>
      Some((p.wrappedValueOfBaseType))
    case _ => None
  }

  implicit def wrapLAToWLA(v: LA): WLA = WLAImpl(v)
}

// Exp -----------------------------------
trait WLAsExp extends WLAsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WLA: Rep[WLACompanionAbs] = new WLACompanionAbs with UserTypeDef[WLACompanionAbs] {
    lazy val selfType = element[WLACompanionAbs]
    override def mirror(t: Transformer) = this

    def apply: Rep[WLA] =
      newObjEx(classOf[WLA], List())
  }

  implicit lazy val lAElement: Elem[LA] = new ExpBaseElemEx[LA, WLA](element[WLA])(weakTypeTag[LA], DefaultOfLA)

  case class ExpWLAImpl
      (override val wrappedValueOfBaseType: Rep[LA])

    extends WLAImpl(wrappedValueOfBaseType) with UserTypeDef[WLAImpl] {
    lazy val selfType = element[WLAImpl]
    override def mirror(t: Transformer) = ExpWLAImpl(t(wrappedValueOfBaseType))
  }

  lazy val WLAImpl: Rep[WLAImplCompanionAbs] = new WLAImplCompanionAbs with UserTypeDef[WLAImplCompanionAbs] {
    lazy val selfType = element[WLAImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WLAImplMethods {
  }

  def mkWLAImpl
    (wrappedValueOfBaseType: Rep[LA]): Rep[WLAImpl] =
    new ExpWLAImpl(wrappedValueOfBaseType)
  def unmkWLAImpl(p: Rep[WLA]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WLAImplElem @unchecked =>
      Some((p.asRep[WLAImpl].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WLAMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WLA]] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WLAElem[_]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WLA]]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WLA]] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object mvm {
      def unapply(d: Def[_]): Option[(Rep[WLA], Rep[WMatr[T]], Rep[WVec[T]], Rep[WNum[T]], Rep[WNumMonoid[T]], Elem[T]) forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(matrix, vector, n, m, emT, _*), _) if receiver.elem.isInstanceOf[WLAElem[_]] && method.getName == "mvm" =>
          Some((receiver, matrix, vector, n, m, emT)).asInstanceOf[Option[(Rep[WLA], Rep[WMatr[T]], Rep[WVec[T]], Rep[WNum[T]], Rep[WNumMonoid[T]], Elem[T]) forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[WLA], Rep[WMatr[T]], Rep[WVec[T]], Rep[WNum[T]], Rep[WNumMonoid[T]], Elem[T]) forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WLACompanionMethods {
    object apply {
      def unapply(d: Def[_]): Option[Unit] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem == WLACompanionElem && method.getName == "apply" =>
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
