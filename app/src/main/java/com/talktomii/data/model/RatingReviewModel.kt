package com.talktomii.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RatingReviewModel(
    val id: Int? = 0,
    val user_id: Int? = 0,
    val order_id: Int? = 0,
    val vendor_id: Int? = 0,
    val rating: Float? = 0F,
    val review: String? = null,
    val created_at: String? = null,
) : Parcelable