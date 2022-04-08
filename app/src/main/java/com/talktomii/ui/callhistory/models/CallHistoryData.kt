package com.talktomii.ui.callhistory.models

import com.google.gson.annotations.SerializedName

data class CallHistoryData(
    @SerializedName("result") var result: Int? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("payload") var payload: CallHistoryPayoad? = CallHistoryPayoad()
)