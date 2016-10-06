package scalan

import scala.annotation.StaticAnnotation
import scalan.compilation.KernelType

class HotSpot(kernel: KernelType = KernelType.Scala) extends StaticAnnotation
