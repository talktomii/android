package com.furniture.ui.mywallet.models

import com.furniture.recycleradapter.AbstractModel

data class WalletEarningItemModel(
    val wallet_Img: Int,
    val wallet_holder_name: String,
    val wallet_date: String,
    val wallet_rs : String,
    val wallet_time : String
) : AbstractModel()