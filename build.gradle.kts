import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version: String by extra
val gradle_kotlin_dsl_version: String by extra

buildscript {
  val kotlin_version: String by extra
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath(kotlin("gradle-plugin", kotlin_version))
  }
}

group = "net.minecraftforge"
version = "1.0.0"

plugins {
  java
  `kotlin-dsl`
}

repositories {
  mavenCentral()
  maven { url = uri("https://repo.gradle.org/gradle/libs-releases-local/") }
}

dependencies {
  compile(gradleApi())
  compile(kotlin("stdlib-jdk8", kotlin_version))

  compile(project(":forgegradle"))
  compile("org.gradle", "gradle-kotlin-dsl", gradle_kotlin_dsl_version)
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}