// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        //maven { url = uri("https://maven.fabric.io/public") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.50")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files

        classpath("com.google.gms:google-services:4.3.2")  // Google Services plugin
        //classpath("io.fabric.tools:gradle:1.31.0")  // Crashlytics plugin
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.1.0") // Navigation plugin
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
