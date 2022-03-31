package com.talktomii.ui.mywallet.models

import com.google.gson.annotations.SerializedName

class CurrentWalletPaylod(
    @SerializedName("result") var result: Int? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("payload") var payload: CurrentPayload? = CurrentPayload()
)