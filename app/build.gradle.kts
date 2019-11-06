plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    //id("io.fabric")
}

androidExtensions {
    isExperimental = true
}

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "com.slice.verifly"
        minSdkVersion(16)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "2.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }
    signingConfigs {
        getByName("debug") {
            aaptOptions.cruncherEnabled = false
            storeFile = rootProject.file("debug.keystore")
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storePassword = "android"
        }

        create("release") {
            aaptOptions.cruncherEnabled = false
            storeFile = rootProject.file("app/verifly.keystore")
            keyAlias = "meshkey"
            keyPassword = "meshapp1234"
            storePassword = "meshapp1234"
        }
    }
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = !isDebuggable
            isShrinkResources = !isDebuggable
            applicationIdSuffix = ".dev"
            //ext.set("enableCrashlytics", false)
            signingConfig = signingConfigs.getByName("debug")

            buildConfigField("String", "SERVER_URL", "\"https://api-feature.slicepay.in:9021/\"")
            buildConfigField("String", "URL", "\"ninjadev.slicepay.in/\"")
            buildConfigField("String", "WEB_URL", "\"https://dev.slicepay.in/\"")
            buildConfigField("String", "ENVIRONMENT", "\"dev\"")

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        create("staging") {
            isDebuggable = true
            isMinifyEnabled = !isDebuggable
            isShrinkResources = !isDebuggable
            applicationIdSuffix = ".test"
            //ext.set("enableCrashlytics", false)
            signingConfig = signingConfigs.getByName("debug")

            buildConfigField("String", "SERVER_URL", "\"https://api-stage-feature.slicepay.in:9036/\"")
            buildConfigField("String", "URL", "\"ninjatest.slicepay.in\"")
            buildConfigField("String", "WEB_URL", "\"https://test.slicepay.in/\"")
            buildConfigField("String", "ENVIRONMENT", "\"test\"")

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        create("beta") {
            isDebuggable = true
            isMinifyEnabled = !isDebuggable
            isShrinkResources = !isDebuggable
            signingConfig = signingConfigs.getByName("release")

            buildConfigField("String", "SERVER_URL", "\"https://api-beta.slicepay.in/\"")
            buildConfigField("String", "URL", "\"ninja.slicepay.in\"")
            buildConfigField("String", "WEB_URL", "\"https://slicepay.in/\"")
            buildConfigField("String", "ENVIRONMENT", "\"prod\"")

//            val gitSha: String by extra
//            buildConfigField("String", "GIT_SHA", "\"${gitSha}\"")
//
//            val buildTime: String by extra
//            buildConfigField("String", "BUILD_TIME", "\"${buildTime}\"")

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = !isDebuggable
            isShrinkResources = !isDebuggable
            signingConfig = signingConfigs.getByName("release")

            buildConfigField("String", "SERVER_URL", "\"https://api.slicepay.in/\"")
            buildConfigField("String", "URL", "\"ninja.slicepay.in\"")
            buildConfigField("String", "WEB_URL", "\"https://slicepay.in/\"")
            buildConfigField("String", "ENVIRONMENT", "\"prod\"")

//            val gitSha: String by extra
//            buildConfigField("String", "GIT_SHA", "\"${gitSha}\"")
//
//            val buildTime: String by extra
//            buildConfigField("String", "BUILD_TIME", "\"${buildTime}\"")

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50")

    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

    // SlicePay Logger
    implementation(project(":slicepaylogger-release"))

    /**
     *  Android components
     */

    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.1.0")
    implementation("androidx.recyclerview:recyclerview:1.0.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.1.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.1.0")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0-alpha01")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0-alpha01")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0-alpha01")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0-alpha01")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0-alpha01")
    kapt("androidx.lifecycle:lifecycle-compiler:2.2.0-alpha01")

    // Room persistence
    implementation("androidx.room:room-runtime:2.2.1")
    kapt("androidx.room:room-compiler:2.2.1")
    implementation("androidx.room:room-ktx:2.2.1") // Kotlin Extensions and Coroutines support

    /**
     *  Kotlin frameworks
     */

    // Koin : Dependency injection
    implementation("org.koin:koin-android:2.0.1")
    implementation("org.koin:koin-core:2.0.1")
    implementation("org.koin:koin-androidx-scope:2.0.1")
    implementation("org.koin:koin-androidx-viewmodel:2.0.1")
    implementation("org.koin:koin-androidx-ext:2.0.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.0")

    /**
     *  Google services
     */

    // Analytics
    implementation("com.google.firebase:firebase-analytics:17.2.1")
    implementation("com.crashlytics.sdk.android:crashlytics:2.10.1")

    // Material designs
    implementation("com.google.android.material:material:1.0.0")

    /**
     *  Dependencies
     */

    // Okhttp3
    implementation("com.squareup.okhttp3:okhttp:3.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.11.0")

    // REST client
    implementation("com.squareup.retrofit2:retrofit:2.6.0") // Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.6.0") // Gson parser

    // Reactive extensions
    implementation("io.reactivex.rxjava2:rxjava:2.2.12") // RxJava
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1") // RxAndroid
    implementation("com.jakewharton.rxbinding3:rxbinding:3.0.0") // Rx binding
    implementation("com.jakewharton.rxbinding3:rxbinding-core:3.0.0")
    implementation("com.jakewharton.rxbinding3:rxbinding-appcompat:3.0.0")
    implementation("com.jakewharton.rxbinding3:rxbinding-material:3.0.0")
    implementation("com.github.tbruyelle:rxpermissions:0.10.2") // Rx permissions

    // EventBus
    implementation("org.greenrobot:eventbus:3.1.1")

    // Image loading framework
    implementation("com.github.bumptech.glide:glide:4.10.0")
    kapt("com.github.bumptech.glide:compiler:4.10.0")

    // JavaX components
    implementation("org.glassfish:javax.annotation:10.0-b28")

    // Logging
    implementation("com.jakewharton.timber:timber:4.7.1") // Timber

    // Cloud uploading
    implementation("com.cloudinary:cloudinary-android:1.24.0")
}