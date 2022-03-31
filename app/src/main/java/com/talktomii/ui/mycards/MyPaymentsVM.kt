package com.talktomii.ui.mycards

import androidx.lifecycle.ViewModel
import com.talktomii.R
import com.talktomii.recycleradapter.AbstractModel
import com.talktomii.recycleradapter.RecyclerAdapter
import javax.inject.Inject

class MyPaymentsVM @Inject constructor() : ViewModel() {

    val mypaymentAdapter by lazy { RecyclerAdapter<PaymentItemsViewModel>(R.layout.mypayments_item) }

}

data class PaymentItemsViewModel(
    val wallet_id : String,
    val wallet_name: String,
    val wallet_date: String,
    val wallet_price: String
) : AbstractModel()