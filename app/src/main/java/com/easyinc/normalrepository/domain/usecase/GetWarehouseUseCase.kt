package com.easyinc.normalrepository.domain.usecase

import com.easyinc.normalrepository.base.usecase.ObservableUseCase
import com.easyinc.normalrepository.base.usecase.PostExecutionThread
import com.easyinc.normalrepository.base.usecase.ThreadExecutor
import com.easyinc.normalrepository.data.model.WarehouseEntity
import com.easyinc.normalrepository.domain.IProductsRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetWarehouseUseCase @Inject constructor(
    private val productRepository: IProductsRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
): ObservableUseCase<WarehouseEntity, String>(threadExecutor,postExecutionThread) {

    override fun buildUseCaseObservable(params: String?): Observable<WarehouseEntity> {
        return productRepository.getWarehouse(params!!)
    }
}