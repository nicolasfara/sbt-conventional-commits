package it.nicolasfarabegoli

import sbt.Keys.baseDirectory
import sbt.{ AutoPlugin, Setting }

object ConventionalCommitsPlugin extends AutoPlugin {

  override def trigger = allRequirements

  object autoImport extends ConventionalCommitsKeys
  import autoImport._

  override lazy val buildSettings: Seq[Setting[_]] = Seq(
    conventionalCommits / types := Seq(
      "build",
      "chore",
      "ci",
      "docs",
      "feat",
      "fix",
      "perf",
      "refactor",
      "revert",
      "style",
      "test",
    ),
    conventionalCommits / scopes := Seq(),
    conventionalCommits := ConventionalCommits(
      baseDirectory.value,
      (conventionalCommits / types).value,
      (conventionalCommits / scopes).value,
    ),
  )
}
