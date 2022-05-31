package it.nicolasfarabegoli

import java.io.{ File => JFile }

import scala.io.Source
import scala.util.Using

import better.files.Dsl._
import better.files._

object ConventionalCommits {

  def apply(baseDir: JFile, types: Seq[String], scopes: Seq[String]): Unit =
    conventionalCommitsTask(baseDir, types, scopes)

  private def conventionalCommitsTask(baseDir: JFile, types: Seq[String], scopes: Seq[String]): Unit = {
    getGitRoot(baseDir.toScala).map(_ / "hooks" / "commit-msg") match {
      case Some(path) => writeScript(path, types, scopes)
      case None => throw new IllegalStateException("Unable to find git root")
    }
  }

  private def writeScript(file: File, types: Seq[String], scopes: Seq[String]): Unit = {
    val fileContent =
      Using(Source.fromInputStream(getClass.getResourceAsStream("/commit-msg.sh"))) { _.mkString }.get
    val typesRegex = types.mkString("|")
    val scopesRegex = if (scopes.isEmpty) "[a-z \\-]+" else scopes.mkString("|")
    val processedContent = fileContent.format(typesRegex, scopesRegex)
    file < processedContent
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
