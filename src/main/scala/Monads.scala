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
//    def when[A:Elem](b: Rep[Boolean])(fa: => Rep[F[A]]): Rep[F[Boolean]] =
//      IF (b) { as(fa)(true) } ELSE { unit(false) }

    def compose[A:Elem,B:Elem,C:Elem](f: Rep[A] => Rep[F[B]], g: Rep[B] => Rep[F[C]]): Rep[A => F[C]] =
      fun {a => flatMap(f(a))(g)}

    def sequence[A:Elem](lma: Rep[List[F[A]]]): Rep[F[List[A]]] =
      lma.foldRight[F[List[A]]](unit(SList.empty[A])) { (p: Rep[(F[A],F[List[A]])]) =>
        val ma = p._1
        val mla = p._2
        map2(ma, mla)(_ :: _)
      }

    def traverse[A:Elem,B:Elem](la: Lst[A])(f: Rep[A] => Rep[F[B]]): Rep[F[List[B]]] =
      la.foldRight[F[List[B]]](unit(SList.empty[B])){ (in: Rep[(A,F[List[B]])]) =>
        val a = in._1
        val mlb = in._2
        map2(f(a), mlb)(_ :: _)
      }

    def replicateM[A:Elem](n: Rep[Int])(ma: Rep[F[A]]): Rep[F[List[A]]] =
      sequence(SList.replicate(n, ma))
    def replicateM_[A:Elem](n: Rep[Int])(f: Rep[F[A]]): Rep[F[Unit]] =
      foreachM(SList.replicate(n, f))(skip)

    def foldM[A:Elem,B:Elem](la: Lst[A])(z: Rep[B])(f: (Rep[B],Rep[A]) => Rep[F[B]]): Rep[F[B]] =
      la.foldLeft[F[B]](unit(z)){ (in: Rep[(F[B],A)]) =>
        //val Pair(fb, a) = in
        val fb = in._1
        val a = in._2
        flatMap(fb)(b => f(b,a))
      }
    def foldM_[A:Elem,B:Elem](l: Lst[A])(z: Rep[B])(f: (Rep[B],Rep[A]) => Rep[F[B]]): Rep[F[Unit]] =
      skip { foldM(l)(z)(f) }

    def foreachM[A:Elem](l: Rep[List[A]])(f: Rep[A] => Rep[F[Unit]]): Rep[F[Unit]] =
      foldM_(l)(())((u,a) => skip(f(a)))

    def filterM[A:Elem](ms: Lst[A])(f: Rep[A] => Rep[F[Boolean]]): Rep[F[List[A]]] =
      ms.foldRight(unit(SList.empty[A])){ (in: Rep[(A,F[List[A]])]) =>
        //val Pair(x, y) = in
        val x = in._1
        val y = in._2
        val h = compose(f, (b: Rep[Boolean]) => IF (b) THEN {map2(unit(x),y)(_ :: _)} ELSE { y })
        h(x)
      }
  }
  trait MonadCompanion
}

trait MonadsDsl extends impl.MonadsAbs
trait MonadsDslSeq extends impl.MonadsSeq
trait MonadsDslExp extends impl.MonadsExp