package com.talktomii.ui.mywallet.models

import com.google.gson.annotations.SerializedName

class CurrentPayload {
    @SerializedName("walletData" ) var walletData : CurrentWalletData? = CurrentWalletData()
}