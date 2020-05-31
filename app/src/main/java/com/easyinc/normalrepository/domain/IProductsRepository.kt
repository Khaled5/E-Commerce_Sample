package com.easyinc.normalrepository.domain

import android.net.Uri
import androidx.paging.PagedList
import com.easyinc.normalrepository.data.model.ProductEntity
import com.easyinc.normalrepository.data.model.WarehouseEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface IProductsRepository {

    fun getProducts(): Observable<PagedList<ProductEntity>>

    fun getProduct(productId: String): Observable<ProductEntity>

    fun addProduct(product: ProductEntity, productImageUri: Uri): Completable

    fun updateProduct(product: ProductEntity, productImageUri: Uri, changedImage: Boolean): Completable

    fun deleteProduct(productId: String): Completable

    fun getWarehouse(warehouseId: String): Observable<WarehouseEntity>
}