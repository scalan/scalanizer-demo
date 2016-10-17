import sbt._
import sbt.Keys._

object ScalanizerDemoRootBuild extends Build {
  val buildSettings = Seq(
    organization := "com.huawei.scalan",
    scalaVersion := "2.11.8",
    scalacOptions ++= Seq(
      "-unchecked", "-deprecation",
      "-feature",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-language:existentials",
      "-language:postfixOps"
//      "-Xgenerate-phase-graph"
//      , "-Xplugin:/Users/slesarenko/.ivy2/local/com.huawei.scalan/scalanizer_2.11/0.0.4-SNAPSHOT/jars/scalanizer_2.11-assembly.jar"
    )//,
  )

  lazy val scalanizerSample = Project(
    id = "scalanizer-sample",
    base = file(".")
  ).settings(
      buildSettings,
      unmanagedBase := file("/Users/slesarenko/.ivy2/local/com.huawei.scalan/scalanizer_2.11/0.0.4-SNAPSHOT/jars/"),
      libraryDependencies ++= Seq(
        "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0",
        "org.scala-lang" % "scala-compiler" % scalaVersion.value,
        "org.scala-lang" % "scala-reflect" % scalaVersion.value,
//        "com.huawei.scalan" %% "scalanizer" % "0.0.4-SNAPSHOT" ,
//        "com.huawei.scalan" %% "scalan-lms-backend-core" % "0.3.0-SNAPSHOT",
        "org.scalatest" %% "scalatest" % "2.2.1" % "test"
      ))
}
