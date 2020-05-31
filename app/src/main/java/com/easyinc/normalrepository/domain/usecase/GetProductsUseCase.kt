package com.easyinc.normalrepository.domain.usecase

import androidx.paging.PagedList
import com.easyinc.normalrepository.base.usecase.ObservableUseCase
import com.easyinc.normalrepository.base.usecase.PostExecutionThread
import com.easyinc.normalrepository.base.usecase.ThreadExecutor
import com.easyinc.normalrepository.data.model.ProductEntity
import com.easyinc.normalrepository.domain.IProductsRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: IProductsRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
): ObservableUseCase<PagedList<ProductEntity>, Unit>(threadExecutor,postExecutionThread) {

    override fun buildUseCaseObservable(params: Unit?): Observable<PagedList<ProductEntity>> {
        return productRepository.getProducts()
    }
}