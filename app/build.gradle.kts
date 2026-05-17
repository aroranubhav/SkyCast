import java.util.Properties

plugins {
    id("skycast.android.application")
    id("skycast.android.compose")
    id("skycast.android.hilt")
}

val localProperties = Properties().apply {
    rootProject.file("local.properties")
        .takeIf {
            it.exists()
        }?.inputStream()
        .use {
            load(it)
        }
}

android {
    namespace = "com.maxi.skycast"

    defaultConfig {
        applicationId = "com.maxi.skycast"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        all {
            buildConfigField("String", "API_KEY", "\"${localProperties["API_KEY"]}\"")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":presentation"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.work)
    ksp(libs.hilt.compiler.androidx)
    ksp(libs.kotlin.metadata.jvm)

    // Navigation
    implementation(libs.navigation.compose)

    // WorkManager
    implementation(libs.work.runtime.ktx)

    implementation(libs.kotlin.metadata.jvm)

    // Glance
    implementation(libs.bundles.glance)
}