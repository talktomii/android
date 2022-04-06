package com.talktomii.utlis

import androidx.fragment.app.FragmentStatePagerAdapter

class GeneralFragmentPagerAdapter(fragmentManager: androidx.fragment.app.FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    private val fragments = ArrayList<androidx.fragment.app.Fragment>()
    private val titles = ArrayList<String>()
    private val orderIdList = ArrayList<Int>()

    fun addFragment(fragment: androidx.fragment.app.Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title)
    }

    fun addFragment(fragment: androidx.fragment.app.Fragment, title: String, orderId: Int) {
        fragments.add(fragment)
        orderIdList.add(orderId)
        titles.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence = titles[position]

    override fun getItem(position: Int): androidx.fragment.app.Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getItemPosition(`object`: Any): Int {
        return androidx.viewpager.widget.PagerAdapter.POSITION_NONE

    }

    fun getFragment(orderId : Int?): androidx.fragment.app.Fragment {
        return fragments[orderIdList.indexOf(orderIdList.find { it == orderId }?:0)]
    }

    fun removeFragment(orderId : Int?) {
        val item =orderIdList.find { it == orderId}
        val itemIndex =orderIdList.indexOf(item)
        if(itemIndex != -1) {
            fragments.removeAt(itemIndex)
            orderIdList.removeAt(itemIndex)
        }
    }
}