package it.nicolasfarabegoli

import sbt._

import scala.annotation.tailrec

object ConventionalCommitsPlugin extends AutoPlugin {

  override def trigger = allRequirements

  object autoImport extends ConventionalCommitsKeys
  import autoImport._

  override lazy val projectSettings: Seq[Setting[_]] = Seq(
    conventionalCommits := writeScriptToFile(),
  )

  private def writeScriptToFile(): Unit = {
    ???
  }

  @tailrec
  private def getGitRoot(path: File): Option[File] = Option(path) match {
    case Some(currentFolder) =>
      val maybeGitRoot = currentFolder.listFiles().collectFirst {
        case currentFile if currentFile.name == ".git" => currentFile
      }
      maybeGitRoot match {
        case g @ Some(_) => g
        case _ => getGitRoot(path.getParentFile)
      }
    case _ => None
  }
}
