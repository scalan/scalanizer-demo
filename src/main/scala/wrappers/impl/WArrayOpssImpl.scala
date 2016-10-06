package wrappers

import scalan._
import impl._
import scala.collection.mutable.ArrayOps
import scala.reflect.runtime.universe._
import scala.reflect._

package impl {
// Abs -----------------------------------
trait WArrayOpssAbs extends ScalanDsl with WArrayOpss {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWArrayOps[T](p: Rep[WArrayOps[T]]): WArrayOps[T] = {
    proxyOps[WArrayOps[T]](p)(scala.reflect.classTag[WArrayOps[T]])
  }

  // TypeWrapper proxy
  //implicit def proxyArrayOps[T:Elem](p: Rep[ArrayOps[T]]): WArrayOps[T] =
  //  proxyOps[WArrayOps[T]](p.asRep[WArrayOps[T]])

  implicit def unwrapValueOfWArrayOps[T](w: Rep[WArrayOps[T]]): Rep[ArrayOps[T]] = w.wrappedValue

  implicit def arrayOpsElement[T:Elem]: Elem[ArrayOps[T]] =
    element[WArrayOps[T]].asInstanceOf[WrapperElem[_, _]].baseElem.asInstanceOf[Elem[ArrayOps[T]]]

  // familyElem
  class WArrayOpsElem[T, To <: WArrayOps[T]](implicit _eeT: Elem[T])
    extends WrapperElem[ArrayOps[T], To] {
    def eeT = _eeT
    lazy val parent: Option[Elem[_]] = None
    lazy val typeArgs = TypeArgs("T" -> eeT)
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WArrayOps[T]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Def[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WArrayOps[T]] => convertWArrayOps(x) }
      tryConvert(element[WArrayOps[T]], this, x, conv)
    }

    def convertWArrayOps(x: Rep[WArrayOps[T]]): Rep[To] = {
      x.selfType1 match {
        case _: WArrayOpsElem[_, _] => x.asRep[To]
        case e => !!!(s"Expected $x to have WArrayOpsElem[_, _], but got $e", x)
      }
    }
    lazy val baseElem = {
            implicit val wT = element[T].tag
      new BaseTypeElem[ArrayOps[T], WArrayOps[T]](this.asInstanceOf[Elem[WArrayOps[T]]])
    }
    lazy val eTo: Elem[_] = new WArrayOpsImplElem[T](isoWArrayOpsImpl(eeT))(eeT)
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wArrayOpsElement[T](implicit eeT: Elem[T]): Elem[WArrayOps[T]] =
    elemCache.getOrElseUpdate(
      (classOf[WArrayOpsElem[T, WArrayOps[T]]], Seq(eeT)),
      new WArrayOpsElem[T, WArrayOps[T]]).asInstanceOf[Elem[WArrayOps[T]]]

  implicit case object WArrayOpsCompanionElem extends CompanionElem[WArrayOpsCompanionAbs] {
    lazy val tag = weakTypeTag[WArrayOpsCompanionAbs]
    protected def getDefaultRep = WArrayOps
  }

  abstract class WArrayOpsCompanionAbs extends CompanionDef[WArrayOpsCompanionAbs] with WArrayOpsCompanion {
    def selfType = WArrayOpsCompanionElem
    override def toString = "WArrayOps"
  }
  def WArrayOps: Rep[WArrayOpsCompanionAbs]
  implicit def proxyWArrayOpsCompanionAbs(p: Rep[WArrayOpsCompanionAbs]): WArrayOpsCompanionAbs =
    proxyOps[WArrayOpsCompanionAbs](p)

  // default wrapper implementation
  abstract class WArrayOpsImpl[T](val wrappedValue: Rep[ArrayOps[T]])(implicit val eeT: Elem[T]) extends WArrayOps[T] with Def[WArrayOpsImpl[T]] {
    lazy val selfType = element[WArrayOpsImpl[T]]

    def zip[A1, B, That](that: Rep[WGenIterable[B]])(bf: Rep[WCanBuildFrom[WArray[T], (A1, B), That]])(emA1: Elem[A1], emB: Elem[B], emThat: Elem[That]): Rep[That] =
      methodCallEx[That](self,
        this.getClass.getMethod("zip", classOf[AnyRef], classOf[AnyRef], classOf[AnyRef], classOf[AnyRef], classOf[AnyRef]),
        List(that.asInstanceOf[AnyRef], bf.asInstanceOf[AnyRef], emA1.asInstanceOf[AnyRef], emB.asInstanceOf[AnyRef], emThat.asInstanceOf[AnyRef]))

    def reduce[A1](op: Rep[((A1, A1)) => A1])(emA1: Elem[A1]): Rep[A1] =
      methodCallEx[A1](self,
        this.getClass.getMethod("reduce", classOf[AnyRef], classOf[AnyRef]),
        List(op.asInstanceOf[AnyRef], emA1.asInstanceOf[AnyRef]))

    def map[B, That](f: Rep[T => B])(bf: Rep[WCanBuildFrom[WArray[T], B, That]])(emB: Elem[B], emThat: Elem[That]): Rep[That] =
      methodCallEx[That](self,
        this.getClass.getMethod("map", classOf[AnyRef], classOf[AnyRef], classOf[AnyRef], classOf[AnyRef]),
        List(f.asInstanceOf[AnyRef], bf.asInstanceOf[AnyRef], emB.asInstanceOf[AnyRef], emThat.asInstanceOf[AnyRef]))
  }
  trait WArrayOpsImplCompanion
  // elem for concrete class
  class WArrayOpsImplElem[T](val iso: Iso[WArrayOpsImplData[T], WArrayOpsImpl[T]])(implicit override val eeT: Elem[T])
    extends WArrayOpsElem[T, WArrayOpsImpl[T]]
    with ConcreteElem[WArrayOpsImplData[T], WArrayOpsImpl[T]] {
    override lazy val parent: Option[Elem[_]] = Some(wArrayOpsElement(element[T]))
    override lazy val typeArgs = TypeArgs("T" -> eeT)
    override lazy val eTo: Elem[_] = this
    override def convertWArrayOps(x: Rep[WArrayOps[T]]) = WArrayOpsImpl(x.wrappedValue)
    override def getDefaultRep = WArrayOpsImpl(DefaultOfArrayOps[T])
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WArrayOpsImpl[T]]
    }
  }

  // state representation type
  type WArrayOpsImplData[T] = ArrayOps[T]

  // 3) Iso for concrete class
  class WArrayOpsImplIso[T](implicit eeT: Elem[T])
    extends EntityIso[WArrayOpsImplData[T], WArrayOpsImpl[T]] with Def[WArrayOpsImplIso[T]] {
    override def from(p: Rep[WArrayOpsImpl[T]]) =
      p.wrappedValue
    override def to(p: Rep[ArrayOps[T]]) = {
      val wrappedValue = p
      WArrayOpsImpl(wrappedValue)
    }
    lazy val eFrom = element[ArrayOps[T]]
    lazy val eTo = new WArrayOpsImplElem[T](self)
    lazy val selfType = new WArrayOpsImplIsoElem[T](eeT)
    def productArity = 1
    def productElement(n: Int) = eeT
  }
  case class WArrayOpsImplIsoElem[T](eeT: Elem[T]) extends Elem[WArrayOpsImplIso[T]] {
    def isEntityType = true
    def getDefaultRep = reifyObject(new WArrayOpsImplIso[T]()(eeT))
    lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WArrayOpsImplIso[T]]
    }
    lazy val typeArgs = TypeArgs("T" -> eeT)
  }
  // 4) constructor and deconstructor
  class WArrayOpsImplCompanionAbs extends CompanionDef[WArrayOpsImplCompanionAbs] {
    def selfType = WArrayOpsImplCompanionElem
    override def toString = "WArrayOpsImpl"

    @scalan.OverloadId("fromFields")
    def apply[T](wrappedValue: Rep[ArrayOps[T]])(implicit eeT: Elem[T]): Rep[WArrayOpsImpl[T]] =
      mkWArrayOpsImpl(wrappedValue)

    def unapply[T](p: Rep[WArrayOps[T]]) = unmkWArrayOpsImpl(p)
  }
  lazy val WArrayOpsImplRep: Rep[WArrayOpsImplCompanionAbs] = new WArrayOpsImplCompanionAbs
  lazy val WArrayOpsImpl: WArrayOpsImplCompanionAbs = proxyWArrayOpsImplCompanion(WArrayOpsImplRep)
  implicit def proxyWArrayOpsImplCompanion(p: Rep[WArrayOpsImplCompanionAbs]): WArrayOpsImplCompanionAbs = {
    proxyOps[WArrayOpsImplCompanionAbs](p)
  }

  implicit case object WArrayOpsImplCompanionElem extends CompanionElem[WArrayOpsImplCompanionAbs] {
    lazy val tag = weakTypeTag[WArrayOpsImplCompanionAbs]
    protected def getDefaultRep = WArrayOpsImpl
  }

  implicit def proxyWArrayOpsImpl[T](p: Rep[WArrayOpsImpl[T]]): WArrayOpsImpl[T] =
    proxyOps[WArrayOpsImpl[T]](p)

  implicit class ExtendedWArrayOpsImpl[T](p: Rep[WArrayOpsImpl[T]])(implicit eeT: Elem[T]) {
    def toData: Rep[WArrayOpsImplData[T]] = isoWArrayOpsImpl(eeT).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWArrayOpsImpl[T](implicit eeT: Elem[T]): Iso[WArrayOpsImplData[T], WArrayOpsImpl[T]] =
    reifyObject(new WArrayOpsImplIso[T]()(eeT))

  // 6) smart constructor and deconstructor
  def mkWArrayOpsImpl[T](wrappedValue: Rep[ArrayOps[T]])(implicit eeT: Elem[T]): Rep[WArrayOpsImpl[T]]
  def unmkWArrayOpsImpl[T](p: Rep[WArrayOps[T]]): Option[(Rep[ArrayOps[T]])]

  registerModule(WArrayOpss_Module)
}

// Exp -----------------------------------
trait WArrayOpssExp extends ScalanExp with WArrayOpssDsl {
  self: WrappersDslExp =>

  lazy val WArrayOps: Rep[WArrayOpsCompanionAbs] = new WArrayOpsCompanionAbs {
  }

  case class ExpWArrayOpsImpl[T]
      (override val wrappedValue: Rep[ArrayOps[T]])(implicit eeT: Elem[T])
    extends WArrayOpsImpl[T](wrappedValue)

  object WArrayOpsImplMethods {
  }

  def mkWArrayOpsImpl[T]
    (wrappedValue: Rep[ArrayOps[T]])(implicit eeT: Elem[T]): Rep[WArrayOpsImpl[T]] =
    new ExpWArrayOpsImpl[T](wrappedValue)
  def unmkWArrayOpsImpl[T](p: Rep[WArrayOps[T]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WArrayOpsImplElem[T] @unchecked =>
      Some((p.asRep[WArrayOpsImpl[T]].wrappedValue))
    case _ =>
      None
  }

  object WArrayOpsMethods {
    object wrappedValue {
      def unapply(d: Def[_]): Option[Rep[WArrayOps[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WArrayOpsElem[_, _]] && method.getName == "wrappedValue" =>
          Some(receiver).asInstanceOf[Option[Rep[WArrayOps[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WArrayOps[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object zip {
      def unapply(d: Def[_]): Option[(Rep[WArrayOps[T]], Rep[WGenIterable[B]], Rep[WCanBuildFrom[WArray[T], (A1, B), That]], Elem[A1], Elem[B], Elem[That]) forSome {type T; type A1; type B; type That}] = d match {
        case MethodCall(receiver, method, Seq(that, bf, emA1, emB, emThat, _*), _) if receiver.elem.isInstanceOf[WArrayOpsElem[_, _]] && method.getName == "zip" =>
          Some((receiver, that, bf, emA1, emB, emThat)).asInstanceOf[Option[(Rep[WArrayOps[T]], Rep[WGenIterable[B]], Rep[WCanBuildFrom[WArray[T], (A1, B), That]], Elem[A1], Elem[B], Elem[That]) forSome {type T; type A1; type B; type That}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[WArrayOps[T]], Rep[WGenIterable[B]], Rep[WCanBuildFrom[WArray[T], (A1, B), That]], Elem[A1], Elem[B], Elem[That]) forSome {type T; type A1; type B; type That}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    // WARNING: Cannot generate matcher for method `reduce`: Method has function arguments op

    // WARNING: Cannot generate matcher for method `map`: Method has function arguments f
  }

  object WArrayOpsCompanionMethods {
  }
}

object WArrayOpss_Module extends scalan.ModuleInfo {
  val dump = "H4sIAAAAAAAAALVVzYsURxR/Pfsxn9HdNYsJRHZdxwiiM+rFwJ42u6MxjLuLvWgYZaWmu2ZsU11dqapZe3LwKCS5hUAgkIMhR0kIueUeCDnkH8jZkx/IHhQCSqqqv2ZGx4+Dcyiqq9+896vf7/de33kAU4LDh8JBBNGajyWq2Wa/ImTVblDpyf75wO0RvIY725/89N9l/6v9OZhpwfQ1JNYEaUEx2jRClu5t6TahiKiDhQy4kHCwaSrUnYAQ7EgvoHXP93sStQmuNz0hl5sw2Q7c/hdwE6wmzDgBdTiW2F4lSAgs4vMC1oi89LlonvsbLKtB6/oW9YFbbHHkSQVf1ZiJ4i9gZvdpQPu+hD0xtA2mYamYMg6ZusM5nxFTZqIJec9nAZdJ1byqcC1wk8dJitQBzDWvox1UV1W7dVtyj3Z1Moacz1EXr6sQHT6p7iAw6Wz1GY6Tl4V0h+qFDACUKqcMsFrGWS3lrKY5q9qYe4h4XyL9cpMHYR+inzUBEDKV4tgrUiQZcIO61a+vOJef2GU/p/8caih5A2haJVoY4xAjj+L2rwvfit2zt0/noNSCkidW2kJy5MhBG8R0lRGlgTSYUwYR7yoFl8YpaKqsqJgRmxSdwGeIqkwxlxUlFPEcT+pgfVaJ5RnDfV4ynIRaIbPS+y6Oua/x0ioiZPPe+8cP3298loPccImiSmmrZuBJUgnFSyucI+3SOL9e90qwtgzJeimG2Zp/Sf2UiSP3Hrp/noAruZS/uNzrSaZSzH30wx+H8eYvOSi0jMPPENQ14mmC1rBwWlAIdjCPzvM7iOjdCwXMu7iDekTGtA7yMaH4kLA4tjkZ1mQtG9NbyfXLkW/XA4qrZzarj+2/v7ujbcmhEr2JuvWZd/rpv3s60jhWQuUGR4xh9yIiPZyQXHgz7vVywATND/zhPcuKwZn3EiYw3kpSTTYI9gez6+Xd59JLeCf1gW72rJJW68A4wxmD0v32+e9/XdjOwdSnMNVRMogmTLWDHnUT56uJKXEoP07OrGEZlNMRR37ikmhOLIIBkUJ9HvQr3ZQM199u3Zp/9PPVfWYAFNqe9BGrnniD9k+69S22NwxboKwjLxnLROim9bKQaTg7KuqhF3qmODAr59MIbeNSZFY78PHs0q63ffsbafrVCoc/Fxvt62o8L5s8H5g8J0ewVhrhakLGyVFYs2PGyAggo7Vqk7nUhquD/EY2YNnFl9UNlsbIbsdEK+VvPvlx/eg/v981k7CkJVNdS9OPZaZPaHLnVcNomfT+eIb2VAohs6MKLWWjU7XyjVisGJ5ez45omuipvqgjrJwzyf8HybGVMfIIAAA="
}
}

trait WArrayOpssDsl extends impl.WArrayOpssAbs {self: WrappersDsl =>}
trait WArrayOpssDslStd extends impl.WArrayOpssStd {self: WrappersDslStd =>}
trait WArrayOpssDslExp extends impl.WArrayOpssExp {self: WrappersDslExp =>}
