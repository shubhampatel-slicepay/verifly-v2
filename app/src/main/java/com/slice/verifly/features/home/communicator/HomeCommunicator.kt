package com.slice.verifly.features.home.communicator

import android.os.Bundle
import com.slice.verifly.features.home.enums.HomeTransaction

interface HomeCommunicator {

    fun setUpToolbar(title: String, showNavIcon: Boolean, showExpandBtn: Boolean)

    fun transact(navigationAction: HomeTransaction, bundle: Bundle? = null)
}