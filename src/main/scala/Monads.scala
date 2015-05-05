package monads

import scalan._
import scalan.collections.ListOps

trait Monads extends Base with ListOps {self: MonadsDsl =>
  type RMonad[F[_]] = Rep[Monad[F]]

  trait Monad[F[_]] extends Reifiable[Monad[F]] {
    implicit def cF: Cont[F]

    def unit[A: Elem](a: Rep[A]): Rep[F[A]]

    def flatMap[A: Elem, B: Elem](ma: Rep[F[A]])(f: Rep[A] => Rep[F[B]]): Rep[F[B]] = join(map(ma)(f))

    def map[A: Elem, B: Elem](ma: Rep[F[A]])(f: Rep[A] => Rep[B]): Rep[F[B]] =
      flatMap(ma)(a => unit(f(a)))

    def join[A:Elem](mma: Rep[F[F[A]]]): Rep[F[A]] = flatMap(mma)(ma => ma)

    def map2[A:Elem,B:Elem,C:Elem](ma: Rep[F[A]], mb: Rep[F[B]])(f: (Rep[A], Rep[B]) => Rep[C]): Rep[F[C]] =
      flatMap(ma)(a => map(mb)(b => f(a, b)))

    def seq[A:Elem,B:Elem,C:Elem](f: Rep[A] => Rep[F[B]], g: Rep[B] => Rep[F[C]]): Rep[A => F[C]] =
      compose(f, g)

    def as[A:Elem,B:Elem](a: Rep[F[A]])(b: Rep[B]): Rep[F[B]] = map(a)(_ => b)
    def skip[A:Elem](a: Rep[F[A]]): Rep[F[Unit]] = as(a)(())
    def when[A:Elem](b: Rep[Boolean])(fa: => Rep[F[A]]): Rep[F[Boolean]] =
      IF (b) { as(fa)(true) } ELSE { unit(false) }

    def compose[A:Elem,B:Elem,C:Elem](f: Rep[A] => Rep[F[B]], g: Rep[B] => Rep[F[C]]): Rep[A => F[C]] =
      fun {a => flatMap(f(a))(g)}

    override def toString = "Monad"
  }
  trait MonadCompanion
}

trait MonadsDsl extends impl.MonadsAbs
trait MonadsDslSeq extends impl.MonadsSeq
trait MonadsDslExp extends impl.MonadsExp