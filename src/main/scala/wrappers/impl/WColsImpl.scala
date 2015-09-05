package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scalanizer.collections.Col
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WColsAbs extends WCols with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWCol[A](p: Rep[WCol[A]]): WCol[A] = {
    proxyOps[WCol[A]](p)(scala.reflect.classTag[WCol[A]])
  }

  // TypeWrapper proxy
  //implicit def proxyCol[A:Elem](p: Rep[Col[A]]): WCol[A] =
  //  proxyOps[WCol[A]](p.asRep[WCol[A]])

  implicit def unwrapValueOfWCol[A](w: Rep[WCol[A]]): Rep[Col[A]] = w.wrappedValueOfBaseType

  implicit def colElement[A:Elem]: Elem[Col[A]]

  // familyElem
  abstract class WColElem[A, To <: WCol[A]](implicit val eeA: Elem[A])
    extends WrapperElem[Col[A], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagA = eeA.tag
      weakTypeTag[WCol[A]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WCol[A]] => convertWCol(x) }
      tryConvert(element[WCol[A]], this, x, conv)
    }

    def convertWCol(x : Rep[WCol[A]]): Rep[To] = {
      assert(x.selfType1 match { case _: WColElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wColElement[A](implicit eeA: Elem[A]): Elem[WCol[A]] =
    new WColElem[A, WCol[A]] {
      lazy val eTo = element[WColImpl[A]]
    }

  implicit case object WColCompanionElem extends CompanionElem[WColCompanionAbs] {
    lazy val tag = weakTypeTag[WColCompanionAbs]
    protected def getDefaultRep = WCol
  }

  abstract class WColCompanionAbs extends CompanionBase[WColCompanionAbs] with WColCompanion {
    override def toString = "WCol"
  }
  def WCol: Rep[WColCompanionAbs]
  implicit def proxyWColCompanion(p: Rep[WColCompanion]): WColCompanion = {
    proxyOps[WColCompanion](p)
  }

  // default wrapper implementation
  abstract class WColImpl[A](val wrappedValueOfBaseType: Rep[Col[A]])(implicit val eeA: Elem[A]) extends WCol[A] {
  }
  trait WColImplCompanion
  // elem for concrete class
  class WColImplElem[A](val iso: Iso[WColImplData[A], WColImpl[A]])(implicit eeA: Elem[A])
    extends WColElem[A, WColImpl[A]]
    with ConcreteElem[WColImplData[A], WColImpl[A]] {
    lazy val eTo = this
    override def convertWCol(x: Rep[WCol[A]]) = WColImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagA = eeA.tag
      weakTypeTag[WColImpl[A]]
    }
  }

  // state representation type
  type WColImplData[A] = Col[A]

  // 3) Iso for concrete class
  class WColImplIso[A](implicit eeA: Elem[A])
    extends Iso[WColImplData[A], WColImpl[A]] {
    override def from(p: Rep[WColImpl[A]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[Col[A]]) = {
      val wrappedValueOfBaseType = p
      WColImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WColImpl[A]] = WColImpl(DefaultOfCol[A].value)
    lazy val eTo = new WColImplElem[A](this)
  }
  // 4) constructor and deconstructor
  abstract class WColImplCompanionAbs extends CompanionBase[WColImplCompanionAbs] with WColImplCompanion {
    override def toString = "WColImpl"

    def apply[A](wrappedValueOfBaseType: Rep[Col[A]])(implicit eeA: Elem[A]): Rep[WColImpl[A]] =
      mkWColImpl(wrappedValueOfBaseType)
  }
  object WColImplMatcher {
    def unapply[A](p: Rep[WCol[A]]) = unmkWColImpl(p)
  }
  def WColImpl: Rep[WColImplCompanionAbs]
  implicit def proxyWColImplCompanion(p: Rep[WColImplCompanionAbs]): WColImplCompanionAbs = {
    proxyOps[WColImplCompanionAbs](p)
  }

  implicit case object WColImplCompanionElem extends CompanionElem[WColImplCompanionAbs] {
    lazy val tag = weakTypeTag[WColImplCompanionAbs]
    protected def getDefaultRep = WColImpl
  }

  implicit def proxyWColImpl[A](p: Rep[WColImpl[A]]): WColImpl[A] =
    proxyOps[WColImpl[A]](p)

  implicit class ExtendedWColImpl[A](p: Rep[WColImpl[A]])(implicit eeA: Elem[A]) {
    def toData: Rep[WColImplData[A]] = isoWColImpl(eeA).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWColImpl[A](implicit eeA: Elem[A]): Iso[WColImplData[A], WColImpl[A]] =
    new WColImplIso[A]

  // 6) smart constructor and deconstructor
  def mkWColImpl[A](wrappedValueOfBaseType: Rep[Col[A]])(implicit eeA: Elem[A]): Rep[WColImpl[A]]
  def unmkWColImpl[A](p: Rep[WCol[A]]): Option[(Rep[Col[A]])]
}

// Seq -----------------------------------
trait WColsSeq extends WColsDsl with ScalanSeq {
  self: WrappersDslSeq =>
  lazy val WCol: Rep[WColCompanionAbs] = new WColCompanionAbs with UserTypeSeq[WColCompanionAbs] {
    lazy val selfType = element[WColCompanionAbs]
    override def apply[T:Elem](arr: Rep[Array[T]])(emT: Elem[T]): Rep[WCol[T]] =
      WColImpl(Col.apply[T](arr)(emT))
  }

    // override proxy if we deal with TypeWrapper
  //override def proxyCol[A:Elem](p: Rep[Col[A]]): WCol[A] =
  //  proxyOpsEx[Col[A],WCol[A], SeqWColImpl[A]](p, bt => SeqWColImpl(bt))

    implicit def colElement[A:Elem]: Elem[Col[A]] = {
     implicit val wA = element[A].tag;
     new SeqBaseElemEx[Col[A], WCol[A]](element[WCol[A]])(weakTypeTag[Col[A]], DefaultOfCol[A])
  }

  case class SeqWColImpl[A]
      (override val wrappedValueOfBaseType: Rep[Col[A]])
      (implicit eeA: Elem[A])
    extends WColImpl[A](wrappedValueOfBaseType)
        with UserTypeSeq[WColImpl[A]] {
    lazy val selfType = element[WColImpl[A]]
  }
  lazy val WColImpl = new WColImplCompanionAbs with UserTypeSeq[WColImplCompanionAbs] {
    lazy val selfType = element[WColImplCompanionAbs]
  }

  def mkWColImpl[A]
      (wrappedValueOfBaseType: Rep[Col[A]])(implicit eeA: Elem[A]): Rep[WColImpl[A]] =
      new SeqWColImpl[A](wrappedValueOfBaseType)
  def unmkWColImpl[A](p: Rep[WCol[A]]) = p match {
    case p: WColImpl[A] @unchecked =>
      Some((p.wrappedValueOfBaseType))
    case _ => None
  }

  implicit def wrapColToWCol[A:Elem](v: Col[A]): WCol[A] = WColImpl(v)
}

// Exp -----------------------------------
trait WColsExp extends WColsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WCol: Rep[WColCompanionAbs] = new WColCompanionAbs with UserTypeDef[WColCompanionAbs] {
    lazy val selfType = element[WColCompanionAbs]
    override def mirror(t: Transformer) = this

    def apply[T:Elem](arr: Rep[Array[T]])(emT: Elem[T]): Rep[WCol[T]] =
      methodCallEx[WCol[T]](self,
        this.getClass.getMethod("apply", classOf[AnyRef], classOf[AnyRef]),
        List(arr.asInstanceOf[AnyRef], emT.asInstanceOf[AnyRef]))
  }

  implicit def colElement[A:Elem]: Elem[Col[A]] = {
     implicit val wA = element[A].tag;
     new ExpBaseElemEx[Col[A], WCol[A]](element[WCol[A]])(weakTypeTag[Col[A]], DefaultOfCol[A])
  }

  case class ExpWColImpl[A]
      (override val wrappedValueOfBaseType: Rep[Col[A]])
      (implicit eeA: Elem[A])
    extends WColImpl[A](wrappedValueOfBaseType) with UserTypeDef[WColImpl[A]] {
    lazy val selfType = element[WColImpl[A]]
    override def mirror(t: Transformer) = ExpWColImpl[A](t(wrappedValueOfBaseType))
  }

  lazy val WColImpl: Rep[WColImplCompanionAbs] = new WColImplCompanionAbs with UserTypeDef[WColImplCompanionAbs] {
    lazy val selfType = element[WColImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WColImplMethods {
  }

  def mkWColImpl[A]
    (wrappedValueOfBaseType: Rep[Col[A]])(implicit eeA: Elem[A]): Rep[WColImpl[A]] =
    new ExpWColImpl[A](wrappedValueOfBaseType)
  def unmkWColImpl[A](p: Rep[WCol[A]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WColImplElem[A] @unchecked =>
      Some((p.asRep[WColImpl[A]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WColMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WCol[A]] forSome {type A}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WColElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WCol[A]] forSome {type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WCol[A]] forSome {type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WColCompanionMethods {
    object apply {
      def unapply(d: Def[_]): Option[(Rep[Array[T]], Elem[T]) forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(arr, emT, _*), _) if receiver.elem == WColCompanionElem && method.getName == "apply" =>
          Some((arr, emT)).asInstanceOf[Option[(Rep[Array[T]], Elem[T]) forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Array[T]], Elem[T]) forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }
}
