package paradise

import scalan._
import paradise.implOfLinearAlgebraExamples.StagedEvaluation._

trait StagedLinearAlgebraTests extends BaseTests {
  class LaStaged(testName: String) extends TestContext(this, testName) with LinearAlgebraExamplesDslExp {
  }

  test("simple") {
    val ctx = new LaStaged("mvm")

    //ctx.emit("ddmvm", ctx.ddmvm)
  }
}
