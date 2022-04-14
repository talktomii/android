package com.talktomii.ui.mywallet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import com.talktomii.R
import com.talktomii.adapter.ViewPagerWalletAdapter
import com.talktomii.databinding.MyWalletBinding
import com.talktomii.ui.mywallet.activities.GetPaidActivity
import com.talktomii.ui.mywallet.activities.RefillWalletActivity
import com.talktomii.utlis.PrefsManager
import com.google.android.material.tabs.TabLayoutMediator
import com.talktomii.adapter.ViewPagerWalletAdapterUser
import com.talktomii.ui.coupon.CouponActivity
import com.talktomii.ui.mycards.data.MyCardsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

val tabData = arrayOf(
    "Earnings",
    "Expenses"
)

val tabData1 = arrayOf(
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



        if(prefsManager.getString(PrefsManager.PREF_ROLE,"") == "user"){
            val adapter = ViewPagerWalletAdapterUser(requireFragmentManager(), lifecycle)
            viewPager.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabData1[position]
            }.attach()
        }else{
            val adapter = ViewPagerWalletAdapter(requireFragmentManager(), lifecycle)
            viewPager.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabData[position]
            }.attach()
        }

        dataModel.getCurrentAmount()

        binding.refillWalletLayout.setOnClickListener {
            val intent  = Intent(context,RefillWalletActivity::class.java)
            startActivity(intent)
        }
        val total : String = binding.myWalletTotalAmount.text.toString()
        Log.d("totalamount : ",total)
        binding.getPaidLayout.setOnClickListener {
            val intent  = Intent(context,GetPaidActivity::class.java)
            startActivity(intent)
        }
        binding.addCouponLayout.setOnClickListener {
            val intent  = Intent(context,CouponActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
    companion object{
        var totalWalletAmount : TextView ?= null
    }
}