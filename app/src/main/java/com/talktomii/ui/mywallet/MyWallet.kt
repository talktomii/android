package com.talktomii.ui.mywallet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.talktomii.R
import com.talktomii.adapter.ViewPagerWalletAdapter
import com.talktomii.databinding.MyWalletBinding
import com.talktomii.ui.mywallet.activities.GetPaidActivity
import com.talktomii.ui.mywallet.activities.RefillWalletActivity
import com.talktomii.utlis.PrefsManager
import com.google.android.material.tabs.TabLayoutMediator
import com.talktomii.ui.mycards.data.MyCardsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

val tabData = arrayOf(
    "Earnings",
    "Refills",
    "Expenses"
)

class MyWallet : DaggerFragment(R.layout.my_wallet){
    private lateinit var binding: MyWalletBinding
    private val viewModels by viewModels<MyWalletVM>()

    @Inject
    lateinit var prefsManager: PrefsManager

    @Inject
    lateinit var viewModel: MyWalletVM

    @Inject
    lateinit var dataModel: MyCardsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyWalletBinding.inflate(inflater, container, false)
        binding.vm = viewModels

        totalWalletAmount  = binding.myWalletTotalAmount

        val viewPager = binding.walletViewPager
        val tabLayout = binding.walletTabLayout

        val adapter = ViewPagerWalletAdapter(requireFragmentManager(), lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabData[position]
        }.attach()

        binding.refillLayout.setOnClickListener {
            val intent = Intent(context,RefillWalletActivity::class.java)
            startActivity(intent)
        }

        binding.getPaidLayout.setOnClickListener {
            val intent = Intent(context,GetPaidActivity::class.java)
            startActivity(intent)
        }

        dataModel.getCurrentAmount()
        return binding.root
    }
    companion object{
        var totalWalletAmount : TextView ?= null
    }
}