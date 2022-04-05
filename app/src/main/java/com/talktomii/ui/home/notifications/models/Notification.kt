package com.talktomii.ui.home.notifications.models

import com.google.gson.annotations.SerializedName

data class Notification(

    @SerializedName("isActive") var isActive: Boolean? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("userId") var userId: String? = null,
    @SerializedName("notificationBy") var notificationBy: NotificationBy? = NotificationBy(),
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("__v") var _v: Int? = null

)