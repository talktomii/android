package com.talktomii.ui.mywallet.models

import com.talktomii.recycleradapter.AbstractModel


data class WalletRefillItemModel(
    val wallet_name: String,
    val wallet_date: String,
    val wallet_price: String
) : AbstractModel()