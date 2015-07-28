package scalanizer

trait BaseArr[T] {
  def apply(j: Int): T = throw new Error()
}

class MyArr[T] extends BaseArr[T] {
//  def length: Int = throw new Error()
  val length = 0
}

