package monads

import scalan._
import scala.reflect.runtime.universe._

trait Frees extends Base { self: FreesDsl =>

  type RFree[F[_],A] = Rep[Free[F,A]]
  sealed trait Free[F[_], A] extends Reifiable[Free[F,A]] {
    implicit def eA: Elem[A]
    implicit def cF: Cont[F]

    def flatMap[B:Elem](f: Rep[A] => Rep[Free[F,B]]): Rep[Free[F,B]] = flatMapBy(fun(f))
    def flatMapBy[B:Elem](f: Rep[A => Free[F,B]]): Rep[Free[F,B]] = Bind(self, f)

    def mapBy[B:Elem](f: Rep[A => B]): Rep[Free[F,B]] =
      flatMap(a => Return(f(a)))

    def map[B:Elem](f: Rep[A] => Rep[B]): Rep[Free[F,B]] = mapBy(fun(f))

//    def foldMap[G[_]:Monad](f: F ~> G): Rep[G[A]]
//    def run[G[_]:Monad](f: F ~> G): Rep[G[A]] = step.foldMap(f)
    def step: Rep[Free[F,A]] = self   // for Return and Suspend
  }
  trait FreeCompanion

  abstract class Return[F[_],A]
  (val a: Rep[A])
  (implicit val eA: Elem[A], val cF: Cont[F]) extends Free[F,A]
  {
    override def flatMap[B:Elem](f: Rep[A] => Rep[Free[F,B]]): Rep[Free[F,B]] = f(a)
    override def flatMapBy[B:Elem](f: Rep[A => Free[F,B]]): Rep[Free[F,B]] = f(a)

//    def foldMap[G[_]:Monad](f: F ~> G): Rep[G[A]] = Monad[G].unit(a)
  }
  trait ReturnCompanion

  abstract class Suspend[F[_],A]
  (val a: Rep[F[A]])
  (implicit val eA: Elem[A], val cF: Cont[F]) extends Free[F, A] {

//    def foldMap[G[_]:Monad](trans: F ~> G): Rep[G[A]] = trans(a)
  }
  trait SuspendCompanion

  abstract class Bind[F[_],S,B]
  (val a: Rep[Free[F, S]], val f: Rep[S => Free[F,B]])
  (implicit val eS: Elem[S], val eA: Elem[B], val cF: Cont[F]) extends Free[F,B] {

    override def flatMap[R:Elem](f1: Rep[B] => Rep[Free[F,R]]): Rep[Free[F,R]] =
      a.flatMap((s: Rep[S]) => f(s).flatMap(f1))
    override def flatMapBy[R:Elem](f1: Rep[B => Free[F,R]]): Rep[Free[F,R]] =
      a.flatMap((s: Rep[S]) => f(s).flatMapBy(f1))

//    def foldMap[G[_]:Monad](trans: F ~> G): Rep[G[B]] = a match {
//      case Def(susp: Suspend[F,S]) =>
//        Monad[G].flatMap(trans(susp.a)) { s =>
//          f(s).run(trans)
//        }
//      case Def(d) => !!!(s"Should be eliminated by method step: Unexpected $this where symbol $a of type ${a.selfType1} has def $d")
//      case _ => !!!(s"Should be eliminated by method step: Unexpected $this where symbol $a of type ${a.selfType1}")
//    }
    //      Monad[G].flatMap(trans(a)) { s =>
    //        f(s).foldMap(trans)
    //      }
    override lazy val step: Rep[Free[F,B]] = a match {
      case Def(b: Bind[F,s,S]) => b.a.flatMap((a: Rep[s]) => b.f(a).flatMapBy(f)).step
      case Def(ret: Return[F,S]) => f(ret.a).step
      case _ => self
    }
  }
  trait BindCompanion

}

trait FreesDsl extends impl.FreesAbs
trait FreesDslSeq extends impl.FreesSeq
trait FreesDslExp extends impl.FreesExp