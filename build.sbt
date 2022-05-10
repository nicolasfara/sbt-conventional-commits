ThisBuild / scalafixDependencies ++= Seq(
  "com.github.liancheng" %% "organize-imports" % "0.6.0",
)

ThisBuild / homepage := Some(url("https://github.com/nicolasfara/sbt-conventional-commits"))
ThisBuild / organization := "it.nicolasfarabegoli"
ThisBuild / licenses := List("MIT" -> url("https://opensource.org/licenses/MIT"))

ThisBuild / developers := List(
  Developer(
    "nicolasfara",
    "Nicolas Farabegoli",
    "nicolas.farabegoli@gmail.com",
    url("https://github.com/nicolasfara"),
  ),
)

lazy val root = project
  .in(file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-conventional-commits",
    version := "0.1.0",
    sbtPlugin := true,
    sonatypeCredentialHost := "s01.oss.sonatype.org",
    sonatypeRepository := "https://s01.oss.sonatype.org/service/local",
    scriptedLaunchOpts := {
      scriptedLaunchOpts.value ++
        Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false,
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    scalacOptions += "-Ywarn-unused-import",
  )
