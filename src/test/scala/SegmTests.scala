package scalan.paradise

import scala.language.reflectiveCalls
import scalan._
import segms._
import segms.StagedEvaluation._

class SegmTests extends BaseTests {suite =>

  trait SimpleSegmentTest extends SegmsDsl {
    lazy val length = fun {(in: Rep[(Int, Int)]) => {
      val Pair(start, end) = in
      val interval = Interval(start, end)

      interval.length
    }}

    lazy val shift = fun {(in: Rep[(Int, (Int, Int))]) => {
      val Pair(start, Pair(end, ofs)) = in
      val s = Interval(start, end)
      val shifted = s.shift(ofs)

      shifted.start
    }}
  }
/*
  test("simpleSegmentStaged") {
    val ctx = new TestContext(this, "simpleSegmentStaged") with SegmsDslExp with SimpleSegmentTest {
      def test() = {}
    }
    ctx.emit("length", ctx.length)
    ctx.emit("shift", ctx.shift)
  }
*/
  test("simpleSegmentSeq") {
    val ctx = new ScalanCtxSeq with SegmsDslSeq with SimpleSegmentTest {
      def test() = {}
    }
    val start: Int = -10
    val center = start
    val end: Int = 1
    val radius = 5

    val len = ctx.length((start, end))
    assertResult(end - start)(len)

    val ofs = 100
    val ofstart = ctx.shift((start, (end, ofs)))
    assertResult(start + ofs)(ofstart)
  }
}
