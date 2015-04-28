package knds

import scalan._
import scala.reflect.runtime.universe._
import knds._
import knds.StagedEvaluation._

trait KndsExamples extends Scalan with KndsDsl {
  type Id[A] = A

  implicit val containerId: Cont[Id] = new Container[Id] {
    def tag[A](implicit evA: WeakTypeTag[A]) = weakTypeTag[Id[A]]
    def lift[A](implicit evA: Elem[A]) = evA
  }

  lazy val t1 = fun { (in: Rep[Knd[Id,Int]]) => in }

  lazy val kindMap = fun { (in: Rep[Knd[Id,Int]]) => in.mapBy(fun {x => x + 1}) }
}

