package com.furniture.ui.mycards

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.furniture.R
import com.furniture.recycleradapter.AbstractModel
import com.furniture.recycleradapter.RecyclerAdapter
import javax.inject.Inject

class MyCardsVM @Inject constructor() : ViewModel() {

    var isUser: ObservableBoolean = ObservableBoolean()

    val mycardsAdapter by lazy { RecyclerAdapter<CardItemsViewModel>(R.layout.mycards_item) }

//    private val cardList = listOf(
//        CardItemsViewModel(card_Number = "53****1234", card_Img = R.drawable.master_card1),
//        CardItemsViewModel(card_Number = "53****1234", card_Img = R.drawable.master_card1),
//        CardItemsViewModel(card_Number = "53****1234", card_Img = R.drawable.visa_card),
//    )

    init {
//        mycardsAdapter.addItems(cardList)
    }

}

data class CardItemsViewModel(
    val id: String,
    val uid: String,
    val card_Number: String,
    val card_Img: Int,
    val expire_date : String,
    val cvv : String,
    val card_holder : String
) : AbstractModel()