package com.talktomii.ui.mywallet.models

import com.google.gson.annotations.SerializedName


data class WPayload(
    @SerializedName("wallet") var wallet: ArrayList<Wallet> = arrayListOf(),
    @SerializedName("currentAmount") var currentAmount: Int? = null
)