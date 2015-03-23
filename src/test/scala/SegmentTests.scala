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
}
