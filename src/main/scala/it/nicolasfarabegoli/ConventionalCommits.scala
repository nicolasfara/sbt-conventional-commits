package it.nicolasfarabegoli

import java.io.{ File => JFile }
import java.nio.file.attribute.PosixFilePermission

import scala.io.Source
import scala.util.Using

import better.files.Dsl._
import better.files._

object ConventionalCommits {
  def apply(baseDir: JFile): Unit = conventionalCommitsTask(baseDir)

  private def conventionalCommitsTask(baseDir: JFile): Unit = {
    getGitRoot(baseDir.toScala).map(_ / "hooks" / "commit-msg") match {
      case Some(path) => writeScript(path)
      case None => throw new IllegalStateException("Unable to find git root")
    }
  }

  private def writeScript(file: File): Unit = {
    val fileContent =
      Using(Source.fromInputStream(getClass.getResourceAsStream("/commit-msg.sh"))) { _.mkString }.get
    file < fileContent
    chmod_+(PosixFilePermission.OWNER_EXECUTE, file)
  }

  private def getGitRoot(path: File): Option[File] = Option(path) match {
    case Some(currentFolder) =>
      val maybeGitRoot = ls(currentFolder).collectFirst {
        case file if file.name == ".git" => file
      }
      maybeGitRoot orElse getGitRoot(path.parent)
    case _ => None
  }
}
