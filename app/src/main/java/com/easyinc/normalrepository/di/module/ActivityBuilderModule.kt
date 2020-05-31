package com.easyinc.normalrepository.di.module

import com.easyinc.normalrepository.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule  {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

}