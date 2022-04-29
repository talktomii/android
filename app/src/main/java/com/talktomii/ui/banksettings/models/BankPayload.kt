package com.talktomii.ui.banksettings.models

import com.google.gson.annotations.SerializedName

class BankPayload(
    @SerializedName("BANK" ) var BANK : BANK? = BANK()
)