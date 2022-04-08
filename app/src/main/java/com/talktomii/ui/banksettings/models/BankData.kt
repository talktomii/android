package com.talktomii.ui.banksettings.models

import com.google.gson.annotations.SerializedName

class BankData(
    @SerializedName("result") var result: Int? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("payload") var payload: BankPayload? = BankPayload()
)