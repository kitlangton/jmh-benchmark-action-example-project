ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.11"

lazy val root = project
  .in(file("."))
  .aggregate(core, benchmarks)
  .settings(
    name           := "zio-http-open-api",
    publish / skip := true
  )

lazy val core = (project in file("core"))
  .settings(
    name := "jmh-benchmark-action-example-project",
    scalacOptions ++= Seq("-deprecation", "-feature"),
    resolvers ++= Resolver.sonatypeOssRepos("snapshots"),
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio"           % "2.0.15",
      "dev.zio" %% "zio-test"      % "2.0.15" % Test,
      "dev.zio" %% "zio-test-sbt"  % "2.0.15" % Test,
      "dev.zio" %% "zio-json"      % "0.6.0",
      "dev.zio" %% "zio-json-yaml" % "0.6.0",
      "dev.zio" %% "zio-http"      % "3.0.0-RC2+41-1b09b785-SNAPSHOT"
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )

val runBenchmarks = TaskKey[Unit]("runBenchmarks")

lazy val benchmarks = (project in file("benchmarks"))
  .enablePlugins(JmhPlugin)
  .settings(
    name := "jmh-benchmark-action-example-benchmarks",
    scalacOptions ++= Seq("-deprecation", "-feature"),
    resolvers ++= Resolver.sonatypeOssRepos("snapshots"),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    runBenchmarks := {
      (Jmh / run).toTask(" -i 3 -wi 3 -f1 -t1 -rf json -rff output.json .*").value
    }
  )
  .dependsOn(core)

Global / onChangedBuildSource := ReloadOnSourceChanges
