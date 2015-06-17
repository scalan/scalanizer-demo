import sbt._
import sbt.Keys._

object ScalanParadiseRootBuild extends Build {
  val buildSettings = Seq(
    organization := "com.huawei.scalan",
    scalaVersion := "2.11.2",
    scalacOptions ++= Seq(
      "-unchecked", "-deprecation",
      "-feature",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-language:existentials",
      "-language:postfixOps"
    ),
    addCompilerPlugin("com.huawei" %% "scalanizer" % "0.0.1" classifier "assembly")
  )

  lazy val scalanizerSample = Project(
    id = "scalanizer-sample",
    base = file(".")
  ).settings(
    buildSettings,
    libraryDependencies ++= Seq(
      "com.huawei.scalan" %% "scalan-lms-backend" % "0.2.9-SNAPSHOT",
      "org.scalatest" %% "scalatest" % "2.2.1" % "test"
    ),
    scalaOrganization := "org.scala-lang.virtualized",
    scalaVersion := "2.11.2")
}
