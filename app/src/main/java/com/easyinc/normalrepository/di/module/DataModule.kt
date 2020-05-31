package com.easyinc.normalrepository.di.module

import com.easyinc.normalrepository.cache.ProductsCacheImpl
import com.easyinc.normalrepository.data.ProductsRepositoryImpl
import com.easyinc.normalrepository.data.repository.ProductsCache
import com.easyinc.normalrepository.data.repository.ProductsDataStore
import com.easyinc.normalrepository.data.repository.ProductsRemote
import com.easyinc.normalrepository.data.store.ProductsDataStoreFactory
import com.easyinc.normalrepository.domain.IProductsRepository
import com.easyinc.normalrepository.remote.ProductsRemoteImpl
import com.easyinc.normalrepository.remote.firebase.FirebaseProductsService
import com.easyinc.normalrepository.remote.firebase.ProductsService
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun provideProductsService(productsService: ProductsService): FirebaseProductsService

    @Binds
    abstract fun provideProductsCache(productsCacheImpl: ProductsCacheImpl): ProductsCache

    @Binds
    abstract fun provideProductsRemote(productsRemoteImpl: ProductsRemoteImpl): ProductsRemote

    @Binds
    abstract fun provideProductsDataStoreFactory(productsDataStoreFactory: ProductsDataStoreFactory): ProductsDataStore

    @Binds
    abstract fun provideProductsRepository(productsRepositoryImpl: ProductsRepositoryImpl): IProductsRepository


}