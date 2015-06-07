package scalan.meta

object ScalanParadiseBoilerplateTool extends BoilerplateTool {
  val paradiseTypeSynonims = Map[String, String](
    "RSeg" -> "Segment"
    ,"RKind" -> "Kind"
    ,"RCoproduct" -> "Coproduct"
    ,"RMonad" -> "Monad"
    ,"RFree" -> "Free"
  )
  lazy val paradiseConfig = CodegenConfig(
    name = "Scalan Paradise",
    srcPath = "src/main/scala",
    entityFiles = List(
      "Segments.scala"
      ,"Kinds.scala"
      ,"Coproducts.scala"
      ,"Monads.scala"
      ,"Frees.scala"
    ),
    paradiseTypeSynonims,
    baseContextTrait = "ScalanDsl",
    seqContextTrait = "ScalanSeq",
    stagedContextTrait = "ScalanExp",
    extraImports = List(
      "scala.reflect.runtime.universe._",
      "scala.reflect._",
      "scalan.common.Default")
  )

  override def getConfigs(args: Array[String]) = Seq(paradiseConfig)
  override def main(args: Array[String]) = super.main(args)
}
