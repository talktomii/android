package com.furniture.ui.mycards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.furniture.R
import com.furniture.adapter.MyAdapter
import com.furniture.databinding.MyCardsBinding
import com.furniture.databinding.TellUsMoreBinding
import com.furniture.ui.mycards.fragments.CardFragment
import com.furniture.ui.mycards.fragments.PaymentFragment
import com.furniture.ui.tellusmore.TellUsMoreVM
import com.furniture.utlis.PrefsManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import androidx.viewpager.widget.PagerAdapter




class MyCards : DaggerFragment(R.layout.my_cards) {

    private lateinit var binding : MyCardsBinding
    private val viewModels by viewModels<MyCardsVM>()

    @Inject
    lateinit var prefsManager: PrefsManager

    @Inject
    lateinit var viewModel: MyCardsVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyCardsBinding.inflate(inflater, container, false)
        binding.vm = viewModels
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("My Cards"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("My Payments"))
        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter: MyAdapter = MyAdapter(fragmentManager, binding.tabLayout.getTabCount())
        binding.viewPager.setAdapter(adapter)
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.id
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        val recyclerview = binding.rvCards
        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.COLUMN
        recyclerview.layoutManager = layoutManager
    }
}