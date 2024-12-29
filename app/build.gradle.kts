import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    kotlin("kapt")
    alias(libs.plugins.navigation.safe.args)
}
// If you want to load SPOONACULAR_API_KEY from local.properties:
val localProps = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProps.load(FileInputStream(localPropertiesFile))
}
val spoonacularApiKey = localProps.getProperty("SPOONACULAR_API_KEY") ?: "YOUR_DEFAULT_KEY"

android {
    namespace = "com.example.testapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.testapp"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "SPOONACULAR_API_KEY", "\"$spoonacularApiKey\"")


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
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.navigation.compose)
    implementation(libs.coil.compose)




    // Retrofit core library
    implementation(libs.retrofit)

    // Gson converter for parsing JSON
    implementation(libs.retrofit.converter.gson)

    // OkHttp client
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // ViewModel
    implementation(libs.viewmodel.ktx)

    // LiveData
    implementation(libs.livedata.ktx)

    // Lifecycle runtime (optional but recommended for lifecycle-aware components)
    implementation(libs.lifecycle.runtime)
    // Room runtime
    implementation(libs.room.runtime)

    // Room Kotlin extensions
    implementation(libs.room.ktx)

    // Room compiler (for annotation processing)
    kapt(libs.room.compiler)

    // Coroutines Core for general coroutine support
    implementation(libs.coroutines.core)

    // Coroutines Android for main thread context
    implementation(libs.coroutines.android)


}