package scalan

import scala.annotation.StaticAnnotation

object Kernels extends Enumeration {
  type Kernel = Value
  val ScalaKernel, CppKernel = Value
}

class HotSpot(kernel: Kernels.Kernel = Kernels.ScalaKernel) extends StaticAnnotation
