package com.talktomii.ui.home.notifications.models

import com.google.gson.annotations.SerializedName

data class NotificationStatus(
    @SerializedName("name") var name: String? = null,
    @SerializedName("modificationDate") var modificationDate: String? = null
)