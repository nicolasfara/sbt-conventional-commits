package it.nicolasfarabegoli

import sbt._

trait ConventionalCommitsKeys {
  val conventionalCommits = taskKey[Unit]("Task for creating a git hook for enforcing conventional commits")
  val warningIfNoGitRoot = settingKey[Boolean]("Raise a warning if no git root is found")
  val scopes = settingKey[Seq[String]]("A list of valid scopes (defaults to any)")
  val types = settingKey[Seq[String]]("A list of valid commit types")
  val successMessage = settingKey[Option[String]]("The message (if any) to show when a commit is valid")
  val failureMessage = settingKey[Option[String]]("The message (if any) to show when a commit is not valid")
}
