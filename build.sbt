name := "todozio"

version := "0.1"

scalaVersion := "2.13.3"

val zioVersion = "1.0.0-RC21-2"

val akkaHttpVersion = "10.1.12"

val akkaVersion = "2.6.6"

val slickVersion = "3.3.2"

val circeVersion = "0.12.1"

val enumeratumVersion = "1.6.1"

val zioLoggingVersion = "0.3.1"

val zioConfigVersion = "1.0.0-RC20"

libraryDependencies ++= Seq(
  "dev.zio"                    %% "zio"                   % zioVersion,
  "dev.zio"                    %% "zio-streams"           % zioVersion,
  "dev.zio"                    %% "zio-config"            % zioConfigVersion,
  "dev.zio"                    %% "zio-config-magnolia"   % zioConfigVersion,
  "dev.zio"                    %% "zio-config-typesafe"   % zioConfigVersion,
  "dev.zio"                    %% "zio-logging"           % zioLoggingVersion,
  "dev.zio"                    %% "zio-logging-slf4j"     % zioLoggingVersion,
  "com.typesafe.akka"          %% "akka-http"             % akkaHttpVersion,
  "com.typesafe.akka"          %% "akka-stream"           % akkaVersion,
  "com.typesafe.slick"         %% "slick"                 % slickVersion,
  "com.typesafe.slick"         %% "slick-hikaricp"        % slickVersion,
  "com.typesafe.scala-logging" %% "scala-logging"         % "3.9.2",
  "org.postgresql"              % "postgresql"            % "42.2.14",
  "io.scalac"                  %% "zio-akka-http-interop" % "0.1.0",
  "io.scalac"                  %% "zio-slick-interop"     % "0.1.0",
  "io.circe"                   %% "circe-core"            % circeVersion,
  "io.circe"                   %% "circe-generic"         % circeVersion,
  "io.circe"                   %% "circe-parser"          % circeVersion,
  "io.circe"                   %% "circe-generic-extras"  % circeVersion,
  "de.heikoseeberger"          %% "akka-http-circe"       % "1.33.0",
  "com.beachape"               %% "enumeratum"            % enumeratumVersion,
  "com.beachape"               %% "enumeratum-circe"      % enumeratumVersion,
  "ch.qos.logback"              % "logback-classic"       % "1.2.3",
)

libraryDependencies ++= Seq(
  "dev.zio"           %% "zio-test-sbt"      % zioVersion      % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
  "com.typesafe.akka" %% "akka-testkit"      % akkaVersion     % Test,
  "com.h2database"     % "h2"                % "1.4.200"       % Test,
)
