package paradise

import org.scalatest._

class LinearAlgebraTests extends FunSuite with LinearAlgebraExamples {



  test("ddmvm") {
    val rows = 10000
    val cols = rows;

    println(s"""Generating random ${rows}x${cols} dense "Array[Array[Double]]" matrix...""")
    val inM = CommonData.genMatr(rows, cols)
    println(s"""Generating random ${rows} elements "Array[Double]" vector...""")
    val inV = CommonData.genArray(cols)

    val correctRes = inM.map({r:Array[Double] => r.zip(inV).map({p => p._1 * p._2}).fold(0.0)(_+_)})

    println( s"Multiplying matrix by vector..." )
    val t1 = System.currentTimeMillis()
    val res = MvMExample().ddmvm0(inM, inV)
    val t2 = System.currentTimeMillis()

    assertResult(correctRes)(res)

    val dt = t2 - t1
    println("")
    println(s"Multiplication is done in $dt millis.")
  }
}
