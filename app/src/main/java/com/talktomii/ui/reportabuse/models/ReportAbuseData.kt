package com.talktomii.ui.reportabuse.models

import com.google.gson.annotations.SerializedName

data class ReportAbuseData(
    @SerializedName("result") var result: Int? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("payload") var payload: ReportAbusePayload? = ReportAbusePayload()
)