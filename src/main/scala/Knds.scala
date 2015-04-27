package knds

trait Knds {

  sealed trait Knd[F[_], A] { self =>
    def flatMap[B](f: A => Knd[F,B]): Knd[F,B] = new Bind(self, f)

    def mapBy[B](f: A => B): Knd[F,B] = flatMap(a => new Return(f(a)))
  }

  class Return[F[_],A](val a: A) extends Knd[F, A] {
    override def flatMap[B](f: A => Knd[F,B]): Knd[F,B] = f(a)
  }

  class Bind[F[_],S,B](val a: Knd[F, S], val f: S => Knd[F,B]) extends Knd[F,B] {
    override def flatMap[R](f1: B => Knd[F,R]): Knd[F,R] = {
      a.flatMap((s: S) => f(s).flatMap(f1))
    }
  }
}
