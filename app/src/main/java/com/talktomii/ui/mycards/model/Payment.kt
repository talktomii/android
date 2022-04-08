package com.talktomii.ui.mycards.model

import com.google.gson.annotations.SerializedName

data class Payment(

    @SerializedName("apid") var apid: String? = null,
    @SerializedName("paymentId") var paymentId: String? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("uid") var uid: String? = null,
    @SerializedName("amount") var amount: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("__v") var _v: Int? = null

)