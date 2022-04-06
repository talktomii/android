package com.example.example

import com.google.gson.annotations.SerializedName


data class Checks(

    @SerializedName("address_line1_check") var addressLine1Check: String? = null,
    @SerializedName("address_postal_code_check") var addressPostalCodeCheck: String? = null,
    @SerializedName("cvc_check") var cvcCheck: String? = null

)