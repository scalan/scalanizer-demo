package mnds

trait Mnds {

  trait Mnd[F[_]] {
    def unit[A](a: A): F[A]

    def flatMap[A, B](ma: F[A])(f: A => F[B]): F[B] = join(map(ma)(f))

    def map[A, B](ma: F[A])(f: A => B): F[B] =
      flatMap(ma)(a => unit(f(a)))

    def join[A](mma: F[F[A]]): F[A] = flatMap(mma)(ma => ma)
  }
}

