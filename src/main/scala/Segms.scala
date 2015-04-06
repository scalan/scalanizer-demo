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
    def length: Int = end - start
    def shift(ofs: Int) = new Interval(start + ofs, end + ofs)
  }
  trait IntervalCompanion

  class Slice(val start: Int, val length: Int) extends Segm {
    def end: Int = start + length
    def shift(ofs: Int) = new Slice(start + ofs, length)
  }
  trait SliceCompanion

  class Centered(val center: Int, val radius: Int) extends Segm {
    def start: Int = center - radius
    def end: Int = center + radius
    def length: Int = radius * 2
    def shift(ofs: Int) = new Centered(center + ofs, radius)
  }
  trait CenteredCompanion
}
