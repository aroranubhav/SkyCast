plugins {
    id("skycast.android.library")
    id("skycast.android.hilt")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.room)
}

android {
    namespace = "com.maxi.skycast.data"
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    implementation(project(":domain"))

    // OkHttp + Retrofit
    implementation(libs.bundles.networking)

    // Room
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    // Datastore
    implementation(libs.datastore.preferences)

    // WorkManager
    implementation(libs.work.runtime.ktx)
    implementation(libs.hilt.work)

    // Hilt
    ksp(libs.hilt.compiler.androidx)
    ksp(libs.kotlin.metadata.jvm)
}