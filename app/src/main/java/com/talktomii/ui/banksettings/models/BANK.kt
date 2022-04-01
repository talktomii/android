package com.talktomii.ui.banksettings.models

import com.google.gson.annotations.SerializedName

data class BANK(

    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("uid") var uid: String? = null,
    @SerializedName("routingNumber") var routingNumber: String? = null,
    @SerializedName("accountNumber") var accountNumber: String? = null,
    @SerializedName("bankType") var bankType: String? = null,
    @SerializedName("holderName") var holderName: String? = null,
    @SerializedName("__v") var _v: Int? = null

)