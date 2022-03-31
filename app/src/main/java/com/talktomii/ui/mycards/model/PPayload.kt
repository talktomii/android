package com.talktomii.ui.mycards.model

import com.google.gson.annotations.SerializedName

class PPayload(
    @SerializedName("wallet") var wallet: ArrayList<Payment> = arrayListOf(),
    @SerializedName("currentAmount") var currentAmount: Int? = null
)