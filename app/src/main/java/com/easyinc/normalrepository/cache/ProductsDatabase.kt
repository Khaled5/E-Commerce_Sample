package com.easyinc.normalrepository.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.easyinc.normalrepository.cache.model.CachedProduct

@Database(
    entities = [CachedProduct::class],
    version = 1,
    exportSchema = false
)
abstract class ProductsDatabase: RoomDatabase() {
    abstract fun productDao(): ProductsDao
}