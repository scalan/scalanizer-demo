package mnds

trait Mnds {

  trait Monad[F[_]] {
    def unit[A](a: A): F[A]
  }
}

