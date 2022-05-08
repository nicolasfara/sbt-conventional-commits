lazy val root = project
  .in(file("."))
  .settings(
    name := "sbt-conventional-commits",
    organization := "it.nicolasfarabegoli",
    sbtPlugin := true,
  )
