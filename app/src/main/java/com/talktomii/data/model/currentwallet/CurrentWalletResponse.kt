package com.talktomii.data.model.currentwallet

import android.widget.TextView
import androidx.databinding.InverseBindingAdapter


data class CurrentWallet(
    val result: Int = 0,
    val payload: PayloadWallet,
    val message: String = ""
)


data class PayloadWallet(val walletData: WalletData)


data class WalletData(
    val uid: String = "",
    val expenses: Int = 0,
    val currentAmount: Int = 0,
    val refills: Int = 0,
    val totalCallAmount: Int = 0,
    val earning: Int = 0
) {
    @InverseBindingAdapter(attribute = "android:textTime")
    fun getText(view: TextView): Int {
        return view.text.toString().toInt()
    }

}



