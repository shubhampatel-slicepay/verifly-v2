package com.slice.verifly.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.slice.verifly.BuildConfig
import com.slice.verifly.di.*
import com.slicepay.logger.log.SlicePayLogger
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class VeriflyApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this@VeriflyApplication)
    }

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initLoggers()
        //initFirebase()
    }

    // Koin structure
    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@VeriflyApplication)
            modules(listOf(
                appModule,
                dbModule,
                prefModule,
                networkModule,
                activityInjectorModule,
                fragmentInjectorModule,
                viewModelInjectorModule,
                repositoryInjectorModule
            ))
        }
    }

    // Loggers
    private fun initLoggers() {
        Timber.plant(Timber.DebugTree())
        SlicePayLogger.initalizeSlicePayLogger(BuildConfig.BUILD_TYPE)
        SlicePayLogger.enableForceLogger()
    }

    // Firebase and Crashlytics
    private fun initFirebase() {
        val core = CrashlyticsCore.Builder()
            .disabled(BuildConfig.DEBUG)
            .build()
        val fabric = Fabric.Builder(this@VeriflyApplication)
            .kits(Crashlytics.Builder().core(core).build())
            .debuggable(BuildConfig.DEBUG)
            .build()
        Fabric.with(fabric)
        //Crashlytics.setString("GIT_SHA", BuildConfig.GIT_SHA)
        //Crashlytics.setString("BUILD_TIME", BuildConfig.BUILD_TIME)
    }
}