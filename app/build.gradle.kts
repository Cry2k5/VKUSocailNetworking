plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.dacs3.socialnetworkingvku"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dacs3.socialnetworkingvku"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Core KTX library (AndroidX core extensions)
    implementation(libs.androidx.core.ktx)

    // Lifecycle runtime KTX
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Activity Compose library for Compose integration with Activity
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose BOM for managing all Compose dependencies' versions
    implementation(platform(libs.androidx.compose.bom))

    // Compose UI (the basic UI components like Box, Column, Row, etc.)
    implementation(libs.androidx.ui)

    // Compose UI graphics (providing graphics-related functionality)
    implementation(libs.androidx.ui.graphics)

    // Compose UI tooling for previewing and debugging
    implementation(libs.androidx.ui.tooling.preview)

    // Material 3 library for Material Design components
    implementation(libs.androidx.material3)

    // Unit test dependencies
    testImplementation(libs.junit)

    // Android test dependencies
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Jetpack Compose BOM for managing the versions of Compose libraries in tests
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debugging tools for Compose (to preview and test Compose UI)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit for network requests
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Room database dependencies
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // Coil for loading images asynchronously
    implementation("io.coil-kt:coil-compose:2.4.0") // or latest version

    implementation("androidx.compose.material:material-icons-extended:1.5.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

}
