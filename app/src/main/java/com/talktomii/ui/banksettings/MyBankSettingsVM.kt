package com.talktomii.ui.banksettings

import androidx.lifecycle.ViewModel
import com.talktomii.recycleradapter.AbstractModel
import javax.inject.Inject

class MyBankSettingsVM @Inject constructor() : ViewModel() {

}

data class BankItemModel(
    val bank_id: String,
    val bank_name: String,
    val bank_type: String,
    val routing_number: String,
    val accounting_number: String
) : AbstractModel()