package com.talktomii.ui.callhistory.models

import com.google.gson.annotations.SerializedName


data class Price(

    @SerializedName("price") var price: Int? = null,
    @SerializedName("time") var time: String? = null,
    @SerializedName("_id") var Id: String? = null

)