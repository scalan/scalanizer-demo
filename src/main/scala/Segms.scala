package segms

trait Segms {

  trait Segm {
    def start: Int
    def length: Int
    def end: Int
    def shift(ofs: Int): Segm
    def inc(x: Int):Int = x + 1
  }

  class Interval(val start: Int, val end: Int) extends Segm {

    def length: Int = {
      type IT = segms.Segms#Interval
      val flag: Boolean = true
      def foo[T <: Double](sec: Long): (Int, Long, Double) = (0, sec, 2.asInstanceOf[Double])
      if (flag) {
        val diff: Int = end - start
        diff: Int
      } else foo[Double](10)._1
    }
    def shift(ofs: Int) = {
      val res = new Interval(start + ofs, end + ofs)

      res
    }
  }

  class Slice(val start: Int, val length: Int) extends Segm {
    def end: Int = start + length
    def shift(ofs: Int) = shiftBy((x: Int) => x + ofs)
    def shiftBy(f: Int => Int) = new Slice(f(start), length)
    def id = Slice.this
    override def inc(x: Int): Int = x + 2
  }

  @SerialVersionUID(value = 1)
  class Centered(@SerialVersionUID(2) val center: Int, val radius: Int) extends Segm {
    def start: Int = center - radius
    def end: Int = center + radius
    def length: Int = radius * 2: @inline
    def shift(ofs: Int) = new Centered(center = center + ofs, radius)
    @SerialVersionUID(3) def id = this
  }
}
