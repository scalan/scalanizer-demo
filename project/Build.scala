import sbt._
import sbt.Keys._

object ScalanStartRootBuild extends Build {
  val commonDeps = libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "2.2.1" % "test",
    "org.scalacheck" %% "scalacheck" % "1.11.5" % "test")

  val testSettings = inConfig(ItTest)(Defaults.testTasks) ++ Seq(
    testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-s"), Tests.Filter(unitFilter)),
    testOptions in ItTest := Seq(Tests.Argument(TestFrameworks.JUnit, "-v", "-a", "-s", "-q"), Tests.Filter(itFilter)),
    parallelExecution in Test := false,
    parallelExecution in ItTest := false,
    publishArtifact in Test := true,
    publishArtifact in(Test, packageDoc) := false
  )

  val buildSettings = Seq(
    organization := "com.huawei.scalan",
    scalaVersion := "2.10.4",
    scalacOptions ++= Seq(
      "-unchecked", "-deprecation",
      "-feature",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-language:existentials",
      "-language:postfixOps")
  )

  override lazy val settings = super.settings ++ buildSettings
  lazy val commonSettings = buildSettings ++ testSettings

  implicit class ProjectExt(p: Project) {
    def allConfigDependency = p % "compile->compile;test->test"
    def addTestConfigsAndCommonSettings =
      p.configs(ItTest).settings(commonSettings: _*)
  }
  def liteDependency(name: String) = "com.huawei.scalan" %% name % "0.2.7-SNAPSHOT"

  lazy val metaDeps = liteDependency("meta")
  lazy val scalanParadiseMeta = Project(
    id = "scalan-paradise-meta",
    base = file("meta")
  ).addTestConfigsAndCommonSettings.settings(libraryDependencies ++= Seq(metaDeps))

  lazy val core = liteDependency("core")
  lazy val common = liteDependency("common")
  lazy val community = liteDependency("community-edition")

  lazy val scalanParadise = Project(
    id = "scalan-paradise",
    base = file(".")
  ).addTestConfigsAndCommonSettings.settings(libraryDependencies ++= Seq(
    core,
    core % "test" classifier "tests",
    common,
    common % "test" classifier "tests",
    community
  ))

  def itFilter(name: String): Boolean = name endsWith "ItTests"
  def unitFilter(name: String): Boolean = !itFilter(name)
  lazy val ItTest = config("it").extend(Test)
  publishArtifact in Test := true
  publishArtifact in (Test, packageDoc) := false
}
