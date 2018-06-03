package net.minecraftforge.gradle.kotlin

import org.gradle.api.Project
import java.lang.ref.WeakReference
import java.util.*

// no idea if this works or not
// needed to get the project from the RepositoryHandler/DependencyHandler because I don't know of another way to do so
private val projectMap: WeakHashMap<Any, WeakReference<Project>> = WeakHashMap()

internal fun getProjectFor(any: Any): Project? =
  projectMap[any]?.get()

internal fun setProjectFor(any: Any, project: Project) {
  projectMap[any]?.get()?.also {
    if (it != project)
      error("This $any has already been registered for another project? " +
            "This should never happen and if it does, this needs to be rewritten")
  }

  println("$any -> $project") // debug

  projectMap[any] = WeakReference(project)
}