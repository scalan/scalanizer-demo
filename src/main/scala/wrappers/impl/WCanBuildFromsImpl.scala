package wrappers

import scalan._
import impl._
import scala.collection.generic.CanBuildFrom
import scala.reflect.runtime.universe._
import scala.reflect._

package impl {
// Abs -----------------------------------
trait WCanBuildFromsAbs extends ScalanDsl with WCanBuildFroms {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo](p: Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]): WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo] = {
    proxyOps[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]](p)(scala.reflect.classTag[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]])
  }

  // TypeWrapper proxy
  //implicit def proxyCanBuildFrom[From:Elem, WCanBuildFromsElem:Elem, WCanBuildFromsTo:Elem](p: Rep[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]): WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo] =
  //  proxyOps[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]](p.asRep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]])

  implicit def unwrapValueOfWCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo](w: Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]): Rep[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] = w.wrappedValue

  implicit def canBuildFromElement[From:Elem, WCanBuildFromsElem:Elem, WCanBuildFromsTo:Elem]: Elem[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] =
    element[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]].asInstanceOf[WrapperElem[_, _]].baseElem.asInstanceOf[Elem[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]]

  // familyElem
  class WCanBuildFromElem[From, WCanBuildFromsElem, WCanBuildFromsTo, To <: WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]](implicit _eeFrom: Elem[From], _eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], _eeWCanBuildFromsTo: Elem[WCanBuildFromsTo])
    extends WrapperElem[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo], To] {
    def eeFrom = _eeFrom
    def eeWCanBuildFromsElem = _eeWCanBuildFromsElem
    def eeWCanBuildFromsTo = _eeWCanBuildFromsTo
    lazy val parent: Option[Elem[_]] = None
    lazy val typeArgs = TypeArgs("From" -> eeFrom, "WCanBuildFromsElem" -> eeWCanBuildFromsElem, "WCanBuildFromsTo" -> eeWCanBuildFromsTo)
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagFrom = eeFrom.tag
      implicit val tagWCanBuildFromsElem = eeWCanBuildFromsElem.tag
      implicit val tagWCanBuildFromsTo = eeWCanBuildFromsTo.tag
      weakTypeTag[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Def[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] => convertWCanBuildFrom(x) }
      tryConvert(element[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]], this, x, conv)
    }

    def convertWCanBuildFrom(x: Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]): Rep[To] = {
      x.selfType1 match {
        case _: WCanBuildFromElem[_, _, _, _] => x.asRep[To]
        case e => !!!(s"Expected $x to have WCanBuildFromElem[_, _, _, _], but got $e", x)
      }
    }
    lazy val baseElem = {
            implicit val wFrom = element[From].tag
      implicit val wWCanBuildFromsElem = element[WCanBuildFromsElem].tag
      implicit val wWCanBuildFromsTo = element[WCanBuildFromsTo].tag
      new BaseTypeElem[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo], WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]](this.asInstanceOf[Elem[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]])
    }
    lazy val eTo: Elem[_] = new WCanBuildFromImplElem[From, WCanBuildFromsElem, WCanBuildFromsTo](isoWCanBuildFromImpl(eeFrom, eeWCanBuildFromsElem, eeWCanBuildFromsTo))(eeFrom, eeWCanBuildFromsElem, eeWCanBuildFromsTo)
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wCanBuildFromElement[From, WCanBuildFromsElem, WCanBuildFromsTo](implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo]): Elem[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] =
    elemCache.getOrElseUpdate(
      (classOf[WCanBuildFromElem[From, WCanBuildFromsElem, WCanBuildFromsTo, WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]], Seq(eeFrom, eeWCanBuildFromsElem, eeWCanBuildFromsTo)),
      new WCanBuildFromElem[From, WCanBuildFromsElem, WCanBuildFromsTo, WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]).asInstanceOf[Elem[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]]

  implicit case object WCanBuildFromCompanionElem extends CompanionElem[WCanBuildFromCompanionAbs] {
    lazy val tag = weakTypeTag[WCanBuildFromCompanionAbs]
    protected def getDefaultRep = WCanBuildFrom
  }

  abstract class WCanBuildFromCompanionAbs extends CompanionDef[WCanBuildFromCompanionAbs] with WCanBuildFromCompanion {
    def selfType = WCanBuildFromCompanionElem
    override def toString = "WCanBuildFrom"
  }
  def WCanBuildFrom: Rep[WCanBuildFromCompanionAbs]
  implicit def proxyWCanBuildFromCompanionAbs(p: Rep[WCanBuildFromCompanionAbs]): WCanBuildFromCompanionAbs =
    proxyOps[WCanBuildFromCompanionAbs](p)

  // default wrapper implementation
  abstract class WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](val wrappedValue: Rep[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]])(implicit val eeFrom: Elem[From], val eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], val eeWCanBuildFromsTo: Elem[WCanBuildFromsTo]) extends WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo] with Def[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]] {
    lazy val selfType = element[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]]
  }
  trait WCanBuildFromImplCompanion
  // elem for concrete class
  class WCanBuildFromImplElem[From, WCanBuildFromsElem, WCanBuildFromsTo](val iso: Iso[WCanBuildFromImplData[From, WCanBuildFromsElem, WCanBuildFromsTo], WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]])(implicit override val eeFrom: Elem[From], override val eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], override val eeWCanBuildFromsTo: Elem[WCanBuildFromsTo])
    extends WCanBuildFromElem[From, WCanBuildFromsElem, WCanBuildFromsTo, WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]]
    with ConcreteElem[WCanBuildFromImplData[From, WCanBuildFromsElem, WCanBuildFromsTo], WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]] {
    override lazy val parent: Option[Elem[_]] = Some(wCanBuildFromElement(element[From], element[WCanBuildFromsElem], element[WCanBuildFromsTo]))
    override lazy val typeArgs = TypeArgs("From" -> eeFrom, "WCanBuildFromsElem" -> eeWCanBuildFromsElem, "WCanBuildFromsTo" -> eeWCanBuildFromsTo)
    override lazy val eTo: Elem[_] = this
    override def convertWCanBuildFrom(x: Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]) = WCanBuildFromImpl(x.wrappedValue)
    override def getDefaultRep = WCanBuildFromImpl(DefaultOfCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo])
    override lazy val tag = {
      implicit val tagFrom = eeFrom.tag
      implicit val tagWCanBuildFromsElem = eeWCanBuildFromsElem.tag
      implicit val tagWCanBuildFromsTo = eeWCanBuildFromsTo.tag
      weakTypeTag[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]]
    }
  }

  // state representation type
  type WCanBuildFromImplData[From, WCanBuildFromsElem, WCanBuildFromsTo] = CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]

  // 3) Iso for concrete class
  class WCanBuildFromImplIso[From, WCanBuildFromsElem, WCanBuildFromsTo](implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo])
    extends EntityIso[WCanBuildFromImplData[From, WCanBuildFromsElem, WCanBuildFromsTo], WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]] with Def[WCanBuildFromImplIso[From, WCanBuildFromsElem, WCanBuildFromsTo]] {
    override def from(p: Rep[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]]) =
      p.wrappedValue
    override def to(p: Rep[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]) = {
      val wrappedValue = p
      WCanBuildFromImpl(wrappedValue)
    }
    lazy val eFrom = element[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]
    lazy val eTo = new WCanBuildFromImplElem[From, WCanBuildFromsElem, WCanBuildFromsTo](self)
    lazy val selfType = new WCanBuildFromImplIsoElem[From, WCanBuildFromsElem, WCanBuildFromsTo](eeFrom, eeWCanBuildFromsElem, eeWCanBuildFromsTo)
    def productArity = 3
    def productElement(n: Int) = n match {
      case 0 => eeFrom
      case 1 => eeWCanBuildFromsElem
      case 2 => eeWCanBuildFromsTo
    }
  }
  case class WCanBuildFromImplIsoElem[From, WCanBuildFromsElem, WCanBuildFromsTo](eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo]) extends Elem[WCanBuildFromImplIso[From, WCanBuildFromsElem, WCanBuildFromsTo]] {
    def isEntityType = true
    def getDefaultRep = reifyObject(new WCanBuildFromImplIso[From, WCanBuildFromsElem, WCanBuildFromsTo]()(eeFrom, eeWCanBuildFromsElem, eeWCanBuildFromsTo))
    lazy val tag = {
      implicit val tagFrom = eeFrom.tag
      implicit val tagWCanBuildFromsElem = eeWCanBuildFromsElem.tag
      implicit val tagWCanBuildFromsTo = eeWCanBuildFromsTo.tag
      weakTypeTag[WCanBuildFromImplIso[From, WCanBuildFromsElem, WCanBuildFromsTo]]
    }
    lazy val typeArgs = TypeArgs("From" -> eeFrom, "WCanBuildFromsElem" -> eeWCanBuildFromsElem, "WCanBuildFromsTo" -> eeWCanBuildFromsTo)
  }
  // 4) constructor and deconstructor
  class WCanBuildFromImplCompanionAbs extends CompanionDef[WCanBuildFromImplCompanionAbs] {
    def selfType = WCanBuildFromImplCompanionElem
    override def toString = "WCanBuildFromImpl"

    @scalan.OverloadId("fromFields")
    def apply[From, WCanBuildFromsElem, WCanBuildFromsTo](wrappedValue: Rep[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]])(implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo]): Rep[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]] =
      mkWCanBuildFromImpl(wrappedValue)

    def unapply[From, WCanBuildFromsElem, WCanBuildFromsTo](p: Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]) = unmkWCanBuildFromImpl(p)
  }
  lazy val WCanBuildFromImplRep: Rep[WCanBuildFromImplCompanionAbs] = new WCanBuildFromImplCompanionAbs
  lazy val WCanBuildFromImpl: WCanBuildFromImplCompanionAbs = proxyWCanBuildFromImplCompanion(WCanBuildFromImplRep)
  implicit def proxyWCanBuildFromImplCompanion(p: Rep[WCanBuildFromImplCompanionAbs]): WCanBuildFromImplCompanionAbs = {
    proxyOps[WCanBuildFromImplCompanionAbs](p)
  }

  implicit case object WCanBuildFromImplCompanionElem extends CompanionElem[WCanBuildFromImplCompanionAbs] {
    lazy val tag = weakTypeTag[WCanBuildFromImplCompanionAbs]
    protected def getDefaultRep = WCanBuildFromImpl
  }

  implicit def proxyWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](p: Rep[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]]): WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo] =
    proxyOps[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]](p)

  implicit class ExtendedWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](p: Rep[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]])(implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo]) {
    def toData: Rep[WCanBuildFromImplData[From, WCanBuildFromsElem, WCanBuildFromsTo]] = isoWCanBuildFromImpl(eeFrom, eeWCanBuildFromsElem, eeWCanBuildFromsTo).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo]): Iso[WCanBuildFromImplData[From, WCanBuildFromsElem, WCanBuildFromsTo], WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]] =
    reifyObject(new WCanBuildFromImplIso[From, WCanBuildFromsElem, WCanBuildFromsTo]()(eeFrom, eeWCanBuildFromsElem, eeWCanBuildFromsTo))

  // 6) smart constructor and deconstructor
  def mkWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](wrappedValue: Rep[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]])(implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo]): Rep[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]]
  def unmkWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](p: Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]): Option[(Rep[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]])]

  registerModule(WCanBuildFroms_Module)
}

// Exp -----------------------------------
trait WCanBuildFromsExp extends ScalanExp with WCanBuildFromsDsl {
  self: WrappersDslExp =>

  lazy val WCanBuildFrom: Rep[WCanBuildFromCompanionAbs] = new WCanBuildFromCompanionAbs {
  }

  case class ExpWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]
      (override val wrappedValue: Rep[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]])(implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo])
    extends WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](wrappedValue)

  object WCanBuildFromImplMethods {
  }

  def mkWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]
    (wrappedValue: Rep[CanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]])(implicit eeFrom: Elem[From], eeWCanBuildFromsElem: Elem[WCanBuildFromsElem], eeWCanBuildFromsTo: Elem[WCanBuildFromsTo]): Rep[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]] =
    new ExpWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](wrappedValue)
  def unmkWCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo](p: Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WCanBuildFromImplElem[From, WCanBuildFromsElem, WCanBuildFromsTo] @unchecked =>
      Some((p.asRep[WCanBuildFromImpl[From, WCanBuildFromsElem, WCanBuildFromsTo]].wrappedValue))
    case _ =>
      None
  }

  object WCanBuildFromMethods {
    object wrappedValue {
      def unapply(d: Def[_]): Option[Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] forSome {type From; type WCanBuildFromsElem; type WCanBuildFromsTo}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WCanBuildFromElem[_, _, _, _]] && method.getName == "wrappedValue" =>
          Some(receiver).asInstanceOf[Option[Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] forSome {type From; type WCanBuildFromsElem; type WCanBuildFromsTo}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WCanBuildFrom[From, WCanBuildFromsElem, WCanBuildFromsTo]] forSome {type From; type WCanBuildFromsElem; type WCanBuildFromsTo}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WCanBuildFromCompanionMethods {
  }
}

object WCanBuildFroms_Module extends scalan.ModuleInfo {
  val dump = "H4sIAAAAAAAAALVWzW8bRRR/63zYjk1IQkiJBGqaulRCYFdwKFIEUuo4fMhNom5EkVOCxrtjd8vszLA7TtcceqwE3BASEhKHIo4VCHHjjoQ48A9w7qmAUA+thARiZvbLu4mbgoQPo92Zt7/38fu9N779G0z5HjzrW4ggWnexQHVTP6/7oma2qHDE8CKzBwRv4N7+61/+ued+eKIAcx2Yvor8DZ90oBw+tAKePJvCbkMZUQv7gnm+gFNt7aFhMUKwJRxGG47rDgTqEtxoO75Ya8Nkl9nD9+EGGG2Ysxi1PCyw2STI97Ef7ZewishJ3sv6fbjNUx+0obJojGSx6yFHyPClj7nQ/hLm5pAyOnQFzEahbXMVlrSp4IDLHN5wOdFuJtpQdFzOPBF7LUoPV5kdv05SJDdgoX0NHaCG9NpvmMJzaF+BcWS9h/p4S5oo80mZg49Jb3fIcQRe8YWd8RdwAJCsvKgDq6c1qyc1q6ua1UzsOYg4HyB1uOOxYAjhz5gACLiEeP4YiBgBt6hd++iKtffArLgF9XGgQinqgKYl0MkxCtH0yNr+eOkT/95rt84XYKYDM46/3vWFhywxKoOoXBVEKRM65qSCyOtLBlfHMai9rEubnEzKFnM5ohIpqmVVEkUcyxHKWO1VI3rG1L4oOI5NjYAbSb4rY/LVWmoiQnbuLr9w5tfW2wUoZF2UJaQpm8GLQQU8drmJ6IWBQ+xNj7mRD7U+LmBSbaW1ljsLWesWwdnzuez5LtOnaikH6Vp8SBJJOc/e/d3+4RxcKSQkRDE/Gu8SYuHlz78/g3e+LkCpo9tkk6C+VoCq8gb2rQ6U2AH2wv3iASLq6UgVFG3cQwMiIm5GizohiypgZWyHc6wqvqY7x4jTr4Ti32IU1zZ3avfNnz69rbTtQTU8CVv+b+f8X7/M9oSWvYDqdQ9xju23EBnguObVRydQLUu596eOpkgtp7TV8gjm04YR5aDPBUxjnLhVDpUiRmNQy+KIh6NRFjE+LKwUoJFHXDoWcSGPuMsegne4BgLmM9+rCZiWRKnvmXFdqLuWnjAvfvbNyf0CTL0JUz0pK78NU102oHY8DuQ1InAgLsR7RlZWsv2Rh9xY9eHwXAEdRBLuYkrmq0dbLB1rcTj5YzssvrW+vXnzyT++evcJPVlLXUe4iNfO/Yu5Go/B/3FuQrYlKsrysm6jMLpptZxOtTCfF8fZ/9RA5ZFLajmxUK0/Eza4yVw8v3rP2b/1sdAzzgiy9/R295q8F9c0zorGaeZyqbaCZlysl/Jhz48ZvbmAtBbkaFnKyL05ykEoFZ4WZ1tmsTpGGmZEhlTHjQdfbD3383d39DU0o2iV044m/1RSDgONXZTjQ1Gpnl9JI95IQkglK01ns+0toHQ9IjUKUa17Oe5j3uVfmlx13tEO/gFRTjMdcwoAAA=="
}
}

trait WCanBuildFromsDsl extends impl.WCanBuildFromsAbs {self: WrappersDsl =>}
trait WCanBuildFromsDslStd extends impl.WCanBuildFromsStd {self: WrappersDslStd =>}
trait WCanBuildFromsDslExp extends impl.WCanBuildFromsExp {self: WrappersDslExp =>}
