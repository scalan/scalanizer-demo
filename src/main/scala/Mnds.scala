package mnds

trait Mnds {

  trait Mnd[F[_]] {
    def unit[A](a: A): F[A]

    def flatMap[A, B](ma: F[A])(f: A => F[B]): F[B] = join(map(ma)(f))

    def map[A, B](ma: F[A])(f: A => B): F[B] =
      flatMap(ma)(a => unit(f(a)))

    def map2[A,B,C](ma: F[A], mb: F[B])(f: (A, B) => C): F[C] =
      flatMap(ma)(a => map(mb)(b => f(a, b)))

    def sequence[A](lma: List[F[A]]): F[List[A]] = {
      lma.foldRight[F[List[A]]](unit(List.empty[A])) {(ma: F[A], mla: F[List[A]]) =>
        map2(ma, mla)((a: A, la: List[A]) => a :: la)
      }
    }

    def traverse[A,B](la: List[A])(f: A => F[B]): F[List[B]] =
      la.foldRight[F[List[B]]](unit(List.empty[B])){ (a:A, mlb: F[List[B]]) =>
        map2(f(a), mlb)((fa: B, lb: List[B]) => fa :: lb)
      }

    def replicateM[A](n: Int)(ma: F[A]): F[List[A]] = {
      //sequence(SList.replicate(n, ma))
      sequence(List[F[A]]())
    }

    def replicateM_[A](n: Int)(f: F[A]): F[Unit] = {
      //foreachM(SList.replicate(n, f))(skip)
      foreachM(List[F[A]]())(skip(_)) // TODO: Should be just (skip)
    }

    def foldM[A,B](la: List[A])(z: B)(f: (B, A) => F[B]): F[B] =
      la.foldLeft[F[B]](unit(z)){ (fb: F[B], a: A) =>
        flatMap(fb)((b: B) => f(b,a))
      }
    def foldM_[A,B](l: List[A])(z: B)(f: (B,A) => F[B]): F[Unit] =
      skip { foldM(l)(z)(f) }

    def foreachM[A](l: List[A])(f: A => F[Unit]): F[Unit] =
      foldM_(l)(())((u: Unit,a: A) => skip(f(a)))

    def seq[A,B,C](f: A => F[B], g: B => F[C]): A => F[C] =
      compose(f, g)
    def as[A,B](a: F[A])(b: B): F[B] = map(a)(_ => b)
    def skip[A](a: F[A]): F[Unit] = as(a)(())
    //    def when[A](b: Boolean)(fa: => F[A]): F[Boolean] =
    //      if (b) { as(fa)(true) } else { unit(false) }

    def compose[A,B,C](f: A => F[B], g: B => F[C]): A => F[C] = a => flatMap(f(a))(g)

    def join[A](mma: F[F[A]]): F[A] = flatMap(mma)(ma => ma)

    def filterM[A](ms: List[A])(f: A => F[Boolean]): F[List[A]] =
      ms.foldRight(unit(List.empty[A])){ (x: A, y: F[List[A]]) =>
        val h = compose(f, (b: Boolean) => if (b) {map2(unit(x),y)((a: A, la: List[A]) => a :: la)} else { y })
        h(x)
      }
  }
}

