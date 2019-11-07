package com.slice.verifly.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.slice.verifly.data.local.db.AppDatabase
import com.slice.verifly.data.local.pref.AppPreferences
import com.slice.verifly.data.repo.DataManagerHelper
import com.slice.verifly.data.repo.DataManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val prefModule = module {
    single { AppPreferences(androidContext()) }
}

val dbModule = module {
    single { AppDatabase.createDatabase(androidContext(), get()) }
}

/**
 * Insert any new dependency into [appModule]
 */
val appModule = module {
    single { provideGson() }
    single { provideDataManager() }
    factory { provideCoroutineScope() }
    factory { provideAppContext(androidApplication()) }
}

fun provideGson(): Gson {
    return Gson()
}

fun provideDataManager(): DataManagerHelper {
    return DataManager()
}

fun provideCoroutineScope(): CoroutineScope {
    //create a new Job
    val job = Job()
    //create a coroutine context with the job and the dispatcher
    val coroutineContext: CoroutineContext = job + Dispatchers.Default
    //create a coroutine scope with the coroutine context
    return CoroutineScope(coroutineContext)
}

fun provideAppContext(androidApplication: Application): Context? {
    return androidApplication.applicationContext
}