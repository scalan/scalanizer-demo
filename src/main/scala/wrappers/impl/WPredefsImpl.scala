package wrappers

import scalan._
import impl._
import scala.Predef
import scala.reflect.runtime.universe._
import scala.reflect._

package impl {
// Abs -----------------------------------
trait WPredefsAbs extends ScalanDsl with WPredefs {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWPredef(p: Rep[WPredef]): WPredef = {
    proxyOps[WPredef](p)(scala.reflect.classTag[WPredef])
  }

  // TypeWrapper proxy
  //implicit def proxyPredef.type(p: Rep[Predef.type]): WPredef =
  //  proxyOps[WPredef](p.asRep[WPredef])

  implicit def unwrapValueOfWPredef(w: Rep[WPredef]): Rep[Predef.type] = w.wrappedValue

  implicit lazy val predef.typeElement: Elem[Predef.type] =
    element[WPredef].asInstanceOf[WrapperElem[_, _]].baseElem.asInstanceOf[Elem[Predef.type]]

  // familyElem
  class WPredefElem[To <: WPredef]
    extends WrapperElem[Predef.type, To] {
    lazy val parent: Option[Elem[_]] = None
    lazy val typeArgs = TypeArgs()
    override def isEntityType = true
    override lazy val tag = {
      weakTypeTag[WPredef].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Def[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WPredef] => convertWPredef(x) }
      tryConvert(element[WPredef], this, x, conv)
    }

    def convertWPredef(x: Rep[WPredef]): Rep[To] = {
      x.selfType1 match {
        case _: WPredefElem[_] => x.asRep[To]
        case e => !!!(s"Expected $x to have WPredefElem[_], but got $e", x)
      }
    }
    lazy val baseElem = {
      new BaseTypeElem[Predef.type, WPredef](this.asInstanceOf[Elem[WPredef]])
    }
    lazy val eTo: Elem[_] = new WPredefImplElem(isoWPredefImpl)
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wPredefElement: Elem[WPredef] =
    elemCache.getOrElseUpdate(
      (classOf[WPredefElem[WPredef]], Nil),
      new WPredefElem[WPredef]).asInstanceOf[Elem[WPredef]]

  implicit case object WPredefCompanionElem extends CompanionElem[WPredefCompanionAbs] {
    lazy val tag = weakTypeTag[WPredefCompanionAbs]
    protected def getDefaultRep = WPredef
  }

  abstract class WPredefCompanionAbs extends CompanionDef[WPredefCompanionAbs] with WPredefCompanion {
    def selfType = WPredefCompanionElem
    override def toString = "WPredef"
  }
  def WPredef: Rep[WPredefCompanionAbs]
  implicit def proxyWPredefCompanionAbs(p: Rep[WPredefCompanionAbs]): WPredefCompanionAbs =
    proxyOps[WPredefCompanionAbs](p)

  // default wrapper implementation
  abstract class WPredefImpl(val wrappedValue: Rep[Predef.type]) extends WPredef with Def[WPredefImpl] {
    lazy val selfType = element[WPredefImpl]
  }
  trait WPredefImplCompanion
  // elem for concrete class
  class WPredefImplElem(val iso: Iso[WPredefImplData, WPredefImpl])
    extends WPredefElem[WPredefImpl]
    with ConcreteElem[WPredefImplData, WPredefImpl] {
    override lazy val parent: Option[Elem[_]] = Some(wPredefElement)
    override lazy val typeArgs = TypeArgs()
    override lazy val eTo: Elem[_] = this
    override def convertWPredef(x: Rep[WPredef]) = WPredefImpl(x.wrappedValue)
    override def getDefaultRep = WPredefImpl(DefaultOfPredef.type)
    override lazy val tag = {
      weakTypeTag[WPredefImpl]
    }
  }

  // state representation type
  type WPredefImplData = Predef.type

  // 3) Iso for concrete class
  class WPredefImplIso
    extends EntityIso[WPredefImplData, WPredefImpl] with Def[WPredefImplIso] {
    override def from(p: Rep[WPredefImpl]) =
      p.wrappedValue
    override def to(p: Rep[Predef.type]) = {
      val wrappedValue = p
      WPredefImpl(wrappedValue)
    }
    lazy val eFrom = element[Predef.type]
    lazy val eTo = new WPredefImplElem(self)
    lazy val selfType = new WPredefImplIsoElem
    def productArity = 0
    def productElement(n: Int) = ???
  }
  case class WPredefImplIsoElem() extends Elem[WPredefImplIso] {
    def isEntityType = true
    def getDefaultRep = reifyObject(new WPredefImplIso())
    lazy val tag = {
      weakTypeTag[WPredefImplIso]
    }
    lazy val typeArgs = TypeArgs()
  }
  // 4) constructor and deconstructor
  class WPredefImplCompanionAbs extends CompanionDef[WPredefImplCompanionAbs] {
    def selfType = WPredefImplCompanionElem
    override def toString = "WPredefImpl"

    @scalan.OverloadId("fromFields")
    def apply(wrappedValue: Rep[Predef.type]): Rep[WPredefImpl] =
      mkWPredefImpl(wrappedValue)

    def unapply(p: Rep[WPredef]) = unmkWPredefImpl(p)
  }
  lazy val WPredefImplRep: Rep[WPredefImplCompanionAbs] = new WPredefImplCompanionAbs
  lazy val WPredefImpl: WPredefImplCompanionAbs = proxyWPredefImplCompanion(WPredefImplRep)
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
    reifyObject(new WPredefImplIso())

  // 6) smart constructor and deconstructor
  def mkWPredefImpl(wrappedValue: Rep[Predef.type]): Rep[WPredefImpl]
  def unmkWPredefImpl(p: Rep[WPredef]): Option[(Rep[Predef.type])]

  registerModule(WPredefs_Module)
}

// Exp -----------------------------------
trait WPredefsExp extends ScalanExp with WPredefsDsl {
  self: WrappersDslExp =>

  lazy val WPredef: Rep[WPredefCompanionAbs] = new WPredefCompanionAbs {
    def intArrayOps(xs: Rep[WArray[Int]]): Rep[WArrayOps[Int]] =
      methodCallEx[WArrayOps[Int]](self,
        this.getClass.getMethod("intArrayOps", classOf[AnyRef]),
        List(xs.asInstanceOf[AnyRef]))

    def refArrayOps[T](xs: Rep[WArray[T]])(emT: Elem[T]): Rep[WArrayOps[T]] =
      methodCallEx[WArrayOps[T]](self,
        this.getClass.getMethod("refArrayOps", classOf[AnyRef], classOf[AnyRef]),
        List(xs.asInstanceOf[AnyRef], emT.asInstanceOf[AnyRef]))

    def genericWrapArray[T](xs: Rep[WArray[T]])(emT: Elem[T]): Rep[WWrappedArray[T]] =
      methodCallEx[WWrappedArray[T]](self,
        this.getClass.getMethod("genericWrapArray", classOf[AnyRef], classOf[AnyRef]),
        List(xs.asInstanceOf[AnyRef], emT.asInstanceOf[AnyRef]))

    def genericArrayOps[T](xs: Rep[WArray[T]])(emT: Elem[T]): Rep[WArrayOps[T]] =
      methodCallEx[WArrayOps[T]](self,
        this.getClass.getMethod("genericArrayOps", classOf[AnyRef], classOf[AnyRef]),
        List(xs.asInstanceOf[AnyRef], emT.asInstanceOf[AnyRef]))
  }

  case class ExpWPredefImpl
      (override val wrappedValue: Rep[Predef.type])
    extends WPredefImpl(wrappedValue)

  object WPredefImplMethods {
  }

  def mkWPredefImpl
    (wrappedValue: Rep[Predef.type]): Rep[WPredefImpl] =
    new ExpWPredefImpl(wrappedValue)
  def unmkWPredefImpl(p: Rep[WPredef]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WPredefImplElem @unchecked =>
      Some((p.asRep[WPredefImpl].wrappedValue))
    case _ =>
      None
  }

  object WPredefMethods {
    object wrappedValue {
      def unapply(d: Def[_]): Option[Rep[WPredef]] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WPredefElem[_]] && method.getName == "wrappedValue" =>
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

object WPredefs_Module extends scalan.ModuleInfo {
  val dump = "H4sIAAAAAAAAALVVz2sTQRidpLVJmlDTikhFsC2pomhSBKnQU0lTFWIbusVKWiqT3Wm6dXZ33Jm0Gw89FtSbeBI8VDwWQbx5F8SD/4BnT1WRHiwIijOzs9tNMNQezGGYH9++7/veezPZ+QqOURecozrE0M5biMG8JueTlOW0ks1M1rzlGA2MptDK8o0XPxeth6fiIFsFPauQTlFcBSl/UvJIONeYUQYpaOuIMselDAyXZYaC7mCMdGY6dsG0rAaDNYwKZZOyiTLorjlG8z7YBLEyyOqOrbuIIa2IIaWIqv0kEhWZ4Tol181ZcpDDLoguCpEu5l1oMl4+z5H14+cQ0Zq2YzctBvpUabNElMVj0sgjvIebFsEyTVcZJEyLOC4LsiZ4hlXHCJbdNuQbYKC8BtdhgWetFzTmmnZdgBGo34N1NMNDRHg374EivDLfJEiBpykzWvJ5BADAVbkiC8sfcJYPOcsLznIack2IzQdQHFZcx2sC/xfrAsAjHOLSIRABAirZRu7Rkr64r6WtuPjYE6UkZEE9HOhsB4dIeTi37+ee0L3r2+Nx0FsFvSadrFHmQp1FbaDoSkPbdpisOWQQunWu4EgnBWWWSR7TZpOU7lgE2hxJcZnhQmFTN5kIFnsZJU8H7hOMoCA05pFY2O9Qh36ll4oQ48ru4OXRL6U7cRBvTZHikBq/DG4AykBioeIiA61ISsWQUux2zhN2fH73m/FuDCzFQ54U7L9JwyEGrj17O4oqr+IgWZVOnsawLkUSREwhqldB0llHrr+fWIdYzP4qVIJ3ARuYKfqifXfxvhkY6ngJCRKkTEhzx4L2074/Zxwb5aYruR/ah6c7wn4uyPgn/q38bY7/+tS3wqQzGchsuJAQZNyGuOE/BccZSPsc55lSN0q0GE7LwAG55tFKEnHr5Gnkk0OZDR6U11tbJ7+/vHtCmj5ZM5kFSW7sCJYPHPofLQ1UNwFLInJB0udX1yOGM/6xGPrbuEtFHoKB8Exo1+srpDkW6h/ZM5e3HzNp0pjX+hbO1tb42zMhcQYlznBbUZmSVwy6HoseHaEgKSL3RlYJW4zS6KtLJJz88AKvf6SDuprikwu8uf985uLHN5/lJe8VynCj2qzl4ZcyeET5qluo0VbxSFjCgct4aFJVyh++5IZSRBUnxqttwgWi8b+KNvxxCf0H3qvmZssHAAA="
}
}

trait WPredefsDsl extends impl.WPredefsAbs {self: WrappersDsl =>}
trait WPredefsDslStd extends impl.WPredefsStd {self: WrappersDslStd =>}
trait WPredefsDslExp extends impl.WPredefsExp {self: WrappersDslExp =>}
