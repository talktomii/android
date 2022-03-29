package com.furniture.ui.mycards.data

import com.furniture.recycleradapter.AbstractModel

data class CardItemsViewModel(
    val id : String,
    val img : Int,
    val last4 : String
) : AbstractModel()