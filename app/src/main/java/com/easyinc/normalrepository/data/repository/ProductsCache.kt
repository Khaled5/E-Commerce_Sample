package com.easyinc.normalrepository.data.repository

import androidx.paging.DataSource
import com.easyinc.normalrepository.data.model.ProductEntity
import io.reactivex.Completable

interface ProductsCache {

    fun getProducts(): DataSource.Factory<Int, ProductEntity>

    fun addProducts(list: List<ProductEntity>): Completable

    fun deleteOldProducts(list: List<String?>?): Completable

    fun deleteAll(): Completable

    fun deleteProduct(productId: String): Completable

}