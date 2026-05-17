import BuildConstants.COMPILE_SDK
import BuildConstants.JVM_TOOLCHAIN
import BuildConstants.MIN_SDK
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply(catalogLibs.findPlugin("android.library").get().get().pluginId)
                apply(catalogLibs.findPlugin("kotlin.android").get().get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                compileSdk = COMPILE_SDK
                defaultConfig {
                    minSdk = MIN_SDK
                }
            }

            extensions.configure<KotlinAndroidProjectExtension> {
                jvmToolchain(JVM_TOOLCHAIN)
            }
        }
    }
}