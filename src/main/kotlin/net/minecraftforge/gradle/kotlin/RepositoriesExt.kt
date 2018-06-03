package net.minecraftforge.gradle.kotlin

import net.minecraftforge.gradle.api.mapping.MappingProvider
import net.minecraftforge.gradle.mappings.MCPMappingProvider
import net.minecraftforge.gradle.repo.MCLauncherStreamer
import net.minecraftforge.gradle.repo.MagicalRepo
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler

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

fun RepositoryHandler.forgeMaven() = maven {
  name = "forge"
  setUrl("http://files.minecraftforge.net/maven")
}

fun RepositoryHandler.minecraftMaven() {
  MagicalRepo.add(project.repositories, "mclauncher", "https://launcher.mojang.com/", MCLauncherStreamer())
  maven {
    name = "mclibraries"
    setUrl("https://libraries.minecraft.net")
    metadataSources {
      artifact() // Don't even start looking for these guys' deps... It ends in hell
    }
  }
}