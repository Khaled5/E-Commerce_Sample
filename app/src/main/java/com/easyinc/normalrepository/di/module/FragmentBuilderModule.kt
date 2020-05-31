package com.easyinc.normalrepository.di.module

import com.easyinc.normalrepository.ui.ProductsFragment
import com.easyinc.normalrepository.ui.WarehouseFragment
import com.easyinc.normalrepository.ui.AddProductFragment
import com.easyinc.normalrepository.ui.EditProductFragment
import com.easyinc.normalrepository.ui.ProductFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {


    @ContributesAndroidInjector
    abstract fun provideProductsFragment(): ProductsFragment

    @ContributesAndroidInjector
    abstract fun provideProductFragment(): ProductFragment

    @ContributesAndroidInjector
    abstract fun provideEditProductFragment(): EditProductFragment

    @ContributesAndroidInjector
    abstract fun provideAddProductFragment(): AddProductFragment

    @ContributesAndroidInjector
    abstract fun provideWarehouseFragment(): WarehouseFragment

}