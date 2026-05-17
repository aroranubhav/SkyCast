import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply(catalogLibs.findPlugin("hilt").get().get().pluginId)
                apply(catalogLibs.findPlugin("ksp").get().get().pluginId)
            }

            dependencies {
                add("implementation", catalogLibs.findLibrary("hilt.android").get())
                add("ksp", catalogLibs.findLibrary("hilt.compiler").get())
                add("ksp", catalogLibs.findLibrary("kotlin.metadata.jvm").get())
            }
        }
    }
}