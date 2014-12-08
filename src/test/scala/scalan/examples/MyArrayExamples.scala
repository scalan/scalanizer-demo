package scalan.examples

import scalan.ScalanDsl
import scalan.primitives.PrimitiveExamples

trait MyArrayExamples extends ScalanDsl with MyArraysDsl with PrimitiveExamples {
  lazy val fromArray = fun { xs: Arr[Int] => MyArray(xs) }
  lazy val fromArrayOfPairs = fun { xs: Arr[(Int,Float)] => MyArray(xs) }
  lazy val fromAndTo = fun { xs: Arr[(Int,Float)] =>
    val ps = MyArray(xs)
    ps.arr
  }

  lazy val mapped = fun {(xs: MyArr[Int]) => xs.mapBy(inc) }
  lazy val zippedMap = fun {(xs: MyArr[Int]) => (xs zip xs).mapBy(tupled) }
  lazy val mapped2 = fun {(xs: MyArr[Int]) => xs.mapBy(inc2) }

  lazy val splitMap = fun {(xs: MyArr[Int]) => Pair(xs.mapBy(inc), xs.mapBy(inc2)) }
  lazy val splitMap2 = fun {(xs: MyArr[Int]) => Pair(xs.mapBy(inc_times), xs.mapBy(inc2)) }
  lazy val mapInc3Times = fun {(xs: MyArr[Int]) => Tuple(xs.mapBy(inc), xs.mapBy(inc), xs.mapBy(inc)) }
  lazy val splitMap3 = fun {(xs: MyArr[Int]) => Tuple(xs.mapBy(inc), xs.mapBy(inc2), xs.mapBy(inc_times)) }
  lazy val splitMapMap = fun {(xs: MyArr[Int]) => Pair(xs.mapBy(inc), xs.mapBy(inc2).mapBy(inc_times)) }

  lazy val mapScalar = fun {(xs: MyArr[Int]) => xs.mapBy(scalar) }
  lazy val mapArrays = fun { xs: Arr[Int] => xs.mapBy(inc) }

  lazy val expBaseArraysInIf = fun { xs: Arr[Int] =>
    val ys = BaseMyArray(xs)
    val zs = BaseMyArray(xs.map { x => x + 1 })
    val res = IF (xs.length > 10) THEN { ys } ELSE { zs }
    res
  }
  lazy val expBaseArraysInIfSpec = fun { xs: Arr[Int] =>
    expBaseArraysInIf(xs).arr
  }

  lazy val expPairArraysInIf = fun { xs: Arr[Int] =>
    val pairs1 = PairMyArray(BaseMyArray(xs), BaseMyArray(xs))
    val ys = xs.map { x => x + 1 }
    val pairs2 = PairMyArray(BaseMyArray(ys), BaseMyArray(xs))
    val res = IF (xs.length > 10) THEN { pairs1 } ELSE { pairs2 }
    res
  }
  lazy val expPairArraysInIfSpec = fun { xs: Arr[Int] =>
    expPairArraysInIf(xs).arr
  }

  lazy val expPairArraysInIfDiffTypes = fun { xs: Arr[(Int,Int)] =>
    val ys = xs.map { x => x._1 + x._2 }
    val pairs1: MyArr[(Int,Int)] = PairMyArray(BaseMyArray(ys), BaseMyArray(ys))
    val pairs2: MyArr[(Int,Int)] = BaseMyArray(xs)
    val res = IF (xs.length > 10) THEN { pairs1 } ELSE { pairs2 }
    res
  }
  lazy val expPairArraysInIfDiffTypesSpec = fun { xs: Arr[(Int,Int)] =>
    expPairArraysInIfDiffTypes(xs).arr
  }

  lazy val pairInIf = fun { in: Arr[Int] =>
    val xs = BaseMyArray(in)
    val ys = xs.map { x => x + 1 }
    val res = IF (xs.length > 10) THEN { Pair(ys, xs) } ELSE { Pair(xs,xs) }
    res._1
  }
  lazy val pairInIfSpec = fun { xs: Arr[Int] =>
    pairInIf(xs).arr
  }

  lazy val nestedPairInIf = fun { in: Arr[Int] =>
    val xs: MyArr[Int] = BaseMyArray(in)
    val ys = xs.map { x => x + 1 }
    val res = IF (xs.length > 10) THEN { Pair(Pair(xs, ys), xs) } ELSE { Pair(Pair(xs,xs),ys) }
    Pair(res._1._2, res._2)
  }
  lazy val nestedPairInIfSpec = fun { xs: Arr[Int] =>
    val res = nestedPairInIf(xs)
    (res._1.arr, res._2.arr)
  }

}
