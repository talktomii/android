package com.furniture.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressModel(
    val id: Int? = 0,
    val address: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,
    val block_no: String? = null,
    val zone_title: String? = null,
    val city: String? = null,
//    val zipcode: String? = null,
    var isSelected: Boolean? = false,
) : Parcelable
@Parcelize
data class ZonesData(
    val id: Int? = 0,
    val title: String? = null,
    val value: Int? = 0,
) : Parcelable