plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    kotlin("plugin.serialization").version(libs.versions.kotlinSerialization)
    kotlin("kapt")
}

android {
    namespace = "com.raxors.wowbase"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.raxors.wowbase"
        minSdk = 26
        targetSdk = 34
        versionCode = 5
        versionName = "0.0.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        addManifestPlaceholders(
//            mapOf("appAuthRedirectScheme" to "https://raxors.ru/wow-oauth")
//        )
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
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //Navigation
    implementation(libs.androidx.navigation.compose)

    //Coroutines
    implementation(libs.kotlinx.coroutines.android)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //Coil
    implementation(libs.coil.compose)

    //Paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    // datastore
    implementation(libs.androidx.datastore.preferences)
    // extension for datastore to support encryption
    implementation(libs.security.crypto.datastore.preferences)
    // utility library for datastore encryption
    implementation(libs.androidx.security.crypto.ktx)

    // OAuth
    implementation(libs.appauth)

    // SplashScreen
    implementation(libs.androidx.core.splashscreen)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}