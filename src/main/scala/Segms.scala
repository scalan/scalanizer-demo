package segms

trait Segms {

  trait Segm {
    def start: Int
    def length: Int
    def end: Int
    def shift(ofs: Int): Segm
  }

  case class Interval(val start: Int, val end: Int) extends Segm {
    def length: Int = end - start
    def shift(ofs: Int) = Interval(start + ofs, end + ofs)
  }

  case class Slice(val start: Int, val length: Int) extends Segm {
    def end: Int = start + length
    def shift(ofs: Int) = Slice(start + ofs, length)
  }

  case class Centered(val center: Int, val radius: Int) extends Segm {
    def start: Int = center - radius
    def end: Int = center + radius
    def length: Int = radius * 2
    def shift(ofs: Int) = Centered(center + ofs, radius)
  }
}
