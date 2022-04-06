package com.furniture.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class CustomerReviewModel(
    val id: Int? = null,
    val user_id: Int? = null,
    val review: String? = null,
    val order_id: Int? = null,
    val rating: Int,
    val created_at: String? = null,
    val updated_at: String? = null,
    val user: UserModel? = null
)
@Parcelize
data class UserModel(
    val id: Int? = null,
    val name: String? = null,
    val avg_rating_received: Float? = 0F,
    val phone_no: String? = null,
    val full_image: String? = null,
    val image: String? = null
):Parcelable
