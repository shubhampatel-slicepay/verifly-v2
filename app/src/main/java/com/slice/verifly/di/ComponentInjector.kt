package com.slice.verifly.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Injectors for Activity
 */
val activityInjectorModule = module {

}

/**
 * Injectors for Fragments
 */
val fragmentInjectorModule = module {

}

/**
 * Injectors for ViewModel (Lifecycle components)
 */
val viewModelInjectorModule = module {
    viewModel { ComponentBuilder.bindSplashFragmentViewModel(get()) }
    viewModel { ComponentBuilder.bindLoginFragmentViewModel(get()) }
    viewModel { ComponentBuilder.bindResetPasswordViewModel(get()) }
    viewModel { ComponentBuilder.bindChangePasswordViewModel(get()) }
    viewModel { ComponentBuilder.bindHomeActivityViewModel(get()) }
    viewModel { ComponentBuilder.bindDashboardFragmentViewModel(get()) }
}

/**
 * Repositories are defined here as dependencies, in case
 */
val repositoryInjectorModule = module {
    single { ComponentBuilder.injectSplashFragmentRepository(get()) }
    single { ComponentBuilder.injectLoginFragmentRepository(get()) }
    single { ComponentBuilder.injectResetPasswordRepository(get()) }
    single { ComponentBuilder.injectChangePasswordRepository(get()) }
    single { ComponentBuilder.injectHomeActivityRepository(get()) }
    single { ComponentBuilder.injectDashboardFragmentRepository(get()) }
}