package scalan

import scala.annotation.StaticAnnotation

object KernelTypes extends Enumeration {
  type KernelType = Value
  val ScalaKernel, CppKernel = Value
}

class HotSpot(kernel: KernelTypes.KernelType = KernelTypes.ScalaKernel) extends StaticAnnotation
