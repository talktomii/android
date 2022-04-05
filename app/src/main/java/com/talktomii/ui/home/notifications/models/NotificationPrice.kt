package com.talktomii.ui.home.notifications.models

import com.google.gson.annotations.SerializedName

data class NotificationPrice(
    @SerializedName("price") var price: Int? = null,
    @SerializedName("time") var time: String? = null,
    @SerializedName("_id") var Id: String? = null
)