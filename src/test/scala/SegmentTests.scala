package scalan.paradise

import scala.language.reflectiveCalls
import scalan._

class SegmentTests extends BaseTests {suite =>

  trait SimpleSegmentTest extends ParadiseDsl {
    lazy val length = fun {(in: Rep[(Int, Int)]) => {
      val Pair(start, end) = in
      val interval = Interval(start, end)

      interval.length
    }}
  }

  test("simpleSegmentStaged") {
    val ctx = new TestContext(this, "simpleSegmentStaged") with ParadiseDslExp with SimpleSegmentTest {
      def test() = {}
    }
    ctx.emit("length", ctx.length)
  }

  test("simpleSegmentSeq") {
    val ctx = new ScalanCtxSeq with ParadiseDslSeq with SimpleSegmentTest {
      def test() = {}
    }
    val start: Int = -10
    val end: Int = 1
    val len = ctx.length((start, end))

    assertResult(end - start)(len)
  }
}
