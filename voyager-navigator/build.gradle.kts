import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

kotlin {
    explicitApi = ExplicitApiMode.Strict

    android {
        publishAllLibraryVariants()
    }
    jvm("desktop")

    sourceSets {
        val commonMain by getting
        val jvmMain by creating {
            dependsOn(commonMain)
            dependencies {
                api(projects.voyagerCore)
                implementation(compose.runtime)
                implementation(compose.ui)
            }
        }
        val desktopMain by getting {
            dependsOn(jvmMain)
        }
        val androidMain by getting {
            dependsOn(jvmMain)
            dependencies {
                implementation(libs.compose.activity)
            }
        }
        val commonTest by getting
        val jvmTest by creating {
            dependsOn(commonTest)
        }
        val desktopTest by getting {
            dependsOn(jvmTest)
        }
        val androidTest by getting {
            dependsOn(jvmTest)
        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
}