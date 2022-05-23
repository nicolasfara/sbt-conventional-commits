package it.nicolasfarabegoli

import sbt._

trait ConventionalCommitsKeys {
  val conventionalCommits = taskKey[Unit]("Task for creating a git hook for enforcing conventional commits")
}
