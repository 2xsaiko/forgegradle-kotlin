package net.minecraftforge.gradle.kotlin

import net.minecraftforge.gradle.api.mapping.MappingProvider
import net.minecraftforge.gradle.shared.mappings.MCPMappingProvider
import net.minecraftforge.gradle.shared.repo.CustomRepository
import net.minecraftforge.gradle.shared.repo.MCLauncherArtifactProvider
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

// TODO: factor these out in FG so there doesn't need to be duplicate code

internal val RepositoryHandler.project: Project
  get() = getProjectFor(this)
          ?: error("No project registered for $this")

fun RepositoryHandler.mappings(provider: MappingProvider): MappingProvider {
  project.fg.mappings.register(provider)
  return provider
}

fun RepositoryHandler.mappings(action: Action<MappingProvider>, provider: MappingProvider): MappingProvider {
  action.execute(provider)
  project.fg.mappings.register(provider)
  return provider
}

fun RepositoryHandler.mcp(): MCPMappingProvider =
  project.objects.newInstance(MCPMappingProvider::class.java)

fun RepositoryHandler.forgeMaven() =
  maven("http://files.minecraftforge.net/maven") {
    name = "forge"
  }

fun RepositoryHandler.minecraftMaven() {
  CustomRepository.add(project.repositories, "mclauncher", "https://launcher.mojang.com/", MCLauncherArtifactProvider(), null)
  maven("https://libraries.minecraft.net") {
    name = "mclibraries"
    metadataSources {
      it.artifact() // Don't even start looking for these guys' deps... It ends in hell
    }
  }
}