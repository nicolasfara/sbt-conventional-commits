import scala.io.Source
import scala.util.Using

ThisBuild / conventionalCommits / successMessage := None
ThisBuild / conventionalCommits / failureMessage := None

lazy val root = project
  .in(file("."))
  .settings(
    TaskKey[Unit]("check") := {
      Using(Source.fromFile(".git/hooks/commit-msg")) { file =>
        val lines = file.getLines()
        val foundEcho = lines.exists(_ contains "echo")
        if (foundEcho) sys.error(s"An unexpected echo was found.\nFile contents:\n${file.mkString}")
        ()
      }
    },
  )
