import scala.io.Source
import scala.util.Using

ThisBuild / conventionalCommits / successMessage := None
ThisBuild / conventionalCommits / failureMessage := Some("failure")

lazy val root = project
  .in(file("."))
  .settings(
    TaskKey[Unit]("check") := {
      Using(Source.fromFile(".git/hooks/commit-msg")) { file =>
        val lines = file.getLines()
        val foundEchos = lines.filter(_ contains "echo")
        if (foundEchos.length > 1)
          sys.error(s"Found ${foundEchos.length} echos instead of 1.\nFile contents:\n${file.mkString}")
        val foundEcho = foundEchos.contains("echo -e 'failure'")
        if (!foundEcho) sys.error(s"The expected failure echo was not found.\nFile contents:\n${file.mkString}")
        ()
      }
    },
  )
