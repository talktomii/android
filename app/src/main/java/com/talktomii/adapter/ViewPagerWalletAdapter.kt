package com.talktomii.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.talktomii.ui.mycards.fragments.CardFragment
import com.talktomii.ui.mywallet.fragments.EarningFragment
import com.talktomii.ui.mywallet.fragments.ExpenseFragment
import com.talktomii.ui.mywallet.fragments.RefillFragment

private const val NUM_TABS = 2

class ViewPagerWalletAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return EarningFragment()
            1 -> return ExpenseFragment()
        }
        return EarningFragment()
    }
}


private const val NUM_TABS_USER = 2

class ViewPagerWalletAdapterUser(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS_USER
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return RefillFragment()
            1 -> return ExpenseFragment()
        }
        return RefillFragment()
    }
}