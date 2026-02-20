import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.kotlinSerialization)
    id("com.android.library")
    id("maven-publish")
}

group = "com.github.anthropics"
version = "1.0.0"

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
        publishLibraryVariants("release", "debug")
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "SmartAlphaTranslatorKit"
            isStatic = true
        }
    }

    // JS and WASM targets commented out — Room KMP does not support these yet.
    // js {
    //     browser()
    //     binaries.executable()
    // }
    //
    // @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    // wasmJs {
    //     browser()
    //     binaries.executable()
    // }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            // Koin (Multiplatform DI) — exposed as api so consumers can
            // call startKoin, inject(), etc. to initialize the library
            api(libs.koin.core)
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)
            // Room KMP
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
            // Ktor (Multiplatform HTTP for OpenAI)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.json)
            // Kotlinx Serialization
            implementation(libs.kotlinx.serialization.json)
        }

        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            // ML Kit & Play Services (Android-only)
            implementation(libs.mlkit.translate)
            implementation(libs.play.services.tasks)
            implementation(libs.kotlinx.coroutines.play.services)
            // OkHttp (Android-only, for OpenAI translator)
            implementation(libs.okhttp)
            // Koin Android — exposed as api so consumers can call
            // androidContext(), androidLogger() in their Application class
            api(libs.koin.android)
            // Ktor OkHttp engine (Android)
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.alpharays.smartalphatranslator"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Room KSP processor for platform targets only
    add("kspAndroid", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

// Publishing configuration for JitPack
afterEvaluate {
    publishing {
        publications {
            // Android publications are auto-created by publishLibraryVariants above
            // KMP targets are auto-published by the kotlin multiplatform plugin
        }
    }
}