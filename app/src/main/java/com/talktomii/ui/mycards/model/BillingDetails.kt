package com.talktomii.ui.mycards.model

import com.google.gson.annotations.SerializedName


data class BillingDetails(

    @SerializedName("address") var address: Address? = Address(),
    @SerializedName("email") var email: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("phone") var phone: String? = null

)