package net.minecraftforge.gradle.kotlin

import net.minecraftforge.gradle.api.mapping.MappingVersion
import net.minecraftforge.gradle.shared.util.RemappedDependency
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.create

// TODO: factor these out in FG so there doesn't need to be duplicate code

internal val DependencyHandler.project: Project
  get() = (this as? DependencyHandlerScope)?.dependencies?.project
          ?: getProjectFor(this)
          ?: error("No project registered for $this")

internal fun DependencyHandler.mappings(): MappingVersion =
  MappingVersion.lazy {
    MappingVersion(project.fg.fgExt.mappings.provider,
      project.fg.fgExt.mappings.channel, project.fg.fgExt.mappings.version,
      project.fg.fgExt.minecraft.version, project.fg.fgExt.mappings.deobfMappings)
  }

/**
 * Creates a dependency on a module without adding it to a configuration.
 * @param dependencyNotation notation for the dependency to be added
 * @see [org.gradle.api.artifacts.dsl.DependencyHandler.create]
 */
fun DependencyHandler.deobf(dependencyNotation: Any): RemappedDependency =
  RemappedDependency(create(dependencyNotation), mappings())

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
fun DependencyHandler.deobf(
  group: String,
  name: String,
  version: String? = null,
  configuration: String? = null,
  classifier: String? = null,
  ext: String? = null): RemappedDependency =
  RemappedDependency(create(group, name, version, configuration, classifier, ext), mappings())

///**
// * Creates a dependency on a module without adding it to a configuration.
// * @param dependencyNotation notation for the dependency to be added
// * @see [org.gradle.api.artifacts.dsl.DependencyHandler.create]
// */
//fun DependencyHandler.remap(
//  dependencyNotation: Any,
//  mapping: String,
//  provider: String? = null,
//  channel: String? = null,
//  mcversion: String? = null,
//  version: String? = null,
//  remapTransitives: Boolean? = null): Any =
//  Remapper.remapDependency(project.fg, create(dependencyNotation),
//    mapOf("mapping" to mapping, "provider" to provider, "channel" to channel,
//      "mcversion" to mcversion, "version" to version, "remapTransitives" to remapTransitives)
//      .filterValues { it != null })
//
///**
// * Creates a dependency on a module without adding it to a configuration.
// * @param group the group of the module to be added as a dependency.
// * @param name the name of the module to be added as a dependency.
// * @param version the optional version of the module to be added as a dependency.
// * @param configuration the optional configuration of the module to be added as a dependency.
// * @param classifier the optional classifier of the module artifact to be added as a dependency.
// * @param ext the optional extension of the module artifact to be added as a dependency.
// * @return The dependency.
// * @see [org.gradle.api.artifacts.dsl.DependencyHandler.create]
// */
//fun DependencyHandler.remap(
//  group: String,
//  name: String,
//  version: String? = null,
//  configuration: String? = null,
//  classifier: String? = null,
//  ext: String? = null,
//  mapping: String,
//  provider: String? = null,
//  channel: String? = null,
//  mcversion: String? = null,
//  mappingVersion: String? = null,
//  remapTransitives: Boolean? = null): Any =
//  Remapper.remapDependency(project.fg, create(group, name, version, configuration, classifier, ext),
//    mapOf("mapping" to mapping, "provider" to provider, "channel" to channel,
//      "mcversion" to mcversion, "version" to mappingVersion, "remapTransitives" to remapTransitives)
//      .filterValues { it != null })
//
//fun DependencyHandler.forge(version: String) =
//  Remapper.remapDependency(project.fg, {
//    project.dependencies.create(
//      group = "net.minecraftforge",
//      name = "forge",
//      version = "${project.fg.fgExt.minecraft.version}-$version",
//      classifier = "universal"
//    )
//  }, mapOf("mapping" to project.fg.fgExt.mappings.forgeMappings))
//
