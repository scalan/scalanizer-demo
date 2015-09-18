package wrappers
package impl

import scalan._
import scalan.common.Default
import impl._
import scala.collection.mutable.ArrayOps
import scala.reflect.runtime.universe._
import scala.reflect._

// Abs -----------------------------------
trait WArrayOpssAbs extends WArrayOpss with ScalanDsl {
  self: WrappersDsl =>

  // single proxy for each type family
  implicit def proxyWArrayOps[T](p: Rep[WArrayOps[T]]): WArrayOps[T] = {
    proxyOps[WArrayOps[T]](p)(scala.reflect.classTag[WArrayOps[T]])
  }

  // TypeWrapper proxy
  //implicit def proxyArrayOps[T:Elem](p: Rep[ArrayOps[T]]): WArrayOps[T] =
  //  proxyOps[WArrayOps[T]](p.asRep[WArrayOps[T]])

  implicit def unwrapValueOfWArrayOps[T](w: Rep[WArrayOps[T]]): Rep[ArrayOps[T]] = w.wrappedValueOfBaseType

  implicit def arrayOpsElement[T:Elem]: Elem[ArrayOps[T]]

  // familyElem
  abstract class WArrayOpsElem[T, To <: WArrayOps[T]](implicit val eeT: Elem[T])
    extends WrapperElem[ArrayOps[T], To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WArrayOps[T]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[WArrayOps[T]] => convertWArrayOps(x) }
      tryConvert(element[WArrayOps[T]], this, x, conv)
    }

    def convertWArrayOps(x : Rep[WArrayOps[T]]): Rep[To] = {
      assert(x.selfType1 match { case _: WArrayOpsElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def wArrayOpsElement[T](implicit eeT: Elem[T]): Elem[WArrayOps[T]] =
    new WArrayOpsElem[T, WArrayOps[T]] {
      lazy val eTo = element[WArrayOpsImpl[T]]
    }

  implicit case object WArrayOpsCompanionElem extends CompanionElem[WArrayOpsCompanionAbs] {
    lazy val tag = weakTypeTag[WArrayOpsCompanionAbs]
    protected def getDefaultRep = WArrayOps
  }

  abstract class WArrayOpsCompanionAbs extends CompanionBase[WArrayOpsCompanionAbs] with WArrayOpsCompanion {
    override def toString = "WArrayOps"
  }
  def WArrayOps: Rep[WArrayOpsCompanionAbs]
  implicit def proxyWArrayOpsCompanion(p: Rep[WArrayOpsCompanion]): WArrayOpsCompanion = {
    proxyOps[WArrayOpsCompanion](p)
  }

  // default wrapper implementation
  abstract class WArrayOpsImpl[T](val wrappedValueOfBaseType: Rep[ArrayOps[T]])(implicit val eeT: Elem[T]) extends WArrayOps[T] {
    def map[B, That]( f: Rep[T => B])( bf: Rep[WCanBuildFrom[WArray[T],B,That]])(implicit emB: Elem[B], emThat: Elem[That]): Rep[That] =
      methodCallEx[That](self,
        this.getClass.getMethod("map", classOf[AnyRef], classOf[AnyRef], classOf[AnyRef], classOf[AnyRef]),
        List(f.asInstanceOf[AnyRef], bf.asInstanceOf[AnyRef], emB.asInstanceOf[AnyRef], emThat.asInstanceOf[AnyRef]))
  }
  trait WArrayOpsImplCompanion
  // elem for concrete class
  class WArrayOpsImplElem[T](val iso: Iso[WArrayOpsImplData[T], WArrayOpsImpl[T]])(implicit eeT: Elem[T])
    extends WArrayOpsElem[T, WArrayOpsImpl[T]]
    with ConcreteElem[WArrayOpsImplData[T], WArrayOpsImpl[T]] {
    lazy val eTo = this
    override def convertWArrayOps(x: Rep[WArrayOps[T]]) = WArrayOpsImpl(x.wrappedValueOfBaseType)
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagT = eeT.tag
      weakTypeTag[WArrayOpsImpl[T]]
    }
  }

  // state representation type
  type WArrayOpsImplData[T] = ArrayOps[T]

  // 3) Iso for concrete class
  class WArrayOpsImplIso[T](implicit eeT: Elem[T])
    extends Iso[WArrayOpsImplData[T], WArrayOpsImpl[T]] {
    override def from(p: Rep[WArrayOpsImpl[T]]) =
      p.wrappedValueOfBaseType
    override def to(p: Rep[ArrayOps[T]]) = {
      val wrappedValueOfBaseType = p
      WArrayOpsImpl(wrappedValueOfBaseType)
    }
    lazy val defaultRepTo: Rep[WArrayOpsImpl[T]] = WArrayOpsImpl(DefaultOfArrayOps[T].value)
    lazy val eTo = new WArrayOpsImplElem[T](this)
  }
  // 4) constructor and deconstructor
  abstract class WArrayOpsImplCompanionAbs extends CompanionBase[WArrayOpsImplCompanionAbs] with WArrayOpsImplCompanion {
    override def toString = "WArrayOpsImpl"

    def apply[T](wrappedValueOfBaseType: Rep[ArrayOps[T]])(implicit eeT: Elem[T]): Rep[WArrayOpsImpl[T]] =
      mkWArrayOpsImpl(wrappedValueOfBaseType)
  }
  object WArrayOpsImplMatcher {
    def unapply[T](p: Rep[WArrayOps[T]]) = unmkWArrayOpsImpl(p)
  }
  def WArrayOpsImpl: Rep[WArrayOpsImplCompanionAbs]
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
    new WArrayOpsImplIso[T]

  // 6) smart constructor and deconstructor
  def mkWArrayOpsImpl[T](wrappedValueOfBaseType: Rep[ArrayOps[T]])(implicit eeT: Elem[T]): Rep[WArrayOpsImpl[T]]
  def unmkWArrayOpsImpl[T](p: Rep[WArrayOps[T]]): Option[(Rep[ArrayOps[T]])]
}

// Exp -----------------------------------
trait WArrayOpssExp extends WArrayOpssDsl with ScalanExp {
  self: WrappersDslExp =>
  lazy val WArrayOps: Rep[WArrayOpsCompanionAbs] = new WArrayOpsCompanionAbs with UserTypeDef[WArrayOpsCompanionAbs] {
    lazy val selfType = element[WArrayOpsCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  implicit def arrayOpsElement[T:Elem]: Elem[ArrayOps[T]] = {
     implicit val wT = element[T].tag;
     new ExpBaseElemEx[ArrayOps[T], WArrayOps[T]](element[WArrayOps[T]])(weakTypeTag[ArrayOps[T]], DefaultOfArrayOps[T])
  }

  case class ExpWArrayOpsImpl[T]
      (override val wrappedValueOfBaseType: Rep[ArrayOps[T]])
      (implicit eeT: Elem[T])
    extends WArrayOpsImpl[T](wrappedValueOfBaseType) with UserTypeDef[WArrayOpsImpl[T]] {
    lazy val selfType = element[WArrayOpsImpl[T]]
    override def mirror(t: Transformer) = ExpWArrayOpsImpl[T](t(wrappedValueOfBaseType))
  }

  lazy val WArrayOpsImpl: Rep[WArrayOpsImplCompanionAbs] = new WArrayOpsImplCompanionAbs with UserTypeDef[WArrayOpsImplCompanionAbs] {
    lazy val selfType = element[WArrayOpsImplCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object WArrayOpsImplMethods {
  }

  def mkWArrayOpsImpl[T]
    (wrappedValueOfBaseType: Rep[ArrayOps[T]])(implicit eeT: Elem[T]): Rep[WArrayOpsImpl[T]] =
    new ExpWArrayOpsImpl[T](wrappedValueOfBaseType)
  def unmkWArrayOpsImpl[T](p: Rep[WArrayOps[T]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: WArrayOpsImplElem[T] @unchecked =>
      Some((p.asRep[WArrayOpsImpl[T]].wrappedValueOfBaseType))
    case _ =>
      None
  }

  object WArrayOpsMethods {
    object wrappedValueOfBaseType {
      def unapply(d: Def[_]): Option[Rep[WArrayOps[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[WArrayOpsElem[_, _]] && method.getName == "wrappedValueOfBaseType" =>
          Some(receiver).asInstanceOf[Option[Rep[WArrayOps[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[WArrayOps[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    // WARNING: Cannot generate matcher for method `map`: Method has function arguments f
  }

  object WArrayOpsCompanionMethods {
  }
}
