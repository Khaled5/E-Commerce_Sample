package com.easyinc.normalrepository.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductEntity(
    var id: String,
    var name: String,
    var warehouseId: String,
    var image: String,
    var price: String
): Parcelable