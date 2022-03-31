package com.talktomii.ui.mycards.data

import com.talktomii.recycleradapter.AbstractModel

data class CardItemsViewModel(
    val id : String,
    val img : Int,
    val last4 : String
) : AbstractModel()