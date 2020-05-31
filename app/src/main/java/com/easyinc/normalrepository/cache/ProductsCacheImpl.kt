package com.easyinc.normalrepository.cache

import androidx.paging.DataSource
import com.easyinc.mappractice.common.Logger
import com.easyinc.normalrepository.cache.mapper.CachedProductsMapper
import com.easyinc.normalrepository.cache.model.CachedProduct
import com.easyinc.normalrepository.data.model.ProductEntity
import com.easyinc.normalrepository.data.repository.ProductsCache
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ProductsCacheImpl @Inject constructor(
    private val productsDao: ProductsDao,
    private val mapper: CachedProductsMapper
): ProductsCache {

    override fun getProducts(): DataSource.Factory<Int, ProductEntity> {
        return productsDao.getProducts().map {
            mapper.mapFromCache(it)
        }
    }

    override fun addProducts(list: List<ProductEntity>): Completable {
        return Completable.defer {
            productsDao.addProducts(list.map {
                mapper.mapToCache(it)
            })
            Completable.complete()
        }
    }

    override fun deleteOldProducts(list: List<String?>?): Completable {
        return Completable.defer {
            productsDao.deleteOldProducts(list)
            Completable.complete()
        }
    }

    override fun deleteAll(): Completable {
        return Completable.defer {
            productsDao.deleteAll()
            Completable.complete()
        }
    }

    override fun deleteProduct(productId: String): Completable {
        return Completable.defer {
            productsDao.deleteProduct(productId)
            Completable.complete()
        }
    }
}