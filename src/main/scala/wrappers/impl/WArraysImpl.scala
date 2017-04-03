package wrappers

import scalan._
import impl._
import scala.Array
import scala.reflect.runtime.universe._
import scala.reflect._

package impl {
// Abs -----------------------------------
trait WArraysAbs extends ScalanDsl with WArrays {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWArray[T](p: Rep[WArray[T]]): WArray[T] = {
    proxyOps[WArray[T]](p)(scala.reflect.classTag[WArray[T]])
  }

  // TypeWrapper proxy
  //implicit def proxyArray[T:Elem](p: Rep[Array[T]]): WArray[T] =
  //  proxyOps[WArray[T]](p.asRep[WArray[T]])

  implicit def unwrapValueOfWArray[T](w: Rep[WArray[T]]): Rep[Array[T]] = w.wrappedValue

  implicit def arrayElement[T:Elem]: Elem[Array[T]] =
    element[WArray[T]].asInstanceOf[WrapperElem[_, _]].baseElem.asInstanceOf[Elem[Array[T]]]

  // familyElem
  class WArrayElem[T, To <: WArray[T]](implicit _eeT: Elem[T])
    extends WrapperElem[Array[T], To] {
    def eeT = _eeT
    lazy val parent: Option[Elem[_]] = None
    lazy val typeArgs = TypeArgs("T" -> (eeT -> scalan.util.Invariant))
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WArray[T]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Def[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WArray[T]] => convertWArray(x) }
      tryConvert(element[WArray[T]], this, x, conv)
    }

    def convertWArray(x: Rep[WArray[T]]): Rep[To] = {
      x.elem match {
        case _: WArrayElem[_, _] => x.asRep[To]
        case e => !!!(s"Expected $x to have WArrayElem[_, _], but got $e", x)
      }
    }
    lazy val baseElem = {
            implicit val wT = element[T].tag
      new BaseTypeElem[Array[T], WArray[T]](this.asInstanceOf[Elem[WArray[T]]])
    }
    lazy val eTo: Elem[_] = new WArrayImplElem[T](isoWArrayImpl(eeT))(eeT)
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wArrayElement[T](implicit eeT: Elem[T]): Elem[WArray[T]] =
    elemCache.getOrElseUpdate(
      (classOf[WArrayElem[T, WArray[T]]], Seq(eeT)),
      new WArrayElem[T, WArray[T]]).asInstanceOf[Elem[WArray[T]]]

  implicit case object WArrayCompanionElem extends CompanionElem[WArrayCompanionAbs] {
    lazy val tag = weakTypeTag[WArrayCompanionAbs]
    protected def getDefaultRep = WArray
  }

  abstract class WArrayCompanionAbs extends CompanionDef[WArrayCompanionAbs] with WArrayCompanion {
    def selfType = WArrayCompanionElem
    override def toString = "WArray"
  }
  def WArray: Rep[WArrayCompanionAbs]
  implicit def proxyWArrayCompanionAbs(p: Rep[WArrayCompanionAbs]): WArrayCompanionAbs =
    proxyOps[WArrayCompanionAbs](p)

  // default wrapper implementation
  abstract class WArrayImpl[T](val wrappedValue: Rep[Array[T]])(implicit val eeT: Elem[T]) extends WArray[T] with Def[WArrayImpl[T]] {
    lazy val selfType = element[WArrayImpl[T]]

    def apply(i: Rep[Int]): Rep[T] =
      methodCallEx[T](self,
        this.getClass.getMethod("apply", classOf[AnyRef]),
        List(i.asInstanceOf[AnyRef]))

    def length: Rep[Int] =
      methodCallEx[Int](self,
        this.getClass.getMethod("length"),
        List())
  }
  trait WArrayImplCompanion
  // elem for concrete class
  class WArrayImplElem[T](val iso: Iso[WArrayImplData[T], WArrayImpl[T]])(implicit override val eeT: Elem[T])
    extends WArrayElem[T, WArrayImpl[T]]
    with ConcreteElem[WArrayImplData[T], WArrayImpl[T]] {
    override lazy val parent: Option[Elem[_]] = Some(wArrayElement(element[T]))
    override lazy val typeArgs = TypeArgs("T" -> (eeT -> scalan.util.Invariant))
    override lazy val eTo: Elem[_] = this
    override def convertWArray(x: Rep[WArray[T]]) = WArrayImpl(x.wrappedValue)
    override def getDefaultRep = WArrayImpl(DefaultOfArray[T])
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WArrayImpl[T]]
    }
  }

  // state representation type
  type WArrayImplData[T] = Array[T]

  // 3) Iso for concrete class
  class WArrayImplIso[T](implicit eeT: Elem[T])
    extends EntityIso[WArrayImplData[T], WArrayImpl[T]] with Def[WArrayImplIso[T]] {
    override def from(p: Rep[WArrayImpl[T]]) =
      p.wrappedValue
    override def to(p: Rep[Array[T]]) = {
      val wrappedValue = p
      WArrayImpl(wrappedValue)
    }
    lazy val eFrom = element[Array[T]]
    lazy val eTo = new WArrayImplElem[T](self)
    lazy val selfType = new WArrayImplIsoElem[T](eeT)
    def productArity = 1
    def productElement(n: Int) = eeT
  }
  case class WArrayImplIsoElem[T](eeT: Elem[T]) extends Elem[WArrayImplIso[T]] {
    def getDefaultRep = reifyObject(new WArrayImplIso[T]()(eeT))
    lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WArrayImplIso[T]]
    }
    lazy val typeArgs = TypeArgs("T" -> (eeT -> scalan.util.Invariant))
  }
  // 4) constructor and deconstructor
  class WArrayImplCompanionAbs extends CompanionDef[WArrayImplCompanionAbs] {
    def selfType = WArrayImplCompanionElem
    override def toString = "WArrayImpl"

    @scalan.OverloadId("fromFields")
    def apply[T](wrappedValue: Rep[Array[T]])(implicit eeT: Elem[T]): Rep[WArrayImpl[T]] =
      mkWArrayImpl(wrappedValue)

    def unapply[T](p: Rep[WArray[T]]) = unmkWArrayImpl(p)
  }
  lazy val WArrayImplRep: Rep[WArrayImplCompanionAbs] = new WArrayImplCompanionAbs
  lazy val WArrayImpl: WArrayImplCompanionAbs = proxyWArrayImplCompanion(WArrayImplRep)
  implicit def proxyWArrayImplCompanion(p: Rep[WArrayImplCompanionAbs]): WArrayImplCompanionAbs = {
    proxyOps[WArrayImplCompanionAbs](p)
  }

  implicit case object WArrayImplCompanionElem extends CompanionElem[WArrayImplCompanionAbs] {
    lazy val tag = weakTypeTag[WArrayImplCompanionAbs]
    protected def getDefaultRep = WArrayImpl
  }

  implicit def proxyWArrayImpl[T](p: Rep[WArrayImpl[T]]): WArrayImpl[T] =
    proxyOps[WArrayImpl[T]](p)

  implicit class ExtendedWArrayImpl[T](p: Rep[WArrayImpl[T]])(implicit eeT: Elem[T]) {
    def toData: Rep[WArrayImplData[T]] = isoWArrayImpl(eeT).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWArrayImpl[T](implicit eeT: Elem[T]): Iso[WArrayImplData[T], WArrayImpl[T]] =
    reifyObject(new WArrayImplIso[T]()(eeT))

  // 6) smart constructor and deconstructor
  def mkWArrayImpl[T](wrappedValue: Rep[Array[T]])(implicit eeT: Elem[T]): Rep[WArrayImpl[T]]
  def unmkWArrayImpl[T](p: Rep[WArray[T]]): Option[(Rep[Array[T]])]

  registerModule(WArrays_Module)
}

// Exp -----------------------------------
trait WArraysExp extends ScalanExp with WArraysDsl {
  self: WrappersDslExp =>

  lazy val WArray: Rep[WArrayCompanionAbs] = new WArrayCompanionAbs {
  }

  case class ExpWArrayImpl[T]
      (override val wrappedValue: Rep[Array[T]])(implicit eeT: Elem[T])
    extends WArrayImpl[T](wrappedValue)

  object WArrayImplMethods {
  }

  def mkWArrayImpl[T]
    (wrappedValue: Rep[Array[T]])(implicit eeT: Elem[T]): Rep[WArrayImpl[T]] = {
    new ExpWArrayImpl[T](wrappedValue)
  }
  def unmkWArrayImpl[T](p: Rep[WArray[T]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WArrayImplElem[T] @unchecked =>
      Some((p.asRep[WArrayImpl[T]].wrappedValue))
    case _ =>
      None
  }

  object WArrayMethods {
    object wrappedValue {
      def unapply(d: Def[_]): Option[Rep[WArray[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WArrayElem[_, _]] && method.getName == "wrappedValue" =>
          Some(receiver).asInstanceOf[Option[Rep[WArray[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WArray[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object apply {
      def unapply(d: Def[_]): Option[(Rep[WArray[T]], Rep[Int]) forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(i, _*), _) if receiver.elem.isInstanceOf[WArrayElem[_, _]] && method.getName == "apply" =>
          Some((receiver, i)).asInstanceOf[Option[(Rep[WArray[T]], Rep[Int]) forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[WArray[T]], Rep[Int]) forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object length {
      def unapply(d: Def[_]): Option[Rep[WArray[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WArrayElem[_, _]] && method.getName == "length" =>
          Some(receiver).asInstanceOf[Option[Rep[WArray[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WArray[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WArrayCompanionMethods {
  }
}

object WArrays_Module extends scalan.ModuleInfo {
  val dump = "H4sIAAAAAAAAALVVv28cRRR+d/b5fpnENrJFCgdjbYSAZC8gQZBcIGOfUeBiW1krgSMCze3OXSbM7g677+w9inSkgA5RIVFEUFpIiAbRIyGK/APUFChOhFIkFREzs7/OFx8hRbYY7c6+/d6b7/ve2/07UAoDWAptwolnuhSJaen71RAN64Lv9Dldp92tg89P/vXd6ftFmG3D1FUSroe8DdX4phmJ7N5CpwWzG8xzmh4yHBiuhkAwW3GOhsrROCqHMfTVSguqxLNpiH4QIrwQf9ywfc6pjcz3Gsx1+0g6nDZaLEQZP9nxncGncB2KLZixfc8OKFJrjZMwpGGyX6EKnmXPVf082BJ5jkcL3AkIQ1mfzDETx1+kwhp4vjdwEY4lpW0JVZaMqdNISCLOu4LrNJMtKDNX+AGmWcsyw1XfSR8nPSI3YK51jeyShszaa1gYMK+nwASxPyE9uilDVHhJniGkvLszEDQBr4foHMoXCQAQUtXXdGVmTpqZkWYq0gyLBoxw9hlRL7cDPxpAfBUmACIFcfoxECkCbXqO8cUV+8MHVt0tqo8jVUtFV1SWQM+PcZjWR5L728Wvwnvv3DxXhFobaixc7YQYEBuHfZDwVSee56OuOaOQBD0p4fI4CXWWVRkz4pOq7buCeBIpIXNaKsWZzVAFq71nEn3GkF9GQdPQYiQK2XnHdZQ20xrhfPv2iTOnDprvFzMLJCmqEtKSLRWkoAhTl1eDgAwScLXOIBR2NMNqqUX5WvmP5BkNL96+6/x6Fq4UoZCQl+T6f3pJiLk3v/nlFN3+oQiVtvb3Bic9rZxiZ52Gdhsq/i4N4v3yLuHq7kj1yg7tkj7HhNNhMiYkGQhLY1tTUMXUirZ8IT1+PTbtpu9RY2PbuG/9/vW+8mQA0/GbuFcfsnP//HGsi9quCNN7ARGCOpcI79OU5NITEK+WkzpoYeiDE4VCUpl+jzBB6U4KNdnk1B1GV8v8I/AItdgBqsnzNEqnxXE+07784MAxn7u7uFeEqXeh1JUChC0odfy+56SGl5MSaYRvp3sjbpQGJwFxswG6S2TDy4ZEWEhF6SPjjUvJfiyFvJZAF5qdZT5VZyGpWH1mnvdiQDRe+Xl/j916aUOrMXz4x1oync8/3rgx//f3Hz+rR0ilw9Alwjj7BAMk7fenOCDgsJXqKvKy9l1cXVktS7kX5kbNYRzpvdrQtF3IIhTbtdjxlu/S2eV77KObX6Ju+kJ0+I+z1bkmB/yKxlnUOK+P1DrdjNZSMl4dLWtuzCwaKUhbQvba8djOa8PkxlYR+anfkuUvj9HcSliWsl9/8O3my7d++lMP0prSS/a9l/1sc3FiW1Vk1ymN1L2Zl/pGVkJuWRlaTiYvQmUvkSmpTa3vjaiZKil/xyN8XNDI/wIRsQijbgkAAA=="
}
}

trait WArraysDsl extends impl.WArraysAbs {self: WrappersDsl =>}
trait WArraysDslExp extends impl.WArraysExp {self: WrappersDslExp =>}
