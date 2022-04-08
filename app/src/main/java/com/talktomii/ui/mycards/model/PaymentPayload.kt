package com.talktomii.ui.mycards.model

import com.google.gson.annotations.SerializedName

class PaymentPayload (
    @SerializedName("result") var result: Int? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("payload") var payload: PPayload? = PPayload()
)