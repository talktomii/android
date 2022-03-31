package com.talktomii.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class NotificationModel(
    val id: Int? = 0,
    val user_id: Int? = 0,
    var title: String? = null,
    var message: String? = null,
    val link: String? = null,
    var type: String? = null,
    var order_id: Int? = 0,
    var is_read: Int? = 0,
    var created_at: String? = null,
) : Parcelable

@Parcelize
data class NotificationResponse(
    val current_page: Int? = 0,
    val data: ArrayList<NotificationModel>? = null,
    val first_page_url: String? = null,
    val last_page_url: String? = null,
    val next_page_url: String? = null,
    val prev_page_url: String? = null,
    val from: Int? = 0,
    val last_page: Int? = 0,
    val per_page: Int? = 0,
    val to: Int? = 0,
    val total: Int? = 0,
) : Parcelable