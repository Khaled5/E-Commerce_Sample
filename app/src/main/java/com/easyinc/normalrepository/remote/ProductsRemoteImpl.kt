package com.easyinc.normalrepository.remote

import android.net.Uri
import com.easyinc.mappractice.common.Logger
import com.easyinc.normalrepository.data.model.ProductEntity
import com.easyinc.normalrepository.data.model.WarehouseEntity
import com.easyinc.normalrepository.data.repository.ProductsRemote
import com.easyinc.normalrepository.remote.firebase.FirebaseProductsService
import com.easyinc.normalrepository.remote.mapper.RemoteProductMapper
import com.easyinc.normalrepository.remote.mapper.RemoteWarehouseMapper
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ProductsRemoteImpl @Inject constructor(
    private val firebaseProductsService: FirebaseProductsService,
    private val productMapper: RemoteProductMapper,
    private val warehouseMapper: RemoteWarehouseMapper
): ProductsRemote {

    override fun getProducts(key: String): Observable<List<ProductEntity>> {
        return firebaseProductsService.getProducts(key).map {list ->
            list.map {
                productMapper.mapFromModel(it)
            }
        }
    }

    override fun getProduct(productId: String): Observable<ProductEntity> {
        return firebaseProductsService.getProduct(productId).map {
            productMapper.mapFromModel(it)
        }
    }

    override fun addProduct(product: ProductEntity, productImageUri: Uri): Completable {
        return firebaseProductsService.addProduct(productMapper.mapToModel(product),
            productImageUri)
    }

    override fun updateProduct(product: ProductEntity, productImageUri: Uri, changedImage: Boolean): Completable {
       return firebaseProductsService.updateProduct(productMapper.mapToModel(product),
           productImageUri,changedImage)
    }

    override fun deleteProduct(productId: String): Completable {
        return firebaseProductsService.deleteProduct(productId)
    }

    override fun getWarehouse(warehouseId: String): Observable<WarehouseEntity> {
        return firebaseProductsService.getWarehouse(warehouseId).map {
            warehouseMapper.mapFromModel(it)
        }
    }
}