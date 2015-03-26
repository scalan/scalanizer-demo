package segms

trait Segms {

  trait Segm {
    def start: Int
    def length: Int
    def end: Int
    def shift(ofs: Int): Segm
  }
  trait SegmCompanion

  class Interval(val start: Int, val end: Int) extends Segm {
    def length = end - start
    def shift(ofs: Int) = new Interval(start + ofs, end + ofs)
  }
  trait IntervalCompanion

  class Slice(val start: Int, val length: Int) extends Segm {
    def end = start + length
    def shift(ofs: Int) = Slice(start + ofs, length)
  }
  trait SliceCompanion

  class Centered(val center: Int, val radius: Int) extends Segm {
    def start = center - radius
    def end = center + radius
    def length = radius * 2
    def shift(ofs: Int) = Centered(center + ofs, radius)
  }
  trait CenteredCompanion
}
