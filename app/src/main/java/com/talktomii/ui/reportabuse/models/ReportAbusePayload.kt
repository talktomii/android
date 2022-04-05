package com.talktomii.ui.reportabuse.models

import com.google.gson.annotations.SerializedName

data class ReportAbusePayload(
    @SerializedName("reportAbuse") var reportAbuse: ArrayList<ReportAbuse> = arrayListOf()
)