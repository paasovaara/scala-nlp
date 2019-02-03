lazy val commonSettings = Seq(
  scalaVersion := "2.12.7",
  organization := "paasovaara",
  version := "1.0-SNAPSHOT",
  name := """scala-nlp"""
)

lazy val compilerSettings = Seq(
  javacOptions ++= Seq(
    "-source", "1.8",
    "-target", "1.8"
  ),
  scalacOptions := Seq(
    "-target:jvm-1.8",
    "-encoding", "UTF-8",
    "-deprecation",
    "-feature",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint",
    // https://github.com/scala/scala/blob/2.12.x/src/compiler/scala/tools/nsc/settings/Warnings.scala
    "-Ywarn-unused:-imports",  // Warn when local and private vals, vars, defs, and types are are unused. Let's exclude imports
    "-Ywarn-value-discard",    // Warn when non-Unit expression results are unused.
    "-Ywarn-dead-code"         // Warn when dead code is identified.
  )
)

lazy val dependencies = Seq(
  libraryDependencies += ws,
  libraryDependencies += guice,
  libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.0",
  libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  libraryDependencies += "com.github.rholder" % "snowball-stemmer" % "1.3.0.581.1",
)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala).settings(commonSettings ++ compilerSettings ++ dependencies)
