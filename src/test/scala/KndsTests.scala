package knds

import scala.language.reflectiveCalls
import scalan._
import knds._
import knds.StagedEvaluation._

class KndsTests extends BaseTests { suite =>

  class ConvProgStaged(testName: String) extends TestContext(this, testName) with KndsExamples with KndsDslExp {
  }
  class ConvProgSeq(testName: String) extends ScalanCtxSeq with  KndsExamples with KndsDslSeq {
  }

  test("simple kinds tests") {
    val ctx = new ConvProgStaged("simple kinds tests")
    ctx.emit("t1", ctx.t1)
  }

  test("kindMap") {
    //pending
    val name = "kindMap"
    val ctx = new ConvProgStaged(name)
    ctx.emit(name, ctx.kindMap)
  }

}
