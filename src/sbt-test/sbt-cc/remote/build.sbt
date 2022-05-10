lazy val root = project
  .in(file("."))
  .settings(
    fromScript := Some(
      url(
        "https://gist.githubusercontent.com/nicolasfara/731bfe58dc5f3bc40387782d9091b519/raw/18398c29144317b9657c131f3d72ff4d24e0122c/commit-msg",
      ),
    ),
  )
