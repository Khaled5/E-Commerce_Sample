package com.easyinc.normalrepository.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.easyinc.mappractice.common.Resource
import com.easyinc.normalrepository.base.viewmodel.BaseViewModel
import com.easyinc.normalrepository.data.model.ProductEntity
import com.easyinc.normalrepository.domain.usecase.GetProductsUseCase
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
): BaseViewModel() {

    private val productsLiveData = MutableLiveData<Resource<PagedList<ProductEntity>>>()

    fun observeProducts(): LiveData<Resource<PagedList<ProductEntity>>>{
        return productsLiveData
    }

    fun getProducts(){

        productsLiveData.postValue(Resource.Loading(null))

        compositeDisposable.add(
            getProductsUseCase.execute()
                .subscribe(
                    {
                        productsLiveData.postValue(Resource.Success(it))
                    },{
                        productsLiveData.postValue(Resource.Error(it.localizedMessage!!))
                    }
                )
        )
    }

}