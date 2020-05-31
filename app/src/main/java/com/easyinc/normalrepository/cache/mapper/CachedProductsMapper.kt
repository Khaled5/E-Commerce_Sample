package com.easyinc.normalrepository.cache.mapper

import com.easyinc.normalrepository.cache.model.CachedProduct
import com.easyinc.normalrepository.data.model.ProductEntity
import javax.inject.Inject

class CachedProductsMapper @Inject constructor(): CacheMapper<CachedProduct,ProductEntity> {
    override fun mapFromCache(cache: CachedProduct): ProductEntity {
        return ProductEntity(
            cache.id,
            cache.name,
            cache.warehouseId,
            cache.image,
            cache.price
        )
    }

    override fun mapToCache(entity: ProductEntity): CachedProduct {
        return CachedProduct(
            entity.id,
            entity.name,
            entity.warehouseId,
            entity.image,
            entity.price
        )
    }
}