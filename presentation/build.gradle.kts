import org.gradle.kotlin.dsl.libs

plugins {
    id("skycast.android.library")
    id("skycast.android.library.compose")
    id("skycast.android.hilt")
}

android {
    namespace = "com.maxi.skycast.presentation"
}

dependencies {
    implementation(project(":domain"))

    implementation(platform(libs.compose.bom))

    // Compose
    implementation(libs.bundles.compose)

    // Lifecycle
    implementation(libs.bundles.lifecycle)

    // Navigation
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.hilt.work)

    /*implementation(libs.glance.appwidget)
    implementation(libs.glance.material3)*/
}