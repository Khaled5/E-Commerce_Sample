package com.easyinc.normalrepository.remote.model

data class WarehouseModel(
    val id: String = "",
    val name: String = "",
    val about: String = "",
    val rate: String = "",
    val image: String = "",
    val location: LocationModel = LocationModel()
)