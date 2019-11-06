package com.slice.verifly.di

import android.content.Context
import com.slice.verifly.BuildConfig
import com.slice.verifly.data.remote.Api
import com.slice.verifly.data.remote.ApiFactory
import com.slice.verifly.data.remote.interceptors.AuthInterceptor
import com.slice.verifly.data.remote.interceptors.GzipRequestInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideRetrofit(get()) }
    factory { provideOkHttpClient(androidContext()) }
    factory { provideApi(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(ApiFactory.getV2BaseUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideOkHttpClient(context: Context): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(AuthInterceptor)
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .connectTimeout(20, TimeUnit.SECONDS)
        .cache(Cache(context.cacheDir, 10 * 1024 * 1024))
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            } else {
                addInterceptor(GzipRequestInterceptor)
            }
        }.build()
}

fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)