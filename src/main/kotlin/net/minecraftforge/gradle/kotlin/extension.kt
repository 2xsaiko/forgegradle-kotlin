package net.minecraftforge.gradle.kotlin

import net.minecraftforge.gradle.plugin.ForgeGradleExtension
import net.minecraftforge.gradle.util.Remapper
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create

/**
 * Creates a dependency on a module without adding it to a configuration.
 * @param dependencyNotation notation for the dependency to be added
 * @see [org.gradle.api.artifacts.dsl.DependencyHandler.create]
 */
fun Project.deobf(dependencyNotation: Any): Any =
  Remapper.resolveDeobf(this, dependencies.create(dependencyNotation))

/**
 * Creates a dependency on a module without adding it to a configuration.
 * @param group the group of the module to be added as a dependency.
 * @param name the name of the module to be added as a dependency.
 * @param version the optional version of the module to be added as a dependency.
 * @param configuration the optional configuration of the module to be added as a dependency.
 * @param classifier the optional classifier of the module artifact to be added as a dependency.
 * @param ext the optional extension of the module artifact to be added as a dependency.
 * @return The dependency.
 * @see [org.gradle.api.artifacts.dsl.DependencyHandler.create]
 */
fun Project.deobf(
  group: String,
  name: String,
  version: String? = null,
  configuration: String? = null,
  classifier: String? = null,
  ext: String? = null): ExternalModuleDependency =
  dependencies.create(group, name, version, configuration, classifier, ext)

/**
 * Configures the ForgeGradle extension for this project.
 *
 * @param configuration the configuration block.
 */
fun Project.forgegradle(configuration: ForgeGradleExtension.() -> Unit) =
  configure(configuration)

/**
 *
 */
fun ForgeGradleExtension.forge(configuration: ForgeGradleExtension.Forge.() -> Unit) =
  forge.run(configuration)

/**
 *
 */
fun ForgeGradleExtension.mappings(configuration: ForgeGradleExtension.Mappings.() -> Unit) =
  mappings.run(configuration)

/**
 *
 */
fun ForgeGradleExtension.minecraft(configuration: ForgeGradleExtension.Minecraft.() -> Unit) =
  minecraft.run(configuration)
