package it.nicolasfarabegoli

object CommitMessageScript {

  def apply(
      types: Seq[String],
      scopes: Seq[String],
      successMessage: Option[String],
      failureMessage: Option[String],
  ): String = {
    val typesRegex = types.mkString("|")
    val scopesRegex = if (scopes.isEmpty) "[a-z \\-]+" else scopes.mkString("|")
    val successMessageEcho = wrapInEcho(successMessage)
    val failureMessageEcho = wrapInEcho(failureMessage)
    s"""
      |#!/usr/bin/env bash
      |
      |# Create a regex for a conventional commit.
      |conventional_commit_regex="^($typesRegex)(\\($scopesRegex\\))?!?: .+$$"
      |
      |# Get the commit message (the parameter we're given is just the path to the
      |# temporary file which holds the message).
      |commit_message=$$(cat "$$1")
      |
      |# Check the message, if we match, all good baby.
      |if [[ "$$commit_message" =~ $$conventional_commit_regex ]]; then
      |   $successMessageEcho
      |   exit 0
      |fi
      |
      |# Uh-oh, this is not a conventional commit, show an example and link to the spec.
      |$failureMessageEcho
      |exit 1
      |""".stripMargin
  }

  private def wrapInEcho(str: Option[String]): String = str map { s => s"echo -e '$s'" } getOrElse ""
}
