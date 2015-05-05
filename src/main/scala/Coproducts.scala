package coproducts

import scalan._

trait Coproducts extends Base { self: CoproductsDsl =>

  type RCoproduct[F[_],G[_],A] = Rep[Coproduct[F,G,A]]

  sealed trait Coproduct[F[_], G[_], A] extends Reifiable[Coproduct[F,G,A]] {
    implicit def cF: Cont[F]
    implicit def cG: Cont[G]
    implicit def eA: Elem[A]
    def run: Rep[F[A] | G[A]]
  }
  trait CoproductCompanion

  abstract class CoproductImpl[F[_],G[_],A]
  (val run: Rep[Either[F[A],G[A]]])
  (implicit val cF: Cont[F], val cG: Cont[G], val eA: Elem[A])
    extends Coproduct[F,G,A]

  trait CoproductImplCompanion
}

trait CoproductsDsl extends impl.CoproductsAbs
trait CoproductsDslSeq extends impl.CoproductsSeq
trait CoproductsDslExp extends impl.CoproductsExp