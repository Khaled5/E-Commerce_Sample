package com.easyinc.normalrepository.data.store

import android.net.Uri
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.easyinc.mappractice.common.Logger
import com.easyinc.normalrepository.data.ProductsBoundryCallback
import com.easyinc.normalrepository.data.model.ProductEntity
import com.easyinc.normalrepository.data.model.WarehouseEntity
import com.easyinc.normalrepository.data.repository.ProductsCache
import com.easyinc.normalrepository.data.repository.ProductsDataStore
import com.easyinc.normalrepository.data.repository.ProductsRemote
import com.easyinc.normalrepository.remote.model.WarehouseModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductsDataStoreFactory
@Inject constructor(
    private val productsCache: ProductsCache,
    private val productsRemote: ProductsRemote,
    private val productsBoundryCallback: ProductsBoundryCallback
):
    ProductsDataStore {

    companion object {
        private const val PAGE_SIZE = 12
    }

    override fun getProducts(): Observable<PagedList<ProductEntity>> {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .build()

        return RxPagedListBuilder(productsCache.getProducts(), config)
            .setBoundaryCallback(productsBoundryCallback)
            .buildObservable()
    }

    override fun getProduct(productId: String): Observable<ProductEntity> {
        return productsRemote.getProduct(productId)
    }

    override fun addProduct(product: ProductEntity, productImageUri: Uri): Completable {
        return productsRemote.addProduct(product,productImageUri)
    }

    override fun addProducts(products: List<ProductEntity>): Completable {
        return productsCache.addProducts(products)
    }

    override fun updateProduct(
        product: ProductEntity,
        productImageUri: Uri,
        changedImage: Boolean
    ): Completable {
        return productsRemote.updateProduct(product,productImageUri,changedImage)
    }

    override fun deleteProduct(productId: String): Completable {
        return productsRemote.deleteProduct(productId).andThen(productsCache.deleteProduct(productId))
    }

    override fun getWarehouse(warehouseId: String): Observable<WarehouseEntity> {
        return productsRemote.getWarehouse(warehouseId)
    }
}