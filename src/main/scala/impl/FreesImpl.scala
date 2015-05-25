package monads
package impl

import scalan._
import scala.reflect.runtime.universe._
import scala.reflect.runtime.universe._
import scala.reflect._
import scalan.common.Default

// Abs -----------------------------------
trait FreesAbs extends Frees with ScalanDsl {
  self: FreesDsl =>

  // single proxy for each type family
  implicit def proxyFree[F[_], A](p: Rep[Free[F, A]]): Free[F, A] = {
    proxyOps[Free[F, A]](p)(classTag[Free[F, A]])
  }

  // familyElem
  class FreeElem[F[_], A, To <: Free[F, A]](implicit val cF: Cont[F], val eA: Elem[A])
    extends EntityElem[To] {
    override def isEntityType = true
    override lazy val tag = {
      implicit val tagA = eA.tag
      weakTypeTag[Free[F, A]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      val conv = fun {x: Rep[Free[F, A]] =>  convertFree(x) }
      tryConvert(element[Free[F, A]], this, x, conv)
    }

    def convertFree(x : Rep[Free[F, A]]): Rep[To] = {
      assert(x.selfType1.asInstanceOf[Element[_]] match { case _: FreeElem[_, _, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def freeElement[F[_], A](implicit cF: Cont[F], eA: Elem[A]): Elem[Free[F, A]] =
    new FreeElem[F, A, Free[F, A]]

  implicit case object FreeCompanionElem extends CompanionElem[FreeCompanionAbs] {
    lazy val tag = weakTypeTag[FreeCompanionAbs]
    protected def getDefaultRep = Free
  }

  abstract class FreeCompanionAbs extends CompanionBase[FreeCompanionAbs] with FreeCompanion {
    override def toString = "Free"
  }
  def Free: Rep[FreeCompanionAbs]
  implicit def proxyFreeCompanion(p: Rep[FreeCompanion]): FreeCompanion = {
    proxyOps[FreeCompanion](p)
  }

  // elem for concrete class
  class ReturnElem[F[_], A](val iso: Iso[ReturnData[F, A], Return[F, A]])(implicit eA: Elem[A], cF: Cont[F])
    extends FreeElem[F, A, Return[F, A]]
    with ConcreteElem[ReturnData[F, A], Return[F, A]] {
    override def convertFree(x: Rep[Free[F, A]]) = // Converter is not generated by meta
!!!("Cannot convert from Free to Return: missing fields List(a)")
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
    def unapply[F[_], A](p: Rep[Free[F, A]]) = unmkReturn(p)
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
  def unmkReturn[F[_], A](p: Rep[Free[F, A]]): Option[(Rep[A])]

  // elem for concrete class
  class SuspendElem[F[_], A](val iso: Iso[SuspendData[F, A], Suspend[F, A]])(implicit eA: Elem[A], cF: Cont[F])
    extends FreeElem[F, A, Suspend[F, A]]
    with ConcreteElem[SuspendData[F, A], Suspend[F, A]] {
    override def convertFree(x: Rep[Free[F, A]]) = // Converter is not generated by meta
!!!("Cannot convert from Free to Suspend: missing fields List(a)")
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagA = eA.tag
      weakTypeTag[Suspend[F, A]]
    }
  }

  // state representation type
  type SuspendData[F[_], A] = F[A]

  // 3) Iso for concrete class
  class SuspendIso[F[_], A](implicit eA: Elem[A], cF: Cont[F])
    extends Iso[SuspendData[F, A], Suspend[F, A]] {
    override def from(p: Rep[Suspend[F, A]]) =
      p.a
    override def to(p: Rep[F[A]]) = {
      val a = p
      Suspend(a)
    }
    lazy val defaultRepTo = Default.defaultVal[Rep[Suspend[F, A]]](Suspend(cF.lift(eA).defaultRepValue))
    lazy val eTo = new SuspendElem[F, A](this)
  }
  // 4) constructor and deconstructor
  abstract class SuspendCompanionAbs extends CompanionBase[SuspendCompanionAbs] with SuspendCompanion {
    override def toString = "Suspend"

    def apply[F[_], A](a: Rep[F[A]])(implicit eA: Elem[A], cF: Cont[F]): Rep[Suspend[F, A]] =
      mkSuspend(a)
  }
  object SuspendMatcher {
    def unapply[F[_], A](p: Rep[Free[F, A]]) = unmkSuspend(p)
  }
  def Suspend: Rep[SuspendCompanionAbs]
  implicit def proxySuspendCompanion(p: Rep[SuspendCompanionAbs]): SuspendCompanionAbs = {
    proxyOps[SuspendCompanionAbs](p)
  }

  implicit case object SuspendCompanionElem extends CompanionElem[SuspendCompanionAbs] {
    lazy val tag = weakTypeTag[SuspendCompanionAbs]
    protected def getDefaultRep = Suspend
  }

  implicit def proxySuspend[F[_], A](p: Rep[Suspend[F, A]]): Suspend[F, A] =
    proxyOps[Suspend[F, A]](p)

  implicit class ExtendedSuspend[F[_], A](p: Rep[Suspend[F, A]])(implicit eA: Elem[A], cF: Cont[F]) {
    def toData: Rep[SuspendData[F, A]] = isoSuspend(eA, cF).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoSuspend[F[_], A](implicit eA: Elem[A], cF: Cont[F]): Iso[SuspendData[F, A], Suspend[F, A]] =
    new SuspendIso[F, A]

  // 6) smart constructor and deconstructor
  def mkSuspend[F[_], A](a: Rep[F[A]])(implicit eA: Elem[A], cF: Cont[F]): Rep[Suspend[F, A]]
  def unmkSuspend[F[_], A](p: Rep[Free[F, A]]): Option[(Rep[F[A]])]

  // elem for concrete class
  class BindElem[F[_], S, B](val iso: Iso[BindData[F, S, B], Bind[F, S, B]])(implicit eS: Elem[S], eA: Elem[B], cF: Cont[F])
    extends FreeElem[F, B, Bind[F, S, B]]
    with ConcreteElem[BindData[F, S, B], Bind[F, S, B]] {
    override def convertFree(x: Rep[Free[F, B]]) = // Converter is not generated by meta
!!!("Cannot convert from Free to Bind: missing fields List(a, f)")
    override def getDefaultRep = super[ConcreteElem].getDefaultRep
    override lazy val tag = {
      implicit val tagS = eS.tag
      implicit val tagB = eA.tag
      weakTypeTag[Bind[F, S, B]]
    }
  }

  // state representation type
  type BindData[F[_], S, B] = (Free[F,S], S => Free[F,B])

  // 3) Iso for concrete class
  class BindIso[F[_], S, B](implicit eS: Elem[S], eA: Elem[B], cF: Cont[F])
    extends Iso[BindData[F, S, B], Bind[F, S, B]]()(pairElement(implicitly[Elem[Free[F,S]]], implicitly[Elem[S => Free[F,B]]])) {
    override def from(p: Rep[Bind[F, S, B]]) =
      (p.a, p.f)
    override def to(p: Rep[(Free[F,S], S => Free[F,B])]) = {
      val Pair(a, f) = p
      Bind(a, f)
    }
    lazy val defaultRepTo = Default.defaultVal[Rep[Bind[F, S, B]]](Bind(element[Free[F,S]].defaultRepValue, fun { (x: Rep[S]) => element[Free[F,B]].defaultRepValue }))
    lazy val eTo = new BindElem[F, S, B](this)
  }
  // 4) constructor and deconstructor
  abstract class BindCompanionAbs extends CompanionBase[BindCompanionAbs] with BindCompanion {
    override def toString = "Bind"
    def apply[F[_], S, B](p: Rep[BindData[F, S, B]])(implicit eS: Elem[S], eA: Elem[B], cF: Cont[F]): Rep[Bind[F, S, B]] =
      isoBind(eS, eA, cF).to(p)
    def apply[F[_], S, B](a: Rep[Free[F,S]], f: Rep[S => Free[F,B]])(implicit eS: Elem[S], eA: Elem[B], cF: Cont[F]): Rep[Bind[F, S, B]] =
      mkBind(a, f)
  }
  object BindMatcher {
    def unapply[F[_], S, B](p: Rep[Free[F, B]]) = unmkBind(p)
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
  def mkBind[F[_], S, B](a: Rep[Free[F,S]], f: Rep[S => Free[F,B]])(implicit eS: Elem[S], eA: Elem[B], cF: Cont[F]): Rep[Bind[F, S, B]]
  def unmkBind[F[_], S, B](p: Rep[Free[F, B]]): Option[(Rep[Free[F,S]], Rep[S => Free[F,B]])]
}

// Seq -----------------------------------
trait FreesSeq extends FreesDsl with ScalanSeq {
  self: FreesDslSeq =>
  lazy val Free: Rep[FreeCompanionAbs] = new FreeCompanionAbs with UserTypeSeq[FreeCompanionAbs] {
    lazy val selfType = element[FreeCompanionAbs]
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
  def unmkReturn[F[_], A](p: Rep[Free[F, A]]) = p match {
    case p: Return[F, A] @unchecked =>
      Some((p.a))
    case _ => None
  }

  case class SeqSuspend[F[_], A]
      (override val a: Rep[F[A]])
      (implicit eA: Elem[A], cF: Cont[F])
    extends Suspend[F, A](a)
        with UserTypeSeq[Suspend[F, A]] {
    lazy val selfType = element[Suspend[F, A]]
  }
  lazy val Suspend = new SuspendCompanionAbs with UserTypeSeq[SuspendCompanionAbs] {
    lazy val selfType = element[SuspendCompanionAbs]
  }

  def mkSuspend[F[_], A]
      (a: Rep[F[A]])(implicit eA: Elem[A], cF: Cont[F]): Rep[Suspend[F, A]] =
      new SeqSuspend[F, A](a)
  def unmkSuspend[F[_], A](p: Rep[Free[F, A]]) = p match {
    case p: Suspend[F, A] @unchecked =>
      Some((p.a))
    case _ => None
  }

  case class SeqBind[F[_], S, B]
      (override val a: Rep[Free[F,S]], override val f: Rep[S => Free[F,B]])
      (implicit eS: Elem[S], eA: Elem[B], cF: Cont[F])
    extends Bind[F, S, B](a, f)
        with UserTypeSeq[Bind[F, S, B]] {
    lazy val selfType = element[Bind[F, S, B]]
  }
  lazy val Bind = new BindCompanionAbs with UserTypeSeq[BindCompanionAbs] {
    lazy val selfType = element[BindCompanionAbs]
  }

  def mkBind[F[_], S, B]
      (a: Rep[Free[F,S]], f: Rep[S => Free[F,B]])(implicit eS: Elem[S], eA: Elem[B], cF: Cont[F]): Rep[Bind[F, S, B]] =
      new SeqBind[F, S, B](a, f)
  def unmkBind[F[_], S, B](p: Rep[Free[F, B]]) = p match {
    case p: Bind[F, S, B] @unchecked =>
      Some((p.a, p.f))
    case _ => None
  }
}

// Exp -----------------------------------
trait FreesExp extends FreesDsl with ScalanExp {
  self: FreesDslExp =>
  lazy val Free: Rep[FreeCompanionAbs] = new FreeCompanionAbs with UserTypeDef[FreeCompanionAbs] {
    lazy val selfType = element[FreeCompanionAbs]
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

    object flatMapBy {
      def unapply(d: Def[_]): Option[(Rep[Return[F, A]], Rep[A => Free[F,B]]) forSome {type F[_]; type A; type B}] = d match {
        case MethodCall(receiver, method, Seq(f, _*), _) if (receiver.elem.asInstanceOf[Element[_]] match { case _: ReturnElem[_, _] => true; case _ => false }) && method.getName == "flatMapBy" =>
          Some((receiver, f)).asInstanceOf[Option[(Rep[Return[F, A]], Rep[A => Free[F,B]]) forSome {type F[_]; type A; type B}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Return[F, A]], Rep[A => Free[F,B]]) forSome {type F[_]; type A; type B}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object ReturnCompanionMethods {
  }

  def mkReturn[F[_], A]
    (a: Rep[A])(implicit eA: Elem[A], cF: Cont[F]): Rep[Return[F, A]] =
    new ExpReturn[F, A](a)
  def unmkReturn[F[_], A](p: Rep[Free[F, A]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: ReturnElem[F, A] @unchecked =>
      Some((p.asRep[Return[F, A]].a))
    case _ =>
      None
  }

  case class ExpSuspend[F[_], A]
      (override val a: Rep[F[A]])
      (implicit eA: Elem[A], cF: Cont[F])
    extends Suspend[F, A](a) with UserTypeDef[Suspend[F, A]] {
    lazy val selfType = element[Suspend[F, A]]
    override def mirror(t: Transformer) = ExpSuspend[F, A](t(a))
  }

  lazy val Suspend: Rep[SuspendCompanionAbs] = new SuspendCompanionAbs with UserTypeDef[SuspendCompanionAbs] {
    lazy val selfType = element[SuspendCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object SuspendMethods {
  }

  object SuspendCompanionMethods {
  }

  def mkSuspend[F[_], A]
    (a: Rep[F[A]])(implicit eA: Elem[A], cF: Cont[F]): Rep[Suspend[F, A]] =
    new ExpSuspend[F, A](a)
  def unmkSuspend[F[_], A](p: Rep[Free[F, A]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: SuspendElem[F, A] @unchecked =>
      Some((p.asRep[Suspend[F, A]].a))
    case _ =>
      None
  }

  case class ExpBind[F[_], S, B]
      (override val a: Rep[Free[F,S]], override val f: Rep[S => Free[F,B]])
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

    object flatMapBy {
      def unapply(d: Def[_]): Option[(Rep[Bind[F, S, B]], Rep[B => Free[F,R]]) forSome {type F[_]; type S; type B; type R}] = d match {
        case MethodCall(receiver, method, Seq(f1, _*), _) if (receiver.elem.asInstanceOf[Element[_]] match { case _: BindElem[_, _, _] => true; case _ => false }) && method.getName == "flatMapBy" =>
          Some((receiver, f1)).asInstanceOf[Option[(Rep[Bind[F, S, B]], Rep[B => Free[F,R]]) forSome {type F[_]; type S; type B; type R}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Bind[F, S, B]], Rep[B => Free[F,R]]) forSome {type F[_]; type S; type B; type R}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object BindCompanionMethods {
  }

  def mkBind[F[_], S, B]
    (a: Rep[Free[F,S]], f: Rep[S => Free[F,B]])(implicit eS: Elem[S], eA: Elem[B], cF: Cont[F]): Rep[Bind[F, S, B]] =
    new ExpBind[F, S, B](a, f)
  def unmkBind[F[_], S, B](p: Rep[Free[F, B]]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: BindElem[F, S, B] @unchecked =>
      Some((p.asRep[Bind[F, S, B]].a, p.asRep[Bind[F, S, B]].f))
    case _ =>
      None
  }

  object FreeMethods {
    // WARNING: Cannot generate matcher for method `flatMap`: Method has function arguments f

    object flatMapBy {
      def unapply(d: Def[_]): Option[(Rep[Free[F, A]], Rep[A => Free[F,B]]) forSome {type F[_]; type A; type B}] = d match {
        case MethodCall(receiver, method, Seq(f, _*), _) if (receiver.elem.asInstanceOf[Element[_]] match { case _: FreeElem[_, _, _] => true; case _ => false }) && method.getName == "flatMapBy" =>
          Some((receiver, f)).asInstanceOf[Option[(Rep[Free[F, A]], Rep[A => Free[F,B]]) forSome {type F[_]; type A; type B}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Free[F, A]], Rep[A => Free[F,B]]) forSome {type F[_]; type A; type B}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object mapBy {
      def unapply(d: Def[_]): Option[(Rep[Free[F, A]], Rep[A => B]) forSome {type F[_]; type A; type B}] = d match {
        case MethodCall(receiver, method, Seq(f, _*), _) if (receiver.elem.asInstanceOf[Element[_]] match { case _: FreeElem[_, _, _] => true; case _ => false }) && method.getName == "mapBy" =>
          Some((receiver, f)).asInstanceOf[Option[(Rep[Free[F, A]], Rep[A => B]) forSome {type F[_]; type A; type B}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Free[F, A]], Rep[A => B]) forSome {type F[_]; type A; type B}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    // WARNING: Cannot generate matcher for method `map`: Method has function arguments f

    object step {
      def unapply(d: Def[_]): Option[Rep[Free[F, A]] forSome {type F[_]; type A}] = d match {
        case MethodCall(receiver, method, _, _) if (receiver.elem.asInstanceOf[Element[_]] match { case _: FreeElem[_, _, _] => true; case _ => false }) && method.getName == "step" =>
          Some(receiver).asInstanceOf[Option[Rep[Free[F, A]] forSome {type F[_]; type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[Free[F, A]] forSome {type F[_]; type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object FreeCompanionMethods {
  }
}
