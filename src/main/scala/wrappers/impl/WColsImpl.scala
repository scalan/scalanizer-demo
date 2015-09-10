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
  implicit def proxyWCol[T](p: Rep[WCol[T]]): WCol[T] = {
    proxyOps[WCol[T]](p)(scala.reflect.classTag[WCol[T]])
  }

  // TypeWrapper proxy
  //implicit def proxyCol[T:Elem](p: Rep[Col[T]]): WCol[T] =
  //  proxyOps[WCol[T]](p.asRep[WCol[T]])

  implicit def unwrapValueOfWCol[T](w: Rep[WCol[T]]): Rep[Col[T]] = w.wrappedValueOfBaseType

  implicit def colElement[T:Elem]: Elem[Col[T]]

  // familyElem
  abstract class WColElem[T, To <: WCol[T]](implicit val eeT: Elem[T])
    extends WrapperElem[Col[T], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WCol[T]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WCol[T]] => convertWCol(x) }
      tryConvert(element[WCol[T]], this, x, conv)
    }

    def convertWCol(x : Rep[WCol[T]]): Rep[To] = {
      assert(x.selfType1 match { case _: WColElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wColElement[T](implicit eeT: Elem[T]): Elem[WCol[T]] =
    new WColElem[T, WCol[T]] {
      lazy val eTo = element[WColImpl[T]]
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
  abstract class WColImpl[T](val wrappedValueOfBaseType: Rep[Col[T]])(implicit val eeT: Elem[T]) extends WCol[T] {
    def arr: Rep[Array[T]] =
      methodCallEx[Array[T]](self,
        this.getClass.getMethod("arr"),
        List())
  }
  trait WColImplCompanion
  // elem for concrete class
  class WColImplElem[T](val iso: Iso[WColImplData[T], WColImpl[T]])(implicit eeT: Elem[T])
    extends WColElem[T, WColImpl[T]]
    with ConcreteElem[WColImplData[T], WColImpl[T]] {
    lazy val eTo = this
    override def convertWCol(x: Rep[WCol[T]]) = WColImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WColImpl[T]]
    }
  }

  // state representation type
  type WColImplData[T] = Col[T]

  // 3) Iso for concrete class
  class WColImplIso[T](implicit eeT: Elem[T])
    extends Iso[WColImplData[T], WColImpl[T]] {
    override def from(p: Rep[WColImpl[T]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[Col[T]]) = {
      val wrappedValueOfBaseType = p
      WColImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WColImpl[T]] = WColImpl(DefaultOfCol[T].value)
    lazy val eTo = new WColImplElem[T](this)
  }
  // 4) constructor and deconstructor
  abstract class WColImplCompanionAbs extends CompanionBase[WColImplCompanionAbs] with WColImplCompanion {
    override def toString = "WColImpl"

    def apply[T](wrappedValueOfBaseType: Rep[Col[T]])(implicit eeT: Elem[T]): Rep[WColImpl[T]] =
      mkWColImpl(wrappedValueOfBaseType)
  }
  object WColImplMatcher {
    def unapply[T](p: Rep[WCol[T]]) = unmkWColImpl(p)
  }
  def WColImpl: Rep[WColImplCompanionAbs]
  implicit def proxyWColImplCompanion(p: Rep[WColImplCompanionAbs]): WColImplCompanionAbs = {
    proxyOps[WColImplCompanionAbs](p)
  }

  implicit case object WColImplCompanionElem extends CompanionElem[WColImplCompanionAbs] {
    lazy val tag = weakTypeTag[WColImplCompanionAbs]
    protected def getDefaultRep = WColImpl
  }

  implicit def proxyWColImpl[T](p: Rep[WColImpl[T]]): WColImpl[T] =
    proxyOps[WColImpl[T]](p)

  implicit class ExtendedWColImpl[T](p: Rep[WColImpl[T]])(implicit eeT: Elem[T]) {
    def toData: Rep[WColImplData[T]] = isoWColImpl(eeT).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWColImpl[T](implicit eeT: Elem[T]): Iso[WColImplData[T], WColImpl[T]] =
    new WColImplIso[T]

  // 6) smart constructor and deconstructor
  def mkWColImpl[T](wrappedValueOfBaseType: Rep[Col[T]])(implicit eeT: Elem[T]): Rep[WColImpl[T]]
  def unmkWColImpl[T](p: Rep[WCol[T]]): Option[(Rep[Col[T]])]
}

// Seq -----------------------------------
trait WColsSeq extends WColsDsl with ScalanSeq {
  self: WrappersDslSeq =>
  lazy val WCol: Rep[WColCompanionAbs] = new WColCompanionAbs with UserTypeSeq[WColCompanionAbs] {
    lazy val selfType = element[WColCompanionAbs]
    override def apply[T]( arr: Rep[Array[T]])(implicit emT: Elem[T]): Rep[WCol[T]] =
      WColImpl(Col.apply[T](arr)(emT.classTag))
  }

    // override proxy if we deal with TypeWrapper
  //override def proxyCol[T:Elem](p: Rep[Col[T]]): WCol[T] =
  //  proxyOpsEx[Col[T],WCol[T], SeqWColImpl[T]](p, bt => SeqWColImpl(bt))

    implicit def colElement[T:Elem]: Elem[Col[T]] = {
     implicit val wT = element[T].tag;
     new SeqBaseElemEx[Col[T], WCol[T]](element[WCol[T]])(weakTypeTag[Col[T]], DefaultOfCol[T])
  }

  case class SeqWColImpl[T]
      (override val wrappedValueOfBaseType: Rep[Col[T]])
      (implicit eeT: Elem[T])
    extends WColImpl[T](wrappedValueOfBaseType)
        with UserTypeSeq[WColImpl[T]] {
    lazy val selfType = element[WColImpl[T]]
    override def arr: Rep[Array[T]] =
      wrappedValueOfBaseType.arr
  }
  lazy val WColImpl = new WColImplCompanionAbs with UserTypeSeq[WColImplCompanionAbs] {
    lazy val selfType = element[WColImplCompanionAbs]
  }

  def mkWColImpl[T]
      (wrappedValueOfBaseType: Rep[Col[T]])(implicit eeT: Elem[T]): Rep[WColImpl[T]] =
      new SeqWColImpl[T](wrappedValueOfBaseType)
  def unmkWColImpl[T](p: Rep[WCol[T]]) = p match {
    case p: WColImpl[T] @unchecked =>
      Some((p.wrappedValueOfBaseType))
    case _ => None
  }

  implicit def wrapColToWCol[T:Elem](v: Col[T]): WCol[T] = WColImpl(v)
}

// Exp -----------------------------------
trait WColsExp extends WColsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WCol: Rep[WColCompanionAbs] = new WColCompanionAbs with UserTypeDef[WColCompanionAbs] {
    lazy val selfType = element[WColCompanionAbs]
    override def mirror(t: Transformer) = this

    def apply[T]( arr: Rep[Array[T]])(implicit emT: Elem[T]): Rep[WCol[T]] =
      methodCallEx[WCol[T]](self,
        this.getClass.getMethod("apply", classOf[AnyRef], classOf[AnyRef]),
        List(arr.asInstanceOf[AnyRef], emT.asInstanceOf[AnyRef]))
  }

  implicit def colElement[T:Elem]: Elem[Col[T]] = {
     implicit val wT = element[T].tag;
     new ExpBaseElemEx[Col[T], WCol[T]](element[WCol[T]])(weakTypeTag[Col[T]], DefaultOfCol[T])
  }

  case class ExpWColImpl[T]
      (override val wrappedValueOfBaseType: Rep[Col[T]])
      (implicit eeT: Elem[T])
    extends WColImpl[T](wrappedValueOfBaseType) with UserTypeDef[WColImpl[T]] {
    lazy val selfType = element[WColImpl[T]]
    override def mirror(t: Transformer) = ExpWColImpl[T](t(wrappedValueOfBaseType))
  }

  lazy val WColImpl: Rep[WColImplCompanionAbs] = new WColImplCompanionAbs with UserTypeDef[WColImplCompanionAbs] {
    lazy val selfType = element[WColImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WColImplMethods {
  }

  def mkWColImpl[T]
    (wrappedValueOfBaseType: Rep[Col[T]])(implicit eeT: Elem[T]): Rep[WColImpl[T]] =
    new ExpWColImpl[T](wrappedValueOfBaseType)
  def unmkWColImpl[T](p: Rep[WCol[T]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WColImplElem[T] @unchecked =>
      Some((p.asRep[WColImpl[T]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WColMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WCol[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WColElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WCol[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WCol[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object arr {
      def unapply(d: Def[_]): Option[Rep[WCol[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WColElem[_, _]] && method.getName == "arr" =>
          Some(receiver).asInstanceOf[Option[Rep[WCol[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WCol[T]] forSome {type T}] = exp match {
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
