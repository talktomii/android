package com.talktomii.ui.banksettings.models

import com.google.gson.annotations.SerializedName

data class BANK(

    @SerializedName("data") var data: ArrayList<Data> = arrayListOf(),
    @SerializedName("has_more") var hasMore: Boolean? = null,
    @SerializedName("url") var url: String? = null

)

data class Data(

    @SerializedName("id") var id: String? = null,
    @SerializedName("account_holder_name") var accountHolderName: String? = null,
    @SerializedName("account_holder_type") var accountHolderType: String? = null,
    @SerializedName("account_type") var accountType: String? = null,
    @SerializedName("bank_name") var bankName: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("customer") var customer: String? = null,
    @SerializedName("fingerprint") var fingerprint: String? = null,
    @SerializedName("last4") var last4: String? = null,
    @SerializedName("routing_number") var routingNumber: String? = null,
    @SerializedName("status") var status: String? = null

)