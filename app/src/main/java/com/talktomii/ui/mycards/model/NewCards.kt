package com.talktomii.ui.mycards.model

import com.google.gson.annotations.SerializedName

data class NewCards(

    @SerializedName("last4") var last4: String? = null,
    @SerializedName("brand") var brand: String? = null
)