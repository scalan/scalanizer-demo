package monads

import scalan._

trait Monads extends Base {self: MonadsDsl =>
  type RMonad[F[_]] = Rep[Monad[F]]

  trait Monad[F[_]] extends Reifiable[Monad[F]] {
    implicit def cF: Cont[F]

    def unit[A: Elem](a: Rep[A]): Rep[F[A]]

    def flatMap[A: Elem, B: Elem](ma: Rep[F[A]])(f: Rep[A] => Rep[F[B]]): Rep[F[B]] = join(map(ma)(f))

    def map[A: Elem, B: Elem](ma: Rep[F[A]])(f: Rep[A] => Rep[B]): Rep[F[B]] =
      flatMap(ma)(a => unit(f(a)))

    def join[A:Elem](mma: Rep[F[F[A]]]): Rep[F[A]] = flatMap(mma)(ma => ma)
  }
  trait MonadCompanion
}

trait MonadsDsl extends impl.MonadsAbs
trait MonadsDslSeq extends impl.MonadsSeq
trait MonadsDslExp extends impl.MonadsExp