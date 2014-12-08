package scalan.examples

import java.io.File
import java.lang.reflect.Method
import scalan.compilation.GraphVizExport
import scalan.{BaseShouldTests, ScalanCtxExp, ScalanCtxSeq}

/**
 * Use this suite as a template for your own tests
 */
class MyArrayExamplesSuite extends BaseShouldTests {

  val testFolder = "test-out/scalan/myarrays/"

  "when mixing trait" should "be constructed in Seq context" in {
      val ctx = new ScalanCtxSeq with MyArraysDslSeq with MyArrayExamples {}
  }
  
  it should "be constructed in Staged context" in {
    val ctx = new ScalanCtxExp with MyArraysDslExp with MyArrayExamples {}
  }

  "in seq context" should "execute functions" in {
    val ctx = new ScalanCtxSeq with MyArraysDslSeq with MyArrayExamples {}
    val in = Array((1,2f), (3,4f), (5,6f))
    val res = ctx.fromAndTo(in)
    res should be(in)
  }

  def testMethod(name: String) = {
    val ctx = new ScalanCtxExp with MyArraysDslExp with MyArrayExamples with GraphVizExport {
      // invoke all domain methods if possible
      override def isInvokeEnabled(d: Def[_], m: Method) = true
    }
    val f = ctx.getStagedFunc(name)
    ctx.emitDepGraph(f, new File(s"$testFolder/$name.dot"), false)
  }

  val whenStaged = "when staged"
  whenStaged should "fromArray" beArgFor { testMethod(_) }
  whenStaged should "fromArrayOfPairs" beArgFor { testMethod(_) }
  whenStaged should "fromAndTo" beArgFor { testMethod(_) }
  whenStaged should "mapped" beArgFor { testMethod(_) }
  whenStaged should "zippedMap" beArgFor { testMethod(_) }
  whenStaged should "mapped2" beArgFor { testMethod(_) }
  whenStaged should "splitMap" beArgFor { testMethod(_) }
  whenStaged should "splitMap2" beArgFor { testMethod(_) }
  whenStaged should "mapInc3Times" beArgFor { testMethod(_) }
  whenStaged should "splitMap3" beArgFor { testMethod(_) }
  whenStaged should "splitMapMap" beArgFor { testMethod(_) }
  whenStaged should "mapScalar" beArgFor { testMethod(_) }
  whenStaged should "expBaseArraysInIf" beArgFor { testMethod(_) }
  whenStaged should "expBaseArraysInIfSpec" beArgFor { testMethod(_) }
  whenStaged should "expPairArraysInIf" beArgFor { testMethod(_) }
  whenStaged should "expPairArraysInIfSpec" beArgFor { testMethod(_) }
  whenStaged should "expPairArraysInIfDiffTypes" beArgFor { testMethod(_) }
  whenStaged should "expPairArraysInIfDiffTypesSpec" beArgFor { testMethod(_) }
  whenStaged should "pairInIf" beArgFor { testMethod(_) }
  whenStaged should "pairInIfSpec" beArgFor { testMethod(_) }
  whenStaged should "nestedPairInIf" beArgFor { testMethod(_) }
  whenStaged should "nestedPairInIfSpec" beArgFor { testMethod(_) }

}
