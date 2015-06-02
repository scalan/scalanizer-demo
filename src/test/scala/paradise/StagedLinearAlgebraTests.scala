package paradise

import scalan._
import scala.language.reflectiveCalls
import paradise.implOfLinearAlgebraExamples.StagedEvaluation._

trait StagedLinearAlgebraTests extends BaseTests { suit =>

  trait SimpleLaTests extends ScalanDsl with LinearAlgebraExamplesDsl {
    lazy val t0 = fun {(in: Rep[MvMExample]) => in.ddmvm0}
  }

  test("simple") {
    val ctx = new TestContext(this, "simpleStagedLa") with SimpleLaTests with LinearAlgebraExamplesDslExp

    ctx.emit("t0", ctx.t0)
  }
}
