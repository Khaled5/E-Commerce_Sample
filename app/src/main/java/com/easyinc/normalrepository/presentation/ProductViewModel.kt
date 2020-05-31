package com.easyinc.normalrepository.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.easyinc.mappractice.common.Logger
import com.easyinc.mappractice.common.Resource
import com.easyinc.normalrepository.base.viewmodel.BaseViewModel
import com.easyinc.normalrepository.data.model.ProductEntity
import com.easyinc.normalrepository.data.model.WarehouseEntity
import com.easyinc.normalrepository.domain.model.UpdatedProduct
import com.easyinc.normalrepository.domain.usecase.*
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getWarehouseUseCase: GetWarehouseUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val addProductUseCase: AddProductUseCase
): BaseViewModel(){

    private val productLiveData = MutableLiveData<Resource<ProductEntity>>()
    private val warehouseLiveData = MutableLiveData<Resource<WarehouseEntity>>()

    private val addProductLiveData = MutableLiveData<Resource<Boolean>>()
    private val updateProductLiveData = MutableLiveData<Resource<Boolean>>()
    private val deleteProductLiveData = MutableLiveData<Resource<Boolean>>()


    fun observeProduct(): LiveData<Resource<ProductEntity>> {
        return productLiveData
    }

    fun observeAddProduct(): LiveData<Resource<Boolean>>{
        return addProductLiveData
    }

    fun observeUpdateProduct(): LiveData<Resource<Boolean>> {
        return updateProductLiveData
    }

    fun observeWarehouse(): LiveData<Resource<WarehouseEntity>> {
        return warehouseLiveData
    }

    fun observeDeleteProduct(): LiveData<Resource<Boolean>>{
        return deleteProductLiveData
    }

    fun getProductAndThenWarehouse(productId: String){
        productLiveData.postValue(Resource.Loading(null))
        compositeDisposable.add(
            getProductUseCase.execute(productId)
                .subscribe(
                    {
                        if (it != null) {
                            productLiveData.postValue(Resource.Success(it))
                            getWarehouse(it.warehouseId)
                        }
                        else {
                            Logger.dt("Null")
                            productLiveData.postValue(Resource.Error("Error has been occurred."))
                        }
                    },{
                        Logger.dt("Error")
                        productLiveData.postValue(Resource.Error(it.localizedMessage!!))
                    }
                )
        )
    }

    fun getWarehouse(warehouseId: String){
        Logger.dt("ware houseIDID:: $warehouseId")
        compositeDisposable.add(
            getWarehouseUseCase.execute(warehouseId)
                .subscribe(
                    {
                            warehouseLiveData.postValue(Resource.Success(it))

                        Logger.dt("ware house:: $it")
                    },{
                        warehouseLiveData.postValue(Resource.Error(it.localizedMessage!!))
                    }
                )
        )
    }

    fun deleteProduct(productId: String){
        compositeDisposable.add(
            deleteProductUseCase.execute(productId)
                .subscribe(
                    {
                        deleteProductLiveData.postValue(Resource.Success(true))
                    },{
                        deleteProductLiveData.postValue(Resource.Error(it.localizedMessage!!))
                    }
                )
        )
    }

    fun updateProduct(updatedProduct: UpdatedProduct){
        compositeDisposable.add(
            updateProductUseCase.execute(updatedProduct)
                .subscribe(
                    {
                        updateProductLiveData.postValue(Resource.Success(true))
                    },{
                        updateProductLiveData.postValue(Resource.Error(it.localizedMessage!!))
                    }
                )
        )
    }

    fun addProduct(addedProduct: UpdatedProduct){
        compositeDisposable.add(
            addProductUseCase.execute(addedProduct)
                .subscribe(
                    {
                        addProductLiveData.postValue(Resource.Success(true))
                    },{
                        addProductLiveData.postValue(Resource.Error(it.localizedMessage!!))
                    }
                )
        )
    }

}