plugins {
    id("org.jetbrains.kotlin.jvm") version "2.2.10"
    id("io.ktor.plugin") version "3.0.3"
    kotlin("plugin.serialization") version "2.2.10"
}

group = "com.example"
version = "1.0.0"

application {
    mainClass.set("com.example.kt6_4.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.compose.ui)
//    implementation(libs.androidx.compose.ui.graphics)
//    implementation(libs.androidx.compose.ui.tooling.preview)
//    implementation(libs.androidx.compose.material3)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
//    debugImplementation(libs.androidx.compose.ui.tooling)
//    debugImplementation(libs.androidx.compose.ui.test.manifest)

// Ktor Server Core
    implementation("io.ktor:ktor-server-core:3.0.1")
    implementation("io.ktor:ktor-server-netty:3.0.1")

    // Content Negotiation + JSON
    implementation("io.ktor:ktor-server-content-negotiation:3.0.1")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.1")

    // Authentication JWT
    implementation("io.ktor:ktor-server-auth:3.0.1")
    implementation("io.ktor:ktor-server-auth-jwt:3.0.1")

    // Call Logging
    implementation("io.ktor:ktor-server-call-logging:3.0.1")

    // Status Pages
    implementation("io.ktor:ktor-server-status-pages:3.0.1")

    // JWT Library
    implementation("com.auth0:java-jwt:4.4.0")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.5.12")
}