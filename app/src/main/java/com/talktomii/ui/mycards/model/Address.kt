package com.example.example

import com.google.gson.annotations.SerializedName


data class Address(

    @SerializedName("city") var city: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("line1") var line1: String? = null,
    @SerializedName("line2") var line2: String? = null,
    @SerializedName("postal_code") var postalCode: String? = null,
    @SerializedName("state") var state: String? = null

)