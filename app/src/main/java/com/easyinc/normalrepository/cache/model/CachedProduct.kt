package com.easyinc.normalrepository.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class CachedProduct(
    @PrimaryKey
    val id: String,
    val name: String,
    val warehouseId: String,
    val image: String,
    val price: String
)