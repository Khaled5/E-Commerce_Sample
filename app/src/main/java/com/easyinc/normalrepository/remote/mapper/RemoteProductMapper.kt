package com.easyinc.normalrepository.remote.mapper

import com.easyinc.normalrepository.data.model.ProductEntity
import com.easyinc.normalrepository.remote.model.ProductModel
import javax.inject.Inject

class RemoteProductMapper @Inject constructor(): ModelMapper<ProductModel,ProductEntity> {
    override fun mapFromModel(model: ProductModel): ProductEntity {
        return ProductEntity(
            model.id,
            model.name,
            model.warehouseId,
            model.image,
            model.price
        )
    }

    override fun mapToModel(entity: ProductEntity): ProductModel {
        return ProductModel(
            entity.id,
            entity.name,
            entity.warehouseId,
            entity.image,
            entity.price
        )
    }
}