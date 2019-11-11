package com.slice.verifly.features.home.communicator

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import com.slice.verifly.features.home.enums.HomeScreen
import com.slice.verifly.features.home.enums.HomeTransaction

interface HomeCommunicator {

    // navigational communications

    /**
     * actions with type-safe arguments
     */

    fun transact(navDirections: NavDirections)

    /**
     * actions with type-unsafe arguments
     */

    fun transact(@IdRes actionId: Int, bundle: Bundle? = null)

    fun transact(homeTransaction: HomeTransaction, bundle: Bundle? = null)

    /**
     * back-press
     */

    fun back()

    /**
     * navigation destinations
     */

    fun getCurrentScreen(): HomeScreen?

    fun refreshCurrentScreen(homeScreen: HomeScreen)

    // other utility communications

    fun setUpToolbar(title: String, showNavIcon: Boolean, showExpandBtn: Boolean)

    fun showLoading()

    fun hideLoading()
}