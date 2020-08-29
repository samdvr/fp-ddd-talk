name := "fp-ddd"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  catsEffect
)

lazy val main = project.in(file("."))
  .settings(moduleName := "root")
  .aggregate(sales, persistence, messaging, serdes)

lazy val root = project.dependsOn(sales)
lazy val sales = project.dependsOn(persistence, messaging)
lazy val persistence = project.settings(
  libraryDependencies ++= Seq(
    "net.debasishg" %% "redisclient" % "3.30",
    catsEffect
  )
)

lazy val messaging = project.settings(
  libraryDependencies ++= Seq(
    "org.apache.kafka" % "kafka-clients" % "2.6.0"
  )
).dependsOn(serdes)

lazy val serdes = project.settings(
  libraryDependencies ++= Seq(
    "io.circe" %% "circe-core" % "0.12.3",
    "io.circe" %% "circe-generic" % "0.12.3",
    catsEffect
  )
)



lazy val catsEffect = "org.typelevel" %% "cats-effect" % "2.1.4"