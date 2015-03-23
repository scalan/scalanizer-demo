package scalan.paradise

import scalan._

trait ParadiseDsl extends ScalanDsl
with SegmentsDsl

trait ParadiseDslSeq extends ParadiseDsl with ScalanSeq
with SegmentsDslSeq

trait ParadiseDslExp extends ParadiseDsl with ScalanExp
with SegmentsDslExp

