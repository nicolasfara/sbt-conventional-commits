ThisBuild / scalafixDependencies ++= Seq(
  "com.github.liancheng" %% "organize-imports" % "0.6.0",
)

lazy val root = project
  .in(file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-conventional-commits",
    organization := "it.nicolasfarabegoli",
    version := "0.1.0",
    sbtPlugin := true,
    scriptedLaunchOpts := {
      scriptedLaunchOpts.value ++
        Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false,
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    scalacOptions += "-Ywarn-unused-import",
  )
