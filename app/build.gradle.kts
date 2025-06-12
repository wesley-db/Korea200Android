import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "2.0.10"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.10"
    //id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    //kotlin("kapt") version "2.1.10"
}

android {
    namespace = "com.korea200"
    compileSdk = 34

    //generating the buildConfig class
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.korea200"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        //Getting the API's key and base URL
        var apiStoreFile = project.rootProject.file("api.properties")
        val properties = Properties()
        properties.load(apiStoreFile.inputStream())

        val apiKey = properties.getProperty("API_KEY") ?: ""
        val apiBaseUrl = properties.getProperty("API_BASE_URL") ?: ""
        buildConfigField("String", "API_KEY", apiKey)
        buildConfigField("String", "API_BASE_URL", apiBaseUrl)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        //isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"

            // This line tells Gradle to ignore duplicate copies of this specific file
            excludes += "META-INF/native-image/*"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.0")

    //Google Font
    implementation("androidx.compose.ui:ui-text-google-fonts:1.7.7")

    //Navigation
    implementation("androidx.navigation:navigation-compose:2.8.7")

    //Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.2")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.retrofit2:converter-kotlinx-serialization:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.2")

    //Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation("junit:junit:4.12")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")
}