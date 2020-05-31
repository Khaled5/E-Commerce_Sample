package com.easyinc.normalrepository.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.bumptech.glide.RequestManager
import com.easyinc.mappractice.common.Logger
import com.easyinc.normalrepository.common.network_state.NetworkEvents
import com.easyinc.normalrepository.common.network_state.NetworkStateHolder
import com.theartofdev.edmodo.cropper.CropImage
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment: DaggerFragment() {

    var isConnected: Boolean = false

    lateinit var navController: NavController

    @Inject
    lateinit var picker: CropImage.ActivityBuilder

    @Inject
    @androidx.annotation.Nullable
    lateinit var requestManager: RequestManager
    
    abstract fun layoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        NetworkEvents.observe(viewLifecycleOwner, Observer {
            isConnected = it.state.isConnected
        })


        return inflater.inflate(layoutId(), container, false)
    }





}