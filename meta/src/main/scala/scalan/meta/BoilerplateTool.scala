package scalan.meta

object ScalanParadiseBoilerplateTool extends BoilerplateTool {
  val paradiseTypeSynonims = Map[String, String](
    "RSeg" -> "Segment"
    ,"RKind" -> "Kind"
  )
  lazy val paradiseConfig = CodegenConfig(
    name = "Scalan Paradise",
    srcPath = "src/main/scala",
    entityFiles = List(
      "Segments.scala"
      ,"Kinds.scala"
    ),
    baseContextTrait = "ScalanDsl",
    seqContextTrait = "ScalanSeq",
    stagedContextTrait = "ScalanExp",
    extraImports = List(
      "scala.reflect.runtime.universe._",
      "scala.reflect._",
      "scalan.common.Default"),
    paradiseTypeSynonims
  )

  override def getConfigs(args: Array[String]) = Seq(paradiseConfig)
  override def main(args: Array[String]) = super.main(args)
}
