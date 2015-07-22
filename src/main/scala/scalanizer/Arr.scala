package scalanizer

trait BaseArr[T] {
  def apply(j: Int): T = throw new Error()
}

class Arr[T] extends BaseArr[T] {
  def length: Int = throw new Error()
}

