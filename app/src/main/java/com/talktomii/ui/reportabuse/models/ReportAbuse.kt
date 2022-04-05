package com.talktomii.ui.reportabuse.models

import com.google.gson.annotations.SerializedName

data class ReportAbuse(
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("__v") var _v: Int? = null
)