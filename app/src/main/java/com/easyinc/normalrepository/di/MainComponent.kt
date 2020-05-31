package com.easyinc.normalrepository.di

import android.app.Application
import com.easyinc.normalrepository.App
import com.easyinc.normalrepository.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBuilderModule::class,
    FragmentBuilderModule::class,
    FirebaseModule::class,
    DataModule::class,
    ThreadingModule::class])
interface MainComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): MainComponent

    }

}