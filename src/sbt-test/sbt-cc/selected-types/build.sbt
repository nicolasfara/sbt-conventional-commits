import scala.io.Source
import scala.util.Using

ThisBuild / conventionalCommits / types := Seq("fix", "feat")

lazy val root = project
  .in(file("."))
  .settings(
    TaskKey[Unit]("check") := {
      val expectedRegex = "^(fix|feat)(\\([a-z \\-]+\\))?!?: .+$"
      Using(Source.fromFile(".git/hooks/commit-msg")) { file =>
        val lines = file.getLines()
        val foundRegex = lines.exists(_ contains expectedRegex)
        if (!foundRegex) sys.error(s"The expected regex was not found.\nFile contents:\n${file.mkString}")
        ()
      }
    },
  )
