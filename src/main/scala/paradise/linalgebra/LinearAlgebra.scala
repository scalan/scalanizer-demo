package paradise.linalgebra

import paradise._
import paradise.collections._

trait LinearAlgebra extends Collections with Monoids
with Vectors
with Matrices

import scalan._
trait LinearAlgebraDsl extends Scalan
with LinearAlgebra

trait LinearAlgebraDslSeq extends ScalanSeq
with LinearAlgebraDsl

trait LinearAlgebraDslExp extends ScalanExp
with LinearAlgebraDsl