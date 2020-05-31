package com.easyinc.normalrepository.data

import android.net.Uri
import androidx.paging.PagedList
import com.easyinc.normalrepository.data.model.ProductEntity
import com.easyinc.normalrepository.data.model.WarehouseEntity
import com.easyinc.normalrepository.data.repository.ProductsDataStore
import com.easyinc.normalrepository.domain.IProductsRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsDataStore: ProductsDataStore
): IProductsRepository {


    override fun getProducts(): Observable<PagedList<ProductEntity>> {
        return productsDataStore.getProducts()
    }

    override fun getProduct(productId: String): Observable<ProductEntity> {
        return productsDataStore.getProduct(productId)
    }

    override fun addProduct(product: ProductEntity, productImageUri: Uri): Completable {
        return productsDataStore.addProduct(product,productImageUri)
    }

    override fun updateProduct(
        product: ProductEntity,
        productImageUri: Uri,
        changedImage: Boolean
    ): Completable {
        return productsDataStore.updateProduct(product,productImageUri,changedImage)
    }

    override fun deleteProduct(productId: String): Completable {
        return productsDataStore.deleteProduct(productId)
    }

    override fun getWarehouse(warehouseId: String): Observable<WarehouseEntity> {
        return productsDataStore.getWarehouse(warehouseId)
    }
}