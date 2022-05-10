sys.props.get("plugin.version") match {
  case Some(v) => addSbtPlugin("it.nicolasfarabegoli" % "sbt-conventional-commits" % v)
  case _ => sys.error("No version specified")
}
