package com.easyinc.normalrepository.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WarehouseEntity(
    val id: String,
    val name: String,
    val about: String,
    val rate: String,
    val image: String,
    val lat: Double,
    val lang: Double
): Parcelable