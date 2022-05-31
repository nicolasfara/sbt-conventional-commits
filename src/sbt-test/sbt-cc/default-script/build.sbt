import scala.io.Source
import scala.util.Using

lazy val root = project
  .in(file("."))
  .settings(
    TaskKey[Unit]("check") := {
      val expectedRegex = "^(build|chore|ci|docs|feat|fix|perf|refactor|revert|style|test)(\\([a-z \\-]+\\))?!?: .+$"
      Using(Source.fromFile(".git/hooks/commit-msg")) { file =>
        val lines = file.getLines()
        val foundRegex = lines.exists(_ contains expectedRegex)
        if (!foundRegex) sys.error(s"The expected regex was not found.\nFile contents:\n${file.mkString}")
        ()
      }
    },
  )
