package com.furniture.ui.mycards

import androidx.lifecycle.ViewModel
import com.furniture.R
import com.furniture.recycleradapter.AbstractModel
import com.furniture.recycleradapter.RecyclerAdapter
import javax.inject.Inject

class MyPaymentsVM @Inject constructor() : ViewModel() {

    val mypaymentAdapter by lazy { RecyclerAdapter<PaymentItemsViewModel>(R.layout.mypayments_item) }

    private val walletList = listOf(
        PaymentItemsViewModel(
            wallet_name = "Wallet refill",
            wallet_date = "13.02.2022  2:00 PM",
            wallet_price = "-$200,00"
        ),
        PaymentItemsViewModel(
            wallet_name = "Wallet refill1",
            wallet_date = "13.02.2022  2:00 PM",
            wallet_price = "-$400,00"
        ),
        PaymentItemsViewModel(
            wallet_name = "Wallet refill2",
            wallet_date = "13.02.2022  3:00 PM",
            wallet_price = "-$200,00"
        ),
        PaymentItemsViewModel(
            wallet_name = "Wallet refill3",
            wallet_date = "13.02.2022  5:00 PM",
            wallet_price = "-$600,00"
        )

    )

    init {
        mypaymentAdapter.addItems(walletList)
    }

}

data class PaymentItemsViewModel(
    val wallet_name: String,
    val wallet_date: String,
    val wallet_price: String
) : AbstractModel()