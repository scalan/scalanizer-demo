import sbt._
import sbt.Keys._

object ScalanParadiseRootBuild extends Build {
  val paradiseVersion = "2.1.0-M5"

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
    scalaVersion := "2.11.6",
    scalacOptions ++= Seq(
      "-unchecked", "-deprecation",
      "-feature",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-language:existentials",
      "-language:postfixOps",
      s"-Xplugin:/Users/slesarenko/Projects/scalan/scalanizer/target/scala-2.11/scalanizer_2.11.6-0.0.1-fat.jar"
//      s"-Xplugin:${Path.userHome.absolutePath}/.ivy2/local/com.huawei/scalan-plugin_2.11.6/0.0.1/jars/scalan-plugin_2.11.6.jar"
    ),
    crossScalaVersions := Seq("2.10.2", "2.10.3", "2.10.4", "2.10.5",
                              "2.11.0", "2.11.1", "2.11.2", "2.11.3", "2.11.4", "2.11.5", "2.11.6"
                             ),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    resolvers += Resolver.sonatypeRepo("releases")
    //addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)
  )

  override lazy val settings = super.settings ++ buildSettings
  lazy val commonSettings = buildSettings ++ testSettings

  implicit class ProjectExt(p: Project) {
    def allConfigDependency = p % "compile->compile;test->test"
    def addTestConfigsAndCommonSettings =
      p.configs(ItTest).settings(commonSettings: _*)
  }
  def scalanDependency(name: String) = "com.huawei.scalan" %% name % "0.2.9-SNAPSHOT"

  lazy val metaDeps = scalanDependency("meta")

//  lazy val scalanParadiseMeta = Project(
//    id = "scalan-paradise-meta",
//    base = file("meta")
//  ).addTestConfigsAndCommonSettings.settings(libraryDependencies ++= Seq(metaDeps))

  lazy val core = scalanDependency("core")
  lazy val common = scalanDependency("common")
  lazy val community = scalanDependency("community-edition")
  lazy val lmsbackend = scalanDependency("lms-backend")
  lazy val scalanizerSample = Project(
    id = "scalanizer-sample",
    base = file(".")
  ).addTestConfigsAndCommonSettings.settings(libraryDependencies ++= Seq(
    core,
    core % "test" classifier "tests",
    common,
    common % "test" classifier "tests",
    community,
    "org.scalatest" %% "scalatest" % "2.2.1" % "test",
    "org.scalacheck" %% "scalacheck" % "1.11.5" % "test",
    lmsbackend
  ))

  def itFilter(name: String): Boolean = name endsWith "ItTests"
  def unitFilter(name: String): Boolean = !itFilter(name)
  lazy val ItTest = config("it").extend(Test)
  publishArtifact in Test := true
  publishArtifact in (Test, packageDoc) := false
}
