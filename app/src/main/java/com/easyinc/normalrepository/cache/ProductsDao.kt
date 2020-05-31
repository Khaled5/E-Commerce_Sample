package com.easyinc.normalrepository.cache

import androidx.paging.DataSource
import androidx.room.*
import com.easyinc.normalrepository.cache.model.CachedProduct

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProducts(list: List<CachedProduct>)

    @Query("SELECT * FROM products")
    fun getProducts(): DataSource.Factory<Int,CachedProduct>

    @Query("DELETE FROM products WHERE products.id NOT IN(:lstIDProducts)")
    fun deleteOldProducts(lstIDProducts: List<String?>?)

    @Query("DELETE FROM products")
    fun deleteAll()

    /*@Delete
    fun deleteProduct(product: CachedProduct)*/

    @Query("DELETE FROM products WHERE id = :productId")
    fun deleteProduct(productId: String)

}