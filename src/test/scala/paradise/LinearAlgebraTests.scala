package paradise

import org.scalatest._

class LinearAlgebraTests extends FunSuite with LinearAlgebraExamples {
  test("ddmvm") {
    val inM = Array(Array(1.0, 1.0), Array(0.0, 1.0))
    val inV = Array(2.0, 3.0)
    val laExample = new LinearAlgebraExample {}

    assertResult(Array(5.0, 3.0))(laExample.ddmvm0(inM, inV))
  }
}
