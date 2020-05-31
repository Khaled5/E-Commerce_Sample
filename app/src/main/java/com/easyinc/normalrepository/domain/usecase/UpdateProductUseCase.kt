package com.easyinc.normalrepository.domain.usecase

import com.easyinc.normalrepository.base.usecase.CompletableUseCase
import com.easyinc.normalrepository.base.usecase.PostExecutionThread
import com.easyinc.normalrepository.base.usecase.ThreadExecutor
import com.easyinc.normalrepository.domain.IProductsRepository
import com.easyinc.normalrepository.domain.model.UpdatedProduct
import io.reactivex.Completable
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val productRepository: IProductsRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
): CompletableUseCase<UpdatedProduct>(threadExecutor,postExecutionThread) {

    override fun buildUseCaseObservable(params: UpdatedProduct?): Completable {
        return productRepository.updateProduct(params!!.product,params.productImageUri,params.changedImage)
    }
}