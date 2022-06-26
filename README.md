# sbt-conventional-commit
[![Build test and deploy](https://github.com/nicolasfara/sbt-conventional-commits/actions/workflows/ci.yml/badge.svg)](https://github.com/nicolasfara/sbt-conventional-commits/actions/workflows/ci.yml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/it.nicolasfarabegoli/sbt-conventional-commits/badge.svg)](https://maven-badges.herokuapp.com/maven-central/it.nicolasfarabegoli/sbt-conventional-commits/)
[![semantic-release: conventional-commits](https://img.shields.io/badge/semantic--release-conventional_commits-e10098?logo=semantic-release)](https://github.com/semantic-release/semantic-release)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

The purpose of this plugin is to enforce the use of [Conventional Commit](https://www.conventionalcommits.org/en/v1.0.0/) in an sbt-based Scala project.

## Setup
In the `project/plugins.sbt` file add the plugin:
```scala 
addSbtPlugin("it.nicolasfarabegoli" % "sbt-conventional-commits" % <version>)
```
In order to run the task as soon as the project is loaded you should configure the `build.sbt` as follows:

```scala
lazy val startupTransition: State => State = "conventionalCommits" :: _

lazy val root = project
  .in(file("."))
  .settings(
    // Other settings...
    Global / onLoad := {
      val old = (Global / onLoad).value
      startupTransition compose old
    }
  )
```

If you want to manually run the task the command is:
```shell
sbt> conventionalCommits
```

The task creates a git hooks file that enforces that each commit is compliant with the Conventional Commit convention:
```shell
> git commit -m "message"
The commit message does not meet the Conventional Commit standard
An example of a valid message is:
  feat(login): add the 'remember me' button
More details at: https://www.conventionalcommits.org/en/v1.0.0/#summary

> git commit -m "feat: we love conventional commit!"
Conventional Commit standards...
```

You can configure the available types for your commits by setting the `ThisBuild / conventionalCommits / types` key.
The default commit types are `build`, `chore`, `ci`, `docs`, `feat`, `fix`, `perf`, `refactor`, `revert`, `style`, and `test`. 
```scala
ThisBuild / conventionalCommits / types := Seq("feat", "fix") // Allow only feat and fix commits
ThisBuild / conventionalCommits / types ++= Seq("some", "other") // Allow "some" and "other" in addition to the default types
```

In the same way, you can restrict the available scopes by setting the `ThisBuild / conventionalCommits / scopes` key.
By default, all scopes are allowed.

```scala
ThisBuild / conventionalCommits / scopes := Seq() // Allow all scopes
ThisBuild / conventionalCommits / scopes := Seq("scope1", "scope2") // Allow only scope1 and scope2
```

You can also configure the success and failure messages displayed when committing, respectively by changing the
`ThisBuild / conventionalCommits / successMessage` and `ThisBuild / conventionalCommits / failureMessage` keys.

You can freely use color codes and newlines in the provided message.

```scala
ThisBuild / conventionalCommits / successMessage = None // Won't display a success message
ThisBuild / conventionalCommits / successMessage = Some("\\e[32mCongratulations!\\e[0m") // Shows "Congratulations" in green
```

## Settings keys

| Key                  | Description                                                                                                | Default                                                                                      |
|----------------------|------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------|
| `warningIfNoGitRoot` | A **warning** is raised if no `.git` root is found walking up until the `/` from the project folder.       | `true`                                                                                       |
| `types`              | List of admitted types in the commit message.                                                              | `build`, `chore`, `ci`, `docs`, `feat`, `fix`, `perf`, `refactor`, `revert`, `style`, `test` |
| `scopes`             | List of admitted scopes in the commit message. An empty list means that all scopes are admitted            | `emptyList`                                                                                  |
| `successMessage`     | A message printed if the commit meets conventional commit. If `null` is set, no message is printed.        | "Commit message meets Conventional Commit standards..."                                      |
| `failureMessage`     | A message printed if the commit **not** meets conventional commit. If `null` is set no message is printed. | "The commit message does not meet the Conventional Commit standard"                          |
