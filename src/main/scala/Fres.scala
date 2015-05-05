package fres

trait Fres {

  trait Free[F[_], A] {

    def flatMap[B](f: A => Free[F,B]): Free[F,B] = flatMapBy(f)
    def flatMapBy[B](f: A => Free[F,B]): Free[F,B] = new Bind(this, f)

    def mapBy[B](f: A => B): Free[F,B] = flatMap(a => new Return(f(a)))
    def map[B](f: A => B): Free[F,B] = mapBy(f)

    def step: Free[F,A] = this
  }

  class Return[F[_],A](val a: A) extends Free[F,A]
  {
    override def flatMap[B](f: A => Free[F,B]): Free[F,B] = f(a)
    override def flatMapBy[B](f: A => Free[F,B]): Free[F,B] = f(a)
  }

  class Suspend[F[_],A](val a: F[A]) extends Free[F, A]

  class Bind[F[_],S,B](val a: Free[F, S], val f: S => Free[F,B]) extends Free[F,B] {
    override def flatMap[R](f1: B => Free[F,R]): Free[F,R] =
      a.flatMap((s: S) => f(s).flatMap(f1))
    override def flatMapBy[R](f1: B => Free[F,R]): Free[F,R] =
      a.flatMap((s: S) => f(s).flatMapBy(f1))
  }
}

