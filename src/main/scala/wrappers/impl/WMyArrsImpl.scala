package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scalanizer.MyArr
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WMyArrsAbs extends WMyArrs with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWMyArr[T](p: Rep[WMyArr[T]]): WMyArr[T] = {
    proxyOps[WMyArr[T]](p)(scala.reflect.classTag[WMyArr[T]])
  }

  // TypeWrapper proxy
  //implicit def proxyMyArr[T:Elem](p: Rep[MyArr[T]]): WMyArr[T] =
  //  proxyOps[WMyArr[T]](p.asRep[WMyArr[T]])

  implicit def unwrapValueOfWMyArr[T](w: Rep[WMyArr[T]]): Rep[MyArr[T]] = w.wrappedValueOfBaseType

  implicit def myArrElement[T:Elem]: Elem[MyArr[T]]

  // familyElem
  abstract class WMyArrElem[T, To <: WMyArr[T]](implicit val eeT: Elem[T])
    extends WrapperElem[MyArr[T], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WMyArr[T]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun { x: Rep[WMyArr[T]] => convertWMyArr(x) }
      tryConvert(element[WMyArr[T]], this, x, conv)
    }

    def convertWMyArr(x: Rep[WMyArr[T]]): Rep[To] = {
      assert(x.selfType1 match { case _: WMyArrElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wMyArrElement[T](implicit eeT: Elem[T]): Elem[WMyArr[T]] =
    new WMyArrElem[T, WMyArr[T]] {
      lazy val eTo = element[WMyArrImpl[T]]
    }

  implicit case object WMyArrCompanionElem extends CompanionElem[WMyArrCompanionAbs] {
    lazy val tag = weakTypeTag[WMyArrCompanionAbs]

    protected def getDefaultRep = WMyArr
  }

  abstract class WMyArrCompanionAbs extends CompanionBase[WMyArrCompanionAbs] with WMyArrCompanion {
    override def toString = "WMyArr"
  }

  def WMyArr: Rep[WMyArrCompanionAbs]

  implicit def proxyWMyArrCompanion(p: Rep[WMyArrCompanion]): WMyArrCompanion = {
    proxyOps[WMyArrCompanion](p)
  }

  // default wrapper implementation
  abstract class WMyArrImpl[T](val wrappedValueOfBaseType: Rep[MyArr[T]])(implicit val eeT: Elem[T]) extends WMyArr[T] {
    def apply(j: Rep[Int]): Rep[T] =
      methodCallEx[T](self,
        this.getClass.getMethod("apply", classOf[AnyRef]),
        List(j.asInstanceOf[AnyRef]))

    def length: Rep[Int] =
      methodCallEx[Int](self,
        this.getClass.getMethod("length"),
        List())
  }

  trait WMyArrImplCompanion
  // elem for concrete class
  class WMyArrImplElem[T](val iso: Iso[WMyArrImplData[T], WMyArrImpl[T]])(implicit eeT: Elem[T])
    extends WMyArrElem[T, WMyArrImpl[T]]
    with ConcreteElem[WMyArrImplData[T], WMyArrImpl[T]] {
    lazy val eTo = this

    override def convertWMyArr(x: Rep[WMyArr[T]]) = WMyArrImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WMyArrImpl[T]]
    }
  }

  // state representation type
  type WMyArrImplData[T] = MyArr[T]

  // 3) Iso for concrete class
  class WMyArrImplIso[T](implicit eeT: Elem[T])
    extends Iso[WMyArrImplData[T], WMyArrImpl[T]] {
    override def from(p: Rep[WMyArrImpl[T]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[MyArr[T]]) = {
      val wrappedValueOfBaseType = p
      WMyArrImpl(wrappedValueOfBaseType)
    }

    lazy val defaultRepTo: Rep[WMyArrImpl[T]] = WMyArrImpl(DefaultOfMyArr[T].value)
    lazy val eTo = new WMyArrImplElem[T](this)
  }
  // 4) constructor and deconstructor
  abstract class WMyArrImplCompanionAbs extends CompanionBase[WMyArrImplCompanionAbs] with WMyArrImplCompanion {
    override def toString = "WMyArrImpl"

    def apply[T](wrappedValueOfBaseType: Rep[MyArr[T]])(implicit eeT: Elem[T]): Rep[WMyArrImpl[T]] =
      mkWMyArrImpl(wrappedValueOfBaseType)
  }

  object WMyArrImplMatcher {
    def unapply[T](p: Rep[WMyArr[T]]) = unmkWMyArrImpl(p)
  }

  def WMyArrImpl: Rep[WMyArrImplCompanionAbs]

  implicit def proxyWMyArrImplCompanion(p: Rep[WMyArrImplCompanionAbs]): WMyArrImplCompanionAbs = {
    proxyOps[WMyArrImplCompanionAbs](p)
  }

  implicit case object WMyArrImplCompanionElem extends CompanionElem[WMyArrImplCompanionAbs] {
    lazy val tag = weakTypeTag[WMyArrImplCompanionAbs]

    protected def getDefaultRep = WMyArrImpl
  }

  implicit def proxyWMyArrImpl[T](p: Rep[WMyArrImpl[T]]): WMyArrImpl[T] =
    proxyOps[WMyArrImpl[T]](p)

  implicit class ExtendedWMyArrImpl[T](p: Rep[WMyArrImpl[T]])(implicit eeT: Elem[T]) {
    def toData: Rep[WMyArrImplData[T]] = isoWMyArrImpl(eeT).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoWMyArrImpl[T](implicit eeT: Elem[T]): Iso[WMyArrImplData[T], WMyArrImpl[T]] =
    new WMyArrImplIso[T]

  // 6) smart constructor and deconstructor
  def mkWMyArrImpl[T](wrappedValueOfBaseType: Rep[MyArr[T]])(implicit eeT: Elem[T]): Rep[WMyArrImpl[T]]

  def unmkWMyArrImpl[T](p: Rep[WMyArr[T]]): Option[(Rep[MyArr[T]])]
}

// Seq -----------------------------------
trait WMyArrsSeq extends WMyArrsDsl with ScalanSeq {
  self: WrappersDslSeq =>
  lazy val WMyArr: Rep[WMyArrCompanionAbs] = new WMyArrCompanionAbs with UserTypeSeq[WMyArrCompanionAbs] {
    lazy val selfType = element[WMyArrCompanionAbs]
  }

    // override proxy if we deal with TypeWrapper
  //override def proxyMyArr[T:Elem](p: Rep[MyArr[T]]): WMyArr[T] =
  //  proxyOpsEx[MyArr[T],WMyArr[T], SeqWMyArrImpl[T]](p, bt => SeqWMyArrImpl(bt))

  implicit def myArrElement[T: Elem]: Elem[MyArr[T]] = new SeqBaseElemEx[MyArr[T], WMyArr[T]](element[WMyArr[T]])(weakTypeTag[MyArr[T]], DefaultOfMyArr[T])

  case class SeqWMyArrImpl[T]
      (override val wrappedValueOfBaseType: Rep[MyArr[T]])
      (implicit eeT: Elem[T])
    extends WMyArrImpl[T](wrappedValueOfBaseType)
    with UserTypeSeq[WMyArrImpl[T]] {
    lazy val selfType = element[WMyArrImpl[T]]
    override def apply(j: Rep[Int]): Rep[T] =
      wrappedValueOfBaseType.apply(j)

    override def length: Rep[Int] =
      wrappedValueOfBaseType.length
  }

  lazy val WMyArrImpl = new WMyArrImplCompanionAbs with UserTypeSeq[WMyArrImplCompanionAbs] {
    lazy val selfType = element[WMyArrImplCompanionAbs]
  }

  def mkWMyArrImpl[T]
  (wrappedValueOfBaseType: Rep[MyArr[T]])(implicit eeT: Elem[T]): Rep[WMyArrImpl[T]] =
    new SeqWMyArrImpl[T](wrappedValueOfBaseType)

  def unmkWMyArrImpl[T](p: Rep[WMyArr[T]]) = p match {
    case p: WMyArrImpl[T]@unchecked =>
      Some((p.wrappedValueOfBaseType))
    case _ => None
  }

  implicit def wrapMyArrToWMyArr[T: Elem](v: MyArr[T]): WMyArr[T] = WMyArrImpl(v)
}

// Exp -----------------------------------
trait WMyArrsExp extends WMyArrsDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WMyArr: Rep[WMyArrCompanionAbs] = new WMyArrCompanionAbs with UserTypeDef[WMyArrCompanionAbs] {
    lazy val selfType = element[WMyArrCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  implicit def myArrElement[T: Elem]: Elem[MyArr[T]] = new ExpBaseElemEx[MyArr[T], WMyArr[T]](element[WMyArr[T]])(weakTypeTag[MyArr[T]], DefaultOfMyArr[T])

  case class ExpWMyArrImpl[T]
      (override val wrappedValueOfBaseType: Rep[MyArr[T]])
      (implicit eeT: Elem[T])
    extends WMyArrImpl[T](wrappedValueOfBaseType) with UserTypeDef[WMyArrImpl[T]] {
    lazy val selfType = element[WMyArrImpl[T]]

    override def mirror(t: Transformer) = ExpWMyArrImpl[T](t(wrappedValueOfBaseType))
  }

  lazy val WMyArrImpl: Rep[WMyArrImplCompanionAbs] = new WMyArrImplCompanionAbs with UserTypeDef[WMyArrImplCompanionAbs] {
    lazy val selfType = element[WMyArrImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WMyArrImplMethods {
  }

  def mkWMyArrImpl[T]
  (wrappedValueOfBaseType: Rep[MyArr[T]])(implicit eeT: Elem[T]): Rep[WMyArrImpl[T]] =
    new ExpWMyArrImpl[T](wrappedValueOfBaseType)

  def unmkWMyArrImpl[T](p: Rep[WMyArr[T]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WMyArrImplElem[T]@unchecked =>
      Some((p.asRep[WMyArrImpl[T]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WMyArrMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WMyArr[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WMyArrElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WMyArr[T]] forSome {type T}]]
        case _ => None
      }

      def unapply(exp: Exp[_]): Option[Rep[WMyArr[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object apply {
      def unapply(d: Def[_]): Option[(Rep[WMyArr[T]], Rep[Int]) forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(j, _*), _) if receiver.elem.isInstanceOf[WMyArrElem[_, _]] && method.getName == "apply" =>
          Some((receiver, j)).asInstanceOf[Option[(Rep[WMyArr[T]], Rep[Int]) forSome {type T}]]
        case _ => None
      }

      def unapply(exp: Exp[_]): Option[(Rep[WMyArr[T]], Rep[Int]) forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object length {
      def unapply(d: Def[_]): Option[Rep[WMyArr[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WMyArrElem[_, _]] && method.getName == "length" =>
          Some(receiver).asInstanceOf[Option[Rep[WMyArr[T]] forSome {type T}]]
        case _ => None
      }

      def unapply(exp: Exp[_]): Option[Rep[WMyArr[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object WMyArrCompanionMethods {
  }
}
