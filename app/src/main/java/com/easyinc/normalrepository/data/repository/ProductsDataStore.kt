package com.easyinc.normalrepository.data.repository

import android.net.Uri
import androidx.paging.PagedList
import com.easyinc.normalrepository.data.model.ProductEntity
import com.easyinc.normalrepository.data.model.WarehouseEntity
import com.easyinc.normalrepository.remote.model.ProductModel
import com.easyinc.normalrepository.remote.model.WarehouseModel
import io.reactivex.Completable
import io.reactivex.Observable

interface ProductsDataStore {

    fun getProducts(): Observable<PagedList<ProductEntity>>

    fun getProduct(productId: String): Observable<ProductEntity>

    fun addProduct(product: ProductEntity, productImageUri: Uri): Completable

    fun addProducts(products: List<ProductEntity>): Completable

    fun updateProduct(product: ProductEntity, productImageUri: Uri, changedImage: Boolean): Completable

    fun deleteProduct(productId: String): Completable

    fun getWarehouse(warehouseId: String): Observable<WarehouseEntity>

}