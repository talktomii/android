package com.talktomii.ui.reportabuse.models

import com.google.gson.annotations.SerializedName

data class AddReport(
    @SerializedName("message") var message: String? = null
)
