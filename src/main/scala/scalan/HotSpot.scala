package scalan

import scala.annotation.StaticAnnotation
import scalan.compilation.KernelTypes

class HotSpot(kernel: KernelTypes.KernelType = KernelTypes.ScalaKernel) extends StaticAnnotation
