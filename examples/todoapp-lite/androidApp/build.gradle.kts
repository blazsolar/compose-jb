plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    android()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.material3)
                implementation("com.google.android.material:material:1.8.0")
            }
        }
    }
}

android {
    compileSdk = 33
    namespace = "example.todoapp.lite"
    defaultConfig {
        applicationId = "org.jetbrains.TodoAppLite"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
