package com.talktomii.ui.mycards.model

import com.google.gson.annotations.SerializedName


data class Card(

    @SerializedName("object") var object1: String? = null,
    @SerializedName("data") var data: ArrayList<Data> = arrayListOf(),
    @SerializedName("has_more") var hasMore: Boolean? = null,
    @SerializedName("url") var url: String? = null

)