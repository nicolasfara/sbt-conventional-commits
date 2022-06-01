import scala.io.Source
import scala.util.Using

ThisBuild / conventionalCommits / successMessage := Some("success")
ThisBuild / conventionalCommits / failureMessage := None

lazy val root = project
  .in(file("."))
  .settings(
    TaskKey[Unit]("check") := {
      Using(Source.fromFile(".git/hooks/commit-msg")) { file =>
        val lines = file.getLines()
        val foundEchos = lines.filter(_ contains "echo")
        if (foundEchos.length > 1)
          sys.error(s"Found ${foundEchos.length} echos instead of 1.\nFile contents:\n${file.mkString}")
        val foundEcho = foundEchos.contains("echo -e 'success'")
        if (!foundEcho) sys.error(s"The expected success echo was not found.\nFile contents:\n${file.mkString}")
        ()
      }
    },
  )
