package com.talktomii.ui.home.notifications.models

import com.google.gson.annotations.SerializedName

data class NotificationData(
    @SerializedName("result") var result: Int? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("payload") var payload: NotificationPayload? = NotificationPayload()
)