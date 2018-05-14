package net.minecraftforge.gradle.kotlin.plugin

import net.minecraftforge.gradle.plugin.ForgeGradlePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class ForgeGradleKotlinPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    project.pluginManager.apply(ForgeGradlePlugin::class.java)
  }
}