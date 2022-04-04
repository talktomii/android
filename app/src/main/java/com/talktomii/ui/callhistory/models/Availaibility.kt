package com.talktomii.ui.callhistory.models

import com.google.gson.annotations.SerializedName


data class Availaibility(

    @SerializedName("startTime") var startTime: String? = null,
    @SerializedName("endTime") var endTime: String? = null,
    @SerializedName("day") var day: String? = null,
    @SerializedName("end") var end: String? = null,
    @SerializedName("isBooked") var isBooked: Boolean? = null,
    @SerializedName("_id") var Id: String? = null

)