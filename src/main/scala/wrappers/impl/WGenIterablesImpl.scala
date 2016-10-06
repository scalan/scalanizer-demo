package wrappers

import scalan._
import impl._
import scala.collection.GenIterable
import scala.reflect.runtime.universe._
import scala.reflect._

package impl {
// Abs -----------------------------------
trait WGenIterablesAbs extends ScalanDsl with WGenIterables {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWGenIterable[A](p: Rep[WGenIterable[A]]): WGenIterable[A] = {
    proxyOps[WGenIterable[A]](p)(scala.reflect.classTag[WGenIterable[A]])
  }

  // TypeWrapper proxy
  //implicit def proxyGenIterable[A:Elem](p: Rep[GenIterable[A]]): WGenIterable[A] =
  //  proxyOps[WGenIterable[A]](p.asRep[WGenIterable[A]])

  implicit def unwrapValueOfWGenIterable[A](w: Rep[WGenIterable[A]]): Rep[GenIterable[A]] = w.wrappedValue

  implicit def genIterableElement[A:Elem]: Elem[GenIterable[A]] =
    element[WGenIterable[A]].asInstanceOf[WrapperElem[_, _]].baseElem.asInstanceOf[Elem[GenIterable[A]]]

  // familyElem
  class WGenIterableElem[A, To <: WGenIterable[A]](implicit _eeA: Elem[A])
    extends WrapperElem[GenIterable[A], To] {
    def eeA = _eeA
    lazy val parent: Option[Elem[_]] = None
    lazy val typeArgs = TypeArgs("A" -> eeA)
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagA = eeA.tag
      weakTypeTag[WGenIterable[A]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Def[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WGenIterable[A]] => convertWGenIterable(x) }
      tryConvert(element[WGenIterable[A]], this, x, conv)
    }

    def convertWGenIterable(x: Rep[WGenIterable[A]]): Rep[To] = {
      x.selfType1 match {
        case _: WGenIterableElem[_, _] => x.asRep[To]
        case e => !!!(s"Expected $x to have WGenIterableElem[_, _], but got $e", x)
      }
    }
    lazy val baseElem = {
            implicit val wA = element[A].tag
      new BaseTypeElem[GenIterable[A], WGenIterable[A]](this.asInstanceOf[Elem[WGenIterable[A]]])
    }
    lazy val eTo: Elem[_] = new WGenIterableImplElem[A](isoWGenIterableImpl(eeA))(eeA)
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wGenIterableElement[A](implicit eeA: Elem[A]): Elem[WGenIterable[A]] =
    elemCache.getOrElseUpdate(
      (classOf[WGenIterableElem[A, WGenIterable[A]]], Seq(eeA)),
      new WGenIterableElem[A, WGenIterable[A]]).asInstanceOf[Elem[WGenIterable[A]]]

  implicit case object WGenIterableCompanionElem extends CompanionElem[WGenIterableCompanionAbs] {
    lazy val tag = weakTypeTag[WGenIterableCompanionAbs]
    protected def getDefaultRep = WGenIterable
  }

  abstract class WGenIterableCompanionAbs extends CompanionDef[WGenIterableCompanionAbs] with WGenIterableCompanion {
    def selfType = WGenIterableCompanionElem
    override def toString = "WGenIterable"
  }
  def WGenIterable: Rep[WGenIterableCompanionAbs]
  implicit def proxyWGenIterableCompanionAbs(p: Rep[WGenIterableCompanionAbs]): WGenIterableCompanionAbs =
    proxyOps[WGenIterableCompanionAbs](p)

  // default wrapper implementation
  abstract class WGenIterableImpl[A](val wrappedValue: Rep[GenIterable[A]])(implicit val eeA: Elem[A]) extends WGenIterable[A] with Def[WGenIterableImpl[A]] {
    lazy val selfType = element[WGenIterableImpl[A]]
  }
  trait WGenIterableImplCompanion
  // elem for concrete class
  class WGenIterableImplElem[A](val iso: Iso[WGenIterableImplData[A], WGenIterableImpl[A]])(implicit override val eeA: Elem[A])
    extends WGenIterableElem[A, WGenIterableImpl[A]]
    with ConcreteElem[WGenIterableImplData[A], WGenIterableImpl[A]] {
    override lazy val parent: Option[Elem[_]] = Some(wGenIterableElement(element[A]))
    override lazy val typeArgs = TypeArgs("A" -> eeA)
    override lazy val eTo: Elem[_] = this
    override def convertWGenIterable(x: Rep[WGenIterable[A]]) = WGenIterableImpl(x.wrappedValue)
    override def getDefaultRep = WGenIterableImpl(DefaultOfGenIterable[A])
    override lazy val tag = {
      implicit val tagA = eeA.tag
      weakTypeTag[WGenIterableImpl[A]]
    }
  }

  // state representation type
  type WGenIterableImplData[A] = GenIterable[A]

  // 3) Iso for concrete class
  class WGenIterableImplIso[A](implicit eeA: Elem[A])
    extends EntityIso[WGenIterableImplData[A], WGenIterableImpl[A]] with Def[WGenIterableImplIso[A]] {
    override def from(p: Rep[WGenIterableImpl[A]]) =
      p.wrappedValue
    override def to(p: Rep[GenIterable[A]]) = {
      val wrappedValue = p
      WGenIterableImpl(wrappedValue)
    }
    lazy val eFrom = element[GenIterable[A]]
    lazy val eTo = new WGenIterableImplElem[A](self)
    lazy val selfType = new WGenIterableImplIsoElem[A](eeA)
    def productArity = 1
    def productElement(n: Int) = eeA
  }
  case class WGenIterableImplIsoElem[A](eeA: Elem[A]) extends Elem[WGenIterableImplIso[A]] {
    def isEntityType = true
    def getDefaultRep = reifyObject(new WGenIterableImplIso[A]()(eeA))
    lazy val tag = {
      implicit val tagA = eeA.tag
      weakTypeTag[WGenIterableImplIso[A]]
    }
    lazy val typeArgs = TypeArgs("A" -> eeA)
  }
  // 4) constructor and deconstructor
  class WGenIterableImplCompanionAbs extends CompanionDef[WGenIterableImplCompanionAbs] {
    def selfType = WGenIterableImplCompanionElem
    override def toString = "WGenIterableImpl"

    @scalan.OverloadId("fromFields")
    def apply[A](wrappedValue: Rep[GenIterable[A]])(implicit eeA: Elem[A]): Rep[WGenIterableImpl[A]] =
      mkWGenIterableImpl(wrappedValue)

    def unapply[A](p: Rep[WGenIterable[A]]) = unmkWGenIterableImpl(p)
  }
  lazy val WGenIterableImplRep: Rep[WGenIterableImplCompanionAbs] = new WGenIterableImplCompanionAbs
  lazy val WGenIterableImpl: WGenIterableImplCompanionAbs = proxyWGenIterableImplCompanion(WGenIterableImplRep)
  implicit def proxyWGenIterableImplCompanion(p: Rep[WGenIterableImplCompanionAbs]): WGenIterableImplCompanionAbs = {
    proxyOps[WGenIterableImplCompanionAbs](p)
  }

  implicit case object WGenIterableImplCompanionElem extends CompanionElem[WGenIterableImplCompanionAbs] {
    lazy val tag = weakTypeTag[WGenIterableImplCompanionAbs]
    protected def getDefaultRep = WGenIterableImpl
  }

  implicit def proxyWGenIterableImpl[A](p: Rep[WGenIterableImpl[A]]): WGenIterableImpl[A] =
    proxyOps[WGenIterableImpl[A]](p)

  implicit class ExtendedWGenIterableImpl[A](p: Rep[WGenIterableImpl[A]])(implicit eeA: Elem[A]) {
    def toData: Rep[WGenIterableImplData[A]] = isoWGenIterableImpl(eeA).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWGenIterableImpl[A](implicit eeA: Elem[A]): Iso[WGenIterableImplData[A], WGenIterableImpl[A]] =
    reifyObject(new WGenIterableImplIso[A]()(eeA))

  // 6) smart constructor and deconstructor
  def mkWGenIterableImpl[A](wrappedValue: Rep[GenIterable[A]])(implicit eeA: Elem[A]): Rep[WGenIterableImpl[A]]
  def unmkWGenIterableImpl[A](p: Rep[WGenIterable[A]]): Option[(Rep[GenIterable[A]])]

  registerModule(WGenIterables_Module)
}

// Exp -----------------------------------
trait WGenIterablesExp extends ScalanExp with WGenIterablesDsl {
  self: WrappersDslExp =>

  lazy val WGenIterable: Rep[WGenIterableCompanionAbs] = new WGenIterableCompanionAbs {
  }

  case class ExpWGenIterableImpl[A]
      (override val wrappedValue: Rep[GenIterable[A]])(implicit eeA: Elem[A])
    extends WGenIterableImpl[A](wrappedValue)

  object WGenIterableImplMethods {
  }

  def mkWGenIterableImpl[A]
    (wrappedValue: Rep[GenIterable[A]])(implicit eeA: Elem[A]): Rep[WGenIterableImpl[A]] =
    new ExpWGenIterableImpl[A](wrappedValue)
  def unmkWGenIterableImpl[A](p: Rep[WGenIterable[A]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WGenIterableImplElem[A] @unchecked =>
      Some((p.asRep[WGenIterableImpl[A]].wrappedValue))
    case _ =>
      None
  }

  object WGenIterableMethods {
    object wrappedValue {
      def unapply(d: Def[_]): Option[Rep[WGenIterable[A]] forSome {type A}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WGenIterableElem[_, _]] && method.getName == "wrappedValue" =>
          Some(receiver).asInstanceOf[Option[Rep[WGenIterable[A]] forSome {type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WGenIterable[A]] forSome {type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WGenIterableCompanionMethods {
  }
}

object WGenIterables_Module extends scalan.ModuleInfo {
  val dump = "H4sIAAAAAAAAALVVz28bRRR+6/ywHZs2aYkKElVCcEFCrd32UqScTOKGIDeJuhVFbhU03h27W2Znh53ndM2hx0rADSEhIXEo4liBEDfuSIgD/wBnTgWEeqASEhUzsz+8dmvaHurDaHb2+b1vvu97b+/8AXMyhFelQxjhdZ8iqdtm35RYs1scPRxeCNwBo5u0t//WV/9c8T86VoDFDsxfI3JTsg6U400rEtneRrcNZcIdKjEIJcLLbVOh4QSMUQe9gDc83x8g6TLaaHsS19sw2w3c4QdwE6w2LDoBd0KK1N5gREoqk/MS1Yi87Llsnoe7YlSDN/QtGrlbXAqJhwq+qrEYx1+kwh7ygA99hEMJtF2hYamYCo2EusO2L5gpM9OGoueLIMS0alFVuBa46eMsJ+oAjrSvkwPSUFX7DRtDj/d1MkGc90mf7qgQHT6r7iAp610aCpokr0h0x+pFAgCUKmcNsPqIs3rGWV1zVrNp6BHmfUj0y70wiIYQ/6wZgEioFCcfkyLNQFvcrX181bly3674Bf3nSEMpGkDzKtHKFIcYeRS3P138VN7bun2uAAsdWPBksysxJA7mbZDQVSGcB2gwZwySsK8UXJumoKnSVDETNik7gS8IV5kSLqtKKOY5HupgfVZN5JnCfREFTUOtSFjZfVen3Nd4aYMwtnf3xVMnfm+9W4DCeImySmmrZgjTpAjVy1uUbyMNNe9JCb0eRrCahme9lKPRWvwfCBkZr9390/3xNFwtZBQmFZ9MNZXiyBtf/HCC7n1TgFLHmPw8I32jn+Zok0qnA6XggIbxefGAML17pIZFl/bIgGHCbJ6SGUUJwurU/hRU87VufG+l16/E1t0JOK2d36v9bf/82R3tzBCq8Zu4YR945/799VAPjWkV1TdCIgR13yFsQFOSK09Nv16Om6Dl3B9esKwEn3mPMENpM00122LUz2fXy/MPpUdYzLtBd/2omNbs+DTnGafyY/aFz79d2S/A3Nsw11NiyDbMdYMBd9MWUKMTaYRvpmfWuBjK8iQkfuqVeGCsggGRoX0Y92M9lU7Z727dWv7r6/eOmklQ6nroE1E7/RRzIG3bZ9jnMO6Cio68bIwTo5vXy8pIxqVJXV95pG3KuaG5nEVoMy/ElrUDny6t3fP2b3+CpmutaPy7sdu9rub0usnzkslzZgJrtRVtpGScmYS1NGWYTAAyWqtmWc47cSNPcewEMbr7urrE2hTl7YRrJf7N+1/uvP7L97+ZqbigVVPty7MP50iiyOQuqrbRSun9qRHgsxmEkSNV6HNjYxShdCORLEGo160JZVNV1Qd2gpttk/8/MHD9lgEJAAA="
}
}

trait WGenIterablesDsl extends impl.WGenIterablesAbs {self: WrappersDsl =>}
trait WGenIterablesDslStd extends impl.WGenIterablesStd {self: WrappersDslStd =>}
trait WGenIterablesDslExp extends impl.WGenIterablesExp {self: WrappersDslExp =>}
