package com.easyinc.normalrepository.domain.usecase

import com.easyinc.normalrepository.base.usecase.ObservableUseCase
import com.easyinc.normalrepository.base.usecase.PostExecutionThread
import com.easyinc.normalrepository.base.usecase.ThreadExecutor
import com.easyinc.normalrepository.data.model.ProductEntity
import com.easyinc.normalrepository.domain.IProductsRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productRepository: IProductsRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
): ObservableUseCase<ProductEntity, String>(threadExecutor,postExecutionThread) {

    override fun buildUseCaseObservable(params: String?): Observable<ProductEntity> {
        return productRepository.getProduct(params!!)
    }
}