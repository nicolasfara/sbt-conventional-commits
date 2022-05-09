ThisBuild / scalafixDependencies ++= Seq(
  "com.github.liancheng" %% "organize-imports" % "0.6.0",
)

lazy val root = project
  .in(file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-conventional-commits",
    organization := "it.nicolasfarabegoli",
    sbtPlugin := true,
    scalaVersion := "2.12.14",
  )
