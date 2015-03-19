package scalan.meta

object ScalanParadiseBoilerplateTool extends BoilerplateTool {
  val paradiseTypeSynonims = Map(
    // declare your type synonims for User Defined types here (see type PA[A] = Rep[PArray[A]])
    "" -> ""
  )
  lazy val paradiseConfig = CodegenConfig(
    name = "Scalan Paradise",
    srcPath = "src/main/scala",
    entityFiles = List(
      ""
    ),
    baseContextTrait = "ScalanCommunityDsl",
    seqContextTrait = "ScalanCommunityDslSeq",
    stagedContextTrait = "ScalanCommunityDslExp",
    extraImports = List(
      "scala.reflect.runtime.universe._",
      "scalan.common.Default"),
    paradiseTypeSynonims
  )

  override def getConfigs(args: Array[String]) = Seq(paradiseConfig)
  override def main(args: Array[String]) = super.main(args)
}
