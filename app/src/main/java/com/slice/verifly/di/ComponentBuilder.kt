package com.slice.verifly.di

import com.slice.verifly.features.login.mainlogin.LoginFragmentRepository
import com.slice.verifly.features.login.mainlogin.LoginFragmentViewModel
import com.slice.verifly.features.splash.mainsplash.SplashFragmentRepository
import com.slice.verifly.features.splash.mainsplash.SplashFragmentViewModel
import com.slice.verifly.data.repo.DataManagerHelper
import com.slice.verifly.features.home.HomeActivityRepository
import com.slice.verifly.features.home.HomeActivityViewModel
import com.slice.verifly.features.home.dashboard.DashboardFragmentRepository
import com.slice.verifly.features.home.dashboard.DashboardFragmentViewModel
import com.slice.verifly.features.login.changepassword.ChangePasswordRepository
import com.slice.verifly.features.login.changepassword.ChangePasswordViewModel
import com.slice.verifly.features.login.resetpassword.ResetPasswordRepository
import com.slice.verifly.features.login.resetpassword.ResetPasswordViewModel

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

    fun bindDashboardFragmentViewModel(get: DashboardFragmentRepository): DashboardFragmentViewModel {
        return DashboardFragmentViewModel(get)
    }

    /**
     * Implementations for Repository Injectors
     */
    fun injectSplashFragmentRepository(get: DataManagerHelper) =
        SplashFragmentRepository(get)

    fun injectLoginFragmentRepository(get: DataManagerHelper) = LoginFragmentRepository(get)

    fun injectResetPasswordRepository(get: DataManagerHelper) = ResetPasswordRepository(get)

    fun injectChangePasswordRepository(get: DataManagerHelper) = ChangePasswordRepository(get)

    fun injectHomeActivityRepository(get: DataManagerHelper) = HomeActivityRepository(get)

    fun injectDashboardFragmentRepository(get: DataManagerHelper) = DashboardFragmentRepository(get)
}