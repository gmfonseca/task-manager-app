plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
}

group = "br.com.gmfonseca"
version = "1.0-SNAPSHOT"

val compose_version: String by project
val koin_version: String by project

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")

    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.material:material:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-rc01")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.runtime:runtime-livedata:$compose_version")

    implementation("io.insert-koin:koin-android:$koin_version")
    implementation("io.insert-koin:koin-android-compat:$koin_version")
    implementation("io.insert-koin:koin-androidx-workmanager:$koin_version")
    implementation("io.insert-koin:koin-androidx-compose:$koin_version")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")
}

android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "br.com.gmfonseca.taskmanager"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = compose_version
        kotlinCompilerVersion = "1.5.21"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}