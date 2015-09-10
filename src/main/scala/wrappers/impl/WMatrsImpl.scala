package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scalanizer.linalgebra.Matr
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WMatrsAbs extends WMatrs with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWMatr[T](p: Rep[WMatr[T]]): WMatr[T] = {
    proxyOps[WMatr[T]](p)(scala.reflect.classTag[WMatr[T]])
  }

  // TypeWrapper proxy
  //implicit def proxyMatr[T:Elem](p: Rep[Matr[T]]): WMatr[T] =
  //  proxyOps[WMatr[T]](p.asRep[WMatr[T]])

  implicit def unwrapValueOfWMatr[T](w: Rep[WMatr[T]]): Rep[Matr[T]] = w.wrappedValueOfBaseType

  implicit def matrElement[T:Elem]: Elem[Matr[T]]

  // familyElem
  abstract class WMatrElem[T, To <: WMatr[T]](implicit val eeT: Elem[T])
    extends WrapperElem[Matr[T], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WMatr[T]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WMatr[T]] => convertWMatr(x) }
      tryConvert(element[WMatr[T]], this, x, conv)
    }

    def convertWMatr(x : Rep[WMatr[T]]): Rep[To] = {
      assert(x.selfType1 match { case _: WMatrElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wMatrElement[T](implicit eeT: Elem[T]): Elem[WMatr[T]] =
    new WMatrElem[T, WMatr[T]] {
      lazy val eTo = element[WMatrImpl[T]]
    }

  implicit case object WMatrCompanionElem extends CompanionElem[WMatrCompanionAbs] {
    lazy val tag = weakTypeTag[WMatrCompanionAbs]
    protected def getDefaultRep = WMatr
  }

  abstract class WMatrCompanionAbs extends CompanionBase[WMatrCompanionAbs] with WMatrCompanion {
    override def toString = "WMatr"
  }
  def WMatr: Rep[WMatrCompanionAbs]
  implicit def proxyWMatrCompanion(p: Rep[WMatrCompanion]): WMatrCompanion = {
    proxyOps[WMatrCompanion](p)
  }

  // default wrapper implementation
  abstract class WMatrImpl[T](val wrappedValueOfBaseType: Rep[Matr[T]])(implicit val eeT: Elem[T]) extends WMatr[T] {
    def numRows: Rep[Int] =
      methodCallEx[Int](self,
        this.getClass.getMethod("numRows"),
        List())
  }
  trait WMatrImplCompanion
  // elem for concrete class
  class WMatrImplElem[T](val iso: Iso[WMatrImplData[T], WMatrImpl[T]])(implicit eeT: Elem[T])
    extends WMatrElem[T, WMatrImpl[T]]
    with ConcreteElem[WMatrImplData[T], WMatrImpl[T]] {
    lazy val eTo = this
    override def convertWMatr(x: Rep[WMatr[T]]) = WMatrImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WMatrImpl[T]]
    }
  }

  // state representation type
  type WMatrImplData[T] = Matr[T]

  // 3) Iso for concrete class
  class WMatrImplIso[T](implicit eeT: Elem[T])
    extends Iso[WMatrImplData[T], WMatrImpl[T]] {
    override def from(p: Rep[WMatrImpl[T]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[Matr[T]]) = {
      val wrappedValueOfBaseType = p
      WMatrImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WMatrImpl[T]] = WMatrImpl(DefaultOfMatr[T].value)
    lazy val eTo = new WMatrImplElem[T](this)
  }
  // 4) constructor and deconstructor
  abstract class WMatrImplCompanionAbs extends CompanionBase[WMatrImplCompanionAbs] with WMatrImplCompanion {
    override def toString = "WMatrImpl"

    def apply[T](wrappedValueOfBaseType: Rep[Matr[T]])(implicit eeT: Elem[T]): Rep[WMatrImpl[T]] =
      mkWMatrImpl(wrappedValueOfBaseType)
  }
  object WMatrImplMatcher {
    def unapply[T](p: Rep[WMatr[T]]) = unmkWMatrImpl(p)
  }
  def WMatrImpl: Rep[WMatrImplCompanionAbs]
  implicit def proxyWMatrImplCompanion(p: Rep[WMatrImplCompanionAbs]): WMatrImplCompanionAbs = {
    proxyOps[WMatrImplCompanionAbs](p)
  }

  implicit case object WMatrImplCompanionElem extends CompanionElem[WMatrImplCompanionAbs] {
    lazy val tag = weakTypeTag[WMatrImplCompanionAbs]
    protected def getDefaultRep = WMatrImpl
  }

  implicit def proxyWMatrImpl[T](p: Rep[WMatrImpl[T]]): WMatrImpl[T] =
    proxyOps[WMatrImpl[T]](p)

  implicit class ExtendedWMatrImpl[T](p: Rep[WMatrImpl[T]])(implicit eeT: Elem[T]) {
    def toData: Rep[WMatrImplData[T]] = isoWMatrImpl(eeT).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWMatrImpl[T](implicit eeT: Elem[T]): Iso[WMatrImplData[T], WMatrImpl[T]] =
    new WMatrImplIso[T]

  // 6) smart constructor and deconstructor
  def mkWMatrImpl[T](wrappedValueOfBaseType: Rep[Matr[T]])(implicit eeT: Elem[T]): Rep[WMatrImpl[T]]
  def unmkWMatrImpl[T](p: Rep[WMatr[T]]): Option[(Rep[Matr[T]])]
}

// Seq -----------------------------------
trait WMatrsSeq extends WMatrsDsl with ScalanSeq {
  self: WrappersDslSeq =>
  lazy val WMatr: Rep[WMatrCompanionAbs] = new WMatrCompanionAbs with UserTypeSeq[WMatrCompanionAbs] {
    lazy val selfType = element[WMatrCompanionAbs]
  }

    // override proxy if we deal with TypeWrapper
  //override def proxyMatr[T:Elem](p: Rep[Matr[T]]): WMatr[T] =
  //  proxyOpsEx[Matr[T],WMatr[T], SeqWMatrImpl[T]](p, bt => SeqWMatrImpl(bt))

    implicit def matrElement[T:Elem]: Elem[Matr[T]] = {
     implicit val wT = element[T].tag;
     new SeqBaseElemEx[Matr[T], WMatr[T]](element[WMatr[T]])(weakTypeTag[Matr[T]], DefaultOfMatr[T])
  }

  case class SeqWMatrImpl[T]
      (override val wrappedValueOfBaseType: Rep[Matr[T]])
      (implicit eeT: Elem[T])
    extends WMatrImpl[T](wrappedValueOfBaseType)
        with UserTypeSeq[WMatrImpl[T]] {
    lazy val selfType = element[WMatrImpl[T]]
    override def numRows: Rep[Int] =
      wrappedValueOfBaseType.numRows
  }
  lazy val WMatrImpl = new WMatrImplCompanionAbs with UserTypeSeq[WMatrImplCompanionAbs] {
    lazy val selfType = element[WMatrImplCompanionAbs]
  }

  def mkWMatrImpl[T]
      (wrappedValueOfBaseType: Rep[Matr[T]])(implicit eeT: Elem[T]): Rep[WMatrImpl[T]] =
      new SeqWMatrImpl[T](wrappedValueOfBaseType)
  def unmkWMatrImpl[T](p: Rep[WMatr[T]]) = p match {
    case p: WMatrImpl[T] @unchecked =>
      Some((p.wrappedValueOfBaseType))
    case _ => None
  }

  implicit def wrapMatrToWMatr[T:Elem](v: Matr[T]): WMatr[T] = WMatrImpl(v)
}

// Exp -----------------------------------
trait WMatrsExp extends WMatrsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WMatr: Rep[WMatrCompanionAbs] = new WMatrCompanionAbs with UserTypeDef[WMatrCompanionAbs] {
    lazy val selfType = element[WMatrCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  implicit def matrElement[T:Elem]: Elem[Matr[T]] = {
     implicit val wT = element[T].tag;
     new ExpBaseElemEx[Matr[T], WMatr[T]](element[WMatr[T]])(weakTypeTag[Matr[T]], DefaultOfMatr[T])
  }

  case class ExpWMatrImpl[T]
      (override val wrappedValueOfBaseType: Rep[Matr[T]])
      (implicit eeT: Elem[T])
    extends WMatrImpl[T](wrappedValueOfBaseType) with UserTypeDef[WMatrImpl[T]] {
    lazy val selfType = element[WMatrImpl[T]]
    override def mirror(t: Transformer) = ExpWMatrImpl[T](t(wrappedValueOfBaseType))
  }

  lazy val WMatrImpl: Rep[WMatrImplCompanionAbs] = new WMatrImplCompanionAbs with UserTypeDef[WMatrImplCompanionAbs] {
    lazy val selfType = element[WMatrImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WMatrImplMethods {
  }

  def mkWMatrImpl[T]
    (wrappedValueOfBaseType: Rep[Matr[T]])(implicit eeT: Elem[T]): Rep[WMatrImpl[T]] =
    new ExpWMatrImpl[T](wrappedValueOfBaseType)
  def unmkWMatrImpl[T](p: Rep[WMatr[T]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WMatrImplElem[T] @unchecked =>
      Some((p.asRep[WMatrImpl[T]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WMatrMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WMatr[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WMatrElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WMatr[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WMatr[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object numRows {
      def unapply(d: Def[_]): Option[Rep[WMatr[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WMatrElem[_, _]] && method.getName == "numRows" =>
          Some(receiver).asInstanceOf[Option[Rep[WMatr[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WMatr[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WMatrCompanionMethods {
  }
}
