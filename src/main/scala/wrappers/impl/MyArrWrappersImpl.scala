package wrappers
package impl

import scalanizer.MyArr
import scalan._
import scalan.common.Default
import scala.reflect.runtime.universe.{WeakTypeTag, weakTypeTag}

// Abs -----------------------------------
trait MyArrWrappersAbs extends MyArrWrappers with scalan.Scalan {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyMyArrWrapper[T](p: Rep[MyArrWrapper[T]]): MyArrWrapper[T] = {
    proxyOps[MyArrWrapper[T]](p)(scala.reflect.classTag[MyArrWrapper[T]])
  }

  // TypeWrapper proxy
  //implicit def proxyMyArr[T:Elem](p: Rep[MyArr[T]]): MyArrWrapper[T] =
  //  proxyOps[MyArrWrapper[T]](p.asRep[MyArrWrapper[T]])

  implicit def unwrapValueOfMyArrWrapper[T](w: Rep[MyArrWrapper[T]]): Rep[MyArr[T]] = w.wrappedValueOfBaseType

  implicit def myArrElement[T:Elem]: Elem[MyArr[T]]

  // familyElem
  abstract class MyArrWrapperElem[T, To <: MyArrWrapper[T]](implicit val eeT: Elem[T])
    extends WrapperElem[MyArr[T], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[MyArrWrapper[T]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[MyArrWrapper[T]] => convertMyArrWrapper(x) }
      tryConvert(element[MyArrWrapper[T]], this, x, conv)
    }

    def convertMyArrWrapper(x : Rep[MyArrWrapper[T]]): Rep[To] = {
      assert(x.selfType1 match { case _: MyArrWrapperElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def myArrWrapperElement[T](implicit eeT: Elem[T]): Elem[MyArrWrapper[T]] =
    new MyArrWrapperElem[T, MyArrWrapper[T]] {
      lazy val eTo = element[MyArrWrapperImpl[T]]
    }

  implicit case object MyArrWrapperCompanionElem extends CompanionElem[MyArrWrapperCompanionAbs] {
    lazy val tag = weakTypeTag[MyArrWrapperCompanionAbs]
    protected def getDefaultRep = MyArrWrapper
  }

  abstract class MyArrWrapperCompanionAbs extends CompanionBase[MyArrWrapperCompanionAbs] with MyArrWrapperCompanion {
    override def toString = "MyArrWrapper"
  }
  def MyArrWrapper: Rep[MyArrWrapperCompanionAbs]
  implicit def proxyMyArrWrapperCompanion(p: Rep[MyArrWrapperCompanion]): MyArrWrapperCompanion = {
    proxyOps[MyArrWrapperCompanion](p)
  }

  // default wrapper implementation
  abstract class MyArrWrapperImpl[T](val wrappedValueOfBaseType: Rep[MyArr[T]])(implicit val eeT: Elem[T]) extends MyArrWrapper[T] {
    def apply(j: Rep[Int]): Rep[T] =
      methodCallEx[T](self,
        this.getClass.getMethod("apply", classOf[AnyRef]),
        List(j.asInstanceOf[AnyRef]))

    def length: Rep[Int] =
      methodCallEx[Int](self,
        this.getClass.getMethod("length"),
        List())
  }
  trait MyArrWrapperImplCompanion
  // elem for concrete class
  class MyArrWrapperImplElem[T](val iso: Iso[MyArrWrapperImplData[T], MyArrWrapperImpl[T]])(implicit eeT: Elem[T])
    extends MyArrWrapperElem[T, MyArrWrapperImpl[T]]
    with ConcreteElem[MyArrWrapperImplData[T], MyArrWrapperImpl[T]] {
    lazy val eTo = this
    override def convertMyArrWrapper(x: Rep[MyArrWrapper[T]]) = MyArrWrapperImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[MyArrWrapperImpl[T]]
    }
  }

  // state representation type
  type MyArrWrapperImplData[T] = MyArr[T]

  // 3) Iso for concrete class
  class MyArrWrapperImplIso[T](implicit eeT: Elem[T])
    extends Iso[MyArrWrapperImplData[T], MyArrWrapperImpl[T]] {
    override def from(p: Rep[MyArrWrapperImpl[T]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[MyArr[T]]) = {
      val wrappedValueOfBaseType = p
      MyArrWrapperImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[MyArrWrapperImpl[T]] = MyArrWrapperImpl(DefaultOfMyArr[T].value)
    lazy val eTo = new MyArrWrapperImplElem[T](this)
  }
  // 4) constructor and deconstructor
  abstract class MyArrWrapperImplCompanionAbs extends CompanionBase[MyArrWrapperImplCompanionAbs] with MyArrWrapperImplCompanion {
    override def toString = "MyArrWrapperImpl"

    def apply[T](wrappedValueOfBaseType: Rep[MyArr[T]])(implicit eeT: Elem[T]): Rep[MyArrWrapperImpl[T]] =
      mkMyArrWrapperImpl(wrappedValueOfBaseType)
  }
  object MyArrWrapperImplMatcher {
    def unapply[T](p: Rep[MyArrWrapper[T]]) = unmkMyArrWrapperImpl(p)
  }
  def MyArrWrapperImpl: Rep[MyArrWrapperImplCompanionAbs]
  implicit def proxyMyArrWrapperImplCompanion(p: Rep[MyArrWrapperImplCompanionAbs]): MyArrWrapperImplCompanionAbs = {
    proxyOps[MyArrWrapperImplCompanionAbs](p)
  }

  implicit case object MyArrWrapperImplCompanionElem extends CompanionElem[MyArrWrapperImplCompanionAbs] {
    lazy val tag = weakTypeTag[MyArrWrapperImplCompanionAbs]
    protected def getDefaultRep = MyArrWrapperImpl
  }

  implicit def proxyMyArrWrapperImpl[T](p: Rep[MyArrWrapperImpl[T]]): MyArrWrapperImpl[T] =
    proxyOps[MyArrWrapperImpl[T]](p)

  implicit class ExtendedMyArrWrapperImpl[T](p: Rep[MyArrWrapperImpl[T]])(implicit eeT: Elem[T]) {
    def toData: Rep[MyArrWrapperImplData[T]] = isoMyArrWrapperImpl(eeT).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoMyArrWrapperImpl[T](implicit eeT: Elem[T]): Iso[MyArrWrapperImplData[T], MyArrWrapperImpl[T]] =
    new MyArrWrapperImplIso[T]

  // 6) smart constructor and deconstructor
  def mkMyArrWrapperImpl[T](wrappedValueOfBaseType: Rep[MyArr[T]])(implicit eeT: Elem[T]): Rep[MyArrWrapperImpl[T]]
  def unmkMyArrWrapperImpl[T](p: Rep[MyArrWrapper[T]]): Option[(Rep[MyArr[T]])]
}

// Seq -----------------------------------
trait MyArrWrappersSeq extends MyArrWrappersDsl with scalan.ScalanSeq {
  self: WrappersDslSeq =>
  lazy val MyArrWrapper: Rep[MyArrWrapperCompanionAbs] = new MyArrWrapperCompanionAbs with UserTypeSeq[MyArrWrapperCompanionAbs] {
    lazy val selfType = element[MyArrWrapperCompanionAbs]
  }

    // override proxy if we deal with TypeWrapper
  //override def proxyMyArr[T:Elem](p: Rep[MyArr[T]]): MyArrWrapper[T] =
  //  proxyOpsEx[MyArr[T],MyArrWrapper[T], SeqMyArrWrapperImpl[T]](p, bt => SeqMyArrWrapperImpl(bt))

    implicit def myArrElement[T:Elem]: Elem[MyArr[T]] = new SeqBaseElemEx[MyArr[T], MyArrWrapper[T]](element[MyArrWrapper[T]])(weakTypeTag[MyArr[T]], DefaultOfMyArr[T])

  case class SeqMyArrWrapperImpl[T]
      (override val wrappedValueOfBaseType: Rep[MyArr[T]])
      (implicit eeT: Elem[T])
    extends MyArrWrapperImpl[T](wrappedValueOfBaseType)
        with UserTypeSeq[MyArrWrapperImpl[T]] {
    lazy val selfType = element[MyArrWrapperImpl[T]]
    override def apply(j: Rep[Int]): Rep[T] =
      wrappedValueOfBaseType.apply(j)

    override def length: Rep[Int] =
      wrappedValueOfBaseType.length
  }
  lazy val MyArrWrapperImpl = new MyArrWrapperImplCompanionAbs with UserTypeSeq[MyArrWrapperImplCompanionAbs] {
    lazy val selfType = element[MyArrWrapperImplCompanionAbs]
  }

  def mkMyArrWrapperImpl[T]
      (wrappedValueOfBaseType: Rep[MyArr[T]])(implicit eeT: Elem[T]): Rep[MyArrWrapperImpl[T]] =
      new SeqMyArrWrapperImpl[T](wrappedValueOfBaseType)
  def unmkMyArrWrapperImpl[T](p: Rep[MyArrWrapper[T]]) = p match {
    case p: MyArrWrapperImpl[T] @unchecked =>
      Some((p.wrappedValueOfBaseType))
    case _ => None
  }

  implicit def wrapMyArrToMyArrWrapper[T:Elem](v: MyArr[T]): MyArrWrapper[T] = MyArrWrapperImpl(v)
}

// Exp -----------------------------------
trait MyArrWrappersExp extends MyArrWrappersDsl with scalan.ScalanExp {
  self: WrappersDslExp =>
  lazy val MyArrWrapper: Rep[MyArrWrapperCompanionAbs] = new MyArrWrapperCompanionAbs with UserTypeDef[MyArrWrapperCompanionAbs] {
    lazy val selfType = element[MyArrWrapperCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  implicit def myArrElement[T:Elem]: Elem[MyArr[T]] = new ExpBaseElemEx[MyArr[T], MyArrWrapper[T]](element[MyArrWrapper[T]])(weakTypeTag[MyArr[T]], DefaultOfMyArr[T])

  case class ExpMyArrWrapperImpl[T]
      (override val wrappedValueOfBaseType: Rep[MyArr[T]])
      (implicit eeT: Elem[T])
    extends MyArrWrapperImpl[T](wrappedValueOfBaseType) with UserTypeDef[MyArrWrapperImpl[T]] {
    lazy val selfType = element[MyArrWrapperImpl[T]]
    override def mirror(t: Transformer) = ExpMyArrWrapperImpl[T](t(wrappedValueOfBaseType))
  }

  lazy val MyArrWrapperImpl: Rep[MyArrWrapperImplCompanionAbs] = new MyArrWrapperImplCompanionAbs with UserTypeDef[MyArrWrapperImplCompanionAbs] {
    lazy val selfType = element[MyArrWrapperImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object MyArrWrapperImplMethods {
  }

  def mkMyArrWrapperImpl[T]
    (wrappedValueOfBaseType: Rep[MyArr[T]])(implicit eeT: Elem[T]): Rep[MyArrWrapperImpl[T]] =
    new ExpMyArrWrapperImpl[T](wrappedValueOfBaseType)
  def unmkMyArrWrapperImpl[T](p: Rep[MyArrWrapper[T]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: MyArrWrapperImplElem[T] @unchecked =>
      Some((p.asRep[MyArrWrapperImpl[T]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object MyArrWrapperMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[MyArrWrapper[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[MyArrWrapperElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[MyArrWrapper[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[MyArrWrapper[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object apply {
      def unapply(d: Def[_]): Option[(Rep[MyArrWrapper[T]], Rep[Int]) forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(j, _*), _) if receiver.elem.isInstanceOf[MyArrWrapperElem[_, _]] && method.getName == "apply" =>
          Some((receiver, j)).asInstanceOf[Option[(Rep[MyArrWrapper[T]], Rep[Int]) forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[MyArrWrapper[T]], Rep[Int]) forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object length {
      def unapply(d: Def[_]): Option[Rep[MyArrWrapper[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[MyArrWrapperElem[_, _]] && method.getName == "length" =>
          Some(receiver).asInstanceOf[Option[Rep[MyArrWrapper[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[MyArrWrapper[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object MyArrWrapperCompanionMethods {
  }
}
