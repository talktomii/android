package com.talktomii.ui.mywallet

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.talktomii.R
import com.talktomii.adapter.ViewPagerWalletAdapter
import com.talktomii.adapter.ViewPagerWalletAdapterUser
import com.talktomii.databinding.MyWalletBinding
import com.talktomii.ui.coupon.CouponActivity
import com.talktomii.ui.mycards.data.MyCardsViewModel
import com.talktomii.ui.mywallet.activities.GetPaidActivity
import com.talktomii.ui.mywallet.activities.RefillWalletActivity
import com.talktomii.utlis.PrefsManager
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

class MyWallet : DaggerFragment(R.layout.my_wallet) {
    private lateinit var binding: MyWalletBinding
    private val viewModels by viewModels<MyWalletVM>()

    @Inject
    lateinit var prefsManager: PrefsManager

    @Inject
    lateinit var viewModel: MyWalletVM

    @Inject
    lateinit var dataModel: MyCardsViewModel

    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout
    lateinit var refillLayout: LinearLayout
    lateinit var getPaidLayout: LinearLayout
    lateinit var addCoupnLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root: View? = null
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                root = inflater.inflate(R.layout.my_wallet_dark, container, false);
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                root = inflater.inflate(R.layout.my_wallet, container, false);

            }
        }
        viewPager = root!!.findViewById(R.id.walletViewPager)
        tabLayout = root.findViewById(R.id.walletTabLayout)
        totalWalletAmount = root.findViewById(R.id.myWalletTotalAmount)
        refillLayout = root.findViewById(R.id.refillWalletLayout)
        getPaidLayout = root.findViewById(R.id.getPaidLayout)
        addCoupnLayout = root.findViewById(R.id.addCouponLayout)

        if (prefsManager.getString(PrefsManager.PREF_ROLE, "") == "user") {
            val adapter = ViewPagerWalletAdapterUser(childFragmentManager, lifecycle)
            viewPager.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabData1[position]
            }.attach()
        } else {
            val adapter = ViewPagerWalletAdapter(childFragmentManager, lifecycle)
            viewPager.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabData[position]
            }.attach()
        }

        dataModel.getCurrentAmount()

        refillLayout.setOnClickListener {
            val intent = Intent(context, RefillWalletActivity::class.java)
            startActivity(intent)
        }
        getPaidLayout.setOnClickListener {
            val intent = Intent(context, GetPaidActivity::class.java)
            startActivity(intent)
        }
        addCoupnLayout.setOnClickListener {
            val intent = Intent(context, CouponActivity::class.java)
            startActivity(intent)
        }
        return root

    }

    companion object {
        var totalWalletAmount: TextView? = null
    }
}