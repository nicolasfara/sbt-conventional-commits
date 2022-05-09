package it.nicolasfarabegoli

import sbt._

trait ConventionalCommitsKeys {
  val fromScript = settingKey[Option[URL]]("Specify a remote URL for custom script")
  val conventionalCommits = taskKey[Unit]("Task for creating a git hook for enforcing conventional commits")
}
