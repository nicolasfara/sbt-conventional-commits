package it.nicolasfarabegoli

import sbt.Keys.{ baseDirectory, streams }
import sbt.{ AutoPlugin, Setting }

object ConventionalCommitsPlugin extends AutoPlugin {

  override def trigger = allRequirements

  object autoImport extends ConventionalCommitsKeys
  import autoImport._

  val defaultSuccessMessage: String = "\\e[32mCommit message meets Conventional Commit standards...\\e[0m"

  val defaultFailureMessage: String =
    """
      |\e[31mThe commit message does not meet the Conventional Commit standard\e[0m
      |An example of a valid message is: 
      |  feat(login): add the 'remember me' button
      |More details at: https://www.conventionalcommits.org/en/v1.0.0/#summary
      |""".stripMargin.strip

  override lazy val buildSettings: Seq[Setting[_]] = Seq(
    conventionalCommits / warningIfNoGitRoot := true,
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
    conventionalCommits / successMessage := Some(defaultSuccessMessage),
    conventionalCommits / failureMessage := Some(defaultFailureMessage),
    conventionalCommits := ConventionalCommits(
      streams.value.log,
      baseDirectory.value,
      (conventionalCommits / warningIfNoGitRoot).value,
      (conventionalCommits / types).value,
      (conventionalCommits / scopes).value,
      (conventionalCommits / successMessage).value,
      (conventionalCommits / failureMessage).value,
    ),
  )
}
