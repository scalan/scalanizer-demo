import scalan._

@staged
trait Num {
  val y: Int = 1
  def incInt(x: Int = 0): Int = x + y
}
