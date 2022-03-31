package com.example.example

import com.talktomii.ui.mycards.model.NewCards
import com.google.gson.annotations.SerializedName


data class Data(

    @SerializedName("id") var id: String? = null,
    @SerializedName("object") var object1: String? = null,
    @SerializedName("billing_details") var billingDetails: BillingDetails? = BillingDetails(),
    @SerializedName("card") var card: NewCards? = NewCards(),
    @SerializedName("created") var created: Int? = null,
    @SerializedName("customer") var customer: String? = null,
    @SerializedName("livemode") var livemode: Boolean? = null,
    @SerializedName("type") var type: String? = null

)