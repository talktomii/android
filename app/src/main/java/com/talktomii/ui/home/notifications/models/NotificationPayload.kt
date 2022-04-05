package com.talktomii.ui.home.notifications.models

import com.google.gson.annotations.SerializedName

data class NotificationPayload(
    @SerializedName("notification") var notification: ArrayList<Notification> = arrayListOf()
)