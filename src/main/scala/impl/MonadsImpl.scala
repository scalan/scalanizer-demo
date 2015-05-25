package monads
package impl

import scalan._
import scalan.collections.ListOps
import scala.reflect.runtime.universe._
import scala.reflect._
import scalan.common.Default

// Abs -----------------------------------
trait MonadsAbs extends Monads with ScalanDsl {
  self: MonadsDsl =>

  // single proxy for each type family
  implicit def proxyMonad[F[_]](p: Rep[Monad[F]]): Monad[F] = {
    proxyOps[Monad[F]](p)(classTag[Monad[F]])
  }

  // familyElem
  class MonadElem[F[_], To <: Monad[F]](implicit val cF: Cont[F])
    extends EntityElem[To] {
    override def isEntityType = true
    override lazy val tag = {
      weakTypeTag[Monad[F]].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = {
      val conv = fun {x: Rep[Monad[F]] =>  convertMonad(x) }
      tryConvert(element[Monad[F]], this, x, conv)
    }

    def convertMonad(x : Rep[Monad[F]]): Rep[To] = {
      assert(x.selfType1.asInstanceOf[Element[_]] match { case _: MonadElem[_, _] => true; case _ => false })
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def monadElement[F[_]](implicit cF: Cont[F]): Elem[Monad[F]] =
    new MonadElem[F, Monad[F]]

  implicit case object MonadCompanionElem extends CompanionElem[MonadCompanionAbs] {
    lazy val tag = weakTypeTag[MonadCompanionAbs]
    protected def getDefaultRep = Monad
  }

  abstract class MonadCompanionAbs extends CompanionBase[MonadCompanionAbs] with MonadCompanion {
    override def toString = "Monad"
  }
  def Monad: Rep[MonadCompanionAbs]
  implicit def proxyMonadCompanion(p: Rep[MonadCompanion]): MonadCompanion = {
    proxyOps[MonadCompanion](p)
  }
}

// Seq -----------------------------------
trait MonadsSeq extends MonadsDsl with ScalanSeq {
  self: MonadsDslSeq =>
  lazy val Monad: Rep[MonadCompanionAbs] = new MonadCompanionAbs with UserTypeSeq[MonadCompanionAbs] {
    lazy val selfType = element[MonadCompanionAbs]
  }
}

// Exp -----------------------------------
trait MonadsExp extends MonadsDsl with ScalanExp {
  self: MonadsDslExp =>
  lazy val Monad: Rep[MonadCompanionAbs] = new MonadCompanionAbs with UserTypeDef[MonadCompanionAbs] {
    lazy val selfType = element[MonadCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object MonadMethods {
    object unit {
      def unapply(d: Def[_]): Option[(Rep[Monad[F]], Rep[A]) forSome {type F[_]; type A}] = d match {
        case MethodCall(receiver, method, Seq(a, _*), _) if (receiver.elem.asInstanceOf[Element[_]] match { case _: MonadElem[_, _] => true; case _ => false }) && method.getName == "unit" =>
          Some((receiver, a)).asInstanceOf[Option[(Rep[Monad[F]], Rep[A]) forSome {type F[_]; type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Monad[F]], Rep[A]) forSome {type F[_]; type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    // WARNING: Cannot generate matcher for method `flatMap`: Method has function arguments f

    // WARNING: Cannot generate matcher for method `map`: Method has function arguments f

    object join {
      def unapply(d: Def[_]): Option[(Rep[Monad[F]], Rep[F[F[A]]]) forSome {type F[_]; type A}] = d match {
        case MethodCall(receiver, method, Seq(mma, _*), _) if (receiver.elem.asInstanceOf[Element[_]] match { case _: MonadElem[_, _] => true; case _ => false }) && method.getName == "join" =>
          Some((receiver, mma)).asInstanceOf[Option[(Rep[Monad[F]], Rep[F[F[A]]]) forSome {type F[_]; type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Monad[F]], Rep[F[F[A]]]) forSome {type F[_]; type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    // WARNING: Cannot generate matcher for method `map2`: Method has function arguments f

    // WARNING: Cannot generate matcher for method `seq`: Method has function arguments f, g

    object as {
      def unapply(d: Def[_]): Option[(Rep[Monad[F]], Rep[F[A]], Rep[B]) forSome {type F[_]; type A; type B}] = d match {
        case MethodCall(receiver, method, Seq(a, b, _*), _) if (receiver.elem.asInstanceOf[Element[_]] match { case _: MonadElem[_, _] => true; case _ => false }) && method.getName == "as" =>
          Some((receiver, a, b)).asInstanceOf[Option[(Rep[Monad[F]], Rep[F[A]], Rep[B]) forSome {type F[_]; type A; type B}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Monad[F]], Rep[F[A]], Rep[B]) forSome {type F[_]; type A; type B}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object skip {
      def unapply(d: Def[_]): Option[(Rep[Monad[F]], Rep[F[A]]) forSome {type F[_]; type A}] = d match {
        case MethodCall(receiver, method, Seq(a, _*), _) if (receiver.elem.asInstanceOf[Element[_]] match { case _: MonadElem[_, _] => true; case _ => false }) && method.getName == "skip" =>
          Some((receiver, a)).asInstanceOf[Option[(Rep[Monad[F]], Rep[F[A]]) forSome {type F[_]; type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Monad[F]], Rep[F[A]]) forSome {type F[_]; type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    // WARNING: Cannot generate matcher for method `compose`: Method has function arguments f, g

    object sequence {
      def unapply(d: Def[_]): Option[(Rep[Monad[F]], Rep[List[F[A]]]) forSome {type F[_]; type A}] = d match {
        case MethodCall(receiver, method, Seq(lma, _*), _) if (receiver.elem.asInstanceOf[Element[_]] match { case _: MonadElem[_, _] => true; case _ => false }) && method.getName == "sequence" =>
          Some((receiver, lma)).asInstanceOf[Option[(Rep[Monad[F]], Rep[List[F[A]]]) forSome {type F[_]; type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Monad[F]], Rep[List[F[A]]]) forSome {type F[_]; type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    // WARNING: Cannot generate matcher for method `traverse`: Method has function arguments f

    object replicateM {
      def unapply(d: Def[_]): Option[(Rep[Monad[F]], Rep[Int], Rep[F[A]]) forSome {type F[_]; type A}] = d match {
        case MethodCall(receiver, method, Seq(n, ma, _*), _) if (receiver.elem.asInstanceOf[Element[_]] match { case _: MonadElem[_, _] => true; case _ => false }) && method.getName == "replicateM" =>
          Some((receiver, n, ma)).asInstanceOf[Option[(Rep[Monad[F]], Rep[Int], Rep[F[A]]) forSome {type F[_]; type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Monad[F]], Rep[Int], Rep[F[A]]) forSome {type F[_]; type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object replicateM_ {
      def unapply(d: Def[_]): Option[(Rep[Monad[F]], Rep[Int], Rep[F[A]]) forSome {type F[_]; type A}] = d match {
        case MethodCall(receiver, method, Seq(n, f, _*), _) if (receiver.elem.asInstanceOf[Element[_]] match { case _: MonadElem[_, _] => true; case _ => false }) && method.getName == "replicateM_" =>
          Some((receiver, n, f)).asInstanceOf[Option[(Rep[Monad[F]], Rep[Int], Rep[F[A]]) forSome {type F[_]; type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Monad[F]], Rep[Int], Rep[F[A]]) forSome {type F[_]; type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    // WARNING: Cannot generate matcher for method `foldM`: Method has function arguments f

    // WARNING: Cannot generate matcher for method `foldM_`: Method has function arguments f

    // WARNING: Cannot generate matcher for method `foreachM`: Method has function arguments f

    // WARNING: Cannot generate matcher for method `filterM`: Method has function arguments f
  }

  object MonadCompanionMethods {
  }
}
