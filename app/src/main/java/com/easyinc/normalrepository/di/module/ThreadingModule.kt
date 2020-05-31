package com.easyinc.normalrepository.di.module

import com.easyinc.normalrepository.base.usecase.JobExecutor
import com.easyinc.normalrepository.base.usecase.PostExecutionThread
import com.easyinc.normalrepository.base.usecase.ThreadExecutor
import com.easyinc.normalrepository.base.usecase.UiThread
import dagger.Binds
import dagger.Module

@Module
abstract class ThreadingModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor

}