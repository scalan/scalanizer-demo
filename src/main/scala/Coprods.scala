package coprods

trait Coprods {

  trait Coprod[F[_], G[_], A] {
    def run: Either[F[A],G[A]]
  }

  abstract class CoproductImpl[F[_],G[_],A]
    (val run: Either[F[A],G[A]])
    extends Coprod[F,G,A]
}
