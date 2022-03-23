package com.furniture.ui.mycards.data

import com.furniture.recycleradapter.AbstractModel

data class CardItemsViewModel(
    val id: String,
    val uid: String,
    val card_Number: String,
    val card_Img: Int,
    val expire_date : String,
    val cvv : String,
    val card_holder : String
) : AbstractModel()