package kinds
package impl

import scalan._
import scala.reflect.runtime.universe._
import scala.reflect._
import scalan.common.Default

// Abs -----------------------------------
trait KindsAbs extends Kinds with ScalanDsl {
  self: KindsDsl =>

  // single proxy for each type family
  implicit def proxyKind[F[_], A](p: Rep[Kind[F, A]]): Kind[F, A] = {
    proxyOps[Kind[F, A]](p)(classTag[Kind[F, A]])
  }

  // familyElem
  class KindElem[F[_], A, To <: Kind[F, A]](implicit val cF: Cont[F], val eA: Elem[A])
    extends EntityElem[To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagA = eA.tag
      weakTypeTag[Kind[F, A]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      val conv = fun {x: Rep[Kind[F, A]] =>  convertKind(x) }
      tryConvert(element[Kind[F, A]], this, x, conv)
    }

    def convertKind(x : Rep[Kind[F, A]]): Rep[To] = {
      assert(x.selfType1.asInstanceOf[Element[_]] match { case _: KindElem[_, _, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def kindElement[F[_], A](implicit cF: Cont[F], eA: Elem[A]): Elem[Kind[F, A]] =
    new KindElem[F, A, Kind[F, A]]

  implicit case object KindCompanionElem extends CompanionElem[KindCompanionAbs] {
    lazy val tag = weakTypeTag[KindCompanionAbs]
    protected def getDefaultRep = Kind
  }

  abstract class KindCompanionAbs extends CompanionBase[KindCompanionAbs] with KindCompanion {
    override def toString = "Kind"
  }
  def Kind: Rep[KindCompanionAbs]
  implicit def proxyKindCompanion(p: Rep[KindCompanion]): KindCompanion = {
    proxyOps[KindCompanion](p)
  }

  // elem for concrete class
  class ReturnElem[F[_], A](val iso: Iso[ReturnData[F, A], Return[F, A]])(implicit eA: Elem[A], cF: Cont[F])
    extends KindElem[F, A, Return[F, A]]
    with ConcreteElem[ReturnData[F, A], Return[F, A]] {
    override def convertKind(x: Rep[Kind[F, A]]) = // Converter is not generated by meta
!!!("Cannot convert from Kind to Return: missing fields List(a)")
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagA = eA.tag
      weakTypeTag[Return[F, A]]
    }
  }

  // state representation type
  type ReturnData[F[_], A] = A

  // 3) Iso for concrete class
  class ReturnIso[F[_], A](implicit eA: Elem[A], cF: Cont[F])
    extends Iso[ReturnData[F, A], Return[F, A]] {
    override def from(p: Rep[Return[F, A]]) =
      p.a
    override def to(p: Rep[A]) = {
      val a = p
      Return(a)
    }
    lazy val defaultRepTo = Default.defaultVal[Rep[Return[F, A]]](Return(element[A].defaultRepValue))
    lazy val eTo = new ReturnElem[F, A](this)
  }
  // 4) constructor and deconstructor
  abstract class ReturnCompanionAbs extends CompanionBase[ReturnCompanionAbs] with ReturnCompanion {
    override def toString = "Return"

    def apply[F[_], A](a: Rep[A])(implicit eA: Elem[A], cF: Cont[F]): Rep[Return[F, A]] =
      mkReturn(a)
  }
  object ReturnMatcher {
    def unapply[F[_], A](p: Rep[Kind[F, A]]) = unmkReturn(p)
  }
  def Return: Rep[ReturnCompanionAbs]
  implicit def proxyReturnCompanion(p: Rep[ReturnCompanionAbs]): ReturnCompanionAbs = {
    proxyOps[ReturnCompanionAbs](p)
  }

  implicit case object ReturnCompanionElem extends CompanionElem[ReturnCompanionAbs] {
    lazy val tag = weakTypeTag[ReturnCompanionAbs]
    protected def getDefaultRep = Return
  }

  implicit def proxyReturn[F[_], A](p: Rep[Return[F, A]]): Return[F, A] =
    proxyOps[Return[F, A]](p)

  implicit class ExtendedReturn[F[_], A](p: Rep[Return[F, A]])(implicit eA: Elem[A], cF: Cont[F]) {
    def toData: Rep[ReturnData[F, A]] = isoReturn(eA, cF).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoReturn[F[_], A](implicit eA: Elem[A], cF: Cont[F]): Iso[ReturnData[F, A], Return[F, A]] =
    new ReturnIso[F, A]

  // 6) smart constructor and deconstructor
  def mkReturn[F[_], A](a: Rep[A])(implicit eA: Elem[A], cF: Cont[F]): Rep[Return[F, A]]
  def unmkReturn[F[_], A](p: Rep[Kind[F, A]]): Option[(Rep[A])]

  // elem for concrete class
  class BindElem[F[_], S, B](val iso: Iso[BindData[F, S, B], Bind[F, S, B]])(implicit eS: Elem[S], eA: Elem[B], cF: Cont[F])
    extends KindElem[F, B, Bind[F, S, B]]
    with ConcreteElem[BindData[F, S, B], Bind[F, S, B]] {
    override def convertKind(x: Rep[Kind[F, B]]) = // Converter is not generated by meta
!!!("Cannot convert from Kind to Bind: missing fields List(a, f)")
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagS = eS.tag
      implicit val tagB = eA.tag
      weakTypeTag[Bind[F, S, B]]
    }
  }

  // state representation type
  type BindData[F[_], S, B] = (Kind[F,S], S => Kind[F,B])

  // 3) Iso for concrete class
  class BindIso[F[_], S, B](implicit eS: Elem[S], eA: Elem[B], cF: Cont[F])
    extends Iso[BindData[F, S, B], Bind[F, S, B]]()(pairElement(implicitly[Elem[Kind[F,S]]], implicitly[Elem[S => Kind[F,B]]])) {
    override def from(p: Rep[Bind[F, S, B]]) =
      (p.a, p.f)
    override def to(p: Rep[(Kind[F,S], S => Kind[F,B])]) = {
      val Pair(a, f) = p
      Bind(a, f)
    }
    lazy val defaultRepTo = Default.defaultVal[Rep[Bind[F, S, B]]](Bind(element[Kind[F,S]].defaultRepValue, fun { (x: Rep[S]) => element[Kind[F,B]].defaultRepValue }))
    lazy val eTo = new BindElem[F, S, B](this)
  }
  // 4) constructor and deconstructor
  abstract class BindCompanionAbs extends CompanionBase[BindCompanionAbs] with BindCompanion {
    override def toString = "Bind"
    def apply[F[_], S, B](p: Rep[BindData[F, S, B]])(implicit eS: Elem[S], eA: Elem[B], cF: Cont[F]): Rep[Bind[F, S, B]] =
      isoBind(eS, eA, cF).to(p)
    def apply[F[_], S, B](a: Rep[Kind[F,S]], f: Rep[S => Kind[F,B]])(implicit eS: Elem[S], eA: Elem[B], cF: Cont[F]): Rep[Bind[F, S, B]] =
      mkBind(a, f)
  }
  object BindMatcher {
    def unapply[F[_], S, B](p: Rep[Kind[F, B]]) = unmkBind(p)
  }
  def Bind: Rep[BindCompanionAbs]
  implicit def proxyBindCompanion(p: Rep[BindCompanionAbs]): BindCompanionAbs = {
    proxyOps[BindCompanionAbs](p)
  }

  implicit case object BindCompanionElem extends CompanionElem[BindCompanionAbs] {
    lazy val tag = weakTypeTag[BindCompanionAbs]
    protected def getDefaultRep = Bind
  }

  implicit def proxyBind[F[_], S, B](p: Rep[Bind[F, S, B]]): Bind[F, S, B] =
    proxyOps[Bind[F, S, B]](p)

  implicit class ExtendedBind[F[_], S, B](p: Rep[Bind[F, S, B]])(implicit eS: Elem[S], eA: Elem[B], cF: Cont[F]) {
    def toData: Rep[BindData[F, S, B]] = isoBind(eS, eA, cF).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoBind[F[_], S, B](implicit eS: Elem[S], eA: Elem[B], cF: Cont[F]): Iso[BindData[F, S, B], Bind[F, S, B]] =
    new BindIso[F, S, B]

  // 6) smart constructor and deconstructor
  def mkBind[F[_], S, B](a: Rep[Kind[F,S]], f: Rep[S => Kind[F,B]])(implicit eS: Elem[S], eA: Elem[B], cF: Cont[F]): Rep[Bind[F, S, B]]
  def unmkBind[F[_], S, B](p: Rep[Kind[F, B]]): Option[(Rep[Kind[F,S]], Rep[S => Kind[F,B]])]
}

// Seq -----------------------------------
trait KindsSeq extends KindsDsl with ScalanSeq {
  self: KindsDslSeq =>
  lazy val Kind: Rep[KindCompanionAbs] = new KindCompanionAbs with UserTypeSeq[KindCompanionAbs] {
    lazy val selfType = element[KindCompanionAbs]
  }

  case class SeqReturn[F[_], A]
      (override val a: Rep[A])
      (implicit eA: Elem[A], cF: Cont[F])
    extends Return[F, A](a)
        with UserTypeSeq[Return[F, A]] {
    lazy val selfType = element[Return[F, A]]
  }
  lazy val Return = new ReturnCompanionAbs with UserTypeSeq[ReturnCompanionAbs] {
    lazy val selfType = element[ReturnCompanionAbs]
  }

  def mkReturn[F[_], A]
      (a: Rep[A])(implicit eA: Elem[A], cF: Cont[F]): Rep[Return[F, A]] =
      new SeqReturn[F, A](a)
  def unmkReturn[F[_], A](p: Rep[Kind[F, A]]) = p match {
    case p: Return[F, A] @unchecked =>
      Some((p.a))
    case _ => None
  }

  case class SeqBind[F[_], S, B]
      (override val a: Rep[Kind[F,S]], override val f: Rep[S => Kind[F,B]])
      (implicit eS: Elem[S], eA: Elem[B], cF: Cont[F])
    extends Bind[F, S, B](a, f)
        with UserTypeSeq[Bind[F, S, B]] {
    lazy val selfType = element[Bind[F, S, B]]
  }
  lazy val Bind = new BindCompanionAbs with UserTypeSeq[BindCompanionAbs] {
    lazy val selfType = element[BindCompanionAbs]
  }

  def mkBind[F[_], S, B]
      (a: Rep[Kind[F,S]], f: Rep[S => Kind[F,B]])(implicit eS: Elem[S], eA: Elem[B], cF: Cont[F]): Rep[Bind[F, S, B]] =
      new SeqBind[F, S, B](a, f)
  def unmkBind[F[_], S, B](p: Rep[Kind[F, B]]) = p match {
    case p: Bind[F, S, B] @unchecked =>
      Some((p.a, p.f))
    case _ => None
  }
}

// Exp -----------------------------------
trait KindsExp extends KindsDsl with ScalanExp {
  self: KindsDslExp =>
  lazy val Kind: Rep[KindCompanionAbs] = new KindCompanionAbs with UserTypeDef[KindCompanionAbs] {
    lazy val selfType = element[KindCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  case class ExpReturn[F[_], A]
      (override val a: Rep[A])
      (implicit eA: Elem[A], cF: Cont[F])
    extends Return[F, A](a) with UserTypeDef[Return[F, A]] {
    lazy val selfType = element[Return[F, A]]
    override def mirror(t: Transformer) = ExpReturn[F, A](t(a))
  }

  lazy val Return: Rep[ReturnCompanionAbs] = new ReturnCompanionAbs with UserTypeDef[ReturnCompanionAbs] {
    lazy val selfType = element[ReturnCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object ReturnMethods {
    // WARNING: Cannot generate matcher for method `flatMap`: Method has function arguments f
  }

  object ReturnCompanionMethods {
  }

  def mkReturn[F[_], A]
    (a: Rep[A])(implicit eA: Elem[A], cF: Cont[F]): Rep[Return[F, A]] =
    new ExpReturn[F, A](a)
  def unmkReturn[F[_], A](p: Rep[Kind[F, A]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: ReturnElem[F, A] @unchecked =>
      Some((p.asRep[Return[F, A]].a))
    case _ =>
      None
  }

  case class ExpBind[F[_], S, B]
      (override val a: Rep[Kind[F,S]], override val f: Rep[S => Kind[F,B]])
      (implicit eS: Elem[S], eA: Elem[B], cF: Cont[F])
    extends Bind[F, S, B](a, f) with UserTypeDef[Bind[F, S, B]] {
    lazy val selfType = element[Bind[F, S, B]]
    override def mirror(t: Transformer) = ExpBind[F, S, B](t(a), t(f))
  }

  lazy val Bind: Rep[BindCompanionAbs] = new BindCompanionAbs with UserTypeDef[BindCompanionAbs] {
    lazy val selfType = element[BindCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object BindMethods {
    // WARNING: Cannot generate matcher for method `flatMap`: Method has function arguments f1
  }

  object BindCompanionMethods {
  }

  def mkBind[F[_], S, B]
    (a: Rep[Kind[F,S]], f: Rep[S => Kind[F,B]])(implicit eS: Elem[S], eA: Elem[B], cF: Cont[F]): Rep[Bind[F, S, B]] =
    new ExpBind[F, S, B](a, f)
  def unmkBind[F[_], S, B](p: Rep[Kind[F, B]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: BindElem[F, S, B] @unchecked =>
      Some((p.asRep[Bind[F, S, B]].a, p.asRep[Bind[F, S, B]].f))
    case _ =>
      None
  }

  object KindMethods {
    // WARNING: Cannot generate matcher for method `flatMap`: Method has function arguments f

    object mapBy {
      def unapply(d: Def[_]): Option[(Rep[Kind[F, A]], Rep[A => B]) forSome {type F[_]; type A; type B}] = d match {
        case MethodCall(receiver, method, Seq(f, _*), _) if (receiver.elem.asInstanceOf[Element[_]] match { case _: KindElem[_, _, _] => true; case _ => false }) && method.getName == "mapBy" =>
          Some((receiver, f)).asInstanceOf[Option[(Rep[Kind[F, A]], Rep[A => B]) forSome {type F[_]; type A; type B}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Kind[F, A]], Rep[A => B]) forSome {type F[_]; type A; type B}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object KindCompanionMethods {
  }
}
