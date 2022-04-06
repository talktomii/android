package com.talktomii.ui.mywallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talktomii.R
import com.talktomii.databinding.FragmentRefillBinding
import com.talktomii.ui.mywallet.adapters.WalletRefillAdapter
import com.talktomii.ui.mywallet.models.WalletRefillItemModel
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