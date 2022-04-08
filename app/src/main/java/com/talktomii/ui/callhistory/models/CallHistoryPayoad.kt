package com.talktomii.ui.callhistory.models

import com.google.gson.annotations.SerializedName

data class CallHistoryPayoad(
    @SerializedName("callHistory") var callHistory: ArrayList<CallHistory> = arrayListOf()
)