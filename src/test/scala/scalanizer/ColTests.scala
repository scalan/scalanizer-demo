package scalanizer

import org.scalatest._

class ColTests extends FunSuite with Demo {

  test("applyTest") {
    val arr = new MyArr[Int]()
    val res = ColOverArray.safeApply(arr, -1)
    assertResult(Int.MinValue)(res)
  }
}
