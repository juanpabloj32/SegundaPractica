plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.miempresa.segundapractica"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.miempresa.segundapractica"
        minSdk = 28
        targetSdk = 36
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
}

dependencies {
    // --- Red y Datos (Retrofit) ---
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // --- Interfaz de Usuario (UI) ---
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.google.android.material:material:1.11.0")

    // --- Librerías Base (Version Catalog / libs) ---
    implementation(libs.appcompat)
    implementation(libs.material) // Esta suele venir en libs, pero la dejé arriba también por seguridad
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // --- Testing ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}