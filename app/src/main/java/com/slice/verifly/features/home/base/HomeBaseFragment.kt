package com.slice.verifly.features.home.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.slice.verifly.base.BaseFragment
import com.slice.verifly.features.home.communicator.HomeCommunicator
import com.slice.verifly.features.home.enums.HomeTransaction
import com.slice.verifly.features.home.viewmodel.HomeActivityViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class HomeBaseFragment: BaseFragment() {

    // Components

    val viewModel: HomeActivityViewModel by viewModel()

    // Properties

    var communicator: HomeCommunicator? = null

    // Lifecycle

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeCommunicator) {
            communicator = context
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpToolbar()
    }

    // Define internal common operational functions here
    // i.e. can't be accessed or over-ridden by HomeBaseFragment types (private)



    // Define accessible common operational functions here
    // can be accessed but can't be over-ridden by HomeBaseFragment types (protected)

    // type-safe navigation arguments
    protected fun navigationController(
        navDirections: NavDirections
    ) {
        activity?.findNavController(homeNavHost.id)?.navigate(navDirections)
    }

    // type-unsafe navigation arguments
    protected fun navigationController(
        @IdRes actionId: Int,
        bundle: Bundle? = null
    ) {
        activity?.findNavController(homeNavHost.id)?.navigate(actionId, bundle)
    }

    protected fun navigationController(
        homeTransaction: HomeTransaction,
        bundle: Bundle? = null
    ) {
        activity?.findNavController(homeNavHost.id)?.navigate(homeTransaction.actionID, bundle)
    }

    // Define default functions here which should be overridden by HomeBaseFragment types, who only need them
    // optional implementations (default)



    // Define abstract functions here which needs to be overridden by all HomeBaseFragment types
    // mandatory implementations (abstract)

    abstract fun setUpToolbar()
}