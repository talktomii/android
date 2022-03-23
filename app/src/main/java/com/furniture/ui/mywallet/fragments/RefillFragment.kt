package com.furniture.ui.mywallet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furniture.R
import com.furniture.adapter.MyPaymentAdapter
import com.furniture.databinding.FragmentEarningBinding
import com.furniture.databinding.FragmentPaymentBinding
import com.furniture.databinding.FragmentRefillBinding
import com.furniture.ui.mycards.PaymentItemsViewModel
import com.furniture.ui.mywallet.adapters.WalletRefillAdapter
import com.furniture.ui.mywallet.models.WalletRefillItemModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.android.support.DaggerFragment

class RefillFragment : DaggerFragment(R.layout.fragment_refill){

    private lateinit var binding: FragmentRefillBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRefillBinding.inflate(inflater, container, false)
        val recyclerview = binding.rvRefillWallet
        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        recyclerview.layoutManager = layoutManager

        val dataList = ArrayList<WalletRefillItemModel>()
        dataList.add(
            WalletRefillItemModel(
                wallet_name = "Wallet refill",
                wallet_date = "13.02.2022  2:00 PM",
                wallet_price = "-$200,00"
            )
        )
        dataList.add(
            WalletRefillItemModel(
                wallet_name = "Wallet refill1",
                wallet_date = "13.02.2022  2:00 PM",
                wallet_price = "-$400,00"
            )
        )
        dataList.add(
            WalletRefillItemModel(
                wallet_name = "Wallet refill2",
                wallet_date = "13.02.2022  3:00 PM",
                wallet_price = "-$200,00"
            )
        )
        dataList.add(
            WalletRefillItemModel(
                wallet_name = "Wallet refill3",
                wallet_date = "13.02.2022  5:00 PM",
                wallet_price = "-$600,00"
            )
        )
        val adapter = WalletRefillAdapter(dataList)
        recyclerview.adapter = adapter
        return binding.root
    }

}