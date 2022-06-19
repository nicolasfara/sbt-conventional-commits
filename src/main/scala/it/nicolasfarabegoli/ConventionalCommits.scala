package it.nicolasfarabegoli

import java.io.{ File => JFile }
import better.files.Dsl._
import better.files._
import sbt.internal.util.ManagedLogger

object ConventionalCommits {

  def apply(
      logger: ManagedLogger,
      baseDir: JFile,
      warningIfNoGitRoot: Boolean,
      types: Seq[String],
      scopes: Seq[String],
      successMessage: Option[String],
      failureMessage: Option[String],
  ): Unit = {
    getGitRoot(baseDir.toScala).map(_ / "hooks" / "commit-msg") match {
      case Some(path) => writeScript(path, types, scopes, successMessage, failureMessage)
      case None => if (warningIfNoGitRoot) logger.warn("No '.git' root found. Script not generated")
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
