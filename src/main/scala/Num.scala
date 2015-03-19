import scalan._

@staged
trait Num {
  def incInt(x: Rep[Int]) = x + 1
}
