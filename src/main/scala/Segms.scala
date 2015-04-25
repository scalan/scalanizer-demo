package segms

trait Segms {

  trait Segm {
    def start: Int
    def length: Int
    def end: Int
    def shift(ofs: Int): Segm
  }

  class Interval(val start: Int, val end: Int) extends Segm {
    def length: Int = {
      val flag: Boolean = true
      def foo: (Int, Long, Double) = (0, 1, 2.0)
      if (flag) {
        val diff: Int = end - start
        diff: Int
      } else foo._1
    }
    def shift(ofs: Int) = new Interval(start + ofs, end + ofs)
  }

  class Slice(val start: Int, val length: Int) extends Segm {
    def end: Int = start + length
    def shift(ofs: Int) = shiftBy((x: Int) => x + ofs)
    def shiftBy(f: Int => Int) = new Slice(f(start), length)
    def id = Slice.this
  }

  class Centered(val center: Int, val radius: Int) extends Segm {
    def start: Int = center - radius
    def end: Int = center + radius
    def length: Int = radius * 2
    def shift(ofs: Int) = new Centered(center + ofs, radius)
    def id = this
  }
}
