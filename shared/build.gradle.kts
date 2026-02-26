import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("org.jetbrains.kotlin.native.cocoapods")

}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
//    listOf(
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach { iosTarget ->
//        iosTarget.binaries.framework {
//            baseName = "Shared"
//            isStatic = true
//        }
//    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()
    cocoapods {
        summary = "Common library for the Viaclara"
        version = "1.2"
        homepage = "https://github.com/touchlab/KaMPKit"

        framework {
//            baseName = "Shared"
            isStatic = false // dynamic required for SwiftUI Preview + SKIE
        }

        extraSpecAttributes["swift_version"] = "\"5.0\""
        podfile = project.file("../iosApp/Podfile")

        // Optional but useful for multi-build environments
        xcodeConfigurationToNativeBuildType["Staging"] =
            org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.DEBUG
    }


    jvm()

    sourceSets {
        commonMain.dependencies {
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }


    }
}

android {
    namespace = "com.app.starleet.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
