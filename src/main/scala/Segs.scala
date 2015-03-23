import scalan._

@CakeSlice
trait Segs {

  @UDT
  trait Seg {
    def start: Int
    def length: Int
    def end: Int
    def shift(ofs: Int): Seg
  }

  @UDT
  class Interval(val start: Int, val end: Int) extends Seg {
    def length = end - start
    def shift(ofs: Int) = ??? //new Interval(start + ofs, end + ofs)
  }

  @UDT
  class Slice(val start: Int, val length: Int) extends Seg {
    def end = start + length
    def shift(ofs: Int) = ??? //new Slice(start + ofs, length)
  }

  @UDT
  class Centered(val center: Int, val radius: Int) extends Seg {
    def start = center - radius
    def end = center + radius
    def length = radius * 2
    def shift(ofs: Int) = ??? //new Centered(center + ofs, radius)
  }
}
