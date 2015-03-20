import scalan._

@staged
trait Num {
  val y: Int = 1: Int
  def incInt(x: Int = 0): Int = x + y
}
