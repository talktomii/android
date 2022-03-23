package com.furniture.ui.mywallet.models

import com.furniture.recycleradapter.AbstractModel


data class WalletRefillItemModel(
    val wallet_name: String,
    val wallet_date: String,
    val wallet_price: String
) : AbstractModel()