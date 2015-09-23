package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scala.Predef
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WPredefsAbs extends WPredefs with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWPredef(p: Rep[WPredef]): WPredef = {
    proxyOps[WPredef](p)(scala.reflect.classTag[WPredef])
  }

  // TypeWrapper proxy
  //implicit def proxyPredef(p: Rep[Predef.type]): WPredef =
  //  proxyOps[WPredef](p.asRep[WPredef])

  implicit def unwrapValueOfWPredef(w: Rep[WPredef]): Rep[Predef.type] = w.wrappedValueOfBaseType

  implicit def predefElement: Elem[Predef.type]

  // familyElem
  abstract class WPredefElem[To <: WPredef]
    extends WrapperElem[Predef.type, To] {
    override def isEntityType = true
    override lazy val tag = {
      weakTypeTag[WPredef].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WPredef] => convertWPredef(x) }
      tryConvert(element[WPredef], this, x, conv)
    }

    def convertWPredef(x : Rep[WPredef]): Rep[To] = {
      assert(x.selfType1 match { case _: WPredefElem[_] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wPredefElement: Elem[WPredef] =
    new WPredefElem[WPredef] {
      lazy val eTo = element[WPredefImpl]
    }

  implicit case object WPredefCompanionElem extends CompanionElem[WPredefCompanionAbs] {
    lazy val tag = weakTypeTag[WPredefCompanionAbs]
    protected def getDefaultRep = WPredef
  }

  abstract class WPredefCompanionAbs extends CompanionBase[WPredefCompanionAbs] with WPredefCompanion {
    override def toString = "WPredef"
  }
  def WPredef: Rep[WPredefCompanionAbs]
  implicit def proxyWPredefCompanion(p: Rep[WPredefCompanion]): WPredefCompanion = {
    proxyOps[WPredefCompanion](p)
  }

  // default wrapper implementation
  abstract class WPredefImpl(val wrappedValueOfBaseType: Rep[Predef.type]) extends WPredef {
  }
  trait WPredefImplCompanion
  // elem for concrete class
  class WPredefImplElem(val iso: Iso[WPredefImplData, WPredefImpl])
    extends WPredefElem[WPredefImpl]
    with ConcreteElem[WPredefImplData, WPredefImpl] {
    lazy val eTo = this
    override def convertWPredef(x: Rep[WPredef]) = WPredefImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      weakTypeTag[WPredefImpl]
    }
  }

  // state representation type
  type WPredefImplData = Predef.type

  // 3) Iso for concrete class
  class WPredefImplIso
    extends Iso[WPredefImplData, WPredefImpl] {
    override def from(p: Rep[WPredefImpl]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[Predef.type]) = {
      val wrappedValueOfBaseType = p
      WPredefImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WPredefImpl] = WPredefImpl(DefaultOfPredef.value)
    lazy val eTo = new WPredefImplElem(this)
  }
  // 4) constructor and deconstructor
  abstract class WPredefImplCompanionAbs extends CompanionBase[WPredefImplCompanionAbs] with WPredefImplCompanion {
    override def toString = "WPredefImpl"

    def apply(wrappedValueOfBaseType: Rep[Predef.type]): Rep[WPredefImpl] =
      mkWPredefImpl(wrappedValueOfBaseType)
  }
  object WPredefImplMatcher {
    def unapply(p: Rep[WPredef]) = unmkWPredefImpl(p)
  }
  def WPredefImpl: Rep[WPredefImplCompanionAbs]
  implicit def proxyWPredefImplCompanion(p: Rep[WPredefImplCompanionAbs]): WPredefImplCompanionAbs = {
    proxyOps[WPredefImplCompanionAbs](p)
  }

  implicit case object WPredefImplCompanionElem extends CompanionElem[WPredefImplCompanionAbs] {
    lazy val tag = weakTypeTag[WPredefImplCompanionAbs]
    protected def getDefaultRep = WPredefImpl
  }

  implicit def proxyWPredefImpl(p: Rep[WPredefImpl]): WPredefImpl =
    proxyOps[WPredefImpl](p)

  implicit class ExtendedWPredefImpl(p: Rep[WPredefImpl]) {
    def toData: Rep[WPredefImplData] = isoWPredefImpl.from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWPredefImpl: Iso[WPredefImplData, WPredefImpl] =
    new WPredefImplIso

  // 6) smart constructor and deconstructor
  def mkWPredefImpl(wrappedValueOfBaseType: Rep[Predef.type]): Rep[WPredefImpl]
  def unmkWPredefImpl(p: Rep[WPredef]): Option[(Rep[Predef.type])]
}

// Exp -----------------------------------
trait WPredefsExp extends WPredefsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WPredef: Rep[WPredefCompanionAbs] = new WPredefCompanionAbs with UserTypeDef[WPredefCompanionAbs] {
    lazy val selfType = element[WPredefCompanionAbs]
    override def mirror(t: Transformer) = this

    def intArrayOps( xs: Rep[WArray[Int]]): Rep[WArrayOps[Int]] =
      methodCallEx[WArrayOps[Int]](self,
        this.getClass.getMethod("intArrayOps", classOf[AnyRef]),
        List(xs.asInstanceOf[AnyRef]))

    def refArrayOps[T]( xs: Rep[WArray[T]])(implicit emT: Elem[T]): Rep[WArrayOps[T]] =
      methodCallEx[WArrayOps[T]](self,
        this.getClass.getMethod("refArrayOps", classOf[AnyRef], classOf[AnyRef]),
        List(xs.asInstanceOf[AnyRef], emT.asInstanceOf[AnyRef]))

    def genericWrapArray[T]( xs: Rep[WArray[T]])(implicit emT: Elem[T]): Rep[WWrappedArray[T]] =
      methodCallEx[WWrappedArray[T]](self,
        this.getClass.getMethod("genericWrapArray", classOf[AnyRef], classOf[AnyRef]),
        List(xs.asInstanceOf[AnyRef], emT.asInstanceOf[AnyRef]))

    def genericArrayOps[T]( xs: Rep[WArray[T]])(implicit emT: Elem[T]): Rep[WArrayOps[T]] =
      methodCallEx[WArrayOps[T]](self,
        this.getClass.getMethod("genericArrayOps", classOf[AnyRef], classOf[AnyRef]),
        List(xs.asInstanceOf[AnyRef], emT.asInstanceOf[AnyRef]))
  }

  implicit lazy val predefElement: Elem[Predef.type] = new ExpBaseElemEx[Predef.type, WPredef](element[WPredef])(weakTypeTag[Predef.type], DefaultOfPredef)

  case class ExpWPredefImpl
      (override val wrappedValueOfBaseType: Rep[Predef.type])

    extends WPredefImpl(wrappedValueOfBaseType) with UserTypeDef[WPredefImpl] {
    lazy val selfType = element[WPredefImpl]
    override def mirror(t: Transformer) = ExpWPredefImpl(t(wrappedValueOfBaseType))
  }

  lazy val WPredefImpl: Rep[WPredefImplCompanionAbs] = new WPredefImplCompanionAbs with UserTypeDef[WPredefImplCompanionAbs] {
    lazy val selfType = element[WPredefImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WPredefImplMethods {
  }

  def mkWPredefImpl
    (wrappedValueOfBaseType: Rep[Predef.type]): Rep[WPredefImpl] =
    new ExpWPredefImpl(wrappedValueOfBaseType)
  def unmkWPredefImpl(p: Rep[WPredef]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WPredefImplElem @unchecked =>
      Some((p.asRep[WPredefImpl].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WPredefMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WPredef]] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WPredefElem[_]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WPredef]]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WPredef]] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WPredefCompanionMethods {
    object intArrayOps {
      def unapply(d: Def[_]): Option[Rep[WArray[Int]]] = d match {
        case MethodCall(receiver, method, Seq(xs, _*), _) if receiver.elem == WPredefCompanionElem && method.getName == "intArrayOps" =>
          Some(xs).asInstanceOf[Option[Rep[WArray[Int]]]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WArray[Int]]] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object refArrayOps {
      def unapply(d: Def[_]): Option[(Rep[WArray[T]], Elem[T]) forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(xs, emT, _*), _) if receiver.elem == WPredefCompanionElem && method.getName == "refArrayOps" =>
          Some((xs, emT)).asInstanceOf[Option[(Rep[WArray[T]], Elem[T]) forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[WArray[T]], Elem[T]) forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object genericWrapArray {
      def unapply(d: Def[_]): Option[(Rep[WArray[T]], Elem[T]) forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(xs, emT, _*), _) if receiver.elem == WPredefCompanionElem && method.getName == "genericWrapArray" =>
          Some((xs, emT)).asInstanceOf[Option[(Rep[WArray[T]], Elem[T]) forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[WArray[T]], Elem[T]) forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object genericArrayOps {
      def unapply(d: Def[_]): Option[(Rep[WArray[T]], Elem[T]) forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(xs, emT, _*), _) if receiver.elem == WPredefCompanionElem && method.getName == "genericArrayOps" =>
          Some((xs, emT)).asInstanceOf[Option[(Rep[WArray[T]], Elem[T]) forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[WArray[T]], Elem[T]) forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }
}
