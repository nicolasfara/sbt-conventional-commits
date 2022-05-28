# sbt-conventional-commit
[![Build test and deploy](https://github.com/nicolasfara/sbt-conventional-commits/actions/workflows/ci.yml/badge.svg)](https://github.com/nicolasfara/sbt-conventional-commits/actions/workflows/ci.yml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/it.nicolasfarabegoli/sbt-conventional-commits/badge.svg)](https://maven-badges.herokuapp.com/maven-central/it.nicolasfarabegoli/sbt-conventional-commits/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

The purpose of this plugin is to enforce the use of [conventional commit](https://www.conventionalcommits.org/en/v1.0.0/) in an sbt-based Scala project.

## Setup
In the `project/plugins.sbt` file add the plugin:
```scala 
addSbtPlugin("it.nicolasfarabegoli" % "sbt-conventional-commits" % <version>)
```
In order to run the task as soon as the project is loaded you should add configure the `build.sbt` as follows:

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

The task creates a git hooks file that enforces that each commit is compliant with the conventional commit convention:
```shell
> git commit -m "message"
Commit message meets the commit message does not meet the Conventional Commit standard
An example of a valid message is:
  feat(login): add the 'remember me' button
More details at: https://www.conventionalcommits.org/en/v1.0.0/#summary

> git commit -m "feat: we love conventional commit!"
Conventional Commit standards...
```
