import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version: String by extra
val gradle_kotlin_dsl_version: String by extra

apply(from = "publish.gradle")

buildscript {
  val kotlin_version: String by extra
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath(kotlin("gradle-plugin", kotlin_version))
  }
}

group = "net.minecraftforge.gradle"
version = "1.0.0"

plugins {
  `java-library`
}

apply(plugin = "kotlin")

repositories {
  mavenCentral()
  mavenLocal()
  maven("https://repo.gradle.org/gradle/libs-releases-local/")
}

dependencies {
  implementation(gradleApi())
  implementation(kotlin("stdlib-jdk8", kotlin_version))

  api("net.minecraftforge.gradle", "forgegradle", "3.0.0+")
  implementation("org.gradle", "gradle-kotlin-dsl", gradle_kotlin_dsl_version)
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}