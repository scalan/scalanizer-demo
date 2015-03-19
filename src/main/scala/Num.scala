import scalan._

@staged
trait Num extends Base with BaseTypes {self: Scalan =>
  def incInt(x: Rep[Int]) = x + toRep(1)
}
