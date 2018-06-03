package net.minecraftforge.gradle.kotlin

import net.minecraftforge.gradle.plugin.ForgeGradleExtension
import net.minecraftforge.gradle.plugin.ForgeGradlePlugin
import net.minecraftforge.gradle.plugin.ForgeGradlePluginInstance
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal val Project.fgPlugin: ForgeGradlePlugin
  get() = plugins.getPlugin(ForgeGradlePlugin::class.java)

internal val Project.fg: ForgeGradlePluginInstance
  get() = fgPlugin.getPluginInstance(this)

/**
 * Configures the ForgeGradle extension for this project.
 *
 * @param configuration the configuration block.
 */
fun Project.forgegradle(configuration: ForgeGradleExtension.() -> Unit): Unit =
  configure(configuration)

/**
 *
 */
fun ForgeGradleExtension.mappings(configuration: ForgeGradleExtension.Mappings.() -> Unit): Unit =
  mappings.run(configuration)

/**
 *
 */
fun ForgeGradleExtension.minecraft(configuration: ForgeGradleExtension.Minecraft.() -> Unit): Unit =
  minecraft.run(configuration)
