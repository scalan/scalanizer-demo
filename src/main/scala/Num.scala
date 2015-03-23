import scalan._

@CakeSlice
trait Nums {

  @UDT
  trait Num {
    def a: Int
    def add(b: Int = 0): Int
    def abs: Int
  }

  @UDT
  class BaseNum(val a: Int = 1) extends Num {
    def add(b: Int = 0): Int = a + b
    def abs: Int = if (a < 0) -a else a
  }
}
