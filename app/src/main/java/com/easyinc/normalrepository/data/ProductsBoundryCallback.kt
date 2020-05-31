package com.easyinc.normalrepository.data

import androidx.paging.PagedList
import com.easyinc.mappractice.common.Logger
import com.easyinc.normalrepository.data.model.ProductEntity
import com.easyinc.normalrepository.data.repository.ProductsCache
import com.easyinc.normalrepository.data.repository.ProductsRemote
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductsBoundryCallback @Inject constructor(
    private val productsCache: ProductsCache,
    private val productsRemote: ProductsRemote
): PagedList.BoundaryCallback<ProductEntity>() {

    private var lastIndex: String = ""
    private var disposable: Disposable? = null

    override fun onZeroItemsLoaded() {
        Logger.dt("onZeroItemsLoaded")
        loadData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: ProductEntity) {
        Logger.dt("onItemAtEndLoaded")
        loadData()
    }

    private fun loadData() {
       disposable = productsRemote.getProducts(lastIndex).distinctUntilChanged()
            .flatMap {

                if (lastIndex.equals("")) {
                    productsCache.deleteAll().subscribeOn(Schedulers.io())
                        .andThen(Observable.just(it))
                }

                productsCache.addProducts(it).subscribeOn(Schedulers.io()).andThen(Observable.just(it))

            }
            .map {
                lastIndex = it.last().id
            }
            .ignoreElements()
            .subscribe(
                {
                    Logger.dt("Success")
                },
                {
                    Logger.dt("Error: ${it.message}")
                }
            )
    }

}