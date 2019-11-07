package com.slice.verifly.di

import com.slice.verifly.features.login.repository.LoginFragmentRepository
import com.slice.verifly.features.login.viewmodel.LoginFragmentViewModel
import com.slice.verifly.features.splash.repository.SplashFragmentRepository
import com.slice.verifly.features.splash.viewmodel.SplashFragmentViewModel
import com.slice.verifly.data.repo.DataManagerHelper
import com.slice.verifly.features.home.repository.HomeActivityRepository
import com.slice.verifly.features.home.viewmodel.HomeActivityViewModel
import com.slice.verifly.features.login.repository.ChangePasswordRepository
import com.slice.verifly.features.login.viewmodel.ChangePasswordViewModel
import com.slice.verifly.features.login.repository.ResetPasswordRepository
import com.slice.verifly.features.login.viewmodel.ResetPasswordViewModel

object ComponentBuilder {
    /**
     * Implementations for Activity Injectors
     */


    /**
     * Implementations for Fragment Injectors
     */


    /**
     * Implementations for ViewModel Injectors
     */
    fun bindSplashFragmentViewModel(get: SplashFragmentRepository): SplashFragmentViewModel {
        return SplashFragmentViewModel(get)
    }

    fun bindLoginFragmentViewModel(get: LoginFragmentRepository): LoginFragmentViewModel {
        return LoginFragmentViewModel(get)
    }

    fun bindResetPasswordViewModel(get: ResetPasswordRepository): ResetPasswordViewModel {
        return ResetPasswordViewModel(get)
    }

    fun bindChangePasswordViewModel(get: ChangePasswordRepository): ChangePasswordViewModel {
        return ChangePasswordViewModel(get)
    }

    fun bindHomeActivityViewModel(get: HomeActivityRepository): HomeActivityViewModel {
        return HomeActivityViewModel(get)
    }

    /**
     * Implementations for Repository Injectors
     */
    fun injectSplashFragmentRepository(get: DataManagerHelper) =
        SplashFragmentRepository(get)

    fun injectLoginFragmentRepository(get: DataManagerHelper) =
        LoginFragmentRepository(get)

    fun injectResetPasswordRepository(get: DataManagerHelper) =
        ResetPasswordRepository(get)

    fun injectChangePasswordRepository(get: DataManagerHelper) =
        ChangePasswordRepository(get)

    fun injectHomeActivityRepository(get: DataManagerHelper) =
        HomeActivityRepository(get)
}