package segments

import scalan._

trait Segments extends Base with BaseTypes { self: SegmentsDsl =>

  type RSeg = Rep[Segment]
  trait Segment extends Reifiable[Segment] { self =>
    def start: Rep[Int]
    def length: Rep[Int]
    def end: Rep[Int]
    //def shift(ofs: Rep[Int]): Rep[Segment]
  }
  trait SegmentCompanion
  implicit def defaultSegmentElem: Elem[Segment] = element[Interval].asElem[Segment]

  abstract class Interval(val start: Rep[Int], val end: Rep[Int]) extends Segment {
    def length = end - start
    //def shift(ofs: Rep[Int]) = Interval(start + ofs, end + ofs)
  }
  trait IntervalCompanion

  abstract class Slice(val start: Rep[Int], val length: Rep[Int]) extends Segment {
    def end = start + length
    //def shift(ofs: Rep[Int]) = Slice(start + ofs, length)
  }
  trait SliceCompanion

  abstract class Centered(val center: Rep[Int], val radius: Rep[Int]) extends Segment {
    def start = center - radius
    def end = center + radius
    def length = radius * 2
    //def shift(ofs: Rep[Int]) = Centered(center + ofs, radius)
  }
  trait CenteredCompanion
}

trait SegmentsDsl extends impl.SegmentsAbs
trait SegmentsDslSeq extends impl.SegmentsSeq
trait SegmentsDslExp extends impl.SegmentsExp