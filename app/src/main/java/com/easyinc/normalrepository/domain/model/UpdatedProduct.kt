package com.easyinc.normalrepository.domain.model

import android.net.Uri
import com.easyinc.normalrepository.data.model.ProductEntity

data class UpdatedProduct(
    val product: ProductEntity,
    val productImageUri: Uri,
    val changedImage: Boolean = false
)