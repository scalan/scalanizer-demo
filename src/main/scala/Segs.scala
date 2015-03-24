package segs

trait Segments {

  trait Segment {
    def start: Int
    def length: Int
    def end: Int
    //def shift(ofs: Int): Seg
  }

  class Interval(val start: Int, val end: Int) extends Segment {
    def length = end - start
    //def shift(ofs: Int) = new Interval(start + ofs, end + ofs)
  }

  class Slice(val start: Int, val length: Int) extends Segment {
    def end = start + length
    //def shift(ofs: Int) = new Slice(start + ofs, length)
  }

  class Centered(val center: Int, val radius: Int) extends Segment {
    def start = center - radius
    def end = center + radius
    def length = radius * 2
    //def shift(ofs: Int) = new Centered(center + ofs, radius)
  }
}
