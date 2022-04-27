package com.talktomii.ui.mycards

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.talktomii.R
import com.talktomii.adapter.ViewPagerAdapter
import com.talktomii.databinding.MyCardsBinding
import com.talktomii.utlis.PrefsManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import com.talktomii.ui.mycards.activities.MyCardsActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.talktomii.databinding.MyCardsDarkBinding

val tabData = arrayOf(
    "My Cards",
    "My Payments"
)

class MyCards : DaggerFragment() {

    private val viewModels by viewModels<MyCardsVM>()

    @Inject
    lateinit var prefsManager: PrefsManager

    @Inject
    lateinit var viewModel: MyCardsVM
    lateinit var addCard : TextView
    lateinit var viewPager : ViewPager2
    lateinit var tabLayout : TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root : View ? = null
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                root = inflater.inflate(R.layout.my_cards_dark, container, false);
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                root = inflater.inflate(R.layout.my_cards, container, false);

            }
        }
        addCard = root!!.findViewById(R.id.tvAddCard)
        viewPager = root.findViewById(R.id.viewPager)
        tabLayout = root.findViewById(R.id.tabLayout)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addCard.setOnClickListener {
            val intent = Intent(context, MyCardsActivity::class.java)
            startActivity(intent)
        }

        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabData[position]
        }.attach()

    }
}