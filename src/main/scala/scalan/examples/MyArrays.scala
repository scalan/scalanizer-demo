package scalan.examples

import scalan._
import scalan.arrays.ArrayOps
import scalan.common.Default
import scalan.common.OverloadHack.Overloaded1

trait MyArrays extends ArrayOps { self: MyArraysDsl =>

  /**
   * Optional type synonim to hide boilerplate of using Rep
   */
  type MyArr[A] = Rep[MyArray[A]]

  /**
   * User defined type
   */
  trait MyArray[A] extends Reifiable[MyArray[A]] {
    implicit def elem: Elem[A]
    def length: Rep[Int]
    def arr: Rep[Array[A]]
    def apply(i: Rep[Int]): Rep[A]
    def map[B: Elem](f: Rep[A] => Rep[B]): MyArr[B] = MyArray(arr.map(f))
    def mapBy[B: Elem](f: Rep[A => B]): MyArr[B] = MyArray(arr.mapBy(f))
    def zip[B: Elem](ys: MyArr[B]): MyArr[(A, B)] = PairMyArray(self, ys)
  }

  /**
   * Declare this implicit to specify how to build Array descriptor (Elem) generically using 
   * descriptor of its element type.
   * This is a generic function that builds descriptors from descriptors
   * @tparam A type of array element
   * @return default descriptor for a given element type
   */
  implicit def defaultMyArrayElement[A:Elem]: Elem[MyArray[A]] = element[A] match {
    case _: BaseElem[_] => element[BaseMyArray[A]].asElem[MyArray[A]]
    case pe: PairElem[a, b] =>
      implicit val ea = pe.eFst
      implicit val eb = pe.eSnd
      element[PairMyArray[a, b]].asElem[MyArray[A]]
    case viewE: ViewElem[_, _] => element[BaseMyArray[A]].asElem[MyArray[A]]
    case e => ???(s"Element is $e")
  }

  /**
   * Companion for MyArray type. Naming convention is used here.
   * Generated companion MyArray is inherited from this trait.
   */
  trait MyArrayCompanion extends TypeFamily1[MyArray] {
    /**
     * Specify how to construct default values of this type family
     * @param ea descriptor of MyArray elements
     * @tparam A type of MyArray elements
     * @return instance of Default type class
     */
    def defaultOf[A](implicit ea: Elem[A]): Default[Rep[MyArray[A]]] = ea match {
      case UnitElement => Default.defaultVal(UnitMyArray(0).asRep[MyArray[A]])
      case baseE: BaseElem[a] => BaseMyArray.defaultOf[a](baseE)
      case pairE: PairElem[a, b] => PairMyArray.defaultOf[a, b](pairE.eFst, pairE.eSnd)
      case e => ???(s"Element is $e")
    }

    /**
     * Constructs MyArray from Array and can be invoked using companion like this
     * val my = MyArray(arr)
     * @param arr
     * @tparam T
     * @return
     */
    def apply[T: Elem](arr: Rep[Array[T]]): MyArr[T] = fromArray(arr)

    /**
     * Construct MyArray in a generic way using type descriptor of its elements.
     * This funtions takes input array and puts it into different structures depending on element type.
     * @param arr input array
     * @tparam T type of array elements
     * @return
     */
    def fromArray[T: Elem](arr: Rep[Array[T]]): MyArr[T] = {
      element[T] match {
        case baseE: BaseElem[a] =>
          BaseMyArray[a](arr.asRep[Array[a]])
        case pairE: PairElem[a, b] =>
          implicit val ea = pairE.eFst
          implicit val eb = pairE.eSnd
          val ps = arr.asRep[Array[(a, b)]]
          val as = fromArray(ps.map { _._1 })
          val bs = fromArray(ps.map { _._2 })
          as zip bs //PairMyArray[a,b](as, bs)
        case viewE: ViewElem[a, b] =>
          BaseMyArray[b](arr.asRep[Array[b]])
        case e => ???(s"Element is $e")
      }
    }

    /**
     * Another example of MyArray constructor. Uses core Array primitives.
     * @param len number of elements in the new MyArray
     * @param v   value to put into each element of MyArray
     * @tparam T
     * @return
     */
    def replicate[T: Elem](len: Rep[Int], v: Rep[T]): MyArr[T] = {
      element[T] match {
        case baseE: BaseElem[a] =>
          BaseMyArray[a](array_replicate(len, v.asRep[a]))
        case pairElem: PairElem[a ,b] => {
          implicit val ea = pairElem.eFst
          implicit val eb = pairElem.eSnd
          val ps = v.asRep[(a, b)]
          val as = replicate(len, ps._1)
          val bs = replicate(len, ps._2)
          as zip bs
        }
        case viewElem: ViewElem[a, b] =>
          BaseMyArray(Array.replicate(len, v))
        case e => ???(s"Element is $e")
      }
    }
  }

  abstract class UnitMyArray(val len: Rep[Int]) extends MyArray[Unit] {
    def elem = UnitElement
    def arr = Array.replicate(len, ())
    def length = len
    def apply(i: Rep[Int]) = ()
  }
  trait UnitMyArrayCompanion extends ConcreteClass0[UnitMyArray] {
    def defaultOf = Default.defaultVal(UnitMyArray(0))
  }

  abstract class BaseMyArray[A](val arr: Rep[Array[A]])(implicit val eA: Elem[A]) extends MyArray[A] {
    def elem = eA
    def length = arr.length
    def apply(i: Rep[Int]) = arr(i)
  }
  trait BaseMyArrayCompanion extends ConcreteClass1[BaseMyArray] {
    def defaultOf[A](implicit ea: Elem[A]) =
      Default.defaultVal(BaseMyArray(Default.defaultOf[Rep[Array[A]]]))
  }

  abstract class PairMyArray[A, B](val as: Rep[MyArray[A]], val bs: Rep[MyArray[B]])(implicit val eA: Elem[A], val eB: Elem[B])
    extends MyArray[(A, B)] {
    lazy val elem = element[(A, B)]
    def arr = as.arr zip bs.arr
    def apply(i: Rep[Int]) = (as(i), bs(i))
    def length = as.length
  }
  trait PairMyArrayCompanion extends ConcreteClass2[PairMyArray] with MyArrayCompanion {
    def defaultOf[A, B](implicit ea: Elem[A], eb: Elem[B]) = {
      val as = MyArray.defaultOf[A].value
      val bs = MyArray.defaultOf[B].value
      Default.defaultVal(PairMyArray(as, bs))
    }
  }
}

trait MyArraysDsl extends Scalan with impl.MyArraysAbs with MyArrays { }

trait MyArraysDslSeq extends MyArraysDsl with impl.MyArraysSeq with ScalanSeq

trait MyArraysDslExp extends MyArraysDsl with impl.MyArraysExp with ScalanExp
