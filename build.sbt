name := """play-scala-starter-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.2"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0-M3" % Test
//libraryDependencies += "com.h2database" % "h2" % "1.4.194"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "3.0.0-M3",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0-M3",
  "org.postgresql" % "postgresql" % "9.4-1200-jdbc41",
  "com.typesafe.play" %% "play-jdbc-api"% "2.6.0-RC2",
  "com.typesafe.play"  %% "play-jdbc-evolutions" % "2.6.0-RC2"
)

// https://mvnrepository.com/artifact/org.postgresql/postgresql


