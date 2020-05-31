package com.easyinc.normalrepository.remote.mapper

import com.easyinc.normalrepository.data.model.WarehouseEntity
import com.easyinc.normalrepository.remote.model.LocationModel
import com.easyinc.normalrepository.remote.model.WarehouseModel
import javax.inject.Inject

class RemoteWarehouseMapper @Inject constructor(): ModelMapper<WarehouseModel,WarehouseEntity>{
    override fun mapFromModel(model: WarehouseModel): WarehouseEntity {
        return WarehouseEntity(
            model.id,
            model.name,
            model.about,
            model.rate,
            model.image,
            model.location.lat,
            model.location.lang
        )
    }

    override fun mapToModel(entity: WarehouseEntity): WarehouseModel {
        return WarehouseModel(
            entity.id,
            entity.name,
            entity.about,
            entity.rate,
            entity.image,
            LocationModel(entity.lat,entity.lang)
        )
    }
}