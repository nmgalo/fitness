import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream


plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    kotlin("kapt")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("org.jetbrains.kotlin.plugin.serialization") version Versions.KOTLIN
}

android {
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        applicationId = AppConfig.APP_ID
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    val cfg = Properties().apply {
        load(FileInputStream(File(rootProject.rootDir, "apiKey.properties")))
    }

    buildTypes.forEach {
        cfg.forEach { key, value ->
            it.buildConfigField("String", key.toString(), value.toString())
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Versions.JAVA
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation("com.airbnb.android:lottie:5.0.3")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-service:2.4.1")

    // map
    implementation("com.google.maps.android:maps-utils-ktx:3.3.0")
    implementation("com.google.android.gms:play-services-maps:18.0.2")
    implementation("com.google.android.gms:play-services-location:20.0.0")

    // room
    applyRoom()

    applyAndroidX()
    applyCoreLibs()
    applyNavigation()
    applyHilt()
    applyFirebase()
    applyNetwork()

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}