name := "todozio"

version := "0.1"

scalaVersion := "2.13.3"

val zioVersion = "1.0.0-RC21-2"

val akkaHttpVersion = "10.1.12"

val circeVersion = "0.12.1"

val enumeratumVersion = "1.6.1"

libraryDependencies ++= Seq(
  "dev.zio"                    %% "zio"                  % zioVersion,
  "dev.zio"                    %% "zio-streams"          % zioVersion,
  "com.typesafe.akka"          %% "akka-http"            % akkaHttpVersion,
  "com.typesafe.akka"          %% "akka-stream"          % "2.6.6",
  "com.typesafe.slick"         %% "slick"                % "3.3.2",
  "com.typesafe.slick"         %% "slick-hikaricp"       % "3.3.2",
  "com.typesafe.scala-logging" %% "scala-logging"        % "3.9.2",
  "org.postgresql"              % "postgresql"           % "42.2.14",
  "io.circe"                   %% "circe-core"           % circeVersion,
  "io.circe"                   %% "circe-generic"        % circeVersion,
  "io.circe"                   %% "circe-parser"         % circeVersion,
  "io.circe"                   %% "circe-generic-extras" % circeVersion,
  "com.beachape"               %% "enumeratum"           % enumeratumVersion,
  "com.beachape"               %% "enumeratum-circe"     % enumeratumVersion,
  "de.heikoseeberger"          %% "akka-http-circe"      % "1.33.0",
  "ch.qos.logback"              % "logback-classic"      % "1.2.3",
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
  "com.typesafe.akka" %% "akka-testkit"      % "2.6.6"         % Test,
  "org.scalatest"     %% "scalatest"         % "3.2.0"         % Test,
  "com.h2database"     % "h2"                % "1.4.200"       % Test,
)
