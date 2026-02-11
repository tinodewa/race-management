@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)

    // Additional Plugins
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.googleServices)
}

//val secretsFile = rootProject.file("secrets.properties")
//val secretsProp = Properties()
//if (secretsFile.exists()) {
//    secretsProp.load(secretsFile.reader())
//} else {
//    logger.warn("No secrets.properties found, using default or environment values")
//}

// Open the buildConfig block (This sits at the root level, OUTSIDE kotlin { })
//buildConfig {
//    // 1. Set the package name for the generated class
//    packageName("com.hit.railway")
//
//    buildConfigField(
//        "String",
//        "USER_ID",
//        "\"${secretsProp.getProperty("USER_ID")}\""
//    )
//}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm()
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }

    // --- SOLUSI UTAMA: DEFINISIKAN HIERARKI MANUAL ---
    // Ini akan menimpa default template yang mungkin "sotoy" membuatkan webMain
    applyDefaultHierarchyTemplate {
        common {
            withAndroidTarget()
            withJvm()
            withWasmJs() // Paksa wasmJs langsung di bawah common, tanpa middleman 'webMain'
        }
    }

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
            implementation(libs.adaptive)
            implementation(libs.material.icons.extended.v173)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Koin for Dependency Injection
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Ktorfit
            implementation(libs.ktorfit.lib)
            implementation(libs.ktorfit.converters.response)

            // Ktor for Networking
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)

            // Napier for Logging
            implementation(libs.napier)

            // Kotlinx datetime
            implementation(libs.kotlinx.datetime)

            // Voyager Navigation
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.koin)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.screenmodel)
            implementation(libs.voyager.bottom.sheet.navigator)
            implementation(libs.voyager.tab.navigator)

            // Multiplatform Settings -> Key-Value Storage
            implementation(libs.multiplatform.settings)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)

            // Room dependencies
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.room.ktx)

            // TAMBAHKAN BOM INI (Wajib untuk mengatur versi otomatis)
            implementation(project.dependencies.platform(libs.firebase.bom))

            // Core Firebase GitLive (hanya interface, implementasi ada di android/web)
            implementation(libs.firebase.firestore)
            implementation(libs.firebase.auth)

            // 3. Ktor Engine (CIO works on both Android & Desktop)
            implementation(libs.ktor.client.cio)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)

            // SQLite JDBC Driver for Desktop
            implementation(libs.sqlite.jdbc)

            // Kotlinx datetime
            implementation(libs.kotlinx.datetime)

            // KITA TIDAK PAKAI GITLIVE DI SINI
            // Kita pakai Admin SDK resmi Google untuk Java
            implementation(libs.firebase.admin)

            // 3. Ktor Engine (CIO works on both Android & Desktop)
            implementation(libs.ktor.client.cio)

//            implementation(libs.kotlin.stdlib.js)
        }
        wasmJsMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}

android {
    namespace = "com.hit.racemanagement"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.hit.racemanagement"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
    ksp(libs.androidx.room.compiler)
}


compose.desktop {
    application {
        mainClass = "com.hit.racemanagement.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.hit.racemanagement"
            packageVersion = "1.0.0"

            description = "Race Management App"
            copyright = "© 2026 Hit Inc."
            vendor = "Race Management"

            // add an app icon
            // macOS needs .icns, Windows needs .ico
            macOS {
                iconFile.set(project.file("launcher-icons/icon.icns"))
                bundleID = "com.railway.desktop"
                dockName = "Railway"
            }
            windows {
                iconFile.set(project.file("launcher-icons/icon.ico"))
                menuGroup = "Railway"
            }
            linux {
                // Points to composeApp/launcher-icons/icon.png
                iconFile.set(project.file("launcher-icons/icon.png"))
            }
        }

        nativeDistributions {
            // ... your existing settings (packageName, version, etc) ...

            // ✅ ADD THIS LINE:
            modules("java.sql")

            // If you still get errors, you might need these too (common for SQLite/JDBC):
            // modules("java.sql", "java.naming")
        }

        // Optimize for Production
        buildTypes.release.proguard {
            configurationFiles.from(project.file("compose-desktop.pro"))
            obfuscate.set(false) // Keep false unless you strictly need it; it breaks reflection often
            optimize.set(true)
        }
    }
}

ktorfit {
    // THIS is the manual override you asked for.
    // Since you are on Kotlin 2.3.0, you might need to force the specific plugin binary.
    compilerPluginVersion = "2.3.3"
}
