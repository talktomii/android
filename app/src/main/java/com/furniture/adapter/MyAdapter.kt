package com.furniture.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.furniture.ui.mycards.fragments.CardFragment
import com.furniture.ui.mycards.fragments.PaymentFragment

@Suppress("DEPRECATION")
class MyAdapter(fm: FragmentManager?, numTabs: Int) :
    FragmentStatePagerAdapter(fm!!) {
    var mNumOfTabs: Int
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CardFragment()
            }
            1 -> {
                PaymentFragment()
            }
            else -> CardFragment()
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }

    init {
        mNumOfTabs = numTabs
    }
}
