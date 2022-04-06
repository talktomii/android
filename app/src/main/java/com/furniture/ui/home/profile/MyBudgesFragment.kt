package com.furniture.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
import com.furniture.databinding.FragmentInfluencerProfileBinding
import com.furniture.databinding.FragmentMyBadgesBinding
import com.furniture.ui.home.HomeViewModel
import dagger.android.support.DaggerFragment
import devs.mulham.horizontalcalendar.HorizontalCalendar
import javax.inject.Inject

class MyBudgesFragment  : DaggerFragment() {

    private lateinit var binding: FragmentMyBadgesBinding


    @Inject
    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyBadgesBinding.inflate(inflater, container, false)
        return binding.root



    }

    private fun setListener() {
        binding.txtClose.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        binding.rvMyBudges.adapter = AdapterMyBudges()

    }
}