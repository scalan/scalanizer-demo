package scalan.examples
package impl

import scalan._
import scalan.arrays.ArrayOps
import scalan.common.Default
import scalan.common.OverloadHack.Overloaded1
import scala.reflect.runtime.universe._
import scalan.common.Default

trait MyArraysAbs extends MyArrays
{ self: MyArraysDsl =>
  // single proxy for each type family
  implicit def proxyMyArray[A](p: Rep[MyArray[A]]): MyArray[A] =
    proxyOps[MyArray[A]](p)

  abstract class MyArrayElem[A, From, To <: MyArray[A]](iso: Iso[From, To]) extends ViewElem[From, To]()(iso)

  trait MyArrayCompanionElem extends CompanionElem[MyArrayCompanionAbs]
  implicit lazy val MyArrayCompanionElem: MyArrayCompanionElem = new MyArrayCompanionElem {
    lazy val tag = typeTag[MyArrayCompanionAbs]
    lazy val defaultRep = Default.defaultVal(MyArray)
  }

  trait MyArrayCompanionAbs extends MyArrayCompanion {
    override def toString = "MyArray"
  }
  def MyArray: Rep[MyArrayCompanionAbs]
  implicit def proxyMyArrayCompanion(p: Rep[MyArrayCompanion]): MyArrayCompanion = {
    proxyOps[MyArrayCompanion](p)
  }

  // elem for concrete class
  class UnitMyArrayElem(iso: Iso[UnitMyArrayData, UnitMyArray]) extends MyArrayElem[Unit, UnitMyArrayData, UnitMyArray](iso)

  // state representation type
  type UnitMyArrayData = Int

  // 3) Iso for concrete class
  class UnitMyArrayIso
    extends Iso[UnitMyArrayData, UnitMyArray] {
    override def from(p: Rep[UnitMyArray]) =
      unmkUnitMyArray(p) match {
        case Some((len)) => len
        case None => !!!
      }
    override def to(p: Rep[Int]) = {
      val len = p
      UnitMyArray(len)
    }
    lazy val tag = {

      typeTag[UnitMyArray]
    }
    lazy val defaultRepTo = Default.defaultVal[Rep[UnitMyArray]](UnitMyArray(0))
    lazy val eTo = new UnitMyArrayElem(this)
  }
  // 4) constructor and deconstructor
  trait UnitMyArrayCompanionAbs extends UnitMyArrayCompanion {
    override def toString = "UnitMyArray"

    def apply(len: Rep[Int]): Rep[UnitMyArray] =
      mkUnitMyArray(len)
    def unapply(p: Rep[UnitMyArray]) = unmkUnitMyArray(p)
  }
  def UnitMyArray: Rep[UnitMyArrayCompanionAbs]
  implicit def proxyUnitMyArrayCompanion(p: Rep[UnitMyArrayCompanionAbs]): UnitMyArrayCompanionAbs = {
    proxyOps[UnitMyArrayCompanionAbs](p)
  }

  class UnitMyArrayCompanionElem extends CompanionElem[UnitMyArrayCompanionAbs] {
    lazy val tag = typeTag[UnitMyArrayCompanionAbs]
    lazy val defaultRep = Default.defaultVal(UnitMyArray)
  }
  implicit lazy val UnitMyArrayCompanionElem: UnitMyArrayCompanionElem = new UnitMyArrayCompanionElem

  implicit def proxyUnitMyArray(p: Rep[UnitMyArray]): UnitMyArray =
    proxyOps[UnitMyArray](p)

  implicit class ExtendedUnitMyArray(p: Rep[UnitMyArray]) {
    def toData: Rep[UnitMyArrayData] = isoUnitMyArray.from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoUnitMyArray: Iso[UnitMyArrayData, UnitMyArray] =
    new UnitMyArrayIso

  // 6) smart constructor and deconstructor
  def mkUnitMyArray(len: Rep[Int]): Rep[UnitMyArray]
  def unmkUnitMyArray(p: Rep[UnitMyArray]): Option[(Rep[Int])]

  // elem for concrete class
  class BaseMyArrayElem[A](iso: Iso[BaseMyArrayData[A], BaseMyArray[A]]) extends MyArrayElem[A, BaseMyArrayData[A], BaseMyArray[A]](iso)

  // state representation type
  type BaseMyArrayData[A] = Array[A]

  // 3) Iso for concrete class
  class BaseMyArrayIso[A](implicit eA: Elem[A])
    extends Iso[BaseMyArrayData[A], BaseMyArray[A]] {
    override def from(p: Rep[BaseMyArray[A]]) =
      unmkBaseMyArray(p) match {
        case Some((arr)) => arr
        case None => !!!
      }
    override def to(p: Rep[Array[A]]) = {
      val arr = p
      BaseMyArray(arr)
    }
    lazy val tag = {
      implicit val tagA = element[A].tag
      typeTag[BaseMyArray[A]]
    }
    lazy val defaultRepTo = Default.defaultVal[Rep[BaseMyArray[A]]](BaseMyArray(element[Array[A]].defaultRepValue))
    lazy val eTo = new BaseMyArrayElem[A](this)
  }
  // 4) constructor and deconstructor
  trait BaseMyArrayCompanionAbs extends BaseMyArrayCompanion {
    override def toString = "BaseMyArray"

    def apply[A](arr: Rep[Array[A]])(implicit eA: Elem[A]): Rep[BaseMyArray[A]] =
      mkBaseMyArray(arr)
    def unapply[A:Elem](p: Rep[BaseMyArray[A]]) = unmkBaseMyArray(p)
  }
  def BaseMyArray: Rep[BaseMyArrayCompanionAbs]
  implicit def proxyBaseMyArrayCompanion(p: Rep[BaseMyArrayCompanionAbs]): BaseMyArrayCompanionAbs = {
    proxyOps[BaseMyArrayCompanionAbs](p)
  }

  class BaseMyArrayCompanionElem extends CompanionElem[BaseMyArrayCompanionAbs] {
    lazy val tag = typeTag[BaseMyArrayCompanionAbs]
    lazy val defaultRep = Default.defaultVal(BaseMyArray)
  }
  implicit lazy val BaseMyArrayCompanionElem: BaseMyArrayCompanionElem = new BaseMyArrayCompanionElem

  implicit def proxyBaseMyArray[A:Elem](p: Rep[BaseMyArray[A]]): BaseMyArray[A] =
    proxyOps[BaseMyArray[A]](p)

  implicit class ExtendedBaseMyArray[A](p: Rep[BaseMyArray[A]])(implicit eA: Elem[A]) {
    def toData: Rep[BaseMyArrayData[A]] = isoBaseMyArray(eA).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoBaseMyArray[A](implicit eA: Elem[A]): Iso[BaseMyArrayData[A], BaseMyArray[A]] =
    new BaseMyArrayIso[A]

  // 6) smart constructor and deconstructor
  def mkBaseMyArray[A](arr: Rep[Array[A]])(implicit eA: Elem[A]): Rep[BaseMyArray[A]]
  def unmkBaseMyArray[A:Elem](p: Rep[BaseMyArray[A]]): Option[(Rep[Array[A]])]

  // elem for concrete class
  class PairMyArrayElem[A, B](iso: Iso[PairMyArrayData[A, B], PairMyArray[A, B]]) extends MyArrayElem[(A,B), PairMyArrayData[A, B], PairMyArray[A, B]](iso)

  // state representation type
  type PairMyArrayData[A, B] = (MyArray[A], MyArray[B])

  // 3) Iso for concrete class
  class PairMyArrayIso[A, B](implicit eA: Elem[A], eB: Elem[B])
    extends Iso[PairMyArrayData[A, B], PairMyArray[A, B]] {
    override def from(p: Rep[PairMyArray[A, B]]) =
      unmkPairMyArray(p) match {
        case Some((as, bs)) => Pair(as, bs)
        case None => !!!
      }
    override def to(p: Rep[(MyArray[A], MyArray[B])]) = {
      val Pair(as, bs) = p
      PairMyArray(as, bs)
    }
    lazy val tag = {
      implicit val tagA = element[A].tag
      implicit val tagB = element[B].tag
      typeTag[PairMyArray[A, B]]
    }
    lazy val defaultRepTo = Default.defaultVal[Rep[PairMyArray[A, B]]](PairMyArray(element[MyArray[A]].defaultRepValue, element[MyArray[B]].defaultRepValue))
    lazy val eTo = new PairMyArrayElem[A, B](this)
  }
  // 4) constructor and deconstructor
  trait PairMyArrayCompanionAbs extends PairMyArrayCompanion {
    override def toString = "PairMyArray"
    def apply[A, B](p: Rep[PairMyArrayData[A, B]])(implicit eA: Elem[A], eB: Elem[B]): Rep[PairMyArray[A, B]] =
      isoPairMyArray(eA, eB).to(p)
    def apply[A, B](as: Rep[MyArray[A]], bs: Rep[MyArray[B]])(implicit eA: Elem[A], eB: Elem[B]): Rep[PairMyArray[A, B]] =
      mkPairMyArray(as, bs)
    def unapply[A:Elem, B:Elem](p: Rep[PairMyArray[A, B]]) = unmkPairMyArray(p)
  }
  def PairMyArray: Rep[PairMyArrayCompanionAbs]
  implicit def proxyPairMyArrayCompanion(p: Rep[PairMyArrayCompanionAbs]): PairMyArrayCompanionAbs = {
    proxyOps[PairMyArrayCompanionAbs](p)
  }

  class PairMyArrayCompanionElem extends CompanionElem[PairMyArrayCompanionAbs] {
    lazy val tag = typeTag[PairMyArrayCompanionAbs]
    lazy val defaultRep = Default.defaultVal(PairMyArray)
  }
  implicit lazy val PairMyArrayCompanionElem: PairMyArrayCompanionElem = new PairMyArrayCompanionElem

  implicit def proxyPairMyArray[A:Elem, B:Elem](p: Rep[PairMyArray[A, B]]): PairMyArray[A, B] =
    proxyOps[PairMyArray[A, B]](p)

  implicit class ExtendedPairMyArray[A, B](p: Rep[PairMyArray[A, B]])(implicit eA: Elem[A], eB: Elem[B]) {
    def toData: Rep[PairMyArrayData[A, B]] = isoPairMyArray(eA, eB).from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoPairMyArray[A, B](implicit eA: Elem[A], eB: Elem[B]): Iso[PairMyArrayData[A, B], PairMyArray[A, B]] =
    new PairMyArrayIso[A, B]

  // 6) smart constructor and deconstructor
  def mkPairMyArray[A, B](as: Rep[MyArray[A]], bs: Rep[MyArray[B]])(implicit eA: Elem[A], eB: Elem[B]): Rep[PairMyArray[A, B]]
  def unmkPairMyArray[A:Elem, B:Elem](p: Rep[PairMyArray[A, B]]): Option[(Rep[MyArray[A]], Rep[MyArray[B]])]
}

trait MyArraysSeq extends MyArraysAbs { self: ScalanSeq with MyArraysDsl =>
  lazy val MyArray: Rep[MyArrayCompanionAbs] = new MyArrayCompanionAbs with UserTypeSeq[MyArrayCompanionAbs, MyArrayCompanionAbs] {
    lazy val selfType = element[MyArrayCompanionAbs]
  }

  case class SeqUnitMyArray
      (override val len: Rep[Int])
      
    extends UnitMyArray(len) with UserTypeSeq[MyArray[Unit], UnitMyArray] {
    lazy val selfType = element[UnitMyArray].asInstanceOf[Elem[MyArray[Unit]]]
  }
  lazy val UnitMyArray = new UnitMyArrayCompanionAbs with UserTypeSeq[UnitMyArrayCompanionAbs, UnitMyArrayCompanionAbs] {
    lazy val selfType = element[UnitMyArrayCompanionAbs]
  }

  def mkUnitMyArray
      (len: Rep[Int]) =
      new SeqUnitMyArray(len)
  def unmkUnitMyArray(p: Rep[UnitMyArray]) =
    Some((p.len))

  case class SeqBaseMyArray[A]
      (override val arr: Rep[Array[A]])
      (implicit eA: Elem[A])
    extends BaseMyArray[A](arr) with UserTypeSeq[MyArray[A], BaseMyArray[A]] {
    lazy val selfType = element[BaseMyArray[A]].asInstanceOf[Elem[MyArray[A]]]
  }
  lazy val BaseMyArray = new BaseMyArrayCompanionAbs with UserTypeSeq[BaseMyArrayCompanionAbs, BaseMyArrayCompanionAbs] {
    lazy val selfType = element[BaseMyArrayCompanionAbs]
  }

  def mkBaseMyArray[A]
      (arr: Rep[Array[A]])(implicit eA: Elem[A]) =
      new SeqBaseMyArray[A](arr)
  def unmkBaseMyArray[A:Elem](p: Rep[BaseMyArray[A]]) =
    Some((p.arr))

  case class SeqPairMyArray[A, B]
      (override val as: Rep[MyArray[A]], override val bs: Rep[MyArray[B]])
      (implicit eA: Elem[A], eB: Elem[B])
    extends PairMyArray[A, B](as, bs) with UserTypeSeq[MyArray[(A,B)], PairMyArray[A, B]] {
    lazy val selfType = element[PairMyArray[A, B]].asInstanceOf[Elem[MyArray[(A,B)]]]
  }
  lazy val PairMyArray = new PairMyArrayCompanionAbs with UserTypeSeq[PairMyArrayCompanionAbs, PairMyArrayCompanionAbs] {
    lazy val selfType = element[PairMyArrayCompanionAbs]
  }

  def mkPairMyArray[A, B]
      (as: Rep[MyArray[A]], bs: Rep[MyArray[B]])(implicit eA: Elem[A], eB: Elem[B]) =
      new SeqPairMyArray[A, B](as, bs)
  def unmkPairMyArray[A:Elem, B:Elem](p: Rep[PairMyArray[A, B]]) =
    Some((p.as, p.bs))
}

trait MyArraysExp extends MyArraysAbs { self: ScalanExp with MyArraysDsl =>
  lazy val MyArray: Rep[MyArrayCompanionAbs] = new MyArrayCompanionAbs with UserTypeDef[MyArrayCompanionAbs, MyArrayCompanionAbs] {
    lazy val selfType = element[MyArrayCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  case class ExpUnitMyArray
      (override val len: Rep[Int])
      
    extends UnitMyArray(len) with UserTypeDef[MyArray[Unit], UnitMyArray] {
    lazy val selfType = element[UnitMyArray].asInstanceOf[Elem[MyArray[Unit]]]
    override def mirror(t: Transformer) = ExpUnitMyArray(t(len))
  }

  lazy val UnitMyArray: Rep[UnitMyArrayCompanionAbs] = new UnitMyArrayCompanionAbs with UserTypeDef[UnitMyArrayCompanionAbs, UnitMyArrayCompanionAbs] {
    lazy val selfType = element[UnitMyArrayCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object UnitMyArrayMethods {
    object elem {
      def unapply(d: Def[_]): Option[Rep[UnitMyArray]] = d match {
        case MethodCall(receiver, method, _) if method.getName == "elem" && receiver.elem.isInstanceOf[UnitMyArrayElem] =>
          Some(receiver).asInstanceOf[Option[Rep[UnitMyArray]]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[UnitMyArray]] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object arr {
      def unapply(d: Def[_]): Option[Rep[UnitMyArray]] = d match {
        case MethodCall(receiver, method, _) if method.getName == "arr" && receiver.elem.isInstanceOf[UnitMyArrayElem] =>
          Some(receiver).asInstanceOf[Option[Rep[UnitMyArray]]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[UnitMyArray]] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object length {
      def unapply(d: Def[_]): Option[Rep[UnitMyArray]] = d match {
        case MethodCall(receiver, method, _) if method.getName == "length" && receiver.elem.isInstanceOf[UnitMyArrayElem] =>
          Some(receiver).asInstanceOf[Option[Rep[UnitMyArray]]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[UnitMyArray]] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object apply {
      def unapply(d: Def[_]): Option[(Rep[UnitMyArray], Rep[Int])] = d match {
        case MethodCall(receiver, method, Seq(i, _*)) if method.getName == "apply" && receiver.elem.isInstanceOf[UnitMyArrayElem] =>
          Some((receiver, i)).asInstanceOf[Option[(Rep[UnitMyArray], Rep[Int])]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[UnitMyArray], Rep[Int])] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object UnitMyArrayCompanionMethods {
    object defaultOf {
      def unapply(d: Def[_]): Option[Unit] = d match {
        case MethodCall(receiver, method, _) if method.getName == "defaultOf" && receiver.elem.isInstanceOf[UnitMyArrayCompanionElem] =>
          Some(()).asInstanceOf[Option[Unit]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Unit] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  def mkUnitMyArray
    (len: Rep[Int]) =
    new ExpUnitMyArray(len)
  def unmkUnitMyArray(p: Rep[UnitMyArray]) =
    Some((p.len))

  case class ExpBaseMyArray[A]
      (override val arr: Rep[Array[A]])
      (implicit eA: Elem[A])
    extends BaseMyArray[A](arr) with UserTypeDef[MyArray[A], BaseMyArray[A]] {
    lazy val selfType = element[BaseMyArray[A]].asInstanceOf[Elem[MyArray[A]]]
    override def mirror(t: Transformer) = ExpBaseMyArray[A](t(arr))
  }

  lazy val BaseMyArray: Rep[BaseMyArrayCompanionAbs] = new BaseMyArrayCompanionAbs with UserTypeDef[BaseMyArrayCompanionAbs, BaseMyArrayCompanionAbs] {
    lazy val selfType = element[BaseMyArrayCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object BaseMyArrayMethods {
    object elem {
      def unapply(d: Def[_]): Option[Rep[BaseMyArray[A]] forSome {type A}] = d match {
        case MethodCall(receiver, method, _) if method.getName == "elem" && receiver.elem.isInstanceOf[BaseMyArrayElem[_]] =>
          Some(receiver).asInstanceOf[Option[Rep[BaseMyArray[A]] forSome {type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[BaseMyArray[A]] forSome {type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object length {
      def unapply(d: Def[_]): Option[Rep[BaseMyArray[A]] forSome {type A}] = d match {
        case MethodCall(receiver, method, _) if method.getName == "length" && receiver.elem.isInstanceOf[BaseMyArrayElem[_]] =>
          Some(receiver).asInstanceOf[Option[Rep[BaseMyArray[A]] forSome {type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[BaseMyArray[A]] forSome {type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object apply {
      def unapply(d: Def[_]): Option[(Rep[BaseMyArray[A]], Rep[Int]) forSome {type A}] = d match {
        case MethodCall(receiver, method, Seq(i, _*)) if method.getName == "apply" && receiver.elem.isInstanceOf[BaseMyArrayElem[_]] =>
          Some((receiver, i)).asInstanceOf[Option[(Rep[BaseMyArray[A]], Rep[Int]) forSome {type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[BaseMyArray[A]], Rep[Int]) forSome {type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object BaseMyArrayCompanionMethods {
    object defaultOf {
      def unapply(d: Def[_]): Option[Unit forSome {type A}] = d match {
        case MethodCall(receiver, method, _) if method.getName == "defaultOf" && receiver.elem.isInstanceOf[BaseMyArrayCompanionElem] =>
          Some(()).asInstanceOf[Option[Unit forSome {type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Unit forSome {type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  def mkBaseMyArray[A]
    (arr: Rep[Array[A]])(implicit eA: Elem[A]) =
    new ExpBaseMyArray[A](arr)
  def unmkBaseMyArray[A:Elem](p: Rep[BaseMyArray[A]]) =
    Some((p.arr))

  case class ExpPairMyArray[A, B]
      (override val as: Rep[MyArray[A]], override val bs: Rep[MyArray[B]])
      (implicit eA: Elem[A], eB: Elem[B])
    extends PairMyArray[A, B](as, bs) with UserTypeDef[MyArray[(A,B)], PairMyArray[A, B]] {
    lazy val selfType = element[PairMyArray[A, B]].asInstanceOf[Elem[MyArray[(A,B)]]]
    override def mirror(t: Transformer) = ExpPairMyArray[A, B](t(as), t(bs))
  }

  lazy val PairMyArray: Rep[PairMyArrayCompanionAbs] = new PairMyArrayCompanionAbs with UserTypeDef[PairMyArrayCompanionAbs, PairMyArrayCompanionAbs] {
    lazy val selfType = element[PairMyArrayCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object PairMyArrayMethods {
    object arr {
      def unapply(d: Def[_]): Option[Rep[PairMyArray[A, B]] forSome {type A; type B}] = d match {
        case MethodCall(receiver, method, _) if method.getName == "arr" && receiver.elem.isInstanceOf[PairMyArrayElem[_, _]] =>
          Some(receiver).asInstanceOf[Option[Rep[PairMyArray[A, B]] forSome {type A; type B}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[PairMyArray[A, B]] forSome {type A; type B}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object apply {
      def unapply(d: Def[_]): Option[(Rep[PairMyArray[A, B]], Rep[Int]) forSome {type A; type B}] = d match {
        case MethodCall(receiver, method, Seq(i, _*)) if method.getName == "apply" && receiver.elem.isInstanceOf[PairMyArrayElem[_, _]] =>
          Some((receiver, i)).asInstanceOf[Option[(Rep[PairMyArray[A, B]], Rep[Int]) forSome {type A; type B}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[PairMyArray[A, B]], Rep[Int]) forSome {type A; type B}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object length {
      def unapply(d: Def[_]): Option[Rep[PairMyArray[A, B]] forSome {type A; type B}] = d match {
        case MethodCall(receiver, method, _) if method.getName == "length" && receiver.elem.isInstanceOf[PairMyArrayElem[_, _]] =>
          Some(receiver).asInstanceOf[Option[Rep[PairMyArray[A, B]] forSome {type A; type B}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[PairMyArray[A, B]] forSome {type A; type B}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object PairMyArrayCompanionMethods {
    object defaultOf {
      def unapply(d: Def[_]): Option[Unit forSome {type A; type B}] = d match {
        case MethodCall(receiver, method, _) if method.getName == "defaultOf" && receiver.elem.isInstanceOf[PairMyArrayCompanionElem] =>
          Some(()).asInstanceOf[Option[Unit forSome {type A; type B}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Unit forSome {type A; type B}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  def mkPairMyArray[A, B]
    (as: Rep[MyArray[A]], bs: Rep[MyArray[B]])(implicit eA: Elem[A], eB: Elem[B]) =
    new ExpPairMyArray[A, B](as, bs)
  def unmkPairMyArray[A:Elem, B:Elem](p: Rep[PairMyArray[A, B]]) =
    Some((p.as, p.bs))

  object MyArrayMethods {
    object length {
      def unapply(d: Def[_]): Option[Rep[MyArray[A]] forSome {type A}] = d match {
        case MethodCall(receiver, method, _) if method.getName == "length" && receiver.elem.isInstanceOf[MyArrayElem[_, _, _]] =>
          Some(receiver).asInstanceOf[Option[Rep[MyArray[A]] forSome {type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[MyArray[A]] forSome {type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object arr {
      def unapply(d: Def[_]): Option[Rep[MyArray[A]] forSome {type A}] = d match {
        case MethodCall(receiver, method, _) if method.getName == "arr" && receiver.elem.isInstanceOf[MyArrayElem[_, _, _]] =>
          Some(receiver).asInstanceOf[Option[Rep[MyArray[A]] forSome {type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[MyArray[A]] forSome {type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object apply {
      def unapply(d: Def[_]): Option[(Rep[MyArray[A]], Rep[Int]) forSome {type A}] = d match {
        case MethodCall(receiver, method, Seq(i, _*)) if method.getName == "apply" && receiver.elem.isInstanceOf[MyArrayElem[_, _, _]] =>
          Some((receiver, i)).asInstanceOf[Option[(Rep[MyArray[A]], Rep[Int]) forSome {type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[MyArray[A]], Rep[Int]) forSome {type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    // WARNING: Cannot generate matcher for method `map` : method has Non-Rep argument f: Rep[A] => Rep[B] 

    object mapBy {
      def unapply(d: Def[_]): Option[(Rep[MyArray[A]], Rep[A => B]) forSome {type A; type B}] = d match {
        case MethodCall(receiver, method, Seq(f, _*)) if method.getName == "mapBy" && receiver.elem.isInstanceOf[MyArrayElem[_, _, _]] =>
          Some((receiver, f)).asInstanceOf[Option[(Rep[MyArray[A]], Rep[A => B]) forSome {type A; type B}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[MyArray[A]], Rep[A => B]) forSome {type A; type B}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    // WARNING: Cannot generate matcher for method `zip` : method has Non-Rep argument ys: MyArr[B] 
  }

  object MyArrayCompanionMethods {
    object defaultOf {
      def unapply(d: Def[_]): Option[Unit forSome {type A}] = d match {
        case MethodCall(receiver, method, _) if method.getName == "defaultOf" && receiver.elem.isInstanceOf[MyArrayCompanionElem] =>
          Some(()).asInstanceOf[Option[Unit forSome {type A}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Unit forSome {type A}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object apply {
      def unapply(d: Def[_]): Option[Rep[Array[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(arr, _*)) if method.getName == "apply" && receiver.elem.isInstanceOf[MyArrayCompanionElem] =>
          Some(arr).asInstanceOf[Option[Rep[Array[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[Array[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object fromArray {
      def unapply(d: Def[_]): Option[Rep[Array[T]] forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(arr, _*)) if method.getName == "fromArray" && receiver.elem.isInstanceOf[MyArrayCompanionElem] =>
          Some(arr).asInstanceOf[Option[Rep[Array[T]] forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[Array[T]] forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object replicate {
      def unapply(d: Def[_]): Option[(Rep[Int], Rep[T]) forSome {type T}] = d match {
        case MethodCall(receiver, method, Seq(len, v, _*)) if method.getName == "replicate" && receiver.elem.isInstanceOf[MyArrayCompanionElem] =>
          Some((len, v)).asInstanceOf[Option[(Rep[Int], Rep[T]) forSome {type T}]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Int], Rep[T]) forSome {type T}] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }
}
