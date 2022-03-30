package com.talktomii.ui.mywallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.databinding.FragmentEarningBinding
import com.talktomii.ui.mywallet.adapters.WalletEarningAdapter
import com.talktomii.ui.mywallet.models.WalletEarningItemModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.android.support.DaggerFragment

class EarningFragment : DaggerFragment(R.layout.fragment_earning){

    private lateinit var binding: FragmentEarningBinding

    val dataList = ArrayList<WalletEarningItemModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEarningBinding.inflate(inflater, container, false)
        recycleview = binding.rvEarningWallet
        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        recycleview.layoutManager = layoutManager
        dataList.add(
            WalletEarningItemModel(
                R.drawable.ic_user,
                "Leslie Alexander",
                "13.02.2022  2:00 PM",
                "$200,00",
                "45min"
            )
        )

        dataList.add(
            WalletEarningItemModel(
                R.drawable.ic_user,
                "Leslie abc",
                "13.02.2022  2:00 PM",
                "$200,00",
                "45min"
            )
        )

        dataList.add(
            WalletEarningItemModel(
                R.drawable.ic_user,
                "Leslie xyz",
                "13.02.2022  2:00 PM",
                "$200,00",
                "45min"
            )
        )
        val adapter = WalletEarningAdapter(dataList)
        recycleview.adapter = adapter
        return binding.root
    }
    companion object{
        lateinit var recycleview: RecyclerView
    }
}