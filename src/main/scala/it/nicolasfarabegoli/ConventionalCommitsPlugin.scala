package it.nicolasfarabegoli

import sbt.Keys.baseDirectory
import sbt.{ AutoPlugin, Setting }

object ConventionalCommitsPlugin extends AutoPlugin {

  override def trigger = allRequirements

  object autoImport extends ConventionalCommitsKeys
  import autoImport._

  override lazy val buildSettings: Seq[Setting[_]] = Seq(
    conventionalCommits := ConventionalCommits(baseDirectory.value),
  )
}
