import org.gradle.kotlin.dsl.libs

plugins {
    id("skycast.android.library")
}

android {
    namespace = "com.maxi.skycast.domain"
}

dependencies {
    // Coroutines Core
    implementation(libs.coroutines.core)

    // Javax
    implementation(libs.javax.inject)
}