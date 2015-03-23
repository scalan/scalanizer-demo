import scalan._

@CakeSlice
trait Segments {

  @UDT
  trait Segment {
    def start: Int
    def length: Int
    def end: Int
    def shift(ofs: Int): Segment
  }

  @UDT
  class Interval(val start: Int, val end: Int) extends Segment {
    def length = end - start
    def shift(ofs: Int) = ??? //Interval(start + ofs, end + ofs)
  }

  @UDT
  class Slice(val start: Int, val length: Int) extends Segment {
    def end = start + length
    def shift(ofs: Int) = ??? //Slice(start + ofs, length)
  }

  @UDT
  class Centered(val center: Int, val radius: Int) extends Segment {
    def start = center - radius
    def end = center + radius
    def length = radius * 2
    def shift(ofs: Int) = ??? //Centered(center + ofs, radius)
  }
}
