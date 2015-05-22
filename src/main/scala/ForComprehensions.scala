package forcomp

trait ForComprehensions {
  trait ForComprehension {
    def isOdd(n: Int): Boolean = n % 2 == 1
    def test(n: Int) = {
      for {
        i <- 1 until n
        j <- 1 until i
        if isOdd(i+j)
      } yield (i, j)
    }
  }
}
