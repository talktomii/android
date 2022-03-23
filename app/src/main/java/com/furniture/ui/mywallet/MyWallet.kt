package com.furniture.ui.mywallet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.furniture.R
import com.furniture.adapter.ViewPagerAdapter
import com.furniture.adapter.ViewPagerWalletAdapter
import com.furniture.databinding.MyWalletBinding
import com.furniture.ui.mywallet.activities.GetPaidActivity
import com.furniture.ui.mywallet.activities.RefillWalletActivity
import com.furniture.utlis.PrefsManager
import com.google.android.material.tabs.TabLayoutMediator
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyWalletBinding.inflate(inflater, container, false)
        binding.vm = viewModels

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
        return binding.root
    }
}