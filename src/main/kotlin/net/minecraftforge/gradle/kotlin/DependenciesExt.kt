package net.minecraftforge.gradle.kotlin

import net.minecraftforge.gradle.mappings.Remapper
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.create

// TODO: factor these out in FG so there doesn't need to be duplicate code

internal val DependencyHandler.project: Project
  get() = (this as? DependencyHandlerScope)?.dependencies?.project
          ?: getProjectFor(this)
          ?: error("No project registered for $this")

/**
 * Creates a dependency on a module without adding it to a configuration.
 * @param dependencyNotation notation for the dependency to be added
 * @see [org.gradle.api.artifacts.dsl.DependencyHandler.create]
 */
fun DependencyHandler.deobf(dependencyNotation: Any): Any =
  Remapper.deobfDependency(project.fg, create(dependencyNotation))

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
  ext: String? = null): Any =
  Remapper.deobfDependency(project.fg, create(group, name, version, configuration, classifier, ext))

/**
 * Creates a dependency on a module without adding it to a configuration.
 * @param dependencyNotation notation for the dependency to be added
 * @see [org.gradle.api.artifacts.dsl.DependencyHandler.create]
 */
fun DependencyHandler.remap(args: Map<String, Any>, dependencyNotation: Any): Any =
  Remapper.remapDependency(project.fg, create(dependencyNotation), args)

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
fun DependencyHandler.remap(
  args: Map<String, Any>,
  group: String,
  name: String,
  version: String? = null,
  configuration: String? = null,
  classifier: String? = null,
  ext: String? = null): Any =
  Remapper.remapDependency(project.fg, create(group, name, version, configuration, classifier, ext), args)

fun DependencyHandler.forge(version: String) =
  Remapper.remapDependency(project.fg, {
    project.dependencies.create(
      group = "net.minecraftforge",
      name = "forge",
      version = "${project.fg.fgExt.minecraft.version}-$version",
      classifier = "universal"
    )
  }, mapOf("mapping" to project.fg.fgExt.mappings.forgeMappings))

