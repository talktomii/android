package com.furniture.ui.mycards

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.furniture.R
import com.furniture.adapter.ViewPagerAdapter
import com.furniture.databinding.MyCardsBinding
import com.furniture.utlis.PrefsManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import com.furniture.ui.mycards.activities.MyCardsActivity
import com.google.android.material.tabs.TabLayoutMediator

val tabData = arrayOf(
    "My Cards",
    "My Payments"
)

class MyCards : DaggerFragment(R.layout.my_cards) {

    private lateinit var binding: MyCardsBinding
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

        binding.tvAddCard.setOnClickListener {
            val intent = Intent(context, MyCardsActivity::class.java)
            startActivity(intent)
        }

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(requireFragmentManager(), lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabData[position]
        }.attach()

    }
}