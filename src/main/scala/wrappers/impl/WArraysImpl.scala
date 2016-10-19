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

//  implicit override def arrayElement[T:Elem]: Elem[Array[T]] =
//    element[WArray[T]].asInstanceOf[WrapperElem[_, _]].baseElem.asInstanceOf[Elem[Array[T]]]

  // familyElem
  class WArrayElem[T, To <: WArray[T]](implicit _eeT: Elem[T])
    extends WrapperElem[Array[T], To] {
    def eeT = _eeT
    lazy val parent: Option[Elem[_]] = None
    lazy val typeArgs = TypeArgs("T" -> eeT)
    override def isEntityType = true
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
      x.selfType1 match {
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
    override lazy val typeArgs = TypeArgs("T" -> eeT)
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
    def isEntityType = true
    def getDefaultRep = reifyObject(new WArrayImplIso[T]()(eeT))
    lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WArrayImplIso[T]]
    }
    lazy val typeArgs = TypeArgs("T" -> eeT)
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
    (wrappedValue: Rep[Array[T]])(implicit eeT: Elem[T]): Rep[WArrayImpl[T]] =
    new ExpWArrayImpl[T](wrappedValue)
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
  val dump = "H4sIAAAAAAAAALVVz48URRR+PbuzPb8Cu+AGTCS7roMmBmaACyR7WncHxAy7G3oDZCBrarprhobq6rKqZunxwJFEvRkTExMPGI9EY7x5NzEe/Ac8e0KN4SAJCYSq6l8zAwNycA6V6uo37331fd97fe8vKAoObwsXEUQbAZao4Zj9mpB1p0WlL4cXQm9A8Abu7b7/zaOrwSeHCjDfgbnrSGwI0oFyvGlFLNs70mtDGVEXCxlyIeHNtqnQdENCsCv9kDb9IBhI1CW42faFXG3DbDf0hh/BbbDaMO+G1OVYYmedICGwSM5LWCPys+eyeR5usbwGbepbNEduscORLxV8VWM+jr+ImTOkIR0GEvYl0LaYhqViqjhi6g7nA0ZMmZk22H7AQi7TqraqcD300sdZitQBHGjfQHuoqar2m47kPu3rZAy5N1Efb6oQHT6r7iAw6e0MGU6SV4X0xupFDACUKqcMsEbOWSPjrKE5qzuY+4j4HyP9cpuH0RDinzUDEDGV4thLUqQZcIt69U+vuVcfOtWgoP8caSi2ATSnEi1NcYiRR3H7y8XPxYNzd08XoNKBii/WukJy5MpRGyR0VRGloTSYMwYR7ysFV6YpaKqsqZgJm5TdMGCIqkwJlzUlFPFdX+pgfVZL5JnCvS0ZTkOtiFnZfZen3Nd4aR0Rsn3/9eNH/2xdKUBhvERZpXRUM/A0qYS5y2uco2GSXK/7JVg7hmG9lKN8tV9QPKPhnft/ez+fgGuFjLyk1n/TS6U4cOarn47i7e8KUOoYe58lqG+U0+xsYOF2oBTuYR6f23uI6N1z1bM93EMDIhNOR8mYUWRIWJ7amQxrplaN4630+tXYtJshxfWz2/V/nV+/uKc9yaEWv4lb9Yl/+vHv+3rS2FVC7RZHjGHvEiIDnJJcfAXi9XLEBC2O/OGwZSXIzHsJMxjvpKlmWwQHo9n18toz6SVUYgfoHs/LaJ2OTPOZ8SU95Fz48vul3QIUP4BiTwkg2lDshgPqpYZXg1LiSL6XnlnjAiiDI46C1B/xeFgGAyLD+Szil/oonak/3Lmz+M+3Hx40fV/q+jJArH7iFbo+bdL/sathXP+qjrxszBKjm9PLUi7gwqSibz3XMOWREbmYRWgDV2KbOmGAF1Ye+Lt3P5OmU61o/Cux1b2hpvKqyfOGyXNyAmutFa2nZJychLUwZYBMADJaqwbZH3twfZTc2AMsv/Wqgr8yRXMnYVnJfvvh15vv/vbjH2b6VbReqllp9oHMxYlMblu1itZI74/nUE9lEHIvqlA7GZcSSrcSmRJsej03oWaqpPqETvBx3mR+CvwGwfLjCAAA"
}
}

trait WArraysDsl extends impl.WArraysAbs {self: WrappersDsl =>}
trait WArraysDslExp extends impl.WArraysExp {self: WrappersDslExp =>}
