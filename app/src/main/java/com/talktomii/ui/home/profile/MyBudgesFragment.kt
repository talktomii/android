package com.talktomii.ui.home.profile

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talktomii.R
import com.talktomii.data.model.admin1.BadgesItem
import com.talktomii.databinding.FragmentMyBadgesBinding
import com.talktomii.ui.home.HomeViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MyBudgesFragment : DaggerFragment() {

    private lateinit var binding: FragmentMyBadgesBinding
    private var adapterMyBudges: AdapterMyBudges? = null

    @Inject
    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyBadgesBinding.inflate(inflater, container, false)
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.ivcloseBudges.setImageResource(R.drawable.closesheeticon_dark)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.ivcloseBudges.setImageResource(R.drawable.close_sheet_icon)
            }
        }
        return binding.root
    }

    private fun setListener() {
        binding.closeLayout.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        adapterMyBudges = AdapterMyBudges()
        binding.rvMyBudges.adapter = adapterMyBudges
        var badgesArrayList: ArrayList<BadgesItem> = arrayListOf()
        badgesArrayList.add(BadgesItem(0, "Fun",R.drawable.ic_badge_1))
        badgesArrayList.add(BadgesItem(0, "Humor",R.drawable.ic_badge_1))
        badgesArrayList.add(BadgesItem(0, "Smart",R.drawable.ic_badge_1))
        badgesArrayList.add(BadgesItem(0, "Knowledgeable",R.drawable.ic_badge_2))
        badgesArrayList.add(BadgesItem(0, "Kind",R.drawable.ic_badge_1))
        badgesArrayList.add(BadgesItem(0, "Talkative",R.drawable.ic_badge_3))

        if (requireArguments().getSerializable("badges") != null){
            for (i in requireArguments().getSerializable("badges") as ArrayList<BadgesItem>) {
                for (j in badgesArrayList) {
                    if (j._id == i._id) {
                        j.count = i.count
                    }
                }
            }
        }


        adapterMyBudges!!.setItemsList(badgesArrayList)

    }
}