package com.talktomii.ui.mywallet.models

import com.google.gson.annotations.SerializedName

class CurrentWalletData (
    @SerializedName("uid")
    var uid: String? = null,
    @SerializedName("currentAmount")
    var currentAmount: Int? = null
)