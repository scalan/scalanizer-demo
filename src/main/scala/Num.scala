import scalan._

@staged
trait Num {
  def incInt(x: Int = 0)(y: Int = 1): Int = x + y
}
