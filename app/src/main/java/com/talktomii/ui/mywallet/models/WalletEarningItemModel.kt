package com.talktomii.ui.mywallet.models

import com.talktomii.recycleradapter.AbstractModel

data class WalletEarningItemModel(
    val wallet_Img: Int,
    val wallet_holder_name: String,
    val wallet_date: String,
    val wallet_rs : String,
    val wallet_time : String
) : AbstractModel()