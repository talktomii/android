package com.furniture.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageModel(
    val id: Int? = 0,
    val url: String? = null,
    val cart_id: Int? = 0,
    val full_image: String? = null,
) : Parcelable