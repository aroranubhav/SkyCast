import BuildConstants.COMPILE_SDK
import BuildConstants.JVM_TOOLCHAIN
import BuildConstants.MIN_SDK
import BuildConstants.TARGET_SDK
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidApplicationConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply(catalogLibs.findPlugin("android.application").get().get().pluginId)
                apply(catalogLibs.findPlugin("kotlin.android").get().get().pluginId)
            }

            extensions.configure<ApplicationExtension> {
                compileSdk = COMPILE_SDK
                defaultConfig {
                    targetSdk = TARGET_SDK
                    minSdk = MIN_SDK
                }
            }

            extensions.configure<KotlinAndroidProjectExtension> {
                jvmToolchain(JVM_TOOLCHAIN)
            }
        }
    }
}