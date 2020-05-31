package com.easyinc.normalrepository.domain.usecase

import com.easyinc.normalrepository.base.usecase.CompletableUseCase
import com.easyinc.normalrepository.base.usecase.PostExecutionThread
import com.easyinc.normalrepository.base.usecase.ThreadExecutor
import com.easyinc.normalrepository.domain.IProductsRepository
import io.reactivex.Completable
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val productRepository: IProductsRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
): CompletableUseCase<String>(threadExecutor,postExecutionThread) {

    override fun buildUseCaseObservable(params: String?): Completable {
        return productRepository.deleteProduct(params!!)
    }
}