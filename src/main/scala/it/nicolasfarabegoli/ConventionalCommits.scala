package it.nicolasfarabegoli

import java.io.{ File => JFile }

import better.files.Dsl._
import better.files._

object ConventionalCommits {

  def apply(
      baseDir: JFile,
      types: Seq[String],
      scopes: Seq[String],
      successMessage: Option[String],
      failureMessage: Option[String],
  ): Unit =
    conventionalCommitsTask(baseDir, types, scopes, successMessage, failureMessage)

  private def conventionalCommitsTask(
      baseDir: JFile,
      types: Seq[String],
      scopes: Seq[String],
      successMessage: Option[String],
      failureMessage: Option[String],
  ): Unit = {
    getGitRoot(baseDir.toScala).map(_ / "hooks" / "commit-msg") match {
      case Some(path) => writeScript(path, types, scopes, successMessage, failureMessage)
      case None => throw new IllegalStateException("Unable to find git root")
    }
  }

  private def writeScript(
      file: File,
      types: Seq[String],
      scopes: Seq[String],
      successMessage: Option[String],
      failureMessage: Option[String],
  ): Unit = {
    file < CommitMessageScript(types, scopes, successMessage, failureMessage)
    file.toJava.setExecutable(true)
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
