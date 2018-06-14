package net.minecraftforge.gradle.kotlin.plugin

import net.minecraftforge.gradle.kotlin.setProjectFor
import net.minecraftforge.gradle.moddev.ForgeGradlePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class ForgeGradleKotlinPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    project.pluginManager.apply(ForgeGradlePlugin::class.java)

    setProjectFor(project.repositories, project)
    setProjectFor(project.dependencies, project)
  }
}