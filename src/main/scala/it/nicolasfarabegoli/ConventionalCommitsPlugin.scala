package it.nicolasfarabegoli

import sbt.{AutoPlugin, Setting, URL}
import sbt.Keys.baseDirectory

import scala.io.Source
import scala.reflect.io.{File, Path}
import scala.util.{Try, Using}
import java.io.{FileNotFoundException, File as JFile}

object ConventionalCommitsPlugin extends AutoPlugin {

  override def trigger = allRequirements

  object autoImport extends ConventionalCommitsKeys
  import autoImport._

  override lazy val projectSettings: Seq[Setting[_]] = Seq(
    conventionalCommits := conventionalCommitsTask(baseDirectory.value, fromScript.value),
  )

  private def appendToFile(file: File, children: String*): File = {
    children.foldLeft(file)((acc, c) => (acc / Path(c)).toFile)
  }

  private def conventionalCommitsTask(baseDir: JFile, fromScript: Option[URL]): Unit = {
    getGitRoot(baseDir).map(appendToFile(_, "hooks", "commit-msg")) match {
      case Some(path) => writeScript(path, fromScript)
      case None => throw new IllegalStateException("Unable to find git root")
    }
  }

  private def writeScript(file: File, fromScript: Option[URL]): Unit = {
    val fileContent = fromScript match {
      case Some(url) => Using(Source.fromURL(url)) { _.mkString }.get
      case None => Using(Source.fromInputStream(getClass.getResourceAsStream("/commit-msg.sh"))) { _.mkString }.get
    }
    file.writeAll(fileContent)
  }

  private def getGitRoot(path: JFile): Option[File] = Option(path) match {
    case Some(currentFolder) =>
      val maybeGitRoot = currentFolder.listFiles.collectFirst {
        case file if file.getName == ".git" => file
      }
      maybeGitRoot map (File(_)) orElse getGitRoot(path.getParentFile)
    case _ => None
  }
}
