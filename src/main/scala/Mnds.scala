package mnds

trait Mnds {

  trait Mnd[F[_]] {
    def unit[A](a: A): F[A]

    def flatMap[A, B](ma: F[A])(f: A => F[B]): F[B] = join(map(ma)(f))

    def map[A, B](ma: F[A])(f: A => B): F[B] =
      flatMap(ma)(a => unit(f(a)))

    def join[A](mma: F[F[A]]): F[A] = flatMap(mma)(ma => ma)

    def map2[A,B,C](ma: F[A], mb: F[B])(f: (A, B) => C): F[C] =
      flatMap(ma)(a => map(mb)(b => f(a, b)))

    def seq[A,B,C](f: A => F[B], g: B => F[C]): A => F[C] =
      compose(f, g)

    def as[A,B](a: F[A])(b: B): F[B] = map(a)(_ => b)
    def skip[A](a: F[A]): F[Unit] = as(a)(())
//    def when[A](b: Boolean)(fa: => F[A]): F[Boolean] =
//      if (b) { as(fa)(true) } else { unit(false) }

    def compose[A,B,C](f: A => F[B], g: B => F[C]): A => F[C] = a => flatMap(f(a))(g)
  }
}

